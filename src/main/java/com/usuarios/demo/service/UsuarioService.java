package com.usuarios.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usuarios.demo.dto.UsuarioDTO;
import com.usuarios.demo.persistence.postgresql.entity.Usuario;
import com.usuarios.demo.persistence.postgresql.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//METODO PARA INSCRIBIR USUARIO CON LA CONTRASEÑA ENCRIPTADA
	@Transactional
	public Usuario inscribeUsuario(UsuarioDTO usuarioDTO) {
		
		//VALIDA SI NOMBRE DE USUARIO YA EXISTE
		if (validaNombreUsuario(usuarioDTO.getNombreUsuario())) {
			throw new IllegalArgumentException("usuario ya existe en la base de datos");
		}
		
		
		Usuario usuario = new Usuario();
		
		usuario.setUsername(usuarioDTO.getNombreUsuario());
		
		//
		usuario.setPassword(encriptaClave(usuarioDTO.getClave()));
		
		return usuarioRepository.save(usuario);
	}
	
	//METODO PARA LOGEAR AL USUARIO
	@Transactional
	public Boolean logearUsuario(UsuarioDTO usuarioDTO) {
		
		//OBTIENE CLAVE ENCRIPTADA POR USUARIO
		String claveEncriptada = usuarioRepository.findPasswordByUsername(usuarioDTO.getNombreUsuario());
		
		return validaClaveEncriptada(usuarioDTO.getClave(), claveEncriptada);
	}
	
	//METODO PARA VALIDAR SI NOMBRE DE USUARIO YA EXISTE
	private Boolean validaNombreUsuario(String username) {
		
		return usuarioRepository.existsByUsername(username);
	}
	
	//METODO PARA ENCRIPTAR CLAVE CON BCRYPT
	private String encriptaClave(String clave) {
		
		return passwordEncoder.encode(clave);		
	}
	
	//METODO PARA VALIDAR SI EXISTE REGISTRO POR USUARIO Y CONTRASEÑA
//	private Boolean validaNombreUsuarioClave(String usuario, String clave) {
//		
//		return usuarioRepository.existsByUsernameAndPassword(usuario, clave);
//	}
	
	//METODO PARA VALIDAR CONTRASEÑA CON CONTRASEÑA ENCRIPTADA
	private Boolean validaClaveEncriptada(String clave, String claveEncriptada) {
		return passwordEncoder.matches(clave, claveEncriptada);
	}
	
}
