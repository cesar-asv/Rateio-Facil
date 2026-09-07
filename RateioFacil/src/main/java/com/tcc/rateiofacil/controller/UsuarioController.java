package com.tcc.rateiofacil.controller;

import com.tcc.rateiofacil.dto.LoginRequest;
import com.tcc.rateiofacil.dto.UsuarioRequest;
import com.tcc.rateiofacil.dto.UsuarioResponse;
import com.tcc.rateiofacil.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse criar(@RequestBody UsuarioRequest request) {
        return service.criar(request);
    }

    // TODO: implementar o login de verdade
    @PostMapping("/login")
    public UsuarioResponse login(@RequestBody LoginRequest request) {
        return service.autenticar(request);
    }
}