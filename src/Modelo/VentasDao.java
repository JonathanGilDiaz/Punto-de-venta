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
public class VentasDao {

    notaCreditoDao notaDao = new notaCreditoDao();
    PagosDao pagosDao = new PagosDao();

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List listarVentas() {//true es para la tabla
        List<Ventas> lsVentas = new ArrayList<Ventas>();
        String sql = "SELECT * FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas n = new Ventas();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setFormaPago(rs.getString("formaPago"));
                n.setMetodoPago(rs.getString("metodoPago"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                n.setEstatus(rs.getInt("estado"));
                n.setInteresAplicado(rs.getInt("interesAplicado"));
                if (n.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                    n.setSaldo(n.getTotal() - pagosDao.regresarPagos(n.getFolio()) - notaDao.retornarSumaNotas(n.getFolio()));
                } else {
                    n.setSaldo(0);
                }
                n.setComentarios(rs.getString("comentario"));
                n.setFacturado(rs.getInt("facturado"));
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

    public List listarVentasPorProducto(String codigo) {//true es para la tabla
        List<Ventas> lsVentas = new ArrayList<>();
        System.out.println(codigo);

        String sql = "SELECT v.* FROM ventas v JOIN detalleventa dv ON v.folio = dv.folioNota WHERE dv.codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas n = new Ventas();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setFormaPago(rs.getString("formaPago"));
                n.setMetodoPago(rs.getString("metodoPago"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                n.setEstatus(rs.getInt("estado"));
                n.setInteresAplicado(rs.getInt("interesAplicado"));
                if (n.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                    n.setSaldo(n.getTotal() - pagosDao.regresarPagos(n.getFolio()) - notaDao.retornarSumaNotas(n.getFolio()));
                } else {
                    n.setSaldo(0);
                }
                n.setComentarios(rs.getString("comentario"));
                n.setFacturado(rs.getInt("facturado"));
                lsVentas.add(n);
                System.out.println(codigo);
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

    public double retornarPorFormaPago(String fecha, String formaPago, String varias) {
        double resultadoFinal = 0;
        try {
            con = cn.getConnection();
            // Establecer la conexión con la base de datos
            // Consulta para obtener la sumatoria de ventas
            String consultaVentas = "SELECT SUM(total) AS sumatoriaVentas FROM ventas WHERE fecha = ? AND formaPago = ?";
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
            String consultaNotasCredito = "SELECT SUM(total) AS sumatoriaNotasCredito FROM notacredito WHERE fecha = ? AND formaPago = ?";
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
            resultadoFinal = sumatoriaVentas - sumatoriaNotasCredito;

            // Cerrar recursos
            resultadoVentas.close();
            resultadoNotasCredito.close();
            pstmtVentas.close();
            pstmtNotasCredito.close();

            if (!formaPago.equals("99 Por definir")) {
                String sqlFormas = "Select SUM(" + varias + ") as sumatoria from ventas where fecha = ? AND formaPago = 'Diferentes formas de pago'";
                ps = con.prepareStatement(sqlFormas);
                ps.setString(1, fecha);
                rs = ps.executeQuery();
                if (rs.next()) {
                    resultadoFinal = resultadoFinal + rs.getDouble("sumatoria");
                }
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
        return resultadoFinal;
    }

    public List listarVentasPeriodo(String fechaInicio, String fechaFinal) {//true es para la tabla
        String sql = "SELECT * FROM ventas where fecha between ? and ?";
        List<Ventas> lsVentas = new ArrayList<Ventas>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFinal);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas n = new Ventas();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setFormaPago(rs.getString("formaPago"));
                n.setMetodoPago(rs.getString("metodoPago"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                n.setEstatus(rs.getInt("estado"));
                n.setInteresAplicado(rs.getInt("interesAplicado"));
                if (n.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                    n.setSaldo(n.getTotal() - pagosDao.regresarPagos(n.getFolio()) - notaDao.retornarSumaNotas(n.getFolio()));
                } else {
                    n.setSaldo(0);
                }
                n.setComentarios(rs.getString("comentario"));
                n.setFacturado(rs.getInt("facturado"));

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

    public List listarVentasDia(String fecha) {//true es para la tabla
        String sql = "SELECT * FROM ventas where fecha=?";
        List<Ventas> lsVentas = new ArrayList<Ventas>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas n = new Ventas();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setFormaPago(rs.getString("formaPago"));
                n.setMetodoPago(rs.getString("metodoPago"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                n.setEstatus(rs.getInt("estado"));
                n.setInteresAplicado(rs.getInt("interesAplicado"));
                if (n.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                    n.setSaldo(n.getTotal() - pagosDao.regresarPagos(n.getFolio()) - notaDao.retornarSumaNotas(n.getFolio()));
                } else {
                    n.setSaldo(0);
                }
                n.setComentarios(rs.getString("comentario"));
                n.setFacturado(rs.getInt("facturado"));
                n.setEfectivo(rs.getDouble("efectivo"));
                n.setCheque(rs.getDouble("cheque"));
                n.setTransferencia(rs.getDouble("transferencia"));
                n.setCredito(rs.getDouble("credito"));
                n.setDebito(rs.getDouble("debito"));
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

    public List listarVentasCliente(int idCliente) {//true es para la tabla
        String sql = "SELECT * FROM ventas where idCliente=?";
        List<Ventas> lsVentas = new ArrayList<Ventas>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas n = new Ventas();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setFormaPago(rs.getString("formaPago"));
                n.setMetodoPago(rs.getString("metodoPago"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                n.setEstatus(rs.getInt("estado"));
                n.setInteresAplicado(rs.getInt("interesAplicado"));
                if (n.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                    n.setSaldo(n.getTotal() - pagosDao.regresarPagos(n.getFolio()) - notaDao.retornarSumaNotas(n.getFolio()));
                } else {
                    n.setSaldo(0);
                }
                n.setComentarios(rs.getString("comentario"));
                n.setFacturado(rs.getInt("facturado"));

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

    public List listarVentasSoloUna(int folio) {//true es para la tabla
        String sql = "SELECT * FROM ventas where folio=?";
        List<Ventas> lsVentas = new ArrayList<Ventas>();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, folio);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas n = new Ventas();
                n.setFolio(rs.getInt("folio"));
                n.setIdEmpleado(rs.getInt("idRecibe"));
                n.setIdCliente(rs.getInt("idCliente"));
                n.setFecha(rs.getString("fecha"));
                n.setHora(rs.getString("hora"));
                n.setFormaPago(rs.getString("formaPago"));
                n.setMetodoPago(rs.getString("metodoPago"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setIva(rs.getDouble("iva"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setTotal(rs.getDouble("total"));
                n.setEstatus(rs.getInt("estado"));
                n.setInteresAplicado(rs.getInt("interesAplicado"));
                if (n.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                    n.setSaldo(n.getTotal() - pagosDao.regresarPagos(n.getFolio()) - notaDao.retornarSumaNotas(n.getFolio()));
                } else {
                    n.setSaldo(0);
                }
                n.setComentarios(rs.getString("comentario"));
                n.setFacturado(rs.getInt("facturado"));

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

    public void registrarVenta(Ventas v) {
        try {
            if (v.getFormaPago().equals("Diferentes formas de pago")) {
                String sql = "INSERT INTO ventas (idRecibe, idCliente, hora, fecha, subtotal, descuento, iva, total, metodoPago, formaPago, comentario, efectivo, transferencia, cheque, credito, debito) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
                ps.setString(9, v.getMetodoPago());
                ps.setString(10, v.getFormaPago());
                ps.setString(11, v.getComentarios());
                ps.setDouble(12, v.getEfectivo());
                ps.setDouble(13, v.getTransferencia());
                ps.setDouble(14, v.getCheque());
                ps.setDouble(15, v.getCredito());
                ps.setDouble(16, v.getDebito());

                ps.execute();
            } else {
                String sql = "INSERT INTO ventas (idRecibe, idCliente, hora, fecha, subtotal, descuento, iva, total, metodoPago, formaPago, comentario) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

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
                ps.setString(9, v.getMetodoPago());
                ps.setString(10, v.getFormaPago());
                ps.setString(11, v.getComentarios());
                ps.execute();
            }
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

    public Ventas buscarPorFolio(int a) {
        String sql = "SELECT * FROM ventas WHERE folio = ? ";
        Ventas n = new Ventas();
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
                n.setFormaPago(rs.getString("formaPago"));
                n.setMetodoPago(rs.getString("metodoPago"));
                n.setSubTotal(rs.getDouble("subtotal"));
                n.setDescuentos(rs.getDouble("descuento"));
                n.setIva(rs.getDouble("iva"));
                n.setTotal(rs.getDouble("total"));
                n.setEstatus(rs.getInt("estado"));
                n.setInteresAplicado(rs.getInt("interesAplicado"));
                if (n.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                    n.setSaldo(n.getTotal() - pagosDao.regresarPagos(n.getFolio()) - notaDao.retornarSumaNotas(n.getFolio()));
                } else {
                    n.setSaldo(0);
                }
                n.setComentarios(rs.getString("comentario"));
                n.setFacturado(rs.getInt("facturado"));

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
        String sql = "SELECT * FROM detalleventa WHERE folioNota = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            int a = 0;
            while (rs.next()) {
                detalleVenta detalle = new detalleVenta();
                detalle.setCantidad(rs.getDouble("cantidad"));
                detalle.setCodigo(rs.getString("codigo"));
                detalle.setDescripcion(rs.getString("descripcion"));
                detalle.setExistencia(rs.getDouble("existencia"));
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

    public List regresarDetallesPorProducto(String codigo) {
        List<detalleVenta> listaDetalle = new ArrayList();
        String sql = "SELECT * FROM detalleventa where codigo=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            int a = 0;
            while (rs.next()) {
                detalleVenta detalle = new detalleVenta();
                detalle.setCantidad(rs.getDouble("cantidad"));
                detalle.setCodigo(rs.getString("codigo"));
                detalle.setDescripcion(rs.getString("descripcion"));
                detalle.setExistencia(rs.getDouble("existencia"));
                detalle.setImporte(rs.getDouble("importe"));
                detalle.setPrecioUnitario(rs.getDouble("precioUnitario"));
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
        String sql = "INSERT INTO detalleventa (folioNota, cantidad, codigo, descripcion, precioUnitario, importe, existencia, descuento) VALUES (?,?,?,?,?,?,?,?)";
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

    public boolean ventasEmpleado(int idRecibe) {
        String sql = "SELECT * FROM ventas WHERE idrecibe=?";
        boolean bandera = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idRecibe);
            rs = ps.executeQuery();
            if (rs.next()) {
                bandera = true;
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
        return bandera;
    }

    public boolean ventasCliente(int idCliente) {
        String sql = "SELECT * FROM ventas WHERE idCliente=?";
        boolean bandera = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();
            if (rs.next()) {
                bandera = true;
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
        return bandera;
    }

    public int idVenta() {
        int id = 0;
        String sql = "SELECT MAX(folio) FROM ventas";
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

    public boolean soloCancelar(int folio) {
        String sql = "UPDATE ventas SET estado=1 WHERE folio=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, folio);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString() + "falla aqui");
            return false;
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

    public boolean soloDevolver(int folio) {
        String sql = "UPDATE ventas SET devolucion=1 WHERE folio=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, folio);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString() + "falla aqui");
            return false;
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

    public boolean soloReponer(int folio) {
        String sql = "UPDATE ventas SET reposicion=1 WHERE folio=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, folio);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString() + "falla aqui");
            return false;
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

    public boolean interesMotatorio(Ventas v) {
        String sql = "UPDATE ventas SET interesAplicado=1, total=? WHERE folio=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, v.getTotal());
            ps.setInt(2, v.getFolio());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString() + "falla aqui");
            return false;
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

}
