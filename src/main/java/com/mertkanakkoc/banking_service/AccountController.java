package com.mertkanakkoc.banking_service;

import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountRepository accountRepository;
    private final TransferService transferService;

    public AccountController(AccountRepository accountRepository, TransferService transferService) {
        this.accountRepository = accountRepository;
        this.transferService = transferService;
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountRepository.findById(id).orElseThrow();
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequest request) {
        transferService.transfer(request.getFromId(), request.getToId(), request.getAmount());
    }
}
