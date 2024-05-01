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

/**ProveedorRepetidaActualizar
 *
 * @author Jonathan Gil
 */
public class ProveedoresDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
        public int idProveedor(){
        
        String sql = "SELECT MAX(id) FROM proveedores";
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

    
        public List listarProveedores(){
            List <Proveedores> lsProveedores = new ArrayList();
       String sql = "SELECT * FROM proveedores";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Proveedores cl = new Proveedores();
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
         cl.setFecha(rs.getString("fecha"));
         cl.setEstatus(rs.getInt("estatus"));
         lsProveedores.add(cl); //Agregar al arreglo
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
       return lsProveedores;
    }
        
             public Proveedores BuscarPorCodigo(int a){
          String sql = "SELECT * FROM proveedores WHERE id = ?";
          Proveedores cl = new Proveedores();
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
         cl.setFecha(rs.getString("fecha"));
         cl.setEstatus(rs.getInt("estatus"));
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
       cl.setNombre("");
       } 
       return cl;

    }
             
          public List buscarLetra(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Proveedores> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
       String sql = "SELECT * FROM proveedores WHERE CONCAT(nombre, ' ', apellidoP, ' ', ApellidoM) LIKE ? OR nombreComercial LIKE ?";
       try{
      con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, filtro);
        ps.setString(2, filtro);
        rs = ps.executeQuery();
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Proveedores cl = new Proveedores(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
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
         cl.setFecha(rs.getString("fecha"));
         cl.setEstatus(rs.getInt("estatus"));
         ListaCl.add(cl); //se agrega el cliente al arreglo
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

        public boolean ProveedorRepetido(Proveedores e){
        String sql = "SELECT * FROM proveedores WHERE nombre = ? AND apellidoP = ? AND ApellidoM = ?";
        boolean bandera=false;
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellidoP());
            ps.setString(3, e.getApellidoM());         rs=ps.executeQuery();
         if(rs.next()){
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
        return bandera;
    }
        public boolean ProveedorRepetidoMoral(Proveedores e){
        String sql = "SELECT * FROM proveedores WHERE nombreComercial = ?";
        boolean bandera=false;
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, e.getNombreComercial());
;         rs=ps.executeQuery();
         if(rs.next()){
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
        return bandera;
    }
        
            public boolean RegistrarProveedor(Proveedores c){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO proveedores (nombre, apellidoP, ApellidoM, nombreComercial, rfc, tipoSociedad, telefono, regimenFiscal, correo, cfdi, calle, codigoPostal, numeroInterior, numeroExterior,municipio, estado,idRecibe, tipoPersona, fecha, colonia) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
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
              ps.setString(19, c.getFecha());
              ps.setString(20, c.getColonia());
          
          ps.execute(); // se ejecutar la instrruccion
          return true; //bandera para checar si se ejecuto correctamnete la instruccion
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.toString());
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

                 public boolean ProveedorRepetidaActualizar(Proveedores e){ //el siguiente metodo verifica que al modificar un cliente, no se repita
        String sql = "SELECT *, CONCAT(nombre, ' ', apellidoP, ' ', ApellidoM) as nombreCompleto FROM proveedores"; //sentencia sql
        boolean bandera=false; //variable para indicar si efectivamente se duplico o no. Si es true se ha repetido, false no se repitio
        String nombreCompleto = e.getNombre()+" "+e.getApellidoP()+" "+e.getApellidoM();
        try{
          con = cn.getConnection(); //se establece la conexion
           ps = con.prepareStatement(sql); //se prepara la sentencia sql
         rs=ps.executeQuery(); //se ejecuta la sentencia sql
            while(rs.next()){
             String nombre=rs.getString("nombreCompleto"); //recibimos los valores de cada cliente para verificar si hay algun repetido
             int codigo = rs.getInt("id");
             //ahora, verificamos si se ha duplicado algun cliente
             if(nombreCompleto.equals(nombre) && e.getId()!= codigo){
                 System.out.println("ENTRO");
                 return true; //Si se duplico, se cambia la bandera a true
             }
             
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
        return bandera; //se retorna la variable boolean
    }
                 
        public boolean ProveedorRepetidaActualizarMoral(Proveedores e){ //el siguiente metodo verifica que al modificar un cliente, no se repita
        String sql = "SELECT * FROM proveedores"; //sentencia sql
        boolean bandera=false; //variable para indicar si efectivamente se duplico o no. Si es true se ha repetido, false no se repitio
        String nombreCompleto = e.getNombreComercial();
        try{
          con = cn.getConnection(); //se establece la conexion
           ps = con.prepareStatement(sql); //se prepara la sentencia sql
         rs=ps.executeQuery(); //se ejecuta la sentencia sql
            while(rs.next()){
             String nombre=rs.getString("nombreComercial"); //recibimos los valores de cada cliente para verificar si hay algun repetido
             int codigo = rs.getInt("id");
             //ahora, verificamos si se ha duplicado algun cliente
             if(nombreCompleto.equals(nombre) && e.getId()!= codigo){
                 System.out.println("ENTRO");
                 return true; //Si se duplico, se cambia la bandera a true
             }
             
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
        return bandera; //se retorna la variable boolean
    }


    public boolean ModificarProveedor(Proveedores c){
     String sql = "UPDATE proveedores SET nombre=?, apellidoP=?, ApellidoM=?, nombreComercial=?, rfc=?, tipoSociedad=?, telefono=?, regimenFiscal=?, correo=?, cfdi=?, calle=?, codigoPostal=?, numeroInterior=?, numeroExterior=?,municipio=?, estado=?,idRecibe=?, tipoPersona=?, colonia=? where id=?";
     try{
                 con = cn.getConnection();
          ps=con.prepareStatement(sql);
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
     }catch(SQLException ex){
         System.out.println(ex.toString());
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
    
        public boolean EliminarProveedor(int id){
      String sql = "DELETE FROM proveedores WHERE id = ?"; //sentencia sql
      try{
                      con =cn.getConnection();
         ps = con.prepareStatement(sql); //se prepara la instruccion
         ps.setInt(1,id); // se vacian los datos a la instruccion
         ps.execute();//se ejecuta la instruccion
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

        
            public boolean ModificarEstado(int codigo, int indicador){
     String sql = "UPDATE proveedores SET estatus=? WHERE id=?";
     try{
                     con =cn.getConnection();

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
            
      public Proveedores seleccionarProveedor(String nombre, int id){
        String sql = "SELECT * FROM proveedores WHERE nombre=? OR id=?";
            Proveedores cl = new Proveedores();
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, nombre);  
         ps.setInt(2, id);
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
         cl.setFecha(rs.getString("fecha"));
         cl.setEstatus(rs.getInt("estatus"));
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
        return cl;
    }

            




}
