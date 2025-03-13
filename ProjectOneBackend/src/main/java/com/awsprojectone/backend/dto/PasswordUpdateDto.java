package com.awsprojectone.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}",
            message = "Password should contain numbers and digits only. 3 to 20 characters")
    @NotBlank(message = "Password is required")
    private String password;
}
