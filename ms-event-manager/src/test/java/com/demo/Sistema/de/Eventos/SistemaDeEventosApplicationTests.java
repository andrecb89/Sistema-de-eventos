package com.demo.Sistema.de.Eventos;


import com.demo.Sistema.de.Eventos.client.ViacepClient;
import com.demo.Sistema.de.Eventos.controller.dto.CepDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.mocks.MockEvent;
import com.demo.Sistema.de.Eventos.repository.EventRepository;
import com.demo.Sistema.de.Eventos.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SistemaDeEventosApplicationTests {

	@Mock
	private EventRepository eventRepository;

	@Mock
	private ViacepClient viacepClient;

	@InjectMocks
	private EventService eventService;

   	MockEvent mockEvent;

	@BeforeEach
	public void setUp() {
		mockEvent = new MockEvent();
	}

	@Test
	public void SaveEvent_WithValidData_shouldEqualExpectedResult() {
        when(eventRepository.save(any(Event.class))).thenReturn(new Event("Show de musica",
                LocalDateTime.parse("2024-12-30T21:00:00"), "01020-000", "Rua Tabatinguera",
                "Sé", "São Paulo", "SP"));
        when(viacepClient.getInfoCep("01020-000")).thenReturn(new CepDTO("Rua Tabatinguera",
				"Sé","São Paulo", "SP"));

        Event event = eventService.saveEvent(mockEvent.mockEvent());

        assertEquals("Show de musica", event.getEventName());
        assertEquals(LocalDateTime.parse("2024-12-30T21:00:00"), event.getDateTime());
        assertEquals("01020-000", event.getCep());
        assertEquals("Rua Tabatinguera", event.getLogradouro());
        assertEquals("Sé", event.getBairro());
        assertEquals("São Paulo", event.getLocalidade());
        assertEquals("SP", event.getUf());
        assertEquals(false, event.isDeleted());
    }

	@Test
	public void testFindAll_TurnIntoSingleton_ExpectListSizeOfOne() {
		List<Event> events = Collections.singletonList(mockEvent.mockEvent());
		when(eventRepository.findByDeletedFalse()).thenReturn(events);

		List<Event> result = eventService.findAllEvents();

		assertEquals(1, result.size());
	}

	@Test
	public void testFindAll_WithNullContent_ReturnsEmptyList() {
		when(eventRepository.findByDeletedFalse()).thenReturn(Collections.emptyList());

		List<Event> result = eventService.findAllEvents();

		assertTrue(result.isEmpty());

		verify(eventRepository, times(1)).findByDeletedFalse();
	}

	@Test
	public void UpdateEvent_WithValidData_shouldEqualExpectedResult() {
		Event returnEvent = mockEvent.mockEvent();
		when(eventRepository.findByEventIdAndDeletedFalse(returnEvent.getEventId())).thenReturn(Optional.of(new Event("Show de musica",
                LocalDateTime.parse("2024-12-30T21:00:00"), "01020-000", "Rua Tabatinguera",
                "Sé", "São Paulo", "SP")));
		when(eventRepository.save(any(Event.class))).thenReturn(new Event("Show de musica",
				LocalDateTime.parse("2024-12-30T21:00:00"), "01020-000", "Rua Tabatinguera",
				"Sé", "São Paulo", "SP"));
		when(viacepClient.getInfoCep("01020-000")).thenReturn(new CepDTO("Rua Tabatinguera",
				"Sé","São Paulo", "SP"));

		Event event = eventService.updateEvent(returnEvent);

		assertEquals("Show de musica", event.getEventName());
		assertEquals(LocalDateTime.parse("2024-12-30T21:00:00"), event.getDateTime());
		assertEquals("01020-000", event.getCep());
		assertEquals("Rua Tabatinguera", event.getLogradouro());
		assertEquals("Sé", event.getBairro());
		assertEquals("São Paulo", event.getLocalidade());
		assertEquals("SP", event.getUf());
		assertEquals(false, event.isDeleted());
	}


	@Test
	public void deleteEvent_WithInvalidId_ThrowsException() {
	assertThatThrownBy(() -> eventService.softDeleteEvent("00000000000000"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Event not found with id 00000000000000");



	}
}