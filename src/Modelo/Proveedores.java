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
public class Proveedores {
    String nombre, apellidoP, apellidoM, nombreComercial, tipoSociedad, cfdi, calle, codigoPostal, numeroInterior, numeroExterior, municipio, colonia, estado, telefono, regimenFiscal,rfc, correo, fecha, tipoPersona;
    int id, idRecibe, estatus;

    public Proveedores() {
    }

    public Proveedores(String nombre, String apellidoP, String apellidoM, String nombreComercial, String tipoSociedad, String cfdi, String calle, String codigoPostal, String numeroInterior, String numeroExterior, String municipio, String colonia, String estado, String telefono, String regimenFiscal, String rfc, String correo, String fecha, String tipoPersona, int id, int idRecibe, int estatus) {
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.nombreComercial = nombreComercial;
        this.tipoSociedad = tipoSociedad;
        this.cfdi = cfdi;
        this.calle = calle;
        this.codigoPostal = codigoPostal;
        this.numeroInterior = numeroInterior;
        this.numeroExterior = numeroExterior;
        this.municipio = municipio;
        this.colonia = colonia;
        this.estado = estado;
        this.telefono = telefono;
        this.regimenFiscal = regimenFiscal;
        this.rfc = rfc;
        this.correo = correo;
        this.fecha = fecha;
        this.tipoPersona = tipoPersona;
        this.id = id;
        this.idRecibe = idRecibe;
        this.estatus = estatus;
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

    public String getTipoSociedad() {
        return tipoSociedad;
    }

    public void setTipoSociedad(String tipoSociedad) {
        this.tipoSociedad = tipoSociedad;
    }

    public String getCfdi() {
        return cfdi;
    }

    public void setCfdi(String cfdi) {
        this.cfdi = cfdi;
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

    public String getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(String regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRecibe() {
        return idRecibe;
    }

    public void setIdRecibe(int idRecibe) {
        this.idRecibe = idRecibe;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

 
    
}
