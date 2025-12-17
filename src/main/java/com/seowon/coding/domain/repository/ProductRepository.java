package com.seowon.coding.domain.repository;

import com.seowon.coding.domain.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByCategory(String category);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByStockQuantityGreaterThan(int minStock);

    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithLock(Long productId);
}