package com.examenfinal.FabrizioValerSanchez.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Bombero;
import com.examenfinal.FabrizioValerSanchez.repository.BomberoRepository;
import com.examenfinal.FabrizioValerSanchez.service.BomberoService;

@Service
public class BomberoServiceImpl implements BomberoService {

    @Autowired
    private BomberoRepository bomberoRepository;

    @Override
    public List<Bombero> listarPorDistrito(int idDistrito) {
        return bomberoRepository.findByDistritoId(idDistrito);
    }
}