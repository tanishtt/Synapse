package com.tanishmohanta.synapse.notification_service.consumer;


import com.tanishmohanta.synapse.notification_service.entity.NotificationEntity;
import com.tanishmohanta.synapse.notification_service.service.NotificationService;
import com.tanishmohanta.synapse.post_service.event.PostCreated;
import com.tanishmohanta.synapse.post_service.event.PostLiked;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PostConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "post-created-topic")
    public void consumePostCreatedEvent(PostCreated postCreated) {
        log.info("Consuming post created event: {}", postCreated);
        String message=String.format("Your connection with id: %d has created a new post, that is %s",postCreated.getOwnerUserId(), postCreated.getContent());
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .userId(postCreated.getUserId())
                .message(message)
                .build();

        notificationService.addNotification(notificationEntity);
    }

    @KafkaListener(topics = "post-liked-topic")
    public void consumePostLikedEvent(PostLiked postLiked){
        log.info("Consuming post liked event: {}", postLiked);
        String message=String.format("User with id: %d has liked your post with id: %d",postLiked.getLikedByUserId(),postLiked.getPostId());

        NotificationEntity notificationEntity=NotificationEntity.builder()
                .userId(postLiked.getOwnerUserId())
                .message(message)
                .build();

        notificationService.addNotification(notificationEntity);
    }
}
