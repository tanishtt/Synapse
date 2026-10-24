package com.tanishmohanta.synapse.connection_service.service;

import com.tanishmohanta.synapse.connection_service.entity.PersonEntity;
import com.tanishmohanta.synapse.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {

    private final PersonRepository personRepository;

    public List<PersonEntity> getFirstDegreeConnections(Long userId) {
        log.info("Getting first degree connections for user with ID: {}", userId);
        List<PersonEntity> firstDegreeConnection = personRepository.getFirstDegreeConnection(userId);
//        for (PersonEntity personEntity: firstDegreeConnection)
//        {log.info("personEntity : "+personEntity.toString());}
        return firstDegreeConnection;
    }
}
