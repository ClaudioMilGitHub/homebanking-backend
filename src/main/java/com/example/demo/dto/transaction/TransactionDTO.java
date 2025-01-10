package com.example.demo.dto.transaction;

import com.example.demo.dto.account.AccountDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private double amount;
    private String type;

    private AccountDTO account;

    private LocalDateTime creationDate;

}
