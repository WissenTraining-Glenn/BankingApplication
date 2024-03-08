package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private int ID;
    private int fromAccount;
    private int toAccount;
    private int amount;
    private String ifsc;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getFromAccount() {
        return fromAccount;
    }
    public void setFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
    }
    public int getToAccount() {
        return toAccount;
    }
    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getIfsc() {
        return ifsc;
    }
    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
}
