using System.ComponentModel.DataAnnotations;

namespace PROYECTOPDFVFINAL.Models
{
    public class LoginViewModel
    {
        [Required]
        [Display(Name = "Correo")]
        public string Correo { get; set; }

        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Contraseña")]
        public string Contraseña { get; set; }
    }
}
