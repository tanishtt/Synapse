package com.tanishmohanta.synapse.connection_service.entity;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class PersonEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private String name;

}
