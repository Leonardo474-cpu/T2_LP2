-- =========================
-- CLÍNICA VIDA - MySQL
-- =========================

-- (Opcional) Para reiniciar todo rápido en entorno de pruebas:
-- DROP DATABASE IF EXISTS clinica_vida;

CREATE DATABASE IF NOT EXISTS clinica_vida
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE clinica_vida;

-- -------------------------
-- Tabla: paciente
-- -------------------------
CREATE TABLE IF NOT EXISTS paciente (
  id_paciente INT UNSIGNED NOT NULL AUTO_INCREMENT,
  nombres     VARCHAR(120) NOT NULL,
  dni         VARCHAR(12)  NOT NULL,
  telefono    VARCHAR(20)  NOT NULL,
  PRIMARY KEY (id_paciente),
  UNIQUE KEY uq_paciente_dni (dni)
) ENGINE=InnoDB;

-- -------------------------
-- Tabla: medico
-- -------------------------
CREATE TABLE IF NOT EXISTS medico (
  id_medico     INT UNSIGNED NOT NULL AUTO_INCREMENT,
  nombres       VARCHAR(120) NOT NULL,
  especialidad  VARCHAR(80)  NOT NULL,
  PRIMARY KEY (id_medico)
) ENGINE=InnoDB;

-- -------------------------
-- Tabla: cita
-- -------------------------
CREATE TABLE IF NOT EXISTS cita (
  id_cita      INT UNSIGNED NOT NULL AUTO_INCREMENT,
  fecha        DATETIME      NOT NULL,
  costo        DECIMAL(10,2) NOT NULL,
  id_paciente  INT UNSIGNED  NOT NULL,
  id_medico    INT UNSIGNED  NOT NULL,

  PRIMARY KEY (id_cita),

  CONSTRAINT fk_cita_paciente
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  CONSTRAINT fk_cita_medico
    FOREIGN KEY (id_medico) REFERENCES medico(id_medico)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  CONSTRAINT chk_cita_costo CHECK (costo >= 0)
) ENGINE=InnoDB;

-- Índices recomendados
CREATE INDEX idx_cita_fecha ON cita(fecha);
CREATE INDEX idx_cita_paciente ON cita(id_paciente);
CREATE INDEX idx_cita_medico ON cita(id_medico);

-- =========================
-- INSERTS (datos de prueba)
-- =========================

-- Pacientes (10)
INSERT INTO paciente (nombres, dni, telefono) VALUES
('Juan Pérez Rojas',      '74859612', '987654321'),
('María López Díaz',      '71234567', '999111222'),
('Carlos Ramírez Soto',   '76543210', '988776655'),
('Ana Torres Medina',     '70112233', '977665544'),
('Luis Gómez Salazar',    '70998877', '966554433'),
('Diana Chávez Ruiz',     '73665544', '955443322'),
('Pedro Vargas León',     '78990011', '944332211'),
('Sofía Herrera Ponce',   '72001122', '933221100'),
('Miguel Flores Castro',  '73334455', '922110099'),
('Valeria Núñez Rivas',   '75556677', '911009988');

-- Médicos (6)
INSERT INTO medico (nombres, especialidad) VALUES
('Dra. Lucía García',     'Medicina General'),
('Dr. Roberto Álvarez',   'Cardiología'),
('Dra. Paula Sánchez',    'Pediatría'),
('Dr. Javier Mendoza',    'Dermatología'),
('Dra. Andrea Quintana',  'Ginecología'),
('Dr. Marco Córdova',     'Traumatología');

-- Citas (mínimo 10) - usando IDs generados (pacientes 1..10, médicos 1..6)
INSERT INTO cita (fecha, costo, id_paciente, id_medico) VALUES
('2026-02-10 09:00:00',  60.00,  1, 1),
('2026-02-10 10:30:00', 120.00,  2, 2),
('2026-02-11 08:45:00',  80.00,  3, 3),
('2026-02-11 11:15:00',  95.50,  4, 4),
('2026-02-12 09:20:00', 110.00,  5, 5),
('2026-02-12 12:00:00',  70.00,  6, 1),
('2026-02-13 10:00:00', 150.00,  7, 2),
('2026-02-13 16:30:00',  85.00,  8, 4),
('2026-02-14 09:40:00',  65.00,  9, 1),
('2026-02-14 14:10:00', 130.00, 10, 6),
('2026-02-15 08:30:00',  75.00,  1, 3),
('2026-02-15 15:00:00', 105.00,  2, 5);

-- (Opcional) Verificación rápida:
-- SELECT c.id_cita, c.fecha, p.nombres AS paciente, m.nombres AS medico, c.costo
-- FROM cita c
-- JOIN paciente p ON p.id_paciente = c.id_paciente
-- JOIN medico m ON m.id_medico = c.id_medico
-- ORDER BY c.fecha DESC;
