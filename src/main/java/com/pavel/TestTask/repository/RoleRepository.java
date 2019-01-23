package com.pavel.TestTask.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavel.TestTask.entity.auth.KindOfRole;
import com.pavel.TestTask.entity.auth.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(KindOfRole kind);
}
