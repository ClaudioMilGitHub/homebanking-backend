package com.example.demo.service.implementation;

import com.example.demo.dto.account.AccountDTO;
import com.example.demo.dto.account.AccountRequestDTO;
import com.example.demo.entity.Account;
import com.example.demo.exceptions.custom.InsufficientFundsException;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.definition.AccountService;
import com.example.demo.service.definition.TransactionService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceJPA implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final AccountMapper accountMapper;

    @Override
    public AccountDTO findById(long id) throws Exception {
        return accountMapper.toAccountDTO(accountRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("account not found with id: " + id)));
    }

    @Override
    public AccountDTO findByAccountNumber(String accountNumber) throws Exception {
        return accountMapper.toAccountDTO(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new EntityNotFoundException("account not found with account number: " + accountNumber)));
    }

    @Override
    public List<AccountDTO> findAll() throws Exception {
        return accountRepository.findAll().stream()
                .map(accountMapper::toAccountDTO).toList();
    }

    @Override
    public AccountDTO withdraw(String accountNumber, double amount) throws Exception {
        String stringa;
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new EntityNotFoundException("account not found with account number: " + accountNumber));

        if(account.getBalance() < amount) {
            throw new InsufficientFundsException("""
                    Balance is lower than requested amount
                    [balance: %.2f / withdraw amount requested: %.2f]
                    """.formatted(account.getBalance(), amount));
        }

        if(amount <= 0) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "amount can't be negative or 0");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        transactionService.insertTransaction(account, amount, "withdraw", LocalDateTime.now());

        return  accountMapper.toAccountDTO(account);
    }



    @Override
    public AccountDTO deposit(String accountNumber, double amount) throws Exception {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new EntityNotFoundException("account not found with account number: " + accountNumber));

        if(amount <= 0) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "amount can't be negative or 0");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        return accountMapper.toAccountDTO(account);
    }

    @Override
    public void insertAccount(AccountRequestDTO dto) throws Exception {

        Account newAccount = accountMapper.fromAccountRequestDTO(dto);
        if(accountRepository.findByAccountNumber(newAccount.getAccountNumber()).isPresent()) {
            throw new EntityExistsException("""
                    account with accountNumber %s already exists
                    """.formatted(newAccount.getAccountNumber()));
        }
        newAccount.setBalance(0);
        accountRepository.save(newAccount);
    }

    @Override
    public void deleteAccountById(long id) throws Exception {

        if(accountRepository.findById(id).isPresent()) {
            accountRepository.deleteAccountById(id);
        } else {
            throw new EntityNotFoundException("account not found with id: " + id);
        }

    }
}
