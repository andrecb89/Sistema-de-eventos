package com.demo.Sistema.de.Eventos.controller;

import com.demo.Sistema.de.Eventos.controller.dto.EventCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.EventResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.exception.EntityNotFoundException;
import com.demo.Sistema.de.Eventos.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@RestController
public class EventController {


    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @Operation(
            summary = "Get all events",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all events",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))
                    )
            }

    )
    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getAllEvents() {

        List<Event> event = eventService.findAllEvents();
        return ResponseEntity.ok().body(event);
    }

    @Operation(
            summary = "Get all events sorted",
            description = "List of all events sorted",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all events sorted",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))
                    )
            }

    )
    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<Event>> getAllEventsSorted() {
        List<Event> event = eventService.findAllEventsSorted();
        return ResponseEntity.ok().body(event);
    }

    @Operation(
            summary = "Get event by ID",
            parameters = @Parameter(name = "id", description = "Event Id", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event details",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))
                    )
            }

    )
    @GetMapping("/get-event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        try {
            Event event = eventService.findEventById(id);
            return ResponseEntity.ok(event);
        } catch (EntityNotFoundException e) {
            log.error("Entity not found", e);
            throw new EntityNotFoundException(e.getMessage());
        }

    }
    @Operation(
            summary = "Create a new event",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Event created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))
                    )
            }

    )
    @PostMapping("/create-event")
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody @Valid EventCreateDTO eventCreateDTO) throws InvocationTargetException, IllegalAccessException {
        var event = new Event();
        BeanUtils.copyProperties(event, eventCreateDTO);
        event = eventService.saveEvent(event);
        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        BeanUtils.copyProperties(eventResponseDTO, event);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponseDTO);
    }

    @Operation(
            summary = "Update an event by ID",
            parameters = @Parameter(name = "id", description = "Event Id", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))
                    )
            }

    )
    @PutMapping("/update-event/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(@RequestBody @Valid EventCreateDTO eventCreateDto,
                                             @PathVariable String id) throws InvocationTargetException, IllegalAccessException {

        Event eventToUpdate = new Event();
        BeanUtils.copyProperties(eventToUpdate, eventCreateDto);
        eventToUpdate.setEventId(id);
        Event updatedEvent = eventService.updateEvent(eventToUpdate);
        EventResponseDTO eventResponseDTO = new EventResponseDTO();
        BeanUtils.copyProperties(eventResponseDTO, updatedEvent);
        return ResponseEntity.status(HttpStatus.OK).body(eventResponseDTO);

    }

    @Operation(
            summary = "Delete an event by ID",
            parameters = @Parameter(name = "id", description = "Event Id", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO.class))
                    )
            }

    )

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable String id) {
        eventService.softDeleteEvent(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
