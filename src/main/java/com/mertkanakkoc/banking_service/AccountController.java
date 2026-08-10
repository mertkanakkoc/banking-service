package com.mertkanakkoc.banking_service;

import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountRepository.findById(id).orElseThrow();
    }
}
