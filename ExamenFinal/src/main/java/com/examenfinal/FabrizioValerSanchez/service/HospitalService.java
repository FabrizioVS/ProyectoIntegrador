package com.examenfinal.FabrizioValerSanchez.service;

import java.util.List;

import com.examenfinal.FabrizioValerSanchez.model.Hospital;

public interface HospitalService {
    List<Hospital> listarPorDistrito(int idDistrito);
}
