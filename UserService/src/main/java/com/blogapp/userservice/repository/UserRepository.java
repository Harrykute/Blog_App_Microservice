package com.blogapp.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.userservice.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
