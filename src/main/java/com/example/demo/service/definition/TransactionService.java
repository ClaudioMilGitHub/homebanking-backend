package com.example.demo.service.definition;

import com.example.demo.dto.transaction.TransactionDTO;
import com.example.demo.entity.Account;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TransactionService {
    TransactionDTO findById(long id) throws Exception;
    List<TransactionDTO> findAll();
    void insertTransaction(Account account, double amount, String type, LocalDateTime creationTime) throws Exception;
    void deleteTransactionById(long id) throws Exception;
}
