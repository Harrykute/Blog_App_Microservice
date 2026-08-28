package com.blogapp.postservice.kafaka.event;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Event sent from Post-Service to Kafka
 */
@Getter
@Setter
public class LikeEvent implements Serializable {

    private Integer postId;
    private Long userId;
    private String action; // LIKE / UNLIKE
    private LocalDateTime timestamp;

}
