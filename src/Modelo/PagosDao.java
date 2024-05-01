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

/**
 *
 * @author Jonathan Gil
 */
public class PagosDao {
    
       Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
            public int idPagos(){
        int id=0;
        String sql = "SELECT MAX(id) FROM pagos";
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        
        }catch(SQLException e){
            System.out.println(e.toString()+"Error al regresar el maximo folio");
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
        return id;
    }

                public double retornarPorFormaPago(String fecha, String formaPago){
        double sumatoriaVentas = 0;
        try {
             con = cn.getConnection();
            // Establecer la conexión con la base de datos
            // Consulta para obtener la sumatoria de ventas
            String consultaVentas = "SELECT SUM(cantidad) AS sumatoriaPagos FROM pagos WHERE fecha = ? AND formaPago = ?";
            PreparedStatement pstmtVentas = con.prepareStatement(consultaVentas);
            pstmtVentas.setString(1, fecha);
            pstmtVentas.setString(2, formaPago);

            // Ejecutar la consulta de ventas
            ResultSet resultadoVentas = pstmtVentas.executeQuery();

            

            if (resultadoVentas.next()) {
                sumatoriaVentas = resultadoVentas.getDouble("sumatoriaPagos");
            }

            // Cerrar recursos
            resultadoVentas.close();
            pstmtVentas.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sumatoriaVentas;
    }
            
            
    public List listarPagosDia(String fecha){
       List<Pagos> lsPagos = new ArrayList<Pagos>();
       String sql = "SELECT * FROM pagos where fecha=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, fecha);
        rs=ps.executeQuery();
        while(rs.next()){
         Pagos p = new Pagos();
         p.setId(rs.getInt("id"));
         p.setFolioVenta(rs.getInt("folioVenta"));
         p.setFecha(rs.getString("fecha"));
         p.setCantidad(rs.getDouble("cantidad"));
         p.setFormaPago(rs.getString("formaPago"));
         p.setAldoAnterior(rs.getDouble("saldoAnterior"));
         p.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         p.setIdRecibe(rs.getInt("idRecibe"));
         lsPagos.add(p);
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
       return lsPagos;
    }
    
        public List listarPagosPeriodo(String fechaInicio, String fechaFinal){
       List<Pagos> lsPagos = new ArrayList<Pagos>();
       String sql = "SELECT * FROM pagos where fecha between ? and ?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);     
        rs=ps.executeQuery();
        while(rs.next()){
         Pagos p = new Pagos();
         p.setId(rs.getInt("id"));
         p.setFolioVenta(rs.getInt("folioVenta"));
         p.setFecha(rs.getString("fecha"));
         p.setCantidad(rs.getDouble("cantidad"));
         p.setFormaPago(rs.getString("formaPago"));
         p.setAldoAnterior(rs.getDouble("saldoAnterior"));
         p.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         p.setIdRecibe(rs.getInt("idRecibe"));
         lsPagos.add(p);
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
       return lsPagos;
    }

    
        public Pagos buscarPorId(int id){
       String sql = "SELECT * FROM pagos where id=?";
       Pagos p = new Pagos();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs=ps.executeQuery();
        while(rs.next()){
         p.setId(rs.getInt("id"));
         p.setFolioVenta(rs.getInt("folioVenta"));
         p.setFecha(rs.getString("fecha"));
         p.setCantidad(rs.getDouble("cantidad"));
         p.setFormaPago(rs.getString("formaPago"));
         p.setAldoAnterior(rs.getDouble("saldoAnterior"));
         p.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         p.setIdRecibe(rs.getInt("idRecibe"));
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
       return p;
    }


    
            public List listarPagos(int folioNota){
       List<Pagos> lsPagos = new ArrayList<Pagos>();
       String sql = "SELECT * FROM pagos where folioVenta=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, folioNota);
        rs=ps.executeQuery();
        while(rs.next()){
         Pagos p = new Pagos();
         p.setId(rs.getInt("id"));
         p.setFolioVenta(rs.getInt("folioVenta"));
         p.setFecha(rs.getString("fecha"));
         p.setCantidad(rs.getDouble("cantidad"));
         p.setFormaPago(rs.getString("formaPago"));
         p.setAldoAnterior(rs.getDouble("saldoAnterior"));
         p.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         p.setIdRecibe(rs.getInt("idRecibe"));
         lsPagos.add(p);
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
       return lsPagos;
    }
            
      public double regresarPagos(int folioNota){
       String sql = "SELECT SUM(cantidad) AS total_cantidad FROM pagos WHERE folioVenta = ?";
       double sumatoria=0;
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, folioNota);
        rs=ps.executeQuery();
        if(rs.next()){
            sumatoria=rs.getDouble("total_cantidad");
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
       return sumatoria;
    }


    
            public void registrarPago(Pagos p){
        String sql = "INSERT INTO pagos (folioVenta, fecha, formaPago, cantidad, saldoAnterior, nuevoSaldo, idRecibe) VALUES (?,?,?,?,?,?,?)";
                 
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, p.getFolioVenta());
            ps.setString(2, p.getFecha());
            ps.setString(3, p.getFormaPago());
            ps.setDouble(4, p.getCantidad());
            ps.setDouble(5, p.getAldoAnterior());
            ps.setDouble(6, p.getNuevoSaldo());
            ps.setInt(7, p.getIdRecibe());
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

}
