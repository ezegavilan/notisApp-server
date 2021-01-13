package com.gavilan.sistemanotificacionesdemo.rest.controllers;

import com.gavilan.sistemanotificacionesdemo.model.dto.AuthResponse;
import com.gavilan.sistemanotificacionesdemo.model.dto.LoginRequest;
import com.gavilan.sistemanotificacionesdemo.model.dto.SignupRequest;
import com.gavilan.sistemanotificacionesdemo.model.exceptions.AutenticacionExcepcion;
import com.gavilan.sistemanotificacionesdemo.model.exceptions.NotisException;
import com.gavilan.sistemanotificacionesdemo.model.services.AutenticacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationController {

    private final AutenticacionService autenticacionService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.autenticacionService.signup(signupRequest);
        } catch (AutenticacionExcepcion | NotisException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Usuario registrado con éxito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/auth/accountVerification/{token}")
    public ResponseEntity<String> verificarToken(@PathVariable String token) {

        try {
            this.autenticacionService.verificarCuenta(token);
        } catch (AutenticacionExcepcion | NotisException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Cuenta verificada exitosamente", HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        AuthResponse authResponse;

        try {
            authResponse = this.autenticacionService.login(loginRequest);
        } catch (AutenticacionExcepcion | NotisException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("authResponse", authResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
