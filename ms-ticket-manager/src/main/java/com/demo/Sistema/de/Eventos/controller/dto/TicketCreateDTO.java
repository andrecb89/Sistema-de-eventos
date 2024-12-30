package com.demo.Sistema.de.Eventos.controller.dto;

import com.demo.Sistema.de.Eventos.entities.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class TicketCreateDTO {

    @Id
    private String ticketId;
    @NotBlank
    private String customerName;
    @NotBlank
    private String cpf;
    @NotBlank
    private String eventId;
    @NotBlank
    private String customerMail;
    private Event event;
    private Double BRLamount;
    private Double USDamount;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public @NotBlank String getEventId() {
        return eventId;
    }

    public void setEventId(@NotBlank String eventId) {
        this.eventId = eventId;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }


    public Double getBRLamount() {
        return BRLamount;
    }

    public void setBRLamount(Double BRLamount) {
        this.BRLamount = BRLamount;
    }

    public Double getUSDamount() {
        return USDamount;
    }

    public void setUSDamount(Double USDamount) {
        this.USDamount = USDamount;
    }


}