package com.gavilan.sistemanotificacionesdemo.models.services;

import com.gavilan.sistemanotificacionesdemo.models.dto.AuthResponse;
import com.gavilan.sistemanotificacionesdemo.models.dto.LoginRequest;
import com.gavilan.sistemanotificacionesdemo.models.dto.SignupRequest;
import com.gavilan.sistemanotificacionesdemo.models.entities.Usuario;

public interface AutenticacionService {

    void signup(SignupRequest signupRequest);

    void verificarCuenta(String token);

    AuthResponse login(LoginRequest loginRequest);

    Usuario getUsuarioActual();

    boolean estaLogueado();
}
