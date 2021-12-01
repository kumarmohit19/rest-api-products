package com.restapi.msproducts.service;

import java.util.List;
import java.util.Optional;

import com.restapi.msproducts.model.Product;

public interface Iproduct {
	List<Product> getAllProducts();

	Optional<Product> getProductById(int id);

	Product saveProduct(Product newProduct);

	void deleteProduct(int id);
}
