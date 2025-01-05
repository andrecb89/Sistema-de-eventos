package com.demo.Sistema.de.Eventos.service;

import com.demo.Sistema.de.Eventos.client.EventClient;
import com.demo.Sistema.de.Eventos.controller.dto.EventDTO;
import com.demo.Sistema.de.Eventos.entities.Email;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.entities.Ticket;
import com.demo.Sistema.de.Eventos.repository.TicketRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    public TicketRepository ticketRepository;
    public EventClient eventClient;
    public EmailService emailService;

    public TicketService(TicketRepository TicketRepository, EventClient eventClient,EmailService emailService) {
        this.ticketRepository = TicketRepository;
        this.eventClient = eventClient;
        this.emailService = emailService;
    }

    public Ticket findTicketById(String ticketId) {
        return ticketRepository.findByTicketIdAndDeletedFalse(ticketId).orElseThrow(
                () -> {
                    log.error("Ticket not found with id " + ticketId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found with id " + ticketId);
                });
    }

       public Ticket saveTicket(Ticket ticket) throws InvocationTargetException, IllegalAccessException {
           EventDTO infoAdress = eventClient.getInfoEvent(ticket.getEventId());
           if(infoAdress.getEventName() == null){
               throw new NoSuchElementException("Evento não encontrado");
           }
           var event = new Event();
           BeanUtils.copyProperties(event,infoAdress);
           ticket.setEvent(event);
           Email email = new Email();
           email.setOwnerRef(ticket.getCustomerName());
           email.setEmailFrom("eventos@contato.com.br");
           email.setEmailTo(ticket.getCustomerMail());
           email.setSubject(event.getEventName());
           String body = String.format(
                   "Olá %s,\n\n" +
                           "Você foi inscrito no evento: %s.\n\n" +
                           "Detalhes do Evento:\n" +
                           "Nome: %s\n" +
                           "Data: %s\n" +
                           "Local: %s, %s, %s - %s \n\n" +
                           "Obrigado por participar!\n\n" +
                           "Atenciosamente,\n" +
                           "Equipe do Sistema de Eventos",
                   ticket.getCustomerName(),
                   event.getEventName(),
                   event.getEventName(),
                   event.getDateTime(),
                   event.getLogradouro(),
                   event.getBairro(),
                   event.getLocalidade(),
                   event.getUf()
           );
           email.setBody(body);

           emailService.sendEmail(email);
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

        Email email = new Email();
        email.setOwnerRef(ticket.getCustomerName());
        email.setEmailFrom("eventos@contato.com.br");
        email.setEmailTo(ticket.getCustomerMail());
        email.setSubject(existingTicket.getEvent().getEventName());
        String body = String.format(
                "Olá %s,\n\n" +
                        "Você foi inscrito no evento: %s.\n\n" +
                        "Detalhes do Evento:\n" +
                        "Nome: %s\n" +
                        "Data: %s\n" +
                        "Local: %s, %s, %s - %s \n\n" +
                        "Obrigado por participar!\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe do Sistema de Eventos",
                ticket.getCustomerName(),
                existingTicket.getEvent().getEventName(),
                existingTicket.getEvent().getEventName(),
                existingTicket.getEvent().getDateTime(),
                existingTicket.getEvent().getLogradouro(),
                existingTicket.getEvent().getBairro(),
                existingTicket.getEvent().getLocalidade(),
                existingTicket.getEvent().getUf()
        );
        email.setBody(body);

        emailService.sendEmail(email);

        return ticketRepository.save(existingTicket);
    }

    public void softDeleteTicketById(String ticketId) {
        Ticket ticket = ticketRepository.findByTicketIdAndDeletedFalse(ticketId)
                .orElseThrow(() -> {
                            log.error("Ticket not found with id " + ticketId);
                            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found with id " + ticketId);
                        }
                );
        ticket.setDeleted(true);
        ticketRepository.save(ticket);
    }

    public List<Ticket> checkTicketsByEventId(String eventId) {
         List<Ticket> response = ticketRepository.findByEventIdAndDeletedFalse(eventId);
        return response;
    }

}
