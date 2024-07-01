package com.springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.Entity.Product;

public interface ProductRepositoy extends JpaRepository<Product, Integer>{

	public Product findById(int id);
}
