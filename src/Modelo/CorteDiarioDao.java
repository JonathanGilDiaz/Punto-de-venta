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
public class CorteDiarioDao {
    
       Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    double saldoInicial;
    
         public String getDia(){
       String sql = "SELECT fecha FROM corte WHERE id = (SELECT MAX(id) from corte)";
                      String fecha="";
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                fecha=(rs.getString("fecha"));
            }
        }catch(SQLException e){
            System.out.println(e.toString()+"O AQUI");
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
         
        public double getSaldoInicial(){
       String sql = "SELECT saldoInicial FROM corte WHERE id = (SELECT MAX(id) from corte)";
       double saldoInicial=0;
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
               saldoInicial=rs.getDouble("saldoInicial");
            }
        }catch(SQLException e){
            System.out.println(e.toString()+"ALOMEJOR pobando");
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
        return saldoInicial;
    }
        
          public int idMax(){
       String sql = "SELECT MAX(id) from corte";
                      int num=0;
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                num=(rs.getInt(1));
            }
        }catch(SQLException e){
            System.out.println(e.toString()+"O AQUI");
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
        return num;
        }

            public void crearNuevoDia(CorteDiario c,String hoy){
    String sql = "INSERT INTO corte (fecha, saldoInicial) VALUES (?,?)";
        try{
          con = cn.getConnection();
          ps = con.prepareStatement(sql);
          ps.setString(1,hoy);
          ps.setDouble(2, c.getSaldoFinal());
          ps.execute();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.toString());
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
  
          
        public boolean terminarCorte(CorteDiario cl){
          String sql = "UPDATE corte SET totalVentas=?, totalDescuentos=?, totalNotasCredito=?, totalPagos=?, totalGastos=?, ventasContado=?, ventasCredito=?, totalEntradas=?, totalSalidas=?, retiro=?, saldoFinal=?, idRecibe=?, comentario=?, ganancia=? WHERE id = "+idMax();
     try{

                     con =cn.getConnection();
          ps=con.prepareStatement(sql);
          ps.setDouble(1, cl.getTotalVentas());
          ps.setDouble(2, cl.getTotalDescuentos());
          ps.setDouble(3, cl.getTotalNotasCredito());
          ps.setDouble(4, cl.getTotalPagos());
          ps.setDouble(5, cl.getTotalGastos());
          ps.setDouble(6, cl.getVentasContado());
          ps.setDouble(7, cl.getVentasCredito());
          ps.setDouble(8, cl.getTotalEntradas());
          ps.setDouble(9, cl.getTotalSalidas());
          ps.setDouble(10, cl.getRetiro());
          ps.setDouble(11, cl.getSaldoFinal());
          ps.setInt(12, cl.getIdRecibe());
          ps.setString(13, cl.getComentario());
          ps.setDouble(14, cl.getGanancia());
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

            public CorteDiario regresarCorte(String fecha){
        String sql = "SELECT * FROM corte WHERE fecha=?";
        CorteDiario c = new CorteDiario();
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, fecha);  
         rs=ps.executeQuery();
         if(rs.next()){
             c.setId(rs.getInt("id"));
             c.setFecha(rs.getString("fecha"));
             c.setIdRecibe(rs.getInt("idRecibe"));
             c.setComentario(rs.getString("comentario"));
             c.setSaldoInicial(rs.getDouble("saldoInicial"));
             c.setTotalVentas(rs.getDouble("totalVentas"));
             c.setTotalDescuentos(rs.getDouble("totalDescuentos"));
             c.setTotalNotasCredito(rs.getDouble("totalNotasCredito"));
             c.setTotalPagos(rs.getDouble("totalPagos"));
             c.setTotalGastos(rs.getDouble("totalGastos"));
             c.setVentasContado(rs.getDouble("ventasContado"));
             c.setVentasCredito(rs.getDouble("ventasCredito"));
             c.setTotalEntradas(rs.getDouble("totalEntradas"));
             c.setTotalSalidas(rs.getDouble("totalSalidas"));
             c.setRetiro(rs.getDouble("retiro"));
             c.setSaldoFinal(rs.getDouble("saldoFinal"));
             c.setGanancia(rs.getDouble("ganancia"));
         }else c.setId(0);
        }catch(SQLException ex){
         System.out.println(ex.toString());
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

     public List listarCortesPeriodo(String fechaInicio, String fechaFinal){//true es para la tabla
       String sql = "SELECT * FROM corte where fecha between ? and ?";
              List<CorteDiario> lsVentas = new ArrayList<CorteDiario>();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
         ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);     
        rs=ps.executeQuery();
        while(rs.next()){
                CorteDiario c = new CorteDiario();
            c.setId(rs.getInt("id"));
             c.setFecha(rs.getString("fecha"));
             c.setIdRecibe(rs.getInt("idRecibe"));
             c.setComentario(rs.getString("comentario"));
             c.setSaldoInicial(rs.getDouble("saldoInicial"));
             c.setTotalVentas(rs.getDouble("totalVentas"));
             c.setTotalDescuentos(rs.getDouble("totalDescuentos"));
             c.setTotalNotasCredito(rs.getDouble("totalNotasCredito"));
             c.setTotalPagos(rs.getDouble("totalPagos"));
             c.setTotalGastos(rs.getDouble("totalGastos"));
             c.setVentasContado(rs.getDouble("ventasContado"));
             c.setVentasCredito(rs.getDouble("ventasCredito"));
             c.setTotalEntradas(rs.getDouble("totalEntradas"));
             c.setTotalSalidas(rs.getDouble("totalSalidas"));
             c.setRetiro(rs.getDouble("retiro"));
             c.setSaldoFinal(rs.getDouble("saldoFinal"));
             c.setGanancia(rs.getDouble("ganancia"));
         lsVentas.add(c);
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
       return lsVentas;
    }

    
}
