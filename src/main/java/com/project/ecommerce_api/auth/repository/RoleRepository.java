package com.project.ecommerce_api.auth.repository;


import com.project.ecommerce_api.auth.domain.Role;
import com.project.ecommerce_api.shared.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName (RoleEnum name);
}
