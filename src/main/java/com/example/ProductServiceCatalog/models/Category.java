package com.example.ProductServiceCatalog.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category extends BaseModel{

	private String name;
    private String description;
    private List<Product> products;
}
