package com.examenfinal.FabrizioValerSanchez.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Chat;
import com.examenfinal.FabrizioValerSanchez.model.Distrito;
import com.examenfinal.FabrizioValerSanchez.model.Publicacion;
import com.examenfinal.FabrizioValerSanchez.model.Usuario;
import com.examenfinal.FabrizioValerSanchez.repository.AlumnoRepository;
import com.examenfinal.FabrizioValerSanchez.repository.ChatRepository;
import com.examenfinal.FabrizioValerSanchez.repository.DistritoRepository;
import com.examenfinal.FabrizioValerSanchez.repository.UsuarioRepository;
import com.examenfinal.FabrizioValerSanchez.service.AlumnoService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private AlumnoRepository alumnoDao;
	@Autowired
	private UsuarioRepository usuarioDao;
	@Autowired
	private ChatRepository chatDao;
	@Autowired
	private DistritoRepository distDao;

	@Override
	public ResponseEntity<Map<String, Object>> listarAlumnos() {
		Map<String, Object> res = new HashMap<>();
		List<Publicacion> alums = alumnoDao.findByBul("A");
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
	public ResponseEntity<Map<String, Object>> listarPublicacionPorBulAndDistritoAndEmergencia(Integer IdDistrito,String emergencia) {
	    Map<String, Object> res = new HashMap<>();

	    // Validar si IdDistrito es nulo
	    if (IdDistrito == null) {
	        res.put("mensaje", "El IdDistrito no puede ser nulo");
	        res.put("status", HttpStatus.BAD_REQUEST);
	        res.put("fecha", new Date());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	    }
	    
	    if(IdDistrito == 0) {
	    	List<Publicacion> alums = alumnoDao.findByBulAndEmergencia("A",emergencia);
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
	    // Verificar si el distrito existe
	    Optional<Distrito> distritoOPT = distDao.findById(IdDistrito);
	    if (!distritoOPT.isPresent()) {
	        res.put("mensaje", "No existe ese distrito");
	        res.put("status", HttpStatus.NOT_FOUND);
	        res.put("fecha", new Date());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
	    }

	    // Buscar publicaciones
	    List<Publicacion> alums = alumnoDao.findByBulAndIdDistritoAndEmergencia("A", IdDistrito,emergencia);
	    ArrayList<String> wa = new ArrayList<String>();
	    if (alums.isEmpty()) {
	        res.put("mensaje", "No existen registros");
	        res.put("status", HttpStatus.NOT_FOUND);
	        res.put("fecha", new Date());
	        res.put("alumnos",wa);
	        return ResponseEntity.status(HttpStatus.OK).body(res);
	    }

	    // Respuesta exitosa
	    res.put("mensaje", "Lista de publicaciones");
	    res.put("alumnos", alums);
	    res.put("status", HttpStatus.OK);
	    res.put("fecha", new Date());
	    return ResponseEntity.status(HttpStatus.OK).body(res);
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
	public ResponseEntity<Map<String, Object>> agregarPublicacionDentroDeUnUsuarioLogeado(Publicacion alumno) {
		Map<String, Object> res = new HashMap<>();

		// Obtener el usuario actual
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		// Obtener el usuario por correo
		Optional<Usuario> usuarioOpt = usuarioDao.findByEmail(email).stream().findFirst();
		
		Optional<Distrito>disOpt = distDao.findById(alumno.getIdDistrito());
		if(disOpt.isPresent()) {
			alumno.setDistrito(disOpt.get());
		}else {
			res.put("mensaje", "el distrito no existe");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}
		Usuario usuActualLogeado = usuarioOpt.orElse(null);
		int IdActual = usuActualLogeado.getIdUsuario();
		if (!usuarioOpt.isPresent()) {
			res.put("mensaje", "Usuario no encontrado");
			res.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}
		
		Optional<Usuario> usuOPT = usuarioDao.findById(IdActual);
		Usuario usu = usuOPT.orElse(null);
				

		if (!usuOPT.isPresent()) {
			res.put("mensaje", "Usuario no encontrado");
			res.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}
		alumno.setUsuario(usuOPT.get());
		LocalDateTime fechaActual = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String fechaFormateada = fechaActual.format(formatter);
		alumno.setFecha(fechaFormateada);
		alumno.setBul("A");
		alumno.setUsuario(usu);
		alumno.setNombreUsuario(usu.getNombre() + " " + usu.getApellido());
		alumno.setUrlImg(usu.getUrlImg());
		alumno.setIdUsuarioP(usu.getIdUsuario());

		alumnoDao.save(alumno);
		res.put("alumnos", alumno);
		res.put("mensaje", "Registro Agregado");
		res.put("status", HttpStatus.CREATED);
		res.put("fecha", new Date());
		return ResponseEntity.status(HttpStatus.CREATED).body(res);

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
			alumno.setFecha(fechaFormateada);
			alumno.setIdDistrito(a.getIdDistrito());
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

			// Actualizamos el campo 'bul' a 'S' para marcar la publicación como eliminada
			producto.setBul("S"); // Asumimos que 'bul' es el campo de eliminación lógica

			// Guardamos la entidad con el campo 'bul' actualizado
			alumnoDao.save(producto);

			res.put("mensaje", "Eliminación lógica exitosa, valor 'bul' actualizado a 'S'");
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
