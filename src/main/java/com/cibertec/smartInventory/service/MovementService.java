package com.cibertec.smartInventory.service;

import java.util.List;

import com.cibertec.smartInventory.entity.Movement;

public interface MovementService {
    Movement registerMovement(Movement movement);
    List<Movement> getMovementsByProductId(Long productId);
    List<Movement> getAllMovements();
}
