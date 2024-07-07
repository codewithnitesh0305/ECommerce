package com.springboot.Service;

import java.util.List;

import com.springboot.Entity.Category;

public interface CategoryService {

	public Category saveCategory(Category category);
	public boolean existCategory(String name);
	public List<Category> getAllCategory();
	public boolean deleteCategroy(int id);
	public Category getCategroyById(int id);
	public List<Category> getAllActiveCategory();
	
}
