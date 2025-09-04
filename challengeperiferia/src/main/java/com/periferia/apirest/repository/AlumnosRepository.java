package com.periferia.apirest.repository;

import com.periferia.apirest.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnosRepository extends JpaRepository<Alumno, Long> {


}
