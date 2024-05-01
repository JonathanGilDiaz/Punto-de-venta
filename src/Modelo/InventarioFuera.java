/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * Es para los productos que lo han sacado del inventario
 */
public class InventarioFuera {
    int id, idRecibe;
    String codigo, razon, fecha, descripcion;
    double existencia;

    public InventarioFuera() {
    }

    public InventarioFuera(int id, int idRecibe, String codigo, String razon, String fecha, String descripcion, double existencia) {
        this.id = id;
        this.idRecibe = idRecibe;
        this.codigo = codigo;
        this.razon = razon;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.existencia = existencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getExistencia() {
        return existencia;
    }

    public void setExistencia(double existencia) {
        this.existencia = existencia;
    }

    public int getIdRecibe() {
        return idRecibe;
    }

    public void setIdRecibe(int idRecibe) {
        this.idRecibe = idRecibe;
    }


    
    
}
