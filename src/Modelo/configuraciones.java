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

/**
 *
 * @author Jonathan Gil
 */
public class configuraciones {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
        public config buscarDatos(){
        config c = new config();
        String sql = "SELECT * FROM config";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        if(rs.next()){
         c.setNomnbre(rs.getString("nombre"));
         c.setDireccion(rs.getString("direccion"));
         c.setRazonSocial(rs.getString("razonSocial"));
         c.setRfc(rs.getString("rfc"));
         c.setTelefono(rs.getString("telefono"));
         c.setEncargado(rs.getString("encargado"));
         c.setHorario(rs.getString("horario"));
         c.setLetraGrande(rs.getInt("letraGrande"));
         c.setLetraChica(rs.getInt("letraChica"));
         c.setMensaje(rs.getString("mensaje"));

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
       
       return c;
    }
    
    //Metodo para modificar los datos de config (La informacion de la empresa)
    public boolean actualizarDatos(config cl){
     String sql = "UPDATE config SET rfc=?, nombre=?, telefono=?, razonSocial=?, direccion=?, encargado=?, horario=?, letraChica=?, letraGrande=?, mensaje=? WHERE id=?";
     try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
          ps.setString(1, cl.getRfc());
          ps.setString(2,cl.getNomnbre());
          ps.setString(3,cl.getTelefono());
          ps.setString(4,cl.getRazonSocial());
          ps.setString(5,cl.getDireccion());
          ps.setString(6,cl.getEncargado());
          ps.setString(7,cl.getHorario());
          ps.setInt(8, cl.getLetraChica());
          ps.setInt(9, cl.getLetraGrande());
          ps.setString(10, cl.getMensaje());
          ps.setInt(11, 1);
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
    
    public boolean cambiarLicencia(String fecha){
         String sql = "UPDATE licencia SET fecha=? WHERE id=1";
     try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
          ps.setString(1, fecha);
         
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

            public String retornarLicencia(){
        String sql = "SELECT * FROM licencia where id=1";
        String fecha="";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        if(rs.next()){
          fecha = rs.getString("fecha");
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
       
       return fecha;
    }

    
}
