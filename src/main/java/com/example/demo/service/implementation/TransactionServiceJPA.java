package com.example.demo.service.implementation;

import com.example.demo.dto.transaction.TransactionDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.definition.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceJPA implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDTO findById(long id) throws Exception {
        return null;
    }

    @Override
    public List<TransactionDTO> findAll() {
        return List.of();
    }

    @Override
    public void insertTransaction(Account account, double amount, String type, LocalDateTime creationTime) throws Exception {

        Transaction newTransaction = new Transaction();

        newTransaction.setAccount(account);
        newTransaction.setAmount(amount);
        newTransaction.setType(type);
        newTransaction.setCreationDate(creationTime);

        transactionRepository.save(newTransaction);

    }

    @Override
    public void deleteTransactionById(long id) throws Exception {

    }
}
