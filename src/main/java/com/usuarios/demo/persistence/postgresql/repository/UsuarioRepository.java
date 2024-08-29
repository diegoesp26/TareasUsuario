package com.usuarios.demo.persistence.postgresql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usuarios.demo.persistence.postgresql.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	
	boolean existsByUsername(String username);
	
	//boolean existsByUsernameAndPassword(String username, String password);
	
	@Query("SELECT u.password FROM Usuario u WHERE u.username = :username")
	String findPasswordByUsername(String username);
	
	List<Usuario> findByUsername(String username);

}
