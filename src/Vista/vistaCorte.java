/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ClienteDao;
import Modelo.Clientes;
import Modelo.CorteDiario;
import Modelo.CorteDiarioDao;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Gastos;
import Modelo.GastosDao;
import Modelo.Pagos;
import Modelo.PagosDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.Proveedores;
import Modelo.ProveedoresDao;
import Modelo.Ventas;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import Modelo.config;
import Modelo.configuraciones;
import Modelo.detalleVenta;
import Modelo.notaCredito;
import Modelo.notaCreditoDao;
import Modelo.ordenCompra;
import Modelo.ordenCompraDao;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.raven.swing.Table3;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.Notification;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jonathan Gil
 */
public class vistaCorte extends javax.swing.JDialog {

    CorteDiarioDao corteDao = new CorteDiarioDao();
    VentasDao ventasDao = new VentasDao();
    ProductoDao productoDao = new ProductoDao();
    notaCreditoDao notaCreditodao = new notaCreditoDao();
    PagosDao pagosDao = new PagosDao();
    ClienteDao clienteDao = new ClienteDao();
    EmpleadosDao empleadosDao = new EmpleadosDao();
    CreditoDao creditoDao = new CreditoDao();
    GastosDao gastosDao = new GastosDao();
    ordenCompraDao ordenDao = new ordenCompraDao();
    DefaultTableModel modelo = new DefaultTableModel();
    bitacoraDao bitacora = new bitacoraDao();
    configuraciones configuracionesDao = new configuraciones();
    ProveedoresDao proveedorDao = new ProveedoresDao();
    List<Proveedores> lsProveedor = proveedorDao.listarProveedores();
    List<Clientes> lsClientes = clienteDao.ListarCliente();
    List<Empleados> lsEmpleados = empleadosDao.listarEmpleados();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    Empleados e;
    int fila = -1;
    double totalVentas = 0;
    double totalDescuentos = 0;
    double ivaGenerado = 0;
    double totalNotasCredito = 0;
    double ivaRestado = 0;
    double totalPagos = 0;
    double totalGastos = 0;
    double ventasContado = 0;
    double ventasCredito = 0;
    double totalEntradas = 0;
    double totalSalidas = 0;
    double saldoCaja = 0;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public vistaCorte(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocation(200, 50);
                };

            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Corte de caja");
        Seticon();

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableVentas.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(7).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(8).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableVentas.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        TableCobranza.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableCobranza.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableCobranza.getColumnModel().getColumn(2).setCellRenderer(tcrDerecha);
        TableCobranza.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableCobranza.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableCobranza.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TableCobranza.getColumnModel().getColumn(6).setCellRenderer(tcr);
        TableCobranza.getColumnModel().getColumn(7).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableCobranza.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        TableSalidas.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableSalidas.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableSalidas.getColumnModel().getColumn(2).setCellRenderer(tcrDerecha);
        TableSalidas.getColumnModel().getColumn(3).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableSalidas.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        vaciarDatos();
        txtFecha.setText(fechaFormatoCorrecto(corteDao.getDia()));
    }

    public void vaciarDatos() {
        List<Ventas> lsVentas = ventasDao.listarVentasDia(corteDao.getDia());
        List<notaCredito> lsNotasCredito = notaCreditodao.listarNotasPorDia(corteDao.getDia());
        List<Pagos> lsPagos = pagosDao.listarPagosDia(corteDao.getDia());
        List<Gastos> lsGastos = gastosDao.listarGastosPorDia(corteDao.getDia());
        Object[] ob = new Object[9];
        modelo = (DefaultTableModel) TableVentas.getModel();
        //vaciarTablasVentas
        //Primero ventas
        for (int i = 0; i < lsVentas.size(); i++) {
            for (int c = 0; c < lsClientes.size(); c++) {
                if (lsClientes.get(c).getId() == lsVentas.get(i).getIdCliente()) {
                    if (lsClientes.get(c).getTipoPersona().equals("Persona Física")) {
                        ob[0] = lsClientes.get(c).getNombre() + " " + lsClientes.get(c).getApellidoP() + " " + lsClientes.get(c).getApellidoM();
                    } else {
                        ob[0] = lsClientes.get(c).getNombreComercial();
                    }
                }
            }
            ob[1] = "F" + lsVentas.get(i).getFolio();
            ob[2] = "Venta";
            ob[3] = "$" + formateador.format(lsVentas.get(i).getSubTotal());
            ob[4] = "$" + formateador.format(lsVentas.get(i).getDescuentos());
            ob[5] = "$" + formateador.format(lsVentas.get(i).getIva());
            ob[6] = "$" + formateador.format(lsVentas.get(i).getTotal());
            totalVentas = totalVentas + lsVentas.get(i).getTotal();
            totalDescuentos = totalDescuentos + lsVentas.get(i).getDescuentos();
            ivaGenerado = ivaGenerado + lsVentas.get(i).getIva();
            for (int c = 0; c < lsEmpleados.size(); c++) {
                if (lsEmpleados.get(c).getId() == lsVentas.get(i).getIdEmpleado()) {
                    ob[7] = lsEmpleados.get(c).getNombre();
                }
            }
            ob[8] = lsVentas.get(i).getFormaPago();
            modelo.addRow(ob);
            if (lsVentas.get(i).getFormaPago().equals("99 Por definir")) {
                ventasCredito = ventasCredito + lsVentas.get(i).getTotal();
            } else {
                ventasContado = ventasContado + lsVentas.get(i).getTotal();
            }
            if (lsVentas.get(i).getFormaPago().equals("01 Efectivo")) {
                totalEntradas = totalEntradas + lsVentas.get(i).getTotal();
            }

            if (lsVentas.get(i).getFormaPago().equals("Diferentes formas de pago")) {
                totalEntradas = totalEntradas + lsVentas.get(i).getEfectivo();
            }
        }

        //listar Notas de credito
        for (int i = 0; i < lsNotasCredito.size(); i++) {
            Ventas miVenta = ventasDao.buscarPorFolio(lsNotasCredito.get(i).getFolioVenta());
            for (int c = 0; c < lsClientes.size(); c++) {
                if (lsClientes.get(c).getId() == miVenta.getIdCliente()) {
                    if (lsClientes.get(c).getTipoPersona().equals("Persona Física")) {
                        ob[0] = lsClientes.get(c).getNombre() + " " + lsClientes.get(c).getApellidoP() + " " + lsClientes.get(c).getApellidoM();
                    } else {
                        ob[0] = lsClientes.get(c).getNombreComercial();
                    }
                }
            }
            ob[1] = "E" + lsNotasCredito.get(i).getId();
            ob[2] = lsNotasCredito.get(i).getTipo();
            ob[3] = "-$" + formateador.format(lsNotasCredito.get(i).getSubtotal());
            ob[4] = "-";
            ob[5] = "-$" + formateador.format(lsNotasCredito.get(i).getIva());
            ob[6] = "-$" + formateador.format(lsNotasCredito.get(i).getTotal());
            totalNotasCredito = totalNotasCredito + lsNotasCredito.get(i).getTotal();
            ivaRestado = ivaRestado + lsNotasCredito.get(i).getIva();

            for (int c = 0; c < lsEmpleados.size(); c++) {
                if (lsEmpleados.get(c).getId() == lsNotasCredito.get(i).getIdRecibe()) {
                    ob[7] = lsEmpleados.get(c).getNombre();
                }
            }
            ob[8] = lsNotasCredito.get(i).getFormaPago();
            modelo.addRow(ob);
            if (lsNotasCredito.get(i).getFormaPago().equals("01 Efectivo")) {
                totalSalidas = totalSalidas + lsNotasCredito.get(i).getTotal();
            }
        }
        TableVentas.setModel(modelo);

        //Cobranza
        modelo = (DefaultTableModel) TableCobranza.getModel();
        for (int i = 0; i < lsPagos.size(); i++) {
            Ventas miVenta = ventasDao.buscarPorFolio(lsPagos.get(i).getFolioVenta());
            for (int c = 0; c < lsClientes.size(); c++) {
                if (lsClientes.get(c).getId() == miVenta.getIdCliente()) {
                    if (lsClientes.get(c).getTipoPersona().equals("Persona Física")) {
                        ob[0] = lsClientes.get(c).getNombre() + " " + lsClientes.get(c).getApellidoP() + " " + lsClientes.get(c).getApellidoM();
                    } else {
                        ob[0] = lsClientes.get(c).getNombreComercial();
                    }
                }
            }
            ob[1] = "F" + miVenta.getFolio();
            ob[2] = "$" + formateador.format(miVenta.getTotal());
            ob[3] = "$" + formateador.format(lsPagos.get(i).getAldoAnterior());
            ob[4] = "$" + formateador.format(lsPagos.get(i).getCantidad());
            totalPagos = totalPagos + lsPagos.get(i).getCantidad();
            ob[5] = "$" + formateador.format(lsPagos.get(i).getNuevoSaldo());
            Credito miCredito = creditoDao.BuscarPorCodigoCliente(miVenta.getIdCliente());
            ob[6] = "$" + formateador.format(miCredito.getAdeudo());
            ob[7] = lsPagos.get(i).getFormaPago();
            modelo.addRow(ob);
            if (lsPagos.get(i).getFormaPago().equals("01 Efectivo")) {
                totalEntradas = totalEntradas + lsPagos.get(i).getCantidad();
            }

        }
        TableCobranza.setModel(modelo);

        //gastos
        modelo = (DefaultTableModel) TableSalidas.getModel();
        for (int i = 0; i < lsGastos.size(); i++) {
            ob[0] = lsGastos.get(i).getComprobante();
            ob[1] = lsGastos.get(i).getDescripcion();
            ob[2] = "$" + formateador.format(lsGastos.get(i).getPrecio());
            totalGastos = totalGastos + lsGastos.get(i).getPrecio();
            ob[3] = lsGastos.get(i).getFormaPago().substring(0, 2);
            modelo.addRow(ob);
            if (lsGastos.get(i).getFormaPago().equals("01 Efectivo")) {
                totalSalidas = totalSalidas + lsGastos.get(i).getPrecio();
            }
        }
        TableSalidas.setModel(modelo);

        List<ordenCompra> lsOrden = ordenDao.listarOrdenesPeriodo(corteDao.getDia(), corteDao.getDia());
        for (int i = 0; i < lsOrden.size(); i++) {
            if (lsOrden.get(i).getFormaPago().equals("01 Efectivo")) {
                totalSalidas = totalSalidas + lsOrden.get(i).getTotal();
            }
        }

        txtContado.setText("$" + formateador.format(ventasContado));
        txtCredito.setText("$" + formateador.format(ventasCredito));
        txtNotasCredito.setText("$" + formateador.format(totalNotasCredito));
        txtVentasNeta.setText("$" + formateador.format(ventasContado + ventasCredito - totalNotasCredito));

        txtSaldoInicial.setText("$" + formateador.format(corteDao.getSaldoInicial()));
        txtEntradas.setText("$" + formateador.format(totalEntradas));
        txtSalidas.setText("$" + formateador.format(totalSalidas));
        saldoCaja = corteDao.getSaldoInicial() + totalEntradas - totalSalidas;
        txtSaldoCaja.setText("$" + formateador.format(saldoCaja));

    }

    public void eventoF1() {
        double ganancia = 0;
        List<Ventas> lsVentas = ventasDao.listarVentasDia(corteDao.getDia());
        for (int i = 0; i < lsVentas.size(); i++) {
            List<detalleVenta> ListaDetalles = ventasDao.regresarDetalles(lsVentas.get(i).getFolio());
            for (int y = 0; y < ListaDetalles.size(); y++) {
                Producto p = productoDao.BuscarPorCodigo(ListaDetalles.get(y).getCodigo());
                ganancia = ganancia + (ListaDetalles.get(y).getCantidad() * (p.getPrecioVenta() - p.getPrecioCompra()));
            }
        }
        cerrarCorte vC = new cerrarCorte(new javax.swing.JFrame(), true);
        vC.vaciarEmpleado(e.getId());
        vC.vaciarDatos(saldoCaja, ganancia);
        vC.setVisible(true);
        if (vC.accionRealizada == true) {
            CorteDiario c = new CorteDiario();
            c.setComentario(vC.observacion);
            c.setGanancia(ganancia);
            c.setIdRecibe(e.getId());
            c.setRetiro(vC.retiro);
            c.setSaldoFinal(vC.saldoFinal);
            c.setTotalDescuentos(totalDescuentos);
            c.setTotalEntradas(totalEntradas);
            c.setTotalGastos(totalGastos);
            c.setTotalNotasCredito(totalNotasCredito);
            c.setTotalPagos(totalPagos);
            c.setTotalSalidas(totalSalidas);
            c.setTotalVentas(totalVentas);
            c.setVentasContado(ventasContado);
            c.setVentasCredito(ventasCredito);
            corteDao.terminarCorte(c);
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Corte registrado");
            panel.showNotification();
            bitacora.registrarRegistro("Se registro corte del dia " + fechaFormatoCorrecto(corteDao.getDia()), e.getId(), corteDao.getDia());
            try {
                corteDiafinal(corteDao.getDia());
            } catch (DocumentException ex) {
                Logger.getLogger(vistaCorte.class.getName()).log(Level.SEVERE, null, ex);
            }
            Date fechaAnterior = StringADate(corteDao.getDia());
            LocalDate fechaActual = LocalDate.now();
            java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);
            System.out.println(fechaAnterior + "----------------" + fechaActual);
            if (fechaAnterior.equals(fechaSQL)) {
                Date fecha = java.sql.Date.valueOf(fechaActual);
                Calendar calendario = Calendar.getInstance();
                calendario.setTime(fecha);
                calendario.add(Calendar.DAY_OF_YEAR, 1);
                fecha = calendario.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                corteDao.crearNuevoDia(c, formatter.format(fecha));
                System.out.println(fechaActual + " Aqui " + formatter.format(fecha));
            } else {
                fechaSQL = java.sql.Date.valueOf(fechaActual);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String nuevaFecha = fechaSQL.toLocalDate().format(formatter);
                corteDao.crearNuevoDia(c, nuevaFecha);
                System.out.println("Aqui NEL" + nuevaFecha);

            }

            dispose();
        }
    }

    public void eventoF2() {
        vistaGasto vg = new vistaGasto(new javax.swing.JFrame(), true);
        vg.vaciarEmpleado(e.getId());
        vg.setVisible(true);
        if (vg.indicador == true) {
            totalVentas = 0;
            totalDescuentos = 0;
            ivaGenerado = 0;
            totalNotasCredito = 0;
            ivaRestado = 0;
            totalPagos = 0;
            totalGastos = 0;
            ventasContado = 0;
            ventasCredito = 0;
            totalEntradas = 0;
            totalSalidas = 0;
            saldoCaja = 0;
            LimpiarTabla();
            vaciarDatos();
        }
    }

    public void LimpiarTabla() {
        modelo = (DefaultTableModel) TableVentas.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        modelo = (DefaultTableModel) TableCobranza.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        modelo = (DefaultTableModel) TableSalidas.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public String fechaFormatoCorrecto(String fechaHoy) {
        Date diaActual = StringADate(fechaHoy);
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar c = Calendar.getInstance();
        c.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
        return fechaUsar;
    }

    public java.util.Date StringADate(String cambiar) { //Este metodo te transforma un String a date (dia)
        SimpleDateFormat formato_del_Texto = new SimpleDateFormat("yyyy-MM-dd"); //El formato del string
        Date fechaE = null;
        try {
            fechaE = formato_del_Texto.parse(cambiar);
            return fechaE; //retornamos la fecha
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JLabel();
        panelRegistrarNota1 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JLabel();
        panelRegistrarNota3 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableCobranza = new javax.swing.JTable();
        cargando = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        txtSaldoInicial = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtEntradas = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtSalidas = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtSaldoCaja = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TableSalidas = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtContado = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtCredito = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtNotasCredito = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtVentasNeta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CORTE DE CAJA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(637, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 930, 50));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText(" FECHA ACTIVA:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 150, 20));

        txtFecha.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtFecha.setText("-");
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 70, 100, 20));

        panelRegistrarNota1.setBackground(new java.awt.Color(255, 255, 255));
        panelRegistrarNota1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelRegistrarNota1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRegistrarNota1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRegistrarNota1MouseEntered(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(153, 204, 255));
        jButton15.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        jButton15.setToolTipText("F1 - Cerrar corte");
        jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton15MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelRegistrarNota1Layout = new javax.swing.GroupLayout(panelRegistrarNota1);
        panelRegistrarNota1.setLayout(panelRegistrarNota1Layout);
        panelRegistrarNota1Layout.setHorizontalGroup(
            panelRegistrarNota1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelRegistrarNota1Layout.setVerticalGroup(
            panelRegistrarNota1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelRegistrarNota1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, 50));

        panelRegistrarNota3.setBackground(new java.awt.Color(255, 255, 255));
        panelRegistrarNota3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelRegistrarNota3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRegistrarNota3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelRegistrarNota3MouseEntered(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(255, 255, 255));
        jButton17.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen4.png"))); // NOI18N
        jButton17.setToolTipText("F2 - Registrar un gasto");
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton17MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton17MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelRegistrarNota3Layout = new javax.swing.GroupLayout(panelRegistrarNota3);
        panelRegistrarNota3.setLayout(panelRegistrarNota3Layout);
        panelRegistrarNota3Layout.setHorizontalGroup(
            panelRegistrarNota3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelRegistrarNota3Layout.setVerticalGroup(
            panelRegistrarNota3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelRegistrarNota3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 50, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setText("Cobranza");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        TableCobranza = new Table3();
        TableCobranza.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableCobranza.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cliente", "Venta", "Total", "Saldo", "Pago", "S. Restante", "Adeudo", "FormaPago"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableCobranza.setToolTipText("");
        TableCobranza.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableCobranza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableCobranzaMouseClicked(evt);
            }
        });
        TableCobranza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableCobranzaKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(TableCobranza);
        if (TableCobranza.getColumnModel().getColumnCount() > 0) {
            TableCobranza.getColumnModel().getColumn(0).setPreferredWidth(100);
            TableCobranza.getColumnModel().getColumn(1).setPreferredWidth(10);
            TableCobranza.getColumnModel().getColumn(2).setPreferredWidth(12);
            TableCobranza.getColumnModel().getColumn(3).setPreferredWidth(20);
            TableCobranza.getColumnModel().getColumn(4).setPreferredWidth(20);
            TableCobranza.getColumnModel().getColumn(5).setPreferredWidth(20);
            TableCobranza.getColumnModel().getColumn(6).setPreferredWidth(20);
            TableCobranza.getColumnModel().getColumn(7).setPreferredWidth(50);
        }

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 30, 900, 140));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 910, 180));

        cargando.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(cargando, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, 70, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setBackground(new java.awt.Color(0, 0, 204));
        jLabel27.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 255));
        jLabel27.setText("Saldo en caja");
        jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        txtSaldoInicial.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtSaldoInicial.setToolTipText("Saldo inicial en caja");
        txtSaldoInicial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldoInicial.setEnabled(false);
        txtSaldoInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSaldoInicialKeyReleased(evt);
            }
        });
        jPanel6.add(txtSaldoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 120, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Saldo inicial");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 34, 90, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Entradas");
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 57, 90, -1));

        txtEntradas.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtEntradas.setToolTipText("Entradas de efectivo a caja");
        txtEntradas.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEntradas.setEnabled(false);
        txtEntradas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEntradasKeyReleased(evt);
            }
        });
        jPanel6.add(txtEntradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 53, 120, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Salidas");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 78, 90, -1));

        txtSalidas.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtSalidas.setToolTipText("Salidas de efectivo a caja");
        txtSalidas.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSalidas.setEnabled(false);
        txtSalidas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSalidasKeyReleased(evt);
            }
        });
        jPanel6.add(txtSalidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 73, 120, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Saldo en caja");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 97, 90, -1));

        txtSaldoCaja.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtSaldoCaja.setToolTipText("Efectivo en caja");
        txtSaldoCaja.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldoCaja.setEnabled(false);
        txtSaldoCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSaldoCajaKeyReleased(evt);
            }
        });
        jPanel6.add(txtSaldoCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 93, 120, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 530, 230, 130));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setBackground(new java.awt.Color(0, 0, 204));
        jLabel28.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 255));
        jLabel28.setText("Ventas");
        jPanel5.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        TableVentas = new Table3();
        TableVentas.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cliente", "Folio", "Tipo", "Subtotal", "Descuento", "IVA", "Total", "Encargado", "FormaPago"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVentas.setToolTipText("Haz doble click para visualizar la nota");
        TableVentas.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        TableVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableVentasKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(100);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(10);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(12);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(4).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(5).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(6).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(7).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(7).setHeaderValue("Encargado");
            TableVentas.getColumnModel().getColumn(8).setPreferredWidth(50);
        }

        jPanel5.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 900, 190));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 910, 230));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setBackground(new java.awt.Color(0, 0, 204));
        jLabel29.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 10)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 255));
        jLabel29.setText("Salidas");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        TableSalidas = new Table3();
        TableSalidas.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableSalidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Comprobante", "Concepto", "Total", "Fpa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableSalidas.setToolTipText("Haz doble click para visualizar la nota");
        TableSalidas.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableSalidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableSalidasMouseClicked(evt);
            }
        });
        TableSalidas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableSalidasKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(TableSalidas);
        if (TableSalidas.getColumnModel().getColumnCount() > 0) {
            TableSalidas.getColumnModel().getColumn(0).setPreferredWidth(100);
            TableSalidas.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableSalidas.getColumnModel().getColumn(2).setPreferredWidth(12);
            TableSalidas.getColumnModel().getColumn(3).setPreferredWidth(8);
        }

        jPanel7.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 27, 410, 97));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 420, 130));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setBackground(new java.awt.Color(0, 0, 204));
        jLabel30.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 255));
        jLabel30.setText("Resumen ventas");
        jPanel8.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        txtContado.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtContado.setToolTipText("Ventas a contado del día");
        txtContado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtContado.setEnabled(false);
        txtContado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContadoKeyReleased(evt);
            }
        });
        jPanel8.add(txtContado, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 120, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Ventas contado");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 34, 90, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Ventas crédito");
        jPanel8.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 57, 90, -1));

        txtCredito.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtCredito.setToolTipText("Ventas a crédito del día");
        txtCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCredito.setEnabled(false);
        txtCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCreditoKeyReleased(evt);
            }
        });
        jPanel8.add(txtCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 53, 120, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Notas crédito");
        jPanel8.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 78, 90, -1));

        txtNotasCredito.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtNotasCredito.setToolTipText("Notas de crédito generadas");
        txtNotasCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNotasCredito.setEnabled(false);
        txtNotasCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNotasCreditoKeyReleased(evt);
            }
        });
        jPanel8.add(txtNotasCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 73, 120, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Ventas netas");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 97, 90, -1));

        txtVentasNeta.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtVentasNeta.setToolTipText("Ventas netas del día");
        txtVentasNeta.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtVentasNeta.setEnabled(false);
        txtVentasNeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtVentasNetaKeyReleased(evt);
            }
        });
        jPanel8.add(txtVentasNeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 93, 120, -1));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 530, 250, 130));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseClicked
        eventoF1();
    }//GEN-LAST:event_jButton15MouseClicked

    private void jButton15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseEntered
        panelRegistrarNota1.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_jButton15MouseEntered

    private void jButton15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseExited
        panelRegistrarNota1.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jButton15MouseExited

    private void panelRegistrarNota1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRegistrarNota1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRegistrarNota1MouseClicked

    private void panelRegistrarNota1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRegistrarNota1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRegistrarNota1MouseEntered

    private void jButton17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton17MouseClicked
        eventoF2();
    }//GEN-LAST:event_jButton17MouseClicked

    private void jButton17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton17MouseEntered

        panelRegistrarNota3.setBackground(new Color(153, 204, 255));
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jButton17MouseEntered

    private void jButton17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton17MouseExited
        panelRegistrarNota3.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jButton17MouseExited

    private void panelRegistrarNota3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRegistrarNota3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRegistrarNota3MouseClicked

    private void panelRegistrarNota3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRegistrarNota3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelRegistrarNota3MouseEntered

    private void TableCobranzaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableCobranzaMouseClicked

    }//GEN-LAST:event_TableCobranzaMouseClicked

    private void TableCobranzaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableCobranzaKeyPressed

        int codigo = evt.getKeyCode();

    }//GEN-LAST:event_TableCobranzaKeyPressed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TableVentas.rowAtPoint(evt.getPoint());
            char letra = TableVentas.getValueAt(fila, 1).toString().charAt(0);
            if (letra == 'F') {
                int buscando = Integer.parseInt(TableVentas.getValueAt(fila, 1).toString().substring(1));
                vistaVenta vN = new vistaVenta(new javax.swing.JFrame(), true);
                vN.vaciarEmpleado(e.getId());
                vN.validandoDatos(buscando, 2);
                vN.setVisible(true);
            }
            if (letra == 'E') {
                notaCredito miNota = notaCreditodao.buscarPorId(Integer.parseInt(TableVentas.getValueAt(fila, 1).toString().substring(1)));

                if (miNota.getTipo().equals("Cancelación")) {
                    vistaCancelar cc = new vistaCancelar(new javax.swing.JFrame(), true);
                    cc.vaciarEmpleado(e.getId());
                    cc.vaciarDatos(new Ventas(), "", 0, false, miNota.getId());
                    cc.setVisible(true);
                }
                if (miNota.getTipo().equals("Devolución")) {
                    vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                    vdr.vaciarEmpleado(e.getId());
                    vdr.validandoDatos(3, miNota.getFolioVenta(), true, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 1).toString().substring(1)));
                    vdr.setVisible(true);
                }
                if (miNota.getTipo().equals("Bonificación")) {
                    vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                    vdr.vaciarEmpleado(e.getId());
                    vdr.validandoDatos(2, miNota.getFolioVenta(), true, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 1).toString().substring(1)));
                    vdr.setVisible(true);
                }
                if (miNota.getTipo().equals("Descuento")) {
                    vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                    vdr.vaciarEmpleado(e.getId());
                    vdr.validandoDatos(1, miNota.getFolioVenta(), true, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 1).toString().substring(1)));
                    vdr.setVisible(true);
                }
            }

        }
    }//GEN-LAST:event_TableVentasMouseClicked

    private void TableVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TableVentasKeyPressed

    private void TableSalidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableSalidasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableSalidasMouseClicked

    private void TableSalidasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableSalidasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TableSalidasKeyPressed

    private void txtSaldoInicialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoInicialKeyReleased

    }//GEN-LAST:event_txtSaldoInicialKeyReleased

    private void txtEntradasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntradasKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEntradasKeyReleased

    private void txtSalidasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalidasKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSalidasKeyReleased

    private void txtSaldoCajaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoCajaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoCajaKeyReleased

    private void txtContadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContadoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContadoKeyReleased

    private void txtCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCreditoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreditoKeyReleased

    private void txtNotasCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotasCreditoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNotasCreditoKeyReleased

    private void txtVentasNetaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVentasNetaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVentasNetaKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(vistaCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaCorte dialog = new vistaCorte(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableCobranza;
    private javax.swing.JTable TableSalidas;
    private javax.swing.JTable TableVentas;
    private javax.swing.JLabel cargando;
    private javax.swing.JLabel jButton15;
    private javax.swing.JLabel jButton17;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel panelRegistrarNota1;
    private javax.swing.JPanel panelRegistrarNota3;
    private javax.swing.JTextField txtContado;
    private javax.swing.JTextField txtCredito;
    private javax.swing.JTextField txtEntradas;
    private javax.swing.JLabel txtFecha;
    private javax.swing.JTextField txtNotasCredito;
    private javax.swing.JTextField txtSaldoCaja;
    private javax.swing.JTextField txtSaldoInicial;
    private javax.swing.JTextField txtSalidas;
    private javax.swing.JTextField txtVentasNeta;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    public void corteDiafinal(String fechaHoy) throws DocumentException {
        try {

            CorteDiario cd = corteDao.regresarCorte(fechaHoy);

            Formatter obj = new Formatter();
            Formatter obj2 = new Formatter();

            LocalDateTime m = LocalDateTime.now(); //Obtenemos la fecha actual
            String mes = String.valueOf(obj.format("%02d", m.getMonthValue()));//Modificamos la fecha al formato que queremos 
            String dia = String.valueOf(obj2.format("%02d", m.getDayOfMonth()));
            String DiagHoy = dia + "-" + mes + "-" + m.getYear();
            String fechaBien = fechaFormatoCorrecto(fechaHoy);

            config configura = configuracionesDao.buscarDatos();
            Font letra = new Font(Font.FontFamily.TIMES_ROMAN, 11);
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            FileOutputStream archivo;
            //File file = new File("C:\\Program Files (x86)\\AppLavanderia\\corte" + fechaHoy + ".pdf");
            File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\CorteDiario\\corte " + fechaBien + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            //Image img = Image.getInstance("C:\\Program Files (x86)\\AppLavanderia\\Iconos\\logo 100x100.jpg");
            Image img = Image.getInstance("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg");

            PdfPTable encabezado = new PdfPTable(2);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            float[] columnaEncabezado = new float[]{85f, 15f};
            encabezado.setWidths(columnaEncabezado);
            encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(configura.getNomnbre() + "\nCORTE DIARIO\nCORTE DEL DÍA " + fechaBien + "\nFECHA DE EMISIÓN " + DiagHoy, letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            encabezado.addCell(img);
            doc.add(encabezado);
            Paragraph dejarEspacio = new Paragraph();
            dejarEspacio.add(Chunk.NEWLINE);
            doc.add(dejarEspacio);

            Paragraph primero = new Paragraph();
            primero.setFont(letra);
            primero.add("VENTAS\n");
            doc.add(primero);
            doc.add(dejarEspacio);

            PdfPTable tablapro = new PdfPTable(9);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float[] columnapro = new float[]{35f, 10f, 9f, 14f, 14f, 14f, 14f, 6f, 6f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pro1 = new PdfPCell(new Phrase("Cliente", letra));
            PdfPCell pro2 = new PdfPCell(new Phrase("Folio", letra));
            PdfPCell pro3 = new PdfPCell(new Phrase("Tipo", letra));
            PdfPCell pro4 = new PdfPCell(new Phrase("SubTotal", letra));
            PdfPCell pro5 = new PdfPCell(new Phrase("Descuento", letra));
            PdfPCell pro6 = new PdfPCell(new Phrase("IVA", letra));
            PdfPCell pro7 = new PdfPCell(new Phrase("Total", letra));
            PdfPCell pro8 = new PdfPCell(new Phrase("U", letra));
            PdfPCell pro9 = new PdfPCell(new Phrase("Fpa", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro6.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro7.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro8.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro9.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);
            tablapro.addCell(pro6);
            tablapro.addCell(pro7);
            tablapro.addCell(pro8);
            tablapro.addCell(pro9);

            double sumSubtotalVentas = 0;
            double sumDescuentoVentas = 0;
            double sumIvaVentas = 0;
            double sumTotalVentas = 0;

            ///SE ENLISTAN LAS VENTAS
            List<Ventas> lsVentas = ventasDao.listarVentasDia(fechaHoy);
            for (int i = 0; i < lsVentas.size(); i++) {

                String nombreF = "";
                cell = new PdfPCell();
                for (int c = 0; c < lsClientes.size(); c++) {
                    if (lsClientes.get(c).getId() == lsVentas.get(i).getIdCliente()) {
                        if (lsClientes.get(c).getTipoPersona().equals("Persona Física")) {
                            nombreF = lsClientes.get(c).getNombre() + " " + lsClientes.get(c).getApellidoP() + " " + lsClientes.get(c).getApellidoM();
                        } else {
                            nombreF = lsClientes.get(c).getNombreComercial();
                        }
                    }
                }
                cell = new PdfPCell(new Phrase(nombreF, letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("F" + lsVentas.get(i).getFolio(), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("Ventas", letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(formateador.format(lsVentas.get(i).getSubTotal()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumSubtotalVentas = sumSubtotalVentas + lsVentas.get(i).getSubTotal();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(formateador.format(lsVentas.get(i).getDescuentos()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumDescuentoVentas = sumDescuentoVentas + lsVentas.get(i).getDescuentos();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(formateador.format(lsVentas.get(i).getIva()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumIvaVentas = sumIvaVentas + lsVentas.get(i).getIva();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(formateador.format(lsVentas.get(i).getTotal()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumTotalVentas = sumTotalVentas + lsVentas.get(i).getTotal();

                cell = new PdfPCell();
                for (int c = 0; c < lsEmpleados.size(); c++) {
                    if (lsEmpleados.get(c).getId() == lsVentas.get(i).getIdEmpleado()) {
                        cell = new PdfPCell(new Phrase("" + lsEmpleados.get(c).getId(), letra));;
                    }
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(lsVentas.get(i).getFormaPago().substring(0, 2), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);
            }

            List<notaCredito> lsNotasCredito = notaCreditodao.listarNotasPorDia(fechaHoy);
            //listar Notas de credito
            for (int i = 0; i < lsNotasCredito.size(); i++) {
                Ventas miVenta = ventasDao.buscarPorFolio(lsNotasCredito.get(i).getFolioVenta());

                cell = new PdfPCell();
                String nombre = "";
                for (int c = 0; c < lsClientes.size(); c++) {
                    if (lsClientes.get(c).getId() == miVenta.getIdCliente()) {
                        if (lsClientes.get(c).getTipoPersona().equals("Persona Física")) {
                            nombre = lsClientes.get(c).getNombre() + " " + lsClientes.get(c).getApellidoP() + " " + lsClientes.get(c).getApellidoM();
                        } else {
                            nombre = lsClientes.get(c).getNombreComercial();
                        }
                    }
                }
                cell = new PdfPCell(new Phrase(nombre, letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("E" + lsNotasCredito.get(i).getId(), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(lsNotasCredito.get(i).getTipo().substring(0, 5), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("-" + formateador.format(lsNotasCredito.get(i).getSubtotal()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumSubtotalVentas = sumSubtotalVentas - lsNotasCredito.get(i).getSubtotal();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("-", letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("-" + formateador.format(lsNotasCredito.get(i).getIva()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumIvaVentas = sumIvaVentas - lsNotasCredito.get(i).getIva();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("-" + formateador.format(lsNotasCredito.get(i).getTotal()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumTotalVentas = sumTotalVentas - lsNotasCredito.get(i).getTotal();

                cell = new PdfPCell();
                for (int c = 0; c < lsEmpleados.size(); c++) {
                    if (lsEmpleados.get(c).getId() == lsNotasCredito.get(i).getIdRecibe()) {
                        cell = new PdfPCell(new Phrase(lsEmpleados.get(c).getId() + "", letra));
                    }
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(lsNotasCredito.get(i).getFormaPago().substring(0, 2), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);
            }

            for (int i = 0; i < 2; i++) {
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(""));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);
            }

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Total", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(sumSubtotalVentas), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(sumDescuentoVentas), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(sumIvaVentas), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(sumTotalVentas), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            for (int i = 0; i < 2; i++) {
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(""));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
            }

            doc.add(tablapro);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(5);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnapro = new float[]{40f, 40f, 20f, 40f, 40f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            pro1 = new PdfPCell(new Phrase("Forma de pago", letra));
            pro2 = new PdfPCell(new Phrase("Total", letra));
            pro3 = new PdfPCell(new Phrase("", letra));
            pro4 = new PdfPCell(new Phrase("Concepto", letra));
            pro5 = new PdfPCell(new Phrase("Total", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);

            pro3.setBorder(0);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("01 Efectivo", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ventasDao.retornarPorFormaPago(fechaHoy, "01 Efectivo", "efectivo")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Ventas a contado", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getVentasContado()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("02 Cheque nominativo", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ventasDao.retornarPorFormaPago(fechaHoy, "02 Cheque nominativo", "cheque")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Ventas a crédito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getVentasCredito()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("03 Transferencia", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ventasDao.retornarPorFormaPago(fechaHoy, "03 Transferencia electrónica de fondos", "transferencia")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Notas a crédito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getTotalNotasCredito()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("04 Tarjeta de crédito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ventasDao.retornarPorFormaPago(fechaHoy, "04 Tarjeta de crédito", "credito")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Ventas netas", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getVentasContado() + cd.getVentasCredito() - cd.getTotalNotasCredito()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("28 Tarjeta de débito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ventasDao.retornarPorFormaPago(fechaHoy, "28 Tarjeta de débito", "debito")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("99 Por definir", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ventasDao.retornarPorFormaPago(fechaHoy, "99 Por definir","")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            doc.add(dejarEspacio);
            primero = new Paragraph();
            primero.setFont(letra);
            primero.add("COBRANZA\n");
            doc.add(primero);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(9);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnapro = new float[]{35f, 10f, 14f, 14f, 14f, 14f, 14f, 6f, 6f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            pro1 = new PdfPCell(new Phrase("Cliente", letra));
            pro2 = new PdfPCell(new Phrase("Venta", letra));
            pro3 = new PdfPCell(new Phrase("Total", letra));
            pro4 = new PdfPCell(new Phrase("Saldo", letra));
            pro5 = new PdfPCell(new Phrase("Pago", letra));
            pro6 = new PdfPCell(new Phrase("S. restante", letra));
            pro7 = new PdfPCell(new Phrase("Adeudo", letra));
            pro8 = new PdfPCell(new Phrase("U", letra));
            pro9 = new PdfPCell(new Phrase("Fpa", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro6.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro7.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro8.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro9.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);
            tablapro.addCell(pro6);
            tablapro.addCell(pro7);
            tablapro.addCell(pro8);
            tablapro.addCell(pro9);

            double totalPagos = 0;
            List<Pagos> lsPagos = pagosDao.listarPagosDia(fechaHoy);
            for (int i = 0; i < lsPagos.size(); i++) {
                Ventas miVenta = ventasDao.buscarPorFolio(lsPagos.get(i).getFolioVenta());
                cell = new PdfPCell();
                String nombre = "";
                for (int c = 0; c < lsClientes.size(); c++) {
                    if (lsClientes.get(c).getId() == miVenta.getIdCliente()) {
                        if (lsClientes.get(c).getTipoPersona().equals("Persona Física")) {
                            nombre = lsClientes.get(c).getNombre() + " " + lsClientes.get(c).getApellidoP() + " " + lsClientes.get(c).getApellidoM();
                        } else {
                            nombre = lsClientes.get(c).getNombreComercial();
                        }
                    }
                }

                cell = new PdfPCell(new Phrase(nombre, letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("F" + miVenta.getFolio(), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(miVenta.getTotal()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(lsPagos.get(i).getAldoAnterior()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(lsPagos.get(i).getCantidad()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                totalPagos = totalPagos + lsPagos.get(i).getCantidad();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(lsPagos.get(i).getNuevoSaldo()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);

                Credito miCredito = creditoDao.BuscarPorCodigoCliente(miVenta.getIdCliente());
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(miCredito.getAdeudo()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                for (int c = 0; c < lsEmpleados.size(); c++) {
                    if (lsEmpleados.get(c).getId() == lsPagos.get(i).getIdRecibe()) {
                        cell = new PdfPCell(new Phrase(lsEmpleados.get(c).getId() + "", letra));
                    }
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(lsPagos.get(i).getFormaPago().substring(0, 2), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

            }

            for (int i = 0; i < 3; i++) {
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(""));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);
            }

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Total", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(totalPagos), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            for (int i = 0; i < 4; i++) {
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(""));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);
            }

            doc.add(tablapro);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(5);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnapro = new float[]{40f, 40f, 20f, 40f, 40f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            pro1 = new PdfPCell(new Phrase("Forma de pago", letra));
            pro2 = new PdfPCell(new Phrase("Total", letra));
            pro3 = new PdfPCell(new Phrase("", letra));
            pro4 = new PdfPCell(new Phrase("Forma de pago", letra));
            pro5 = new PdfPCell(new Phrase("Total", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);

            pro3.setBorder(0);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("01 Efectivo", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(pagosDao.retornarPorFormaPago(fechaHoy, "01 Efectivo")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("02 Cheque nominativo", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(pagosDao.retornarPorFormaPago(fechaHoy, "02 Cheque nominativo")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("03 Transferencia", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(pagosDao.retornarPorFormaPago(fechaHoy, "03 Transferencia electrónica de fondos")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("04 Tarjeta de crédito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(pagosDao.retornarPorFormaPago(fechaHoy, "04 Tarjeta de crédito")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("28 Tarjeta de débito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(pagosDao.retornarPorFormaPago(fechaHoy, "28 Tarjeta de débito")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("99 Por definir", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(pagosDao.retornarPorFormaPago(fechaHoy, "99 Por definir")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            doc.add(dejarEspacio);
            primero = new Paragraph();
            primero.setFont(letra);
            primero.add("SALIDAS\n");
            doc.add(primero);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(5);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnapro = new float[]{35f, 35f, 20f, 6f, 6f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            pro1 = new PdfPCell(new Phrase("Comprobante", letra));
            pro2 = new PdfPCell(new Phrase("Concepto", letra));
            pro3 = new PdfPCell(new Phrase("Total", letra));
            pro4 = new PdfPCell(new Phrase("Fpa", letra));
            pro5 = new PdfPCell(new Phrase("U", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);

            double totalGastos = 0;
            List<Gastos> lsGastos = gastosDao.listarGastosPorDia(fechaHoy);
            for (int i = 0; i < lsGastos.size(); i++) {

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(lsGastos.get(i).getComprobante(), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(lsGastos.get(i).getDescripcion(), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(lsGastos.get(i).getPrecio()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                totalGastos = totalGastos + lsGastos.get(i).getPrecio();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(lsGastos.get(i).getFormaPago().substring(0, 2), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                for (int c = 0; c < lsEmpleados.size(); c++) {
                    if (lsEmpleados.get(c).getId() == lsGastos.get(i).getIdRecibe()) {
                        cell = new PdfPCell(new Phrase(lsEmpleados.get(c).getId() + "", letra));
                    }
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

            }

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(""));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Total", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(totalGastos), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            for (int i = 0; i < 2; i++) {
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(""));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);
            }

            doc.add(tablapro);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(5);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnapro = new float[]{40f, 40f, 20f, 40f, 40f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            pro1 = new PdfPCell(new Phrase("Forma de pago", letra));
            pro2 = new PdfPCell(new Phrase("Total", letra));
            pro3 = new PdfPCell(new Phrase("", letra));
            pro4 = new PdfPCell(new Phrase("Forma de pago", letra));
            pro5 = new PdfPCell(new Phrase("Total", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);

            pro3.setBorder(0);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("01 Efectivo", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(gastosDao.retornarPorFormaPago(fechaHoy, "01 Efectivo")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("02 Cheque nominativo", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(gastosDao.retornarPorFormaPago(fechaHoy, "02 Cheque nominativo")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("03 Transferencia", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(gastosDao.retornarPorFormaPago(fechaHoy, "03 Transferencia electrónica de fondos")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("04 Tarjeta de crédito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(gastosDao.retornarPorFormaPago(fechaHoy, "04 Tarjeta de crédito")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("28 Tarjeta de débito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(gastosDao.retornarPorFormaPago(fechaHoy, "28 Tarjeta de débito")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("99 Por definir", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(gastosDao.retornarPorFormaPago(fechaHoy, "99 Por definir")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            //las compras
            doc.add(dejarEspacio);
            primero = new Paragraph();
            primero.setFont(letra);
            primero.add("COMPRAS\n");
            doc.add(primero);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(8);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnapro = new float[]{10f, 35f, 14f, 14f, 14f, 14f, 6f, 6f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            pro1 = new PdfPCell(new Phrase("Código", letra));
            pro2 = new PdfPCell(new Phrase("Proveedor", letra));
            pro3 = new PdfPCell(new Phrase("Subtotal", letra));
            pro4 = new PdfPCell(new Phrase("Descuentos", letra));
            pro5 = new PdfPCell(new Phrase("IVA", letra));
            pro6 = new PdfPCell(new Phrase("Total", letra));
            pro7 = new PdfPCell(new Phrase("U", letra));
            pro8 = new PdfPCell(new Phrase("Fpa", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro6.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro7.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro8.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);
            tablapro.addCell(pro6);
            tablapro.addCell(pro7);
            tablapro.addCell(pro8);

            double sumSubtotalCompras = 0;
            double sumDescuentoCompras = 0;
            double sumIvaCompras = 0;
            double sumTotalCompras = 0;

            List<ordenCompra> lsCompras = ordenDao.listarOrdenesSoloUndia(fechaHoy);
            lsCompras = ordenDao.listarOrdenesCanceladasSoloUnDia(lsCompras, fechaHoy);
            for (int i = 0; i < lsCompras.size(); i++) {

                cell = new PdfPCell();
                String id = "";
                if (lsCompras.get(i).getTotal() > 0) {
                    id = lsCompras.get(i).getId() + "";
                } else {
                    id = "C" + lsCompras.get(i).getId();
                }
                cell = new PdfPCell(new Phrase(id, letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                Proveedores p = new Proveedores();
                for (int j = 0; j < lsProveedor.size(); j++) {
                    if (lsProveedor.get(j).getId() == lsCompras.get(i).getIdProveedor()) {
                        if (lsProveedor.get(j).getTipoPersona().equals("Persona Física")) {
                            p.setNombre(lsProveedor.get(j).getNombre() + " " + lsProveedor.get(j).getApellidoP() + " " + lsProveedor.get(j).getApellidoM());
                        } else {
                            p.setNombre(lsProveedor.get(j).getNombreComercial());
                        }
                    }
                }
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(p.getNombre(), letra));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(lsCompras.get(i).getSubtotal()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumSubtotalCompras = sumSubtotalCompras + lsCompras.get(i).getSubtotal();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(lsCompras.get(i).getDescuento()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumDescuentoCompras = sumDescuentoCompras + lsCompras.get(i).getDescuento();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(lsCompras.get(i).getIva()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumIvaCompras = sumIvaCompras + lsCompras.get(i).getIva();

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("" + formateador.format(lsCompras.get(i).getTotal()), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                sumTotalCompras = sumTotalCompras + lsCompras.get(i).getTotal();

                cell = new PdfPCell();
                for (int c = 0; c < lsEmpleados.size(); c++) {
                    if (lsEmpleados.get(c).getId() == lsCompras.get(i).getIdRecibe()) {
                        cell = new PdfPCell(new Phrase(lsEmpleados.get(c).getId() + "", letra));
                    }
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(lsCompras.get(i).getFormaPago().substring(0, 2), letra));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);

            }

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(""));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Total", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(sumSubtotalCompras), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(sumDescuentoCompras), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(sumIvaCompras), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(sumTotalCompras), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            for (int i = 0; i < 2; i++) {
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(""));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);
            }

            doc.add(tablapro);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(5);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnapro = new float[]{40f, 40f, 20f, 40f, 40f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            pro1 = new PdfPCell(new Phrase("Forma de pago", letra));
            pro2 = new PdfPCell(new Phrase("Total", letra));
            pro3 = new PdfPCell(new Phrase("", letra));
            pro4 = new PdfPCell(new Phrase("Forma de pago", letra));
            pro5 = new PdfPCell(new Phrase("Total", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);

            pro3.setBorder(0);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("01 Efectivo", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ordenDao.retornarPorFormaPago(fechaHoy, "01 Efectivo")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("02 Cheque nominativo", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ordenDao.retornarPorFormaPago(fechaHoy, "02 Cheque nominativo")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("03 Transferencia", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ordenDao.retornarPorFormaPago(fechaHoy, "03 Transferencia electrónica de fondos")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("04 Tarjeta de crédito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ordenDao.retornarPorFormaPago(fechaHoy, "04 Tarjeta de crédito")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("28 Tarjeta de débito", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ordenDao.retornarPorFormaPago(fechaHoy, "28 Tarjeta de débito")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("99 Por definir", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(ordenDao.retornarPorFormaPago(fechaHoy, "99 Por definir")), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            doc.add(dejarEspacio);
            primero = new Paragraph();
            primero.setFont(letra);
            primero.add("RESUMEN\n");
            doc.add(primero);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(6);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnapro = new float[]{15f, 40f, 30f, 40f, 30f, 15f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            pro1 = new PdfPCell(new Phrase("", letra));
            pro2 = new PdfPCell(new Phrase("Concepto", letra));
            pro3 = new PdfPCell(new Phrase("Cantidad", letra));
            pro4 = new PdfPCell(new Phrase("Concepto", letra));
            pro5 = new PdfPCell(new Phrase("Cantidad", letra));
            pro6 = new PdfPCell(new Phrase("", letra));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro6.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);
            tablapro.addCell(pro6);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Saldo inicial", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getSaldoInicial()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Retiro", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getRetiro()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Entradas", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getTotalEntradas()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Saldo final", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getSaldoFinal()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Salidas", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getTotalSalidas()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Observaciones", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(cd.getComentario(), letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Saldo en caja", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getSaldoInicial() + cd.getTotalEntradas() - cd.getTotalSalidas()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Revisó", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            for (int c = 0; c < lsEmpleados.size(); c++) {
                if (lsEmpleados.get(c).getId() == cd.getIdRecibe()) {
                    cell = new PdfPCell(new Phrase(lsEmpleados.get(c).getNombre() + "", letra));
                }
            }
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Ganancia", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("" + formateador.format(cd.getGanancia()), letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);
            doc.add(dejarEspacio);
            doc.add(dejarEspacio);

            PdfPTable tablaFinal = new PdfPTable(3);
            tablaFinal.setWidthPercentage(100);
            tablaFinal.getDefaultCell().setBorder(0);
            columnapro = new float[]{50f, 50f, 50f};
            tablaFinal.setWidths(columnapro);
            tablaFinal.setHorizontalAlignment(Element.ALIGN_LEFT);
            for (int i = 0; i < 3; i++) {
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("____________________"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablaFinal.addCell(cell);
            }

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Elaboró", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Gerencia", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Auditoria", letra));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell);

            primero = new Paragraph();
            primero.setFont(negrita);
            primero.add(Chunk.NEWLINE);
            primero.add(Chunk.NEWLINE);
            primero.add(Chunk.NEWLINE);
            primero.add(Chunk.NEWLINE);
            doc.add(primero);

            doc.add(tablaFinal);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException e) {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe cerrar antes el documento");
            panel.showNotification();
        }
    }

}
