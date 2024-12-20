package com.examenfinal.FabrizioValerSanchez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examenfinal.FabrizioValerSanchez.model.Chat;

public interface ChatRepository extends JpaRepository<Chat,Integer>{
	
	
	//Retora lista de chats asocaidas a una publicacion
	List<Chat> findByPublicacionId(int publicacionId);

}
