package com.project.ecommerce_api.services;

//Project Classes
import com.project.ecommerce_api.entities.Role;
import com.project.ecommerce_api.entities.Token;
import com.project.ecommerce_api.entities.User;
import com.project.ecommerce_api.exceptions.CustomException;
import com.project.ecommerce_api.helpers.ResponseUtil;
import com.project.ecommerce_api.helpers.TokenGenerator;
import com.project.ecommerce_api.models.JwtAuthenticationResponse;
import com.project.ecommerce_api.models.UserInfo;
import com.project.ecommerce_api.models.auth.request.LoginUserDto;
import com.project.ecommerce_api.models.auth.request.RegisterUserDto;
import com.project.ecommerce_api.models.auth.request.ResendOtpDto;
import com.project.ecommerce_api.models.auth.request.VerifyUserDto;
import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.models.auth.response.LoginResponse;
import com.project.ecommerce_api.models.auth.response.VerificationResponse;
import com.project.ecommerce_api.models.auth.response.SignupResponse;
import com.project.ecommerce_api.repositories.RoleRepository;
import com.project.ecommerce_api.repositories.TokenRepository;
import com.project.ecommerce_api.repositories.UserRepository;
import com.project.ecommerce_api.utilities.EmailType;
import com.project.ecommerce_api.utilities.RoleEnum;
import com.project.ecommerce_api.utilities.TokenType;

