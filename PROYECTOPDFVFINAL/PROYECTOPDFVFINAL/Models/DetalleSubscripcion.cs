using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace PROYECTOPDFVFINAL.Models
{
    [Table("Detalle_Subscripcion")]
    public class DetalleSubscripcion
    {
        [Key]
        [Column("id_detalle_subscripcion")] // Coincide con el nombre de la columna en la base de datos
        public int IdDetalleSubscripcion { get; set; }

        [Required]
        [MaxLength(100)]
        [Column("tipo_subscripcion")]
        public string TipoSubscripcion { get; set; } = "BASICO";

        [Required]
        [Column("fecha_inicio")]
        public DateTime FechaInicio { get; set; } = DateTime.Now;

        [Required]
        [Column("fecha_final")]
        public DateTime FechaFinal { get; set; } = DateTime.Now.AddYears(1);

        [Required]
        [Column("precio")]
        public decimal Precio { get; set; } = 0;

        [Required]
        [Column("operaciones_realizadas")]
        public int OperacionesRealizadas { get; set; } = 0;

        [Required]
        [ForeignKey("Usuario")]
        [Column("id_usuario")]
        public int IdUsuario { get; set; }

        public Usuario Usuario { get; set; }
    }
}