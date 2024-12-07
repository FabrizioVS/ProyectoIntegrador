package com.examenfinal.FabrizioValerSanchez.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@Table(name = "chat")
@EntityListeners(AuditingEntityListener.class)
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "idUsuario")
	private int idUsuario;
	@Column(name = "idPublicacion")
	private int idPublicacion;
	private String mensaje;
	private String fechachat;
	
	@ManyToOne
	@JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "idPublicacion", referencedColumnName = "id", insertable = false, updatable = false)
	private Publicacion publicacion;

} 
