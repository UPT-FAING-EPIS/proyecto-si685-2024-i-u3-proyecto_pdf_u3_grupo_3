using System.ComponentModel.DataAnnotations;

namespace PROYECTOPDFVFINAL.Models
{
    public class RegisterViewModel
    {
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
    }
}
