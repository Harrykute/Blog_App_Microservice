package com.blogapp.postservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.blogapp.postservice.apiresponse.ApiResponse;
import com.blogapp.postservice.apiresponse.PostResponse;
import com.blogapp.postservice.config.ApplicationConstant;
import com.blogapp.postservice.entities.Post;
import com.blogapp.postservice.service.PostLikeService;
import com.blogapp.postservice.service.PostService;


@RestController
@RequestMapping("/api/posts/")
public class PostRestController {
	
  @Autowired
  private PostService postService;
  
  @Autowired
  private PostLikeService postLikeService;
  
	@PostMapping("/{categoryId}/create")
	public ResponseEntity<Post> createPost(@RequestBody Post postDto,@PathVariable Integer categoryId){
		Long userId = postDto.getUserId(); 
		Post createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<Post>(createdPostDto,HttpStatus.CREATED); 
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<Post> updatePost(@RequestBody Post postDto,@PathVariable("postId") Integer postId){
		Post post = this.postService.updatePost(postDto, postId);
	    return new ResponseEntity<Post>(post,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<Post>> getPostsByUser(@PathVariable("userId") Long userId){
		List<Post> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<Post>> getPostByCategory(@PathVariable Integer categoryId){
		List<Post> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<Post> getSinglePost(@PathVariable("postId") Integer postId){
		Post post = this.postService.getSinglePost(postId);
		return new ResponseEntity<Post>(post,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Post>> getAllPost(@RequestParam(value="pageNumber",defaultValue="1",required = false) Integer pageNumber,@RequestParam(value="pageSize",defaultValue = "10",required = false)  Integer pageSize){
		List<Post> posts = this.postService.getPosts(pageNumber,pageSize);
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully",false),HttpStatus.OK);
	}
	
	@GetMapping("/postss")
	public ResponseEntity<PostResponse> getDetailedPosts(@RequestParam(value=ApplicationConstant.PAGE_NUMBER,defaultValue="1",required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = ApplicationConstant.PAGE_SIZE,required = false)  Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=ApplicationConstant.SORT_BY,required = false) String sortBy ,
			@RequestParam(value="sortDir",defaultValue=ApplicationConstant.SORT_DIR,required = false) String sortDir
			){
		PostResponse postResponse = this.postService.getDeatilAllpost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("posts/search/{keyword}")
	public ResponseEntity<List<Post>> searchPost(@PathVariable("keyword") String keyword){
		
		List<Post> posts = this.postService.searchPosts(keyword);
		
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
	}

    @PostMapping(value="/postlike/{postId}/user/{userId}")
    public ResponseEntity<String> likePost(@PathVariable("postId") Integer postId ,@PathVariable("userId") Long userId){
    	String like = this.postLikeService.postLike(postId, userId);
    	return new ResponseEntity<String>(like,HttpStatus.OK);
    }

}