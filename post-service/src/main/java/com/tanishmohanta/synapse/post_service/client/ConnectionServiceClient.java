package com.tanishmohanta.synapse.post_service.client;

import com.tanishmohanta.synapse.post_service.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connection-service", path = "/connection")
public interface ConnectionServiceClient {

    @GetMapping("/core/{userId}/first-degree")
    List<PersonDTO> getFirstDegreeConnections(@PathVariable Long userId);
}
