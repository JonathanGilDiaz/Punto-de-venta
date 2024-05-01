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
public class FacturaDao {
    
    Conexion cn = new Conexion(); //con este hacemos la conexion a base de datos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
                public boolean registrrFactura(Factura f){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO factura (fecha, nombre, direccion, correo, regimen, cfdi, formaPago, rfc, subtotal, total, iva, descuento, folios) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; //sentencia sql
        try{
          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setString(1, f.getFecha()); // se vacian los datos del cliente a la instrucciones
          ps.setString(2, f.getNombre());
          ps.setString(3, f.getDireccion());
          ps.setString(4, f.getCorreo());
          ps.setString(5, f.getRegimen());
          ps.setString(6, f.getCfdi());
          ps.setString(7, f.getFormaPago());
          ps.setString(8, f.getRfc());
          ps.setDouble(9, f.getSubtotal());
          ps.setDouble(10, f.getTotal());
          ps.setDouble(11, f.getIva());
          ps.setDouble(12, f.getDescuento());
          ps.setString(13, f.getFolios());

          ps.execute(); // se ejecutar la instrruccion
          return true; //bandera para checar si se ejecuto correctamnete la instruccion
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.toString()+"Error el registrar factura");
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
                
                public int idFactura(){
        
        String sql = "SELECT MAX(id) FROM factura";
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

    public void registrarDetalle(detalleVenta dv){
        String sql = "INSERT INTO detallefactura (folioFactura, cantidad, codigo, descripcion, precioUnitario, importe, existencia, descuento) VALUES (?,?,?,?,?,?,?,?)";
        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, dv.getFolioVenta());
            ps.setDouble(2, dv.getCantidad());
            ps.setString(3, dv.getCodigo());
            ps.setString(4, dv.getDescripcion());
            ps.setDouble(5, dv.getPrecioUnitario());
            ps.setDouble(6, dv.getImporte());
            ps.setDouble(7, dv.getExistencia());
            ps.setDouble(8, dv.getDescuento());
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

       public boolean cambiarIndicador(int folio){
     String sql = "UPDATE ventas SET facturado=1 WHERE folio=?";
     try{
          con =cn.getConnection();
          ps=con.prepareStatement(sql);
          ps.setInt(1,folio);
          ps.execute();
          return true;
     }catch(SQLException e){
         System.out.println(e.toString()+"falla aqui");
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
       
               public List listarFacturas(){//true es para la tabla
       List<Factura> lsFactura = new ArrayList<Factura>();
       String sql = "SELECT * FROM factura";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Factura f = new Factura();
            f.setId(rs.getInt("id"));
            f.setFecha(rs.getString("fecha"));
            f.setNombre(rs.getString("nombre"));
            f.setDireccion(rs.getString("direccion"));
            f.setCorreo(rs.getString("correo"));
            f.setRegimen(rs.getString("regimen"));
            f.setCfdi(rs.getString("cfdi"));
            f.setFormaPago(rs.getString("formaPago"));
            f.setRfc(rs.getString("rfc"));
            f.setSubtotal(rs.getDouble("subtotal"));
            f.setDescuento(rs.getDouble("descuento"));
            f.setIva(rs.getDouble("iva"));
            f.setTotal(rs.getDouble("total"));
            f.setFolios(rs.getString("folios"));
         lsFactura.add(f);
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
       return lsFactura;
    }

         public Factura BuscarPorCodigo(int a){
          String sql = "SELECT * FROM factura WHERE id = ?";
         Factura f = new Factura();
                  boolean bandera = false;   
          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
         if(rs.next()){
           f.setId(rs.getInt("id"));
            f.setFecha(rs.getString("fecha"));
            f.setNombre(rs.getString("nombre"));
            f.setDireccion(rs.getString("direccion"));
            f.setCorreo(rs.getString("correo"));
            f.setRegimen(rs.getString("regimen"));
            f.setCfdi(rs.getString("cfdi"));
            f.setFormaPago(rs.getString("formaPago"));
            f.setRfc(rs.getString("rfc"));
            f.setSubtotal(rs.getDouble("subtotal"));
            f.setDescuento(rs.getDouble("descuento"));
            f.setIva(rs.getDouble("iva"));
            f.setTotal(rs.getDouble("total"));
            f.setFolios(rs.getString("folios"));
         bandera=true;
            }
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
       if(bandera==false) {
       f.setNombre("");
       } 
       return f;

    }

        public List buscarLetra(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Factura> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
       String sql = "SELECT * FROM factura WHERE nombre LIKE"+'"'+filtro+'"';//Sentencia sql
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Factura f = new Factura(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
            f.setId(rs.getInt("id"));
            f.setFecha(rs.getString("fecha"));
            f.setNombre(rs.getString("nombre"));
            f.setDireccion(rs.getString("direccion"));
            f.setCorreo(rs.getString("correo"));
            f.setRegimen(rs.getString("regimen"));
            f.setCfdi(rs.getString("cfdi"));
            f.setFormaPago(rs.getString("formaPago"));
            f.setRfc(rs.getString("rfc"));
            f.setSubtotal(rs.getDouble("subtotal"));
            f.setDescuento(rs.getDouble("descuento"));
            f.setIva(rs.getDouble("iva"));
            f.setTotal(rs.getDouble("total"));
            f.setFolios(rs.getString("folios"));
         ListaCl.add(f); //se agrega el cliente al arreglo
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
        
                  public List regresarDetalles(int id){
        List <detalleVenta> listaDetalle = new ArrayList();
        String sql = "SELECT * FROM detallefactura WHERE folioFactura = ? ";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs=ps.executeQuery();
        int a=0;
        while(rs.next()){
         detalleVenta detalle = new detalleVenta();
         detalle.setCantidad(rs.getDouble("cantidad"));
         detalle.setCodigo(rs.getString("codigo"));
         detalle.setDescripcion(rs.getString("descripcion"));
         detalle.setExistencia(rs.getDouble("existencia"));
         detalle.setImporte(rs.getDouble("importe"));
         detalle.setPrecioUnitario(rs.getDouble("precioUnitario"));
         detalle.setDescuento(rs.getDouble("descuento"));

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



}
