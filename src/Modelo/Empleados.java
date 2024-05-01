//Clase para crear Empleados, esta sobrecargado con metodos getter y setter para sus variables
package Modelo;

/**
 *
 * @author Jonathan Gil
 */
public class Empleados {
    
    String nombre, contraseña,fechaCreacion;
    int id,estado, idPerfil;

    public Empleados(String nombre, String contraseña, String fechaCreacion, int id, int estado, int idPerfil) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.fechaCreacion = fechaCreacion;
        this.id = id;
        this.estado = estado;
        this.idPerfil = idPerfil;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    
    public Empleados() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    

   
    
    
    
}
