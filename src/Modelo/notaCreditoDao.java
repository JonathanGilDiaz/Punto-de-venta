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
 *cancelacion
 * @author Jonathan Gil
 */
public class notaCreditoDao {
        Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
              public int idNotaCredito(){
        
        String sql = "SELECT MAX(id) FROM notacredito";
        int id=0;
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
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
        return id;
    }
              
           public List listarNotasPorVenta(int folioVenta){//true es para la tabla
       List<notaCredito> lsVentas = new ArrayList<notaCredito>();
       String sql = "SELECT * FROM notacredito where idVenta=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, folioVenta);
        rs=ps.executeQuery();
        while(rs.next()){
        notaCredito n = new notaCredito();
         n.setId(rs.getInt("id"));
         n.setFolioVenta(rs.getInt("idVenta"));
         n.setSubtotal(rs.getDouble("subtotal"));
         n.setDescuento(rs.getDouble("descuentos"));
         n.setIva(rs.getDouble("iva"));
         n.setTotal(rs.getDouble("total"));
         n.setIdRecibe(rs.getInt("idRecibe"));
         n.setTipo(rs.getString("tipo"));
         n.setRazon(rs.getString("razon"));
         n.setFecha(rs.getString("fecha"));
         n.setHora(rs.getString("hora"));
         n.setSaldoViejo(rs.getDouble("saldoViejo"));
         n.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         n.setFormaPago(rs.getString("formaPago"));
         lsVentas.add(n);
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

                 public List listarNotasPorDia(String fecha){//true es para la tabla
       List<notaCredito> lsVentas = new ArrayList<notaCredito>();
       String sql = "SELECT * FROM notacredito where fecha=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, fecha);
        rs=ps.executeQuery();
        while(rs.next()){
        notaCredito n = new notaCredito();
         n.setId(rs.getInt("id"));
         n.setFolioVenta(rs.getInt("idVenta"));
         n.setSubtotal(rs.getDouble("subtotal"));
         n.setDescuento(rs.getDouble("descuentos"));
         n.setIva(rs.getDouble("iva"));
         n.setTotal(rs.getDouble("total"));
         n.setIdRecibe(rs.getInt("idRecibe"));
         n.setTipo(rs.getString("tipo"));
         n.setRazon(rs.getString("razon"));
         n.setFecha(rs.getString("fecha"));
         n.setHora(rs.getString("hora"));
         n.setSaldoViejo(rs.getDouble("saldoViejo"));
         n.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         n.setFormaPago(rs.getString("formaPago"));
         lsVentas.add(n);
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
     
              
       public List listarNotas(){//true es para la tabla
       List<notaCredito> lsVentas = new ArrayList<notaCredito>();
       String sql = "SELECT * FROM notacredito";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
        notaCredito n = new notaCredito();
         n.setId(rs.getInt("id"));
         n.setFolioVenta(rs.getInt("idVenta"));
         n.setSubtotal(rs.getDouble("subtotal"));
         n.setDescuento(rs.getDouble("descuentos"));
         n.setIva(rs.getDouble("iva"));
         n.setTotal(rs.getDouble("total"));
         n.setIdRecibe(rs.getInt("idRecibe"));
         n.setTipo(rs.getString("tipo"));
         n.setRazon(rs.getString("razon"));
         n.setFecha(rs.getString("fecha"));
         n.setHora(rs.getString("hora"));
         n.setSaldoViejo(rs.getDouble("saldoViejo"));
         n.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         n.setFormaPago(rs.getString("formaPago"));
         lsVentas.add(n);
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

              public List listarNotasSoloUna(int folio){//true es para la tabla
       String sql = "SELECT * FROM notacredito where id=?";
                     List<notaCredito> lsVentas = new ArrayList<notaCredito>();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, folio);
        rs=ps.executeQuery();
        while(rs.next()){
         notaCredito n = new notaCredito();
       n.setId(rs.getInt("id"));
         n.setFolioVenta(rs.getInt("idVenta"));
         n.setSubtotal(rs.getDouble("subtotal"));
         n.setDescuento(rs.getDouble("descuentos"));
         n.setIva(rs.getDouble("iva"));
         n.setTotal(rs.getDouble("total"));
         n.setIdRecibe(rs.getInt("idRecibe"));
         n.setTipo(rs.getString("tipo"));
         n.setRazon(rs.getString("razon"));
         n.setFecha(rs.getString("fecha"));
         n.setHora(rs.getString("hora"));
         n.setSaldoViejo(rs.getDouble("saldoViejo"));
         n.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         n.setFormaPago(rs.getString("formaPago"));
         lsVentas.add(n);
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
      
                public List listarNotasPeriodo(String fechaInicio, String fechaFinal){//true es para la tabla
       String sql = "SELECT * FROM notacredito where fecha between ? and ?";
              List<notaCredito> lsVentas = new ArrayList<notaCredito>();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
         ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);     
        rs=ps.executeQuery();
        while(rs.next()){
         notaCredito n = new notaCredito();
         n.setId(rs.getInt("id"));
         n.setFolioVenta(rs.getInt("idVenta"));
         n.setSubtotal(rs.getDouble("subtotal"));
         n.setDescuento(rs.getDouble("descuentos"));
         n.setIva(rs.getDouble("iva"));
         n.setTotal(rs.getDouble("total"));
         n.setIdRecibe(rs.getInt("idRecibe"));
         n.setTipo(rs.getString("tipo"));
         n.setRazon(rs.getString("razon"));
         n.setFecha(rs.getString("fecha"));
         n.setHora(rs.getString("hora"));
         n.setSaldoViejo(rs.getDouble("saldoViejo"));
         n.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         n.setFormaPago(rs.getString("formaPago"));
         lsVentas.add(n);
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

          public notaCredito buscarPorId(int a){
        String sql = "SELECT * FROM notacredito WHERE id = ? ";
        notaCredito n = new notaCredito();
       boolean bandera = false;
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
        if(rs.next()){
         n.setId(rs.getInt("id"));
         n.setFolioVenta(rs.getInt("idVenta"));
         n.setSubtotal(rs.getDouble("subtotal"));
         n.setDescuento(rs.getDouble("descuentos"));
         n.setIva(rs.getDouble("iva"));
         n.setTotal(rs.getDouble("total"));
         n.setIdRecibe(rs.getInt("idRecibe"));
         n.setTipo(rs.getString("tipo"));
         n.setRazon(rs.getString("razon"));
         n.setFecha(rs.getString("fecha"));
         n.setHora(rs.getString("hora"));
         n.setSaldoViejo(rs.getDouble("saldoViejo"));
         n.setNuevoSaldo(rs.getDouble("nuevoSaldo"));
         n.setFormaPago(rs.getString("formaPago"));
         bandera=true;
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"13");
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
           n.setFecha("fallo");
       }
                System.out.println(n.getHora()+"yeaaaaaaaah");
       return n;
    }

                public void registrarVentaCancelada(notaCredito nota){
        String sql = "INSERT INTO notacredito (idVenta, fecha, hora, idRecibe, total, razon, saldoViejo, nuevoSaldo, tipo, formaPago) VALUES (?,?,?,?,?,?,?,?,'Cancelación',?)";
        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, nota.getFolioVenta());
            ps.setString(2, nota.getFecha());
            ps.setString(3, nota.getHora());
            ps.setInt(4, nota.getIdRecibe());
            ps.setDouble(5, nota.getTotal());
            ps.setString(6, nota.getRazon());
            ps.setDouble(7, nota.getSaldoViejo());
            ps.setDouble(8, nota.getNuevoSaldo());
            ps.setString(9, nota.getFormaPago());
            ps.execute();
        }catch(SQLException e){
         System.out.println(e.toString());
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
                
      public void registrarVenta(notaCredito nota){
        String sql = "INSERT INTO notacredito (idVenta, fecha, hora, idRecibe, total, razon, saldoViejo, nuevoSaldo, subtotal, iva, tipo, formaPago) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, nota.getFolioVenta());
            ps.setString(2, nota.getFecha());
            ps.setString(3, nota.getHora());
            ps.setInt(4, nota.getIdRecibe());
            ps.setDouble(5, nota.getTotal());
            ps.setString(6, nota.getRazon());
            ps.setDouble(7, nota.getSaldoViejo());
            ps.setDouble(8, nota.getNuevoSaldo());
            ps.setDouble(9, nota.getSubtotal());
            ps.setDouble(10, nota.getIva());
            ps.setString(11, nota.getTipo());
            ps.setString(12, nota.getFormaPago());
            ps.execute();
        }catch(SQLException e){
         System.out.println(e.toString());
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


    public double retornarSumaNotas(int idVenta){
        String sql = "SELECT SUM(total) AS totalS from notacredito where idVenta=? ";
        double saldo=0;
        try{
         con = cn.getConnection();
         ps = con.prepareStatement(sql);
         ps.setInt(1, idVenta);
         rs=ps.executeQuery();
        if(rs.next()){
            saldo=rs.getDouble("totalS");
        }
        }catch(SQLException e){
         System.out.println(e.toString());
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
        return saldo;
    }
    
           public List regresarDetalles(int id, String tipo){
        List <detalleVenta> listaDetalle = new ArrayList();
        String sql = "SELECT * FROM detallenotacredito WHERE folioNota = ? and tipo = ?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, tipo);
        rs=ps.executeQuery();
        while(rs.next()){
         detalleVenta detalle = new detalleVenta();
         detalle.setId(rs.getInt("id"));
         detalle.setFolioVenta(rs.getInt("folioNota"));
         detalle.setFolioNotaCredito(rs.getInt("folioNotaCredito"));
         detalle.setCantidad(rs.getDouble("cantidad"));
         detalle.setCodigo(rs.getString("codigo"));
         detalle.setDescripcion(rs.getString("descripcion"));
         detalle.setPrecioUnitario(rs.getDouble("precio"));
         detalle.setCantidadModificar(rs.getDouble("monto"));
         detalle.setTipo(rs.getString("tipo"));
         detalle.setDescuento(rs.getDouble("ultimoDato"));
         detalle.setImporte(rs.getDouble("importe"));
         listaDetalle.add(detalle);
        }

       }catch(SQLException e){
           System.out.println(e.toString()+"19");
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
       
       return listaDetalle;
    }
           
                      public List regresarDetallesSoloUna(int id){
        List <detalleVenta> listaDetalle = new ArrayList();
        String sql = "SELECT * FROM detallenotacredito WHERE folioNotaCredito = ?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs=ps.executeQuery();
        while(rs.next()){
         detalleVenta detalle = new detalleVenta();
         detalle.setId(rs.getInt("id"));
         detalle.setFolioVenta(rs.getInt("folioNota"));
         detalle.setFolioNotaCredito(rs.getInt("folioNotaCredito"));
         detalle.setCantidad(rs.getDouble("cantidad"));
         detalle.setCodigo(rs.getString("codigo"));
         detalle.setDescripcion(rs.getString("descripcion"));
         detalle.setPrecioUnitario(rs.getDouble("precio"));
         detalle.setCantidadModificar(rs.getDouble("monto"));
         detalle.setTipo(rs.getString("tipo"));
         detalle.setDescuento(rs.getDouble("ultimoDato"));
         detalle.setImporte(rs.getDouble("importe"));
         listaDetalle.add(detalle);
        }

       }catch(SQLException e){
           System.out.println(e.toString()+"19");
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
       
       return listaDetalle;
    }


           public void registrarDetalle(detalleVenta dv, double ultimoDato){
        String sql = "INSERT INTO detallenotacredito (folioNota, folioNotaCredito, codigo, descripcion, precio, cantidad, monto, tipo, importe, ultimoDato) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, dv.getFolioVenta());
            ps.setInt(2, dv.getFolioNotaCredito());
            ps.setString(3, dv.getCodigo());
            ps.setString(4, dv.getDescripcion());
            ps.setDouble(5, dv.getPrecioUnitario());
            ps.setDouble(6, dv.getCantidad());
            ps.setDouble(7, dv.getCantidadModificar());
            ps.setString(8, dv.getTipo());
            ps.setDouble(9, dv.getImporte());
            ps.setDouble(10, ultimoDato);
            ps.execute();
        }catch(SQLException e){
         System.out.println(e.toString());
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


    
}
