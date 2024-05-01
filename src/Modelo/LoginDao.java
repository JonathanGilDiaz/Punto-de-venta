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

public class LoginDao {
           Conexion cn = new Conexion(); //con este hacemos la conexion a base de datos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int id, r;
    
    public Empleados verificarCredenciales(String usuario, String pass){
       String sql = "SELECT * FROM empleados WHERE nombre=? AND contraseña=? AND estado=1";
       Empleados empleado = new Empleados();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, usuario);
        ps.setString(2, pass);
        rs=ps.executeQuery();
        if(rs.next()){
            empleado.setNombre(rs.getString("nombre"));
            empleado.setContraseña(rs.getString("contraseña"));
            empleado.setId(rs.getInt("idempleados"));
            empleado.setIdPerfil(rs.getInt("idPerfil"));
        }else{
            empleado.setNombre("fallo");
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
       return empleado;
    }

    
}
