package com.example.ProductServiceCatalog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.ProductServiceCatalog.clients.FakeStoreApiClient;
import com.example.ProductServiceCatalog.dtos.FakeStoreProductDto;
import com.example.ProductServiceCatalog.models.Category;
import com.example.ProductServiceCatalog.models.Product;

import io.micrometer.common.lang.Nullable;

@Service("fks")
public class FakeStoreProductService implements IProductService{
	
	@Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

	@Override
	public List<Product> getProducts() {
		List<Product> products = new ArrayList<>();
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity("http://fakestoreapi.com/products",FakeStoreProductDto[].class);
        if(response.getBody() == null ||
                response.getStatusCode().equals(HttpStatusCode.valueOf(500))) {
            return null;
        }

        for(FakeStoreProductDto fakeStoreProductDto : response.getBody()) {
            products.add(from(fakeStoreProductDto));
        }

        return products;
	}

	@Override
	public Product getProductById(Long productId) {
		return from(fakeStoreApiClient.getProductById(productId));
	}

	@Override
	public Product createProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product replaceProduct(Long productId, Product inputProduct) {
		FakeStoreProductDto input = from(inputProduct);
        ResponseEntity<FakeStoreProductDto> response =  requestForEntity("http://fakestoreapi.com/products/{productId}",HttpMethod.PUT,input, FakeStoreProductDto.class,productId);
        if(response.getBody() == null ||
                response.getStatusCode().equals(HttpStatusCode.valueOf(500))) {
            return null;
        }

        return from(response.getBody());
	}
	public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }

}
