package com.springboot.Service;

import java.util.List;

import com.springboot.Entity.Product;

public interface ProductService {

	public Product saveProduct(Product product);
	public List<Product> getAllProduct();
	public Product getProductById(int id);
	public boolean deleteProductById(int id);
	public Product updateProduct(Product product);
	
}
