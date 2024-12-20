package com.examenfinal.FabrizioValerSanchez.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examenfinal.FabrizioValerSanchez.model.Bombero;
import com.examenfinal.FabrizioValerSanchez.service.BomberoService;

@RestController
@RequestMapping("/api/bomberos")
public class BomberoController {

    @Autowired
    private BomberoService bomberoService;

    @GetMapping("/distrito/{idDistrito}")
    public List<Bombero> listarPorDistrito(@PathVariable int idDistrito) {
        return bomberoService.listarPorDistrito(idDistrito);
    }
}