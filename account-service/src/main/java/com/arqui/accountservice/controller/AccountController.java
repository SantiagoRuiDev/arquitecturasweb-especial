package com.arqui.accountservice.controller;

import com.arqui.accountservice.dto.request.AccountRequestDTO;
import com.arqui.accountservice.dto.request.DiscountRequestDTO;
import com.arqui.accountservice.dto.request.RechargeRequestDTO;
import com.arqui.accountservice.dto.request.StatusUpdateRequestDTO;
import com.arqui.accountservice.dto.response.AccountResponseDTO;
import com.arqui.accountservice.dto.response.DiscountResultDTO;
import com.arqui.accountservice.dto.response.RechargeResultDTO;
import com.arqui.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

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

    @GetMapping("")
    public ResponseEntity<List<AccountResponseDTO>> findAll() {
        List<AccountResponseDTO> res = accountService.findAll();
        return ResponseEntity.ok().body(res);
    }

    // (B) Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
    // momentáneo de la aplicación.
    @PutMapping("/set-status/{id}")
    public ResponseEntity<AccountResponseDTO> setStatus(@PathVariable Long id, @RequestBody StatusUpdateRequestDTO dto) {
        AccountResponseDTO res = accountService.setStatus(id, dto);
        return ResponseEntity.ok().body(res);
    }

    // Esta anulación elimina la cuenta permanentemente
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
