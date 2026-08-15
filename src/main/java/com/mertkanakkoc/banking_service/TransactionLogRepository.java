package com.mertkanakkoc.banking_service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepository  extends JpaRepository<TransactionLog, Long> {
}
