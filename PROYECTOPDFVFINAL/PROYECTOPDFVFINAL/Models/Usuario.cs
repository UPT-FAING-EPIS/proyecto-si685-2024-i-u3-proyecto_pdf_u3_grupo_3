using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PROYECTOPDFVFINAL.Models
{
    [Table("Usuario")]
    public class Usuario
    {
        [Key]
        [Column("id_usuario")]
        public int IdUsuario { get; set; }

        [Required(ErrorMessage = "El campo Nombre es obligatorio.")]
        [MaxLength(100)]
        public string Nombre { get; set; }

        [Required(ErrorMessage = "El campo Correo es obligatorio.")]
        [EmailAddress(ErrorMessage = "El formato del correo no es válido.")]
        [MaxLength(100)]
        public string Correo { get; set; }

        [Required(ErrorMessage = "El campo Contraseña es obligatorio.")]
        [MaxLength(255)]
        public string Contraseña { get; set; }

        public ICollection<DetalleSubscripcion> DetallesSubscripcion { get; set; }
    }
}
