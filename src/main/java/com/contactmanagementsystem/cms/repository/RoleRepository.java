package com.contactmanagementsystem.cms.repository;

import com.contactmanagementsystem.cms.entity.ERole;
import com.contactmanagementsystem.cms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}