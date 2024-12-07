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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Distrito;
import com.examenfinal.FabrizioValerSanchez.model.Usuario;
import com.examenfinal.FabrizioValerSanchez.repository.DistritoRepository;
import com.examenfinal.FabrizioValerSanchez.repository.UsuarioRepository;
import com.examenfinal.FabrizioValerSanchez.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioDao;
	@Autowired
	private DistritoRepository disDao;

	@Override
	public ResponseEntity<Map<String, Object>> crearUsuario(Usuario usuario) {
		Map<String, Object> res = new HashMap<>();
		Optional<Distrito>disOpt = disDao.findById(usuario.getIdDistrito());
		if(disOpt.isPresent()) {
			usuario.setDistrito(disOpt.get());
		}else {
			res.put("mensaje", "el distrito no existe");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}
		LocalDateTime fechaActual = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String fechaFormateada = fechaActual.format(formatter);

		usuario.setFecha(fechaFormateada);

		if (usuario.getPassword() != null) {
			String contraEncriptada = new BCryptPasswordEncoder().encode(usuario.getPassword());
			usuario.setPassword(contraEncriptada);
		}
		usuarioDao.save(usuario);
		res.put("usuarios", usuario);
		res.put("mensaje", "Se añadio correctamente el usuario");
		res.put("status", HttpStatus.CREATED);
		res.put("fecha", new Date());
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarUsuarios() {
		Map<String, Object> res = new HashMap<>();
		List<Usuario> usus = usuarioDao.findAll();
		if (!usus.isEmpty()) {
			res.put("mensaje", "Lista de usuarios");
			res.put("usuarios", usus);
			res.put("status", HttpStatus.OK);
			res.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			res.put("mensaje", "No existen registros");
			res.put("status", HttpStatus.NOT_FOUND);
			res.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}
	}


}
