package com.usuarios.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usuarios.demo.dto.UsuarioDTO;
import com.usuarios.demo.persistence.postgresql.entity.Tarea;
import com.usuarios.demo.persistence.postgresql.entity.Usuario;
import com.usuarios.demo.service.TareaService;
import com.usuarios.demo.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TareaService tareaService;
	
	/*
	 * ENDPOINT QUE INSCRIBE Y ACTUALIZA USUARIO
	 */
	// XDXDXD
	@PostMapping("/inscripcion/save")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> inscribeUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			return ResponseEntity.ok(usuarioService.inscribeUsuario(usuarioDTO));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	/*
	 * ENDPOINT QUE VALIDA EL LOGIN DE USUARIO
	 */
	@PostMapping("/login")
	@PreAuthorize("hasRole('USER')")
	public Boolean login(@RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.logearUsuario(usuarioDTO);
	}
	
	/*
	 * ENDPOINT PARA OBTENER DATOS USUARIO A PARTIR DE LA SESION
	 */
	
	/*
	 * ENDPOINT PARA Obtenerlistado de tareas del usuario y su estado
	 */
	@GetMapping("/tareas/getlistatareabyusuario")
	@PreAuthorize("hasRole('USER')")
	public List<Tarea> getListaTareaByUsuario(
			@RequestParam("usuario") String usuario
			) {
		return tareaService.getTareasByUsuario(usuario);
	} 
	
	/*
	 * ENDPOINT PARA Agregartarea asociada al usuario
	 */
	@PostMapping("/tareas/savetareabyusuario")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> agregaTareaByUsuario(@RequestParam String usuario, @RequestBody Tarea tarea) {
		return ResponseEntity.ok(tareaService.agregaTareaByUsuario(usuario, tarea));	
	}
	
	/*
	 * ENDPOINT PARA  Marcarcomoresuelta una tarea de un usuario
	 */
	@PutMapping("/tareas/marcartareabyusuario")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> marcarTareaByUsuario(
			@RequestParam String usuario, 
			@RequestParam Boolean resuelta) {
		return ResponseEntity.ok(tareaService.marcaTareaByUsuario(usuario, resuelta));	
	}
	
	
	/*
	 * ENDPOINT PARA  Eliminar una tarea de un usuario
	 */
	@DeleteMapping("/tareas/eliminatarea{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> deleteTarea(@PathVariable Integer id) {
        try {
            tareaService.eliminaTareaById(id);
            return ResponseEntity.ok("Tarea eliminada con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
