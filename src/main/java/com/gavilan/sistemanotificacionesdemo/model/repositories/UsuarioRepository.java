package com.gavilan.sistemanotificacionesdemo.model.repositories;

import com.gavilan.sistemanotificacionesdemo.model.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {

}
