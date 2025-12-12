package com.cibertec.smartInventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal; // Importante para dinero
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a Uno: Muchos productos pertenecen a una categoría
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = true, unique = true)
    private String barcode;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    // Usamos BigDecimal para dinero, nunca Double
    @Column(name = "price_cost", nullable = false)
    private BigDecimal priceCost;

    @Column(name = "price_sale", nullable = false)
    private BigDecimal priceSale;

    @Column(name = "stock_current", nullable = false)
    private Integer stockCurrent;

    @Column(name = "stock_min")
    private Integer stockMin;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Se actualiza solo cada vez que modificas el producto
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
