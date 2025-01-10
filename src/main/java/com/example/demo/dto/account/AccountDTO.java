package com.example.demo.dto.account;

import com.example.demo.dto.user.UserDTO;
import lombok.Data;

@Data
public class AccountDTO {

    private long id;

    private String accountNumber;
    private double balance;

    private UserDTO user;
}