//Spring Classes
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Java Classes
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailSender;
    private final CustomUserDetailService userDetailService;
    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public CustomResponse<SignupResponse> signup(RegisterUserDto userInput) {
        CustomResponse<SignupResponse> signupResponse = new CustomResponse<>();
        SignupResponse userDetails = new SignupResponse();
        User user = new User();

        // Check for existing user
        Optional<User> existingUser = userRepository.findByEmail(userInput.getEmail());
        if (existingUser.isPresent()) {
            return ResponseUtil.createErrorResponse(signupResponse, HttpStatus.CONFLICT, "User Already Exists");
        }
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }
        Role userRole = optionalRole.get();


        // Set user details
        user.setFirstName(userInput.getFirst_name());
        user.setLastName(userInput.getLast_name());
        user.setPhoneNumber(userInput.getPhone_number());
        user.setEmail(userInput.getEmail());
        user.setPassword(passwordEncoder.encode(userInput.getPassword()));
        user.setRole(userRole);
        user.setIsVerified(false);
        user.setCreatedAt(LocalDateTime.now());

        // Create and set token
        Token token = new Token();
        token.setUser(user);
        token.setToken(TokenGenerator.generateToken());
        token.setType(TokenType.EMAIL_VERIFICATION);
        token.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        token.setIsUsed(false);

        try {
            User savedUser = userRepository.save(user);
            logger.info("User successfully created: {}", savedUser.getId());

            tokenRepository.save(token);
            emailSender.sendWelcomeEmail(savedUser.getEmail(), savedUser.getFirstName(), token.getToken());

            logger.info("Email Sent Successfully. Proceed for verification");

            userDetails.setEmail(savedUser.getEmail());
            userDetails.setFirst_name(savedUser.getFirstName());
            userDetails.setLast_name(savedUser.getLastName());
            userDetails.setIsVerified(savedUser.getIsVerified());

            signupResponse.setSuccess(true);
            signupResponse.setStatusCode(HttpStatus.CREATED);
            signupResponse.setMessage("User successfully created (Not Verified)");
            signupResponse.setData(userDetails);
        } catch (Exception e) {
            logger.error("Error creating new user", e);
            throw new CustomException("Error creating new user", "500");
        }
        return signupResponse;
    }


    public CustomResponse<LoginResponse> login(LoginUserDto userInput) {
        CustomResponse<LoginResponse> loginResponse = new CustomResponse<>();
        LoginResponse loginInfo = new LoginResponse();

        // Check if user exists
        Optional<User> user = userRepository.findByEmail(userInput.getEmail());
        if (user.isEmpty()) {
            return ResponseUtil.createErrorResponse(loginResponse, HttpStatus.NOT_FOUND, "User Doesn't exist");
        }
        User requiredUser = user.get();

        boolean isValidPassword = passwordEncoder.matches(userInput.getPassword(), requiredUser.getPassword());
        if (!isValidPassword) {
            return ResponseUtil.createErrorResponse(loginResponse, HttpStatus.UNAUTHORIZED, "Invalid Password");
        }

        if (!requiredUser.getIsVerified()) {
            loginInfo.setUserInfo(new UserInfo(
                    requiredUser.getId(),
                    requiredUser.getFirstName(),
                    requiredUser.getLastName(),
                    requiredUser.getEmail(),
                    false
            ));

            loginInfo.setAuthInfo(new JwtAuthenticationResponse(null));

            loginResponse.setSuccess(false);
            loginResponse.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
            loginResponse.setMessage("Credentials accepted, but user is not verified. Verification required.");
            loginResponse.setData(loginInfo);

            return loginResponse;
        }

        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(requiredUser.getEmail());
            String accessToken = jwtService.generateToken(userDetails);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, requiredUser.getPassword(), userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            loginInfo.setAuthInfo(new JwtAuthenticationResponse(accessToken));
            loginInfo.setUserInfo(new UserInfo(
                    requiredUser.getId(),
                    requiredUser.getFirstName(),
                    requiredUser.getLastName(),
                    requiredUser.getEmail(),
                    requiredUser.getIsVerified()
            ));

            loginResponse.setSuccess(true);
            loginResponse.setStatusCode(HttpStatus.ACCEPTED);
            loginResponse.setMessage("User authenticated successfully");
            loginResponse.setData(loginInfo);
        } catch (Exception e) {
            logger.error("Error authentication user, login(AuthenticationService.java): ", e);
            throw new CustomException("Error in user authentication");
        }
        return loginResponse;
    }

    public CustomResponse<VerificationResponse> verify(VerifyUserDto userInput) {
        CustomResponse<VerificationResponse> verificationResponse = new CustomResponse<>();
        VerificationResponse verifyInfo = new VerificationResponse();

        // Check if user exists
        Optional<User> user = userRepository.findByEmail(userInput.getEmail());
        if (user.isEmpty()) {
            return ResponseUtil.createErrorResponse(verificationResponse, HttpStatus.NOT_FOUND, "User doesn't exist");
        }
        User requiredUser = user.get();

        // Check if token exists
        Optional<Token> token = tokenRepository.findByUser(requiredUser);
        if (token.isEmpty()) {
            return ResponseUtil.createErrorResponse(verificationResponse, HttpStatus.NOT_FOUND, "Token doesn't exist");
        }
        Token requiredToken = token.get();

        //Check if token isUsed
        if (requiredToken.getIsUsed()) {
            return  ResponseUtil.createErrorResponse(verificationResponse, HttpStatus.IM_USED, "Token has been used");
        }

        //Check if token is expired
        if (requiredToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            return  ResponseUtil.createErrorResponse(verificationResponse, HttpStatus.UNAUTHORIZED, "Expired Token");
        }

        // Check if user input matches token value
        if (!Objects.equals(userInput.getToken(), requiredToken.getToken())) {
            return ResponseUtil.createErrorResponse(verificationResponse, HttpStatus.UNAUTHORIZED, "Invalid Token");
        }

        // Update user and token status
        requiredUser.setIsVerified(true);
        requiredToken.setIsUsed(true);

        try {
            User savedUser = userRepository.save(requiredUser);
            tokenRepository.save(requiredToken);

            // Set authentication context
            UserDetails userDetails = userDetailService.loadUserByUsername(requiredUser.getEmail());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, requiredUser.getPassword(), userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtService.generateToken(userDetails);

            verifyInfo.setAuthInfo(new JwtAuthenticationResponse(accessToken));
            verifyInfo.setUser(
                    new UserInfo(
                            savedUser.getId(),
                            savedUser.getFirstName(),
                            savedUser.getLastName(),
                            savedUser.getEmail(),
                            savedUser.getIsVerified()
                    )
            );

            verificationResponse.setSuccess(true);
            verificationResponse.setStatusCode(HttpStatus.OK);
            verificationResponse.setMessage("Verification Successful");
            verificationResponse.setData(verifyInfo);

            logger.info("Verification Successful for user: {}", requiredUser.getId());
        } catch (Exception e) {
            logger.error("Error in verify (AuthenticationService.java): ", e);
            throw new CustomException("Error in user verification");
        }
        return verificationResponse;
    }

    public CustomResponse<?> resendOtp (ResendOtpDto resendOtpDto) {
        CustomResponse<?> response = new CustomResponse<>();

        Optional<User> userOptional = userRepository.findByEmail(resendOtpDto.getUserEmail());
        if (userOptional.isEmpty()) {
            return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "user not found");
        }
        User requiredUser = userOptional.get();


        //TODO Check out logic for this resend token method

        Optional<Token> existingToken = findActiveTokenByUser(requiredUser.getId(), TokenType.EMAIL_VERIFICATION);

        Token token  = new Token();
        token.setUser(userOptional.get());
        token.setType(TokenType.EMAIL_VERIFICATION);
        token.setToken(TokenGenerator.generateToken());
        token.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        token.setIsUsed(false);

        try {
            tokenRepository.save(token);

            emailSender.sendEmail(requiredUser.getEmail(), requiredUser.getFirstName(), token.getToken(), EmailType.EMAIL_VERIFICATION);

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Otp Sent. Verify you account to proceed.");
        } catch (Exception e) {
            logger.error("Error sending new otp", e);
            throw new CustomException("Error sending new otp", "500");
        }

        return response;
    }

    private Optional<Token> findActiveTokenByUser(UUID userId, TokenType type) {
        return tokenRepository.findFirstByUser_IdAndTypeAndIsUsedFalseAndExpiredAtAfter(
                userId, type, LocalDateTime.now());
    }

    private Token getCorrectToken(String email, EmailType emailType) {

    }
}
