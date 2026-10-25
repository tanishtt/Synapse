package com.tanishmohanta.synapse.connection_service.service;

import com.tanishmohanta.synapse.connection_service.auth.AuthContextHolder;
import com.tanishmohanta.synapse.connection_service.entity.PersonEntity;
import com.tanishmohanta.synapse.connection_service.exception.BadRequestException;
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

        return firstDegreeConnection;
    }

    public void sendConnectionRequest(Long receiverId) {
        Long senderId= AuthContextHolder.getCurrentUserId();

        if (senderId.equals(receiverId)){
            throw new BadRequestException("Both sender and receiver are same.");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (alreadySentRequest) {
            throw new BadRequestException("Connection request already exists, cannot send again.");
        }

        boolean alreadyConnected= personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new BadRequestException("Users are already connected, cannot add connection request.");
        }

        log.info("Sending connection request for user with ID: {} from: {}", receiverId, senderId);
        personRepository.addConnectionRequest(senderId, receiverId);
        log.info("Connection request sent for user with ID: {} from: {}", receiverId, senderId);
    }

    public void acceptConnectionRequest(Long senderId) {

        Long receiverId= AuthContextHolder.getCurrentUserId();
        log.info("Accepting connection request for user with ID: {} from: {}", senderId, receiverId);

        if (senderId.equals(receiverId)){
            throw new BadRequestException("Both sender and receiver are same.");
        }

        boolean alreadyConnected= personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new BadRequestException("Users are already connected, cannot accept connection request.");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (!alreadySentRequest) {
            throw new BadRequestException("No connection request found, cannot accept.");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);
        log.info("Connection request for user with ID: {} has been accepted.", senderId);
    }


    public void rejectConnectionRequest(Long senderId) {

        Long receiverId= AuthContextHolder.getCurrentUserId();
        log.info("Rejecting connection request for user with ID: {} from: {}", senderId, receiverId);

        if (senderId.equals(receiverId)){
            throw new BadRequestException("Both sender and receiver are same.");
        }
        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (!alreadySentRequest) {
            throw new BadRequestException("No connection request found, cannot accept.");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);
        log.info("Connection request for user with ID: {} has been rejected.", senderId);
    }

}
