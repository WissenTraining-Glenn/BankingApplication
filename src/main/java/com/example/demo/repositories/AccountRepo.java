package com.example.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Account;


public interface AccountRepo extends JpaRepository<Account, Integer>{
    public boolean existsByAccountNumber(int accountNumber);
    public Account findByAccountNumber(int accountNumber);
    public List<Account> findByBalanceLessThan(int amount);
    public List<Account> findByBalanceGreaterThan(int amount);
}
