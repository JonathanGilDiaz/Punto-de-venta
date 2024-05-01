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
public class Perfil {
    
    String fecha, descripcion, nombre;
    int idRecibe, id, estado, 
       nuevaVenta, controlVentas, cotizaciones,
       proveedores, empleados, productos, clientes, departamento, linea, gastos, perfil, credito,
       corteVenta, bitacora, inventario, ordenesCompra, facturando,
       datosEmpresa, respaldar, restaurar,
    ventasR, notasCreditoR, productosR, concentradoGeneralR, datosClientesR, cobranzaR, inventarioR;

    public Perfil(int facturando, String fecha, String descripcion, String nombre, int idRecibe, int id, int estado, int nuevaVenta, int controlVentas, int cotizaciones, int proveedores, int empleados, int productos, int clientes, int departamento, int linea, int gastos, int perfil, int credito, int corteVenta, int bitacora, int inventario, int ordenesCompra, int datosEmpresa, int respaldar, int restaurar, int ventasR, int notasCreditoR, int productosR, int concentradoGeneralR, int datosClientesR, int cobranzaR, int inventarioR) {
        this.fecha = fecha;
        this.facturando=facturando;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.idRecibe = idRecibe;
        this.id = id;
        this.estado = estado;
        this.nuevaVenta = nuevaVenta;
        this.controlVentas = controlVentas;
        this.cotizaciones = cotizaciones;
        this.proveedores = proveedores;
        this.empleados = empleados;
        this.productos = productos;
        this.clientes = clientes;
        this.departamento = departamento;
        this.linea = linea;
        this.gastos = gastos;
        this.perfil = perfil;
        this.credito = credito;
        this.corteVenta = corteVenta;
        this.bitacora = bitacora;
        this.inventario = inventario;
        this.ordenesCompra = ordenesCompra;
        this.datosEmpresa = datosEmpresa;
        this.respaldar = respaldar;
        this.restaurar = restaurar;
        this.ventasR = ventasR;
        this.notasCreditoR = notasCreditoR;
        this.productosR = productosR;
        this.concentradoGeneralR = concentradoGeneralR;
        this.datosClientesR = datosClientesR;
        this.cobranzaR = cobranzaR;
        this.inventarioR = inventarioR;
    }

    public int getVentasR() {
        return ventasR;
    }

    public int getFacturando() {
        return facturando;
    }

    public void setFacturando(int facturando) {
        this.facturando = facturando;
    }
    
    

    public void setVentasR(int ventasR) {
        this.ventasR = ventasR;
    }

    public int getNotasCreditoR() {
        return notasCreditoR;
    }

    public void setNotasCreditoR(int notasCreditoR) {
        this.notasCreditoR = notasCreditoR;
    }

    public int getProductosR() {
        return productosR;
    }

    public void setProductosR(int productosR) {
        this.productosR = productosR;
    }

    public int getConcentradoGeneralR() {
        return concentradoGeneralR;
    }

    public void setConcentradoGeneralR(int concentradoGeneralR) {
        this.concentradoGeneralR = concentradoGeneralR;
    }

    public int getDatosClientesR() {
        return datosClientesR;
    }

    public void setDatosClientesR(int datosClientesR) {
        this.datosClientesR = datosClientesR;
    }

    public int getCobranzaR() {
        return cobranzaR;
    }

    public void setCobranzaR(int cobranzaR) {
        this.cobranzaR = cobranzaR;
    }

    public int getInventarioR() {
        return inventarioR;
    }

    public void setInventarioR(int inventarioR) {
        this.inventarioR = inventarioR;
    }


    
    public int getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(int cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    
    
    public Perfil() {
    }

    public String getFecha() {
        return fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    

    public int getDatosEmpresa() {
        return datosEmpresa;
    }

    public void setDatosEmpresa(int datosEmpresa) {
        this.datosEmpresa = datosEmpresa;
    }

    public int getRespaldar() {
        return respaldar;
    }

    public void setRespaldar(int respaldar) {
        this.respaldar = respaldar;
    }

    public int getRestaurar() {
        return restaurar;
    }

    public void setRestaurar(int restaurar) {
        this.restaurar = restaurar;
    }
    
    

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getNuevaVenta() {
        return nuevaVenta;
    }

    public void setNuevaVenta(int nuevaVenta) {
        this.nuevaVenta = nuevaVenta;
    }

    public int getControlVentas() {
        return controlVentas;
    }

    public void setControlVentas(int controlVentas) {
        this.controlVentas = controlVentas;
    }

    public int getProveedores() {
        return proveedores;
    }

    public void setProveedores(int proveedores) {
        this.proveedores = proveedores;
    }

    public int getEmpleados() {
        return empleados;
    }

    public void setEmpleados(int empleados) {
        this.empleados = empleados;
    }

    public int getProductos() {
        return productos;
    }

    public void setProductos(int productos) {
        this.productos = productos;
    }

    public int getClientes() {
        return clientes;
    }

    public void setClientes(int clientes) {
        this.clientes = clientes;
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

    public int getGastos() {
        return gastos;
    }

    public void setGastos(int gastos) {
        this.gastos = gastos;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public int getCorteVenta() {
        return corteVenta;
    }

    public void setCorteVenta(int corteVenta) {
        this.corteVenta = corteVenta;
    }

    public int getBitacora() {
        return bitacora;
    }

    public void setBitacora(int bitacora) {
        this.bitacora = bitacora;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public int getOrdenesCompra() {
        return ordenesCompra;
    }

    public void setOrdenesCompra(int ordenesCompra) {
        this.ordenesCompra = ordenesCompra;
    }
    
    
 
    
}
