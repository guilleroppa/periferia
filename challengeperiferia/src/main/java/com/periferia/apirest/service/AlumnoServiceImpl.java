package com.periferia.apirest.service;

import com.periferia.apirest.model.Alumno;
import com.periferia.apirest.repository.AlumnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements AlumnoInterface{

    @Autowired
    AlumnosRepository alumnosRepository;

    @Override
    public void grabarAlumno(Alumno alumno) {
        alumnosRepository.save(alumno);
    }

    @Override
    public List<Alumno> listarAlumnos() {
        return alumnosRepository.findAll();
    }

    @Override
    public List<Alumno> listarAlumnosActIna(boolean condicion) {
        List<Alumno> lista = alumnosRepository.findAll();
        return lista.stream()
                .filter(alumno -> alumno.isEstado()== condicion)
                .collect(Collectors.toList());
    }

    @Override
    public Alumno buscarAlumno(Long id) {
        return alumnosRepository.getReferenceById(id);

    }

}
