
package Modelo;

/**
 *
 * @author Mario
 */
public class ListaDeOperacionesSuscripcionUsuario {

    private DetalleSuscripcionUsuario suscripcionUsuario;
    private int cantidadOperaciones;
    
     public ListaDeOperacionesSuscripcionUsuario() {
        this.cantidadOperaciones = 0;
    }

    public ListaDeOperacionesSuscripcionUsuario(DetalleSuscripcionUsuario suscripcionUsuario) {
        this.suscripcionUsuario = suscripcionUsuario;
        this.cantidadOperaciones = suscripcionUsuario.getOperacionesRealizadas();
    }

    // Getters y Setters
    public DetalleSuscripcionUsuario getSuscripcionUsuario() {
        return suscripcionUsuario;
    }

    public void setSuscripcionUsuario(DetalleSuscripcionUsuario suscripcionUsuario) {
        this.suscripcionUsuario = suscripcionUsuario;
    }

    public int getCantidadOperaciones() {
        return cantidadOperaciones;
    }

    public void setCantidadOperaciones(int cantidadOperaciones) {
        this.cantidadOperaciones = cantidadOperaciones;
    }

    public void incrementarConteo() {
        this.cantidadOperaciones++;
        
    }

    public String realizarOperacion() {
        if ("Básico".equals(suscripcionUsuario.getTipoSuscripcion()) && cantidadOperaciones >= 5) {
            return "Límite de operaciones alcanzado. Actualiza a una suscripción premium.";
        }
        
        return "Operación realizada con éxito.";
    }

}
