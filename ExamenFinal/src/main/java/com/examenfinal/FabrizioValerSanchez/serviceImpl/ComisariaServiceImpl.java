package com.examenfinal.FabrizioValerSanchez.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Comisaria;
import com.examenfinal.FabrizioValerSanchez.repository.ComisariaRepository;
import com.examenfinal.FabrizioValerSanchez.service.ComisariaService;

@Service
public class ComisariaServiceImpl implements ComisariaService {

    @Autowired
    private ComisariaRepository comisariaRepository;

    @Override
    public List<Comisaria> listarPorDistrito(int idDistrito) {
        return comisariaRepository.findByDistritoId(idDistrito);
    }
}