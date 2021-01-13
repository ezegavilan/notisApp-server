package com.gavilan.sistemanotificacionesdemo.model.repositories;

import com.gavilan.sistemanotificacionesdemo.model.entities.Post;
import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUsuario(Usuario usuario);

    List<Post> findAllByOrderByCreadoEnAsc();
}
