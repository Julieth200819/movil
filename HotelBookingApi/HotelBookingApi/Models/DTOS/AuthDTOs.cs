// Models/DTOs/RegisterRequest.cs
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace HotelBookingApi.Models.DTOS
{
    public class RegisterRequest
    {
        [Required(ErrorMessage = "La cédula es obligatoria")]
        public string Cedula { get; set; } = string.Empty;

        [Required(ErrorMessage = "El nombre es obligatorio")]
        public string Nombre { get; set; } = string.Empty;

        [Required(ErrorMessage = "El apellido es obligatorio")]
        public string Apellido { get; set; } = string.Empty;

        [Required(ErrorMessage = "El celular es obligatorio")]
        public string Celular { get; set; } = string.Empty;

        [Required(ErrorMessage = "El correo es obligatorio")]
        [EmailAddress(ErrorMessage = "Correo no válido")]
        public string Correo { get; set; } = string.Empty;

        [Required(ErrorMessage = "La contraseña es obligatoria")]
        [JsonPropertyName("password")]
        [MinLength(6, ErrorMessage = "La contraseña debe tener al menos 6 caracteres")]
        public string Contrasena { get; set; } = string.Empty;
    }

    public class LoginRequest
    {
        [Required(ErrorMessage = "El correo es obligatorio")]
        [EmailAddress(ErrorMessage = "Correo no válido")]
        public string Correo { get; set; } = string.Empty;

        [Required(ErrorMessage = "La contraseña es obligatoria")]
        [JsonPropertyName("password")]
        public string Contrasena { get; set; } = string.Empty;
    }

    public class LoginResponse
    {
        public int Id { get; set; }
        public string Nombre { get; set; } = string.Empty;
        public string Apellido { get; set; } = string.Empty;
        public string Correo { get; set; } = string.Empty;
        public string Cedula { get; set; } = string.Empty;
        public string Celular { get; set; } = string.Empty;
        public string Mensaje { get; set; } = string.Empty;
    }
}
