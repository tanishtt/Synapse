package com.tanishmohanta.synapse.connection_service.controller;

import com.tanishmohanta.synapse.connection_service.entity.PersonEntity;
import com.tanishmohanta.synapse.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<PersonEntity>> getFirstDegreeConnections(@PathVariable Long userId) {
        List<PersonEntity> firstDegreeConnections = connectionService.getFirstDegreeConnections(userId);
        return ResponseEntity.ok(firstDegreeConnections);
    }
}
