package com.examenfinal.FabrizioValerSanchez.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Chat;
import com.examenfinal.FabrizioValerSanchez.model.Publicacion;
import com.examenfinal.FabrizioValerSanchez.model.Usuario;
import com.examenfinal.FabrizioValerSanchez.repository.AlumnoRepository;
import com.examenfinal.FabrizioValerSanchez.repository.ChatRepository;
import com.examenfinal.FabrizioValerSanchez.repository.UsuarioRepository;
import com.examenfinal.FabrizioValerSanchez.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatDao;
	@Autowired
	private UsuarioRepository usuarioDao;
	@Autowired
	private AlumnoRepository alumnoDao;

	@Override
	public ResponseEntity<Map<String, Object>> crearChatPorIdPublicacion(int publicacionId,Chat chat) {
		Map<String, Object> res = new HashMap<>();

	    // Obtener el usuario actual desde la sesión
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();

	    // Obtener el usuario por correo
	    Optional<Usuario> usuarioOpt = usuarioDao.findByEmail(email).stream().findFirst();

	    // Si no encontramos al usuario, retornar error
	    if (!usuarioOpt.isPresent()) {
	        res.put("mensaje", "Usuario no encontrado");
	        res.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
	    }

	    // Obtener la publicación asociada al ID enviado (ID de la publicación pasado como parámetro)
	    Optional<Publicacion> publicacionOpt = alumnoDao.findById(publicacionId);

	    // Si la publicación no existe, retornar error
	    if (!publicacionOpt.isPresent()) {
	        res.put("mensaje", "Publicación no encontrada");
	        res.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
	    }

	    // Asociar la publicación y el usuario al chat
	    chat.setPublicacion(publicacionOpt.get());
	    chat.setUsuario(usuarioOpt.get()); // Asignar el usuario encontrado
	    LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fechaFormateada = fechaActual.format(formatter);
	    chat.setFechachat(fechaFormateada);
	    // Guardar el chat en la base de datos
	    chatDao.save(chat);

	    // Preparar la respuesta de éxito
	    res.put("mensaje", "Chat creado correctamente");
	    res.put("status", HttpStatus.CREATED);
	    res.put("fecha", new Date());
	    return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	@Override
	public ResponseEntity<Map<String, Object>> listarChatPorIdPublicacion(int publicacionId) {
	    Map<String, Object> res = new HashMap<>();

	    // Intentamos obtener la publicación con el id proporcionado
	    System.out.println("Buscando publicación con ID: " + publicacionId);
	    Optional<Publicacion> optPubliOptional = alumnoDao.findById(publicacionId);
	    
	    // Verificamos si la publicación existe
	    if (!optPubliOptional.isPresent()) {
	        System.out.println("Publicación no encontrada con ID: " + publicacionId);
	        res.put("message", "Publicación no encontrada.");
	        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND); 
	    }
	    
	    // Si la publicación existe, mostramos los detalles de la publicación
	    Publicacion publicacion = optPubliOptional.get();

	    // Buscamos los chats relacionados con la publicación
	    System.out.println("Buscando chats relacionados con la publicación ID: " + publicacionId);
	    List<Chat> chats = chatDao.findByPublicacionId(publicacionId);

	    // Verificamos si se encontraron chats
	    if (chats.isEmpty()) {
	        System.out.println("No se encontraron chats para la publicación con ID: " + publicacionId);
	        res.put("message", "No se encontraron chats para esta publicación.");
	        return new ResponseEntity<>(res, HttpStatus.NO_CONTENT); // Si no hay chats, devolvemos un 204
	    }

	    // Si se encontraron chats, mostramos la cantidad de chats y los detalles
	    System.out.println("Chats encontrados para la publicación con ID: " + publicacionId);
	    for (Chat chat : chats) {
	        System.out.println("Chat ID: " + chat.getId() + ", Mensaje: " + chat.getMensaje() + ", Fecha: " + chat.getFechachat());
	    }
	    List<Map<String, Object>> chatsJson = chats.stream().map(chat -> {
	        Map<String, Object> chatMap = new HashMap<>();
	        chatMap.put("id", chat.getId());
	        chatMap.put("mensaje", chat.getMensaje());
	        chatMap.put("fechachat", chat.getFechachat());
	        
	        // Incluir el usuario del chat
	        Map<String, Object> usuarioMap = new HashMap<>();
	        usuarioMap.put("idUsuario", chat.getUsuario().getIdUsuario());
	        usuarioMap.put("nombre", chat.getUsuario().getNombre());
	        usuarioMap.put("urlImg", chat.getUsuario().getUrlImg());
	        
	        chatMap.put("usuario", usuarioMap);  // Agregar usuario al chat
	        
	        return chatMap;
	    }).collect(Collectors.toList());

	    // Si se encontraron chats, devolvemos los datos
	    res.put("message", "Chats encontrados.");
	    res.put("chats", chatsJson);
	    return new ResponseEntity<>(res, HttpStatus.OK); // Si se encontraron chats, devolvemos un 200 OK
	}



}
