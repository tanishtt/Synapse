package com.tanishmohanta.synapse.post_service.service;

import com.tanishmohanta.synapse.post_service.entity.PostEntity;
import com.tanishmohanta.synapse.post_service.entity.PostLikeEntity;
import com.tanishmohanta.synapse.post_service.exception.BadRequestException;
import com.tanishmohanta.synapse.post_service.exception.ResourceNotFoundException;
import com.tanishmohanta.synapse.post_service.repository.PostLikeRepository;
import com.tanishmohanta.synapse.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public void likePost(Long postId) {
        Long userId = 1L; // Placeholder for the authenticated user's ID
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


        //TODO : SEND NOTIFICATION TO THE POST OWNER
    }
}
