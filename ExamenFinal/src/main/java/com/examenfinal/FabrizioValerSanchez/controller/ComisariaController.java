package com.examenfinal.FabrizioValerSanchez.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examenfinal.FabrizioValerSanchez.model.Comisaria;
import com.examenfinal.FabrizioValerSanchez.service.ComisariaService;

@RestController
@RequestMapping("/api/comisarias")
public class ComisariaController {

    @Autowired
    private ComisariaService comisariaService;

    @GetMapping("/distrito/{idDistrito}")
    public List<Comisaria> listarPorDistrito(@PathVariable int idDistrito) {
        return comisariaService.listarPorDistrito(idDistrito);
    }
}