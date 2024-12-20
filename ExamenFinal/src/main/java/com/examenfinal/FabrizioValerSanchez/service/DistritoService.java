package com.examenfinal.FabrizioValerSanchez.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.examenfinal.FabrizioValerSanchez.model.Distrito;

public interface DistritoService {
	
	public ResponseEntity<Map<String, Object>> listarDistritos();
	// se le agrega
	List<Distrito> obtenerTodos();
}
