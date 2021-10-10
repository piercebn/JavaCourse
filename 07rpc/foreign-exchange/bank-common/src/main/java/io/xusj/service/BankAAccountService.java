package io.xusj.service;


public interface BankAAccountService {

    Boolean decreaseBalance(String from, String to, double amount);

}
