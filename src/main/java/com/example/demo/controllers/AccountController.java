package com.example.demo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Account;
import com.example.demo.models.Transaction;
import com.example.demo.repositories.AccountRepo;
import com.example.demo.repositories.TransactionRepo;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
public class AccountController {
    @Autowired
    AccountRepo accdoa;

    @Autowired
    TransactionRepo trxdoa;

    @PatchMapping("/changepwd/{id}/{oldpwd}/{newpwd}")
    public String changePassword(@PathVariable int id, @PathVariable String oldpwd, @PathVariable String newpwd) {
        if(id == 0 || oldpwd == null || newpwd == null) {
            return "Invalid Input";
        }
        if(accdoa.existsById(id)) {
            Account acc = accdoa.findById(id).get();
            if(acc.getPassword().equals(oldpwd)) {
            	if(acc.getPassword().equals(newpwd)) {
            		return "Cant use password from prev 2";
            	}
            	if(acc.getOldPassword()==null) {
	                acc.setPassword(newpwd);
	                acc.setOldPassword(oldpwd);
	                accdoa.save(acc);
	                return "Password Changed";
            	} else if(!acc.getOldPassword().equals(newpwd)) {
            		acc.setPassword(newpwd);
	                acc.setOldPassword(oldpwd);
	                accdoa.save(acc);
	                return "Password Changed";
            	} else return "Cant use password from prev 2";
            }
            return "Authentication Failed";
        }
        
        return "Account Not Found";
    }

    @PostMapping("/transfer")
    public boolean transfer(@RequestBody Transaction trx,@RequestParam int amount) {
        if(trx.getFromAccount() == 0 || trx.getToAccount() == 0 || trx.getIfsc() == null || amount <=0) {
            return false;
        }
        if(accdoa.existsById(trx.getFromAccount()) && accdoa.existsById(trx.getToAccount())) {
            Account from = accdoa.findById(trx.getFromAccount()).get();
            Account to = accdoa.findById(trx.getToAccount()).get();
            if(to.getIfsc().equals(trx.getIfsc()) && from.getBalance() >= amount) {
                from.setBalance(from.getBalance() - amount);
                to.setBalance(to.getBalance() + amount);
                accdoa.save(from);
                accdoa.save(to);
                trx.setAmount(amount);
                trxdoa.save(trx);
                return true;
            }
        }
        return false;
    }
    
    @GetMapping("/balance/{accountNumber}")
    public String getBalance(@PathVariable int accountNumber) {
        if(accountNumber == 0) {
            return "Account Not Found";
        }
        if(accdoa.existsByAccountNumber(accountNumber)) {
            Account acc = accdoa.findByAccountNumber(accountNumber);
            return "Balance: " + acc.getBalance();
        }
        return "Account Not Found";
    }

    @GetMapping("/accounts/below")
    public List<Account> getAccountsBelowBalance(@RequestParam int amount) {
        return accdoa.findByBalanceLessThan(amount);
    }

    @GetMapping("/accounts/above")
    public List<Account> getAccountsAboveBalance(@RequestParam int amount) {
        return accdoa.findByBalanceGreaterThan(amount);
    }
}
