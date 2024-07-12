namespace PROYECTOPDFVFINAL.Models
{
    public class DashboardViewModel
    {
        public string Nombre { get; set; }
        public string TipoSubscripcion { get; set; } = "No tiene suscripción"; // Valor predeterminado
        public int OperacionesRealizadas { get; set; } = 0; // Valor predeterminado
    }
}
