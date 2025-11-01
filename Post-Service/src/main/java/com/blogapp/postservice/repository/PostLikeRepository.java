package com.blogapp.postservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.postservice.entities.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer>  {
	
	 Optional<PostLike> findByPostIdAndUserId(Integer postId, Long userId);
	 
}