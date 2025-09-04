package com.periferia.apirest.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private boolean estado;
    private int  edad;

    public Alumno() {
    }

}
