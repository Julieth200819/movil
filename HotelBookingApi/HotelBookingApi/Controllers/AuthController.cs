// Controllers/AuthController.cs
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HotelBookingApi.Data;
using HotelBookingApi.Models;
using HotelBookingApi.Models.DTOs;
using BCrypt.Net;

namespace HotelBookingApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class AuthController : ControllerBase
    {
        private readonly AppDbContext _context;

        public AuthController(AppDbContext context)
        {
            _context = context;
        }

        // POST api/auth/register
        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegisterRequest request)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            // Verificar duplicados
            bool correoExiste = await _context.Usuarios.AnyAsync(u => u.Correo == request.Correo);
            if (correoExiste)
                return Conflict(new { mensaje = "El correo ya está registrado." });

            bool cedulaExiste = await _context.Usuarios.AnyAsync(u => u.Cedula == request.Cedula);
            if (cedulaExiste)
                return Conflict(new { mensaje = "La cédula ya está registrada." });

            // Hashear contraseña con bcrypt
            string hash = BCrypt.Net.BCrypt.HashPassword(request.Contrasena);

            var usuario = new Usuario
            {
                Cedula    = request.Cedula,
                Nombre    = request.Nombre,
                Apellido  = request.Apellido,
                Celular   = request.Celular,
                Correo    = request.Correo,
                Contrasena = hash
            };

            _context.Usuarios.Add(usuario);
            await _context.SaveChangesAsync();

            return Ok(new { mensaje = "Usuario registrado exitosamente." });
        }

        // POST api/auth/login
        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginRequest request)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var usuario = await _context.Usuarios
                .FirstOrDefaultAsync(u => u.Correo == request.Correo);

            if (usuario == null || !BCrypt.Net.BCrypt.Verify(request.Contrasena, usuario.Contrasena))
                return Unauthorized(new { mensaje = "Correo o contraseña incorrectos." });

            var response = new LoginResponse
            {
                Id       = usuario.Id,
                Nombre   = usuario.Nombre,
                Apellido = usuario.Apellido,
                Correo   = usuario.Correo,
                Cedula   = usuario.Cedula,
                Celular  = usuario.Celular,
                Mensaje  = "Login exitoso"
            };

            return Ok(response);
        }
    }
}
