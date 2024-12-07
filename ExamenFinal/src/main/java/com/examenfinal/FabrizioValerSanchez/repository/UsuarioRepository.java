package com.examenfinal.FabrizioValerSanchez.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examenfinal.FabrizioValerSanchez.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findOneByEmail(String email);

	List<Usuario> findByNombre(String nombre);
	List<Usuario> findByEmail(String email);
}
