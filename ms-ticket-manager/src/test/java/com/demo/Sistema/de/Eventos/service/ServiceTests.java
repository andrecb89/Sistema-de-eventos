package com.demo.Sistema.de.Eventos.service;

import com.demo.Sistema.de.Eventos.client.EventClient;
import com.demo.Sistema.de.Eventos.controller.dto.EventDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.entities.Ticket;
import com.demo.Sistema.de.Eventos.mocks.MockTicket;
import com.demo.Sistema.de.Eventos.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceTests {

        @Mock
        private TicketRepository ticketRepository;


        @InjectMocks
        private TicketService ticketService;

//        @Mock
//        private TicketService ticketService;

        MockTicket mockTicket;

    @Mock
    public EventClient eventClient;

    @Mock
    public EmailService emailService;

    @BeforeEach
        public void setUp() {
            mockTicket = new MockTicket();
        }


        @Test
        public void FindTicketById_WithValidId_ExpectValidTicket() {
            when(ticketRepository.findByTicketIdAndDeletedFalse("6772975c6eaae32cbfdc7c66")).thenReturn(
                    of(new Ticket("João", "01234567890", "joao@gmail.com",
                            "Concluído", 50.00)));

            Ticket ticket = ticketService.findTicketById("6772975c6eaae32cbfdc7c66");

            assertThat(ticket).isNotNull();
            assertEquals("joao@gmail.com", ticket.getCustomerMail());
            assertEquals("01234567890", ticket.getCpf());
            assertEquals("João", ticket.getCustomerName());
            assertEquals("Concluído", ticket.getStatus());
            assertEquals(50.00, ticket.getBRLamount());

        }

        @Test
        public void SaveTicket_WithValidData_shouldEqualExpectedResult() throws InvocationTargetException, IllegalAccessException {
            when(ticketRepository.save(any(Ticket.class))).thenReturn(new Ticket("João", "01234567890", "joao@gmail.com",
                    "Concluído", 50.00));

            EventDTO infoAdress = new EventDTO(
                    "676aa088899e6c4a7b713a4a",
                    "Show de musica",
                    "01020-000",
                    "Rua Tabatinguera",
                    "Sé",
                    "São Paulo",
                    "SP"
            );
            when(eventClient.getInfoEvent("676aa088899e6c4a7b713a4a")).thenReturn(infoAdress);

            Ticket ticket = ticketService.saveTicket(mockTicket.mockTicket());

            assertThat(ticket).isNotNull();
            assertEquals("joao@gmail.com", ticket.getCustomerMail());
            assertEquals("01234567890", ticket.getCpf());
            assertEquals("João", ticket.getCustomerName());
            assertEquals("Concluído", ticket.getStatus());
            assertEquals(50.00, ticket.getBRLamount());

        }

        @Test
        public void UpdateTicket_WithValidData_shouldEqualExpectedResult() {
            Ticket returnTicket = mockTicket.mockTicket();
            when(ticketRepository.findByTicketIdAndDeletedFalse(anyString())).thenReturn(
                    Optional.of(new Ticket("6772975c6eaae32cbfdc7c66","João",
                                    "01234567890", "joao@gmail.com", "Concluído",
                             50.00)));
            when(ticketRepository.save(any(Ticket.class))).thenReturn(returnTicket);

            Ticket ticket = ticketService.updateTicket(returnTicket);

            assertEquals("João", ticket.getCustomerName());
            assertEquals("01234567890", ticket.getCpf());
            assertEquals("joao@gmail.com", ticket.getCustomerMail());
            assertEquals("Concluído", ticket.getStatus());
            assertEquals(50.00, ticket.getBRLamount());
        }


        @Test
        public void DeleteTicket_WithInvalidId_ThrowsException() {
            assertThatThrownBy(() -> ticketService.softDeleteTicketById("00000000000000"))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasMessageContaining("Ticket not found with id 00000000000000");


        }

    @Test
    public void CheckTicketsByEvent_WithValidEventId_ExpectListSizeOfOne() {
        List<Ticket> tickets = Collections.singletonList(mockTicket.mockTicket());
        when(ticketRepository.findByEventIdAndDeletedFalse(anyString())).thenReturn(tickets);


        List<Ticket> response = ticketService.checkTicketsByEventId("676aa088899e6c4a7b713a4a");

        assertEquals(1, response.size());

        }
}
