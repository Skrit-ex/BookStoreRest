package com.example.bookstorerest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Username", example = "Jon")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Filed cannot be empty")
    private String username;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Length no more 255 symbol")
    private String password;
}
