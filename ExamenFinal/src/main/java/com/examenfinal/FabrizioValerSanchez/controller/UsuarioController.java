package com.examenfinal.FabrizioValerSanchez.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examenfinal.FabrizioValerSanchez.model.Usuario;
import com.examenfinal.FabrizioValerSanchez.service.AlumnoService;
import com.examenfinal.FabrizioValerSanchez.service.DistritoService;
import com.examenfinal.FabrizioValerSanchez.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private DistritoService serv;

	@GetMapping("/listar")
	public ResponseEntity<Map<String, Object>> listarUsuarios() {
		// TODO Auto-generated method stub
		return service.listarUsuarios();
	}

	@PostMapping("/registrar")
	public ResponseEntity<Map<String, Object>> agregarUsuarios(@Validated @RequestBody Usuario usuario) {
		// TODO Auto-generated method stub
		return service.crearUsuario(usuario);
	}

	@GetMapping("/dis")
	public ResponseEntity<Map<String, Object>> listarDistritos() {
		// TODO Auto-generated method stub
		return serv.listarDistritos();
	}

	@GetMapping("/actual")
	public ResponseEntity<Map<String, Object>> listarUsuarioActualwaa(@Validated @RequestBody(required = false) Usuario usuario) {
		// TODO Auto-generated method stub
		return service.listarUsuarioActual(usuario);
}
}
