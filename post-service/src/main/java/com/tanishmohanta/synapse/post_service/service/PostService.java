package com.tanishmohanta.synapse.post_service.service;

import com.tanishmohanta.synapse.post_service.auth.AuthContextHolder;
import com.tanishmohanta.synapse.post_service.client.ConnectionServiceClient;
import com.tanishmohanta.synapse.post_service.dto.PersonDTO;
import com.tanishmohanta.synapse.post_service.dto.PostCreateRequestDTO;
import com.tanishmohanta.synapse.post_service.dto.PostDTO;
import com.tanishmohanta.synapse.post_service.entity.PostEntity;
import com.tanishmohanta.synapse.post_service.exception.ResourceNotFoundException;
import com.tanishmohanta.synapse.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionServiceClient connectionServiceClient;

    public PostDTO createPost(PostCreateRequestDTO postCreateRequestDTO, Long userId) {
        log.info("Creating post for user with ID: {}", userId);
        PostEntity postEntity=modelMapper.map(postCreateRequestDTO, PostEntity.class);
        postEntity.setUserId(userId);
        PostEntity savedPost=postRepository.save(postEntity);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    public List<PostDTO> getAllPostsByUserId(Long userId) {
        log.info("Getting all posts by user with ID: {}", userId);
        List<PostEntity> postEntities=postRepository.findAllByUserId(userId);
        return postEntities.stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .toList();
    }

    public PostDTO getPostById(Long postId) {
        log.info("Getting post by ID: {}", postId);
        Long userId= AuthContextHolder.getCurrentUserId ();

        List<PersonDTO> personDTOList = connectionServiceClient.getFirstDegreeConnections(userId);
        log.info(personDTOList.toString());
        PostEntity postEntity=postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }
}
