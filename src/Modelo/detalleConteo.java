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
public class detalleConteo {
    
    int id, idConteo;
    String codigo, descripcion, unidad;
    double piezas, diferencia, fisico; //pieza es lo que habia

    public detalleConteo() {
    }

    public detalleConteo(int id, int idConteo, String codigo, String descripcion, String unidad, double piezas, double diferencia, double fisico) {
        this.id = id;
        this.idConteo = idConteo;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.piezas = piezas;
        this.diferencia = diferencia;
        this.fisico = fisico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConteo() {
        return idConteo;
    }

    public void setIdConteo(int idConteo) {
        this.idConteo = idConteo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String nombre) {
        this.codigo = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getPiezas() {
        return piezas;
    }

    public void setPiezas(double piezas) {
        this.piezas = piezas;
    }

    public double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(double diferencia) {
        this.diferencia = diferencia;
    }

    public double getFisico() {
        return fisico;
    }

    public void setFisico(double fisico) {
        this.fisico = fisico;
    }
    
    
}
