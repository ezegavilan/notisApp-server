package com.gavilan.sistemanotificacionesdemo.model.services.impl;

import com.gavilan.sistemanotificacionesdemo.model.dto.AuthResponse;
import com.gavilan.sistemanotificacionesdemo.model.dto.LoginRequest;
import com.gavilan.sistemanotificacionesdemo.model.dto.NotificacionEmail;
import com.gavilan.sistemanotificacionesdemo.model.dto.SignupRequest;
import com.gavilan.sistemanotificacionesdemo.model.entities.Rol;
import com.gavilan.sistemanotificacionesdemo.model.entities.TokenVerificacion;
import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;
import com.gavilan.sistemanotificacionesdemo.model.exceptions.AutenticacionExcepcion;
import com.gavilan.sistemanotificacionesdemo.model.repositories.RolRepository;
import com.gavilan.sistemanotificacionesdemo.model.repositories.UsuarioRepository;
import com.gavilan.sistemanotificacionesdemo.model.services.AutenticacionService;
import com.gavilan.sistemanotificacionesdemo.model.services.MailService;
import com.gavilan.sistemanotificacionesdemo.model.services.TokenVerificacionService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
public class AutenticacionServiceImpl implements AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final TokenVerificacionService tokenVerificacionService;
    private final MailService mailService;

    @Transactional
    @Override
    public void signup(SignupRequest signupRequest) {
        Rol roleUser = this.rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new AutenticacionExcepcion("ROLE_USER no cargado"));

        if (this.usuarioRepository.findById(signupRequest.getUsername()).isPresent())
            throw new AutenticacionExcepcion("Ya existe el usuario");

        Usuario usuario = Usuario.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(this.passwordEncoder.encode(signupRequest.getPassword()))
                .enabled(false)
                .fechaCreacion(new Date())
                .rol(roleUser).build();

        try {
            this.usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new AutenticacionExcepcion("Excepción al registrar al usuario: ".concat(signupRequest.getUsername()));
        }

        String tokenVerificacion = this.tokenVerificacionService.generarTokenVerificacion(usuario);
        this.notificarMail(usuario.getEmail(), tokenVerificacion);
    }

    private void notificarMail(String email, String tokenVerificacion) {
        String baseUrl = "http://localhost:8080";

        NotificacionEmail notificacionEmail = new NotificacionEmail();

        String mailBody = "Gracias por registrarte. Porfavor, verifique su cuenta haciendo click aquí abajo:\n";
        notificacionEmail.setSubject("Activá tu cuenta");
        notificacionEmail.setRecipient(email);
        notificacionEmail.setBody(mailBody);

        this.mailService.sendEmail(notificacionEmail, baseUrl.concat("/api/auth/accountVerification/")
                .concat(tokenVerificacion));
    }

    @Override
    public void verificarCuenta(String token) {
        TokenVerificacion tokenVerificacion = this.tokenVerificacionService.getTokenVerificacion(token);

        this.fetchUserAndEnable(tokenVerificacion);
    }

    @Transactional
    public void fetchUserAndEnable(TokenVerificacion tokenVerificacion) {

        String username = tokenVerificacion.getUsuario().getUsername();

        Usuario usuario = this.usuarioRepository.findById(username)
                .orElseThrow(() -> new AutenticacionExcepcion("No se encontró al usuario: " + username));

        usuario.setEnabled(true);
        this.usuarioRepository.save(usuario);
        this.tokenVerificacionService.delete(tokenVerificacion);
    }

    @Transactional
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Usuario usuario = this.usuarioRepository.findById(loginRequest.getUsername())
                .orElseThrow(() -> new AutenticacionExcepcion("Usuario no encontrado"));

        Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwt;
        return null;
    }

    @Override
    public Usuario getUsuarioActual() {
        return null;
    }

    @Override
    public boolean estaLogueado() {
        return false;
    }
}
