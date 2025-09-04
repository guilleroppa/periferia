package com.periferia.apirest.controller;

import com.periferia.apirest.model.Alumno;
import com.periferia.apirest.service.AlumnoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/alumnos")
public class AlumnoController {

    @Autowired
    AlumnoService alumnoInterface;

    @PostMapping(value = "/grabarAlumnos")
    public ResponseEntity<Alumno> grabarAlumno(@RequestBody Alumno alumno) {
        try {
            alumnoInterface.grabarAlumno(alumno);
            log.info("Alumno creado exitosamente con el id {}", alumno.getId());
            return ResponseEntity.ok().body(alumno);
        } catch (Exception e) {
            log.error("Error al crear el alumno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/listarAlumnosActivos")
    public ResponseEntity<List<Alumno>> listarProductos( boolean estado) {
        try {
            List<Alumno> lista = alumnoInterface.listarAlumnosActIna(estado);
            log.info("Lista retornada correcatemente");
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            log.error("Error al listar los alumnos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
