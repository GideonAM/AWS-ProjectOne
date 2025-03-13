package com.awsprojectone.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthDto {

    @Email(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}", message = "Please enter a valid email")
    @NotBlank(message = "Email cannot be null or empty")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}",
            message = "Password should contain numbers and digits only. 3 to 20 characters")
    @NotBlank(message = "Password cannot be null or empty")
    private String password;

}
