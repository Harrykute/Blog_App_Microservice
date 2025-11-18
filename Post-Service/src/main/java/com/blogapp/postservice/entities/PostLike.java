package com.blogapp.postservice.entities;

import java.io.Serializable;

import com.blogapp.postservice.feign.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PostLike implements Serializable{

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name="post_id",nullable = false)
	@JsonIgnore
	private Post post;
}
