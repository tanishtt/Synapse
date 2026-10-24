package com.tanishmohanta.synapse.connection_service.repository;

import com.tanishmohanta.synapse.connection_service.entity.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<PersonEntity, Long> {

    Optional<PersonEntity> findByUserId(Long userId);

    @Query("MATCH (p:Person)-[:CONNECTED_TO]-(b:Person) WHERE p.userId = $userId RETURN DISTINCT b")
    List<PersonEntity> getFirstDegreeConnection(@Param("userId") Long userId);

}
