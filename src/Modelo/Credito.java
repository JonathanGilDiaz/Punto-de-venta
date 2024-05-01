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
public class Credito {
    
    String nombreCliente, fecha, nombre, apellidoP, apellidoM, calle, codigoPostal, numeroInterior, numeroExterior, municipio, colonia, estado, correo, telefono, giroOcupacion, antiguedad, acreditado, documentoAcreditacion;
    int id, idCliente, vigencia, plazo, interesAplicado;
    double limite, interesOrdinario, interesMoratorio, adeudo;

    public Credito() {
    }

    public Credito(String nombreCliente, String fecha, String nombre, String apellidoP, String apellidoM, String calle, String codigoPostal, String numeroInterior, String numeroExterior, String municipio, String colonia, String estado, String correo, String telefono, String giroOcupacion, String antiguedad, String acreditado, String documentoAcreditacion, int id, int idCliente, int vigencia, int plazo, int interesAplicado, double limite, double interesOrdinario, double interesMoratorio, double adeudo) {
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.calle = calle;
        this.codigoPostal = codigoPostal;
        this.numeroInterior = numeroInterior;
        this.numeroExterior = numeroExterior;
        this.municipio = municipio;
        this.colonia = colonia;
        this.estado = estado;
        this.correo = correo;
        this.telefono = telefono;
        this.giroOcupacion = giroOcupacion;
        this.antiguedad = antiguedad;
        this.acreditado = acreditado;
        this.documentoAcreditacion = documentoAcreditacion;
        this.id = id;
        this.idCliente = idCliente;
        this.vigencia = vigencia;
        this.plazo = plazo;
        this.interesAplicado = interesAplicado;
        this.limite = limite;
        this.interesOrdinario = interesOrdinario;
        this.interesMoratorio = interesMoratorio;
        this.adeudo = adeudo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }


    
    public int getInteresAplicado() {
        return interesAplicado;
    }

    public void setInteresAplicado(int interesAplicado) {
        this.interesAplicado = interesAplicado;
    }

    public double getAdeudo() {
        return adeudo;
    }

    public void setAdeudo(double adeudo) {
        this.adeudo = adeudo;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGiroOcupacion() {
        return giroOcupacion;
    }

    public void setGiroOcupacion(String giroOcupacion) {
        this.giroOcupacion = giroOcupacion;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getAcreditado() {
        return acreditado;
    }

    public void setAcreditado(String acreditado) {
        this.acreditado = acreditado;
    }

    public String getDocumentoAcreditacion() {
        return documentoAcreditacion;
    }

    public void setDocumentoAcreditacion(String documentoAcreditacion) {
        this.documentoAcreditacion = documentoAcreditacion;
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

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getInteresOrdinario() {
        return interesOrdinario;
    }

    public void setInteresOrdinario(double interesOrdinario) {
        this.interesOrdinario = interesOrdinario;
    }

    public double getInteresMoratorio() {
        return interesMoratorio;
    }

    public void setInteresMoratorio(double interesMoratorio) {
        this.interesMoratorio = interesMoratorio;
    }
    
    
    
}
