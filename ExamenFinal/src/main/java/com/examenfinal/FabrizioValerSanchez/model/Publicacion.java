package com.examenfinal.FabrizioValerSanchez.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private String fecha;
    private int idUsuarioP;
    private String nombreUsuario;
    @Column(name = "idDistrito")
	private int idDistrito;
    private String bul;
    private String emergencia;
    private String urlImg;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
	@JoinColumn(name = "idDistrito", referencedColumnName = "id", insertable = false, updatable = false)
	private Distrito distrito;
    
    @OneToMany(mappedBy = "publicacion", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Chat> chats = new ArrayList<>();

}

