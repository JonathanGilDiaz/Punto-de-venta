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
public class LineaDao {
    
      Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
                public List listarPorDepartamento(int departamento){
            List <Linea> lsDepartamento = new ArrayList();
       String sql = "SELECT * FROM linea WHERE departamento=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, departamento);
        rs=ps.executeQuery();
        while(rs.next()){
         Linea linea = new Linea();
           linea.setNombre(rs.getString("nombre"));
         linea.setId(rs.getInt("id"));
         linea.setDescripcion(rs.getString("descripcion"));
         linea.setFechs(rs.getString("fecha"));
         linea.setEstado(rs.getInt("estado"));
         linea.setAumento(rs.getDouble("aumento"));
         linea.setDescuento(rs.getDouble("descuento"));
         linea.setDepartamento(rs.getInt("departamento"));
         linea.setIdRecibe(rs.getInt("idRecibe"));
           
         lsDepartamento.add(linea); //Agregar al arreglo
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
                
    public Linea seleccionarDepartamento(String nombre, int id){
        String sql = "SELECT * FROM linea WHERE nombre=? OR id=?";
        Linea e = new Linea();
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, nombre);  
         ps.setInt(2, id);
         rs=ps.executeQuery();
         if(rs.next()){
             e.setNombre(rs.getString("nombre"));
         e.setId(rs.getInt("id"));
         e.setDescripcion(rs.getString("descripcion"));
         e.setFechs(rs.getString("fecha"));
         e.setEstado(rs.getInt("estado"));
         e.setAumento(rs.getDouble("aumento"));
         e.setDescuento(rs.getDouble("descuento"));
         e.setDepartamento(rs.getInt("departamento"));
                  e.setIdRecibe(rs.getInt("idRecibe"));  
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
        return e;
    }

                
             
          public List listarLineas(){
            List <Linea> lsDepartamento = new ArrayList();
       String sql = "SELECT * FROM linea";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Linea linea = new Linea();
           linea.setNombre(rs.getString("nombre"));
         linea.setId(rs.getInt("id"));
         linea.setDescripcion(rs.getString("descripcion"));
         linea.setFechs(rs.getString("fecha"));
         linea.setEstado(rs.getInt("estado"));
         linea.setAumento(rs.getDouble("aumento"));
         linea.setDescuento(rs.getDouble("descuento"));
         linea.setDepartamento(rs.getInt("departamento"));
                  linea.setIdRecibe(rs.getInt("idRecibe"));

           
         lsDepartamento.add(linea); //Agregar al arreglo
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

                
            public int idLinea(){
        
        String sql = "SELECT MAX(id) FROM linea";
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

                     public Linea BuscarPorCodigo(int a){
          String sql = "SELECT * FROM linea WHERE id = ?";
          Linea e = new Linea();
                  boolean bandera = false;   

          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
         if(rs.next()){
           e.setNombre(rs.getString("nombre"));
         e.setId(rs.getInt("id"));
         e.setDescripcion(rs.getString("descripcion"));
         e.setFechs(rs.getString("fecha"));
         e.setEstado(rs.getInt("estado"));
         e.setAumento(rs.getDouble("aumento"));
         e.setDescuento(rs.getDouble("descuento"));
         e.setDepartamento(rs.getInt("departamento"));
                  e.setIdRecibe(rs.getInt("idRecibe"));

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
       e.setNombre("");
       } 
       return e;

    }
             
          public List buscarLetra(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Linea> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
       String sql = "SELECT * FROM linea WHERE nombre LIKE"+'"'+filtro+'"';//Sentencia sql
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
         Linea e = new Linea(); //Se crea el cliente con el cual se guardaran los datos para posteriormente agregarlos a la lista
             e.setNombre(rs.getString("nombre"));
         e.setId(rs.getInt("id"));
         e.setDescripcion(rs.getString("descripcion"));
         e.setFechs(rs.getString("fecha"));
         e.setEstado(rs.getInt("estado"));
         e.setAumento(rs.getDouble("aumento"));
         e.setDescuento(rs.getDouble("descuento"));
         e.setDepartamento(rs.getInt("departamento"));
         e.setIdRecibe(rs.getInt("idRecibe"));
         ListaCl.add(e); //se agrega el cliente al arreglo
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

        public boolean LineaRepetido(Linea e){
        String sql = "SELECT * FROM linea WHERE nombre=?";
        boolean bandera=false;
        try{
          con = cn.getConnection();
           ps = con.prepareStatement(sql);
         ps.setString(1, e.getNombre());  
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
        
            public boolean RegistrarLinea(Linea e){ //para ello, se necesita un cliente para vaciar los datos
      String sql = "INSERT INTO linea (nombre, descripcion, fecha, aumento, descuento, departamento, idRecibe) VALUES (?,?,?,?,?,?,?)"; //sentencia sql
        try{
          con = cn.getConnection(); //se establece la conexion
          ps = con.prepareStatement(sql); //se prepara la instruccion
          ps.setString(1,e.getNombre()); // se vacian los datos del cliente a la instrucciones
          ps.setString(2, e.getDescripcion());
          ps.setString(3, e.getFechs());
          ps.setDouble(4, e.getAumento());
          ps.setDouble(5, e.getDescuento());
          ps.setInt(6, e.getDepartamento());
          ps.setInt(7, e.getIdRecibe());
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

                 public boolean LineaRepetidaActualizar(Linea e){ //el siguiente metodo verifica que al modificar un cliente, no se repita
        String sql = "SELECT * FROM linea"; //sentencia sql
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

    public boolean ModificarLinea(Linea e){
     String sql = "UPDATE linea SET nombre=?, descripcion=?, aumento=?, descuento=?, departamento=? WHERE id=?";
     try{
                 con = cn.getConnection();
          ps=con.prepareStatement(sql);
          ps.setString(1, e.getNombre());
          ps.setString(2, e.getDescripcion());
          ps.setDouble(3, e.getAumento());
          ps.setDouble(4, e.getDescuento());
          ps.setInt(5,e.getId());
          ps.setInt(6, e.getDepartamento());
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
    
        public boolean EliminarLinea(int id){
      String sql = "DELETE FROM linea WHERE id = ?"; //sentencia sql
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

        
public boolean ModificarEstado(int codigo, int indicador) {
    String sql1 = "UPDATE producto SET estado=? WHERE linea=?;";
    String sql2 = "UPDATE linea SET estado=? WHERE id=?;";

    try {
        con = cn.getConnection();

        // Actualización para la tabla producto
        ps = con.prepareStatement(sql1);
        ps.setInt(1, indicador);
        ps.setInt(2, codigo);
        ps.execute();
        ps.close();

        // Actualización para la tabla linea
        ps = con.prepareStatement(sql2);
        ps.setInt(1, indicador);
        ps.setInt(2, codigo);
        ps.execute();
        ps.close();

        return true;
    } catch (SQLException e) {
        System.out.println(e.toString());
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
            
     public boolean ajustarAumento(int linea){
     String sql = "UPDATE producto\n" +
"SET precioVenta = precioUsuario + (precioUsuario * ((SELECT aumento FROM linea WHERE id = producto.linea) + (SELECT aumento FROM departamento WHERE id = producto.departamento)) / 100)\n" +
"WHERE linea = ?;";
     try{
          con = cn.getConnection();
          ps=con.prepareStatement(sql);
          ps.setInt(1, linea);
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

        public boolean ajustarDescuento(int linea){
     String sql = "UPDATE producto\n" +
"SET precioVenta = precioVenta - (precioVenta * ((SELECT descuento FROM linea WHERE id = producto.linea) + (SELECT descuento FROM departamento WHERE id = producto.departamento)) / 100)\n" +
"WHERE linea = ?";
     try{
          con = cn.getConnection();
          ps=con.prepareStatement(sql);
          ps.setInt(1, linea);
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

    
}
