package com.minaya.examen.service;

import com.minaya.examen.model.Medico;
import com.minaya.examen.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Transactional(readOnly = true)
    public List<Medico> listar() {
        return medicoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Medico obtenerPorId(Integer id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado: " + id));
    }

    public Medico registrar(Medico medico) {
        return medicoRepository.save(medico);
    }

    public Medico actualizar(Integer id, Medico medico) {
        Medico existente = obtenerPorId(id);
        existente.setNombres(medico.getNombres());
        existente.setEspecialidad(medico.getEspecialidad());
        return medicoRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Medico existente = obtenerPorId(id);
        medicoRepository.delete(existente);
    }
}
