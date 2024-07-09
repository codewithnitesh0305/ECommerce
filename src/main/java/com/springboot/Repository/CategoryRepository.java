package com.springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.Entity.Category;
import com.springboot.Entity.Product;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Integer>{

	public boolean existsByName(String name);
	public Category findById(int id);
	public List<Category> findByIsactiveTrue();
	
}
