package com.blogapp.likeservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapp.likeservice.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostIdAndUserId(Integer postId, Long userId);
    long countByPostId(Integer postId);
    void deleteByPostIdAndUserId(Integer postId, Long userId);
}