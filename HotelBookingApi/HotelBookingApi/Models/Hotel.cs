using System.ComponentModel.DataAnnotations.Schema;

namespace HotelBookingApi.Models
{
    [Table("hoteles")]
    public class Hotel
    {
        public int Id { get; set; }
        public string Nombre { get; set; } = string.Empty;
        public string Ciudad { get; set; } = string.Empty;
        public string Descripcion { get; set; } = string.Empty;
        public decimal PrecioPorNoche { get; set; }
        public int Estrellas { get; set; }
        public string? ImagenUrl { get; set; }
        public string Amenidades { get; set; } = string.Empty;
        public bool Activo { get; set; } = true;
        public DateTime CreadoEn { get; set; } = DateTime.Now;
    }
}