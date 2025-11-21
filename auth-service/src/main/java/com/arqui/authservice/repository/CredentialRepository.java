package com.arqui.authservice.repository;

import com.arqui.authservice.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Credential findByUsername(String username);
}
