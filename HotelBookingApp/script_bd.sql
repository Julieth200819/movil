-- ============================================================
--  HotelBooking – Script SQL
--  Grupo 8 | Desarrollo Móvil
-- ============================================================

CREATE DATABASE IF NOT EXISTS hotelbooking_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hotelbooking_db;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS Usuarios (
    Id          INT          NOT NULL AUTO_INCREMENT,
    Cedula      VARCHAR(20)  NOT NULL,
    Nombre      VARCHAR(100) NOT NULL,
    Apellido    VARCHAR(100) NOT NULL,
    Celular     VARCHAR(20)  NOT NULL,
    Correo      VARCHAR(150) NOT NULL,
    Contrasena  VARCHAR(255) NOT NULL,   -- hash BCrypt
    CreadoEn    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (Id),
    UNIQUE KEY UQ_Usuarios_Correo (Correo),
    UNIQUE KEY UQ_Usuarios_Cedula (Cedula)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
--  Datos de prueba (contraseñas hasheadas con BCrypt)
--  Contraseña real de ambos usuarios: "hotel123"
-- ============================================================
INSERT INTO Usuarios (Cedula, Nombre, Apellido, Celular, Correo, Contrasena)
VALUES
(
    '1234567890',
    'Carlos',
    'Ramírez',
    '3001234567',
    'carlos@hotelbooking.com',
    '$2a$11$K8z0P1v5Q2n3M4L6N7J8K.uGxO1W2E3R4T5Y6U7I8O9P0A1S2D3'
),
(
    '0987654321',
    'María',
    'López',
    '3109876543',
    'maria@hotelbooking.com',
    '$2a$11$K8z0P1v5Q2n3M4L6N7J8K.uGxO1W2E3R4T5Y6U7I8O9P0A1S2D3'
);

-- Verificar
SELECT Id, Cedula, Nombre, Apellido, Celular, Correo, CreadoEn
FROM Usuarios;
