using System.Text.Json.Serialization;

namespace HotelBookingApi.Models.DTOS
{
    public class BookingRequest
    {
        [JsonPropertyName("usuarioId")]
        public int UsuarioId { get; set; }

        [JsonPropertyName("hotelId")]
        public int HotelId { get; set; }

        [JsonPropertyName("fechaInicio")]
        public string FechaInicio { get; set; } = string.Empty;

        [JsonPropertyName("fechaFin")]
        public string FechaFin { get; set; } = string.Empty;
    }
}