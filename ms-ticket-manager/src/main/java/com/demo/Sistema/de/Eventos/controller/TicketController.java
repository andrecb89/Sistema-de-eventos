package com.demo.Sistema.de.Eventos.controller;

import com.demo.Sistema.de.Eventos.controller.dto.TicketCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.TicketResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.entities.Ticket;
import com.demo.Sistema.de.Eventos.service.TicketService;
import jakarta.validation.Valid;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;


       @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable String id) throws InvocationTargetException, IllegalAccessException {
        Ticket ticket = ticketService.findTicketById(id);
           TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
           BeanUtils.copyProperties(ticketResponseDTO, ticket);
        return ResponseEntity.ok(ticketResponseDTO);
    }

    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody @Valid TicketCreateDTO ticketCreateDTO) throws InvocationTargetException, IllegalAccessException {
        var ticket = new Ticket();
        BeanUtils.copyProperties(ticket, ticketCreateDTO);
        ticket = ticketService.saveTicket(ticket);
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        BeanUtils.copyProperties(ticketResponseDTO, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketResponseDTO);
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@RequestBody @Valid TicketCreateDTO ticketCreateDto,
                                                          @PathVariable String id) throws InvocationTargetException, IllegalAccessException {

        Ticket ticketToUpdate = new Ticket();
        BeanUtils.copyProperties(ticketToUpdate, ticketCreateDto);
        ticketToUpdate.setTicketId(id);
        Ticket updatedTicket = ticketService.updateTicket(ticketToUpdate);
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        BeanUtils.copyProperties(ticketResponseDTO, updatedTicket);
        return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTO);
    }


    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable String id) {
        ticketService.softDeleteTicketById(id);
        return ResponseEntity.ok("Deleted successfully");
    }



}
