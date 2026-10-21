package com.tanishmohanta.synapse.connection_service.repository;

import com.tanishmohanta.synapse.connection_service.entity.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<PersonEntity, Long> {

    Optional<PersonEntity> findByUserId(Long userId);

    @Query("MATCH (p:PersonEntity)-[:CONNECTED_TO]-(b:PersonEntity) WHERE p.userId = $userId RETURN b")
    List<PersonEntity> getFirstDegreeConnection(Long userId);

}
