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
public class notaCredito {
    
    String razon, tipo, fecha, hora, formaPago;
    double subtotal, iva, descuento, total, saldoViejo, nuevoSaldo;
    int id, folioVenta, idRecibe;

    public notaCredito(String formaPago,String razon, String tipo, String fecha, String hora, double subtotal, double iva, double descuento, double total, double saldoViejo, double nuevoSaldo, int id, int folioVenta, int idRecibe) {
        this.razon = razon;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.subtotal = subtotal;
        this.iva = iva;
        this.descuento = descuento;
        this.total = total;
        this.saldoViejo = saldoViejo;
        this.nuevoSaldo = nuevoSaldo;
        this.id = id;
        this.folioVenta = folioVenta;
        this.idRecibe = idRecibe;
        this.formaPago=formaPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
    

    public double getSaldoViejo() {
        return saldoViejo;
    }

    public void setSaldoViejo(double saldoViejo) {
        this.saldoViejo = saldoViejo;
    }

    public double getNuevoSaldo() {
        return nuevoSaldo;
    }

    public void setNuevoSaldo(double nuevoSaldo) {
        this.nuevoSaldo = nuevoSaldo;
    }
    
    

    public notaCredito() {
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
    
    
}
