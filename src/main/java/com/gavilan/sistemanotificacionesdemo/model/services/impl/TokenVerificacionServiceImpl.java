package com.gavilan.sistemanotificacionesdemo.model.services.impl;

import com.gavilan.sistemanotificacionesdemo.model.entities.TokenVerificacion;
import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;
import com.gavilan.sistemanotificacionesdemo.model.exceptions.AutenticacionExcepcion;
import com.gavilan.sistemanotificacionesdemo.model.repositories.TokenVerificacionRepository;
import com.gavilan.sistemanotificacionesdemo.model.services.TokenVerificacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenVerificacionServiceImpl implements TokenVerificacionService {

    private final TokenVerificacionRepository tokenVerificacionRepository;

    @Transactional
    @Override
    public String generarTokenVerificacion(Usuario usuario) {

        String token = UUID.randomUUID().toString();

        TokenVerificacion tokenVerificacion = TokenVerificacion.builder()
                .token(token)
                .usuario(usuario).build();

        this.tokenVerificacionRepository.save(tokenVerificacion);
        return token;
    }

    @Transactional(readOnly = true)
    @Override
    public TokenVerificacion getTokenVerificacion(String token) {
        return this.tokenVerificacionRepository.findByToken(token)
                .orElseThrow(() -> new AutenticacionExcepcion("Token inválido"));
    }

    @Transactional
    @Override
    public void delete(TokenVerificacion tokenVerificacion) {
        this.tokenVerificacionRepository.delete(tokenVerificacion);
    }
}
