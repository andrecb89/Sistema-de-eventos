package com.demo.Sistema.de.Eventos.controller;

import com.demo.Sistema.de.Eventos.controller.dto.EventCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.EventResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.mocks.MockEvent;
import com.demo.Sistema.de.Eventos.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ControllerTests {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    MockEvent mockEvent;

    @BeforeEach
    public void setUp() {
        mockEvent = new MockEvent();
    }

    @Test
    public void GetAllEvents_TurnIntoSingleton_ExpectListSizeOfOne() throws InvocationTargetException, IllegalAccessException {
        List<Event> events = Collections.singletonList(mockEvent.mockEvent());
        when(eventService.findAllEvents()).thenReturn(events);

        List<EventResponseDTO> result = eventController.getAllEvents().getBody();

        assertEquals(1, result.size());
    }

    @Test
    public void GetAllEventsSorted_TurnIntoSingleton_ExpectListSizeOfOne() throws InvocationTargetException, IllegalAccessException {
        List<Event> events = Collections.singletonList(mockEvent.mockEvent());
        when(eventService.findAllEventsSorted()).thenReturn(events);

        List<EventResponseDTO> result = eventController.getAllEventsSorted().getBody();

        assertEquals(1, result.size());
    }

    @Test
    public void GetEventById_WithValidId_ExpectEventFound() throws InvocationTargetException, IllegalAccessException {
        Event event = mockEvent.mockEvent();
        EventResponseDTO dto = mockEvent.mockEventResponseDTO();
        when(eventService.findEventById(event.getEventId())).thenReturn(event);

        ResponseEntity<EventResponseDTO> result = eventController.getEventById(event.getEventId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dto.getEventName(), result.getBody().getEventName());
        assertEquals(dto.getBairro(), result.getBody().getBairro());
    }


    @Test
    public void CreateEvent_WithValidData_ExpectEventCreated() throws InvocationTargetException, IllegalAccessException {
        EventResponseDTO eventResponseDTO = new MockEvent().mockEventResponseDTO();
        Event event = mockEvent.mockEvent();
        EventCreateDTO eventCreateDTO = new MockEvent().mockEventDTO();
        when(eventService.saveEvent(any())).thenReturn(event);

        ResponseEntity<EventResponseDTO> eventResponse = eventController.createEvent(eventCreateDTO);

        assertEquals(HttpStatus.CREATED, eventResponse.getStatusCode());
        assertEquals("Show de musica", event.getEventName());
        assertEquals(LocalDateTime.parse("2024-12-30T21:00:00"), event.getDateTime());
        assertEquals("01020-000", event.getCep());
        assertEquals("Rua Tabatinguera", event.getLogradouro());
        assertEquals("Sé", event.getBairro());
        assertEquals("São Paulo", event.getLocalidade());
        assertEquals("SP", event.getUf());
    }

    @Test
    public void UpdateEvent_WithValidData_shouldEqualExpectedResult() throws InvocationTargetException, IllegalAccessException {
        Event returnEvent = mockEvent.mockEvent();
        EventCreateDTO returnEventDTO = mockEvent.mockEventDTO();
        when(eventService.updateEvent(any(Event.class))).thenReturn(new Event("Show de musica",
                LocalDateTime.parse("2024-12-30T21:00:00"), "01020-000", "Rua Tabatinguera",
                "Sé", "São Paulo", "SP"));

        ResponseEntity<EventResponseDTO> event = eventController.updateEvent(returnEventDTO, "676af34f0e58c90e00360090");

        assertEquals("Show de musica", event.getBody().getEventName());
        assertEquals(LocalDateTime.parse("2024-12-30T21:00:00"), event.getBody().getDateTime());
        assertEquals("01020-000", event.getBody().getCep());
        assertEquals("Rua Tabatinguera", event.getBody().getLogradouro());
        assertEquals("Sé", event.getBody().getBairro());
        assertEquals("São Paulo", event.getBody().getLocalidade());
        assertEquals("SP", event.getBody().getUf());
    }


    @Test
    public void DeleteEvent_WithValidId_ReturnsOkStatus() {
        doNothing().when(eventService).softDeleteEvent("00000000000000");

        ResponseEntity<String> response = eventController.deleteEventById("00000000000000");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Deleted successfully");


    }

}
