package com.tanishmohanta.synapse.post_service.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PostDTO {

    public Long id;

    private String content;

    private Long userId;

    private String imageUrl;

    private Instant createdAt;
}
