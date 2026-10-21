package com.tanishmohanta.synapse.post_service.repository;


import com.tanishmohanta.synapse.post_service.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
   // @Query("SELECT CASE WHEN COUNT(pl) > 0 THEN true ELSE false END FROM PostLikeEntity pl WHERE pl.postId = :postId AND pl.userId = :userId")
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
