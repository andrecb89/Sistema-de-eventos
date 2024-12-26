package com.demo.Sistema.de.Eventos.service;

import com.demo.Sistema.de.Eventos.client.EventClient;
import com.demo.Sistema.de.Eventos.controller.dto.EventDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.entities.Ticket;
import com.demo.Sistema.de.Eventos.repository.TicketRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;


@Service
public class TicketService {

    public TicketRepository ticketRepository;
    public EventClient eventClient;

    public TicketService(TicketRepository TicketRepository, EventClient eventClient) {
        this.ticketRepository = TicketRepository;
        this.eventClient = eventClient;
    }

        public Ticket findTicketById(String ticketId) {
        return ticketRepository.findByTicketIdAndDeletedFalse(ticketId).orElseThrow(
                () -> new RuntimeException("Ticketo não encontrado")
        );
    }

       public Ticket saveTicket(Ticket ticket) throws InvocationTargetException, IllegalAccessException {
           EventDTO infoAdress = eventClient.getInfoEvent(ticket.getEventId());
           var event = new Event();
           BeanUtils.copyProperties(event,infoAdress);
           ticket.setEvent(event);
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Ticket ticket) {
        Ticket existingTicket = findTicketById(ticket.getTicketId());

        existingTicket.setTicketId(ticket.getTicketId());
        existingTicket.setCustomerName(ticket.getCustomerName());
        existingTicket.setCpf(ticket.getCpf());
        existingTicket.setCustomerMail(ticket.getCustomerMail());
        existingTicket.setEvent(ticket.getEvent());
        existingTicket.setStatus(ticket.getStatus());
        existingTicket.setCpf(ticket.getCpf());
        existingTicket.setBRLamount(ticket.getBRLamount());
        existingTicket.setUSDamount(ticket.getUSDamount());

        return ticketRepository.save(existingTicket);
    }

    public void softDeleteTicketById(String ticketId) {
        Ticket ticket = ticketRepository.findByTicketIdAndDeletedFalse(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));
        ticket.setDeleted(true);
        ticketRepository.save(ticket);
    }

}
