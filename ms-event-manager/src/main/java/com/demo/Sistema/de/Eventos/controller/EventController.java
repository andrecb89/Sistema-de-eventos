package com.demo.Sistema.de.Eventos.controller;

import com.demo.Sistema.de.Eventos.controller.dto.EventCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.EventResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.service.EventService;
import jakarta.validation.Valid;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public class EventController {


    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> event = eventService.findAllEvents();
        return ResponseEntity.ok().body(event);
    }

    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<Event>> getAllEventsSorted() {
        List<Event> event = eventService.findAllEventsSorted();
        return ResponseEntity.ok().body(event);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        Event event = eventService.findEventById(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping("/create-event")
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody @Valid EventCreateDTO eventCreateDTO) throws InvocationTargetException, IllegalAccessException {
        var event = new Event();
        BeanUtils.copyProperties(event, eventCreateDTO);
        event = eventService.saveEvent(event);
        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        BeanUtils.copyProperties(eventResponseDTO, event);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponseDTO);
    }

    @PutMapping("/update-event/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody @Valid EventCreateDTO postCreateDto,
                                             @PathVariable String id) throws InvocationTargetException, IllegalAccessException {

        Event eventToUpdate = new Event();
        BeanUtils.copyProperties(eventToUpdate, postCreateDto);
        eventToUpdate.setEventId(id);
        Event updatedEvent = eventService.updateEvent(eventToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEvent);
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable String id) {
        eventService.softDeleteEvent(id);
        return ResponseEntity.ok("Deleted successfully");
    }






//    @DeleteMapping("/cancel-ticket/{id}")
//    public ResponseEntity<?> deleteEventById(@PathVariable ObjectId id) {
//        eventService.softDelete(id);
//        return ResponseEntity.ok("Deleted successfully");
//    }
//
//    @DeleteMapping("/cancel-ticket/{cpf}")
//    public ResponseEntity<?> deleteEventByCpf(@PathVariable String cpf) {
//        eventService.softDelete(cpf);
//        return ResponseEntity.ok("Deleted successfully");
//    }








}
