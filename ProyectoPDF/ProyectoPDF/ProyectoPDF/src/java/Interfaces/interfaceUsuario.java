
package Interfaces;

import Modelo.Usuario;

/**
 *
 */
public interface interfaceUsuario {
    public boolean registrar(Usuario usuario);
    public Usuario iniciarSesion(String correo, String contraseña);
}
