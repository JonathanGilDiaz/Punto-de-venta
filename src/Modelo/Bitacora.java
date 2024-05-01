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
public class Bitacora {
    
    int id;
    String idRecibe, fecha, accion;

    public Bitacora(int id, String idRecibe, String fecha, String accion) {
        this.id = id;
        this.idRecibe = idRecibe;
        this.fecha = fecha;
        this.accion = accion;
    }

    public Bitacora() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdRecibe() {
        return idRecibe;
    }

    public void setIdRecibe(String idRecibe) {
        this.idRecibe = idRecibe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    
    
}
