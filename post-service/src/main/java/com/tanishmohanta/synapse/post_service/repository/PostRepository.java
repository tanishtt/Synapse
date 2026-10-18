package com.tanishmohanta.synapse.post_service.repository;

import com.tanishmohanta.synapse.post_service.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p WHERE p.userId = :userId")
    List<PostEntity> findAllByUserId(Long userId);
}
