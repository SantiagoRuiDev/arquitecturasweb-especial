package com.arqui.accountservice.controller;

import com.arqui.accountservice.dto.request.UserRequestDTO;
import com.arqui.accountservice.dto.response.UserResponseDTO;
import com.arqui.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserRequestDTO dto) {
        UserResponseDTO res = userService.save(dto);
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        UserResponseDTO res = userService.update(id, dto);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean res = userService.delete(id);
        if(res){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO res = userService.findById(id);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> res = userService.findAll();
        return ResponseEntity.ok().body(res);
    }
}
