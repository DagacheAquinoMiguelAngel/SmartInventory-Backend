package com.cibertec.smartInventory.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.smartInventory.entity.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    List<Category> getActiveCategories(); // Solo las que se pueden usar
    Category saveCategory(Category category);
    void deleteCategory(Long id);
}