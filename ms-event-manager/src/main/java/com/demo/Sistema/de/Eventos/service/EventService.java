package com.demo.Sistema.de.Eventos.service;

import com.demo.Sistema.de.Eventos.client.ViacepClient;
import com.demo.Sistema.de.Eventos.controller.dto.CepDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class EventService {

    public EventRepository eventRepository;
    public ViacepClient viacepClient;

    public EventService(EventRepository eventRepository, ViacepClient viacepClient) {
        this.eventRepository = eventRepository;
        this.viacepClient = viacepClient;
    }

    public List<Event> findAllEvents() {
        return eventRepository.findByDeletedFalse();
    }


    public List<Event> findAllEventsSorted(){ return eventRepository.findByDeletedFalseOrderByEventNameAsc();}

    public Event findEventById(String id) {
        return eventRepository.findByEventIdAndDeletedFalse(id).orElseThrow(
                () -> {
                    log.error("Event not found with id " + id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id " + id);
                });

    }

    public Event updateEvent(Event event) {
        Event existingEvent = findEventById(event.getEventId());

        existingEvent.setEventId(event.getEventId());
        existingEvent.setEventName(event.getEventName());
        existingEvent.setDateTime(event.getDateTime());
        existingEvent.setCep(event.getCep());
        CepDTO infoAdress = viacepClient.getInfoCep(event.getCep());
        existingEvent.setLogradouro(infoAdress.getLogradouro());
        existingEvent.setBairro(infoAdress.getBairro());
        existingEvent.setLocalidade(infoAdress.getLocalidade());
        existingEvent.setUf(infoAdress.getUf());
        existingEvent.setDeleted(event.isDeleted());


        return eventRepository.save(existingEvent);
    }

    public Event saveEvent(Event event){

        CepDTO infoAdress = viacepClient.getInfoCep(event.getCep());
        event.setLogradouro(infoAdress.getLogradouro());
        event.setBairro(infoAdress.getBairro());
        event.setLocalidade(infoAdress.getLocalidade());
        event.setUf(infoAdress.getUf());
        return eventRepository.save(event);
    }

    public void softDeleteEvent(String id) {
        Event event = eventRepository.findByEventIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("Entity not found");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id " + id);
                        }
                );

                event.setDeleted(true);
        eventRepository.save(event);
    }
}
