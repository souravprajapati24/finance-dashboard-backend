package com.sourav.financedashboardbackend.repositories;

import com.sourav.financedashboardbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User , Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
