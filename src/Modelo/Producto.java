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
public class Producto {
    String codigo, descripcion, fechaCreacion, tipoVenta;
    double precioUsuario, precioCompra, precioVenta, existencia, minimo;
    int id, aviso, idEmpleado, estado, departamento, linea, inventario;

    public Producto() {
    }

    public Producto(String codigo, String descripcion, String fechaCreacion, String tipoVenta, double precioUsuario, double precioCompra, double precioVenta, double existencia, double minimo, int id, int aviso, int idEmpleado, int estado, int departamento, int linea, int inventario) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.tipoVenta = tipoVenta;
        this.precioUsuario = precioUsuario;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.existencia = existencia;
        this.minimo = minimo;
        this.id = id;
        this.aviso = aviso;
        this.idEmpleado = idEmpleado;
        this.estado = estado;
        this.departamento = departamento;
        this.linea = linea;
        this.inventario = inventario;
    }

    public double getPrecioUsuario() {
        return precioUsuario;
    }

    public void setPrecioUsuario(double precioUsuario) {
        this.precioUsuario = precioUsuario;
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getExistencia() {
        return existencia;
    }

    public void setExistencia(double existencia) {
        this.existencia = existencia;
    }

    public double getMinimo() {
        return minimo;
    }

    public void setMinimo(double minimo) {
        this.minimo = minimo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAviso() {
        return aviso;
    }

    public void setAviso(int aviso) {
        this.aviso = aviso;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }


    
}
