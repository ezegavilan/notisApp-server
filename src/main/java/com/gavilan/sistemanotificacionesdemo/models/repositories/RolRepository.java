package com.gavilan.sistemanotificacionesdemo.models.repositories;

import com.gavilan.sistemanotificacionesdemo.models.entities.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {
}
