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
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Publicacion;
import com.examenfinal.FabrizioValerSanchez.model.Usuario;
import com.examenfinal.FabrizioValerSanchez.repository.AlumnoRepository;
import com.examenfinal.FabrizioValerSanchez.repository.UsuarioRepository;
import com.examenfinal.FabrizioValerSanchez.service.AlumnoService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private AlumnoRepository alumnoDao;
	@Autowired
	private UsuarioRepository usuarioDao;

	
	@Override
	public ResponseEntity<Map<String, Object>> listarAlumnos() {
		Map<String, Object> res = new HashMap<>();
		List<Publicacion> alums = alumnoDao.findAll();
		if (!alums.isEmpty()) {
			res.put("mensaje", "Lista de alumnos");
			res.put("alumnos", alums);
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

	@Override
	public ResponseEntity<Map<String, Object>> listarAlumnosPorId(int id) {
		Map<String, Object> res = new HashMap<>();
		Optional<Publicacion> alums = alumnoDao.findById(id);

		if (alums.isPresent()) {
			res.put("alumnos", alums);
			res.put("mensaje", "Busqueda correcta");
			res.put("status", HttpStatus.OK);
			res.put("fecha", new Date());
			return ResponseEntity.ok().body(res);
		} else {
			res.put("mensaje", "Sin registros con ID: " + id);
			res.put("status", HttpStatus.NOT_FOUND);
			res.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> agregarAlumnos(Publicacion alumno) {
	    Map<String, Object> respuesta = new HashMap<>();
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    
	    
	    List<Usuario> idUsu = usuarioDao.findByEmail(email);
	    
	   
	    if (!idUsu.isEmpty()) {
	        Usuario primerUsuario = idUsu.get(0);
	        System.out.println("print: " + primerUsuario.getIdUsuario());
	        
	        alumno.setNombreusu(primerUsuario.getNombre());
	        LocalDateTime fechaActual = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String fechaFormateada = fechaActual.format(formatter);
	        
	        alumno.setFecha(fechaFormateada);
	        
	        
	        System.out.println("Método agregarAlumnos llamado por el usuario: " + email + ", ID: " + primerUsuario.getIdUsuario());

	       
	        alumnoDao.save(alumno); 
	        
	        
	        respuesta.put("alumnos", alumno);
	        respuesta.put("mensaje", "Registro Agregado");
	        respuesta.put("status", HttpStatus.CREATED);
	        respuesta.put("fecha", new Date());
			
	    }
		return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
	
	}
	@Override
	public ResponseEntity<Map<String, Object>> editarAlumnos(Publicacion a, int id) {
	    
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    List<Usuario> idUsu = usuarioDao.findByEmail(email);
	    
	  
	    if (idUsu.isEmpty()) {
	        Map<String, Object> respuesta = new HashMap<>();
	        respuesta.put("mensaje", "Usuario no encontrado");
	        respuesta.put("status", HttpStatus.NOT_FOUND);
	        respuesta.put("fecha", new Date());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	    }

	    Usuario primerUsuario = idUsu.get(0);
	  

	    
	    LocalDateTime fechaActual = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String fechaFormateada = fechaActual.format(formatter);
	    a.setFecha(fechaFormateada);
	    
	    Map<String, Object> respuesta = new HashMap<>();
	    Optional<Publicacion> aluExist = alumnoDao.findById(id);

	    if (aluExist.isPresent()) {
	        Publicacion alumno = aluExist.get();
	        alumno.setDescripcion(a.getDescripcion());
	        alumno.setImg(a.getImg());
	        alumno.setDireccion(a.getDireccion());
	        alumno.setEstado(a.getEstado());
	        alumno.setNombreusu(primerUsuario.getNombre());
	        alumno.setFecha(fechaFormateada);
	        alumnoDao.save(alumno);
	        respuesta.put("alumnos", alumno);
	        respuesta.put("mensaje", "Datos del alumno modificados con éxito");
	        respuesta.put("status", HttpStatus.OK); 
	        respuesta.put("fecha", new Date());
	        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
	    } else {
	        respuesta.put("mensaje", "Sin registros con ID: " + id);
	        respuesta.put("status", HttpStatus.NOT_FOUND);
	        respuesta.put("fecha", new Date());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	    }
	}


	@Override
	public ResponseEntity<Map<String, Object>> eliminarAlumnos(int id) {
		Map<String, Object> res = new HashMap<>();
		Optional<Publicacion> aluExist = alumnoDao.findById(id);
		if (aluExist.isPresent()) {
			Publicacion producto = aluExist.get();
			alumnoDao.delete(producto);
			res.put("mensaje", "Eliminado correctamente");
			res.put("status", HttpStatus.NO_CONTENT);
			res.put("fecha", new Date());

			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(res);
		} else {
			res.put("mensaje", "Sin registros con ID: " + id);
			res.put("status", HttpStatus.NOT_FOUND);
			res.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}
	}


}
