package com.gavilan.sistemanotificacionesdemo.model.services.impl;

import com.gavilan.sistemanotificacionesdemo.model.entities.Post;
import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;
import com.gavilan.sistemanotificacionesdemo.model.exceptions.NotisException;
import com.gavilan.sistemanotificacionesdemo.model.repositories.PostRepository;
import com.gavilan.sistemanotificacionesdemo.model.repositories.UsuarioRepository;
import com.gavilan.sistemanotificacionesdemo.model.services.AutenticacionService;
import com.gavilan.sistemanotificacionesdemo.model.services.NotificacionService;
import com.gavilan.sistemanotificacionesdemo.model.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UsuarioRepository usuarioRepository;
    private final AutenticacionService autenticacionService;
    private final NotificacionService notificacionService;

    @Transactional
    @Override
    public Post crearPost(Post post) {
        Usuario usuarioActual = this.autenticacionService.getUsuarioActual();

        Post nuevoPost = Post.builder()
                .nombrePost(post.getNombrePost())
                .descripcion(post.getDescripcion())
                .creadoEn(new Date())
                .usuario(usuarioActual).build();

        this.notificacionService.enviarNotificacion(nuevoPost);
        return this.postRepository.save(nuevoPost);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> listarPosts() {
        return this.postRepository.findAllByOrderByCreadoEnAsc();
    }

    @Transactional(readOnly = true)
    @Override
    public Post obtenerPost(Long idPost) {
        return this.postRepository.findById(idPost)
                .orElseThrow(() -> new NotisException("Post no encontrado con id: ".concat(idPost.toString())));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> obtenerPostPorUsuario(String username) {

        Usuario usuario = this.usuarioRepository.findById(username)
                .orElseThrow(() -> new NotisException("Usuario no existente con nombre de usuario: "
                + username));

        return this.postRepository.findAllByUsuario(usuario);
    }
}
