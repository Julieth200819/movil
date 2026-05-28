using System.ComponentModel.DataAnnotations.Schema;

namespace HotelBookingApi.Models
{
    [Table("reservas")]
    public class Booking
    {
        public int Id { get; set; }
        public int UsuarioId { get; set; }
        public int HotelId { get; set; }
        public DateTime FechaInicio { get; set; }
        public DateTime FechaFin { get; set; }
        public DateTime CreadoEn { get; set; } = DateTime.Now;

        public Usuario? Usuario { get; set; }
        public Hotel? Hotel { get; set; }
    }
}