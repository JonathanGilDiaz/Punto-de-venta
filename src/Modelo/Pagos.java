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
public class Pagos {
    int id, folioVenta, idRecibe;
    double aldoAnterior, nuevoSaldo, cantidad;
    String formaPago, fecha;

    public Pagos() {
    }

    public Pagos(int id, int folioVenta, int idRecibe, double aldoAnterior, double nuevoSaldo, double cantidad, String formaPago, String fecha) {
        this.id = id;
        this.folioVenta = folioVenta;
        this.idRecibe = idRecibe;
        this.aldoAnterior = aldoAnterior;
        this.nuevoSaldo = nuevoSaldo;
        this.cantidad = cantidad;
        this.formaPago = formaPago;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFolioVenta() {
        return folioVenta;
    }

    public void setFolioVenta(int folioVenta) {
        this.folioVenta = folioVenta;
    }

    public int getIdRecibe() {
        return idRecibe;
    }

    public void setIdRecibe(int idRecibe) {
        this.idRecibe = idRecibe;
    }

    public double getAldoAnterior() {
        return aldoAnterior;
    }

    public void setAldoAnterior(double aldoAnterior) {
        this.aldoAnterior = aldoAnterior;
    }

    public double getNuevoSaldo() {
        return nuevoSaldo;
    }

    public void setNuevoSaldo(double nuevoSaldo) {
        this.nuevoSaldo = nuevoSaldo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

  
}
