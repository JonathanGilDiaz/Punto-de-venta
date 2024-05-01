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
public class PerfilDao {
    
      Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
        public int idPerfil(){
        
        String sql = "SELECT MAX(id) FROM perfil";
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
        
       public boolean PerfilRepetido(Perfil p){
        String sql = "SELECT * FROM perfil WHERE nombre=?";
        boolean bandera=false;
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, p.getNombre());  
         rs=ps.executeQuery();
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
       
       public boolean RegistrarPerfil(Perfil p){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO perfil (`nombre`, `descripcion`, `fecha`, `idRecibe`, `nuevaVenta`, `controlVentas`, `proveedores`, `empleados`, `productos`, `clientes`, `departamento`, `linea`, `gastos`, `perfil`, `corteVenta`, `bitacora`, `inventario`, `ordenesCompra`, `datosEmpresa`, `respaldar`, `restaurar`, `credito`, `cotizaciones`,`ventasR`,`notasCreditoR`,`productosR`,`concentradoGeneralR`,`datosClientesR`,`cobranzaR`,`inventarioR`,`facturando`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //sentencia sql
        try{

          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setString(1,p.getNombre()); // se vacian los datos del cliente a la instrucciones
          ps.setString(2,p.getDescripcion());
          ps.setString(3, p.getFecha());
          ps.setInt(4, p.getIdRecibe());
          ps.setInt(5, p.getNuevaVenta());
          ps.setInt(6, p.getControlVentas());
          ps.setInt(7, p.getProveedores());
          ps.setInt(8, p.getEmpleados());
          ps.setInt(9, p.getProductos());
          ps.setInt(10, p.getClientes());
          ps.setInt(11, p.getDepartamento());
          ps.setInt(12, p.getLinea());
          ps.setInt(13, p.getGastos());
          ps.setInt(14, p.getPerfil());
          ps.setInt(15, p.getCorteVenta());
          ps.setInt(16, p.getBitacora());
          ps.setInt(17, p.getInventario());
          ps.setInt(18, p.getOrdenesCompra());
          ps.setInt(19, p.getDatosEmpresa());
          ps.setInt(20, p.getRespaldar());
          ps.setInt(21, p.getRestaurar());
          ps.setInt(22, p.getCredito());
          ps.setInt(23, p.getCotizaciones());
          ps.setInt(24, p.getVentasR());
          ps.setInt(25, p.getNotasCreditoR());
          ps.setInt(26, p.getProductosR());
          ps.setInt(27, p.getConcentradoGeneralR());
          ps.setInt(28, p.getDatosClientesR());
          ps.setInt(29, p.getCobranzaR());
          ps.setInt(30, p.getInventarioR());
          ps.setInt(31, p.getFacturando());
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

                 public boolean PerfilRepetidaActualizar(Perfil e){ //el siguiente metodo verifica que al modificar un cliente, no se repita
        String sql = "SELECT * FROM perfil"; //sentencia sql
        boolean bandera=false; //variable para indicar si efectivamente se duplico o no. Si es true se ha repetido, false no se repitio
        
        try{
          con = cn.getConnection(); //se establece la conexion
           ps = con.prepareStatement(sql); //se prepara la sentencia sql
         rs=ps.executeQuery(); //se ejecuta la sentencia sql
            while(rs.next()){
             String nombre=rs.getString("nombre"); //recibimos los valores de cada cliente para verificar si hay algun repetido
             int codigo = rs.getInt("id");
             //ahora, verificamos si se ha duplicado algun cliente
             if(e.getNombre().equals(nombre) && e.getId()!= codigo){
                 bandera=true; //Si se duplico, se cambia la bandera a true
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
                 
        public boolean ModificarPerfil(Perfil p){
     String sql = "UPDATE perfil SET `nombre`=?, `descripcion`=?, `fecha`=?, `idRecibe`=?, `nuevaVenta`=?, `controlVentas`=?, `proveedores`=?, `empleados`=?, `productos`=?, `clientes`=?, `departamento`=?, `linea`=?, `gastos`=?, `perfil`=?, `corteVenta`=?, `bitacora`=?, `inventario`=?, `ordenesCompra`=?, `datosEmpresa`=?, `respaldar`=?, `restaurar`=?, credito=?, ventasR=?, notasCreditoR=?, productosR=?, concentradoGeneralR=?, datosClientesR=?, cobranzaR=?, inventarioR=?, cotizaciones=?, facturando=? where `id`=?"; //sentencia sql

     try{
                 con = cn.getConnection();
          ps=con.prepareStatement(sql);
           ps.setString(1,p.getNombre()); // se vacian los datos del cliente a la instrucciones
          ps.setString(2,p.getDescripcion());
          ps.setString(3, p.getFecha());
          ps.setInt(4, p.getIdRecibe());
          ps.setInt(5, p.getNuevaVenta());
          ps.setInt(6, p.getControlVentas());
          ps.setInt(7, p.getProveedores());
          ps.setInt(8, p.getEmpleados());
          ps.setInt(9, p.getProductos());
          ps.setInt(10, p.getClientes());
          ps.setInt(11, p.getDepartamento());
          ps.setInt(12, p.getLinea());
          ps.setInt(13, p.getGastos());
          ps.setInt(14, p.getPerfil());
          ps.setInt(15, p.getCorteVenta());
          ps.setInt(16, p.getBitacora());
          ps.setInt(17, p.getInventario());
          ps.setInt(18, p.getOrdenesCompra());
          ps.setInt(19, p.getDatosEmpresa());
          ps.setInt(20, p.getRespaldar());
          ps.setInt(21, p.getRestaurar());
          ps.setInt(22, p.getCredito());
          ps.setInt(23, p.getVentasR());
          ps.setInt(24, p.getNotasCreditoR());
          ps.setInt(25, p.getProductosR());
          ps.setInt(26, p.getConcentradoGeneralR());
          ps.setInt(27, p.getDatosClientesR());
          ps.setInt(28, p.getCobranzaR());
          ps.setInt(29, p.getInventarioR());
          ps.setInt(30, p.getCotizaciones());
          ps.setInt(31, p.getFacturando());
          ps.setInt(32, p.getId());
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
        
            public List listarPerfiles(){
            List <Perfil> lsPerfil = new ArrayList();
       String sql = "SELECT * FROM perfil";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Perfil p = new Perfil();
             p.setId(rs.getInt("id"));
         p.setNombre(rs.getString("nombre"));
         p.setFecha(rs.getString("fecha"));
         p.setDescripcion(rs.getString("descripcion"));
         p.setIdRecibe(rs.getInt("idRecibe"));
         p.setNuevaVenta(rs.getInt("nuevaVenta"));
         p.setControlVentas(rs.getInt("controlVentas"));
         p.setProveedores(rs.getInt("proveedores"));
         p.setEmpleados(rs.getInt("empleados"));
         p.setProductos(rs.getInt("productos"));
         p.setClientes(rs.getInt("clientes"));
         p.setDepartamento(rs.getInt("departamento"));
         p.setLinea(rs.getInt("linea"));
         p.setGastos(rs.getInt("gastos"));
         p.setPerfil(rs.getInt("perfil"));
         p.setCorteVenta(rs.getInt("corteVenta"));
         p.setBitacora(rs.getInt("bitacora"));
         p.setInventario(rs.getInt("inventario"));
         p.setOrdenesCompra(rs.getInt("ordenesCompra"));
         p.setDatosEmpresa(rs.getInt("datosEmpresa"));
         p.setRespaldar(rs.getInt("respaldar"));
         p.setRestaurar(rs.getInt("restaurar"));
         p.setEstado(rs.getInt("estado"));
         p.setCredito(rs.getInt("credito"));
         p.setCotizaciones(rs.getInt("cotizaciones"));
         p.setVentasR(rs.getInt("ventasR"));
         p.setProductosR(rs.getInt("productosR"));
         p.setNotasCreditoR(rs.getInt("notasCreditoR"));
         p.setConcentradoGeneralR(rs.getInt("concentradoGeneralR"));
         p.setDatosClientesR(rs.getInt("datosClientesR"));
         p.setCobranzaR(rs.getInt("cobranzaR"));
         p.setInventarioR(rs.getInt("inventarioR"));
         p.setFacturando(rs.getInt("facturando"));
         lsPerfil.add(p); //Agregar al arreglo
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
       return lsPerfil;
    }
            
                  public Perfil seleccionarPerfil(String nombre, int id){
        String sql = "SELECT * FROM perfil WHERE nombre=? OR id=?";
            Perfil p = new Perfil();
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, nombre);  
         ps.setInt(2, id);
         rs=ps.executeQuery();
         if(rs.next()){
            p.setId(rs.getInt("id"));
         p.setNombre(rs.getString("nombre"));
         p.setFecha(rs.getString("fecha"));
         p.setDescripcion(rs.getString("descripcion"));
         p.setIdRecibe(rs.getInt("idRecibe"));
         p.setNuevaVenta(rs.getInt("nuevaVenta"));
         p.setControlVentas(rs.getInt("controlVentas"));
         p.setProveedores(rs.getInt("proveedores"));
         p.setEmpleados(rs.getInt("empleados"));
         p.setProductos(rs.getInt("productos"));
         p.setClientes(rs.getInt("clientes"));
         p.setDepartamento(rs.getInt("departamento"));
         p.setLinea(rs.getInt("linea"));
         p.setGastos(rs.getInt("gastos"));
         p.setLinea(rs.getInt("perfil"));
         p.setCorteVenta(rs.getInt("corteVenta"));
         p.setBitacora(rs.getInt("bitacora"));
         p.setInventario(rs.getInt("inventario"));
         p.setOrdenesCompra(rs.getInt("ordenesCompra"));
         p.setDatosEmpresa(rs.getInt("datosEmpresa"));
         p.setRespaldar(rs.getInt("respaldar"));
         p.setRestaurar(rs.getInt("restaurar"));
         p.setEstado(rs.getInt("estado"));
          p.setCredito(rs.getInt("credito"));
         p.setCotizaciones(rs.getInt("cotizaciones"));
         p.setVentasR(rs.getInt("ventasR"));
         p.setProductosR(rs.getInt("productosR"));
         p.setNotasCreditoR(rs.getInt("notasCreditoR"));
         p.setConcentradoGeneralR(rs.getInt("concentradoGeneralR"));
         p.setDatosClientesR(rs.getInt("datosClientesR"));
         p.setCobranzaR(rs.getInt("cobranzaR"));
         p.setInventarioR(rs.getInt("inventarioR"));
         p.setFacturando(rs.getInt("facturando"));
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
        return p;
    }

            
               public Perfil BuscarPorCodigo(int a){
          String sql = "SELECT * FROM perfil WHERE id = ?";
          Perfil p = new Perfil();
                  boolean bandera = false;   

          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
         if(rs.next()){
                 p.setId(rs.getInt("id"));
         p.setNombre(rs.getString("nombre"));
         p.setFecha(rs.getString("fecha"));
         p.setDescripcion(rs.getString("descripcion"));
         p.setIdRecibe(rs.getInt("idRecibe"));
         p.setNuevaVenta(rs.getInt("nuevaVenta"));
         p.setControlVentas(rs.getInt("controlVentas"));
         p.setProveedores(rs.getInt("proveedores"));
         p.setEmpleados(rs.getInt("empleados"));
         p.setProductos(rs.getInt("productos"));
         p.setClientes(rs.getInt("clientes"));
         p.setDepartamento(rs.getInt("departamento"));
         p.setLinea(rs.getInt("linea"));
         p.setGastos(rs.getInt("gastos"));
         p.setPerfil(rs.getInt("perfil"));
         p.setCorteVenta(rs.getInt("corteVenta"));
         p.setBitacora(rs.getInt("bitacora"));
         p.setInventario(rs.getInt("inventario"));
         p.setOrdenesCompra(rs.getInt("ordenesCompra"));
         p.setDatosEmpresa(rs.getInt("datosEmpresa"));
         p.setRespaldar(rs.getInt("respaldar"));
         p.setRestaurar(rs.getInt("restaurar"));
         p.setEstado(rs.getInt("estado"));
          p.setCredito(rs.getInt("credito"));
         p.setCotizaciones(rs.getInt("cotizaciones"));
                  p.setVentasR(rs.getInt("ventasR"));
         p.setProductosR(rs.getInt("productosR"));
         p.setNotasCreditoR(rs.getInt("notasCreditoR"));
         p.setConcentradoGeneralR(rs.getInt("concentradoGeneralR"));
         p.setDatosClientesR(rs.getInt("datosClientesR"));
         p.setCobranzaR(rs.getInt("cobranzaR"));
         p.setInventarioR(rs.getInt("inventarioR"));
         p.setFacturando(rs.getInt("facturando"));
         
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
       p.setNombre("");
       } 
       return p;

    }
             
          public List buscarLetra(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Perfil> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
       String sql = "SELECT * FROM perfil WHERE nombre LIKE"+'"'+filtro+'"';//Sentencia sql
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Perfil p = new Perfil(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
         p.setId(rs.getInt("id"));
         p.setNombre(rs.getString("nombre"));
         p.setFecha(rs.getString("fecha"));
         p.setDescripcion(rs.getString("descripcion"));
         p.setIdRecibe(rs.getInt("idRecibe"));
         p.setNuevaVenta(rs.getInt("nuevaVenta"));
         p.setControlVentas(rs.getInt("controlVentas"));
         p.setProveedores(rs.getInt("proveedores"));
         p.setEmpleados(rs.getInt("empleados"));
         p.setProductos(rs.getInt("productos"));
         p.setClientes(rs.getInt("clientes"));
         p.setDepartamento(rs.getInt("departamento"));
         p.setLinea(rs.getInt("linea"));
         p.setGastos(rs.getInt("gastos"));
         p.setLinea(rs.getInt("perfil"));
         p.setCorteVenta(rs.getInt("corteVenta"));
         p.setBitacora(rs.getInt("bitacora"));
         p.setInventario(rs.getInt("inventario"));
         p.setOrdenesCompra(rs.getInt("ordenesCompra"));
         p.setDatosEmpresa(rs.getInt("datosEmpresa"));
         p.setRespaldar(rs.getInt("respaldar"));
         p.setRestaurar(rs.getInt("restaurar"));
         p.setEstado(rs.getInt("estado"));
          p.setCredito(rs.getInt("credito"));
         p.setCotizaciones(rs.getInt("cotizaciones"));
                  p.setVentasR(rs.getInt("ventasR"));
         p.setProductosR(rs.getInt("productosR"));
         p.setNotasCreditoR(rs.getInt("notasCreditoR"));
         p.setConcentradoGeneralR(rs.getInt("concentradoGeneralR"));
         p.setDatosClientesR(rs.getInt("datosClientesR"));
         p.setCobranzaR(rs.getInt("cobranzaR"));
         p.setInventarioR(rs.getInt("inventarioR"));
         p.setFacturando(rs.getInt("facturando"));
       
         ListaCl.add(p); //se agrega el cliente al arreglo
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
          
     public boolean ModificarEstado(int codigo, int indicador){
     String sql = "UPDATE perfil SET estado=? WHERE id=?";
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
     
             public boolean EliminarPerfil(int id){
      String sql = "DELETE FROM perfil WHERE id = ?"; //sentencia sql
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
}
