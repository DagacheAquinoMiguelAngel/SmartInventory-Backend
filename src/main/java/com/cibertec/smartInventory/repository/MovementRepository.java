package com.cibertec.smartInventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.smartInventory.entity.Movement;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    
    // Ver el historial de movimientos de un producto específico
    List<Movement> findByProductIdOrderByMovementDateDesc(Long productId);
}