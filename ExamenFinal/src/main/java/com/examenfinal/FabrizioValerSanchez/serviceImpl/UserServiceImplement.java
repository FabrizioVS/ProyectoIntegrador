package com.examenfinal.FabrizioValerSanchez.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Usuario;
import com.examenfinal.FabrizioValerSanchez.repository.UsuarioRepository;
import com.examenfinal.FabrizioValerSanchez.service.UsuarioService;
import com.examenfinal.FabrizioValerSanchez.service.UsuarioServiceHelp;

@Service
public class UserServiceImplement implements UserDetailsService, UsuarioServiceHelp {

	@Autowired
	private UsuarioRepository dao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = dao.findOneByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException("El usuario con dicho email: " + email + "no existe."));

		return new UserDetailImplement(usuario);
	}

	@Override
	public ResponseEntity<Map<String, Object>> actualizarFechaAlaActual(String nombre) {
	    Map<String, Object> respuesta = new HashMap<>();

	    List<Usuario> usuariosExistentes = dao.findByNombre(nombre);

	    if (!usuariosExistentes.isEmpty()) {
	        
	        Usuario usuario = usuariosExistentes.get(0);
	      
	       LocalDateTime fechaActual = LocalDateTime.now();

			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			String fechaTexto = fechaActual.format(formato);
	       
	    
	        usuario.setFecha(fechaTexto);  
	        
	     
	        dao.save(usuario);
	       
	        respuesta.put("usuario", usuario);
	        respuesta.put("mensaje", "Fecha del usuario actualizada");
	        respuesta.put("status", HttpStatus.CREATED);
	        respuesta.put("fecha", new Date());
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
	    } else {
	        respuesta.put("mensaje", "No se encontraron usuarios con el nombre: " + nombre);
	        respuesta.put("status", HttpStatus.NOT_FOUND);
	        respuesta.put("fecha", new Date());
	        
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	    }
	}


}
