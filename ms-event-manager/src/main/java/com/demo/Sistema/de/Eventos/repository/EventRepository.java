package com.demo.Sistema.de.Eventos.repository;

import com.demo.Sistema.de.Eventos.entities.Event;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, ObjectId> {
}
