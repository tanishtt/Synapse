package com.tanishmohanta.synapse.post_service.service;

import com.tanishmohanta.synapse.post_service.auth.AuthContextHolder;
import com.tanishmohanta.synapse.post_service.client.ConnectionServiceClient;
import com.tanishmohanta.synapse.post_service.client.UploaderServiceClient;
import com.tanishmohanta.synapse.post_service.dto.PersonDTO;
import com.tanishmohanta.synapse.post_service.dto.PostCreateRequestDTO;
import com.tanishmohanta.synapse.post_service.dto.PostDTO;
import com.tanishmohanta.synapse.post_service.entity.PostEntity;
import com.tanishmohanta.synapse.post_service.event.PostCreated;
import com.tanishmohanta.synapse.post_service.exception.ResourceNotFoundException;
import com.tanishmohanta.synapse.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionServiceClient connectionServiceClient;
    private final KafkaTemplate<String, PostCreated> postCreatedKafkaTemplate;
    private final UploaderServiceClient uploaderServiceClient;

    public PostDTO createPost(PostCreateRequestDTO postCreateRequestDTO, MultipartFile file) {
        Long userId=AuthContextHolder.getCurrentUserId();
        log.info("Creating post for user with ID: {}", userId);

        ResponseEntity<String> imageUrl = uploaderServiceClient.uploadFile(file);
        PostEntity postEntity=modelMapper.map(postCreateRequestDTO, PostEntity.class);
        postEntity.setUserId(userId);
        postEntity.setImageUrl(imageUrl.getBody());
        PostEntity savedPost=postRepository.save(postEntity);

        List<PersonDTO> personDTOList = connectionServiceClient.getFirstDegreeConnections(userId);
        log.info("personDTOList : "+personDTOList.toString());

        for (PersonDTO personDTO: personDTOList){
            PostCreated postCreated=PostCreated.builder()
                    .postId(postEntity.getId())
                    .userId(personDTO.getUserId())
                    .content(postEntity.getContent())
                    .ownerUserId(userId)
                    .build();
            postCreatedKafkaTemplate.send("post-created-topic", postCreated);
            log.info("Notification dispatched successfully....");
        }
        log.info("Post created successfully for user with ID: {}", userId);

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
        //Long userId= AuthContextHolder.getCurrentUserId ();

        PostEntity postEntity=postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }
}
