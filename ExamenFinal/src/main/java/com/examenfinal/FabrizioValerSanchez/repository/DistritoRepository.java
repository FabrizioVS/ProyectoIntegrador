package com.examenfinal.FabrizioValerSanchez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examenfinal.FabrizioValerSanchez.model.Distrito;

@Repository
public interface DistritoRepository  extends JpaRepository<Distrito, Integer>{
	// Se agrega
	List<Distrito> findAll();
	
}
