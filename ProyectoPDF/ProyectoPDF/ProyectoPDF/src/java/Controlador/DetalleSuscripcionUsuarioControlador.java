/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controlador;

import Modelo.DetalleSuscripcionUsuario;
import Modelo.Usuario;
import ModeloDAO.DetalleSuscripcionUsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DetalleSuscripcionUsuarioControlador extends HttpServlet {
    private DetalleSuscripcionUsuarioDAO dao = new DetalleSuscripcionUsuarioDAO();
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DetalleSuscripcionUsuarioControlador</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetalleSuscripcionUsuarioControlador at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }
        switch (action) {
            case "listar":
                listarSuscripciones(request, response);
                break;
            case "agregar":
                request.getRequestDispatcher("vista/agregarSuscripcion.jsp").forward(request, response);
                break;
            case "editar":
                int id = Integer.parseInt(request.getParameter("id"));
                DetalleSuscripcionUsuario detalle = dao.obtenerPorUsuario(id);
                request.setAttribute("detalle", detalle);
                request.getRequestDispatcher("vista/editarSuscripcion.jsp").forward(request, response);
                break;
           
            case "comprar":
                prepararCompra(request, response);
                break;
            case "comprarPremium":
                comprarSuscripcionPremium(request, response);
                break;
            default:
                listarSuscripciones(request, response);
                break;
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         String action = request.getParameter("action");
        switch (action) {
            case "agregar":
                agregarSuscripcion(request, response);
                break;
            case "actualizar":
                actualizarSuscripcion(request, response);
                break;
            case "comprarPremium":
                comprarSuscripcionPremium(request, response);
                break;
            default:
                listarSuscripciones(request, response);
                break;
        }
    }
    
     private void listarSuscripciones(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         List<DetalleSuscripcionUsuario> lista = dao.listar();
        request.setAttribute("suscripciones", lista);
        request.getRequestDispatcher("vista/listarSuscripciones.jsp").forward(request, response);
    }
     private void agregarSuscripcion(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
        String tipoSuscripcion = request.getParameter("tipo_suscripcion");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        Date fechaFinal = null;
        try {
            fechaInicio = formatter.parse(request.getParameter("fecha_inicio"));
            fechaFinal = formatter.parse(request.getParameter("fecha_final"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        double precio = Double.parseDouble(request.getParameter("precio"));

        DetalleSuscripcionUsuario detalle = new DetalleSuscripcionUsuario();
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        detalle.setUsuario(usuario);
        detalle.setTipoSuscripcion(tipoSuscripcion);
        detalle.setFechaInicio(fechaInicio);
        detalle.setFechaFinal(fechaFinal);
        detalle.setPrecio(precio);

        dao.agregar(detalle);
        response.sendRedirect("DetalleSuscripcionUsuarioControlador?action=listar");
    }
      private void actualizarSuscripcion(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
        String tipoSuscripcion = request.getParameter("tipo_suscripcion");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        Date fechaFinal = null;
        try {
            fechaInicio = formatter.parse(request.getParameter("fecha_inicio"));
            fechaFinal = formatter.parse(request.getParameter("fecha_final"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        double precio = Double.parseDouble(request.getParameter("precio"));

        DetalleSuscripcionUsuario detalle = new DetalleSuscripcionUsuario();
        detalle.setId(id);
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        detalle.setUsuario(usuario);
        detalle.setTipoSuscripcion(tipoSuscripcion);
        detalle.setFechaInicio(fechaInicio);
        detalle.setFechaFinal(fechaFinal);
        detalle.setPrecio(precio);

        dao.actualizar(detalle);
        response.sendRedirect("DetalleSuscripcionUsuarioControlador?action=listar");
    }
      
       private void comprarSuscripcionPremium(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            DetalleSuscripcionUsuario detalle = dao.obtenerPorUsuario(usuario.getId());
            detalle.comprarSuscripcionPremium();
            dao.actualizar(detalle);
            session.setAttribute("suscripcion", detalle.getTipoSuscripcion());
            response.sendRedirect("vista/home.jsp");
        } else {
            response.sendRedirect("UsuarioControlador?action=login");
        }
    }
      private void prepararCompra(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Date fechaInicio = new Date();
        Date fechaFinal = new Date(fechaInicio.getTime() + (30L * 24 * 60 * 60 * 1000));

        request.setAttribute("fechaInicio", fechaInicio);
        request.setAttribute("fechaFinal", fechaFinal);
        request.getRequestDispatcher("vista/comprarSuscripcion.jsp").forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
