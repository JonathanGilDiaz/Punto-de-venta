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
public class ordenCompra {
    int id, idProveedor, IdRecibe, plazoCredito;
    double total, subtotal, descuento, iva, interesCredito;
    String fecha, formaPago, metodoPago;

    public ordenCompra() {
    }

    public ordenCompra(int id, int idProveedor, int IdRecibe, int plazoCredito, double total, double subtotal, double descuento, double iva, double interesCredito, String fecha, String formaPago, String metodoPago) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.IdRecibe = IdRecibe;
        this.plazoCredito = plazoCredito;
        this.total = total;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.interesCredito = interesCredito;
        this.fecha = fecha;
        this.formaPago = formaPago;
        this.metodoPago = metodoPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdRecibe() {
        return IdRecibe;
    }

    public void setIdRecibe(int IdRecibe) {
        this.IdRecibe = IdRecibe;
    }

    public int getPlazoCredito() {
        return plazoCredito;
    }

    public void setPlazoCredito(int plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getInteresCredito() {
        return interesCredito;
    }

    public void setInteresCredito(double interesCredito) {
        this.interesCredito = interesCredito;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

   
    
    
}
