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

import com.examenfinal.FabrizioValerSanchez.model.Chat;
import com.examenfinal.FabrizioValerSanchez.model.Publicacion;
import com.examenfinal.FabrizioValerSanchez.service.AlumnoService;
import com.examenfinal.FabrizioValerSanchez.service.ChatService;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {
	
	@Autowired
	private AlumnoService service;
	@Autowired
	private ChatService chatservice;
	
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

	@PostMapping("")
	public ResponseEntity<Map<String, Object>> agregarAlumnos (@RequestBody Publicacion alumno) {
		// TODO Auto-generated method stub
		return service.agregarPublicacionDentroDeUnUsuarioLogeado(alumno);
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

	@PostMapping("{publicacionId}/chat")
	public ResponseEntity<Map<String, Object>> crearChat(@PathVariable int publicacionId, @RequestBody Chat chat) {
		// TODO Auto-generated method stub
		return chatservice.crearChatPorIdPublicacion(publicacionId, chat);
	}
	@GetMapping("{publicacionId}/chat")
	public ResponseEntity<Map<String, Object>> listarChatPorIdPublicacionWA(@PathVariable(required = false) int publicacionId) {
		// TODO Auto-generated method stub
		return chatservice.listarChatPorIdPublicacion(publicacionId);
	}

	@GetMapping("{IdDistrito}/{emergencia}/waa")
	public ResponseEntity<Map<String, Object>> listarPublicacionPorBulAndDistrito(@PathVariable Integer IdDistrito,@PathVariable String emergencia) {
		// TODO Auto-generated method stub
		return service.listarPublicacionPorBulAndDistritoAndEmergencia(IdDistrito,emergencia);
	}

}
