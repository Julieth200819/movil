using HotelBookingApi.Data;
using HotelBookingApi.Models;
using HotelBookingApi.Models.DTOS;
using Microsoft.AspNetCore.Mvc;

namespace HotelBookingApi.Controllers
{
    [Route("api/bookings")]
    [ApiController]
    public class BookingsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public BookingsController(AppDbContext context)
        {
            _context = context;
        }

        [Route("/api/reservas")]
        [HttpPost]
        public async Task<IActionResult> CreateBooking(BookingRequest request)
        {
            // Validar que las fechas no vengan vacías
            if (string.IsNullOrWhiteSpace(request.FechaInicio) || string.IsNullOrWhiteSpace(request.FechaFin))
            {
                return BadRequest(new { mensaje = "Error: Las fechas de inicio y fin son obligatorias." });
            }

            // Intentar parsear las fechas de forma segura
            if (!DateTime.TryParse(request.FechaInicio, out DateTime fechaInicio) || 
                !DateTime.TryParse(request.FechaFin, out DateTime fechaFin))
            {
                return BadRequest(new { mensaje = "Error: El formato de fecha no es válido. Use AAAA-MM-DD." });
            }

            try
            {
                var booking = new Booking
                {
                    UsuarioId = request.UsuarioId,
                    HotelId = request.HotelId,
                    FechaInicio = fechaInicio,
                    FechaFin = fechaFin
                };

                _context.Bookings.Add(booking);
                await _context.SaveChangesAsync();

                return Ok(new { mensaje = "Reserva creada exitosamente", id = booking.Id });
            }
            catch (Exception ex)
            {
                return BadRequest(new { 
                    mensaje = "Error al crear la reserva", 
                    error = ex.Message 
                });
            }
        }
    }
}