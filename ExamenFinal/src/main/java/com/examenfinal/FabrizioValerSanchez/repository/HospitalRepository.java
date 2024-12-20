package com.examenfinal.FabrizioValerSanchez.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examenfinal.FabrizioValerSanchez.model.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    @Query("SELECT h FROM Hospital h WHERE h.distrito.id = :idDistrito")
    List<Hospital> findByDistritoId(int idDistrito);

}
