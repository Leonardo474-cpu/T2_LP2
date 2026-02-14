package com.minaya.examen.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Integer idMedico;

    @Column(name = "nombres", nullable = false, length = 120)
    private String nombres;

    @Column(name = "especialidad", nullable = false, length = 80)
    private String especialidad;

    public Medico() {}

    public Medico(String nombres, String especialidad) {
        this.nombres = nombres;
        this.especialidad = especialidad;
    }

    public Integer getIdMedico() { return idMedico; }
    public void setIdMedico(Integer idMedico) { this.idMedico = idMedico; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}
