using System.Text.Json.Serialization;

namespace HotelBookingApi.Models.DTOS
{
    public class HotelDto
    {
        public int Id { get; set; }
        public string Nombre { get; set; } = string.Empty;
        public string Descripcion { get; set; } = string.Empty;

        [JsonPropertyName("imagenUrl")]
        public string ImagenUrl { get; set; } = string.Empty;

        [JsonPropertyName("precio")]
        public decimal PrecioPorNoche { get; set; }

        [JsonPropertyName("ubicacion")]
        public string Ciudad { get; set; } = string.Empty;

        [JsonPropertyName("estrellas")]
        public int Estrellas { get; set; }

        [JsonPropertyName("servicios")]
        public List<string> Servicios { get; set; } = new();
    }
}