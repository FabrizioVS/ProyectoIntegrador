package com.examenfinal.FabrizioValerSanchez.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@Table(name = "alumno")
@EntityListeners(AuditingEntityListener.class)
public class Alumno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String nombre;
	private String apellido;
	private String dni;
	private int ciclo;
	private String estado;
	private String nombreusu;
	private String fecha;
}
