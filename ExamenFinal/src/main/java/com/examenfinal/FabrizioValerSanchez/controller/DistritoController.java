package com.examenfinal.FabrizioValerSanchez.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examenfinal.FabrizioValerSanchez.model.Distrito;
import com.examenfinal.FabrizioValerSanchez.service.DistritoService;

@RestController
@RequestMapping("/api/distritos")
public class DistritoController {

    @Autowired
    private DistritoService distritoService;

    @GetMapping
    public List<Distrito> obtenerDistritos() {
        return distritoService.obtenerTodos();
    }
}
