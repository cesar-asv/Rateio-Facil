package com.tcc.rateiofacil.service;

import com.tcc.rateiofacil.dto.LoginRequest;
import com.tcc.rateiofacil.dto.UsuarioRequest;
import com.tcc.rateiofacil.dto.UsuarioResponse;
import com.tcc.rateiofacil.model.Usuario;
import com.tcc.rateiofacil.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponse criar(UsuarioRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        Usuario usuario = new Usuario(request.getNome(), request.getEmail(), request.getSenha());
        return UsuarioResponse.from(repository.save(usuario));
    }

    public List<UsuarioResponse> listar() {
        return repository.findAll().stream().map(UsuarioResponse::from).toList();
    }

    public UsuarioResponse autenticar(LoginRequest request) {
        Usuario usuario = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos"));
        if (!usuario.getSenha().equals(request.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }
        return UsuarioResponse.from(usuario);
    }

    public Usuario buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }
}