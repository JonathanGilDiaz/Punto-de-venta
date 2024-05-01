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
public class ReferenciasCliente {
    
     String tipo, nombre, apellidoP, apellidoM, nombreComercial, calle, codigoPostal, numeroInterior, numeroExterior, municipio, colonia, estado, telefono, correo, fechaCreacion, antiguedad, giroOcupacional;
    int id, idCliente;

    public ReferenciasCliente(String tipo, String nombre, String apellidoP, String apellidoM, String nombreComercial, String calle, String codigoPostal, String numeroInterior, String numeroExterior, String municipio, String colonia, String estado, String telefono, String correo, String fechaCreacion, String antiguedad, String giroOcupacional, int id, int idCliente) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.nombreComercial = nombreComercial;
        this.calle = calle;
        this.codigoPostal = codigoPostal;
        this.numeroInterior = numeroInterior;
        this.numeroExterior = numeroExterior;
        this.municipio = municipio;
        this.colonia = colonia;
        this.estado = estado;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaCreacion = fechaCreacion;
        this.antiguedad = antiguedad;
        this.giroOcupacional = giroOcupacional;
        this.id = id;
        this.idCliente = idCliente;
    }

    public ReferenciasCliente() {
    }
    
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getGiroOcupacional() {
        return giroOcupacional;
    }

    public void setGiroOcupacional(String giroOcupacional) {
        this.giroOcupacional = giroOcupacional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idRecibe) {
        this.idCliente = idRecibe;
    }
    
    
    
}
