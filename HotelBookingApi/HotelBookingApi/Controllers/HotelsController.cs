using HotelBookingApi.Data;
using HotelBookingApi.Models.DTOS;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HotelBookingApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HotelsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public HotelsController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<IActionResult> GetHotels()
        {
            // Primero traemos los datos de la DB a la memoria con ToListAsync
            var hotelsData = await _context.Hoteles
                .Where(h => h.Activo)
                .ToListAsync();

            // Ahora que están en memoria, podemos usar lógica de C# compleja para el DTO
            var hotels = hotelsData.Select(h => new HotelDto
            {
                Id = h.Id,
                Nombre = h.Nombre,
                Descripcion = h.Descripcion,
                PrecioPorNoche = h.PrecioPorNoche,
                Ciudad = h.Ciudad,
                ImagenUrl = h.ImagenUrl ?? "",
                Estrellas = h.Estrellas,
                    // Esta es la lógica que envía la lista a la App
                Servicios = string.IsNullOrEmpty(h.Amenidades) 
                    ? new List<string>() 
                    : h.Amenidades.Split(',', StringSplitOptions.RemoveEmptyEntries)
                                  .Select(s => s.Trim()).ToList()
            }).ToList();

            return Ok(hotels);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetHotel(int id)
        {
            var h = await _context.Hoteles
                .FirstOrDefaultAsync(h => h.Id == id);

            if (h == null) return NotFound();

            var hotelDto = new HotelDto
            {
                Id = h.Id,
                Nombre = h.Nombre,
                Descripcion = h.Descripcion,
                PrecioPorNoche = h.PrecioPorNoche,
                Ciudad = h.Ciudad,
                ImagenUrl = h.ImagenUrl ?? "",
                Estrellas = h.Estrellas,
                Servicios = string.IsNullOrEmpty(h.Amenidades) 
                    ? new List<string>() 
                    : h.Amenidades.Split(',', StringSplitOptions.RemoveEmptyEntries)
                                  .Select(s => s.Trim()).ToList()
            };

            return Ok(hotelDto);
        }
    }
}