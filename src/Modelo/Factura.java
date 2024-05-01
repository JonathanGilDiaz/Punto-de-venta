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
public class Factura {
    String folios, nombre, direccion, correo, regimen, cfdi, formaPago,rfc, fecha;
    double total, subtotal, descuento, iva;
    int id;

    public Factura() {
    }

    public Factura(String folios, String nombre, String direccion, String correo, String regimen, String cfdi, String formaPago, String rfc, String fecha, double total, double subtotal, double descuento, double iva, int id) {
        this.folios = folios;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.regimen = regimen;
        this.cfdi = cfdi;
        this.formaPago = formaPago;
        this.rfc = rfc;
        this.fecha = fecha;
        this.total = total;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.id = id;
    }
    
    

    public String getFolios() {
        return folios;
    }

    public void setFolios(String folios) {
        this.folios = folios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getCfdi() {
        return cfdi;
    }

    public void setCfdi(String cfdi) {
        this.cfdi = cfdi;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
