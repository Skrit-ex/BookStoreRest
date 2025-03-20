package com.example.bookstorerest.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Username", example = "Jon")
    @Size(min = 5, max = 50, message = "Username from 5 - 50 symbols")
    @NotBlank(message = "Filed cannot be empty")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Length from 8 - 255 symbols")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
