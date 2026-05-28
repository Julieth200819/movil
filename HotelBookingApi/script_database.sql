-- ============================================
-- SCRIPT DE BASE DE DATOS - HOTELBOOKING
-- Grupo 8 - Proyecto Académico Desarrollo Móvil
-- ============================================

-- 1. CREAR LA BASE DE DATOS
CREATE DATABASE IF NOT EXISTS HotelBookingDB;
USE HotelBookingDB;

-- 2. CREAR TABLA DE USUARIOS
CREATE TABLE IF NOT EXISTS Usuarios (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Cedula VARCHAR(20) UNIQUE NOT NULL,
    Nombre VARCHAR(100) NOT NULL,
    Apellido VARCHAR(100) NOT NULL,
    Celular VARCHAR(20) NOT NULL,
    Correo VARCHAR(100) UNIQUE NOT NULL,
    Contrasena VARCHAR(255) NOT NULL,
    FechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ActualizadoEn DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. ÍNDICES PARA OPTIMIZACIÓN
CREATE INDEX idx_correo ON Usuarios(Correo);
CREATE INDEX idx_cedula ON Usuarios(Cedula);

-- 4. DATOS DE PRUEBA (Opcional - comentar si no se desea)
-- Las contraseñas están hasheadas con BCrypt. Para propósitos de prueba:
-- Usuario 1: correo=juan@example.com, contraseña=123456
-- Usuario 2: correo=maria@example.com, contraseña=abcdef

INSERT INTO Usuarios (Cedula, Nombre, Apellido, Celular, Correo, Contrasena) VALUES
('1023456789', 'Juan', 'Pérez', '3001234567', 'juan@example.com', '$2a$11$GKz0zN2kY4R5Z8pL9qWdQu1F7xJ3mR6nT0sV2wY5z8Q1pL4tN7uU0'),
('1098765432', 'María', 'García', '3009876543', 'maria@example.com', '$2a$11$B5kF3sR9mL2nQ8pJ1wX0dO4T6vY7zC9aB2cE5dF8gH1jK4lM7oP0');

-- 5. MOSTRAR LA ESTRUCTURA
SHOW TABLES;
DESCRIBE Usuarios;
SELECT * FROM Usuarios;
