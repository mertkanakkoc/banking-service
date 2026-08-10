package com.mertkanakkoc.banking_service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {
    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public void transfer(Long fromId, Long toId, BigDecimal amount){
        Account from = accountRepository.findById(fromId).orElseThrow();
        Account to = accountRepository.findById(toId).orElseThrow();

        from.setBalance(from.getBalance().subtract(amount));
        accountRepository.save(from);

        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(to);
    }
}
