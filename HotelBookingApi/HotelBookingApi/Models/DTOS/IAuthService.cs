using HotelBookingApi.Models.DTOS;

namespace HotelBookingApi.Services.Interfaces
{
    public interface IAuthService
    {
        Task<AuthResponse?> RegisterAsync(RegisterRequest request);
        Task<LoginResponse?> LoginAsync(LoginRequest request);
    }
    
    public class AuthResponse { public string Mensaje { get; set; } = ""; }
}