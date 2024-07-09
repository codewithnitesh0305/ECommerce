package com.springboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.Entity.Product;

public interface ProductRepositoy extends JpaRepository<Product, Integer>{

	public Product findById(int id);
	public List<Product> findByIsactiveTrue();
	public List<Product> findByCategory(String Category);
}
