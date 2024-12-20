package com.examenfinal.FabrizioValerSanchez.service;

import java.util.List;

import com.examenfinal.FabrizioValerSanchez.model.Bombero;

public interface BomberoService {
    List<Bombero> listarPorDistrito(int idDistrito);
}
