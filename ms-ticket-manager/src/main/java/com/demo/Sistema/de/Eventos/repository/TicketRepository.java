package com.demo.Sistema.de.Eventos.repository;
import com.demo.Sistema.de.Eventos.entities.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {


    Optional<Ticket> findByTicketIdAndDeletedFalse(String ticketId);

    List<Ticket> findByEventIdAndDeletedFalse(String eventId);




}
