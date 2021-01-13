package com.gavilan.sistemanotificacionesdemo.model.repositories;

import com.gavilan.sistemanotificacionesdemo.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
