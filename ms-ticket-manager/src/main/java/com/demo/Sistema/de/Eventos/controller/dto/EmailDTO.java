package com.demo.Sistema.de.Eventos.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

    @NotBlank
    private String ownerRef;
    @NotBlank
    @Email
    private String emailFrom;
    @Email
    @NotBlank
    private String emailTo;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;



}
