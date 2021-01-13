package com.gavilan.sistemanotificacionesdemo.model.services.impl;

import com.gavilan.sistemanotificacionesdemo.model.entities.Rol;
import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;
import com.gavilan.sistemanotificacionesdemo.model.exceptions.AutenticacionExcepcion;
import com.gavilan.sistemanotificacionesdemo.model.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = this.usuarioRepository.findById(username)
                .orElseThrow(() -> new AutenticacionExcepcion("No existe el usuario: " + username));

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(),
                true, true, true,
                this.getAuthorities(usuario.getRol()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Rol rol) {
        return Collections.singletonList(new SimpleGrantedAuthority(rol.getNombre()));
    }
}
