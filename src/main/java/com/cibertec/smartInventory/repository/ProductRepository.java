package com.cibertec.smartInventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.smartInventory.entity.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar por código de barras (útil si logras implementar el escáner)
    Optional<Product> findByBarcode(String barcode);

    // Buscar productos por nombre (Buscador tipo Google en la app)
    // Containing ignora mayúsculas/minúsculas y busca parciales
    List<Product> findByNameContainingIgnoreCase(String name);

    // CRÍTICO PARA SINCRONIZACIÓN:
    // "Dame todos los productos que han cambiado desde que me conecté la última vez"
    @Query("SELECT p FROM Product p WHERE p.updatedAt > :lastSyncDate")
    List<Product> findProductsUpdatedAfter(@Param("lastSyncDate") LocalDateTime lastSyncDate);
}