package com.gavilan.sistemanotificacionesdemo.model.repositories;

import com.gavilan.sistemanotificacionesdemo.model.entities.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {
}
