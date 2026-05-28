using HotelBookingApi.Models.DTOS;
using HotelBookingApi.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace HotelBookingApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IAuthService _authService;

        // Inyectamos el servicio en el constructor
        public AuthController(IAuthService authService)
        {
            _authService = authService;
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register(RegisterRequest request)
        {
            // Delegamos la lógica al servicio
            var result = await _authService.RegisterAsync(request);

            if (result == null)
            {
                return Conflict(new { mensaje = "El correo o la cédula ya están registrados" });
            }

            return Ok(result);
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login(LoginRequest request)
        {
            // Delegamos la lógica al servicio
            var result = await _authService.LoginAsync(request);

            if (result == null)
            {
                return Unauthorized(new { mensaje = "Correo o contraseña incorrectos" });
            }

            return Ok(result);
        }
    }
}
