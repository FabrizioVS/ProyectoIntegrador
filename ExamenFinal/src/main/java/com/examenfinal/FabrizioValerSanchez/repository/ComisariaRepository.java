package com.examenfinal.FabrizioValerSanchez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examenfinal.FabrizioValerSanchez.model.Comisaria;

@Repository
public interface ComisariaRepository extends JpaRepository<Comisaria, Integer> {

    @Query("SELECT c FROM Comisaria c WHERE c.distrito.id = :idDistrito")
    List<Comisaria> findByDistritoId(int idDistrito);
}