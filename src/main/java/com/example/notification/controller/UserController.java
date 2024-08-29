package com.example.notification.controller;

import com.example.notification.dto.CredentialDTO;
import com.example.notification.dto.UserDTO;
import com.example.notification.service.CredentialService;
import com.example.notification.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class UserController {
    UserService userService;
    CredentialService credentialService;

    @PostMapping("/create-credential")
    public ResponseEntity<UUID> createCredential(@RequestParam("email") String email) {
        UserDTO user = userService.getUserDTO(email);
        return ResponseEntity.ok(credentialService.createCredential(user));
    }

    @GetMapping("/get-credentials")
    public ResponseEntity<List<CredentialDTO>> getCredentials(@RequestParam("email") String email) {
        UserDTO user = userService.getUserDTO(email);
        return ResponseEntity.ok(credentialService.getCredentials(user.getId()));
    }
}
