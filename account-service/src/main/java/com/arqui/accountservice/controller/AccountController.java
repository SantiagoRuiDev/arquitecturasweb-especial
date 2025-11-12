package com.arqui.accountservice.controller;

import com.arqui.accountservice.dto.request.AccountRequestDTO;
import com.arqui.accountservice.dto.request.DiscountRequestDTO;
import com.arqui.accountservice.dto.request.RechargeRequestDTO;
import com.arqui.accountservice.dto.response.AccountResponseDTO;
import com.arqui.accountservice.dto.response.DiscountResultDTO;
import com.arqui.accountservice.dto.response.RechargeResultDTO;
import com.arqui.accountservice.dto.response.UserResponseDTO;
import com.arqui.accountservice.service.AccountService;
import com.arqui.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<AccountResponseDTO> save(@RequestBody AccountRequestDTO req) {
        AccountResponseDTO res =  accountService.save(req);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/{id}/recharge")
    public ResponseEntity<RechargeResultDTO> recharge(@PathVariable Long id, @RequestBody RechargeRequestDTO req) {
        RechargeResultDTO res =  accountService.recharge(id, req);
        return ResponseEntity.ok().body(res);
    }
    @PostMapping("/{id}/discount")
    public ResponseEntity<DiscountResultDTO> discount(@PathVariable Long id, @RequestBody DiscountRequestDTO req) {
        DiscountResultDTO res =  accountService.discount(id, req);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        AccountResponseDTO res = accountService.findById(id);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
        UserResponseDTO res = userService.findById(id);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean res = accountService.delete(id);
        if(res){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
