package com.examenfinal.FabrizioValerSanchez.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface DistritoService {
	
	public ResponseEntity<Map<String, Object>> listarDistritos();

}
