package com.example.demo.repository;

import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findById(long id);
    @NonNull
    List<Account> findAll();
    Account save(Account account);
    void deleteAccountById(long id);

}
