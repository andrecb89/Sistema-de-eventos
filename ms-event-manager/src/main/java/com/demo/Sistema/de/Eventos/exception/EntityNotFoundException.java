package com.demo.Sistema.de.Eventos.exception;

public class EntityNotFoundException extends RuntimeException  {
    public EntityNotFoundException(String message) {
        super(message);
    }
}