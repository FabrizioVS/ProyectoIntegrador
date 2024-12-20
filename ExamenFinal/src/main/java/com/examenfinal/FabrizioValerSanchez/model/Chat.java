package com.examenfinal.FabrizioValerSanchez.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "chat")
@EntityListeners(AuditingEntityListener.class)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mensaje;
    private String fechachat;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "publicacion_id", nullable = false)
    @JsonBackReference
    private Publicacion publicacion;

	@Override
	public String toString() {
		return "Chat [id=" + id + ", mensaje=" + mensaje + ", fechachat=" + fechachat + "]";
	}
    
    
}

