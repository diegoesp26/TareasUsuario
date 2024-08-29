package com.usuarios.demo.persistence.postgresql.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.usuarios.demo.persistence.postgresql.entity.Tarea;
import com.usuarios.demo.persistence.postgresql.entity.Usuario;

public interface TareaRepository extends CrudRepository<Tarea, Integer> {
	
	List<Tarea> findByUsuario(Usuario usuario);

}
