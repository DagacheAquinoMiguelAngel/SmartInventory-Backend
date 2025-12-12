package com.cibertec.smartInventory.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.smartInventory.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Para listar solo las activas en el combo box de la app
    List<Category> findByActiveTrue();
}
