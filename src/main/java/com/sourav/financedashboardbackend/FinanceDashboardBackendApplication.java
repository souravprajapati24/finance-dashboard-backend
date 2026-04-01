package com.sourav.financedashboardbackend;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FinanceDashboardBackendApplication {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(FinanceDashboardBackendApplication.class, args);
    }
    @PostConstruct
    public void test() {
        System.out.println(passwordEncoder.encode("123456"));
    }

}
