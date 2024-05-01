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
import javax.swing.JOptionPane;

/**
 *
 * @author Jonathan Gil
 */
public class ReferenciasClienteDao {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
           public ReferenciasCliente BuscarPorCodigoClienteTipo(int a, String tipo){
          String sql = "SELECT * FROM referenciascliente WHERE idCliente = ? AND tipo";
          ReferenciasCliente cl = new ReferenciasCliente();
                  boolean bandera = false;   
          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
         if(rs.next()){
         cl.setId(rs.getInt("id"));
         cl.setIdCliente(rs.getInt("idCliente"));
         cl.setNombre(rs.getString("nombre"));
         cl.setApellidoP(rs.getString("apellidoP"));
         cl.setApellidoM(rs.getString("ApellidoM"));
         cl.setCalle(rs.getString("calle"));
         cl.setCodigoPostal(rs.getString("codigoPostal"));
         cl.setNumeroInterior(rs.getString("numeroInterior"));
         cl.setNumeroExterior(rs.getString("numeroExterior"));
         cl.setMunicipio(rs.getString("municipio"));
         cl.setColonia(rs.getString("colonia"));
         cl.setEstado(rs.getString("estado"));
         cl.setCorreo(rs.getString("correo"));
         cl.setTelefono(rs.getString("telefono"));
         cl.setGiroOcupacional(rs.getString("giroOcupacion"));
         cl.setAntiguedad(rs.getString("antiguedad"));
         cl.setTipo(rs.getString("tipo"));
         bandera=true;
            }else cl.setId(0);
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
       if(bandera==false) {
       cl.setNombre("");
       } 
       return cl;

    }

    
      public boolean RegistrarReferencia(ReferenciasCliente r){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO referenciascliente (idCliente, tipo, nombre, apellidoP, apellidoM, nombreComercial, calle, codigoPostal, numeroInterior, numeroExterior, municipio, colonia, estado, correo, telefono, giroOcupacion, antiguedad) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //sentencia sql
        try{

          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setInt(1, r.getIdCliente()); // se vacian los datos del cliente a la instrucciones
          ps.setString(2, r.getTipo());
          ps.setString(3, r.getNombre());
          ps.setString(4, r.getApellidoP());
          ps.setString(5, r.getApellidoM());
          ps.setString(6, r.getNombreComercial());
          ps.setString(7, r.getCalle());
          ps.setString(8, r.getCodigoPostal());
          ps.setString(9, r.getNumeroInterior());
          ps.setString(10, r.getNumeroExterior());
          ps.setString(11, r.getMunicipio());
          ps.setString(12, r.getColonia());
          ps.setString(13, r.getEstado());
          ps.setString(14, r.getCorreo());
          ps.setString(15, r.getTelefono());
          ps.setString(16, r.getGiroOcupacional());
          ps.setString(17, r.getAntiguedad());

          ps.execute(); // se ejecutar la instrruccion
          return true; //bandera para checar si se ejecuto correctamnete la instruccion
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.toString()+"Error con las referencias");
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
      
           public boolean EliminarReferencias(int id){ 
      String sql = "DELETE FROM referenciascliente WHERE idCliente = ?";
      try{
                  con = cn.getConnection();
         ps = con.prepareStatement(sql);
         ps.setInt(1,id);
         ps.execute();
         return true;
      }catch(SQLException e){
        System.out.println(e.toString());
        return false;
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


    
}
