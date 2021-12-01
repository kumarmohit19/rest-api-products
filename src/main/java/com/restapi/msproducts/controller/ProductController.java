package com.restapi.msproducts.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restapi.msproducts.dto.ProductDTO;
import com.restapi.msproducts.exception.ProductNotFoundException;
import com.restapi.msproducts.mapper.ProductMapper;
import com.restapi.msproducts.model.Product;
import com.restapi.msproducts.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	// Get all products 
	// GET /api/products
	@GetMapping(value = "/products")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	// Get single product by ID GET /api/products/{id}
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") @Min(1) int id) {
		Product prd = productService.getProductById(id)
				.orElseThrow(() -> new ProductNotFoundException("No Product available with ID : " + id));

		return ResponseEntity.ok().body(prd);
	}

	// Create new product
	// POST /api/products
	@PostMapping(value="/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO inprod) {
    	Product prd= ProductMapper.DtoToEntity(inprod);
    	Product addedProd= productService.saveProduct(prd);
    	
    	URI location= ServletUriComponentsBuilder.fromCurrentRequest()
    						.path("/{id}")
    						.buildAndExpand(addedProd.getId())
    						.toUri();
    	return ResponseEntity.created(location).build();
    }

	// Update product details
	// PUT /api/products/{id}
	@PutMapping(value="/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") @Min(1) int id, @Valid @RequestBody ProductDTO inprod){
    	Product prd= productService.getProductById(id)
    							   .orElseThrow(() -> new ProductNotFoundException("No Product available with ID : " + id));
    	
    	Product newprd= ProductMapper.DtoToEntity(inprod);
    	newprd.setId(prd.getId());
    	productService.saveProduct(newprd);
    	
    	return ResponseEntity.ok().body(newprd);
	}
	
    // Delete product by ID 
	// DELETE /api/products/{id}
	@DeleteMapping(value="/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") @Min(1) int id) {
		Product prd = productService.getProductById(id)
				.orElseThrow(() -> new ProductNotFoundException("No Product available with ID : " + id));
		
		productService.deleteProduct(prd.getId());
		
		return ResponseEntity.ok().body("Product with ID : "+id+" deleted with success!");
	}

}
