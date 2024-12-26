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
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id) {
        Ticket ticket = ticketService.findTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody @Valid TicketCreateDTO ticketCreateDTO) throws InvocationTargetException, IllegalAccessException {
        var Ticket = new Ticket();
        BeanUtils.copyProperties(Ticket, ticketCreateDTO);
        Ticket = ticketService.saveTicket(Ticket);
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        BeanUtils.copyProperties(ticketResponseDTO, Ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketResponseDTO);
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<Ticket> updateTicket(@RequestBody @Valid TicketCreateDTO ticketCreateDto,
                                             @PathVariable String id) throws InvocationTargetException, IllegalAccessException {

        Ticket ticketToUpdate = new Ticket();
        BeanUtils.copyProperties(ticketToUpdate, ticketCreateDto);
        ticketToUpdate.setTicketId(id);
        Ticket updatedTicket = ticketService.updateTicket(ticketToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTicket);
    }


    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable String id) {
        ticketService.softDeleteTicketById(id);
        return ResponseEntity.ok("Deleted successfully");
    }



}
