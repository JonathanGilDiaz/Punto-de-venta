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
public class Ventas {

    int folio, idCliente, idEmpleado, estatus, interesAplicado, facturado;
    double subTotal, iva, descuentos, total, saldo, notasCredito, pagos;
    String hora, fecha, metodoPago, formaPago, comentarios;
    double efectivo, cheque, transferencia, credito, debito;

    public Ventas() {
    }

    public Ventas(int folio, int idCliente, int idEmpleado, int estatus, int interesAplicado, int facturado, double subTotal, double iva, double descuentos, double total, double saldo, double notasCredito, double pagos, String hora, String fecha, String metodoPago, String formaPago, String comentarios, double efectivo, double cheque, double transferencia, double credito, double debito) {
        this.folio = folio;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.estatus = estatus;
        this.interesAplicado = interesAplicado;
        this.facturado = facturado;
        this.subTotal = subTotal;
        this.iva = iva;
        this.descuentos = descuentos;
        this.total = total;
        this.saldo = saldo;
        this.notasCredito = notasCredito;
        this.pagos = pagos;
        this.hora = hora;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.formaPago = formaPago;
        this.comentarios = comentarios;
        this.efectivo = efectivo;
        this.cheque = cheque;
        this.transferencia = transferencia;
        this.credito = credito;
        this.debito = debito;
    }

    public double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(double efectivo) {
        this.efectivo = efectivo;
    }

    public double getCheque() {
        return cheque;
    }

    public void setCheque(double cheque) {
        this.cheque = cheque;
    }

    public double getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(double transferencia) {
        this.transferencia = transferencia;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getDebito() {
        return debito;
    }

    public void setDebito(double debito) {
        this.debito = debito;
    }

    public int getFacturado() {
        return facturado;
    }

    public void setFacturado(int facturado) {
        this.facturado = facturado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getInteresAplicado() {
        return interesAplicado;
    }

    public void setInteresAplicado(int interesAplicado) {
        this.interesAplicado = interesAplicado;
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

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getNotasCredito() {
        return notasCredito;
    }

    public void setNotasCredito(double notasCredito) {
        this.notasCredito = notasCredito;
    }

    public double getPagos() {
        return pagos;
    }

    public void setPagos(double pagos) {
        this.pagos = pagos;
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

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

}
