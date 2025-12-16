package com.cibertec.smartInventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cibertec.smartInventory.entity.Movement;
import com.cibertec.smartInventory.service.MovementService;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovementController {

    private final MovementService movementService;

    // Registrar Entrada o Salida
    @PostMapping
    public ResponseEntity<?> createMovement(@RequestBody Movement movement) {
        try {
            Movement newMovement = movementService.registerMovement(movement);
            return ResponseEntity.ok(newMovement);
        } catch (RuntimeException e) {
            // Si falta stock o producto, devolvemos error 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Ver historial de un producto
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Movement>> getHistory(@PathVariable Long productId) {
        return ResponseEntity.ok(movementService.getMovementsByProductId(productId));
    }
        @GetMapping
    public ResponseEntity<List<Movement>> getAllMovement() {
    	return ResponseEntity.ok(movementService.getAllMovements());
    }
}
