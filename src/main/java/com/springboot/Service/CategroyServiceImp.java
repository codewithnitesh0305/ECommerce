package com.springboot.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.Entity.Category;
import com.springboot.Repository.CategoryRepository;

@Service
public class CategroyServiceImp implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category saveCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public boolean existCategory(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.existsByName(name);
	}

	@Override
	public boolean deleteCategroy(int id) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(id);
		if(!category.equals(null)) {
			categoryRepository.delete(category);
			return true;
		}	
		return false;
	}

	
}
