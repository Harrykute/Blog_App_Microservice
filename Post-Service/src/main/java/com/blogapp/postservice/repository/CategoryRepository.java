package com.blogapp.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.postservice.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

}