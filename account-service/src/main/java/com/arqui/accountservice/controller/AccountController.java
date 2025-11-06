package com.arqui.accountservice.controller;

import com.arqui.accountservice.dto.request.AccountRequestDTO;
import com.arqui.accountservice.dto.response.AccountResponseDTO;
import com.arqui.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("")
    public ResponseEntity<AccountResponseDTO> save(@RequestBody AccountRequestDTO req) {
        AccountResponseDTO res =  accountService.save(req);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Integer id) {
        AccountResponseDTO res = accountService.findById(id);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean res = accountService.delete(id);
        if(res){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
