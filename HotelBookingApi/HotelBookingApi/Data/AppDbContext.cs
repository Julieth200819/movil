// Data/AppDbContext.cs
using Microsoft.EntityFrameworkCore;
using HotelBookingApi.Models;

namespace HotelBookingApi.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Hotel> Hoteles { get; set; }
        public DbSet<Booking> Bookings { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Usuario>(entity =>
            {
                entity.ToTable("usuarios");
                entity.HasKey(u => u.Id);
                entity.Property(u => u.Id).HasColumnName("id");
                entity.Property(u => u.Cedula).HasColumnName("cedula").IsRequired().HasMaxLength(20);
                entity.Property(u => u.Nombre).HasColumnName("nombre").IsRequired().HasMaxLength(100);
                entity.Property(u => u.Apellido).HasColumnName("apellido").IsRequired().HasMaxLength(100);
                entity.Property(u => u.Celular).HasColumnName("celular").IsRequired().HasMaxLength(20);
                entity.Property(u => u.Correo).HasColumnName("correo").IsRequired().HasMaxLength(150);
                entity.Property(u => u.Contrasena).HasColumnName("contrasena").IsRequired().HasMaxLength(255);
                entity.HasIndex(u => u.Correo).IsUnique();
                entity.HasIndex(u => u.Cedula).IsUnique();
            });

            modelBuilder.Entity<Hotel>(entity =>
            {
                entity.ToTable("hoteles");
                entity.HasKey(h => h.Id);
                entity.Property(h => h.Id).HasColumnName("id");
                entity.Property(h => h.Nombre).HasColumnName("nombre");
                entity.Property(h => h.Ciudad).HasColumnName("ciudad");
                entity.Property(h => h.Descripcion).HasColumnName("descripcion");
                entity.Property(h => h.PrecioPorNoche).HasColumnName("precioPorNoche");
                entity.Property(h => h.Estrellas).HasColumnName("estrellas");
                entity.Property(h => h.ImagenUrl).HasColumnName("imagenUrl");
                entity.Property(h => h.Amenidades).HasColumnName("amenidades");
                entity.Property(h => h.Activo).HasColumnName("activo");
                entity.Property(h => h.CreadoEn).HasColumnName("creadoEn");
            });

            modelBuilder.Entity<Booking>(entity =>
            {
                entity.ToTable("reservas");
                entity.HasKey(b => b.Id);
                entity.Property(b => b.Id).HasColumnName("id");
                entity.Property(b => b.UsuarioId).HasColumnName("usuarioId");
                entity.Property(b => b.HotelId).HasColumnName("hotelId");
                entity.Property(b => b.FechaInicio).HasColumnName("fechaInicio");
                entity.Property(b => b.FechaFin).HasColumnName("fechaFin");
                entity.Property(b => b.CreadoEn).HasColumnName("creadoEn");
            });
        }
    }
}
