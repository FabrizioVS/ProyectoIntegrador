package com.examenfinal.FabrizioValerSanchez.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.examenfinal.FabrizioValerSanchez.model.Usuario;

public interface UsuarioServiceHelp {
	public ResponseEntity<Map<String, Object>> actualizarFechaAlaActual(String nombre,int idUsuario);

	

}
