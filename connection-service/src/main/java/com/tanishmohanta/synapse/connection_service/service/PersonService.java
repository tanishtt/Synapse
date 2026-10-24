package com.tanishmohanta.synapse.connection_service.service;


import com.tanishmohanta.synapse.connection_service.entity.PersonEntity;
import com.tanishmohanta.synapse.connection_service.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void createPerson(Long userId, String name) {
        PersonEntity personEntity = PersonEntity.builder()
                .userId(userId)
                .name(name)
                .build();
        personRepository.save(personEntity);
    }

}
