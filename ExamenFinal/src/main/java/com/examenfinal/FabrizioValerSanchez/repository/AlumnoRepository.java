package com.examenfinal.FabrizioValerSanchez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examenfinal.FabrizioValerSanchez.model.Chat;
import com.examenfinal.FabrizioValerSanchez.model.Publicacion;

public interface AlumnoRepository extends JpaRepository<Publicacion,Integer>{
	List<Publicacion> findByBul(String bul);
	List<Publicacion> findByBulAndEmergencia(String bul,String emergencia);
	List<Publicacion> findByBulAndIdDistritoAndEmergencia(String bul,Integer idDistrito,String emergencia);
	//Retora lista de Publicaciones asociadas a un usuario
		//List<Publicacion> findByUsuarioId(int usuarioId);
	//List<Publicacion>findById
	
}
