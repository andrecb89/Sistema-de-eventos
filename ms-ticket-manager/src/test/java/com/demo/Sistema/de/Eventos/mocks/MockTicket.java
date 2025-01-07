package com.demo.Sistema.de.Eventos.mocks;

import com.demo.Sistema.de.Eventos.controller.dto.TicketCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.TicketResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.entities.Ticket;

import java.time.LocalDateTime;

public class MockTicket {
   
    public Ticket mockTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("6772975c6eaae32cbfdc7c66");
        ticket.setCustomerName("João");
        ticket.setCpf("01234567890");
        ticket.setEvent(mockEvent());
        ticket.setEventId("676aa088899e6c4a7b713a4a");
        ticket.setCustomerMail("joao@gmail.com");
        ticket.setStatus("Concluído");
        ticket.setBRLamount(50.00);
        return ticket;
    }

    public TicketCreateDTO mockTicketDTO() {
        TicketCreateDTO ticket = new TicketCreateDTO();
        ticket.setCustomerName("João");
        ticket.setCpf("01234567890");
        ticket.setEventId("676aa088899e6c4a7b713a4a");
        ticket.setCustomerMail("joao@gmail.com");
        ticket.setBRLamount(50.00);
        return ticket;
    }

    public TicketResponseDTO mockTicketResponseDTO() {
        TicketResponseDTO ticket = new TicketResponseDTO();
        ticket.setTicketId("676af34f0e58c90e00360090");
        ticket.setCustomerName("João");
        ticket.setCpf("01234567890");
        ticket.setCustomerMail("joao@gmail.com");
        ticket.setBRLamount(50.00);
        return ticket;
    }

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
        return event;
    }

}
