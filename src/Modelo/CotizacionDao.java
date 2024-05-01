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
public class CotizacionDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public int idCotizacion() {
        int id = 0;
        String sql = "SELECT MAX(folio) FROM cotizacion";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e.toString() + "Error al regresar el maximo folio");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }
        return id;
    }

    public void registrarCotizacion(Cotizacion v) {
        String sql = "INSERT INTO cotizacion (idRecibe, idCliente, hora, fecha, subtotal, descuento, iva, total, tiempo, anticipo) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdEmpleado());
            ps.setInt(2, v.getIdCliente());
            ps.setString(3, v.getHora());
            ps.setString(4, v.getFecha());
            ps.setDouble(5, v.getSubTotal());
            ps.setDouble(6, v.getDescuentos());
            ps.setDouble(7, v.getIva());
            ps.setDouble(8, v.getTotal());
            ps.setString(9, v.getTiempoValidacion());
            ps.setDouble(10, v.getAnticipo());
            ps.execute();

        } catch (SQLException e) {
            System.out.println(e.toString() + "Error al registrarVenta");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }
    }

    public Cotizacion buscarPorFolio(int a) {
        String sql = "SELECT * FROM cotizacion WHERE folio = ? ";
        Cotizacion n = new Cotizacion();
        boolean bandera = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, a);
            rs = ps.executeQuery();
            if (rs.next()) {
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setIva(rs.getDouble("iva"));
                n.setTotal(rs.getDouble("total"));
                n.setTiempoValidacion(rs.getString("tiempo"));
                n.setAnticipo(rs.getDouble("anticipo"));
                bandera = true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString() + "13");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }
        if (bandera == false) {
            n.setFecha("fallo");
        }
        return n;
    }

    public List regresarDetalles(int id) {
        List<detalleVenta> listaDetalle = new ArrayList();
        String sql = "SELECT * FROM detallecotizacion WHERE folioCotizacion = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            int a = 0;
            while (rs.next()) {
                detalleVenta detalle = new detalleVenta();
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setCodigo(rs.getString("codigo"));
                detalle.setDescripcion(rs.getString("descripcion"));
                detalle.setExistencia(rs.getInt("existencia"));
                detalle.setImporte(rs.getDouble("importe"));
                detalle.setPrecioUnitario(rs.getDouble("precioUnitario"));
                detalle.setDescuento(rs.getDouble("descuento"));

                listaDetalle.add(detalle);
            }

        } catch (SQLException e) {
            System.out.println(e.toString() + "19");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }

        return listaDetalle;
    }

    public void registrarDetalle(detalleVenta dv) {
        String sql = "INSERT INTO detallecotizacion (folioCotizacion, cantidad, codigo, descripcion, precioUnitario, importe, existencia, descuento) VALUES (?,?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, dv.getFolioVenta());
            ps.setDouble(2, dv.getCantidad());
            ps.setString(3, dv.getCodigo());
            ps.setString(4, dv.getDescripcion());
            ps.setDouble(5, dv.getPrecioUnitario());
            ps.setDouble(6, dv.getImporte());
            ps.setDouble(7, dv.getExistencia());
            ps.setDouble(8, dv.getDescuento());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }
    }

    public List listarCotizacionPeriodo(String fechaInicio, String fechaFinal) {//true es para la tabla
        String sql = "SELECT * FROM cotizacion where fecha between ? and ?";
        List<Cotizacion> lsVentas = new ArrayList<Cotizacion>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFinal);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cotizacion n = new Cotizacion();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                n.setTiempoValidacion(rs.getString("tiempo"));
                n.setAnticipo(rs.getDouble("anticipo"));
                lsVentas.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.toString() + "1");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }
        return lsVentas;
    }

    public List listarCotizacionCliente(int idCliente) {//true es para la tabla
        String sql = "SELECT * FROM cotizacion where idCliente=?";
        List<Cotizacion> lsVentas = new ArrayList<Cotizacion>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cotizacion n = new Cotizacion();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                n.setTiempoValidacion(rs.getString("tiempo"));
                n.setAnticipo(rs.getDouble("anticipo"));
                lsVentas.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.toString() + "1");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }
        return lsVentas;
    }

    public List listarCotizacion() {//true es para la tabla
        List<Cotizacion> lsVentas = new ArrayList<Cotizacion>();
        String sql = "SELECT * FROM cotizacion";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cotizacion n = new Cotizacion();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setTiempoValidacion(rs.getString("tiempo"));
                n.setAnticipo(rs.getDouble("anticipo"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                lsVentas.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.toString() + "1");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }
        return lsVentas;
    }

    public List listarCotizacionSoloUna(int folio) {//true es para la tabla
        String sql = "SELECT * FROM cotizacion where folio=?";
        List<Cotizacion> lsVentas = new ArrayList<Cotizacion>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, folio);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cotizacion n = new Cotizacion();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setTiempoValidacion(rs.getString("tiempo"));
                n.setAnticipo(rs.getDouble("anticipo"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                lsVentas.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.toString() + "1");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString() + "6");
            }
        }
        return lsVentas;
    }

}
