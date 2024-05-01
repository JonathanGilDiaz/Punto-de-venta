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
public class CreditoDao {
    
       Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
            public int idCredito(){
        
        String sql = "SELECT MAX(id) FROM credito";
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
            
       public Credito BuscarPorCodigoCliente(int a){
          String sql = "SELECT * FROM credito WHERE idCliente = ?";
          Credito cl = new Credito();
                  boolean bandera = false;   

          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
         if(rs.next()){
         cl.setId(rs.getInt("id"));
         cl.setFecha(rs.getString("fecha"));
         cl.setIdCliente(rs.getInt("idCliente"));
         cl.setNombre(rs.getString("nombre"));
         cl.setApellidoP(rs.getString("apellidoP"));
         cl.setApellidoM(rs.getString("ApellidoM"));
         cl.setCalle(rs.getString("calle"));
         cl.setCodigoPostal(rs.getString("codigoPostal"));
         cl.setNumeroInterior(rs.getString("numeroInterior"));
         cl.setNumeroExterior(rs.getString("numeroExrterior"));
         cl.setMunicipio(rs.getString("municipio"));
         cl.setColonia(rs.getString("colonia"));
         cl.setEstado(rs.getString("estado"));
         cl.setCorreo(rs.getString("correo"));
         cl.setTelefono(rs.getString("telefono"));
         cl.setGiroOcupacion(rs.getString("giroOcupacion"));
         cl.setAntiguedad(rs.getString("antiguedad"));
         cl.setVigencia(rs.getInt("vigencia"));
         cl.setPlazo(rs.getInt("plazo"));
         cl.setDocumentoAcreditacion(rs.getString("documento"));
         cl.setAcreditado(rs.getString("acreditado"));
         cl.setLimite(rs.getDouble("limite"));
         cl.setInteresMoratorio(rs.getDouble("interesMoratorio"));
         cl.setInteresOrdinario(rs.getDouble("interesOrdinario"));
         cl.setInteresAplicado(rs.getInt("interesAplicado"));
         cl.setAdeudo(rs.getDouble("adeudo"));
       
         bandera=true;
            }
         else cl.setId(0);
       }catch(SQLException e){
           System.out.println(e.toString()+" error al retornar el credito");
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
       
      public List listarCreditos(){
          List<Credito> lsCredito = new ArrayList<Credito>();
           String sql = "SELECT tc.*, CONCAT(cl.nombre, ' ', cl.apellidoP, ' ', cl.apellidoM, ' ', cl.nombreComercial) AS nombre_completo " +
                          "FROM credito AS tc " +
                          "JOIN clientes AS cl ON tc.idCliente = cl.id";          
         boolean bandera = false;   
          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
         while(rs.next()){
          Credito cl = new Credito();
         cl.setId(rs.getInt("id"));
         cl.setIdCliente(rs.getInt("idCliente"));
         cl.setNombre(rs.getString("nombre"));
         cl.setApellidoP(rs.getString("apellidoP"));
         cl.setApellidoM(rs.getString("ApellidoM"));
         cl.setCalle(rs.getString("calle"));
         cl.setCodigoPostal(rs.getString("codigoPostal"));
         cl.setNumeroInterior(rs.getString("numeroInterior"));
         cl.setNumeroExterior(rs.getString("numeroExrterior"));
         cl.setMunicipio(rs.getString("municipio"));
         cl.setColonia(rs.getString("colonia"));
         cl.setEstado(rs.getString("estado"));
         cl.setCorreo(rs.getString("correo"));
         cl.setTelefono(rs.getString("telefono"));
         cl.setGiroOcupacion(rs.getString("giroOcupacion"));
         cl.setAntiguedad(rs.getString("antiguedad"));
         cl.setVigencia(rs.getInt("vigencia"));
         cl.setPlazo(rs.getInt("plazo"));
         cl.setDocumentoAcreditacion(rs.getString("documento"));
         cl.setAcreditado(rs.getString("acreditado"));
         cl.setLimite(rs.getDouble("limite"));
         cl.setInteresMoratorio(rs.getDouble("interesMoratorio"));
         cl.setInteresOrdinario(rs.getDouble("interesOrdinario"));
         cl.setInteresAplicado(rs.getInt("interesAplicado"));
         cl.setAdeudo(rs.getDouble("adeudo"));
         cl.setNombreCliente(rs.getString("nombre_completo"));
         cl.setFecha(rs.getString("fecha"));
         lsCredito.add(cl);
         bandera=true;
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
       return lsCredito;

    }

    public Credito BuscarPorCodigo(int a){
  String sql = "SELECT tc.*, CONCAT(cl.nombre, ' ', cl.apellidoP, ' ', cl.apellidoM, ' ', cl.nombreComercial) AS nombre_completo " +
              "FROM credito AS tc " +
              "JOIN clientes AS cl ON tc.idCliente = cl.id " +
              "WHERE tc.id = ?";

    Credito cl = new Credito();
    boolean bandera = false;   

          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
         if(rs.next()){
         cl.setId(rs.getInt("id"));
         cl.setIdCliente(rs.getInt("idCliente"));
         cl.setNombre(rs.getString("nombre"));
         cl.setApellidoP(rs.getString("apellidoP"));
         cl.setApellidoM(rs.getString("ApellidoM"));
         cl.setCalle(rs.getString("calle"));
         cl.setCodigoPostal(rs.getString("codigoPostal"));
         cl.setNumeroInterior(rs.getString("numeroInterior"));
         cl.setNumeroExterior(rs.getString("numeroExrterior"));
         cl.setMunicipio(rs.getString("municipio"));
         cl.setColonia(rs.getString("colonia"));
         cl.setFecha(rs.getString("fecha"));
         cl.setEstado(rs.getString("estado"));
         cl.setCorreo(rs.getString("correo"));
         cl.setTelefono(rs.getString("telefono"));
         cl.setGiroOcupacion(rs.getString("giroOcupacion"));
         cl.setAntiguedad(rs.getString("antiguedad"));
         cl.setVigencia(rs.getInt("vigencia"));
         cl.setPlazo(rs.getInt("plazo"));
         cl.setDocumentoAcreditacion(rs.getString("documento"));
         cl.setAcreditado(rs.getString("acreditado"));
         cl.setLimite(rs.getDouble("limite"));
         cl.setInteresMoratorio(rs.getDouble("interesMoratorio"));
         cl.setInteresOrdinario(rs.getDouble("interesOrdinario"));
         cl.setInteresAplicado(rs.getInt("interesAplicado"));
         cl.setAdeudo(rs.getDouble("adeudo"));
         cl.setNombreCliente(rs.getString("nombre_completo"));
       
         bandera=true;
            }else cl.setId(0);
       }catch(SQLException e){
           System.out.println(e.toString()+e.getMessage());
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
        List <Credito> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
         String sql = "SELECT tc.*, CONCAT(cl.nombre, ' ', cl.apellidoP, ' ', cl.apellidoM, ' ', cl.nombreComercial) AS nombre_completo " +
                          "FROM credito AS tc " +
                          "JOIN clientes AS cl ON tc.idCliente = cl.id where nombre_completo LIKE"+'"'+filtro+'"';       
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Credito cl = new Credito(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
             cl.setId(rs.getInt("id"));
         cl.setIdCliente(rs.getInt("idCliente"));
         cl.setNombre(rs.getString("nombre"));
         cl.setApellidoP(rs.getString("apellidoP"));
         cl.setApellidoM(rs.getString("ApellidoM"));
         cl.setCalle(rs.getString("calle"));
         cl.setCodigoPostal(rs.getString("codigoPostal"));
         cl.setNumeroInterior(rs.getString("numeroInterior"));
         cl.setNumeroExterior(rs.getString("numeroExrterior"));
         cl.setMunicipio(rs.getString("municipio"));
         cl.setColonia(rs.getString("colonia"));
         cl.setEstado(rs.getString("estado"));
         cl.setCorreo(rs.getString("correo"));
         cl.setTelefono(rs.getString("telefono"));
         cl.setGiroOcupacion(rs.getString("giroOcupacion"));
         cl.setAntiguedad(rs.getString("antiguedad"));
         cl.setVigencia(rs.getInt("vigencia"));
         cl.setPlazo(rs.getInt("plazo"));
         cl.setFecha(rs.getString("fecha"));
         cl.setDocumentoAcreditacion(rs.getString("documento"));
         cl.setAcreditado(rs.getString("acreditado"));
         cl.setLimite(rs.getDouble("limite"));
         cl.setInteresMoratorio(rs.getDouble("interesMoratorio"));
         cl.setInteresOrdinario(rs.getDouble("interesOrdinario"));
         cl.setInteresAplicado(rs.getInt("interesAplicado"));
         cl.setAdeudo(rs.getDouble("adeudo"));
         cl.setNombreCliente(rs.getString("nombre_completo"));
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



                   public boolean RegistrarCredito(Credito c){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO credito (idCliente, fecha, nombre, apellidoP, apellidoM, calle, codigoPostal, numeroInterior, numeroExrterior, municipio, colonia, estado, correo, telefono, giroOcupacion, antiguedad, vigencia, plazo, documento, acreditado, limite, interesOrdinario, interesMoratorio) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //sentencia sql
        try{

          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setInt(1, c.getIdCliente()); // se vacian los datos del cliente a la instrucciones
          ps.setString(2, c.getFecha());
          ps.setString(3, c.getNombre());
          ps.setString(4, c.getApellidoP());
          ps.setString(5, c.getApellidoM());
          ps.setString(6, c.getCalle());
          ps.setString(7, c.getCodigoPostal());
          ps.setString(8, c.getNumeroInterior());
          ps.setString(9, c.getNumeroExterior());
          ps.setString(10, c.getMunicipio());
          ps.setString(11, c.getColonia());
          ps.setString(12, c.getEstado());
          ps.setString(13, c.getCorreo());
          ps.setString(14, c.getTelefono());
          ps.setString(15, c.getGiroOcupacion());
          ps.setString(16, c.getAntiguedad());
          ps.setInt(17, c.getVigencia());
          ps.setInt(18, c.getPlazo());
          ps.setString(19, c.getDocumentoAcreditacion());
          ps.setString(20, c.getAcreditado());
          ps.setDouble(21, c.getLimite());
          ps.setDouble(22, c.getInteresOrdinario());
          ps.setDouble(23, c.getInteresMoratorio());

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
                   
                   
                   public boolean actualizarCredito(Credito c){ //para ello, se necesita un cliente para vaciar los datos
        actualizarAdeudoMoratorio(c);
                       String sql = "UPDATE credito SET fecha=?, nombre=?, apellidoP=?, apellidoM=?, calle=?, codigoPostal=?, numeroInterior=?, numeroExrterior=?, municipio=?, colonia=?, estado=?, correo=?, telefono=?, giroOcupacion=?, antiguedad=?, vigencia=?, plazo=?, documento=?, acreditado=?, limite=?, interesOrdinario=?, interesMoratorio=?, interesAplicado=0 where idCliente=?"; //sentencia sql
        try{

          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setString(1, c.getFecha());
          ps.setString(2, c.getNombre());
          ps.setString(3, c.getApellidoP());
          ps.setString(4, c.getApellidoM());
          ps.setString(5, c.getCalle());
          ps.setString(6, c.getCodigoPostal());
          ps.setString(7, c.getNumeroInterior());
          ps.setString(8, c.getNumeroExterior());
          ps.setString(9, c.getMunicipio());
          ps.setString(10, c.getColonia());
          ps.setString(11, c.getEstado());
          ps.setString(12, c.getCorreo());
          ps.setString(13, c.getTelefono());
          ps.setString(14, c.getGiroOcupacion());
          ps.setString(15, c.getAntiguedad());
          ps.setInt(16, c.getVigencia());
          ps.setInt(17, c.getPlazo());
          ps.setString(18, c.getDocumentoAcreditacion());
          ps.setString(19, c.getAcreditado());
          ps.setDouble(20, c.getLimite());
          ps.setDouble(21, c.getInteresOrdinario());
          ps.setDouble(22, c.getInteresMoratorio());
          ps.setInt(23, c.getIdCliente()); // se vacian los datos del cliente a la instrucciones

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

                   public boolean actualizarAdeudoMoratorio(Credito c){ //para ello, se necesita un cliente para vaciar los datos
        String sql = "UPDATE credito SET adeudo = 100*adeudo/(100+interesOrdinario)  where idCliente=? AND interesAplicado=1"; //sentencia sql
        try{

          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setInt(1, c.getIdCliente());
         ps.execute(); 
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
                   
       public boolean agregarInteresMoratorio(Credito c){ //para ello, se necesita un cliente para vaciar los datos
        String sql = "UPDATE credito SET interesAplicado=1, adeudo = adeudo+(adeudo*(interesOrdinario/100))  where id=? AND interesAplicado=0"; //sentencia sql
        try{

          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setInt(1, c.getId());
          ps.execute();
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
       
              public boolean agregarInteresOrdinario(Credito c, double cantidad){ //para ello, se necesita un cliente para vaciar los datos
        String sql = "UPDATE credito SET adeudo = adeudo+(?*(interesOrdinario/100))  where id=?"; //sentencia sql
        try{

          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setDouble(1, cantidad);
          ps.setInt(2, c.getId());
          ps.execute();
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

       
        public boolean aumentarAdeudo(int idCliente, double cantidad){ //para ello, se necesita un cliente para vaciar los datos
       String sql = "UPDATE credito SET adeudo = adeudo + ? where idCliente=?"; //sentencia sql
        try{

          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setDouble(1, cantidad);
          ps.setInt(2, idCliente);
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

      
    
}
