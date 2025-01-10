package com.example.demo.service.definition;

import com.example.demo.dto.account.AccountDTO;
import com.example.demo.dto.account.AccountRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {
    AccountDTO findById(long id) throws Exception;
    AccountDTO findByAccountNumber(String accountNumber) throws Exception;
    List<AccountDTO> findAll() throws  Exception;
    AccountDTO withdraw(String accountNumber, double amount) throws Exception;
    AccountDTO deposit(String accountNumber, double amount) throws Exception;
    void insertAccount(AccountRequestDTO dto) throws Exception;
    void deleteAccountById(long id) throws Exception;
}
