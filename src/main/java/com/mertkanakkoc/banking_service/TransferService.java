package com.mertkanakkoc.banking_service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {
    private final AccountRepository accountRepository;
    private final TransactionLogService transactionLogService;

    public TransferService(AccountRepository accountRepository, TransactionLogService transactionLogService){
        this.accountRepository = accountRepository;
        this.transactionLogService = transactionLogService;
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount){
        try {
            Account from = accountRepository.findById(fromId).orElseThrow();
            Account to = accountRepository.findById(toId).orElseThrow();

            from.setBalance(from.getBalance().subtract(amount));
            accountRepository.save(from);

            to.setBalance(to.getBalance().add(amount));
            accountRepository.save(to);

            transactionLogService.log(fromId, toId, amount, true);
        } catch (Exception e) {
            transactionLogService.log(fromId, toId, amount, false);
            throw e;
        }
    }
}
