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
}
