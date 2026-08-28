package com.blogapp.postservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.blogapp.postservice.entities.Post;
import com.blogapp.postservice.entities.PostLike;
import com.blogapp.postservice.exception.ResourceNotFoundException;
import com.blogapp.postservice.feign.entities.User;
import com.blogapp.postservice.feign.service.UserService;
import com.blogapp.postservice.kafaka.event.LikeEvent;
import com.blogapp.postservice.repository.PostLikeRepository;
import com.blogapp.postservice.repository.PostRepository;

@Service
public class PostLikeService {

	@Autowired
	private PostRepository postRepo;
	
	@Autowired(required=false)
	private UserService userService;
	
	@Autowired
	private PostLikeRepository postLikeRepo;
	
	@Autowired
	private StreamBridge streamBridge;

	@CachePut(value = "posts", key = "#post.id")
	public String postLike(Integer postId, Long userId) {
		
		 User user = this.userService.getUserById(userId);

	          Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
			  Optional<PostLike> existingLike = postLikeRepo.findByPostIdAndUserId(postId,userId); 
			  if (existingLike.isPresent()) 
			  { 
				  return "User has already liked this post"; 
			  }
			  
			  // this is code for saving the like in db 
	          PostLike postLike = new PostLike();
	          postLike.setPost(post);
	          postLike.setUserId(user.getId());
	          postLikeRepo.save(postLike);
			  
		        LikeEvent event = new LikeEvent();
		        event.setPostId(postId);
		        event.setUserId(userId);
		        event.setAction("LIKE");
		        event.setTimestamp(LocalDateTime.now());

		        // Send event to Kafka topic
		        streamBridge.send("like-out-0", event);
	          post.incrementLikeCount();
	          postRepo.save(post);
	          
	          
		return "like Saved Succesfully";
	}

}