package com.fullstack.demo.repository;

import com.fullstack.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleIgnoreCase(String role);
}
