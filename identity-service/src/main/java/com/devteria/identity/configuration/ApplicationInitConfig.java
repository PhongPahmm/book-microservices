/* (C)2024 */
package com.devteria.identity.configuration;

import java.time.LocalDate;
import java.util.HashSet;

import com.devteria.identity.constant.PredefinedRole;
import com.devteria.identity.entity.Role;
import com.devteria.identity.entity.User;
import com.devteria.identity.repository.RoleRepository;
import com.devteria.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());
                Role adminRole = roleRepository.save(Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                var role = new HashSet<Role>();
                role.add(adminRole);

                User user =
                        User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin"))
                                .firstName("Admin")
                                .lastName("User")
                                .dob(LocalDate.of(2003, 10, 18))
                                .roles(role)
                                .build();

                userRepository.save(user);
                log.info("User admin has been created");
            }
        };
    }
}
