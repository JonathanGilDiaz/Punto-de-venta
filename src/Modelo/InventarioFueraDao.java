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
public class InventarioFueraDao {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ProductoDao productoDao = new ProductoDao();
    
        public boolean registrarMovimiento(InventarioFuera inv){ 
      String sql = "INSERT INTO inventariofuera (codigo, existencia, razon, fecha, idRecibe) VALUES (?,?,?,?,?)";
        try{
          con = cn.getConnection();
          ps = con.prepareStatement(sql);
          ps.setString(1,inv.getCodigo());
          ps.setDouble(2, inv.getExistencia());
          ps.setString(3, inv.getRazon());
          ps.setString(4, inv.getFecha());
          ps.setInt(5, inv.getIdRecibe());
          ps.execute();
          return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.toString());
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
    
            public void actualizarCodigo(String viejoCodigo, String nuevoCodigo){
             String sql = "UPDATE inventariofuera SET codigo = ? WHERE codigo = ?";
        try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, viejoCodigo);
        ps.setString(2, nuevoCodigo);
          ps.execute(); // se ejecuta la instruccion   
       }catch(SQLException e){
           System.out.println(e.toString()+" error al actualizar el codigo");
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
            
            public List listarInventario(){//true es para la tabla
       String sql = "SELECT inventariofuera.*, producto.descripcion FROM inventariofuera JOIN producto ON inventariofuera.codigo = producto.codigo;";
       List<InventarioFuera> lsInventario = new ArrayList<InventarioFuera>();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         InventarioFuera inv = new InventarioFuera();
         inv.setId(rs.getInt("id"));
         inv.setCodigo(rs.getString("codigo"));
         inv.setDescripcion(rs.getString("descripcion"));
         inv.setExistencia(rs.getDouble("existencia"));
         inv.setFecha(rs.getString("fecha"));
         inv.setIdRecibe(rs.getInt("idRecibe"));
         inv.setRazon(rs.getString("razon"));
         lsInventario.add(inv);
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
       return lsInventario;
    }

       public List listarPeriodo(String fechaInicio, String fechaFinal){//true es para la tabla
       String sql = "SELECT inventariofuera.*, producto.descripcion FROM inventariofuera JOIN producto ON inventariofuera.codigo = producto.codigo where inventariofuera.fecha between ? and ?";
       List <InventarioFuera> ListaCl = new ArrayList(); 
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
         ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);     
        rs=ps.executeQuery();
        while(rs.next()){
         InventarioFuera inv = new InventarioFuera();
         inv.setId(rs.getInt("id"));
         inv.setCodigo(rs.getString("codigo"));
         inv.setDescripcion(rs.getString("descripcion"));
         inv.setExistencia(rs.getDouble("existencia"));
         inv.setFecha(rs.getString("fecha"));
         inv.setIdRecibe(rs.getInt("idRecibe"));
         inv.setRazon(rs.getString("razon"));
         ListaCl.add(inv);
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
       
               public List buscarLetraCodigoDescripcion(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <InventarioFuera> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
    String sql = "SELECT inventariofuera.*, producto.descripcion FROM inventariofuera JOIN producto ON inventariofuera.codigo = producto.codigo WHERE descripcion LIKE '" + filtro + "' OR codigo LIKE '" + filtro + "'";
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         InventarioFuera inv = new InventarioFuera(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
           inv.setId(rs.getInt("id"));
         inv.setCodigo(rs.getString("codigo"));
         inv.setDescripcion(rs.getString("descripcion"));
         inv.setExistencia(rs.getDouble("existencia"));
         inv.setFecha(rs.getString("fecha"));
         inv.setIdRecibe(rs.getInt("idRecibe"));
         inv.setRazon(rs.getString("razon"));
        
         ListaCl.add(inv); //se agrega el cliente al arreglo
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
               
public boolean actualizarProducto(String codigo, double cantidadM) {
    String sqlUpdate = "UPDATE inventariofuera SET existencia = existencia - ? WHERE codigo = ?;";
    String sqlDelete = "DELETE FROM inventariofuera WHERE existencia = 0;";
    String sqlUpdateProducto = "UPDATE producto SET existencia = existencia + ? WHERE codigo = ?;";
    
    try {
        con = cn.getConnection();
        
        // Actualizar existencia en inventariofuera
        ps = con.prepareStatement(sqlUpdate);
        ps.setDouble(1, cantidadM);
        ps.setString(2, codigo);
        ps.execute();
        
        // Eliminar registros con existencia = 0 en inventariofuera
        ps = con.prepareStatement(sqlDelete);
        ps.execute();
        
        // Actualizar existencia en producto
        ps = con.prepareStatement(sqlUpdateProducto);
        ps.setDouble(1, cantidadM);
        ps.setString(2, codigo);
        ps.execute();
        
        return true;
    } catch (SQLException ex) {
        System.out.println(ex.toString());
        return false;
    } finally {
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
            if (con != null)
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString() + "6");
        }
    }
}





    
}
