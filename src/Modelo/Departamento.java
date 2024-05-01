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
public class Departamento {
    
       String nombre, descripcion, fechs;
    int id, estado, idRecibe;
    double aumento, descuento;

    public Departamento() {
    }

    public Departamento(String nombre, String descripcion, String fechs, int id, int estado, int idRecibe, double aumento, double descuento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechs = fechs;
        this.id = id;
        this.estado = estado;
        this.idRecibe = idRecibe;
        this.aumento = aumento;
        this.descuento = descuento;
    }

    public int getIdRecibe() {
        return idRecibe;
    }

    public void setIdRecibe(int idRecibe) {
        this.idRecibe = idRecibe;
    }


    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechs() {
        return fechs;
    }

    public void setFechs(String fechs) {
        this.fechs = fechs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAumento() {
        return aumento;
    }

    public void setAumento(double aumento) {
        this.aumento = aumento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
}
