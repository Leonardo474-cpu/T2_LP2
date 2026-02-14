package com.minaya.examen.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "cita",
    indexes = {
        @Index(name = "idx_cita_fecha", columnList = "fecha"),
        @Index(name = "idx_cita_paciente", columnList = "id_paciente"),
        @Index(name = "idx_cita_medico", columnList = "id_medico")
    }
)
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_paciente", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cita_paciente"))
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_medico", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cita_medico"))
    private Medico medico;

    public Cita() {}

    public Cita(LocalDateTime fecha, BigDecimal costo, Paciente paciente, Medico medico) {
        this.fecha = fecha;
        this.costo = costo;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
}
