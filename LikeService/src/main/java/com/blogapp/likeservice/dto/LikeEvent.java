package com.blogapp.likeservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Event consumed from Kafka
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeEvent implements Serializable {

    private Integer postId;
    private Long userId;
    private String action;
    private LocalDateTime timestamp;

    // getters & setters
}
