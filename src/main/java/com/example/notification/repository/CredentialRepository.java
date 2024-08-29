package com.example.notification.repository;

import com.example.notification.dto.CredentialDTO;
import com.example.notification.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Credential c " +
            "WHERE c.apiKey = :apiKey " +
            "AND c.secretKey = :secretKey")
    boolean existsByApiKeyAndSecretKey(String apiKey, String secretKey);

    @Query("SELECT new com.example.notification.dto.CredentialDTO(c.apiKey,c.secretKey) " +
            "FROM Credential c " +
            "WHERE c.user.id = :userId and c.status = com.example.notification.enums.CredentialStatus.ACTIVE")
    List<CredentialDTO> findAllByUserId(UUID userId);
}
