package com.example.notification.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SenderInfo {
    // Either a phone number or an email
    @NotBlank(message = "Contact information is required")
    @Size(max = 255, message = "Contact information cannot exceed 255 characters")
    @Pattern(
            regexp = "^(\\+?[0-9. ()-]{7,25})?([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})?$",
            message = "Contact must be a valid phone number or email address"
    )
    String contact; // Maybe Email or PhoneNumber
    String name;
}
