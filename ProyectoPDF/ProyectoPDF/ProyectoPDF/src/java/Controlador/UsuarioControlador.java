/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controlador;

import Modelo.DetalleSuscripcionUsuario;
import Modelo.Usuario;
import ModeloDAO.DetalleSuscripcionUsuarioDAO;
import ModeloDAO.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UsuarioControlador extends HttpServlet {
  private UsuarioDAO dao = new UsuarioDAO();

    private DetalleSuscripcionUsuarioDAO detalleDao = new DetalleSuscripcionUsuarioDAO();
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
            out.println("<title>Servlet UsuarioControlador</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsuarioControlador at " + request.getContextPath () + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "login";
        }
        switch (action) {
            case "login":
                request.getRequestDispatcher("vista/login.jsp").forward(request, response);
                break;
            case "register":
                request.getRequestDispatcher("vista/register.jsp").forward(request, response);
                break;
            case "logout":
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect("UsuarioControlador?action=login");
                break;
            default:
                request.getRequestDispatcher("vista/login.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "login";
        }
        switch (action) {
            case "register2":
                registrarUsuario(request, response);
                break;
            case "login":
                iniciarSesion(request, response);
                break;
            default:
                request.getRequestDispatcher("vista/login.jsp").forward(request, response);
                break;
        }
    }
     private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
         String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");

        if (contraseña == null || contraseña.isEmpty()) {
            request.setAttribute("error", "La contraseña no puede ser nula.");
            request.getRequestDispatcher("vista/register.jsp").forward(request, response);
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContraseña(contraseña);

        if (dao.registrar(usuario)) {
            // Crear una suscripción básica para el nuevo usuario
            DetalleSuscripcionUsuario detalle = new DetalleSuscripcionUsuario(usuario);
            detalleDao.agregar(detalle);
            response.sendRedirect("UsuarioControlador?action=login");
        } else {
            request.setAttribute("error", "Error al registrar usuario");
            request.getRequestDispatcher("vista/register.jsp").forward(request, response);
        }
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");

        Usuario usuario = dao.iniciarSesion(correo, contraseña);

        if (usuario != null) {
            DetalleSuscripcionUsuario detalle = detalleDao.obtenerPorUsuario(usuario.getId());
            if (detalle == null) {
                // Crear una suscripción básica si no existe
                detalle = new DetalleSuscripcionUsuario(usuario);
                detalleDao.agregar(detalle);
            } else {
                // Verificar si la suscripción ha expirado
                if ("Premium".equals(detalle.getTipoSuscripcion()) && detalle.getFechaFinal() != null) {
                    Date now = new Date();
                    if (detalle.getFechaFinal().before(now)) {
                        // La suscripción premium ha expirado, volver a básica
                        detalle.setTipoSuscripcion("Básico");
                        detalle.setFechaInicio(null);
                        detalle.setFechaFinal(null);
                        detalle.setPrecio(0.0);
                        detalleDao.actualizar(detalle);
                    }
                }
            }
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("suscripcion", detalle.getTipoSuscripcion());
              session.setAttribute("detalle", detalle); // Asegúrate de agregar detalles a la sesión
            response.sendRedirect("vista/home.jsp");
        } else {
            request.setAttribute("error", "Correo o contraseña incorrectos");
            request.getRequestDispatcher("vista/login.jsp").forward(request, response);
        }
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
