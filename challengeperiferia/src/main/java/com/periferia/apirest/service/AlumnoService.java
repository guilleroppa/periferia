package com.periferia.apirest.service;

import com.periferia.apirest.model.Alumno;
import java.util.List;

public interface AlumnoService {

    void grabarAlumno(Alumno alumno);
    List<Alumno> listarAlumnos();
    List<Alumno> listarAlumnosActIna(boolean condicion);
    Alumno buscarAlumno(Long id);

}
