package com.periferia.apirest.controller;

import com.periferia.apirest.model.Alumno;
import com.periferia.apirest.model.AlumnoDTO;
import com.periferia.apirest.service.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/alumnos")
@Tag(name = "Alumnos", description = "Operaciones relacionadas con alumnos")
public class AlumnoController {

    @Autowired
    AlumnoService alumnoInterface;

    @Operation(summary = "Grabar un alumno")
    @PostMapping(value = "/grabarAlumnos")
    public ResponseEntity<Alumno> grabarAlumno(@RequestBody Alumno alumno) {
        try {
            alumnoInterface.grabarAlumno(alumno);
            log.info("Alumno creado exitosamente con el id {}", alumno.getId());
            return ResponseEntity.ok().body(new Alumno());
        } catch (Exception e) {
            log.error("Error al crear el alumno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Listar alumnos activos/inactivos")
    @GetMapping(value = "/listarAlumnosActivos")
    public ResponseEntity<List<Alumno>> listarProductos(boolean estado) {
        try {
            List<Alumno> lista = alumnoInterface.listarAlumnosActIna(estado);
            log.info("Lista retornada correcatemente");
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            log.error("Error al listar los alumnos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Buscar un alumno")
    @GetMapping(value = "/buscarAlumno", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlumnoDTO> buscarAlumno(@RequestParam int id) {
        try {
            if (id <= 0) {
                log.warn("Código inválido: {}", id);
                return ResponseEntity.badRequest().build();
            }

            Alumno alumno = alumnoInterface.buscarAlumno((long) id);
            if (alumno == null) {
                log.warn("No se encontró alumno con el código {}", id);
                return ResponseEntity.notFound().build();
            }

            AlumnoDTO dto = new AlumnoDTO(
                    alumno.getId(),
                    alumno.getNombre(),
                    alumno.getApellido(),
                    alumno.isEstado(),
                    alumno.getEdad()
            );

            log.info("Alumno encontrado con el id {}", alumno.getId());
            return ResponseEntity.ok().body(dto);

        } catch (Exception e) {
            log.error("Error al encontrar el alumno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
