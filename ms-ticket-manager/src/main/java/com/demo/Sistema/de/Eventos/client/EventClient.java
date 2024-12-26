package com.demo.Sistema.de.Eventos.client;

import com.demo.Sistema.de.Eventos.controller.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EventClient", url = "http://localhost:8080")
public interface EventClient {

    @GetMapping("/get-event/{eventId}")
    EventDTO getInfoEvent(@PathVariable("eventId") String eventId);
}







