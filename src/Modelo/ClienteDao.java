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
public class ClienteDao {
    
     Conexion cn = new Conexion(); //con este hacemos la conexion a base de datos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
        public List ListarCliente(){ // metodo para regresar la informacion de todos los datos de la base de datos cliente
       List <Clientes> ListaCl = new ArrayList();
       String sql = "SELECT * FROM clientes";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Clientes cl = new Clientes();
         cl.setId(rs.getInt("id"));
         cl.setNombre(rs.getString("nombre"));
         cl.setApellidoP(rs.getString("apellidoP"));
         cl.setApellidoM(rs.getString("ApellidoM"));
         cl.setNombreComercial(rs.getString("nombreComercial"));
         cl.setRfc(rs.getString("RFC"));
         cl.setTipoSociedad(rs.getString("tipoSociedad"));
         cl.setTelefono(rs.getString("telefono"));
         cl.setRegimenFiscal(rs.getString("regimenFiscal"));
         cl.setCorreo(rs.getString("correo"));
         cl.setCfdi(rs.getString("cfdi"));
         cl.setCalle(rs.getString("calle"));
         cl.setCodigoPostal(rs.getString("codigoPostal"));
         cl.setNumeroInterior(rs.getString("numeroInterior"));
         cl.setNumeroExterior(rs.getString("numeroExterior"));
         cl.setMunicipio(rs.getString("municipio"));
         cl.setColonia(rs.getString("colonia"));
         cl.setEstado(rs.getString("estado"));
         cl.setIdRecibe(rs.getInt("idRecibe"));
         cl.setTipoPersona(rs.getString("tipoPersona"));
         cl.setFechaCreacion(rs.getString("fecha"));
         cl.setEstatus(rs.getInt("estatus"));
         ListaCl.add(cl);
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
       return ListaCl;
        
    }
        
            public Clientes BuscarPorCodigo(int a){
          String sql = "SELECT * FROM clientes WHERE id = ?";
          Clientes cl = new Clientes();
                  boolean bandera = false;   

          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
         if(rs.next()){
         cl.setId(rs.getInt("id"));
         cl.setNombre(rs.getString("nombre"));
         cl.setApellidoP(rs.getString("apellidoP"));
         cl.setApellidoM(rs.getString("ApellidoM"));
         cl.setNombreComercial(rs.getString("nombreComercial"));
         cl.setRfc(rs.getString("RFC"));
         cl.setTipoSociedad(rs.getString("tipoSociedad"));
         cl.setTelefono(rs.getString("telefono"));
         cl.setRegimenFiscal(rs.getString("regimenFiscal"));
         cl.setCorreo(rs.getString("correo"));
         cl.setCfdi(rs.getString("cfdi"));
         cl.setCalle(rs.getString("calle"));
         cl.setCodigoPostal(rs.getString("codigoPostal"));
         cl.setNumeroInterior(rs.getString("numeroInterior"));
         cl.setNumeroExterior(rs.getString("numeroExterior"));
         cl.setMunicipio(rs.getString("municipio"));
         cl.setColonia(rs.getString("colonia"));
         cl.setEstado(rs.getString("estado"));
         cl.setIdRecibe(rs.getInt("idRecibe"));
         cl.setTipoPersona(rs.getString("tipoPersona"));
         cl.setFechaCreacion(rs.getString("fecha"));
         cl.setEstatus(rs.getInt("estatus"));
         bandera=true;
            }
       }catch(SQLException e){
           System.out.println(e.toString()+"Error el regresar el cliente");
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

public List<Clientes> buscarLetra(String buscar) {
    List<Clientes> listaCl = new ArrayList<>();
    String filtro = "%" + buscar + "%";
    String sql = "SELECT * FROM clientes WHERE CONCAT(nombre, ' ', apellidoP, ' ', ApellidoM) LIKE ? OR nombreComercial LIKE ?";

    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, filtro);
        ps.setString(2, filtro);
        rs = ps.executeQuery();

        while (rs.next()) {
            Clientes cl = new Clientes();
            cl.setId(rs.getInt("id"));
            cl.setNombre(rs.getString("nombre"));
            cl.setApellidoP(rs.getString("apellidoP"));
            cl.setApellidoM(rs.getString("ApellidoM"));
            cl.setNombreComercial(rs.getString("nombreComercial"));
            cl.setRfc(rs.getString("RFC"));
            cl.setTipoSociedad(rs.getString("tipoSociedad"));
            cl.setTelefono(rs.getString("telefono"));
            cl.setRegimenFiscal(rs.getString("regimenFiscal"));
            cl.setCorreo(rs.getString("correo"));
            cl.setCfdi(rs.getString("cfdi"));
            cl.setCalle(rs.getString("calle"));
            cl.setCodigoPostal(rs.getString("codigoPostal"));
            cl.setNumeroInterior(rs.getString("numeroInterior"));
            cl.setNumeroExterior(rs.getString("numeroExterior"));
            cl.setMunicipio(rs.getString("municipio"));
            cl.setColonia(rs.getString("colonia"));
            cl.setEstado(rs.getString("estado"));
            cl.setIdRecibe(rs.getInt("idRecibe"));
            cl.setTipoPersona(rs.getString("tipoPersona"));
            cl.setFechaCreacion(rs.getString("fecha"));
            cl.setEstatus(rs.getInt("estatus"));

            listaCl.add(cl);
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
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

    return listaCl;
}

        
        public boolean insertarCliente(Clientes c) {
        try {
            // Consulta para verificar si existe un registro con los mismos valores
            String sql = "SELECT COUNT(*) FROM clientes WHERE \n" +
"    ((nombre = ? AND apellidoP = ? AND ApellidoM = ?) OR (nombreComercial = ? AND nombreComercial <> ''))\n" +
"    AND (nombre <> '' AND apellidoP <> '' AND ApellidoM <> '')";
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidoP());
            ps.setString(3, c.getApellidoM());
            ps.setString(4, c.getNombreComercial());
             rs=ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    // Ya existe un registro con los mismos valores, no se permite la inserción
                    System.out.println("El registro ya existe. No se puede insertar.");
                    return false;
                }
            }

