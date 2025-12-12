package com.cibertec.smartInventory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.cibertec.smartInventory.entity.Category;
import com.cibertec.smartInventory.repository.CategoryRepository;
import com.cibertec.smartInventory.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getActiveCategories() {
        return categoryRepository.findByActiveTrue();
    }

    @Override
    public Category saveCategory(Category category) {
        // Por defecto activamos la categoría si viene nulo
        if (category.getActive() == null) {
            category.setActive(true);
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        // Eliminación lógica (Mejor práctica): Solo la desactivamos
        categoryRepository.findById(id).ifPresent(cat -> {
            cat.setActive(false);
            categoryRepository.save(cat);
        });
        // Si prefieres borrarla de verdad: categoryRepository.deleteById(id);
    }
}
