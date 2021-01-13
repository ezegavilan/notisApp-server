package com.gavilan.sistemanotificacionesdemo.model.services;

import com.gavilan.sistemanotificacionesdemo.model.entities.Post;

import java.util.List;

public interface PostService {

    Post crearPost(Post post);

    List<Post> listarPosts();

    Post obtenerPost(Long idPost);

    List<Post> obtenerPostPorUsuario(String username);

}
