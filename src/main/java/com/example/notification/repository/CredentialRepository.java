package com.example.notification.repository;

import com.example.notification.dto.CredentialDTO;
import com.example.notification.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {
    @Query("SELECT c.user.id " +
            "FROM Credential c " +
            "WHERE c.apiKey = :apiKey " +
            "AND c.secretKey = :secretKey")
    UUID getUserWithCredential(String apiKey, String secretKey);

    @Query("SELECT new com.example.notification.dto.CredentialDTO(c.user.id, c.apiKey,c.secretKey) " +
            "FROM Credential c " +
            "WHERE c.user.id = :userId and c.status = com.example.notification.enums.CredentialStatus.ACTIVE")
    List<CredentialDTO> findAllByUserId(UUID userId);
}
