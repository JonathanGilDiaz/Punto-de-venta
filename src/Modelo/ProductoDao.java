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
public class ProductoDao {
    
       Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
         public boolean EliminarProducto(String codigo){ 
      String sql = "DELETE FROM producto WHERE codigo = ?";
      try{
                  con = cn.getConnection();
         ps = con.prepareStatement(sql);
         ps.setString(1,codigo);
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

    
            public boolean ProductoRepetido(Producto p){
        String sql = "SELECT * FROM producto WHERE codigo=?";
        boolean bandera=false;
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, p.getCodigo());  
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
            
        public Producto BuscarPorCodigo(String codigo){
                 Producto producto = new Producto();
       String sql = "SELECT * FROM producto where codigo=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, codigo);
        rs=ps.executeQuery();
        if(rs.next()){
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
                      producto.setInventario(rs.getInt("inventario"));
                      producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
        }else{
                producto.setId(0);
                }
       }catch(SQLException e){
           System.out.println(e.toString()+"Error al listar productos");
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
       return producto;
    }

            
       public List listarProductosInventario(){
            List <Producto> lsDepartamento = new ArrayList();
       String sql = "SELECT *\n" +
"FROM producto\n" +
"ORDER BY CASE WHEN existencia <= minimo THEN 0 ELSE 1 END;";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Producto producto = new Producto();
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
                                 producto.setPrecioUsuario(rs.getDouble("precioUsuario"));

           
         lsDepartamento.add(producto); //Agregar al arreglo
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"Error al listar productos");
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
       return lsDepartamento;
    }
    
        public List listarProductosConInventario(){
            List <Producto> lsDepartamento = new ArrayList();
       String sql = "SELECT * FROM producto where inventario=1";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Producto producto = new Producto();
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setInventario(rs.getInt("inventario"));
                                 producto.setPrecioUsuario(rs.getDouble("precioUsuario"));

           
         lsDepartamento.add(producto); //Agregar al arreglo
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"Error al listar productos");
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
       return lsDepartamento;
    }

        
                   public List listarProductos(){
            List <Producto> lsDepartamento = new ArrayList();
       String sql = "SELECT * FROM producto";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Producto producto = new Producto();
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setInventario(rs.getInt("inventario"));
                                 producto.setPrecioUsuario(rs.getDouble("precioUsuario"));

           
         lsDepartamento.add(producto); //Agregar al arreglo
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"Error al listar productos");
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
       return lsDepartamento;
    }
            
            public boolean RegistrarProducto(Producto p){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO producto (codigo, fechaCreacion, idRecibe, descripcion, minimo, aviso, departamento, linea, tipoVenta, precioCompra, precioVenta, existencia, inventario, precioUsuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //sentencia sql
        try{
          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setString(1,p.getCodigo()); // se vacian los datos del cliente a la instrucciones
          ps.setString(2,p.getFechaCreacion());
          ps.setInt(3, p.getIdEmpleado());
          ps.setString(4, p.getDescripcion());
          ps.setDouble(5, p.getMinimo());  
          ps.setInt(6, p.getAviso());  
           ps.setInt(7, p.getDepartamento());  
          ps.setInt(8, p.getLinea());  
          ps.setString(9, p.getTipoVenta());
          ps.setDouble(10, p.getPrecioCompra());
          ps.setDouble(11, p.getPrecioVenta());
          ps.setDouble(12, p.getExistencia());
          ps.setInt(13, p.getInventario());
          ps.setDouble(14, p.getPrecioUsuario());

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
            
               public boolean ProductoRepetidaActualizar(Producto p){ //el siguiente metodo verifica que al modificar un cliente, no se repita
        String sql = "SELECT * FROM producto"; //sentencia sql
        boolean bandera=false; //variable para indicar si efectivamente se duplico o no. Si es true se ha repetido, false no se repitio
        
        try{
          con = cn.getConnection(); //se establece la conexion
           ps = con.prepareStatement(sql); //se prepara la sentencia sql
         rs=ps.executeQuery(); //se ejecuta la sentencia sql
            while(rs.next()){
             String nombre=rs.getString("codigo"); //recibimos los valores de cada cliente para verificar si hay algun repetido
             int codigo = rs.getInt("id");
             //ahora, verificamos si se ha duplicado algun cliente
             if(p.getCodigo().equals(nombre) && p.getId()!= codigo){
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

    public boolean ModificarProducto(Producto p){
     String sql = "UPDATE producto SET codigo=?, descripcion=?,  minimo=?, aviso=?, departamento=?, linea=?, tipoVenta=?, existencia=?, precioVenta=?, precioCompra=?, inventario=?, precioUsuario=? WHERE id=?";
     try{
                 con = cn.getConnection();
          ps=con.prepareStatement(sql);
          ps.setString(1, p.getCodigo());
          ps.setString(2, p.getDescripcion());
          ps.setDouble(3, p.getMinimo());
          ps.setInt(4, p.getAviso());
           ps.setInt(5, p.getDepartamento());
          ps.setInt(6, p.getLinea());
          ps.setString(7, p.getTipoVenta());
          ps.setDouble(8, p.getExistencia());
          ps.setDouble(9, p.getPrecioVenta());
          ps.setDouble(10, p.getPrecioCompra());
          ps.setInt(11, p.getInventario());
          ps.setDouble(12, p.getPrecioUsuario());
          ps.setInt(13, p.getId());
          
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
    
            public List listarPorDepartamento(int departamento){
            List <Producto> lsDepartamento = new ArrayList();
       String sql = "SELECT * FROM producto WHERE departamento=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, departamento);
        rs=ps.executeQuery();
        while(rs.next()){
         Producto producto = new Producto();
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
           
         lsDepartamento.add(producto); //Agregar al arreglo
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
       return lsDepartamento;
    }

       public List listarPorLinea(int linea){
            List <Producto> lsDepartamento = new ArrayList();
       String sql = "SELECT * FROM producto WHERE linea=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, linea);
        rs=ps.executeQuery();
        while(rs.next()){
         Producto producto = new Producto();
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
           
         lsDepartamento.add(producto); //Agregar al arreglo
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
       return lsDepartamento;
    }

    
          public Producto existeProducto(String codigo){
       String sql = "SELECT * FROM producto WHERE codigo=?";
       boolean bandera=false;
       Producto producto = new Producto();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, codigo);
        rs=ps.executeQuery();
        
        if(rs.next()){
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
           
        }else{
                       producto.setId(0);

        }
        
       }catch(SQLException e){
           System.out.println(e.toString()+" error aqui");
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
       return producto;
    }    

            public List buscarLetra(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Producto> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
       String sql = "SELECT * FROM producto WHERE descripcion LIKE"+'"'+filtro+'"';//Sentencia sql
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Producto producto = new Producto(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
             producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
        
         ListaCl.add(producto); //se agrega el cliente al arreglo
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
            
        public List buscarLetraCodigoDescripcion(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Producto> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
    String sql = "SELECT * FROM producto WHERE descripcion LIKE '" + filtro + "' OR codigo LIKE '" + filtro + "'";
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Producto producto = new Producto(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
             producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
        
         ListaCl.add(producto); //se agrega el cliente al arreglo
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

                public List buscarLetraCodigoDescripcionInventario(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Producto> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
String sql = "SELECT * FROM producto WHERE descripcion LIKE '" + filtro + "' OR codigo LIKE '" + filtro + "' " +
             "ORDER BY CASE WHEN existencia <= minimo THEN 0 ELSE 1 END";
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Producto producto = new Producto(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
             producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
        
         ListaCl.add(producto); //se agrega el cliente al arreglo
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

        
            
      public Producto existeProductoDescripcion(String descripcion){
       String sql = "SELECT * FROM producto WHERE descripcion=?";
       boolean bandera=false;
       Producto producto = new Producto();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, descripcion);
        rs=ps.executeQuery();
        
        if(rs.next()){
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
      
        }else{
                       producto.setId(0);

        }
        
       }catch(SQLException e){
           System.out.println(e.toString()+" error aqui");
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
       return producto;
    }    
      
    public void ajustarInventarioVenta(List<Producto>lsProducto){
        for (int i = 0; i < lsProducto.size(); i++) {
            if(lsProducto.get(i).getInventario()==1){
             String sql = "UPDATE producto SET existencia = existencia - ? WHERE codigo = ?";
        try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setDouble(1, lsProducto.get(i).getExistencia());
        ps.setString(2, lsProducto.get(i).getCodigo());
          ps.execute(); // se ejecuta la instruccion   
       }catch(SQLException e){
           System.out.println(e.toString()+" error aqui");
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
        
    }  
    
        public void IntroducirProductos(Producto p){

             String sql = "UPDATE producto SET existencia = existencia + ?, precioCompra = ? WHERE codigo = ?";
        try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setDouble(1, p.getExistencia());
        ps.setDouble(2, p.getPrecioCompra());
        ps.setString(3, p.getCodigo());

          ps.execute(); // se ejecuta la instruccion   
       }catch(SQLException e){
           System.out.println(e.toString()+" error aqui");
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


        public boolean ModificarEstado(String codigo, int indicador){
     String sql = "UPDATE producto SET estado=? WHERE codigo=?";
     try{
                 con = cn.getConnection();
          ps=con.prepareStatement(sql);
          ps.setInt(1, indicador);
          ps.setString(2,codigo);
  
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
        
         public List listarProductosInventarioBajo(){
            List <Producto> lsDepartamento = new ArrayList();
       String sql = "SELECT * FROM producto WHERE existencia <= minimo;";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Producto producto = new Producto();
           producto.setId(rs.getInt("id"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setFechaCreacion(rs.getString("fechaCreacion"));
           producto.setIdEmpleado(rs.getInt("idRecibe"));
           producto.setDescripcion(rs.getString("descripcion"));
           producto.setPrecioCompra(rs.getDouble("precioCompra"));
           producto.setPrecioVenta(rs.getDouble("precioVenta"));
           producto.setExistencia(rs.getDouble("existencia"));
           producto.setMinimo(rs.getDouble("minimo"));
           producto.setAviso(rs.getInt("aviso"));
           producto.setEstado(rs.getInt("estado"));
           producto.setInventario(rs.getInt("inventario"));
           producto.setDepartamento(rs.getInt("departamento"));
           producto.setLinea(rs.getInt("linea"));
           producto.setTipoVenta(rs.getString("tipoVenta"));
           producto.setPrecioUsuario(rs.getDouble("precioUsuario"));
           
         lsDepartamento.add(producto); //Agregar al arreglo
        }
       }catch(SQLException e){
           System.out.println(e.toString()+"Error al listar productos");
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
       return lsDepartamento;
    }

    public void conteoNuevaExistencia(Producto p){
             String sql = "UPDATE producto SET existencia = ? WHERE codigo = ?";
        try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setDouble(1, p.getExistencia());
        ps.setString(2, p.getCodigo());
          ps.execute(); // se ejecuta la instruccion   
       }catch(SQLException e){
           System.out.println(e.toString()+" error aqui");
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


