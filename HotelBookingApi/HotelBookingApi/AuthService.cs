using HotelBookingApi.Data;
using HotelBookingApi.Models;
using HotelBookingApi.Models.DTOS;
using HotelBookingApi.Services.Interfaces;
using Microsoft.EntityFrameworkCore;
using BCrypt.Net;

namespace HotelBookingApi.Services
{
    public class AuthService : IAuthService
    {
        private readonly AppDbContext _context;

        public AuthService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<AuthResponse?> RegisterAsync(RegisterRequest request)
        {
            // Verificar si ya existe
            if (await _context.Usuarios.AnyAsync(u => u.Correo == request.Correo || u.Cedula == request.Cedula))
            {
                return null; // El controlador manejará el conflicto (409)
            }

            var usuario = new Usuario
            {
                Nombre = request.Nombre,
                Apellido = request.Apellido,
                Cedula = request.Cedula,
                Correo = request.Correo,
                Celular = request.Celular,
                Contrasena = BCrypt.Net.BCrypt.HashPassword(request.Contrasena)
            };

            _context.Usuarios.Add(usuario);
            await _context.SaveChangesAsync();

            return new AuthResponse { Mensaje = "Usuario registrado exitosamente." };
        }

        public async Task<LoginResponse?> LoginAsync(LoginRequest request)
        {
            var usuario = await _context.Usuarios.FirstOrDefaultAsync(u => u.Correo == request.Correo);

            if (usuario == null || !BCrypt.Net.BCrypt.Verify(request.Contrasena, usuario.Contrasena))
            {
                return null; // Credenciales inválidas
            }

            return new LoginResponse
            {
                Mensaje = "Login exitoso",
                User = new UserDto
                {
                    Id = usuario.Id,
                    Nombre = usuario.Nombre,
                    Apellido = usuario.Apellido,
                    Correo = usuario.Correo,
                    Cedula = usuario.Cedula,
                    Celular = usuario.Celular
                }
            };
        }
    }
}