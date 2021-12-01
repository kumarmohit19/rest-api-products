package com.restapi.msproducts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.msproducts.model.Product;
import com.restapi.msproducts.repository.ProductRepository;

@Service
public class ProductService implements Iproduct {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(int id) {

		return productRepository.findById(id);
	}

	@Override
	public Product saveProduct(Product newProduct) {

		return productRepository.save(newProduct);
	}

	@Override
	public void deleteProduct(int id) {

		productRepository.deleteById(id);
	}

}
