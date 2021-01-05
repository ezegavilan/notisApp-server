package com.gavilan.sistemanotificacionesdemo.model.services;

import com.gavilan.sistemanotificacionesdemo.model.entities.TokenVerificacion;
import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;

public interface TokenVerificacionService {

    String generarTokenVerificacion(Usuario usuario);

    TokenVerificacion getTokenVerificacion(String token);

    void delete(TokenVerificacion tokenVerificacion);
}
