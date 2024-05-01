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

public class HistorialClienteDao {
    
     Conexion cn = new Conexion(); //con este hacemos la conexion a base de datos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List retornarMovimientos(int idCliente){
         List <HistorialCliente> ListaCl = new ArrayList();
       String sql = "SELECT * FROM historialcliente where idCliente=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idCliente);
        rs=ps.executeQuery();
        while(rs.next()){
         HistorialCliente cl = new HistorialCliente();
         cl.setAbono(rs.getDouble("abono"));
         cl.setCargo(rs.getDouble("cargo"));
         cl.setFecha(rs.getString("fecha"));
         cl.setFolio(rs.getString("folio"));
         cl.setId(rs.getInt("id"));
         cl.setIdCliente(rs.getInt("idCliente"));
         cl.setIdRecibe(rs.getInt("idRecibe"));
         cl.setMovimiento(rs.getString("movimiento"));
         cl.setSaldo(rs.getDouble("saldo"));
         
         ListaCl.add(cl);
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"error al retornar los movimientos");
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
    
            public void registrarMovimiento(HistorialCliente hc){
        String sql = "INSERT INTO historialcliente (idCliente, fecha, movimiento, idRecibe, cargo, abono, saldo, folio) VALUES (?,?,?,?,?,?,?,?)";
                 
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, hc.getIdCliente());
            ps.setString(2, hc.getFecha());
            ps.setString(3, hc.getMovimiento());
            ps.setInt(4, hc.getIdRecibe());
            ps.setDouble(5, hc.getCargo());
            ps.setDouble(6, hc.getAbono());
            ps.setDouble(7, hc.getSaldo());
            ps.setString(8, hc.getFolio());
            ps.execute();
     
        }catch(SQLException e){
            System.out.println(e.toString()+"Error al registrarVenta");
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

      public HistorialCliente buscarPorFolio(String folio){
       String sql = "SELECT * FROM historialcliente where folio=?";
       HistorialCliente cl = new HistorialCliente();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, folio);
        rs=ps.executeQuery();
        while(rs.next()){
         cl.setAbono(rs.getDouble("abono"));
         cl.setCargo(rs.getDouble("cargo"));
         cl.setFecha(rs.getString("fecha"));
         cl.setFolio(rs.getString("folio"));
         cl.setId(rs.getInt("id"));
         cl.setIdCliente(rs.getInt("idCliente"));
         cl.setIdRecibe(rs.getInt("idRecibe"));
         cl.setMovimiento(rs.getString("movimiento"));
         cl.setSaldo(rs.getDouble("saldo"));
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"error al retornar los movimientos");
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
       return cl;
    }
    
}
