package com.example.ProductServiceCatalog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProductServiceCatalog.dtos.CategoryDto;
import com.example.ProductServiceCatalog.dtos.ProductDto;
import com.example.ProductServiceCatalog.models.Category;
import com.example.ProductServiceCatalog.models.Product;
import com.example.ProductServiceCatalog.services.IProductService;

@RestController
public class ProductController {
	
	@Autowired
    @Qualifier("sps")
    private IProductService productService;
	
	@GetMapping("/products")
    public List<ProductDto> getProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> response = productService.getProducts();

        if(response == null) {
            return null;
        }

        for(Product product : response) {
            productDtos.add(from(product));
        }

        return  productDtos;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try {
            if(productId <= 0) {
                throw new IllegalArgumentException("productId is invalid");
            }
            Product product = productService.getProductById(productId);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

            if (product == null) {
                headers.add("message", "product not exist");
                return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            ProductDto productDto = from(product);

            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }catch (IllegalArgumentException exception) {
            throw exception;
            // return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        productService.createProduct(from(productDto));
        return productDto;
    }

    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product inputProduct = from(productDto);
        Product response = productService.replaceProduct(id,inputProduct);
        return from(response);
    }

    private ProductDto from (Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }

	
}
