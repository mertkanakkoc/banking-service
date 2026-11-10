package com.mertkanakkoc.banking_service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionLogService {
    private final TransactionLogRepository transactionLogRepository;

    public TransactionLogService(TransactionLogRepository transactionLogRepository){
        this.transactionLogRepository = transactionLogRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(Long fromId, Long toId, BigDecimal amount, boolean success) {
        TransactionLog log = new TransactionLog();
        log.setFromAccountId(fromId);
        log.setToAccountId(toId);
        log.setAmount(amount);
        log.setTimestamp(LocalDateTime.now());
        log.setSuccess(success);
        transactionLogRepository.save(log);
    }
}
