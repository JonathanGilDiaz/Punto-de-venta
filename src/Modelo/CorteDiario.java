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
public class CorteDiario {

    int idRecibe, id;
    String comentario, fecha;
    double ganancia, saldoInicial, totalVentas,totalDescuentos,totalNotasCredito,totalPagos,totalGastos, ventasContado, ventasCredito, totalEntradas, totalSalidas, retiro, saldoFinal;

    public CorteDiario(int idRecibe, int id, String comentario, String fecha, double ganancia, double saldoInicial, double totalVentas, double totalDescuentos, double totalNotasCredito, double totalPagos, double totalGastos, double ventasContado, double ventasCredito, double totalEntradas, double totalSalidas, double retiro, double saldoFinal) {
        this.idRecibe = idRecibe;
        this.id = id;
        this.comentario = comentario;
        this.fecha = fecha;
        this.ganancia = ganancia;
        this.saldoInicial = saldoInicial;
        this.totalVentas = totalVentas;
        this.totalDescuentos = totalDescuentos;
        this.totalNotasCredito = totalNotasCredito;
        this.totalPagos = totalPagos;
        this.totalGastos = totalGastos;
        this.ventasContado = ventasContado;
        this.ventasCredito = ventasCredito;
        this.totalEntradas = totalEntradas;
        this.totalSalidas = totalSalidas;
        this.retiro = retiro;
        this.saldoFinal = saldoFinal;
    }

    
    
    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

    
    
    public int getIdRecibe() {
        return idRecibe;
    }

    public void setIdRecibe(int idRecibe) {
        this.idRecibe = idRecibe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public double getTotalNotasCredito() {
        return totalNotasCredito;
    }

    public void setTotalNotasCredito(double totalNotasCredito) {
        this.totalNotasCredito = totalNotasCredito;
    }

    public double getTotalPagos() {
        return totalPagos;
    }

    public void setTotalPagos(double totalPagos) {
        this.totalPagos = totalPagos;
    }

    public double getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(double totalGastos) {
        this.totalGastos = totalGastos;
    }

    public double getVentasContado() {
        return ventasContado;
    }

    public void setVentasContado(double ventasContado) {
        this.ventasContado = ventasContado;
    }

    public double getVentasCredito() {
        return ventasCredito;
    }

    public void setVentasCredito(double ventasCredito) {
        this.ventasCredito = ventasCredito;
    }

    public double getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(double totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public double getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(double totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    public double getRetiro() {
        return retiro;
    }

    public void setRetiro(double retiro) {
        this.retiro = retiro;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public CorteDiario() {
    }

    

}
