package com.demo.Sistema.de.Eventos.mocks;

import com.demo.Sistema.de.Eventos.controller.dto.EventCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.EventResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Event;

import java.time.LocalDateTime;

public class MockEvent {
   
    public Event mockEvent() {
        Event event = new Event();
        event.setEventId("676af34f0e58c90e00360090");
        event.setEventName("Show de musica");
        event.setDateTime(LocalDateTime.parse("2024-12-30T21:00:00"));
        event.setCep("01020-000");
        event.setLogradouro("Rua Tabatinguera");
        event.setBairro("Sé");
        event.setLocalidade("São Paulo");
        event.setUf("SP");
        event.setDeleted(false);
        return event;
    }

    public EventCreateDTO mockEventDTO() {
        EventCreateDTO event = new EventCreateDTO();
        event.setEventName("Show de musica");
        event.setDateTime(LocalDateTime.parse("2024-12-30T21:00:00"));
        event.setCep("01020-000");
        return event;
    }

    public EventResponseDTO mockEventResponseDTO() {
        EventResponseDTO event = new EventResponseDTO();
        event.setEventId("676af34f0e58c90e00360090");
        event.setEventName("Show de musica");
        event.setDateTime(LocalDateTime.parse("2024-12-30T21:00:00"));
        event.setCep("01020-000");
        event.setLogradouro("Rua Tabatinguera");
        event.setBairro("Sé");
        event.setLocalidade("São Paulo");
        event.setUf("SP");
        return event;
    }


}
