
package ModeloDAO;

import Config.Conexion;
import Interfaces.interfaceDetalleSuscripcionUsuario;
import Modelo.DetalleSuscripcionUsuario;
import Modelo.Usuario;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Mario
 */
public class DetalleSuscripcionUsuarioDAO implements interfaceDetalleSuscripcionUsuario {
    
        private Conexion cn = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<DetalleSuscripcionUsuario> listar() {  
        List<DetalleSuscripcionUsuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalles_suscripciones";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetalleSuscripcionUsuario detalle = new DetalleSuscripcionUsuario();
                Usuario usuario = new Usuario();
                detalle.setId(rs.getInt("id"));
                usuario.setId(rs.getInt("usuario_id"));
                detalle.setUsuario(usuario);
                detalle.setTipoSuscripcion(rs.getString("tipo_suscripcion"));
                detalle.setFechaInicio(rs.getDate("fecha_inicio"));
                detalle.setFechaFinal(rs.getDate("fecha_final"));
                detalle.setPrecio(rs.getDouble("precio"));
                lista.add(detalle);
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
        return lista;
    }

    @Override
    public boolean agregar(DetalleSuscripcionUsuario detalle) {
         String sql = "INSERT INTO detalles_suscripciones (usuario_id, tipo_suscripcion, fecha_inicio, fecha_final, precio) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, detalle.getUsuario().getId());
            ps.setString(2, detalle.getTipoSuscripcion());
            if (detalle.getFechaInicio() != null) {
                ps.setDate(3, new java.sql.Date(detalle.getFechaInicio().getTime()));
            } else {
                ps.setDate(3, null);
            }
            if (detalle.getFechaFinal() != null) {
                ps.setDate(4, new java.sql.Date(detalle.getFechaFinal().getTime()));
            } else {
                ps.setDate(4, null);
            }
            ps.setDouble(5, detalle.getPrecio());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) cn.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    

    

    

    @Override
    public DetalleSuscripcionUsuario obtenerPorUsuario(int id) {
        String sql = "SELECT * FROM detalles_suscripciones WHERE usuario_id = ?";
    DetalleSuscripcionUsuario detalle = null;
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if (rs.next()) {
            detalle = new DetalleSuscripcionUsuario();
            Usuario usuario = new Usuario();
            detalle.setId(rs.getInt("id"));
            usuario.setId(rs.getInt("usuario_id"));
            detalle.setUsuario(usuario);
            detalle.setTipoSuscripcion(rs.getString("tipo_suscripcion"));
            detalle.setFechaInicio(rs.getDate("fecha_inicio"));
            detalle.setFechaFinal(rs.getDate("fecha_final"));
            detalle.setPrecio(rs.getDouble("precio"));
            detalle.setOperacionesRealizadas(rs.getInt("operaciones_realizadas"));
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
    return detalle;
    }

    @Override
    public void actualizar(DetalleSuscripcionUsuario detalle) {
       String sql = "UPDATE detalles_suscripciones SET tipo_suscripcion = ?, fecha_inicio = ?, fecha_final = ?, precio = ?, operaciones_realizadas = ? WHERE usuario_id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, detalle.getTipoSuscripcion());
            if (detalle.getFechaInicio() != null) {
                ps.setDate(2, new java.sql.Date(detalle.getFechaInicio().getTime()));
            } else {
                ps.setDate(2, null);
            }
            if (detalle.getFechaFinal() != null) {
                ps.setDate(3, new java.sql.Date(detalle.getFechaFinal().getTime()));
            } else {
                ps.setDate(3, null);
            }
            ps.setDouble(4, detalle.getPrecio());
            ps.setInt(5, detalle.getOperacionesRealizadas());
            ps.setInt(6, detalle.getUsuario().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) cn.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
