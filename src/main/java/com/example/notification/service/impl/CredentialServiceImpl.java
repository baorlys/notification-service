package com.example.notification.service.impl;

import com.example.notification.dto.CredentialDTO;
import com.example.notification.dto.UserDTO;
import com.example.notification.enums.KeyType;
import com.example.notification.global.mapper.Mapper;
import com.example.notification.global.service.AESEncryptionService;
import com.example.notification.global.utils.key.KeyGeneratorFactory;
import com.example.notification.model.Credential;
import com.example.notification.model.User;
import com.example.notification.repository.CredentialRepository;
import com.example.notification.service.CredentialService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CredentialServiceImpl implements CredentialService {
    CredentialRepository credentialRepository;
    Mapper mapper;


    @Override
    public List<CredentialDTO> getCredentials(UUID userId) {
        return credentialRepository.findAllByUserId(userId).stream()
                .map(this::decryptCredential)
                .collect(Collectors.toList());
    }

    @Override
    public UUID createCredential(UserDTO user) {
        CredentialDTO credentialDTO = generateCredential();
        // Save the credential to the database
        Credential credential = new Credential();
        mapper.map(encryptCredential(credentialDTO), credential);
        credential.setUser(mapper.map(user, User.class));

        credentialRepository.save(credential);
        return credential.getId();
    }

    @Override
    public UUID getUserIdByCredential(CredentialDTO credentialDTO) {
        CredentialDTO encryptCre = encryptCredential(credentialDTO);
        return credentialRepository.getUserWithCredential(encryptCre.getApiKey(), encryptCre.getSecretKey());
    }

    @Override
    public CredentialDTO generateCredential() {
        String apiKey = KeyGeneratorFactory.getKeyGenerator(KeyType.API_KEY).generate();
        String secretKey = KeyGeneratorFactory.getKeyGenerator(KeyType.SECRET_KEY).generate();

        return new CredentialDTO(apiKey, secretKey);
    }

    @Override
    public boolean isValidCredential(CredentialDTO credentialDTO) {
        return getUserIdByCredential(credentialDTO) != null;
    }

    private CredentialDTO encryptCredential(CredentialDTO credential) {
        credential.setApiKey(AESEncryptionService.encrypt(credential.getApiKey()));
        credential.setSecretKey(AESEncryptionService.encrypt(credential.getSecretKey()));
        return credential;
    }

    private CredentialDTO decryptCredential(CredentialDTO credential) {
        credential.setApiKey(AESEncryptionService.decrypt(credential.getApiKey()));
        credential.setSecretKey(AESEncryptionService.decrypt(credential.getSecretKey()));
        return credential;
    }
}
