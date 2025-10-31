package com.blogapp.postservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.postservice.apiresponse.ApiResponse;
import com.blogapp.postservice.entities.Category;
import com.blogapp.postservice.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<Category> createCategory(@RequestBody Category categoryDto){
		Category createdCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<Category>(createdCategory,HttpStatus.CREATED); 
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<Category> updateCategory(@RequestBody Category categoryDto,@PathVariable("categoryId") Integer cateId){
		Category updatedcategory = this.categoryService.updateCategory(categoryDto, cateId);
		return new ResponseEntity<Category>(updatedcategory,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully",false),HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getSingleCategory(@PathVariable("categoryId") Integer categoryId){
		Category category = this.categoryService.getSingleCategory(categoryId);
		return new ResponseEntity<Category>(category,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Category>> getCategories(){
		List<Category> categories = this.categoryService.getCategories();
		return new ResponseEntity<List<Category>>(categories,HttpStatus.OK);
	}

}