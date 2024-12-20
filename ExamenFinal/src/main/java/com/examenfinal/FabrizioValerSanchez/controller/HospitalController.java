package com.examenfinal.FabrizioValerSanchez.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examenfinal.FabrizioValerSanchez.model.Hospital;
import com.examenfinal.FabrizioValerSanchez.service.HospitalService;

@RestController
@RequestMapping("/api/hospitales")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/distrito/{idDistrito}")
    public List<Hospital> listarPorDistrito(@PathVariable int idDistrito) {
        return hospitalService.listarPorDistrito(idDistrito);
    }
}
