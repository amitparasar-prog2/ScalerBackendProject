package com.example.ProductServiceCatalog.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
	private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Category category;
    private Boolean isPrime;

}
