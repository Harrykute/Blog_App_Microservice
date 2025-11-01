package com.blogapp.postservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.postservice.apiresponse.PostResponse;
import com.blogapp.postservice.entities.Category;
import com.blogapp.postservice.entities.Post;
import com.blogapp.postservice.exception.ResourceNotFoundException;
import com.blogapp.postservice.feign.entities.User;
import com.blogapp.postservice.feign.service.UserService;
import com.blogapp.postservice.repository.CategoryRepository;
import com.blogapp.postservice.repository.PostRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired(required=false)
	private UserService userService;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
    @CircuitBreaker(name = "userServiceBreaker", fallbackMethod = "userFallback")
    @Retry(name = "userServiceRetry")
	public Post createPost(Post postPayload, Long userId , Integer categoryId) {
		  User user = this.userService.getUserById(userId);
		 Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId", categoryId));
		    Post post = new Post();
		    post.setTitle(postPayload.getTitle());
	        post.setImageName("default.png");
	        post.setAddedDate(new Date());
	        post.setContent(postPayload.getContent());
	        post.setUserId(user.getId());
	        post.setCategory(category);
	        Post newPost = this.postRepo.save(post);
	        return newPost;
	}

	public Post updatePost(Post postPayload,Integer postId) {
		
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        post.setContent(postPayload.getContent());
        post.setTitle(postPayload.getTitle());
        
        if(postPayload.getImageName()!= null) {
        	post.setImageName(postPayload.getImageName());
        }
        Post updatePost = this.postRepo.save(post);
		return updatePost;
	}

	public void deletePost(Integer postId) {
		
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        this.postRepo.delete(post);
	}

	public Post getSinglePost(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
		return post;
	}

	public List<Post> getPosts(Integer pageNumber,Integer pageSize) {
		Pageable p = PageRequest.of(pageNumber-1,pageSize);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPost = pagePost.getContent();
		return allPost;
	}
	
	
	public List<Post> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		return posts;
	}


	public List<Post> getPostsByUser(Long userId) {
		// User user = this.userService.getUserById(userId);
		 List<Post> posts = this.postRepo.findByUserId(userId);
		return posts;
	}

	public List<Post> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		return posts;
	}
	


	//we are creating customized getall post method for detailed response
     public PostResponse getDeatilAllpost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
    	    Sort sort =null;
    	    if(sortDir.equalsIgnoreCase("asc")) {
    	    	sort = sort.by(sortBy).ascending();
    	    }else {
    	    	sort =sort.by(sortBy).descending();
    	    }
    		Pageable p = PageRequest.of(pageNumber-1,pageSize,sort);
    		
    		Page<Post> pagePost = this.postRepo.findAll(p);
    		List<Post> allPost = pagePost.getContent();
    		
            PostResponse postResponse = new PostResponse();
            postResponse.setContent(allPost);
            postResponse.setPageNumber(pagePost.getNumber());
            postResponse.setPageSize(pagePost.getSize());
            postResponse.setTotalElement(pagePost.getTotalElements());
            postResponse.setTotalPages(pagePost.getTotalPages());
            postResponse.setLastPage(pagePost.isLast());
    		return  postResponse;
     }
     
     
     // ✅ Fallback Method (must match method signature + Throwable) also 
     // parameters of fallback method should same with service method
     public Post userFallback(Post postRequest, Long userId , Integer categoryId, Throwable throwable) {
         System.out.println("Fallback triggered due to: " + throwable.getMessage());
         postRequest.setUserId(postRequest.getUserId());
		 Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId", categoryId));
		 postRequest.setCategory(category);
         return postRepo.save(postRequest);
     }
	
}
