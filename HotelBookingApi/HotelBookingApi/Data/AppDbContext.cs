// Data/AppDbContext.cs
using Microsoft.EntityFrameworkCore;
using HotelBookingApi.Models;

namespace HotelBookingApi.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Usuario> Usuarios { get; set; }

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
        }
    }
}
