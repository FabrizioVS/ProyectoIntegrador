package com.examenfinal.FabrizioValerSanchez.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.examenfinal.FabrizioValerSanchez.model.Chat;

public interface ChatService {
	public ResponseEntity<Map<String, Object>> crearChatPorIdPublicacion(int publicacionId, Chat chat);

	public ResponseEntity<Map<String, Object>> listarChatPorIdPublicacion(int publicacionId);

}
