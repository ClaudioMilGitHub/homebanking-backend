package com.example.demo.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseUserRequestDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
