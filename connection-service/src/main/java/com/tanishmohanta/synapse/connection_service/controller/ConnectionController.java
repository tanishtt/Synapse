package com.tanishmohanta.synapse.connection_service.controller;

import com.tanishmohanta.synapse.connection_service.entity.PersonEntity;
import com.tanishmohanta.synapse.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<PersonEntity>> getFirstDegreeConnections(@PathVariable Long userId) {
        log.debug("getFirstDegreeConnections({})", userId);
        List<PersonEntity> firstDegreeConnections = connectionService.getFirstDegreeConnections(userId);
        return ResponseEntity.ok(firstDegreeConnections);
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Void> sendConnectionRequest(@PathVariable Long userId) {
        log.info("sendConnectionRequest({})", userId);
        connectionService.sendConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Void> acceptConnectionRequest(@PathVariable Long userId) {
        log.info("acceptConnectionRequest({})", userId);
        connectionService.acceptConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Void> rejectConnectionRequest(@PathVariable Long userId) {
        log.info("rejectConnectionRequest({})", userId);
        connectionService.rejectConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }
}
