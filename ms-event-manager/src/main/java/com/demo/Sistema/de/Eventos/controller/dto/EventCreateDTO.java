package com.demo.Sistema.de.Eventos.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class EventCreateDTO {


    @NotBlank
    private String eventName;
    @NotNull
    private LocalDateTime dateTime;
    @NotBlank
    private String cep;


    public @NotBlank String getEventName() {
        return eventName;
    }

    public void setEventName(@NotBlank String eventName) {
        this.eventName = eventName;
    }

    public @NotNull LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(@NotNull LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public @NotBlank String getCep() {
        return cep;
    }

    public void setCep(@NotBlank String cep) {
        this.cep = cep;
    }

    public EventCreateDTO() {
    }


}
