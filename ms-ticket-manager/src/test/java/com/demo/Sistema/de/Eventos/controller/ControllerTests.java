package com.demo.Sistema.de.Eventos.controller;

import com.demo.Sistema.de.Eventos.controller.dto.TicketCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.TicketResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Ticket;
import com.demo.Sistema.de.Eventos.mocks.MockTicket;
import com.demo.Sistema.de.Eventos.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
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
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    MockTicket mockTicket;

    @BeforeEach
    public void setUp() {
        mockTicket = new MockTicket();
    }

    @Test
    public void GetTicketById_WithValidId_ExpectTicketFound() throws InvocationTargetException, IllegalAccessException {
        Ticket ticket = mockTicket.mockTicket();
        when(ticketService.findTicketById(ticket.getTicketId())).thenReturn(ticket);

        ResponseEntity<TicketResponseDTO> result = ticketController.getTicketById(ticket.getTicketId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(ticket.getTicketId(), result.getBody().getTicketId());
        assertEquals(ticket.getCustomerName(), result.getBody().getCustomerName());
        assertEquals(ticket.getBRLamount(), result.getBody().getBRLamount());
        assertEquals(ticket.getCustomerMail(), result.getBody().getCustomerMail());
    }

    @Test
    public void CreateTicket_WithValidData_ExpectTicketCreated() throws InvocationTargetException, IllegalAccessException {
        Ticket ticket = mockTicket.mockTicket();
        TicketCreateDTO ticketCreateDTO = new MockTicket().mockTicketDTO();
        when(ticketService.saveTicket(any())).thenReturn(ticket);

        ResponseEntity<TicketResponseDTO> ticketResponse = ticketController.createTicket(ticketCreateDTO);

        assertEquals(HttpStatus.CREATED, ticketResponse.getStatusCode());
        assertEquals("João", ticket.getCustomerName());
        assertEquals("01234567890", ticket.getCpf());
        assertEquals("joao@gmail.com", ticket.getCustomerMail());
        assertEquals(50.00, ticket.getBRLamount());
    }

    @Test
    public void UpdateTicket_WithValidData_shouldEqualExpectedResult() throws InvocationTargetException, IllegalAccessException {
        TicketCreateDTO returnTicketDTO = mockTicket.mockTicketDTO();
        Ticket ticket = mockTicket.mockTicket();
        when(ticketService.updateTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<TicketResponseDTO> response = ticketController.updateTicket(returnTicketDTO, "676af34f0e58c90e00360090");

        assertEquals("João", response.getBody().getCustomerName());
        assertEquals("01234567890", response.getBody().getCpf());
        assertEquals("joao@gmail.com", response.getBody().getCustomerMail());
        assertEquals(50, response.getBody().getBRLamount());
    }


    @Test
    public void DeleteTicket_WithValidId_ReturnsOkStatus() {
        doNothing().when(ticketService).softDeleteTicketById("00000000000000");

        ResponseEntity<String> response = ticketController.deleteTicketById("00000000000000");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Deleted successfully");
    }

    @Test
    public void checkTicketsByEvent_WithValidEventId_ExpectListSizeOfOne() throws InvocationTargetException, IllegalAccessException {
        List<Ticket> tickets = Collections.singletonList(mockTicket.mockTicket());
        Ticket ticket = mockTicket.mockTicket();
        when(ticketService.checkTicketsByEventId(ticket.getTicketId())).thenReturn(tickets);

        List<TicketResponseDTO> result = ticketController.checkTicketsByEvent(ticket.getTicketId()).getBody();

        assertEquals(1, result.size());
    }

}
