-- =========================
-- CLÍNICA VIDA - MySQL
-- =========================

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
-- 1 cita -> 1 paciente
-- 1 cita -> 1 medico
-- -------------------------
CREATE TABLE IF NOT EXISTS cita (
  id_cita     INT UNSIGNED NOT NULL AUTO_INCREMENT,
  fecha       DATETIME     NOT NULL,
  costo       DECIMAL(10,2) NOT NULL,
  id_paciente INT UNSIGNED NOT NULL,
  id_medico   INT UNSIGNED NOT NULL,

  PRIMARY KEY (id_cita),

  CONSTRAINT fk_cita_paciente
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  CONSTRAINT fk_cita_medico
    FOREIGN KEY (id_medico) REFERENCES medico(id_medico)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  -- Nota: CHECK funciona en MySQL 8.0+ (en versiones antiguas puede ignorarse)
  CONSTRAINT chk_cita_costo CHECK (costo >= 0)
) ENGINE=InnoDB;

-- Índices recomendados para performance
CREATE INDEX idx_cita_fecha ON cita(fecha);
CREATE INDEX idx_cita_paciente ON cita(id_paciente);
CREATE INDEX idx_cita_medico ON cita(id_medico);

-- (Opcional) Evitar doble cita del mismo médico a la misma hora exacta:
-- CREATE UNIQUE INDEX uq_cita_medico_fecha ON cita(id_medico, fecha);
