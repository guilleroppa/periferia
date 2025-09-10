package com.periferia.apirest.model;

import lombok.Data;

@Data
public class AlumnoDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private boolean estado;
    private int edad;

    public AlumnoDTO(Long id, String nombre,  String apellido, boolean estado, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
        this.edad = edad;
    }
}
