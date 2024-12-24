package com.demo.Sistema.de.Eventos.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.time.LocalDate;

public class EventResponseDTO {

    @NotBlank
    private ObjectId id;
    @NotBlank
    private String eventName;
    @NotNull
    private LocalDate dateTime;
    @NotBlank
    private String cep;


}
