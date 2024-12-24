package com.demo.Sistema.de.Eventos.service;

import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.repository.EventRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    public EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findAllSorted(){ return eventRepository.findAllByOrderByEventNameAsc();}

    public Event findById(String id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not found")
        );
    }

    public Event update(Event event) {
        Event existingEvent = findById(event.getId());

        existingEvent.setId(event.getId());
        existingEvent.setEventName(event.getEventName());
        existingEvent.setDateTime(event.getDateTime());
        existingEvent.setCep(event.getCep());
        existingEvent.setLogradouro(event.getLogradouro());
        existingEvent.setBairro(event.getBairro());
        existingEvent.setCidade(event.getCidade());
        existingEvent.setUf(event.getUf());
        existingEvent.setDeleted(event.isDeleted());


        return eventRepository.save(existingEvent);
    }

    public Event save(Event event){
        return eventRepository.save(event);
    }


    public void softDelete(String id) {
        Optional<Event> event = eventRepository.findById(id);
        eventRepository.delete(event.get());
    }
}
