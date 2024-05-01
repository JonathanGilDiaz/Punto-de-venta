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
public class conteoInventarioDao {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
        public int idLinea(){
        String sql = "SELECT MAX(id) FROM conteoinventario";
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

              public List listarConteos(){
            List <conteoInventario> lsConteo = new ArrayList();
       String sql = "SELECT * FROM conteoinventario";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         conteoInventario conteo = new conteoInventario();
         conteo.setId(rs.getInt("id"));
         conteo.setFecha(rs.getString("fecha"));
         conteo.setIdEmpleado(rs.getInt("idRecibe"));
         conteo.setDiferencia(rs.getDouble("diferencia"));
         conteo.setObservaciones(rs.getString("observaciones"));
         conteo.setDinero(rs.getDouble("dinero"));
         lsConteo.add(conteo); //Agregar al arreglo
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
       return lsConteo;
    }
              
      public List listarConteosPeriodo(String fechaInicio, String fechaFinal){//true es para la tabla
       String sql = "SELECT * FROM conteoinventario where fecha between ? and ?";
              List<conteoInventario> lsConteo = new ArrayList<conteoInventario>();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
         ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);     
        rs=ps.executeQuery();
        while(rs.next()){
         conteoInventario conteo = new conteoInventario();
         conteo.setId(rs.getInt("id"));
         conteo.setFecha(rs.getString("fecha"));
         conteo.setIdEmpleado(rs.getInt("idRecibe"));
         conteo.setDiferencia(rs.getDouble("diferencia"));
         conteo.setObservaciones(rs.getString("observaciones"));
                  conteo.setDinero(rs.getDouble("dinero"));
         lsConteo.add(conteo);
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
       return lsConteo;
    }
      
      public boolean registrarConteo(conteoInventario ci){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO conteoinventario (observaciones, diferencia, fecha, idRecibe, dinero) VALUES (?,?,?,?,?)"; //sentencia sql
        try{
          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setString(1, ci.getObservaciones()); // se vacian los datos del cliente a la instrucciones
          ps.setDouble(2, ci.getDiferencia());
          ps.setString(3, ci.getFecha());
          ps.setInt(4, ci.getIdEmpleado());
          ps.setDouble(5, ci.getDinero());
          ps.execute(); // se ejecutar la instrruccion
          return true; //bandera para checar si se ejecuto correctamnete la instruccion
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.toString()+"Error el registrar Linea");
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

      public boolean registrarDetalle(detalleConteo dt){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO detalleconteo (idConteo, codigo, descripcion, piezas, fisico, diferencia, unidad) VALUES (?,?,?,?,?,?,?)"; //sentencia sql
        try{
          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setInt(1, dt.getIdConteo()); // se vacian los datos del cliente a la instrucciones
          ps.setString(2, dt.getCodigo());
          ps.setString(3, dt.getDescripcion());
          ps.setDouble(4, dt.getPiezas());
            ps.setDouble(5, dt.getFisico());
          ps.setDouble(6, dt.getDiferencia());
          ps.setString(7, dt.getUnidad());
          ps.execute(); // se ejecutar la instrruccion
          return true; //bandera para checar si se ejecuto correctamnete la instruccion
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.toString()+"Error el registrar Linea");
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
      
         public List listarDetalles(int id){
            List <detalleConteo> lsConteo = new ArrayList();
       String sql = "SELECT * FROM detalleconteo where idConteo=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs=ps.executeQuery();
        while(rs.next()){
         detalleConteo conteo = new detalleConteo();
         conteo.setId(rs.getInt("id"));
         conteo.setIdConteo(rs.getInt("idConteo"));
         conteo.setCodigo(rs.getString("codigo"));
         conteo.setDescripcion(rs.getString("descripcion"));
         conteo.setPiezas(rs.getDouble("piezas"));
         conteo.setFisico(rs.getDouble("fisico"));
         conteo.setDiferencia(rs.getDouble("diferencia"));
         conteo.setUnidad(rs.getString("unidad"));
         lsConteo.add(conteo); //Agregar al arreglo
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
       return lsConteo;
    }
         
         public conteoInventario buscarPorFolio(int id){
       String sql = "SELECT * FROM conteoinventario where id=?";
                conteoInventario conteo = new conteoInventario();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs=ps.executeQuery();
        while(rs.next()){
         conteo.setId(rs.getInt("id"));
         conteo.setFecha(rs.getString("fecha"));
         conteo.setIdEmpleado(rs.getInt("idRecibe"));
         conteo.setDiferencia(rs.getDouble("diferencia"));
         conteo.setObservaciones(rs.getString("observaciones"));
                  conteo.setDinero(rs.getDouble("dinero"));
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
       return conteo;
    }




}
