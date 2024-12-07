package com.examenfinal.FabrizioValerSanchez.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.examenfinal.FabrizioValerSanchez.model.Usuario;

public interface UsuarioService {
	public ResponseEntity<Map<String, Object>> listarUsuarios();

	public ResponseEntity<Map<String, Object>> crearUsuario(Usuario usuario);
}
