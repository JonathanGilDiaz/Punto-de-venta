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
public class detalleVenta {
    String codigo, descripcion, tipo;
    int  id, folioVenta, folioNotaCredito;
    double cantidad, existencia, precioUnitario, importe, descuento, cantidadModificar;

    public detalleVenta(String tipo, String codigo, String descripcion, int id, int folioVenta, double cantidadModificar, int folioNotaCredito, double cantidad, double existencia, double precioUnitario, double importe, double descuento) {
        this.tipo=tipo;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.id = id;
        this.folioVenta = folioVenta;
        this.cantidadModificar = cantidadModificar;
        this.folioNotaCredito = folioNotaCredito;
        this.cantidad = cantidad;
        this.existencia = existencia;
        this.precioUnitario = precioUnitario;
        this.importe = importe;
        this.descuento = descuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
    
    
    public int getFolioNotaCredito() {
        return folioNotaCredito;
    }

    public void setFolioNotaCredito(int folioNotaCredito) {
        this.folioNotaCredito = folioNotaCredito;
    }


    public detalleVenta() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public double getCantidadModificar() {
        return cantidadModificar;
    }

    public void setCantidadModificar(double cantidadModificar) {
        this.cantidadModificar = cantidadModificar;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getExistencia() {
        return existencia;
    }

    public void setExistencia(double existencia) {
        this.existencia = existencia;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }



 
}
