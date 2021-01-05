package com.gavilan.sistemanotificacionesdemo.model.services;

import com.gavilan.sistemanotificacionesdemo.model.dto.AuthResponse;
import com.gavilan.sistemanotificacionesdemo.model.dto.LoginRequest;
import com.gavilan.sistemanotificacionesdemo.model.dto.SignupRequest;
import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;

public interface AutenticacionService {

    void signup(SignupRequest signupRequest);

    void verificarCuenta(String token);

    AuthResponse login(LoginRequest loginRequest);

    Usuario getUsuarioActual();

    boolean estaLogueado();
}
