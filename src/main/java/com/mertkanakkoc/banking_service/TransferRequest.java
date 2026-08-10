package com.mertkanakkoc.banking_service;

import java.math.BigDecimal;

public class TransferRequest {
    private Long fromId;
    private Long toId;
    private BigDecimal amount;

    public Long getFromId() {
        return fromId;
    }

    public Long getToId() {
        return toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
