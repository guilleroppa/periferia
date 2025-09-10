package com.periferia.apirest;

import com.periferia.apirest.controller.AlumnoController;
import com.periferia.apirest.model.Alumno;
import com.periferia.apirest.model.AlumnoDTO;
import com.periferia.apirest.repository.AlumnosRepository;
import com.periferia.apirest.service.AlumnoService;
import com.periferia.apirest.service.AlumnoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlumnoServiceTest {

    @Mock
    private AlumnosRepository alumnosRepository;

    @Mock
    private AlumnoService alumnoInterface;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGrabarAlumno() {
        Alumno alumno1 = new Alumno("Juan", "Perez", true, 12);
        Alumno alumno2 = new Alumno("María", "Perez", true, 12);

        alumnoService.grabarAlumno(alumno1);
        alumnoService.grabarAlumno(alumno2);

        ArgumentCaptor<Alumno> captor = ArgumentCaptor.forClass(Alumno.class);
        verify(alumnosRepository, times(2)).save(captor.capture());

        List<Alumno> capturados = captor.getAllValues();
        assertTrue(capturados.contains(alumno1));
        assertTrue(capturados.contains(alumno2));
    }

    @Test
    void testListarAlumnosActivos() {
        // Arrange
        Alumno activo1 = new Alumno("Juan", "Perez", true, 12);
        Alumno activo2 = new Alumno("María", "Perez", true, 12);
        Alumno inactivo = new Alumno("Pedro", "Rios", false, 12);

        when(alumnosRepository.findAll()).thenReturn(Arrays.asList(activo1, activo2, inactivo));

        // Act
        List<Alumno> resultado = alumnoService.listarAlumnosActIna(true);

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(activo1));
        assertTrue(resultado.contains(activo2));
        assertFalse(resultado.contains(inactivo));
    }

    @Test
    void testListarAlumnosInactivos() {
        Alumno activo = new Alumno("Ana", "Lopez", true, 15);
        Alumno inactivo1 = new Alumno("Carlos", "Ramírez", false, 16);
        Alumno inactivo2 = new Alumno("Sofía", "Torres", false, 17);

        when(alumnosRepository.findAll()).thenReturn(Arrays.asList(activo, inactivo1, inactivo2));

        List<Alumno> resultado = alumnoService.listarAlumnosActIna(false);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(inactivo1));
        assertTrue(resultado.contains(inactivo2));
        assertFalse(resultado.contains(activo));
    }

    @Test
    void testBuscarAlumnoPorId() {
        // Arrange
        Alumno alumno = new Alumno("Lucía", "Gómez", true, 18);
        Long id = 1L;

        when(alumnosRepository.getReferenceById(id)).thenReturn(alumno);

        // Act
        Alumno resultado = alumnoService.buscarAlumno(id);

        // Assert
        assertNotNull(resultado);
        assertEquals("Lucía", resultado.getNombre());
        assertEquals("Gómez", resultado.getApellido());
        assertEquals(18, resultado.getEdad());
        assertTrue(resultado.isEstado());
    }

    @Test
    void buscarAlumno_deberiaRetornarNotFound_siNoExiste() {
        Mockito.<Optional<Alumno>>when(Optional.ofNullable(alumnoService.buscarAlumno(999L)))
                .thenReturn(Optional.empty());

        ResponseEntity<AlumnoDTO> response = alumnoController.buscarAlumno(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void buscarAlumno_deberiaRetornarBadRequest_siIdInvalido() {
        ResponseEntity<AlumnoDTO> response = alumnoController.buscarAlumno(-5);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}