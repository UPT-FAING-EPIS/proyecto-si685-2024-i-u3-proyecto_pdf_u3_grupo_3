
package Modelo;

import java.util.Date;

/**
 *
 * @author Mario
 */
public class DetalleSuscripcionUsuario {

    private int id;
    private Usuario usuario;
    private String tipoSuscripcion;
    private Date fechaInicio;
    private Date fechaFinal;
    private double precio;
    private int operacionesRealizadas; // Nuevo campo

    public DetalleSuscripcionUsuario() {
        // Constructor predeterminado
    }

    public DetalleSuscripcionUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.tipoSuscripcion = "Básico"; // Valor predeterminado
        this.fechaInicio = null;
        this.fechaFinal = null;
        this.precio = 0.0;
        this.operacionesRealizadas = 0; // Inicializar a 0
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoSuscripcion() {
        return tipoSuscripcion;
    }

    public void setTipoSuscripcion(String tipoSuscripcion) {
        this.tipoSuscripcion = tipoSuscripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getOperacionesRealizadas() {
        return operacionesRealizadas;
    }

    public void setOperacionesRealizadas(int operacionesRealizadas) {
        this.operacionesRealizadas = operacionesRealizadas;
    }

    public void incrementarOperaciones() {
        this.operacionesRealizadas++;
    }

    public void comprarSuscripcionPremium() {
        this.tipoSuscripcion = "Premium";
        this.precio = 25.0;
        this.fechaInicio = new java.util.Date();
        this.fechaFinal = new java.util.Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)); // 30 días después
        this.operacionesRealizadas = 0; // Reiniciar el contador
    }

}
