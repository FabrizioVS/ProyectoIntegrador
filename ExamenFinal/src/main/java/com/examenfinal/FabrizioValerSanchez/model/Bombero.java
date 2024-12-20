package com.examenfinal.FabrizioValerSanchez.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bombero")
public class Bombero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idDistrito", referencedColumnName = "id", insertable = false, updatable = false)
    private Distrito distrito;

    @Column(name = "url_direccion")
    private String urlDireccion;
    
    @Column(name = "url_imagen")
    private String imagenUrl;
}
