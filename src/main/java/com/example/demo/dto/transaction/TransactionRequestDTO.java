package com.example.demo.dto.transaction;

import com.example.demo.dto.account.AccountDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequestDTO {

    @NotNull
    @Min(0)
    private double amount;

    @NotNull
    private String type;
    @NotNull
    private AccountDTO account;
}
