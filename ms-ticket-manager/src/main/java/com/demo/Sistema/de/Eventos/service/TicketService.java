package com.demo.Sistema.de.Eventos.service;

import com.demo.Sistema.de.Eventos.client.EventClient;
import com.demo.Sistema.de.Eventos.controller.dto.EmailDTO;
import com.demo.Sistema.de.Eventos.controller.dto.EventDTO;
import com.demo.Sistema.de.Eventos.entities.Email;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.entities.Ticket;
import com.demo.Sistema.de.Eventos.repository.TicketRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;


@Service
public class TicketService {

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
                () -> new RuntimeException("Ticket não encontrado")
        );
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
           email.setOwnerRef("Andre");
           email.setEmailFrom("andre_c_branco@gmail.com");
           email.setEmailTo("andre_c_branco@estudante.sc.senai.br");
           email.setSubject(event.getEventName());
           String body = String.format(
                   "Olá %s,\n\n" +
                           "Você foi inscrito no evento: %s.\n\n" +
                           "Detalhes do Evento:\n" +
                           "Nome: %s\n" +
                           "Data: %s\n" +
                           "Local: %s, %s, %s - %s \n\n" +  // Se o evento tiver um local
                           "Obrigado por participar!\n\n" +
                           "Atenciosamente,\n" +
                           "Equipe do Sistema de Eventos",
                   ticket.getCustomerName(),  // Inserindo o nome do proprietário no corpo
                   event.getEventName(),  // Nome do evento
                   event.getEventName(),  // Nome do evento (novamente, pode ser substituído por outro atributo)
                   event.getDateTime(),  // Se o evento tiver uma data, substitua por esse atributo
                   event.getLogradouro(),  // Se o evento tiver um local, substitua por esse atributo
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

        return ticketRepository.save(existingTicket);
    }

    public void softDeleteTicketById(String ticketId) {
        Ticket ticket = ticketRepository.findByTicketIdAndDeletedFalse(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));
        ticket.setDeleted(true);
        ticketRepository.save(ticket);
    }

}
