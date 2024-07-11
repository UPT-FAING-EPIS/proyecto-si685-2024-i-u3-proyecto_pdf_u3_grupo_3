/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controlador;

import Modelo.DetalleSuscripcionUsuario;
import Modelo.ListaDeOperacionesSuscripcionUsuario;
import Modelo.Usuario;
import ModeloDAO.DetalleSuscripcionUsuarioDAO;
import ModeloDAO.UsuarioDAO;
import Servicio.CortadorPDF;
import Servicio.FusionadorPDF;
import com.itextpdf.text.pdf.PdfReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@MultipartConfig
public class OperacionControlador extends HttpServlet {
     private UsuarioDAO usuarioDao = new UsuarioDAO();
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
            out.println("<title>Servlet OperacionControlador</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OperacionControlador at " + request.getContextPath () + "</h1>");
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
            HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("UsuarioControlador?action=login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "operaciones":
                request.getRequestDispatcher("vista/operaciones.jsp").forward(request, response);
                break;
            case "fusionarPDF":
                request.getRequestDispatcher("vista/fusionarPDF.jsp").forward(request, response);
                break;
            case "cortarPDF":
                request.getRequestDispatcher("vista/cortarPDF.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("UsuarioControlador?action=login");
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
         HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("UsuarioControlador?action=login");
            return;
        }

        String action = request.getParameter("action");
        if ("fusionarPDF".equals(action)) {
            manejarFusionarPDF(request, response, usuario);
        } else if ("subirPDF".equals(action)) {
            manejarSubirPDF(request, response, usuario);
        } else if ("cortarPDF".equals(action)) {
            manejarCortarPDF(request, response, usuario);
        }
    }
     private void manejarFusionarPDF(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException {
        DetalleSuscripcionUsuario detalle = detalleDao.obtenerPorUsuario(usuario.getId());
        if (detalle == null) {
            response.sendRedirect("UsuarioControlador?action=login");
            return;
        }

        ListaDeOperacionesSuscripcionUsuario listaOperaciones = new ListaDeOperacionesSuscripcionUsuario(detalle);

        String resultadoOperacion = listaOperaciones.realizarOperacion();
        if (resultadoOperacion.startsWith("Límite de operaciones alcanzado")) {
            request.setAttribute("error", resultadoOperacion);
            request.getRequestDispatcher("vista/operaciones.jsp").forward(request, response);
            return;
        }

        // Obtener los archivos subidos
        List<Part> partes = (List<Part>) request.getParts();
        List<InputStream> archivosPDF = new ArrayList<>();
        for (Part parte : partes) {
            if (parte.getName().equals("archivos") && parte.getSize() > 0) {
                archivosPDF.add(parte.getInputStream());
            }
        }

        if (archivosPDF.size() < 2) {
            request.setAttribute("error", "Debes seleccionar al menos dos archivos PDF para fusionar.");
            request.getRequestDispatcher("vista/fusionarPDF.jsp").forward(request, response);
            return;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        FusionadorPDF fusionador = new FusionadorPDF();
        String resultadoFusion = fusionador.fusionarPDFs(archivosPDF, outputStream);

        if (resultadoFusion.startsWith("Error")) {
            request.setAttribute("error", resultadoFusion);
            request.getRequestDispatcher("vista/fusionarPDF.jsp").forward(request, response);
            return;
        }

        listaOperaciones.incrementarConteo();
        detalle.setOperacionesRealizadas(listaOperaciones.getCantidadOperaciones());
        detalleDao.actualizar(detalle);

        // Configurar la respuesta para la descarga
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=fusionado.pdf");
        response.setContentLength(outputStream.size());

        outputStream.writeTo(response.getOutputStream());
        outputStream.flush();
    }


     private void manejarSubirPDF(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException {
     Part parte = request.getPart("archivo");
        if (parte == null || parte.getSize() == 0) {
            request.setAttribute("error", "Debes seleccionar un archivo PDF para cortar.");
            request.getRequestDispatcher("vista/cortarPDF.jsp").forward(request, response);
            return;
        }

        InputStream archivoPDF = parte.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        archivoPDF.transferTo(baos);
        byte[] pdfBytes = baos.toByteArray();

        // Obtener el número de páginas del PDF
        PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes));
        int numeroPaginas = reader.getNumberOfPages();
        reader.close();

        // Guardar el archivo en la sesión
        HttpSession session = request.getSession();
        session.setAttribute("archivoPDF", pdfBytes);
        session.setAttribute("numeroPaginas", numeroPaginas);

        // Enviar el número de páginas al JSP
        request.setAttribute("numeroPaginas", numeroPaginas);
        request.getRequestDispatcher("vista/cortarPDF.jsp").forward(request, response);
}

     private void manejarCortarPDF(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException {
    DetalleSuscripcionUsuario detalle = detalleDao.obtenerPorUsuario(usuario.getId());
        if (detalle == null) {
            response.sendRedirect("UsuarioControlador?action=login");
            return;
        }

        ListaDeOperacionesSuscripcionUsuario listaOperaciones = new ListaDeOperacionesSuscripcionUsuario(detalle);

        String resultadoOperacion = listaOperaciones.realizarOperacion();
        if (resultadoOperacion.startsWith("Límite de operaciones alcanzado")) {
            request.setAttribute("error", resultadoOperacion);
            request.getRequestDispatcher("vista/operaciones.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        byte[] pdfBytes = (byte[]) session.getAttribute("archivoPDF");
        int numeroPaginas = (int) session.getAttribute("numeroPaginas");

        // Obtener los parámetros de la solicitud
        int inicio = Integer.parseInt(request.getParameter("inicio"));
        int fin = Integer.parseInt(request.getParameter("fin"));

        if (inicio < 1 || fin > numeroPaginas || inicio > fin) {
            request.setAttribute("error", "Rango de páginas inválido.");
            request.getRequestDispatcher("vista/cortarPDF.jsp").forward(request, response);
            return;
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(pdfBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        CortadorPDF cortador = new CortadorPDF();
        String resultadoCorte = cortador.cortarPDF(bais, inicio, fin, outputStream);

        if (resultadoCorte.startsWith("Error")) {
            request.setAttribute("error", resultadoCorte);
            request.getRequestDispatcher("vista/cortarPDF.jsp").forward(request, response);
            return;
        }

        listaOperaciones.incrementarConteo();
        detalle.setOperacionesRealizadas(listaOperaciones.getCantidadOperaciones());
        detalleDao.actualizar(detalle);

        // Limpiar la sesión
        session.removeAttribute("archivoPDF");
        session.removeAttribute("numeroPaginas");

        // Configurar la respuesta para la descarga
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=cortado.pdf");
        response.setContentLength(outputStream.size());

        outputStream.writeTo(response.getOutputStream());
        outputStream.flush();
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
