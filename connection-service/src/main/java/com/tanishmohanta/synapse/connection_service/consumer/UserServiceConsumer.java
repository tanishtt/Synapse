package com.tanishmohanta.synapse.connection_service.consumer;

import com.tanishmohanta.synapse.user_service.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tanishmohanta.synapse.connection_service.service.PersonService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceConsumer {

    private final PersonService personService;

    @KafkaListener(topics = "user-created-topic")
    public void handlePersonCreated(UserCreatedEvent userCreatedEvent){
        log.info("Received user-created event {}", userCreatedEvent);
        personService.createPerson(userCreatedEvent.getUserId(), userCreatedEvent.getName());
    }
}
