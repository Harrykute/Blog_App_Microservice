	package com.blogapp.postservice.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
	@Table(name="categories")
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public class Category implements Serializable{
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer categoryId;
		
		@NotBlank(message = "categoryTitle is required")
	    @Size(min = 3, message = "categoryTitle must be at least 3 characters")
		@Column(name="title",length=100,nullable=false)
		private String categoryTitle;
		
		@Column(name="description")
		private String categoryDescription;
		
	}