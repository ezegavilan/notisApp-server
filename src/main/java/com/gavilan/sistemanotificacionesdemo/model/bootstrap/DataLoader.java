package com.gavilan.sistemanotificacionesdemo.model.bootstrap;

import com.gavilan.sistemanotificacionesdemo.model.entities.Rol;
import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;
import com.gavilan.sistemanotificacionesdemo.model.repositories.RolRepository;
import com.gavilan.sistemanotificacionesdemo.model.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Rol roleUser = this.rolRepository.findByNombre("ROLE_USER")
                .orElse(null);

        Usuario usuario = new Usuario();
        usuario.setUsername("neritoo");
        usuario.setEmail("ezegavilan95@gmail.com");
        usuario.setEnabled(true);
        usuario.setPassword(passwordEncoder.encode("1677613325"));
        usuario.setRol(roleUser);

        this.usuarioRepository.save(usuario);
    }
}
