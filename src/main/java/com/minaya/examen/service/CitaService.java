package com.minaya.examen.service;

import com.minaya.examen.model.Cita;
import com.minaya.examen.model.Medico;
import com.minaya.examen.model.Paciente;
import com.minaya.examen.repository.CitaRepository;
import com.minaya.examen.repository.MedicoRepository;
import com.minaya.examen.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaService(CitaRepository citaRepository,
                       PacienteRepository pacienteRepository,
                       MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Transactional(readOnly = true)
    public List<Cita> listarParaConsulta() {
        return citaRepository.findAllByOrderByFechaDesc();
    }

    @Transactional(readOnly = true)
    public Cita obtenerParaEdicion(Integer idCita) {
        return citaRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada: " + idCita));
    }

    public Cita actualizarDesdeMantenimiento(Integer idCita, Cita datos) {
        Cita existente = citaRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada: " + idCita));

        existente.setFecha(datos.getFecha());
        existente.setCosto(datos.getCosto());

        Integer idPaciente = (datos.getPaciente() != null) ? datos.getPaciente().getIdPaciente() : null;
        Integer idMedico   = (datos.getMedico() != null) ? datos.getMedico().getIdMedico() : null;

        if (idPaciente == null || idMedico == null) {
            throw new RuntimeException("Debe seleccionar paciente y médico.");
        }

        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado: " + idPaciente));

        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado: " + idMedico));

        existente.setPaciente(paciente);
        existente.setMedico(medico);

        return citaRepository.save(existente);
    }
}
