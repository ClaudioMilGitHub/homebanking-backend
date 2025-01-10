package com.example.demo.dto.account;

import com.example.demo.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequestDTO {

    @NotNull
    private String accountNumber;

    @NotNull
    private User user;
}
