package com.demo.Sistema.de.Eventos.repository;

import com.demo.Sistema.de.Eventos.entities.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<Email, String> {
}
