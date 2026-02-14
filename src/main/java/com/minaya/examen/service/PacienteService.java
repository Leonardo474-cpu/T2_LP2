package com.minaya.examen.service;

import com.minaya.examen.model.Paciente;
import com.minaya.examen.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional(readOnly = true)
    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Paciente obtenerPorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado: " + id));
    }

    public Paciente registrar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizar(Integer id, Paciente paciente) {
        Paciente existente = obtenerPorId(id);
        existente.setNombres(paciente.getNombres());
        existente.setDni(paciente.getDni());
        existente.setTelefono(paciente.getTelefono());
        return pacienteRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Paciente existente = obtenerPorId(id);
        pacienteRepository.delete(existente);
    }

    @Transactional(readOnly = true)
    public Paciente obtenerPorDni(String dni) {
        return pacienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dni));
    }
}
