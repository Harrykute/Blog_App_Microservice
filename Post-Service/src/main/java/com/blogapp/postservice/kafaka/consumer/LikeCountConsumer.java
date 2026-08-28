package com.blogapp.postservice.kafaka.consumer;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blogapp.postservice.entities.Post;
import com.blogapp.postservice.kafaka.event.LikeCountChangedEvent;
import com.blogapp.postservice.repository.PostRepository;

@Configuration
public class LikeCountConsumer {
	
	@Autowired
	private PostRepository postRepo;
	
	@Bean
	public Consumer<LikeCountChangedEvent> likeCountConsumerMethod() {
	    return event -> {
	        Post post = postRepo.findById(event.getPostId())
	            .orElseThrow();
	        post.setLikeCount(event.getLikeCount());
	        postRepo.save(post);
	    };
	}
}
