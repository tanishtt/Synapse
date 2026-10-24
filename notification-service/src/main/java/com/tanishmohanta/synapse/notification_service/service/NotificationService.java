package com.tanishmohanta.synapse.notification_service.service;


import com.tanishmohanta.synapse.notification_service.entity.NotificationEntity;
import com.tanishmohanta.synapse.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void addNotification(NotificationEntity notificationEntity){
        log.info("adding notification to db");
        notificationRepository.save(notificationEntity);
        //send mailer to send notification
    }
}