            // Si no existe un registro con los mismos valores, se permite la inserción
            sql = "INSERT INTO clientes (nombre, apellidoP, ApellidoM, nombreComercial, rfc, tipoSociedad, telefono, regimenFiscal, correo, cfdi, calle, codigoPostal, numeroInterior, numeroExterior,municipio, estado,idRecibe, tipoPersona, fecha, colonia) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
              con = cn.getConnection(); //se establece la conexion
              ps = con.prepareStatement(sql);
              ps.setString(1, c.getNombre());
              ps.setString(2, c.getApellidoP());
              ps.setString(3, c.getApellidoM());
              ps.setString(4, c.getNombreComercial());
              ps.setString(5, c.getRfc());
              ps.setString(6, c.getTipoSociedad());
              ps.setString(7, c.getTelefono());
              ps.setString(8, c.getRegimenFiscal());
              ps.setString(9, c.getCorreo());
              ps.setString(10, c.getCfdi());
              ps.setString(11, c.getCalle());
              ps.setString(12, c.getCodigoPostal());
              ps.setString(13, c.getNumeroInterior());
              ps.setString(14, c.getNumeroExterior());
              ps.setString(15, c.getMunicipio());
              ps.setString(16, c.getEstado());
              ps.setInt(17, c.getIdRecibe());
              ps.setString(18, c.getTipoPersona());
              ps.setString(19, c.getFechaCreacion());
              ps.setString(20, c.getColonia());
            ps.execute(); 
            System.out.println("Nuevo cliente insertado correctamente.");
            return true;
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.toString()+"Error al registrar cliente");
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
        

         
        public boolean ModificarCliente(Clientes c) {
        try {
            // Consulta para verificar si existe un registro con los mismos valores
                String sql = "SELECT COUNT(*) FROM clientes WHERE \n" +
"    ((nombre = ? AND apellidoP = ? AND ApellidoM = ?) OR (nombreComercial = ? AND nombreComercial <> ''))\n" +
"    AND (nombre <> '' AND apellidoP <> '' AND ApellidoM <> '') AND id != ?";
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidoP());
            ps.setString(3, c.getApellidoM());
                        ps.setString(4, c.getNombreComercial());
            ps.setInt(5, c.getId());
            rs=ps.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            if (count > 0) {
                // Existe otro registro con los mismos valores, no se permite la actualización
                System.out.println("No se puede actualizar el registro. Existe otro registro con los mismos valores.");
                return false;
            }
        } else {
            // Error al ejecutar la consulta
            System.out.println("Error al ejecutar la consulta.");
            return false;
        }

            // Si no existe un registro con los mismos valores, se permite la inserción
            sql = "UPDATE clientes SET nombre=?, apellidoP=?, ApellidoM=?, nombreComercial=?, rfc=?, tipoSociedad=?, telefono=?, regimenFiscal=?, correo=?, cfdi=?, calle=?, codigoPostal=?, numeroInterior=?, numeroExterior=?,municipio=?, estado=?,idRecibe=?, tipoPersona=?, colonia=? where id=?";
              con = cn.getConnection(); //se establece la conexion
              ps = con.prepareStatement(sql);
              ps.setString(1, c.getNombre());
              ps.setString(2, c.getApellidoP());
              ps.setString(3, c.getApellidoM());
              ps.setString(4, c.getNombreComercial());
              ps.setString(5, c.getRfc());
              ps.setString(6, c.getTipoSociedad());
              ps.setString(7, c.getTelefono());
              ps.setString(8, c.getRegimenFiscal());
              ps.setString(9, c.getCorreo());
              ps.setString(10, c.getCfdi());
              ps.setString(11, c.getCalle());
              ps.setString(12, c.getCodigoPostal());
              ps.setString(13, c.getNumeroInterior());
              ps.setString(14, c.getNumeroExterior());
              ps.setString(15, c.getMunicipio());
              ps.setString(16, c.getEstado());
              ps.setInt(17, c.getIdRecibe());
              ps.setString(18, c.getTipoPersona());
              ps.setString(19, c.getColonia());
              ps.setInt(20, c.getId());
            ps.execute(); 
            return true;
        } catch(SQLException e){
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
              
        public boolean ModificarEstado(int codigo, int indicador){
     String sql = "UPDATE clientes SET estatus=? WHERE id=?";
     try{
                 con = cn.getConnection();
          ps=con.prepareStatement(sql);
          ps.setInt(1, indicador);
          ps.setInt(2,codigo);
  
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

             
     public boolean EliminarCliente(int id){ 
      String sql = "DELETE FROM clientes WHERE id = ?";
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


          public int idCliente(){
        
        String sql = "SELECT MAX(id) FROM clientes";
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
          


         

}
