package com.usuarios.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usuarios.demo.persistence.postgresql.entity.Tarea;
import com.usuarios.demo.persistence.postgresql.entity.Usuario;
import com.usuarios.demo.persistence.postgresql.repository.TareaRepository;
import com.usuarios.demo.persistence.postgresql.repository.UsuarioRepository;

@Service
public class TareaService {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private TareaRepository tareaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//METODO PARA OBTENER TAREAS Y SU ESTADO POR USUARIO
	@Transactional
	public List<Tarea> getTareasByUsuario(String usuario) {
		
		List<Usuario> usuarioTY = usuarioRepository.findByUsername(usuario);
		
		return tareaRepository.findByUsuario(usuarioTY.get(0));
		
	}
	
	//METODO PARA AGREGAR TAREA POR USUARIO
	@Transactional
	public Tarea agregaTareaByUsuario(String usuario, Tarea tarea) {
			
		List<Usuario> usuarioTY = usuarioRepository.findByUsername(usuario);
			
		tarea.setUsuario(usuarioTY.get(0));
			
		return tareaRepository.save(tarea);
			
	}
	
	//METODO PARA MARCAR TAREA POR USUARIO
	@Transactional
	public Tarea marcaTareaByUsuario(String usuario, Boolean resuelta) {
				
		List<Usuario> usuarioTY = usuarioRepository.findByUsername(usuario);
		
		List<Tarea> tareaTY = tareaRepository.findByUsuario(usuarioTY.get(0));
		
		Tarea tarea = tareaTY.get(0);
		
		tarea.setEstado(resuelta);
				
		return tareaRepository.save(tarea);
				
	}
	
	//METODO PARA ELOIMINAR TAREA
	@Transactional
	public void eliminaTareaById(Integer idTarea) {
					
		if (tareaRepository.existsById(idTarea)) {
	        	tareaRepository.deleteById(idTarea);
	       } else {
	            throw new RuntimeException("Tarea no encontrada con el id: " + idTarea);
	        }
					
		}
	

}
