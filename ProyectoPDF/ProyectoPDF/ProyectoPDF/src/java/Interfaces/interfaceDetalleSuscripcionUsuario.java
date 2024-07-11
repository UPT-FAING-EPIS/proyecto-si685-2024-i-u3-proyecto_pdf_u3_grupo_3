
package Interfaces;

import Modelo.DetalleSuscripcionUsuario;
import java.util.List;

/**
 *
 */
public interface interfaceDetalleSuscripcionUsuario {
 public List<DetalleSuscripcionUsuario> listar();
    public boolean agregar(DetalleSuscripcionUsuario detalle);
    public void actualizar(DetalleSuscripcionUsuario detalle);
     public DetalleSuscripcionUsuario obtenerPorUsuario(int id);
}
