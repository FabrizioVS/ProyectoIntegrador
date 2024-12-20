package com.examenfinal.FabrizioValerSanchez.service;

import java.util.List;

import com.examenfinal.FabrizioValerSanchez.model.Comisaria;

public interface ComisariaService {
    List<Comisaria> listarPorDistrito(int idDistrito);
}