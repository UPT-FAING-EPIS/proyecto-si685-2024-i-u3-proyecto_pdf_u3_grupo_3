
package ModeloDAO;

import Config.Conexion;
import Interfaces.interfaceUsuario;
import Modelo.Usuario;
import java.sql.*;

/**
 *
 * @author Mario
 */
public class UsuarioDAO implements interfaceUsuario {
    
    Conexion cn=new Conexion();//para establecer la conexion DB
    Connection con; //para llamar ala cadena de conexion
    PreparedStatement ps;//ejecutar consulta
    ResultSet rs;//almacena datos de la consulta

    @Override
    public boolean registrar(Usuario usuario) {
         String sql = "INSERT INTO usuarios (nombre, correo, contraseña) VALUES (?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContraseña());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Usuario iniciarSesion(String correo, String contraseña) {
     String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";
        Usuario usuario = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contraseña);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) cn.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }
   

}
