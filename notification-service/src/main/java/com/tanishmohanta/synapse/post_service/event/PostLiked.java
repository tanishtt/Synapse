package com.tanishmohanta.synapse.post_service.event;


import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLiked {
    private Long ownerUserId;
    private Long postId;
    private Long likedByUserId;
}
