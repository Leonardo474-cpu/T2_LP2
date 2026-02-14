package com.minaya.examen.repository;

import com.minaya.examen.model.Cita;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @EntityGraph(attributePaths = {"paciente", "medico"})
    List<Cita> findAllByOrderByFechaDesc();
    
    @Override
    @EntityGraph(attributePaths = {"paciente", "medico"})
    Optional<Cita> findById(Integer id);
}
