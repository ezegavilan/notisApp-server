package com.gavilan.sistemanotificacionesdemo.models.repositories;

import com.gavilan.sistemanotificacionesdemo.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {

}
