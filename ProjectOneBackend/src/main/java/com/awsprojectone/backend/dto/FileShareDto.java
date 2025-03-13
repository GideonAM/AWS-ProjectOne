package com.awsprojectone.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileShareDto {

    @NotBlank(message = "File ID is required")
    private String fileId;

    @Email(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}", message = "Please enter a valid email")
    @NotBlank(message = "Email cannot be null or empty")
    private String receiverEmail;
}
