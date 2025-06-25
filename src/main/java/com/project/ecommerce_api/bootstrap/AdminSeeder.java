package com.project.ecommerce_api.bootstrap;

import com.project.ecommerce_api.entities.Role;
import com.project.ecommerce_api.entities.User;
import com.project.ecommerce_api.models.auth.request.RegisterUserDto;
import com.project.ecommerce_api.repositories.RoleRepository;
import com.project.ecommerce_api.repositories.UserRepository;
import com.project.ecommerce_api.services.CustomUserDetailService;
import com.project.ecommerce_api.services.JwtService;
import com.project.ecommerce_api.utilities.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminSeeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailService userDetailService;

    private static final Logger logger = LoggerFactory.getLogger(AdminSeeder.class);

    public void onApplicationEvent () {
        this.createSuperAdmin();
    }

    private void createSuperAdmin() {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFirst_name("David");
        userDto.setLast_name("Famoyegun");
        userDto.setEmail("davidfamoyegun1@gmail.com");
        userDto.setPassword("David123");
        userDto.setPhone_number("07062736244");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        User user = new User();
        user.setFirstName(userDto.getFirst_name());
        user.setLastName(userDto.getLast_name());
        user.setPhoneNumber(userDto.getPhone_number());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsVerified(true);

        user.setRole(optionalRole.get());

        User admin = userRepository.save(user);

        UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        logger.info("Super Admin: {}", admin.getId());
        logger.info("Admin Token: {}", token);
    }
}
