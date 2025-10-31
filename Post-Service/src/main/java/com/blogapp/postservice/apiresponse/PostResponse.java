package com.blogapp.postservice.apiresponse;

import java.util.List;

import com.blogapp.postservice.entities.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

	private List<Post> content;
	private Integer pageNumber;
	private Integer pageSize;
	private Long totalElement;
	private Integer totalPages;
	private boolean lastPage;
	
}
