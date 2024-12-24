package com.demo.Sistema.de.Eventos.client;

import com.demo.Sistema.de.Eventos.controller.dto.CepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ViacepClient", url = "https://viacep.com.br/ws")
public interface ViacepClient {

    @GetMapping("/{cep}/json/")
    CepDTO getInfoCep(@PathVariable("cep") String cep);
}







