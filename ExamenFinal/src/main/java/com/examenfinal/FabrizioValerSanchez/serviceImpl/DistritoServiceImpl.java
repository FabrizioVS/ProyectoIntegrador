package com.examenfinal.FabrizioValerSanchez.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.examenfinal.FabrizioValerSanchez.model.Distrito;
import com.examenfinal.FabrizioValerSanchez.repository.DistritoRepository;
import com.examenfinal.FabrizioValerSanchez.service.DistritoService;

@Service
public class DistritoServiceImpl implements DistritoService {
	
	@Autowired
	private DistritoRepository dao;

	@Override
	public ResponseEntity<Map<String, Object>> listarDistritos() {
		Map<String, Object> res = new HashMap<>();
		List<Distrito> dis = dao.findAll();
		if (!dis.isEmpty()) {
			res.put("mensaje", "Lista de distritos");
			res.put("distritos", dis);
			res.put("status", HttpStatus.OK);
			res.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			res.put("mensaje", "No existen registros");
			res.put("status", HttpStatus.NOT_FOUND);
			res.put("fecha", new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}
	}

	


}
