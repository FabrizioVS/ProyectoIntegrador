package com.examenfinal.FabrizioValerSanchez.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.examenfinal.FabrizioValerSanchez.model.Alumno;
import com.examenfinal.FabrizioValerSanchez.model.Usuario;

public interface AlumnoService {

	public ResponseEntity<Map<String, Object>> listarAlumnos();

	public ResponseEntity<Map<String, Object>> listarAlumnosPorId(Long id);

	public ResponseEntity<Map<String, Object>> agregarAlumnos(Alumno alumno);

	public ResponseEntity<Map<String, Object>> editarAlumnos(Alumno alumno, Long id);

	public ResponseEntity<Map<String, Object>> eliminarAlumnos(Long id);

	

}
