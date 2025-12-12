package com.cibertec.smartInventory.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // IMPORTANTE

import com.cibertec.smartInventory.entity.Movement;
import com.cibertec.smartInventory.entity.Product;
import com.cibertec.smartInventory.enums.MovementType;
import com.cibertec.smartInventory.repository.MovementRepository;
import com.cibertec.smartInventory.repository.ProductRepository;
import com.cibertec.smartInventory.service.MovementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional // Esto asegura que si falla la actualización del stock, no se guarde el movimiento
    public Movement registerMovement(Movement movement) {
        // 1. Validar que el producto exista
        Product product = productRepository.findById(movement.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // 2. Calcular nuevo stock
        int currentStock = product.getStockCurrent();
        int quantity = movement.getQuantity();

        if (movement.getType() == MovementType.IN) {
            // Entrada: Sumamos
            product.setStockCurrent(currentStock + quantity);
        } else if (movement.getType() == MovementType.OUT) {
            // Salida: Restamos (validando que no quede negativo)
            if (currentStock < quantity) {
                throw new RuntimeException("Stock insuficiente para realizar la salida");
            }
            product.setStockCurrent(currentStock - quantity);
        }
        // Ajuste: Simplemente reemplazamos o aplicamos lógica custom (por ahora lo trataremos como corrección manual)
        // Podrías decidir que ADJUSTMENT simplemente setea el stock al valor quantity.
        
        // 3. Guardar el Producto con el nuevo stock
        productRepository.save(product);

        // 4. Guardar el registro del Movimiento (Kardex)
        return movementRepository.save(movement);
    }

    @Override
    public List<Movement> getMovementsByProductId(Long productId) {
        return movementRepository.findByProductIdOrderByMovementDateDesc(productId);
    }
}