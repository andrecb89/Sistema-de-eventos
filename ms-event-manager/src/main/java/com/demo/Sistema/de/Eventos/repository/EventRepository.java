package com.demo.Sistema.de.Eventos.repository;

import com.demo.Sistema.de.Eventos.entities.Event;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {


    List<Event> findByDeletedFalse();

    Optional<Event> findByEventIdAndDeletedFalse(String id);

    List<Event> findByDeletedFalseOrderByEventNameAsc();


}
