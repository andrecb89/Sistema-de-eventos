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

    Optional<Event> findByIdAndDeletedFalse(String id);

    List<Event> findByDeletedFalseOrderByEventNameAsc();



//    default void softDeleteWithId(ObjectId id) {
//        Event event = findById(id).orElseThrow(() -> new RuntimeException("Evento não encontrado"));
//        event.setDeleted(true);
//        save(event);
//    }
//
//    default void softDeleteWithCpf(String cpf) {
//        Event event = findByCpf(cpf).orElseThrow(() -> new RuntimeException("Evento não encontrado"));
//        event.setDeleted(true);
//        save(event);
//    }
//
//    Optional<Event> findByCpf(String cpf);
}
