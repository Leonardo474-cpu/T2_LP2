package com.minaya.examen.controller;

import com.minaya.examen.model.Cita;
import com.minaya.examen.model.Medico;
import com.minaya.examen.model.Paciente;
import com.minaya.examen.service.CitaService;
import com.minaya.examen.service.MedicoService;
import com.minaya.examen.service.PacienteService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;

    public CitaController(CitaService citaService,
                          PacienteService pacienteService,
                          MedicoService medicoService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
    }

    @GetMapping({"", "/", "/consulta"})
    public String consultaCitas(Model model,
                                @ModelAttribute("mensajeOk") String mensajeOk,
                                @ModelAttribute("mensajeError") String mensajeError) {

        model.addAttribute("citas", citaService.listarParaConsulta());
        return "consultaCita";
    }

    @GetMapping("/editar/{id}")
    public String editarCita(@PathVariable("id") Integer id, Model model,
                             @ModelAttribute("mensajeOk") String mensajeOk,
                             @ModelAttribute("mensajeError") String mensajeError) {

        model.addAttribute("cita", citaService.obtenerParaEdicion(id));

        model.addAttribute("pacientes", pacienteService.listar());
        model.addAttribute("medicos", medicoService.listar());

        return "mantenimientoCita";
    }

    @PostMapping("/actualizar")
    public String actualizarCita(@RequestParam("idCita") Integer idCita,
                                 @RequestParam("idPaciente") Integer idPaciente,
                                 @RequestParam("idMedico") Integer idMedico,
                                 @RequestParam("fecha")
                                 @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
                                 LocalDateTime fecha,
                                 @RequestParam("costo") BigDecimal costo,
                                 RedirectAttributes redirectAttributes) {

        try {
            Cita datos = new Cita();
            datos.setFecha(fecha);
            datos.setCosto(costo);

            Paciente p = new Paciente();
            p.setIdPaciente(idPaciente);

            Medico m = new Medico();
            m.setIdMedico(idMedico);

            datos.setPaciente(p);
            datos.setMedico(m);

            citaService.actualizarDesdeMantenimiento(idCita, datos);

            redirectAttributes.addFlashAttribute("mensajeOk", "Cita actualizada correctamente.");
            return "redirect:/citas/editar/" + idCita;

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("mensajeError", ex.getMessage());
            return "redirect:/citas/editar/" + idCita;
        }
    }
}
