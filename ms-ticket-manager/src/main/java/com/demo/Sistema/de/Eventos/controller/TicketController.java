package com.demo.Sistema.de.Eventos.controller;

import com.demo.Sistema.de.Eventos.controller.dto.TicketCreateDTO;
import com.demo.Sistema.de.Eventos.controller.dto.TicketResponseDTO;
import com.demo.Sistema.de.Eventos.entities.Event;
import com.demo.Sistema.de.Eventos.entities.Ticket;
import com.demo.Sistema.de.Eventos.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Operation(
            summary = "Get ticket by ID",
            parameters = @Parameter(name = "id", description = "Ticket Id", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ticket details",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
                    )
            }

    )
       @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable String id) throws InvocationTargetException, IllegalAccessException {
        log.info("Getting ticket by ID");
        Ticket ticket = ticketService.findTicketById(id);
           TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
           BeanUtils.copyProperties(ticketResponseDTO, ticket);
        return ResponseEntity.ok(ticketResponseDTO);
    }


    @Operation(
            summary = "Create a new ticket",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ticket created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
                    )
            }

    )
    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody @Valid TicketCreateDTO ticketCreateDTO) throws InvocationTargetException, IllegalAccessException {
        log.info("Creating a new ticket");
        var ticket = new Ticket();
        BeanUtils.copyProperties(ticket, ticketCreateDTO);
        ticket = ticketService.saveTicket(ticket);
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        BeanUtils.copyProperties(ticketResponseDTO, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketResponseDTO);
    }

    @Operation(
            summary = "Update an ticket by ID",
            parameters = @Parameter(name = "id", description = "Ticket Id", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ticket updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
                    )
            }

    )

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@RequestBody @Valid TicketCreateDTO ticketCreateDto,
                                                          @PathVariable String id) throws InvocationTargetException, IllegalAccessException {
        log.info("Updating ticket by ID");
        Ticket ticketToUpdate = new Ticket();
        BeanUtils.copyProperties(ticketToUpdate, ticketCreateDto);
        ticketToUpdate.setTicketId(id);
        Ticket updatedTicket = ticketService.updateTicket(ticketToUpdate);
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        BeanUtils.copyProperties(ticketResponseDTO, updatedTicket);
        return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTO);
    }


    @Operation(
            summary = "Delete an ticket by ID",
            parameters = @Parameter(name = "id", description = "Ticket Id", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ticket deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
                    )
            }

    )

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<String> deleteTicketById(@PathVariable String id) {
        log.info("Deleting ticket by ID");
        ticketService.softDeleteTicketById(id);
        return ResponseEntity.ok("Deleted successfully");
    }



}
