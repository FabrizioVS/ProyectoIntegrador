package com.examenfinal.FabrizioValerSanchez.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Hospital;
import com.examenfinal.FabrizioValerSanchez.repository.HospitalRepository;
import com.examenfinal.FabrizioValerSanchez.service.HospitalService;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public List<Hospital> listarPorDistrito(int idDistrito) {
        return hospitalRepository.findByDistritoId(idDistrito);
    }
}
