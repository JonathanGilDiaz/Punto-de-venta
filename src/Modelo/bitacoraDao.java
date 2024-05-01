/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonathan Gil
 */
public class bitacoraDao {
    
    Conexion cn = new Conexion(); //con este hacemos la conexion a base de datos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
public void borrarRegistros() {
    String sqlSafeModeOff = "SET SQL_SAFE_UPDATES = 0;";
    String sqlDelete = "DELETE FROM bitacora\n" +
                      "WHERE fecha < DATE_SUB(CURDATE(), INTERVAL 30 DAY);";
    
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sqlSafeModeOff);
        ps.executeUpdate(); // Desactivar el modo seguro
        
        ps = con.prepareStatement(sqlDelete);
        int rowsAffected = ps.executeUpdate(); // Ejecutar la instrucción DELETE
        
    } catch (SQLException e) {
        System.out.println(e.toString());
    } finally {
        try {
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString() + "6");
        }
    }
}
                  
                     public void desactivandoModoSeguro(){
        String sql = "SET SQL_SAFE_UPDATES = 0";
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
         try{
         if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
            if(con != null)
                con.close();
         }catch(SQLException ex){
                   System.out.println(ex.toString()+"6");
         }
          }
    }

    
    public boolean registrarRegistro(String accion, int empleado, String fecha){
         String sql = "INSERT INTO bitacora (accion, idEmpleado, fecha) VALUES (?,?,?)"; //sentencia sql
        try{
          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setString(1,accion); // se vacian los datos del cliente a la instrucciones
          ps.setInt(2, empleado);
          ps.setString(3, fecha);
          ps.execute(); // se ejecutar la instrruccion
          return true; //bandera para checar si se ejecuto correctamnete la instruccion
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.toString()+"Error al registrar bitacora");
            return false;
         } finally{
         try{
         if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
            if(con != null)
                con.close();
         }catch(SQLException ex){
                   System.out.println(ex.toString()+"6");
         }
          }
    }
    
        public List listarRegistros(){
            List <Bitacora> lsRegistros = new ArrayList();
       String sql = "SELECT b.*, e.nombre AS nombreEmpleado FROM bitacora AS b JOIN empleados AS e ON b.idEmpleado = e.idempleados;";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Bitacora b = new Bitacora();
         b.setId(rs.getInt("id"));
         b.setFecha(rs.getString("fecha"));
         b.setAccion(rs.getString("accion"));
         b.setIdRecibe(rs.getString("nombreEmpleado"));
         lsRegistros.add(b); //Agregar al arreglo
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"ph yea");
       }finally{
         try{
         if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
            if(con != null)
                con.close();
         }catch(SQLException ex){
                   System.out.println(ex.toString()+"6");
         }
          }
       return lsRegistros;
    }

           public List buscarLetra(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Bitacora> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
String sql = "SELECT b.*, e.nombre AS nombreEmpleado " +
             "FROM bitacora AS b " +
             "JOIN empleados AS e ON b.idEmpleado = e.idempleados " +
             "WHERE b.accion LIKE '%" + filtro + "%'";
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Bitacora b = new Bitacora(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
           b.setId(rs.getInt("id"));
         b.setFecha(rs.getString("fecha"));
         b.setAccion(rs.getString("accion"));
         b.setIdRecibe(rs.getString("nombreEmpleado"));
         ListaCl.add(b); //se agrega el cliente al arreglo
        }
       }catch(SQLException e){
           System.out.println(e.toString());
       }finally{
         try{
         if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
            if(con != null)
                con.close();
         }catch(SQLException ex){
                   System.out.println(ex.toString()+"6");
         }
          }
       return ListaCl; //se retorna la lista
        
    }
           
    public List listarPeriodo(String fechaInicio, String fechaFinal){//true es para la tabla
String sql = "SELECT b.*, e.nombre AS nombreEmpleado " +
             "FROM bitacora AS b " +
             "JOIN empleados AS e ON b.idEmpleado = e.idempleados " +
             "WHERE b.fecha BETWEEN ? AND ?";
       List <Bitacora> ListaCl = new ArrayList(); 
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
         ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);     
        rs=ps.executeQuery();
        while(rs.next()){
        Bitacora b = new Bitacora(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
           b.setId(rs.getInt("id"));
         b.setFecha(rs.getString("fecha"));
         b.setAccion(rs.getString("accion"));
         b.setIdRecibe(rs.getString("nombreEmpleado"));
         ListaCl.add(b);
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"1");
       }finally{
         try{
         if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
            if(con != null)
                con.close();
         }catch(SQLException ex){
                   System.out.println(ex.toString()+"6");
         }
          }
       return ListaCl;
    }


    
}
