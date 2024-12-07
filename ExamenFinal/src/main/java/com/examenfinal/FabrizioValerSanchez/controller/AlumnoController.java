package com.examenfinal.FabrizioValerSanchez.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examenfinal.FabrizioValerSanchez.model.Publicacion;
import com.examenfinal.FabrizioValerSanchez.service.AlumnoService;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController  {
	
	@Autowired
	private AlumnoService service;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> listarAlumnos() {
		// TODO Auto-generated method stub
		return service.listarAlumnos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> listarAlumnosPorId(@PathVariable int id) {
		// TODO Auto-generated method stub
		return service.listarAlumnosPorId(id);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> agregarAlumnos(@Validated @RequestBody Publicacion alumno) {
		// TODO Auto-generated method stub
		return service.agregarAlumnos(alumno);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> editarAlumnos(@RequestBody Publicacion alumno, @PathVariable int id) {
		// TODO Auto-generated method stub
		return service.editarAlumnos(alumno, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminarAlumnos(@PathVariable int id) {
		// TODO Auto-generated method stub
		return service.eliminarAlumnos(id);
	}

}
