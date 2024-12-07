package com.examenfinal.FabrizioValerSanchez.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@Table(name = "publicacion")
@EntityListeners(AuditingEntityListener.class)
public class Publicacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	private String descripcion;
	private String img;
	private String direccion;
	private String estado;
	private String nombreusu;
	private String fecha;
}
