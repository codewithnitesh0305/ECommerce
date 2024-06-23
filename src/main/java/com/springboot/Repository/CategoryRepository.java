package com.springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	public boolean existsByName(String name);
}
