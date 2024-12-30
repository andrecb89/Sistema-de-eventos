package com.demo.Sistema.de.Eventos.mocks;

import com.demo.Sistema.de.Eventos.controller.dto.TicketCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.TicketResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Ticket;

public class MockTicket {
   
    public Ticket mockTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("6772975c6eaae32cbfdc7c66");
        ticket.setCustomerName("João");
        ticket.setCpf("01234567890");
        ticket.setEventId("676aa088899e6c4a7b713a4a");
        ticket.setCustomerMail("joao@gmail.com");
        ticket.setStatus("Concluído");
        ticket.setBRLamount(50.00);
        return ticket;
    }

    public TicketCreateDTO mockTicketDTO() {
        TicketCreateDTO ticket = new TicketCreateDTO();
        ticket.setTicketId("6772975c6eaae32cbfdc7c66");
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


}
