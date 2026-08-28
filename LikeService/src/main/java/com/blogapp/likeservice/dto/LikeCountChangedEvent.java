package com.blogapp.likeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeCountChangedEvent {

    private Integer postId;
    private Integer likeCount;
}
