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

/**
 *
 * @author Jonathan Gil
 */
public class ordenCompraDao {
    
     Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    ProductoDao productoDao = new ProductoDao();
    
            public int idCompra(){
        int id=0;
        String sql = "SELECT MAX(id) FROM ordencompra";
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        
        }catch(SQLException e){
            System.out.println(e.toString()+"Error al regresar el maximo folio");
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
            
        

    
            public boolean comprasProveedor(int idproveedor){
       String sql = "SELECT * FROM ordencompra WHERE idProveedor=?";
       boolean bandera=false;
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idproveedor);
        rs=ps.executeQuery();
        if(rs.next()){
           bandera=true;
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
       return bandera;
    }
            
      public ordenCompra buscarPorId(int id){
       String sql = "SELECT * FROM ordencompra where id=?";
                ordenCompra c = new ordenCompra();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs=ps.executeQuery();
        if(rs.next()){
         c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
        }else{
            c.setId(0);
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
       return c;
    }

            
     public List listarOrdenes(){
            List <ordenCompra> lsCompra = new ArrayList();
       String sql = "SELECT * FROM ordencompra";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         ordenCompra c = new ordenCompra();
        c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
         lsCompra.add(c); //Agregar al arreglo
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
       return lsCompra;
    }
     
          public List listarOrdenesSoloUndia(String fecha){
            List <ordenCompra> lsCompra = new ArrayList();
       String sql = "SELECT * FROM ordencompra where fecha = ?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, fecha);
        rs=ps.executeQuery();
        while(rs.next()){
         ordenCompra c = new ordenCompra();
        c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
         lsCompra.add(c); //Agregar al arreglo
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
       return lsCompra;
    }

     
           public List buscarLetra(String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        List <ordenCompra> ListaCl = new ArrayList(); //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
String sql = "SELECT oc.* FROM ordencompra oc JOIN proveedores p ON oc.idProveedor = p.id WHERE CONCAT(p.nombre, ' ', p.apellidoP, ' ', p.ApellidoM) LIKE '%" + filtro + "%' OR p.nombreComercial LIKE '%" + filtro + "%'";
       try{
        con = cn.getConnection(); //Se perepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
        ordenCompra c = new ordenCompra();
        c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
         ListaCl.add(c); //se agrega el cliente al arreglo
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
      
       public List listarOrdenesPeriodo(String fechaInicio, String fechaFinal){//true es para la tabla
       String sql = "SELECT * FROM ordencompra where fecha between ? and ?";
       List <ordenCompra> ListaCl = new ArrayList(); 
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
         ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);     
        rs=ps.executeQuery();
        while(rs.next()){
         ordenCompra c = new ordenCompra();
        c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
         ListaCl.add(c);
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

            public void registrarOrden(ordenCompra orden){
        String sql = "INSERT INTO ordencompra (idProveedor, IdRecibe, formaPago, fecha, total, subtotal, descuento, metodoPago, iva, plazoCredito, interesCredito) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                 
        try{
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, orden.getIdProveedor());
            ps.setInt(2, orden.getIdRecibe());
            ps.setString(3, orden.getFormaPago());
            ps.setString(4, orden.getFecha());
            ps.setDouble(5, orden.getTotal());
            ps.setDouble(6, orden.getSubtotal());
            ps.setDouble(7, orden.getDescuento());
            ps.setString(8, orden.getMetodoPago());
            ps.setDouble(9, orden.getIva());
            ps.setInt(10, orden.getPlazoCredito());
            ps.setDouble(11, orden.getInteresCredito());

            ps.execute();
     
        }catch(SQLException e){
            System.out.println(e.toString()+"Error al registrar Orden");
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
            
       public List regresarDetalles(int id){
        List <DetalleOrdenCompra> listaDetalle = new ArrayList();
        String sql = "SELECT * FROM detallecompra WHERE idOrdenCompra = ? ";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs=ps.executeQuery();
        int a=0;
        while(rs.next()){
         DetalleOrdenCompra detalle = new DetalleOrdenCompra();
         detalle.setId(rs.getInt("id"));
         detalle.setIdCompra(rs.getInt("idOrdenCompra"));
         detalle.setCodigo(rs.getString("codigo"));
         detalle.setCantidad(rs.getDouble("cantidad"));
         detalle.setUnidad(rs.getString("Unidad"));
         detalle.setDescripcion(rs.getString("descripcion"));
         detalle.setPrecioUnitario(rs.getDouble("precioUnitario"));
         detalle.setDescuento(rs.getDouble("descuento"));
         detalle.setImporte(rs.getDouble("importe"));
         listaDetalle.add(detalle);
        }

       }catch(SQLException e){
           System.out.println(e.toString()+"19");
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
       
       return listaDetalle;
    }

            
        public void registrarDetalle(DetalleOrdenCompra detalle){
        String sql = "INSERT INTO detallecompra (idOrdenCompra, codigo, cantidad, unidad, descripcion, precioUnitario, descuento, importe) VALUES (?,?,?,?,?,?,?,?)";
        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, detalle.getIdCompra());
            ps.setString(2, detalle.getCodigo());
            ps.setDouble(3, detalle.getCantidad());
            ps.setString(4, detalle.getUnidad());
            ps.setString(5, detalle.getDescripcion());
            ps.setDouble(6, detalle.getPrecioUnitario());
            ps.setDouble(7, detalle.getDescuento());
            ps.setDouble(8, detalle.getImporte());
            ps.execute();
        }catch(SQLException e){
         System.out.println(e.toString());
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
        
                public void registrarOrdenCancelada(int folioOrden){
        String sql = "INSERT INTO ordencompracancelada (idProveedor, IdRecibe, formaPago, fecha, total, subtotal, descuento, metodoPago, iva, plazoCredito, interesCredito,id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                 
        try{
            ordenCompra orden = buscarPorId(folioOrden);
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, orden.getIdProveedor());
            ps.setInt(2, orden.getIdRecibe());
            ps.setString(3, orden.getFormaPago());
            ps.setString(4, orden.getFecha());
            ps.setDouble(5, orden.getTotal()*(-1));
            ps.setDouble(6, orden.getSubtotal()*(-1));
            ps.setDouble(7, orden.getDescuento()*(-1));
            ps.setString(8, orden.getMetodoPago());
            ps.setDouble(9, orden.getIva()*(-1));
            ps.setInt(10, orden.getPlazoCredito());
            ps.setDouble(11, orden.getInteresCredito());
            ps.setInt(12, orden.getId());
            ps.execute();
            regresarObjetos(folioOrden);
          //  registrarDetalleNegativo(orden.getId());
        }catch(SQLException e){
            System.out.println(e.toString()+"Error al registrar Orden");
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
                
     public void regresarObjetos(int folio){
         List <DetalleOrdenCompra> listaDetalle = regresarDetalles(folio);
         for(int i=0;i<listaDetalle.size();i++){
             Producto p = productoDao.BuscarPorCodigo(listaDetalle.get(i).getCodigo());
             if(p.getInventario()==1){
                 p.setExistencia((-1)*listaDetalle.get(i).getCantidad());
                 productoDao.IntroducirProductos(p);
             }
         }
     }
                
      public void registrarDetalleNegativo(int idFolio){
        List <DetalleOrdenCompra> listaDetalle = regresarDetalles(idFolio);
        for(int i=0; i<listaDetalle.size();i++){
               String sql = "INSERT INTO detallecompranegativa (idOrdenCompra, codigo, cantidad, unidad, descripcion, precioUnitario, descuento, importe) VALUES (?,?,?,?,?,?,?,?)";
        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, listaDetalle.get(i).getIdCompra());
            ps.setString(2, listaDetalle.get(i).getCodigo());
            ps.setDouble(3, listaDetalle.get(i).getCantidad()*(-1));
            ps.setString(4, listaDetalle.get(i).getUnidad());
            ps.setString(5, listaDetalle.get(i).getDescripcion());
            ps.setDouble(6, listaDetalle.get(i).getPrecioUnitario());
            ps.setDouble(7, listaDetalle.get(i).getDescuento()*(-1));
            ps.setDouble(8, listaDetalle.get(i).getImporte()*(-1));
            ps.execute();
        }catch(SQLException e){
         System.out.println(e.toString());
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

                
          public ordenCompra buscarPorIdCancelada(int id){
       String sql = "SELECT * FROM ordencompracancelada where id=?";
                ordenCompra c = new ordenCompra();
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs=ps.executeQuery();
        if(rs.next()){
         c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
        }else{
            c.setId(0);
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
       return c;
    }


     public List listarOrdenesCanceladas(List <ordenCompra> lsCompra){
       String sql = "SELECT * FROM ordencompracancelada";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
         ordenCompra c = new ordenCompra();
        c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
         lsCompra.add(c); //Agregar al arreglo
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
       return lsCompra;
    }
     
          public List listarOrdenesCanceladasSoloUnDia(List <ordenCompra> lsCompra, String fecha){
       String sql = "SELECT * FROM ordencompracancelada where fecha = ?";
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, fecha);
        rs=ps.executeQuery();
        while(rs.next()){
         ordenCompra c = new ordenCompra();
        c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
         lsCompra.add(c); //Agregar al arreglo
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
       return lsCompra;
    }
          
              public double retornarPorFormaPago(String fecha, String formaPago){
        double resultadoFinal =0;
        try {
             con = cn.getConnection();
            // Establecer la conexión con la base de datos
            // Consulta para obtener la sumatoria de ventas
            String consultaVentas = "SELECT SUM(total) AS sumatoriaVentas FROM ordencompra WHERE fecha = ? AND formaPago = ?";
            PreparedStatement pstmtVentas = con.prepareStatement(consultaVentas);
            pstmtVentas.setString(1, fecha);
            pstmtVentas.setString(2, formaPago);

            // Ejecutar la consulta de ventas
            ResultSet resultadoVentas = pstmtVentas.executeQuery();

            double sumatoriaVentas = 0;

            if (resultadoVentas.next()) {
                sumatoriaVentas = resultadoVentas.getDouble("sumatoriaVentas");
            }

            // Consulta para obtener la sumatoria de notacredito
            String consultaNotasCredito = "SELECT SUM(total) AS sumatoriaNotasCredito FROM ordencompracancelada WHERE fecha = ? AND formaPago = ?";
            PreparedStatement pstmtNotasCredito = con.prepareStatement(consultaNotasCredito);
            pstmtNotasCredito.setString(1, fecha);
            pstmtNotasCredito.setString(2, formaPago);

            // Ejecutar la consulta de notacredito
            ResultSet resultadoNotasCredito = pstmtNotasCredito.executeQuery();

            double sumatoriaNotasCredito = 0;

            if (resultadoNotasCredito.next()) {
                sumatoriaNotasCredito = resultadoNotasCredito.getDouble("sumatoriaNotasCredito");
            }

            // Realizar la resta
            resultadoFinal = sumatoriaVentas + sumatoriaNotasCredito;

            // Cerrar recursos
            resultadoVentas.close();
            resultadoNotasCredito.close();
            pstmtVentas.close();
            pstmtNotasCredito.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultadoFinal;
    }



       public List listarOrdenesPeriodoCanceladas(List <ordenCompra> lsCompra, String fechaInicio, String fechaFinal){//true es para la tabla
       String sql = "SELECT * FROM ordencompracancelada where fecha between ? and ?";
      
       try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
         ps.setString(1, fechaInicio);
         ps.setString(2, fechaFinal);     
        rs=ps.executeQuery();
        while(rs.next()){
         ordenCompra c = new ordenCompra();
        c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
         lsCompra.add(c);
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
       return lsCompra;
    }

           public List buscarLetraCancelada(List <ordenCompra> lsCompra,String buscar){ //este metodo permite el buscar algun cliente con ciertas letras
        //se declara un arreglo cliente para guardar los datos
        String filtro=""+buscar+"%"; //se guarda la variable con la cual se buscara 
String sql = "SELECT oc.* FROM ordencompracancelada oc JOIN proveedores p ON oc.idProveedor = p.id WHERE CONCAT(p.nombre, ' ', p.apellidoP, ' ', p.ApellidoM) LIKE '%" + filtro + "%' OR p.nombreComercial LIKE '%" + filtro + "%'";
       try{                                                                                             
        con = cn.getConnection(); //Se perepara la conexion
        ps = con.prepareStatement(sql); //se prepara la sentencia sql
        rs=ps.executeQuery(); //se ejecuta la instruccion sql
        while(rs.next()){ // si hay algun dato, entra en el ciclo
        ordenCompra c = new ordenCompra();
        c.setId(rs.getInt("id"));
         c.setFecha(rs.getString("fecha"));
         c.setFormaPago(rs.getString("formaPago"));
         c.setIdProveedor(rs.getInt("idProveedor"));
         c.setIdRecibe(rs.getInt("IdRecibe"));
         c.setTotal(rs.getDouble("total"));
         c.setDescuento(rs.getDouble("descuento"));
         c.setSubtotal(rs.getDouble("subtotal"));
         c.setMetodoPago(rs.getString("metodoPago"));
         c.setIva(rs.getDouble("iva"));
         c.setPlazoCredito(rs.getInt("plazoCredito"));
         c.setInteresCredito(rs.getDouble("interesCredito"));
         lsCompra.add(c); //se agrega el cliente al arreglo
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
       return lsCompra; //se retorna la lista
        
    }

}
