package com.blogapp.postservice.entities;



import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blogapp.postservice.feign.entities.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Post implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Title cannot be blank")
    @Size(min = 5, message = "Title should be at least 5 characters long")
	@Column(name="post_title",length=100,nullable=false)
	private String title;
	
	@NotBlank(message = "Content is required")
    @Size(min = 10, message = "Content must be at least 10 characters")
	@Column(length=10000)
	private String content;
	
	private String imageName;
	
	private Date addedDate; 
	
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	private Long userId;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<Comment> comments=new HashSet<>();
	
	private Integer likeCount=Integer.valueOf(0); 
	
	@OneToMany(mappedBy="post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<PostLike> like;
	
	public void incrementLikeCount() {
        this.likeCount++;
    }
	
}
