package com.example.ProductServiceCatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProductServiceCatalog.models.Product;
import com.example.ProductServiceCatalog.repos.ProductRepo;

@Service("sps")
public class StorageProductService implements IProductService{
	
	@Autowired
    private ProductRepo productRepo;
	
	@Override
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductById(Long productId) {
		 System.out.println("Reading from db !!");
	       Optional<Product> productOptional  = productRepo.findById(productId);
	       if(productOptional.isPresent()) {
	           return productOptional.get();
	       }

	       return  null;

	}

	@Override
	public Product createProduct(Product product) {
		System.out.println("storing into db !!");
        return productRepo.save(product);
	}

	@Override
	public Product replaceProduct(Long productId, Product inputProduct) {
		// TODO Auto-generated method stub
		return null;
	}

}
