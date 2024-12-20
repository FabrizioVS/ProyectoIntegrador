package com.examenfinal.FabrizioValerSanchez.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idUsuario")
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String urlImg;
	@Column(name = "idDistrito")
	private int idDistrito;
	private String email;
	private String password;
	private String fecha;
	
	@ManyToOne
	@JoinColumn(name = "idDistrito", referencedColumnName = "id", insertable = false, updatable = false)
	private Distrito distrito;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Publicacion> publicaciones = new ArrayList<>();
	
}
