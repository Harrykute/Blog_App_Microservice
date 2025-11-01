package com.blogapp.postservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.postservice.entities.Category;
import com.blogapp.postservice.entities.Post;
import com.blogapp.postservice.feign.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer>{

	public List<Post> findByUserId(Long userId);
	
	public List<Post> findByCategory(Category category);
	
	public List<Post> findByTitleContaining(String title);
}
