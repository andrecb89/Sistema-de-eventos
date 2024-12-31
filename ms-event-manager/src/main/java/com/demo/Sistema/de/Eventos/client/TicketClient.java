package com.demo.Sistema.de.Eventos.client;

import com.demo.Sistema.de.Eventos.controller.dto.TicketResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "TicketClient", url = "http://localhost:8081")
public interface TicketClient {

    @GetMapping("/check-tickets-by-event/{eventId}")
    ResponseEntity<List<TicketResponseDTO>> getInfoEvent(@PathVariable("eventId") String eventId);
}







