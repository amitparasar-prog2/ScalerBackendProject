package com.example.ProductServiceCatalog.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
	Long id;
    String title;
    Double price;
    String description;
    String image;
    String category;
}
