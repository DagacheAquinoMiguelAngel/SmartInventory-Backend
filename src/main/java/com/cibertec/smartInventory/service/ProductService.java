package com.cibertec.smartInventory.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.smartInventory.entity.Product;

public interface ProductService {
	List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    Product updateProduct(Long id, Product productDetails);
    void deleteProduct(Long id);
    
    // Método especial para buscar (Buscador en la App)
    List<Product> searchProducts(String name);
}
