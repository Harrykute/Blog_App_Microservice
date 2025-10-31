package com.blogapp.postservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.postservice.entities.Comment;
import com.blogapp.postservice.entities.Post;
import com.blogapp.postservice.exception.ResourceNotFoundException;
import com.blogapp.postservice.repository.CommentRepository;
import com.blogapp.postservice.repository.PostRepository;

@Service
public class CommentService {

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	public Comment createComment(Comment comment, Integer postId) {
		
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        comment.setPost(post);
        Comment createdComment = this.commentRepo.save(comment);
		return createdComment;
	}

	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
		this.commentRepo.delete(comment);
	}

}