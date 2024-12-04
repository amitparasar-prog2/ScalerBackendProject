package com.example.ProductServiceCatalog.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  abstract class BaseModel {
	private Long id;
	private Date createdAt;
	private Date lastUpdatedAt;
	private State state;
	
	
}
