package com.demo.Sistema.de.Eventos.controller.dto;

import com.demo.Sistema.de.Eventos.entities.Event;

public class TicketResponseDTO {


    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private Event event;
    private Double BRLamount;
    private Double USDamount;
//    private String status;

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

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

//    public String getStatus() {
//        return "concluído";
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
}
