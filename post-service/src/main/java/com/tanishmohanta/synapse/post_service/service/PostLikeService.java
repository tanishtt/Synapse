package com.tanishmohanta.synapse.post_service.service;

import com.tanishmohanta.synapse.post_service.auth.AuthContextHolder;
import com.tanishmohanta.synapse.post_service.entity.PostEntity;
import com.tanishmohanta.synapse.post_service.entity.PostLikeEntity;
import com.tanishmohanta.synapse.post_service.event.PostLiked;
import com.tanishmohanta.synapse.post_service.exception.BadRequestException;
import com.tanishmohanta.synapse.post_service.exception.ResourceNotFoundException;
import com.tanishmohanta.synapse.post_service.repository.PostLikeRepository;
import com.tanishmohanta.synapse.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, PostLiked> postLikedKafkaTemplate;

    @Transactional
    public void likePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("User with ID {} liked post with ID {}.", userId, postId);
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        boolean hasAlreadyLiked = postLikeRepository.existsByPostIdAndUserId(postId, userId);
        if (hasAlreadyLiked) {
            throw new BadRequestException("User has already liked this post.");
        }

        PostLikeEntity postLikeEntity = new PostLikeEntity();
        postLikeEntity.setPostId(postId);
        postLikeEntity.setUserId(userId);
        postLikeRepository.save(postLikeEntity);

        PostLiked postLiked=PostLiked.builder()
                .ownerUserId(postEntity.getUserId())
                .postId(postId)
                .likedByUserId(userId)
                .build();
        postLikedKafkaTemplate.send("post-liked-topic", postLiked);
        log.info("Post liked successfully for user with ID: {}", userId);
    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("User with ID {} unliked post with ID {}.", userId, postId);
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        boolean hasAlreadyLiked = postLikeRepository.existsByPostIdAndUserId(postId, userId);
        if (!hasAlreadyLiked) {
            throw new BadRequestException("User has not liked this post yet.");
        }

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
