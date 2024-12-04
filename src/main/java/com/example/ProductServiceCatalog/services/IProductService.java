package com.example.ProductServiceCatalog.services;

import java.util.List;

import com.example.ProductServiceCatalog.models.Product;

public interface IProductService {
	List<Product> getProducts();

    Product getProductById(Long productId);

    Product createProduct(Product product);

    Product replaceProduct(Long productId,Product inputProduct);
}
