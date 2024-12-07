package com.examenfinal.FabrizioValerSanchez.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.examenfinal.FabrizioValerSanchez.model.Publicacion;
import com.examenfinal.FabrizioValerSanchez.model.Usuario;

public interface AlumnoService {

	public ResponseEntity<Map<String, Object>> listarAlumnos();

	public ResponseEntity<Map<String, Object>> listarAlumnosPorId(int id);

	public ResponseEntity<Map<String, Object>> agregarAlumnos(Publicacion alumno);

	public ResponseEntity<Map<String, Object>> editarAlumnos(Publicacion alumno, int id);

	public ResponseEntity<Map<String, Object>> eliminarAlumnos(int id);

	

}
