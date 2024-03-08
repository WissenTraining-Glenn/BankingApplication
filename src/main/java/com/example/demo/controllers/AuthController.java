package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repositories.AccountRepo;
import com.example.demo.models.Account;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController {
	@Autowired
	AccountRepo accdoa;
	
	@PostMapping("/login")
	public boolean Login(@RequestBody Account acc) {
		if(acc.getCustomerID() == 0 || acc.getPassword() == null) {
			return false;
		}
		if(accdoa.existsById(acc.getCustomerID())) {
			Account acc1 = accdoa.findById(acc.getCustomerID()).get();
			if(acc1.getPassword().equals(acc.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	@PostMapping("/register")
	public Account Register(@RequestBody Account acc) {
		if(acc.getCustomerID() != 0 || acc.getPassword() == null || acc.getName() == null || acc.getPhone() == 0 || acc.getAccountNumber() == 0 || acc.getIfsc() == null || acc.getType() == null){
			return null;
		}
		if(accdoa.existsByAccountNumber(acc.getAccountNumber()))  {
			return null;
		}
		accdoa.save(acc);
		return acc;
	}	
}
