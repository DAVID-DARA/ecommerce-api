package com.project.ecommerce_api.auth.service;

//Project Classes
import com.project.ecommerce_api.auth.domain.Role;
import com.project.ecommerce_api.auth.model.JwtAuthenticationResponse;
import com.project.ecommerce_api.auth.model.request.LoginUserDto;
import com.project.ecommerce_api.auth.model.request.RegisterUserDto;
import com.project.ecommerce_api.auth.model.request.ResendOtpDto;
import com.project.ecommerce_api.auth.model.request.VerifyUserDto;
import com.project.ecommerce_api.auth.model.response.LoginResponse;
import com.project.ecommerce_api.auth.model.response.SignupResponse;
import com.project.ecommerce_api.auth.model.response.VerificationResponse;
import com.project.ecommerce_api.auth.repository.RoleRepository;
import com.project.ecommerce_api.auth.token.Token;
import com.project.ecommerce_api.auth.token.TokenRepository;
import com.project.ecommerce_api.cart.domain.Cart;
import com.project.ecommerce_api.cart.repository.CartRepository;
import com.project.ecommerce_api.config.CustomUserDetailService;
import com.project.ecommerce_api.helpers.email.EmailService;
import com.project.ecommerce_api.shared.enums.EmailType;
import com.project.ecommerce_api.shared.enums.RoleEnum;
import com.project.ecommerce_api.shared.enums.TokenType;
import com.project.ecommerce_api.shared.exceptions.CustomException;
import com.project.ecommerce_api.shared.response.CustomResponse;
import com.project.ecommerce_api.shared.utils.ResponseUtil;
import com.project.ecommerce_api.shared.utils.TokenGenerator;
import com.project.ecommerce_api.user.domain.User;
import com.project.ecommerce_api.user.dto.UserInfo;
import com.project.ecommerce_api.user.repository.UserRepository;
import com.project.ecommerce_api.wishlist.domain.Wishlist;
import com.project.ecommerce_api.wishlist.repository.WishlistRepository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    //Repositories
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;

    //Services
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailSender;
    private final CustomUserDetailService userDetailService;
    private final JwtService jwtService;

    //Helpers
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Transactional
    public CustomResponse<SignupResponse> signup(RegisterUserDto request) {
        CustomResponse<SignupResponse> signupResponse = new CustomResponse<>();
        try {
            SignupResponse userDetails = new SignupResponse();
            User user = new User();

            // Check for existing user
            Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                return ResponseUtil.createErrorResponse(signupResponse, HttpStatus.CONFLICT, "User Already Exists");
            }
            Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

            if (optionalRole.isEmpty()) {
                return null;
            }
            Role userRole = optionalRole.get();


            // Set user details
            user.setFirstName(request.getFirst_name());
            user.setLastName(request.getLast_name());
            user.setPhoneNumber(request.getPhone_number());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
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

            Cart cart = new Cart();
            Wishlist wishlist = new Wishlist();

            User savedUser = userRepository.save(user);

            cart.setUser(savedUser);
            cartRepository.save(cart);

            tokenRepository.save(token);

            wishlist.setUser(savedUser);
            wishlistRepository.save(wishlist);

            emailSender.sendWelcomeEmail(savedUser.getEmail(), savedUser.getFirstName(), token.getToken());
            logger.info("User successfully created: {}", savedUser.getId());
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
            logger.error("Error creating new user, signup(AuthenticationService.java)", e);
            throw new CustomException("Error creating new user", "500");
        }
        return signupResponse;
    }

    @Transactional
    public CustomResponse<LoginResponse> login(LoginUserDto request) {
        CustomResponse<LoginResponse> loginResponse = new CustomResponse<>();
        LoginResponse loginInfo = new LoginResponse();

        // Check if user exists
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return ResponseUtil.createErrorResponse(loginResponse, HttpStatus.NOT_FOUND, "User Doesn't exist");
        }
        User requiredUser = user.get();

        boolean isValidPassword = passwordEncoder.matches(request.getPassword(), requiredUser.getPassword());
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
            logger.error("Error authenticating user, login(AuthenticationService.java): ", e);
            throw new CustomException("Error in user authentication");
        }
        return loginResponse;
    }

    @Transactional
    public CustomResponse<VerificationResponse> verify(VerifyUserDto request) {
        CustomResponse<VerificationResponse> verificationResponse = new CustomResponse<>();
        try {
            VerificationResponse verifyInfo = new VerificationResponse();

            // Check if user exists
            Optional<User> user = userRepository.findByEmail(request.getEmail());
            if (user.isEmpty()) {
                return ResponseUtil.createErrorResponse(verificationResponse, HttpStatus.NOT_FOUND, "User doesn't exist");
            }
            User requiredUser = user.get();

            // Check if token exists
            Optional<Token> token = tokenRepository.findFirstByUserIdAndTypeOrderByCreatedAtDesc(requiredUser.getId(), TokenType.EMAIL_VERIFICATION);

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
            if (!Objects.equals(request.getToken(), requiredToken.getToken())) {
                return ResponseUtil.createErrorResponse(verificationResponse, HttpStatus.UNAUTHORIZED, "Invalid Token");
            }

            // Update user and token status
            requiredUser.setIsVerified(true);
            requiredToken.setIsUsed(true);

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
            logger.error("Error in user validation, verify(AuthenticationService.java): ", e);
            throw new CustomException("Error in user verification");
        }
        return verificationResponse;
    }


    public CustomResponse<?> resendOtp (ResendOtpDto request) {
        CustomResponse<?> response = new CustomResponse<>();
        try {

            Optional<User> userOptional = userRepository.findByEmail(request.getUserEmail());
            if (userOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "user not found");
            }
            User currentUser = userOptional.get();

    //        invalidateToken(currentUser, request.getTokenType());

            Token token  = new Token();
            token.setUser(userOptional.get());
            token.setType(TokenType.EMAIL_VERIFICATION);
            token.setToken(TokenGenerator.generateToken());
            token.setExpiredAt(LocalDateTime.now().plusMinutes(5));


            tokenRepository.save(token);

            emailSender.sendEmail(currentUser.getEmail(), currentUser.getFirstName(), token.getToken(), EmailType.EMAIL_VERIFICATION);

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Otp Sent. Verify your account to proceed.");
        } catch (Exception e) {
            logger.error("Error sending new otp", e);
            throw new CustomException("Error sending new otp", "500");
        }
        return response;
    }

}
