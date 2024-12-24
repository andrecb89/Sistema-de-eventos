package com.demo.Sistema.de.Eventos.service;

import com.demo.Sistema.de.Eventos.client.ViacepClient;
import com.demo.Sistema.de.Eventos.controller.dto.CepDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return eventRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new RuntimeException("Evento não encontrado")
        );
    }

    public Event updateEvent(Event event) {
        Event existingEvent = findEventById(event.getId());

        existingEvent.setId(event.getId());
        existingEvent.setEventName(event.getEventName());
        existingEvent.setDateTime(event.getDateTime());
        existingEvent.setCep(event.getCep());
        existingEvent.setLogradouro(event.getLogradouro());
        existingEvent.setBairro(event.getBairro());
        existingEvent.setLocalidade(event.getLocalidade());
        existingEvent.setUf(event.getUf());
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


//    public void softDelete(String id) {
//        Optional<Event> event = eventRepository.findById(id);
//        eventRepository.delete(event.get());
//    }

    public void softDeleteEvent(String id) {
        Event event = eventRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        event.setDeleted(true);
        eventRepository.save(event);
    }
}
