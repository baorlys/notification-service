package com.example.notification.service;

import com.example.notification.dto.CredentialDTO;
import com.example.notification.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface CredentialService {

    List<CredentialDTO> getCredentials(UUID userId);

    UUID createCredential(UserDTO userDTO);

    UUID getUserIdByCredential(CredentialDTO credentialDTO);

    CredentialDTO generateCredential();

    boolean isValidCredential(CredentialDTO credentialDTO);
}
