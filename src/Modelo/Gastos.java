//Metodo para construir los gastos
//Esta sobrecargado con metodos getter y setter de cada variable

package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Gastos {

    private String comprobante, formaPago;
    private String descripcion;
    private double precio;
    private int id, idRecibe;
    private String fecha;    

    public Gastos(String comprobante, String descripcion, double precio, int id, String formaPago, int idRecibe, String fecha) {
        this.comprobante = comprobante;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id = id;
        this.formaPago = formaPago;
        this.idRecibe = idRecibe;
        this.fecha = fecha;
    }

    public int getIdRecibe() {
        return idRecibe;
    }

    public void setIdRecibe(int idRecibe) {
        this.idRecibe = idRecibe;
    }


    
    
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
  
    
    public Gastos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
}
