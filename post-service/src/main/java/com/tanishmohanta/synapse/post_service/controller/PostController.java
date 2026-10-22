package com.tanishmohanta.synapse.post_service.controller;

import com.tanishmohanta.synapse.post_service.dto.PostCreateRequestDTO;
import com.tanishmohanta.synapse.post_service.dto.PostDTO;
import com.tanishmohanta.synapse.post_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDTO postCreateRequestDTO,
                                              HttpServletRequest request) {
        PostDTO createdPost = postService.createPost(postCreateRequestDTO, 1L); // Replace with actual user ID from request context
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
        PostDTO postDTO=postService.getPostById(postId);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/user/{userId}/allPosts")
    public ResponseEntity<List<PostDTO>> getAllPostsByUserId(@PathVariable Long userId) {
        List<PostDTO> posts=postService.getAllPostsByUserId(userId);
        return ResponseEntity.ok(posts); // Replace with actual retrieved posts
    }
}
