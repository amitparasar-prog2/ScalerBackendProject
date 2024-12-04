package com.example.ProductServiceCatalog.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProductServiceCatalog.models.Product;



public interface ProductRepo extends JpaRepository<Product,Long>{
	
	Optional<Product> findById(Long productId);

    @SuppressWarnings("unchecked")
	Product save(Product product);
}
