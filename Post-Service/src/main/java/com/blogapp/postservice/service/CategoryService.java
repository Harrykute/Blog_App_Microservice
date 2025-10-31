package com.blogapp.postservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.postservice.entities.Category;
import com.blogapp.postservice.exception.ResourceNotFoundException;
import com.blogapp.postservice.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	public Category createCategory(Category category) {
		Category createdCategory = this.categoryRepo.save(category);
		return createdCategory ;
	}
	
	public Category updateCategory(Category categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(category);
		return updatedCategory;
	}
	
	public void deleteCategory(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
		this.categoryRepo.delete(category);
	}
	
	public Category getSingleCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
		return category;
	}
	
	public List<Category> getCategories() {
		List<Category> allCategories = this.categoryRepo.findAll();
		return allCategories;
	}
}
