using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace PROYECTOPDFVFINAL.Models
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options) { }

        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<DetalleSubscripcion> DetallesSubscripcion { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Usuario>()
                .ToTable("Usuario")
                .HasKey(u => u.IdUsuario);

            modelBuilder.Entity<DetalleSubscripcion>()
                .ToTable("Detalle_Subscripcion")
                .HasKey(ds => ds.IdDetalleSubscripcion);

            modelBuilder.Entity<DetalleSubscripcion>()
                .HasOne(ds => ds.Usuario)
                .WithMany(u => u.DetallesSubscripcion)
                .HasForeignKey(ds => ds.IdUsuario);
        }
    }
}
