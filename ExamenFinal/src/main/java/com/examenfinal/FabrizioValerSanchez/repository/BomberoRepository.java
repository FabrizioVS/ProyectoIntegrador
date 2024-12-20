package com.examenfinal.FabrizioValerSanchez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examenfinal.FabrizioValerSanchez.model.Bombero;

@Repository
public interface BomberoRepository extends JpaRepository<Bombero, Integer> {

    @Query("SELECT b FROM Bombero b WHERE b.distrito.id = :idDistrito")
    List<Bombero> findByDistritoId(int idDistrito);
}
