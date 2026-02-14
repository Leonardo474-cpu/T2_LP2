package com.minaya.examen.model;

import jakarta.persistence.*;

@Entity
@Table(name = "paciente", uniqueConstraints = { @UniqueConstraint(name = "uq_paciente_dni", columnNames = "dni") })
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_paciente")
	private Integer idPaciente;

	@Column(name = "nombres", nullable = false, length = 120)
	private String nombres;

	@Column(name = "dni", nullable = false, length = 12, unique = true)
	private String dni;

	@Column(name = "telefono", nullable = false, length = 20)
	private String telefono;

	public Paciente() {
	}

	public Paciente(String nombres, String dni, String telefono) {
		this.nombres = nombres;
		this.dni = dni;
		this.telefono = telefono;
	}

	public Integer getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
