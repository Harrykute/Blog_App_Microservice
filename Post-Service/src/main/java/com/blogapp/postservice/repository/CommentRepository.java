package com.blogapp.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.postservice.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer>{

}