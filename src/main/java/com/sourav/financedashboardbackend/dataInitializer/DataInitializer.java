package com.sourav.financedashboardbackend.dataInitializer;

import com.sourav.financedashboardbackend.enums.Role;
import com.sourav.financedashboardbackend.model.User;
import com.sourav.financedashboardbackend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        if (userRepository.findByEmail("admin@test.com")==null) {

            User admin = new User();
            admin.setName("Admin_user");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin@1234"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);

            userRepository.save(admin);

            System.out.println("Admin user created!");
        }
    }
}
