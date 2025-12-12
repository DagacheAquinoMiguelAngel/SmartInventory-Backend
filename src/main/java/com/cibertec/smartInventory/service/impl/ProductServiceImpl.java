package com.cibertec.smartInventory.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.cibertec.smartInventory.entity.Product;
import com.cibertec.smartInventory.repository.ProductRepository;
import com.cibertec.smartInventory.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Inyecta el repositorio automáticamente (Adiós @Autowired)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        // Aquí podrías validar cosas antes de guardar
        // Ej: Si el barcode viene vacío, generar uno interno (opcional)
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        // Buscamos si existe
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizamos campos
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPriceCost(productDetails.getPriceCost());
        existingProduct.setPriceSale(productDetails.getPriceSale());
        existingProduct.setStockCurrent(productDetails.getStockCurrent());
        existingProduct.setImageUrl(productDetails.getImageUrl()); // URL de Firebase
        
        // No actualizamos el ID ni la fecha de creación
        
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
