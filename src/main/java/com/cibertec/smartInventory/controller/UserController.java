package com.cibertec.smartInventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cibertec.smartInventory.entity.User;
import com.cibertec.smartInventory.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // Endpoint clave: iOS llama a esto justo después de hacer login exitoso en Firebase
    @PostMapping("/sync")
    public ResponseEntity<User> syncUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.syncUser(user));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<User> getUserByUid(@PathVariable String uid) {
        return userService.getUserByFirebaseUid(uid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}