package com.periferia.apirest;

import com.periferia.apirest.model.Alumno;
import com.periferia.apirest.repository.AlumnosRepository;
import com.periferia.apirest.service.AlumnoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlumnoServiceTest {

    @Mock
    private AlumnosRepository alumnosRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

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

}