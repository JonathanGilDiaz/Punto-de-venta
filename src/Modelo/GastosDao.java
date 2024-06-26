//Clase con los metodos de la Gastos con la base de datos

package Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;



public class GastosDao {
        Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    //Si no entiende algo de esta clase, verifique las primeras clases: Cliente, ClienteDao, ahi esta mas explicado
    
    //Se van a listar los gastos de una fecha especific
     public List listarGastosPorDia(String fecha){
    List <Gastos> lsGastos = new ArrayList();
       String sql = "SELECT * FROM gastos WHERE fecha=?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, fecha);
        rs=ps.executeQuery();
        while(rs.next()){
         Gastos g = new Gastos();
         g.setComprobante(rs.getString("comprobante"));
         g.setDescripcion(rs.getString("descripcion"));
         g.setFecha(rs.getString("fecha"));
         g.setPrecio(rs.getDouble("precio"));
         g.setFormaPago(rs.getString("formaPago"));
         g.setIdRecibe(rs.getInt("idRecibe"));
         lsGastos.add(g);
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
       return lsGastos;
    }
     
       public List listarPorFecha(String fechaInicio, String fechaFinal){
         List <Gastos> lsGastos = new ArrayList();
       String sql = "SELECT * FROM gastos WHERE fecha between ? and ?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);       
        rs=ps.executeQuery();
        while(rs.next()){
         Gastos g = new Gastos();
         g.setComprobante(rs.getString("comprobante"));
         g.setDescripcion(rs.getString("descripcion"));
         g.setFecha(rs.getString("fecha"));
         g.setPrecio(rs.getDouble("precio"));
         g.setFormaPago(rs.getString("formaPago"));
         g.setId(rs.getInt("id"));
        g.setIdRecibe(rs.getInt("idRecibe"));


         lsGastos.add(g);
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
       return lsGastos;
        
    }
    
     //Se listan todos los gastos con un arreglo
      public List listarGastos(){
    List <Gastos> lsGastos = new ArrayList();
       String sql = "SELECT * FROM gastos";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         Gastos g = new Gastos();
         g.setId(rs.getInt("id"));
         g.setComprobante(rs.getString("comprobante"));
         g.setDescripcion(rs.getString("descripcion"));
         g.setFecha(rs.getString("fecha"));
         g.setPrecio(rs.getDouble("precio"));
          g.setFormaPago(rs.getString("formaPago"));
          g.setId(rs.getInt("id"));
          g.setIdRecibe(rs.getInt("idRecibe"));



         lsGastos.add(g);
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
       return lsGastos;
    }
      
       public List buscarLetra(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <Gastos> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
       String sql = "SELECT * FROM gastos WHERE descripcion LIKE"+'"'+filtro+'"';//Sentencia sql
       try{
        con = cn.getConnection(); //Se prepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
          Gastos g = new Gastos();
         g.setId(rs.getInt("id"));
         g.setComprobante(rs.getString("comprobante"));
         g.setDescripcion(rs.getString("descripcion"));
         g.setFecha(rs.getString("fecha"));
         g.setPrecio(rs.getDouble("precio"));
         g.setFormaPago(rs.getString("formaPago"));
         g.setIdRecibe(rs.getInt("idRecibe"));
         ListaCl.add(g); //se agrega el cliente al arreglo
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
      
      public Gastos BuscarPorCodigo(int a){
          String sql = "SELECT * FROM gastos WHERE id = ?";
         Gastos g = new Gastos();
                  boolean bandera = false;   

          try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, a);
        rs=ps.executeQuery();
         if(rs.next()){
        g.setId(rs.getInt("id"));
         g.setComprobante(rs.getString("comprobante"));
         g.setDescripcion(rs.getString("descripcion"));
         g.setFecha(rs.getString("fecha"));
         g.setPrecio(rs.getDouble("precio"));
         g.setFormaPago(rs.getString("formaPago"));
         g.setIdRecibe(rs.getInt("idRecibe"));
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
       if(bandera==false) {
       g.setDescripcion("");
       g.setComprobante("");
       } 
       return g;

    }
    
     //Se registra el gsto
    public boolean registrarGasto(Gastos s, String fecha){
       String sql = "INSERT INTO gastos (comprobante, descripcion, precio, fecha,formaPago, idRecibe) VALUES (?,?,?,?,?,?)";
       try{
          con = cn.getConnection();
          ps = con.prepareStatement(sql);
          ps.setString(1, s.getComprobante());
          ps.setString(2, s.getDescripcion());
          ps.setDouble(3, s.getPrecio());
          ps.setString(4, fecha);
          ps.setString(5, s.getFormaPago());
          ps.setInt(6, s.getIdRecibe());
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
    
                    public double retornarPorFormaPago(String fecha, String formaPago){
        double sumatoriaGastos = 0;
        try {
             con = cn.getConnection();
            // Establecer la conexión con la base de datos
            // Consulta para obtener la sumatoria de ventas
            String consultaVentas = "SELECT SUM(precio) AS sumatoriaGastos FROM gastos WHERE fecha = ? AND formaPago = ?";
            PreparedStatement pstmtVentas = con.prepareStatement(consultaVentas);
            pstmtVentas.setString(1, fecha);
            pstmtVentas.setString(2, formaPago);

            // Ejecutar la consulta de ventas
            ResultSet resultadoVentas = pstmtVentas.executeQuery();

            

            if (resultadoVentas.next()) {
                sumatoriaGastos = resultadoVentas.getDouble("sumatoriaGastos");
            }

            // Cerrar recursos
            resultadoVentas.close();
            pstmtVentas.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sumatoriaGastos;
    }

    
    //Esta metodo pertenece a la clase config
    //Lo que hace es retornar los datos los cuales son la informacion de la empresa

}


