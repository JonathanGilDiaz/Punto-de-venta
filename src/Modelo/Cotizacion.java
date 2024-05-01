/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Jonathan Gil
 */
public class Cotizacion {

    int folio, idCliente, idEmpleado;
    double subTotal, iva, descuentos, total, anticipo;
    String hora, fecha, tiempoValidacion;

    public Cotizacion(int folio, int idCliente, int idEmpleado, double subTotal, double iva, double descuentos, double total, double anticipo, String hora, String fecha, String tiempoValidacion) {
        this.folio = folio;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.subTotal = subTotal;
        this.iva = iva;
        this.descuentos = descuentos;
        this.total = total;
        this.anticipo = anticipo;
        this.hora = hora;
        this.fecha = fecha;
        this.tiempoValidacion = tiempoValidacion;

    }

    public double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(double anticipo) {
        this.anticipo = anticipo;
    }

    public String getTiempoValidacion() {
        return tiempoValidacion;
    }

    public void setTiempoValidacion(String tiempoValidacion) {
        this.tiempoValidacion = tiempoValidacion;
    }

    public Cotizacion() {
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(double descuentos) {
        this.descuentos = descuentos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
