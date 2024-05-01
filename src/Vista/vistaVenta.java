/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ClienteDao;
import Modelo.Clientes;
import Modelo.CorteDiarioDao;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.HistorialCliente;
import Modelo.HistorialClienteDao;
import Modelo.PagosDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.Ventas;
import Modelo.VentasDao;
import Modelo.VistaVentaListener;
import Modelo.bitacoraDao;
import Modelo.config;
import Modelo.configuraciones;
import Modelo.detalleVenta;
import Modelo.imprimiendo;
import Modelo.notaCreditoDao;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.DefaultListModel;
import com.raven.swing.Table3;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.Notification;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class vistaVenta extends javax.swing.JDialog {

    private VistaVentaListener vistaVentaListener;

    ClienteDao client = new ClienteDao();
    VentasDao ventaDao = new VentasDao();
    Eventos event = new Eventos();
    ProductoDao productoDao = new ProductoDao();
    EmpleadosDao emple = new EmpleadosDao();
    CorteDiarioDao corteDao = new CorteDiarioDao();
    CreditoDao creditoDao = new CreditoDao();
    notaCreditoDao notaDao = new notaCreditoDao();
    bitacoraDao bitacora = new bitacoraDao();
    PagosDao pagosDao = new PagosDao();
    EmpleadosDao empleadosDao = new EmpleadosDao();
    HistorialClienteDao historialDao = new HistorialClienteDao();
    configuraciones configuracionesDao = new configuraciones();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp;

    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat formato = new DecimalFormat("0.00");
    int indicador;
    int folioMostrar;
    Calendar calendario;
    int clienteElegido = 0;
    List<String> conMayoreo = new ArrayList<String>();
    double totalAPagar = 0.00;
    boolean guardar, ver, imprimir, reponer, devolver, cancelar, cambiandoPrecio, btnEliminar, btnModificar;
    String hora;
    int idEmpleado;

    public void vaciarEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public vistaVenta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocation(250, 50);
        Seticon();
        txtMetodoPago.setBackground(new Color(255, 255, 255));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocation(200, 50);
                };

            }
        });
        txtTiempoCredito.setBackground(new Color(240, 240, 240));
        txtInteresCredito.setBackground(new Color(240, 240, 240));

        controladorLista.setVisible(false);
        listClientes.setVisible(false);
        controladorListaProductos.setVisible(false);
        txtSubtotalFinal.setBackground(new Color(240, 240, 240));
        txtIvaFInal.setBackground(new Color(240, 240, 240));
        txtDescuentoFinal.setBackground(new Color(240, 240, 240));
        txtTotalFinal.setBackground(new Color(240, 240, 240));
        txtSaldo.setBackground(new Color(240, 240, 240));
        txtPagos.setBackground(new Color(240, 240, 240));
        txtNotasCredito.setBackground(new Color(240, 240, 240));
        //Datos del cliente

        txtFormaPago.setBackground(Color.WHITE);

        TableVenta.setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(new Color(204, 204, 204));
        TableVenta.setSelectionForeground(Color.WHITE);
        TableVenta.setSelectionForeground(Color.BLACK);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TableVenta.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(8).setCellRenderer(tcrDerecha);
        ((DefaultTableCellRenderer) TableVenta.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

    }

    public void setJFrame2Listener(VistaVentaListener listener) {
        this.vistaVentaListener = listener;
    }

    public void validandoDatos(int folioMostrar, int indicador) {
        this.indicador = indicador;
        this.folioMostrar = folioMostrar;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Ventas");
        cargando.setVisible(false);
        SetImagenLabel(cargando, "/Imagenes/loading.gif");
        tmp = (DefaultTableModel) TableVenta.getModel();
        if (indicador == 1) { //Nueva venta
            int i = ventaDao.idVenta() + 1; //Se obtiene el numero actual que contendra la siguiente nota
            txtFolio.setText("F" + String.valueOf(i));
            cambiandoPrecio = ver = imprimir = reponer = devolver = cancelar = cancelar = false;
            guardar = btnEliminar = btnModificar = true;
            panelReporte.setBackground(new Color(179, 195, 219));
            panelImprimir.setBackground(new Color(179, 195, 219));
            panelDevolucion.setBackground(new Color(179, 195, 219));
            panelCancelar.setBackground(new Color(179, 195, 219));
            panelReposicion.setBackground(new Color(179, 195, 219));
            panelPago.setBackground(new Color(179, 195, 219));
            jLabel63.setText(fechaFormatoCorrecto(corteDao.getDia()));
        } else {
            if (indicador == 2) {//ver venta
                bloquearDatos(false);
                vaciarDatos(folioMostrar);
            } else { //cotizacion

            }
        }
    }

    public java.util.Date StringADateHora(String cambiar) {//Este metodo te transforma un String a date (hora)
        SimpleDateFormat formato_del_Texto = new SimpleDateFormat("HH:mm");
        Date fechaE = null;
        try {
            fechaE = formato_del_Texto.parse(cambiar);
            return fechaE;
        } catch (ParseException ex) {
            return null;
        }
    }

    public void vaciarDatos(int folioBuscar) {
        Ventas ven = ventaDao.buscarPorFolio(folioBuscar);//Obtenemos los datos de la nota de la BD
        txtFolio.setText("F" + String.valueOf(ven.getFolio())); //Colocamos la fecha de recepcion de la nota y su folio
        cambiandoPrecio = btnEliminar = btnModificar = false;
        jLabel63.setText(fechaFormatoCorrecto(ven.getFecha()));

        Date horaFecha = StringADateHora(ven.getHora());
        Formatter obj2 = new Formatter();
        String horaModificada = horaFecha.getHours() + ":" + String.valueOf(obj2.format("%02d", horaFecha.getMinutes()));
        jLabel7.setText(horaModificada);
        ver = imprimir = true;
        clienteElegido = ven.getIdCliente();
        if (ven.getEstatus() == 1) {
            espacioFecha.setText("CANCELADA ");
            guardar = reponer = devolver = cancelar = cambiandoPrecio = false;

        } else {
            if (ven.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                btnDescuentos.setToolTipText("F7 - Descuento");

                cambiandoPrecio = true;
            } else {
                btnDescuentos.setToolTipText("F7 - Bonificación");
            }
            espacioFecha.setText("REGISTRADA ");
            if (notaDao.retornarSumaNotas(ven.getFolio()) >= ven.getTotal()) {
                cancelar = devolver = reponer = cambiandoPrecio = false;
            } else {
                cancelar = devolver = reponer = true;
            }
            if (pagosDao.regresarPagos(ven.getFolio()) >= (ven.getTotal() - notaDao.retornarSumaNotas(ven.getFolio()))) {
                cambiandoPrecio = false;
            }

        }
        if (ven.getFacturado() == 1) {
            espacioFecha.setText(espacioFecha.getText() + " - FACTURADA");
        }

        //vaciamos cada uno de los datos de la nota como si se acabara de generar la nota, bloqueando los espacios de texto para que no se puedan modificar
        Clientes alistar = client.BuscarPorCodigo(ven.getIdCliente());
        System.out.println(ven.getIdCliente());

        System.out.println(alistar.getTipoPersona());
        if (alistar.getTipoPersona().equals("Persona Física")) {
            txtNombreCliente.setText(alistar.getNombre() + " " + alistar.getApellidoP() + " " + alistar.getApellidoM());
        } else {
            txtNombreCliente.setText(alistar.getNombreComercial());
            txtTipoSociedad.setText(alistar.getTipoPersona());
        }
        txtTelefono.setText(alistar.getTelefono());
        txtCorreo.setText(alistar.getCorreo());

        txtTipoPersona.setText(alistar.getTipoPersona());
        txtRegimenFiscal.setText(alistar.getRegimenFiscal());
        txtRFC.setText(alistar.getRfc());
        txtCFDI.setText(alistar.getCfdi());

        txtDomicilio.setText(alistar.getCalle() + " " + alistar.getNumeroInterior() + " " + alistar.getNumeroExterior() + " " + alistar.getColonia() + " " + alistar.getMunicipio() + " " + alistar.getEstado());
        txtCodigoPostal.setText(alistar.getCodigoPostal());

        txtSubtotalFinal.setText("$" + formateador.format(ven.getSubTotal()));
        txtDescuentoFinal.setText("$" + formateador.format(ven.getDescuentos()));
        txtIvaFInal.setText("$" + formateador.format(ven.getIva()));
        txtTotalFinal.setText("$" + formateador.format(ven.getTotal()));
        txtComentarios.setText(ven.getComentarios());

        txtMetodoPago.removeAllItems();
        txtMetodoPago.addItem(ven.getMetodoPago());
        txtFormaPago.removeAllItems();
        txtFormaPago.addItem(ven.getFormaPago());
        txtFormaPago.setEnabled(true);
        Credito credito = creditoDao.BuscarPorCodigoCliente(alistar.getId());
        if (credito.getId() != 0) {
            txtAdeudo.setText("$" + formateador.format(credito.getAdeudo()));
            txtLimCredito.setText("$" + formateador.format(credito.getLimite()));
            txtTiempoCredito.setText(credito.getPlazo() + " días");
            txtInteresCredito.setText(formato.format(credito.getInteresOrdinario()) + "%");
            txtTiempoCredito.setVisible(true);
            txtInteresCredito.setVisible(true);
            lbPlazo.setVisible(true);
            lbInteres.setVisible(true);
        } else {
            txtTiempoCredito.setVisible(false);
            txtInteresCredito.setVisible(false);
            jLabel30.setVisible(false);
        }

        if (notaDao.retornarSumaNotas(ven.getFolio()) != 0) {
            txtNotasCredito.setText("$" + formateador.format(notaDao.retornarSumaNotas(ven.getFolio())));
        }
        if (pagosDao.regresarPagos(ven.getFolio()) != 0) {
            txtPagos.setText("$" + formateador.format(pagosDao.regresarPagos(ven.getFolio())));
        }

        txtSaldo.setText("$" + formateador.format(ven.getSaldo()));

        List<detalleVenta> ListaDetalles = ventaDao.regresarDetalles(ven.getFolio());
        tmp = (DefaultTableModel) TableVenta.getModel();
        //vaciamos cada concepto en la tabla 
        for (int i = 0; i < ListaDetalles.size(); i++) {

            Producto proc = productoDao.BuscarPorCodigo(ListaDetalles.get(i).getCodigo());
            Object[] o = new Object[9];
            o[0] = ListaDetalles.get(i).getCodigo();
            o[1] = formato.format(ListaDetalles.get(i).getCantidad());
            o[2] = proc.getTipoVenta();
            o[3] = ListaDetalles.get(i).getDescripcion();
            o[4] = "$" + formateador.format(ListaDetalles.get(i).getPrecioUnitario());
            o[5] = "$" + formateador.format(ListaDetalles.get(i).getPrecioUnitario() * ListaDetalles.get(i).getCantidad());
            double importeT = ListaDetalles.get(i).getPrecioUnitario() * ListaDetalles.get(i).getCantidad();
            double ivaT = (importeT - ListaDetalles.get(i).getDescuento()) * .16;
            o[6] = "$" + formateador.format(ListaDetalles.get(i).getDescuento());
            o[7] = "$" + formateador.format(ivaT);
            o[8] = "$" + formateador.format(ivaT + (importeT - ListaDetalles.get(i).getDescuento()));

            tmp.addRow(o);
        }
        TableVenta.setModel(tmp);
        panelGuardar.setBackground(new Color(179, 195, 219));

        if (ver == true) {
            panelReporte.setBackground(new Color(255, 255, 255));
        } else {
            panelReporte.setBackground(new Color(179, 195, 219));
        }

        if (imprimir == true) {
            panelImprimir.setBackground(new Color(255, 255, 255));
        } else {
            panelImprimir.setBackground(new Color(179, 195, 219));
        }

        if (devolver == true) {
            panelDevolucion.setBackground(new Color(255, 255, 255));
        } else {
            panelDevolucion.setBackground(new Color(179, 195, 219));
        }

        if (cancelar == true) {
            panelCancelar.setBackground(new Color(255, 255, 255));
        } else {
            panelCancelar.setBackground(new Color(179, 195, 219));
        }

        if (reponer == true) {
            panelReposicion.setBackground(new Color(255, 255, 255));
        } else {
            panelReposicion.setBackground(new Color(179, 195, 219));
        }

        if (cambiandoPrecio == true) {
            panelPago.setBackground(new Color(255, 255, 255));
        } else {
            panelPago.setBackground(new Color(179, 195, 219));
        }

    }

    public String fechaFormatoCorrecto(String fecha) {
        Date diaActual = StringADate(fecha);
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

    public static boolean isNumeric(String s) {
        if (s == null || s.equals("")) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static double redondear(double valor, int decimales) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(decimales, RoundingMode.HALF_UP); // Puedes elegir otros modos de redondeo si lo deseas
        return bd.doubleValue();
    }

    public void calculandoImporte() {
        try {
            double subtotal = 0;
            double descuento = 0;
            double iva = 0;
            double total = 0;
            int numFIla = TableVenta.getRowCount();
            for (int i = 0; i < numFIla; i++) {
                Number cantidadImporte = formateador.parse(removefirstChar(TableVenta.getModel().getValueAt(i, 5).toString()));
                subtotal = subtotal + cantidadImporte.doubleValue();
                Number cantidadDescuento = formateador.parse(removefirstChar(TableVenta.getModel().getValueAt(i, 6).toString()));
                descuento = descuento + cantidadDescuento.doubleValue();
            }
            if (subtotal == 0) {
                txtSubtotalFinal.setText("");
                txtDescuentoFinal.setText("");
                txtIvaFInal.setText("");
                txtTotalFinal.setText("");
            } else {
                txtSubtotalFinal.setText("$" + formateador.format(subtotal));
                txtDescuentoFinal.setText("$" + formateador.format(descuento));
                double subMDescuento = subtotal - descuento;
                double ivaCalculado = subMDescuento * 0.16;
                txtIvaFInal.setText("$" + formateador.format(ivaCalculado));
                txtTotalFinal.setText("$" + formateador.format(ivaCalculado + subMDescuento));
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String removefirstChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(1);
    }

    public void rellenarListaProductos() {
        DefaultListModel model = new DefaultListModel();
        controladorListaProductos.setVisible(true);
        controladorListaProductos.setBounds(controladorListaProductos.getX(), controladorListaProductos.getY(), controladorListaProductos.getWidth(), 85);
        listProductos.setBounds(listProductos.getX(), listProductos.getY(), listProductos.getWidth(), 85);
        if ("".equals(txtCodigo.getText())) {
            controladorListaProductos.setVisible(false);
        } else {
            List<Producto> lista = productoDao.buscarLetraCodigoDescripcion(txtCodigo.getText());
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getEstado() == 1) {
                    model.addElement(lista.get(i).getDescripcion());
                }
            }
            listProductos.setModel(model);
        }

    }

    public void vaciarCotizacion(DefaultTableModel modeloCotizacion, int idClienteCotizacion) {
        modelo = modeloCotizacion;
        TableVenta.setModel(modelo);
        clienteElegido = idClienteCotizacion;
        TableVenta.setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(new Color(204, 204, 204));
        TableVenta.setSelectionForeground(Color.WHITE);
        TableVenta.setSelectionForeground(Color.BLACK);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TableVenta.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(2).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(5).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableVenta.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        calculandoImporte();
        Clientes alistar = client.BuscarPorCodigo(idClienteCotizacion);
        if (alistar.getTipoPersona().equals("Persona Física")) {
            txtNombreCliente.setText(alistar.getNombre() + " " + alistar.getApellidoP() + " " + alistar.getApellidoM());
        } else {
            txtNombreCliente.setText(alistar.getNombreComercial());
            txtTipoSociedad.setText(alistar.getTipoPersona());
        }
        txtTelefono.setText(alistar.getTelefono());
        txtCorreo.setText(alistar.getCorreo());

        txtTipoPersona.setText(alistar.getTipoPersona());
        txtRegimenFiscal.setText(alistar.getRegimenFiscal());
        txtRFC.setText(alistar.getRfc());
        txtCFDI.setText(alistar.getCfdi());

        txtDomicilio.setText(alistar.getCalle() + " " + alistar.getNumeroInterior() + " " + alistar.getNumeroExterior() + " " + alistar.getColonia() + " " + alistar.getMunicipio() + " " + alistar.getEstado());
        txtCodigoPostal.setText(alistar.getCodigoPostal());
        Credito credito = creditoDao.BuscarPorCodigoCliente(alistar.getId());
        if (credito.getId() != 0) {
            txtAdeudo.setText("$" + formateador.format(credito.getAdeudo()));
            txtLimCredito.setText("$" + formateador.format(credito.getLimite()));
        }
    }

    public void eventoF1() {
        bloquearDatos(true);
        limpiarDatosNota();
        LimpiarTabla();
        txtFolio.setText("F" + (ventaDao.idVenta() + 1));
        ver = imprimir = reponer = devolver = cancelar = cancelar = false;
        guardar = cambiandoPrecio = btnEliminar = btnModificar = true;
        jLabel63.setText(fechaFormatoCorrecto(corteDao.getDia()));
        panelReporte.setBackground(new Color(179, 195, 219));
        panelImprimir.setBackground(new Color(179, 195, 219));
        panelDevolucion.setBackground(new Color(179, 195, 219));
        panelCancelar.setBackground(new Color(179, 195, 219));
        panelReposicion.setBackground(new Color(179, 195, 219));
        panelPago.setBackground(new Color(179, 195, 219));
        panelGuardar.setBackground(new Color(255, 255, 255));
        txtMetodoPago.removeAllItems();
        txtMetodoPago.addItem("Método de pago *");
        txtMetodoPago.addItem("PUE Pago en una sola exhibición");
        txtMetodoPago.addItem("PPD Pago en parcialidades o diferido");
        indicador = 1;
    }

    public void limpiarDatosNota() {
        txtBuscarCliente.setText("");
        txtNombreCliente.setText("");
        txtRFC.setText("");
        txtCorreo.setText("");
        txtRegimenFiscal.setText("");
        txtTelefono.setText("");
        txtDomicilio.setText("");
        txtTipoPersona.setText("");
        txtCFDI.setText("");
        txtCodigoPostal.setText("");
        txtLimCredito.setText("");
        txtAdeudo.setText("");
        txtTipoSociedad.setText("");
        espacioFecha.setText("-");
        txtSubtotalFinal.setText("");
        txtIvaFInal.setText("");
        txtTotalFinal.setText("");
        txtDescuentoFinal.setText("");
        txtNotasCredito.setText("");
        txtPagos.setText("");
        txtSaldo.setText("");
        txtTiempoCredito.setText("");
        txtInteresCredito.setText("");
        txtMetodoPago.setSelectedIndex(0);
        txtFormaPago.setSelectedIndex(0);
        txtCodigo.setText("");
        txtComentarios.setText("");
        controladorListaProductos.setVisible(false);
        listClientes.setVisible(false);
        clienteElegido = 0;
        totalAPagar = 0.00;
        LimpiarTabla();
        jLabel7.setText("-");

    }

    public void LimpiarTabla() {
        TableVenta.setModel(tmp);
        for (int i = 0; i < tmp.getRowCount(); i++) {
            tmp.removeRow(i);
            i = i - 1;
        }
    }

    public void eventoF2() {
        if (guardar == true) {
            if (!"".equals(txtNombreCliente.getText()) && !"".equals(txtSubtotalFinal.getText()) && txtMetodoPago.getSelectedIndex() != 0) {
                try {
                    Empleados empleado = emple.seleccionarEmpleado("", idEmpleado);

                    ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                    ContraseñaConUsuarioVenta cc2 = new ContraseñaConUsuarioVenta(new javax.swing.JFrame(), true);
                    variasFormasPago vfP = new variasFormasPago(new javax.swing.JFrame(), true);
                    Number totalC = formateador.parse(removefirstChar(txtTotalFinal.getText()));
                    if (txtFormaPago.getSelectedItem().toString().equals("01 Efectivo")) {
                        cc2.vaciarEmpleado(empleado.getId(), totalC.doubleValue());
                        cc2.setVisible(true);
                    } else {
                        if (txtFormaPago.getSelectedItem().toString().equals("Diferentes formas de pago")) {
                            vfP.vaciarEmpleado(empleado.getId(), totalC.doubleValue());
                            vfP.setVisible(true);
                        } else {
                            cc.vaciarEmpleado(empleado.getId());
                            cc.setVisible(true);
                        }
                    }
                    if (cc.contraseñaAceptada == true || cc2.contraseñaAceptada == true || vfP.contraseñaAceptada == true) {
                        new Thread() {
                            public void run() {
                                try {
                                    cargando.setVisible(true);
                                    proceso();
                                    cargando.setVisible(false);
                                    Ventas venta = new Ventas();
                                    LocalDateTime m = LocalDateTime.now();
                                    venta.setHora((m.getHour()) + ":" + m.getMinute() + ":" + m.getSecond());
                                    venta.setFecha(corteDao.getDia());
                                    venta.setIdCliente(clienteElegido);

                                    Number subtotalC = formateador.parse(removefirstChar(txtSubtotalFinal.getText()));
                                    venta.setSubTotal(subtotalC.doubleValue());
                                    Number descuentosC = formateador.parse(removefirstChar(txtDescuentoFinal.getText()));
                                    venta.setDescuentos(descuentosC.doubleValue());
                                    Number ivaC = formateador.parse(removefirstChar(txtIvaFInal.getText()));
                                    venta.setIva(ivaC.doubleValue());
                                    venta.setTotal(totalC.doubleValue());

                                    venta.setIdEmpleado(empleado.getId());
                                    venta.setComentarios(txtComentarios.getText());
                                    venta.setMetodoPago(txtMetodoPago.getSelectedItem().toString());
                                    venta.setFormaPago(txtFormaPago.getSelectedItem().toString());

                                    if (vfP.contraseñaAceptada == true) {
                                        venta.setEfectivo(vfP.efectivo);
                                        venta.setCheque(vfP.cheque);
                                        venta.setTransferencia(vfP.transferencia);
                                        venta.setCredito(vfP.credito);
                                        venta.setDebito(vfP.debito);
                                    }

                                    if (venta.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                                        Credito miCredito = creditoDao.BuscarPorCodigoCliente(venta.getIdCliente());
                                        if (miCredito.getId() == 0) {
                                            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "No se ha autorizado crédito");
                                            panel.showNotification();
                                        } else {
                                            double nuevoAdeudo = venta.getTotal() + miCredito.getAdeudo();
                                            if (nuevoAdeudo > miCredito.getLimite()) {
                                                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Crédito no suficiente");
                                                panel.showNotification();
                                            } else {
                                                LocalDate fechaHoy = LocalDate.now();
                                                LocalDate fechaCreacion = LocalDate.parse(miCredito.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                                LocalDate fechaVencimiento = fechaCreacion.plusDays(miCredito.getVigencia());
                                                if (fechaVencimiento.isBefore(fechaHoy)) {
                                                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Se ha terminado la vigencia del crédito");
                                                    panel.showNotification();
                                                } else {
                                                    venta.setTotal(venta.getTotal() + (venta.getTotal() * (miCredito.getInteresOrdinario() / 100)));
                                                    creditoDao.aumentarAdeudo(miCredito.getIdCliente(), venta.getTotal());
                                                    miCredito = creditoDao.BuscarPorCodigoCliente(venta.getIdCliente());
                                                    HistorialCliente hc = new HistorialCliente();
                                                    hc.setAbono(0);
                                                    hc.setCargo(venta.getTotal());
                                                    hc.setFecha(venta.getFecha());
                                                    hc.setFolio("F" + (ventaDao.idVenta() + 1));
                                                    hc.setIdCliente(venta.getIdCliente());
                                                    hc.setIdRecibe(idEmpleado);
                                                    hc.setMovimiento("Venta a crédito");
                                                    hc.setSaldo(miCredito.getAdeudo());
                                                    historialDao.registrarMovimiento(hc);
                                                    bitacora.registrarRegistro("Se registro la venta a credito de " + txtNombreCliente.getText() + " por $" + formateador.format(venta.getTotal()), idEmpleado, corteDao.getDia());
                                                    ventaDao.registrarVenta(venta);

                                                    registrarDetalle();

                                                    bloquearDatos(false);
                                                    guardar = btnEliminar = cambiandoPrecio = btnModificar = false;
                                                    ver = imprimir = reponer = devolver = cancelar = cancelar = true;
                                                    LimpiarTabla();
                                                    vaciarDatos(Integer.parseInt(txtFolio.getText().substring(1)));

                                                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Venta registrada exitosamente");
                                                    panel.showNotification();
                                                }
                                            }
                                        }
                                    } else {
                                        ventaDao.registrarVenta(venta);
                                        bitacora.registrarRegistro("Venta registrada de " + txtNombreCliente.getText() + " por $" + formateador.format(venta.getTotal()), idEmpleado, corteDao.getDia());

                                        registrarDetalle();

                                        bloquearDatos(false);
                                        guardar = btnEliminar = cambiandoPrecio = btnModificar = false;
                                        ver = imprimir = reponer = devolver = cancelar = cancelar = true;
                                        LimpiarTabla();
                                        vaciarDatos(Integer.parseInt(txtFolio.getText().substring(1)));
                                        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
                                        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
                                        tcr.setHorizontalAlignment(SwingConstants.CENTER);
                                        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
                                        TableVenta.getColumnModel().getColumn(0).setCellRenderer(tcr);
                                        TableVenta.getColumnModel().getColumn(1).setCellRenderer(tcr);
                                        TableVenta.getColumnModel().getColumn(2).setCellRenderer(tcr);
                                        TableVenta.getColumnModel().getColumn(3).setCellRenderer(tcr);
                                        TableVenta.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
                                        TableVenta.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
                                        TableVenta.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
                                        TableVenta.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);
                                        TableVenta.getColumnModel().getColumn(8).setCellRenderer(tcrDerecha);
                                        ((DefaultTableCellRenderer) TableVenta.getTableHeader().getDefaultRenderer())
                                                .setHorizontalAlignment(SwingConstants.CENTER);
                                        folioMostrar = Integer.parseInt(txtFolio.getText().substring(1));
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Venta registrada exitosamente");
                                        panel.showNotification();
                                    }
                                } catch (ParseException ex) {
                                    Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }.start();
                    } else {
                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Contraseña incorrecta");
                        panel.showNotification();
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Rellene todos los campos");
                panel.showNotification();
            }
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Se encuentra visualizando una nota");
            panel.showNotification();
        }

    }

    public void bloquearDatos(boolean cambiar) {
        txtBuscarCliente.setEnabled(cambiar);
        txtCodigo.setEnabled(cambiar);
        txtComentarios.setEnabled(cambiar);
    }

    private void registrarDetalle() throws ParseException {
        int id = ventaDao.idVenta();
        List<Producto> lsProducto = new ArrayList<Producto>();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            String codigo = TableVenta.getValueAt(i, 0).toString();
            String descripcion = TableVenta.getValueAt(i, 3).toString();
            Number cambiando = formateador.parse(removefirstChar(TableVenta.getValueAt(i, 4).toString()));
            double precioUnitario = cambiando.doubleValue();
            double cantidad = Double.parseDouble(TableVenta.getValueAt(i, 1).toString());
            cambiando = formateador.parse(removefirstChar(TableVenta.getValueAt(i, 5).toString()));
            double precioFinal = cambiando.doubleValue();
            cambiando = formateador.parse(removefirstChar(TableVenta.getValueAt(i, 6).toString()));
            double descuentosD = cambiando.doubleValue();

            Producto prod = productoDao.BuscarPorCodigo(codigo);
            prod.setCodigo(codigo);
            prod.setExistencia(cantidad);
            lsProducto.add(prod);

            detalleVenta detalle = new detalleVenta();
            detalle.setCodigo(codigo);
            detalle.setDescripcion(descripcion);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setImporte(precioFinal);
            detalle.setFolioVenta(id);
            detalle.setCantidad(cantidad);
            detalle.setDescuento(descuentosD);
            ventaDao.registrarDetalle(detalle);
        }
        productoDao.ajustarInventarioVenta(lsProducto);
    }

    public void proceso() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eventoF6() {
        if (cancelar == true) {
            Ventas ven = ventaDao.buscarPorFolio(Integer.parseInt(txtFolio.getText().substring(1)));
            double cantidadSaldo = ven.getTotal() - notaDao.retornarSumaNotas(ven.getFolio()) - pagosDao.regresarPagos(ven.getFolio());

            if (cantidadSaldo != 0) {
                vistaCancelar cc = new vistaCancelar(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(idEmpleado);
                cc.vaciarDatos(ventaDao.buscarPorFolio(Integer.parseInt(txtFolio.getText().substring(1))), corteDao.getDia(), cantidadSaldo, true, 0);
                cc.setVisible(true);
                if (cc.accionRealizada == true) {
                    bloquearDatos(false);
                    limpiarDatosNota();
                    vaciarDatos(Integer.parseInt(txtFolio.getText().substring(1)));
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Venta cancelada exitosamente");
                    panel.showNotification();

                }
            } else {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "El saldo de la venta ya es 0");
                panel.showNotification();
            }
        }

    }

    public void eventoF5() {
        if (devolver == true) {
            vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
            vdr.vaciarEmpleado(idEmpleado);
            vdr.validandoDatos(3, Integer.parseInt(txtFolio.getText().substring(1)), false, 0);
            vdr.setVisible(true);
            if (vdr.accionRealizada == true) {
                bloquearDatos(false);
                limpiarDatosNota();
                vaciarDatos(Integer.parseInt(txtFolio.getText().substring(1)));
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Nota de crédito registrado exitosamente");
                panel.showNotification();

            }

        }
    }

    public void eventoF3() {
        if (ver == true) {
            try {
                int id = Integer.parseInt(txtFolio.getText().substring(WIDTH));
                File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\Tickets\\venta" + txtFolio.getText() + ".pdf");
                // File file = new File("C:\\Program Files (x86)\\AppLavanderia\\venta"+id+".pdf");

                ticket(id);
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {

            } catch (ParseException ex) {
                Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void eventoF4() {
        if (imprimir == true) {

            try {
                int id = Integer.parseInt(txtFolio.getText().substring(WIDTH));
                File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\Tickets\\venta" + txtFolio.getText() + ".pdf");
                ticket(id);
                imprimiendo m = new imprimiendo();
                if (file.isFile()) {
                    m.imprimir(file);
                } else {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Error inesperado");
                    panel.showNotification();
                }

            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Error de impresion", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());

            } catch (ParseException ex) {
                Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void eventoF7() {
        if (reponer == true) {
            vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
            vdr.vaciarEmpleado(idEmpleado);
            if (btnDescuentos.getToolTipText().equals("F7 - Bonificación")) {
                vdr.validandoDatos(2, Integer.parseInt(txtFolio.getText().substring(1)), false, 0);
            } else {
                vdr.validandoDatos(1, Integer.parseInt(txtFolio.getText().substring(1)), false, 0);
            }
            vdr.setVisible(true);
            if (vdr.accionRealizada == true) {
                bloquearDatos(false);
                limpiarDatosNota();
                vaciarDatos(Integer.parseInt(txtFolio.getText().substring(1)));
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Nota de crédito registrado exitosamente");
                panel.showNotification();

            }

        }
    }

    public void eventoF8() {
        if (cambiandoPrecio == true) {
            pagarAdeudo vdr = new pagarAdeudo(new javax.swing.JFrame(), true);
            vdr.vaciarEmpleado(idEmpleado);
            Ventas venM = ventaDao.buscarPorFolio(Integer.parseInt(txtFolio.getText().substring(1)));
            vdr.vaciarDatos(venM);
            vdr.setVisible(true);
            if (vdr.accionRealizada == true) {
                bloquearDatos(false);
                limpiarDatosNota();
                vaciarDatos(Integer.parseInt(txtFolio.getText().substring(1)));
            }
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
        jLabel2 = new javax.swing.JLabel();
        txtFolio = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        panelRegistrarNota1 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JLabel();
        panelGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        panelReporte = new javax.swing.JPanel();
        jButton18 = new javax.swing.JLabel();
        panelImprimir = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JLabel();
        panelDevolucion = new javax.swing.JPanel();
        btnDevolucion = new javax.swing.JLabel();
        panelCancelar = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        controladorLista = new javax.swing.JScrollPane();
        listClientes = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtRFC = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtRegimenFiscal = new javax.swing.JTextField();
        txtDomicilio = new javax.swing.JTextField();
        txtLimCredito = new javax.swing.JTextField();
        txtAdeudo = new javax.swing.JTextField();
        panelClientes = new javax.swing.JPanel();
        btnClientes = new javax.swing.JLabel();
        txtBuscarCliente = new javax.swing.JTextField();
        txtTipoSociedad = new javax.swing.JTextField();
        txtCodigoPostal = new javax.swing.JTextField();
        txtCFDI = new javax.swing.JTextField();
        txtTipoPersona = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        controladorListaProductos = new javax.swing.JScrollPane();
        listProductos = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        txtCodigo = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        APOYOPARAOBTNEREMPELADO = new javax.swing.JLabel();
        txtApoyoeliminar = new javax.swing.JTextField();
        txtMetodoPago = new javax.swing.JComboBox<>();
        txtFormaPago = new javax.swing.JComboBox<>();
        txtInteresCredito = new javax.swing.JTextField();
        txtTiempoCredito = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lbInteres = new javax.swing.JLabel();
        lbPlazo = new javax.swing.JLabel();
        lbPlazo2 = new javax.swing.JLabel();
        lbInteres8 = new javax.swing.JLabel();
        lbInteres9 = new javax.swing.JLabel();
        txtComentarios = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtSubtotalFinal = new javax.swing.JTextField();
        txtDescuentoFinal = new javax.swing.JTextField();
        txtIvaFInal = new javax.swing.JTextField();
        txtTotalFinal = new javax.swing.JTextField();
        txtSaldo = new javax.swing.JTextField();
        txtPagos = new javax.swing.JTextField();
        txtNotasCredito = new javax.swing.JTextField();
        lbInteres1 = new javax.swing.JLabel();
        lbInteres2 = new javax.swing.JLabel();
        lbInteres3 = new javax.swing.JLabel();
        lbInteres4 = new javax.swing.JLabel();
        lbInteres5 = new javax.swing.JLabel();
        lbInteres6 = new javax.swing.JLabel();
        lbInteres7 = new javax.swing.JLabel();
        espacioFecha = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cargando = new javax.swing.JLabel();
        panelPago = new javax.swing.JPanel();
        btnPago = new javax.swing.JLabel();
        panelReposicion = new javax.swing.JPanel();
        btnDescuentos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NOTA DE VENTA");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("FOLIO");

        txtFolio.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        txtFolio.setForeground(new java.awt.Color(255, 255, 255));
        txtFolio.setText("-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFolio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 870, 50));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("FECHA:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, -1, 20));

        jLabel63.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel63.setText("-");
        jPanel1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, 100, 20));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("-");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 80, 20));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("ESTATUS:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, -1, 30));

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
        jButton15.setToolTipText("F1 - Nueva venta");
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

        panelGuardar.setBackground(new java.awt.Color(255, 255, 255));
        panelGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelGuardarMouseEntered(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnGuardar.setToolTipText("F2 - Registrar Venta");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelGuardarLayout = new javax.swing.GroupLayout(panelGuardar);
        panelGuardar.setLayout(panelGuardarLayout);
        panelGuardarLayout.setHorizontalGroup(
            panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelGuardarLayout.setVerticalGroup(
            panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 50, 50));

        panelReporte.setBackground(new java.awt.Color(255, 255, 255));
        panelReporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelReporteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelReporteMouseEntered(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(255, 255, 255));
        jButton18.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen1.png"))); // NOI18N
        jButton18.setToolTipText("F3 - Visualizar PDF");
        jButton18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton18MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton18MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton18MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelReporteLayout = new javax.swing.GroupLayout(panelReporte);
        panelReporte.setLayout(panelReporteLayout);
        panelReporteLayout.setHorizontalGroup(
            panelReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelReporteLayout.setVerticalGroup(
            panelReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 50, 50));

        panelImprimir.setBackground(new java.awt.Color(255, 255, 255));
        panelImprimir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelImprimirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelImprimirMouseEntered(evt);
            }
        });

        btnImprimir.setBackground(new java.awt.Color(255, 255, 255));
        btnImprimir.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnImprimir.setForeground(new java.awt.Color(255, 255, 255));
        btnImprimir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen2.png"))); // NOI18N
        btnImprimir.setToolTipText("F4 - Imprimir ticket");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImprimirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnImprimirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnImprimirMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelImprimirLayout = new javax.swing.GroupLayout(panelImprimir);
        panelImprimir.setLayout(panelImprimirLayout);
        panelImprimirLayout.setHorizontalGroup(
            panelImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelImprimirLayout.setVerticalGroup(
            panelImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnImprimir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 50, 50));

        panelDevolucion.setBackground(new java.awt.Color(255, 255, 255));
        panelDevolucion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelDevolucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDevolucionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDevolucionMouseEntered(evt);
            }
        });

        btnDevolucion.setBackground(new java.awt.Color(255, 255, 255));
        btnDevolucion.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnDevolucion.setForeground(new java.awt.Color(255, 255, 255));
        btnDevolucion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDevolucion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen3.png"))); // NOI18N
        btnDevolucion.setToolTipText("F5 - Devolución de mercancia");
        btnDevolucion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDevolucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDevolucionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDevolucionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDevolucionMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelDevolucionLayout = new javax.swing.GroupLayout(panelDevolucion);
        panelDevolucion.setLayout(panelDevolucionLayout);
        panelDevolucionLayout.setHorizontalGroup(
            panelDevolucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDevolucion, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelDevolucionLayout.setVerticalGroup(
            panelDevolucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDevolucion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelDevolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 50, 50));

        panelCancelar.setBackground(new java.awt.Color(255, 255, 255));
        panelCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCancelarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelCancelarMouseEntered(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnCancelar.setToolTipText("F6 - Cancelar venta");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelCancelarLayout = new javax.swing.GroupLayout(panelCancelar);
        panelCancelar.setLayout(panelCancelarLayout);
        panelCancelarLayout.setHorizontalGroup(
            panelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelCancelarLayout.setVerticalGroup(
            panelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 50, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        controladorLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controladorListaMouseClicked(evt);
            }
        });

        listClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listClientesMouseClicked(evt);
            }
        });
        listClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listClientesKeyReleased(evt);
            }
        });
        controladorLista.setViewportView(listClientes);

        jPanel4.add(controladorLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 340, 10));

        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Información cliente");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 12, -1, -1));

        txtNombreCliente.setBackground(new java.awt.Color(204, 204, 204));
        txtNombreCliente.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtNombreCliente.setToolTipText("Nombre del cliente");
        txtNombreCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombreCliente.setEnabled(false);
        jPanel4.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 200, 20));

        txtRFC.setBackground(new java.awt.Color(204, 204, 204));
        txtRFC.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtRFC.setToolTipText("RFC");
        txtRFC.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRFC.setEnabled(false);
        jPanel4.add(txtRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 200, -1));

        txtTelefono.setBackground(new java.awt.Color(204, 204, 204));
        txtTelefono.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtTelefono.setToolTipText("Teléfono");
        txtTelefono.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTelefono.setEnabled(false);
        jPanel4.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 200, -1));

        txtCorreo.setBackground(new java.awt.Color(204, 204, 204));
        txtCorreo.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtCorreo.setToolTipText("Correo el{ectronico");
        txtCorreo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCorreo.setEnabled(false);
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel4.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 200, -1));

        txtRegimenFiscal.setBackground(new java.awt.Color(204, 204, 204));
        txtRegimenFiscal.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtRegimenFiscal.setToolTipText("Régimen fiscal");
        txtRegimenFiscal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRegimenFiscal.setEnabled(false);
        jPanel4.add(txtRegimenFiscal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, 200, -1));

        txtDomicilio.setBackground(new java.awt.Color(204, 204, 204));
        txtDomicilio.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtDomicilio.setToolTipText("Domicilio");
        txtDomicilio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDomicilio.setEnabled(false);
        jPanel4.add(txtDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 200, -1));

        txtLimCredito.setBackground(new java.awt.Color(204, 204, 204));
        txtLimCredito.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtLimCredito.setToolTipText("Límite de crédito");
        txtLimCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtLimCredito.setEnabled(false);
        txtLimCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLimCreditoActionPerformed(evt);
            }
        });
        jPanel4.add(txtLimCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 200, -1));

        txtAdeudo.setBackground(new java.awt.Color(204, 204, 204));
        txtAdeudo.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtAdeudo.setToolTipText("Adeudo actual");
        txtAdeudo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAdeudo.setEnabled(false);
        jPanel4.add(txtAdeudo, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 200, -1));

        panelClientes.setBackground(new java.awt.Color(255, 255, 255));
        panelClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelClientesMouseEntered(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(153, 204, 255));
        btnClientes.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/empleado.png"))); // NOI18N
        btnClientes.setToolTipText("Visualizar clientes");
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClientesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.add(panelClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 5, 50, 50));

        txtBuscarCliente.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 13)); // NOI18N
        txtBuscarCliente.setToolTipText("Buscar cliente");
        txtBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyReleased(evt);
            }
        });
        jPanel4.add(txtBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 340, -1));

        txtTipoSociedad.setBackground(new java.awt.Color(204, 204, 204));
        txtTipoSociedad.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtTipoSociedad.setToolTipText("Tipo de sociedad (Persona moral)");
        txtTipoSociedad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTipoSociedad.setEnabled(false);
        jPanel4.add(txtTipoSociedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 200, -1));

        txtCodigoPostal.setBackground(new java.awt.Color(204, 204, 204));
        txtCodigoPostal.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtCodigoPostal.setToolTipText("Código postal");
        txtCodigoPostal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigoPostal.setEnabled(false);
        jPanel4.add(txtCodigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, 200, -1));

        txtCFDI.setBackground(new java.awt.Color(204, 204, 204));
        txtCFDI.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtCFDI.setToolTipText("Uso de CFDI");
        txtCFDI.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCFDI.setEnabled(false);
        txtCFDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCFDIActionPerformed(evt);
            }
        });
        jPanel4.add(txtCFDI, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, 200, -1));

        txtTipoPersona.setBackground(new java.awt.Color(204, 204, 204));
        txtTipoPersona.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        txtTipoPersona.setToolTipText("Tipo de persona");
        txtTipoPersona.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTipoPersona.setEnabled(false);
        jPanel4.add(txtTipoPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 200, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Sociedad");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 122, 52, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Buscar cliente");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Nombre");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 62, 52, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Correo");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 82, 52, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Teléfono");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 102, 52, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Persona");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 62, 52, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("RFC");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 82, 52, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Régimen");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 102, 52, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("CFDI");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 122, 52, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Domicilio");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 62, 52, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("C.P.");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 82, 52, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Crédito");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 102, 52, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Adeudo");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 122, 52, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 850, 150));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProductosMouseClicked(evt);
            }
        });
        controladorListaProductos.setViewportView(listProductos);

        jPanel5.add(controladorListaProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 260, 10));

        TableVenta = new Table3();
        TableVenta.setBackground(new java.awt.Color(204, 204, 255));
        TableVenta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cant", "Unidad", "Descripción", "P/U", "Importe", "Descuento", "IVA", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVenta.setToolTipText("Haga doble click para modificar");
        TableVenta.setFocusable(false);
        TableVenta.setGridColor(new java.awt.Color(255, 255, 255));
        TableVenta.setOpaque(false);
        TableVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentaMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TableVentaMouseExited(evt);
            }
        });
        TableVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableVentaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(90);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(7);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(15);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(75);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(20);
            TableVenta.getColumnModel().getColumn(5).setPreferredWidth(20);
            TableVenta.getColumnModel().getColumn(6).setPreferredWidth(20);
            TableVenta.getColumnModel().getColumn(7).setPreferredWidth(15);
            TableVenta.getColumnModel().getColumn(8).setPreferredWidth(20);
        }

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 830, 200));

        txtCodigo.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        txtCodigo.setToolTipText("Buscar producto por código o descripción");
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });
        jPanel5.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 260, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Producto");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, 52, 20));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 850, 260));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        APOYOPARAOBTNEREMPELADO.setText("-");
        jPanel7.add(APOYOPARAOBTNEREMPELADO, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        txtApoyoeliminar.setEditable(false);
        txtApoyoeliminar.setEnabled(false);
        jPanel7.add(txtApoyoeliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 20, 10));

        txtMetodoPago.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Método de pago *", "PUE Pago en una sola exhibición", "PPD Pago en parcialidades o diferido" }));
        txtMetodoPago.setToolTipText("Seleccione el método de pago");
        txtMetodoPago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtMetodoPagoItemStateChanged(evt);
            }
        });
        txtMetodoPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMetodoPagoKeyReleased(evt);
            }
        });
        jPanel7.add(txtMetodoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 25, 200, -1));

        txtFormaPago.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFormaPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Forma de pago" }));
        txtFormaPago.setToolTipText("Seleccione el forma de pago");
        txtFormaPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFormaPagoKeyReleased(evt);
            }
        });
        jPanel7.add(txtFormaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 52, 200, -1));

        txtInteresCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteresCredito.setToolTipText("Intéres ordinario");
        txtInteresCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtInteresCredito.setEnabled(false);
        txtInteresCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteresCreditoKeyReleased(evt);
            }
        });
        jPanel7.add(txtInteresCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 52, 120, -1));

        txtTiempoCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTiempoCredito.setToolTipText("Plazo para pagar a crédito");
        txtTiempoCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTiempoCredito.setEnabled(false);
        txtTiempoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTiempoCreditoKeyReleased(evt);
            }
        });
        jPanel7.add(txtTiempoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 25, 120, -1));

        jLabel29.setBackground(new java.awt.Color(0, 0, 204));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Pago");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 5, 140, -1));

        jLabel30.setBackground(new java.awt.Color(0, 0, 204));
        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Datos a Crédito");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 5, 140, -1));

        lbInteres.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres.setText("Interés");
        jPanel7.add(lbInteres, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 52, 52, 20));

        lbPlazo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbPlazo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbPlazo.setText("Plazo");
        jPanel7.add(lbPlazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 25, 52, 20));

        lbPlazo2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbPlazo2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbPlazo2.setText("Método");
        jPanel7.add(lbPlazo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 25, 50, 20));

        lbInteres8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres8.setText("Forma");
        jPanel7.add(lbInteres8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 52, 50, 20));

        lbInteres9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres9.setText("Comentarios");
        jPanel7.add(lbInteres9, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 80, -1, 20));

        txtComentarios.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtComentarios.setToolTipText("Intéres ordinario");
        txtComentarios.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtComentarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtComentariosKeyReleased(evt);
            }
        });
        jPanel7.add(txtComentarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 80, 347, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 525, 450, 115));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSubtotalFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSubtotalFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotalFinal.setToolTipText("Subtotal");
        txtSubtotalFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubtotalFinal.setEnabled(false);
        txtSubtotalFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSubtotalFinalKeyReleased(evt);
            }
        });
        jPanel2.add(txtSubtotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 5, 120, -1));

        txtDescuentoFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDescuentoFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuentoFinal.setToolTipText("Descuentos acumulados");
        txtDescuentoFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescuentoFinal.setEnabled(false);
        txtDescuentoFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoFinalKeyReleased(evt);
            }
        });
        jPanel2.add(txtDescuentoFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 32, 120, -1));

        txtIvaFInal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtIvaFInal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIvaFInal.setToolTipText("IVA");
        txtIvaFInal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIvaFInal.setEnabled(false);
        txtIvaFInal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIvaFInalKeyReleased(evt);
            }
        });
        jPanel2.add(txtIvaFInal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 59, 120, -1));

        txtTotalFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTotalFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalFinal.setToolTipText("Total de la venta");
        txtTotalFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalFinal.setEnabled(false);
        txtTotalFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalFinalKeyReleased(evt);
            }
        });
        jPanel2.add(txtTotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 86, 120, -1));

        txtSaldo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtSaldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSaldo.setToolTipText("Saldo de la venta");
        txtSaldo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldo.setEnabled(false);
        txtSaldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSaldoKeyReleased(evt);
            }
        });
        jPanel2.add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 59, 120, 22));

        txtPagos.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtPagos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPagos.setToolTipText("Pagos realizados (crédito)");
        txtPagos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPagos.setEnabled(false);
        txtPagos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPagosKeyReleased(evt);
            }
        });
        jPanel2.add(txtPagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 32, 120, -1));

        txtNotasCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNotasCredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNotasCredito.setToolTipText("Notas de crédito generadas");
        txtNotasCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNotasCredito.setEnabled(false);
        txtNotasCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNotasCreditoKeyReleased(evt);
            }
        });
        jPanel2.add(txtNotasCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 5, 120, -1));

        lbInteres1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres1.setText("Saldo");
        jPanel2.add(lbInteres1, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 60, 50, 20));

        lbInteres2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres2.setText("N.Crédito");
        jPanel2.add(lbInteres2, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 5, 52, 20));

        lbInteres3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres3.setText("Total");
        jPanel2.add(lbInteres3, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 87, 60, 20));

        lbInteres4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres4.setText("Pagos");
        jPanel2.add(lbInteres4, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 35, 50, 20));

        lbInteres5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres5.setText("Subtotal");
        jPanel2.add(lbInteres5, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 7, 60, 20));

        lbInteres6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres6.setText("Descuento");
        jPanel2.add(lbInteres6, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 33, 60, 20));

        lbInteres7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres7.setText("IVA");
        jPanel2.add(lbInteres7, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 60, 60, 20));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 525, 390, 115));

        espacioFecha.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        espacioFecha.setForeground(new java.awt.Color(0, 0, 153));
        jPanel1.add(espacioFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 230, 30));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setText(" HORA:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, -1, 20));

        cargando.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(cargando, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 70, 40));

        panelPago.setBackground(new java.awt.Color(255, 255, 255));
        panelPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelPagoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelPagoMouseEntered(evt);
            }
        });

        btnPago.setBackground(new java.awt.Color(255, 255, 255));
        btnPago.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnPago.setForeground(new java.awt.Color(255, 255, 255));
        btnPago.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen4.png"))); // NOI18N
        btnPago.setToolTipText("F8 - Pagar adeudo");
        btnPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPagoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPagoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPagoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelPagoLayout = new javax.swing.GroupLayout(panelPago);
        panelPago.setLayout(panelPagoLayout);
        panelPagoLayout.setHorizontalGroup(
            panelPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPago, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelPagoLayout.setVerticalGroup(
            panelPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 50, 50));

        panelReposicion.setBackground(new java.awt.Color(255, 255, 255));
        panelReposicion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelReposicion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelReposicionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelReposicionMouseEntered(evt);
            }
        });

        btnDescuentos.setBackground(new java.awt.Color(255, 255, 255));
        btnDescuentos.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnDescuentos.setForeground(new java.awt.Color(255, 255, 255));
        btnDescuentos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDescuentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/descuento.png"))); // NOI18N
        btnDescuentos.setToolTipText("F7 - Reposición de mercancia");
        btnDescuentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDescuentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDescuentosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDescuentosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDescuentosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelReposicionLayout = new javax.swing.GroupLayout(panelReposicion);
        panelReposicion.setLayout(panelReposicionLayout);
        panelReposicionLayout.setHorizontalGroup(
            panelReposicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDescuentos, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelReposicionLayout.setVerticalGroup(
            panelReposicionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDescuentos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelReposicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 50, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseClicked
        eventoF1();
    }//GEN-LAST:event_jButton15MouseClicked

    private void jButton15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseEntered
        panelRegistrarNota1.setBackground(new Color(179, 195, 219));
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

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        if (guardar == true) {
            eventoF2();
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered

        panelGuardar.setBackground(new Color(179, 195, 219));
        if (guardar == true) {
            panelGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelGuardar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        if (guardar == true) {
            panelGuardar.setBackground(new Color(255, 255, 255));
            panelGuardar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelGuardar.setBackground(new Color(179, 195, 219));
            panelGuardar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnGuardarMouseExited

    private void panelGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuardarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGuardarMouseClicked

    private void panelGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuardarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGuardarMouseEntered

    private void jButton18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MouseClicked
        eventoF3();
    }//GEN-LAST:event_jButton18MouseClicked

    private void jButton18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MouseEntered
        panelReporte.setBackground(new Color(179, 195, 219));
        if (ver == true) {
            panelReporte.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jButton18MouseEntered

    private void jButton18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MouseExited
        if (ver == true) {
            panelReporte.setBackground(new Color(255, 255, 255));
            panelReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelReporte.setBackground(new Color(179, 195, 219));
            panelReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jButton18MouseExited

    private void panelReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReporteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelReporteMouseClicked

    private void panelReporteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReporteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelReporteMouseEntered

    private void btnImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseClicked
        eventoF4();
    }//GEN-LAST:event_btnImprimirMouseClicked

    private void btnImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseEntered
        panelImprimir.setBackground(new Color(179, 195, 219));
        if (imprimir == true) {
            panelImprimir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelImprimir.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnImprimirMouseEntered

    private void btnImprimirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseExited
        if (imprimir == true) {
            panelImprimir.setBackground(new Color(255, 255, 255));
            panelImprimir.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelImprimir.setBackground(new Color(179, 195, 219));
            panelImprimir.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnImprimirMouseExited

    private void panelImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelImprimirMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelImprimirMouseClicked

    private void panelImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelImprimirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelImprimirMouseEntered

    private void btnDevolucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDevolucionMouseClicked
        eventoF5();
    }//GEN-LAST:event_btnDevolucionMouseClicked

    private void btnDevolucionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDevolucionMouseEntered
        panelDevolucion.setBackground(new Color(179, 195, 219));
        if (devolver == true) {
            panelDevolucion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelDevolucion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnDevolucionMouseEntered

    private void btnDevolucionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDevolucionMouseExited
        if (devolver == true) {
            panelDevolucion.setBackground(new Color(255, 255, 255));
            panelDevolucion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelDevolucion.setBackground(new Color(179, 195, 219));
            panelDevolucion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnDevolucionMouseExited

    private void panelDevolucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDevolucionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelDevolucionMouseClicked

    private void panelDevolucionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDevolucionMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelDevolucionMouseEntered

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        eventoF6();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        panelCancelar.setBackground(new Color(179, 195, 219));
        if (cancelar == true) {
            panelCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelCancelar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        if (cancelar == true) {
            panelCancelar.setBackground(new Color(255, 255, 255));
            panelCancelar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelCancelar.setBackground(new Color(179, 195, 219));
            panelCancelar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnCancelarMouseExited

    private void panelCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCancelarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelCancelarMouseClicked

    private void panelCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCancelarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelCancelarMouseEntered

    private void listClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listClientesMouseClicked
        if (evt.getClickCount() == 2) {

            if (isNumeric(txtBuscarCliente.getText()) == true) {
                Clientes alistar = client.BuscarPorCodigo(Integer.parseInt(txtBuscarCliente.getText()));
                clienteElegido = alistar.getId();
                if (alistar.getTipoPersona().equals("Persona Física")) {
                    txtNombreCliente.setText(alistar.getNombre() + " " + alistar.getApellidoP() + " " + alistar.getApellidoM());
                } else {
                    txtNombreCliente.setText(alistar.getNombreComercial());
                    txtTipoSociedad.setText(alistar.getTipoPersona());
                }
                txtTelefono.setText(alistar.getTelefono());
                txtCorreo.setText(alistar.getCorreo());

                txtTipoPersona.setText(alistar.getTipoPersona());
                txtRegimenFiscal.setText(alistar.getRegimenFiscal());
                txtRFC.setText(alistar.getRfc());
                txtCFDI.setText(alistar.getCfdi());

                txtDomicilio.setText(alistar.getCalle() + " " + alistar.getNumeroInterior() + " " + alistar.getNumeroExterior() + " " + alistar.getColonia() + " " + alistar.getMunicipio() + " " + alistar.getEstado());
                txtCodigoPostal.setText(alistar.getCodigoPostal());
                Credito credito = creditoDao.BuscarPorCodigoCliente(alistar.getId());
                if (credito.getId() != 0) {
                    txtAdeudo.setText("$" + formateador.format(credito.getAdeudo()));
                    txtLimCredito.setText("$" + formateador.format(credito.getLimite()));
                    txtTiempoCredito.setText(credito.getPlazo() + " días");
                    txtInteresCredito.setText(formato.format(credito.getInteresOrdinario()) + "%");
                }

            } else {
                int k = listClientes.getSelectedIndex();
                List<Clientes> lista = client.buscarLetra(txtBuscarCliente.getText());
                clienteElegido = lista.get(k).getId();
                if (lista.get(k).getTipoPersona().equals("Persona Física")) {
                    txtNombreCliente.setText(lista.get(k).getNombre() + " " + lista.get(k).getApellidoP() + " " + lista.get(k).getApellidoM());
                } else {
                    txtNombreCliente.setText(lista.get(k).getNombreComercial());
                    txtTipoSociedad.setText(lista.get(k).getTipoPersona());
                }
                txtTelefono.setText(lista.get(k).getTelefono());
                txtCorreo.setText(lista.get(k).getCorreo());

                txtTipoPersona.setText(lista.get(k).getTipoPersona());
                txtRegimenFiscal.setText(lista.get(k).getRegimenFiscal());
                txtRFC.setText(lista.get(k).getRfc());
                txtCFDI.setText(lista.get(k).getCfdi());

                txtDomicilio.setText(lista.get(k).getCalle() + " " + lista.get(k).getNumeroInterior() + " " + lista.get(k).getNumeroExterior() + " " + lista.get(k).getColonia() + " " + lista.get(k).getMunicipio() + " " + lista.get(k).getEstado());
                txtCodigoPostal.setText(lista.get(k).getCodigoPostal());
                Credito credito = creditoDao.BuscarPorCodigoCliente(lista.get(k).getId());
                if (credito.getId() != 0) {
                    txtAdeudo.setText("$" + formateador.format(credito.getAdeudo()));
                    txtLimCredito.setText("$" + formateador.format(credito.getLimite()));
                    txtTiempoCredito.setText(credito.getPlazo() + " días");
                    txtInteresCredito.setText(formato.format(credito.getInteresOrdinario()) + "%");
                }

            }
            txtBuscarCliente.setText("");
            listClientes.setVisible(false);
            controladorLista.setVisible(false);
        }
    }//GEN-LAST:event_listClientesMouseClicked

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void txtLimCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLimCreditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLimCreditoActionPerformed

    private void btnClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientesMouseClicked
        vistaClientes cc1 = new vistaClientes(new javax.swing.JFrame(), true);
        cc1.setLocation(200, 80);
        Empleados e = empleadosDao.BuscarPorCodigo(idEmpleado);
        cc1.vaciarEmpleado(e);
        cc1.setVisible(true);
        if (clienteElegido != 0) {
            Clientes alistar = client.BuscarPorCodigo(clienteElegido);

            if (alistar.getTipoPersona().equals("Persona Física")) {
                txtNombreCliente.setText(alistar.getNombre() + " " + alistar.getApellidoP() + " " + alistar.getApellidoM());
            } else {
                txtNombreCliente.setText(alistar.getNombreComercial());
                txtTipoSociedad.setText(alistar.getTipoPersona());
            }
            txtTelefono.setText(alistar.getTelefono());
            txtCorreo.setText(alistar.getCorreo());

            txtTipoPersona.setText(alistar.getTipoPersona());
            txtRegimenFiscal.setText(alistar.getRegimenFiscal());
            txtRFC.setText(alistar.getRfc());
            txtCFDI.setText(alistar.getCfdi());

            txtDomicilio.setText(alistar.getCalle() + " " + alistar.getNumeroInterior() + " " + alistar.getNumeroExterior() + " " + alistar.getColonia() + " " + alistar.getMunicipio() + " " + alistar.getEstado());
            txtCodigoPostal.setText(alistar.getCodigoPostal());
            Credito credito = creditoDao.BuscarPorCodigoCliente(alistar.getId());
            if (credito.getId() != 0) {
                txtAdeudo.setText("$" + formateador.format(credito.getAdeudo()));
                txtLimCredito.setText("$" + formateador.format(credito.getLimite()));
            }
        }
    }//GEN-LAST:event_btnClientesMouseClicked

    private void btnClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientesMouseEntered
        panelClientes.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnClientesMouseEntered

    private void btnClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientesMouseExited
        panelClientes.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnClientesMouseExited

    private void panelClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelClientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelClientesMouseClicked

    private void panelClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelClientesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelClientesMouseEntered

    private void listProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProductosMouseClicked
        Producto p = productoDao.existeProductoDescripcion(listProductos.getSelectedValue());
        if (p.getId() != 0) {
            double aumentar = 0; //lo de la tabla
            double cantidadAumentar = 1; // lo del jtextEfild
            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                if (TableVenta.getValueAt(i, 0).toString().equals(p.getCodigo())) {
                    aumentar = Double.parseDouble(TableVenta.getValueAt(i, 1).toString());
                    if (p.getInventario() == 1 && (aumentar + cantidadAumentar) <= p.getExistencia()) {
                        tmp = (DefaultTableModel) TableVenta.getModel();
                        tmp.removeRow(i);
                    }
                    if (p.getInventario() == 0) {
                        tmp = (DefaultTableModel) TableVenta.getModel();
                        tmp.removeRow(i);
                    }
                }
            }
            double descuento = 0;

            Object[] o = new Object[9];
            o[0] = p.getCodigo();
            if (!p.getTipoVenta().equals("Kilogramo")) {
                aumentar = redondear(aumentar, 0); // Redondea a 0 decimales
                cantidadAumentar = redondear(cantidadAumentar, 0); // Redondea a 0 decimales
            }
            double cantidadAgregar = aumentar + cantidadAumentar;
            o[1] = formato.format(cantidadAgregar);
            o[2] = p.getTipoVenta();
            o[3] = p.getDescripcion();
            o[4] = "$" + formateador.format(p.getPrecioVenta());
            double importe = cantidadAgregar * p.getPrecioVenta();
            o[5] = "$" + formateador.format(importe);
            importe = importe - descuento;
            double iva = importe * .16;
            o[6] = "$" + formateador.format(descuento);
            o[7] = "$" + formateador.format(iva);
            o[8] = "$" + formateador.format((iva) + importe);

            if (p.getInventario() == 1) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "La cantidad en existencia es: " + p.getExistencia());
                panel.showNotification();
            }

            if (p.getInventario() == 1 && (aumentar + cantidadAumentar) >= (1 + p.getExistencia())) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "La cantidad supera la existencia");
                panel.showNotification();
                txtCodigo.setText("");
                controladorListaProductos.setVisible(false);
            } else {
                if (descuento > ((aumentar + cantidadAumentar) * p.getPrecioVenta())) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "El descuento es mayor al importe");
                    panel.showNotification();
                    txtCodigo.setText("");
                    controladorListaProductos.setVisible(false);
                } else {

                    tmp.addRow(o);
                    TableVenta.setModel(tmp);
                    calculandoImporte();
                    controladorListaProductos.setVisible(false);
                    txtCodigo.setText("");

                }
            }
            txtCodigo.requestFocus();
        }
    }//GEN-LAST:event_listProductosMouseClicked

    private void TableVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentaMouseClicked
        if (evt.getClickCount() == 2) {
            if (indicador == 1) { //Nueva venta
                try {
                    int fila = TableVenta.rowAtPoint(evt.getPoint());
                    txtApoyoeliminar.setText(TableVenta.getValueAt(fila, 0).toString());
                    double cantidad = Double.parseDouble(TableVenta.getValueAt(fila, 1).toString());
                    Number elDescuento = formateador.parse(removefirstChar(TableVenta.getModel().getValueAt(TableVenta.getSelectedRow(), 6).toString()));
                    ventaTabla vdr = new ventaTabla(new javax.swing.JFrame(), true);
                    vdr.vaciarProducto(productoDao.BuscarPorCodigo(TableVenta.getValueAt(fila, 0).toString()), cantidad, elDescuento.doubleValue(), 1);
                    vdr.setVisible(true);
                    if (vdr.accionRealizada == true) {
                        List<Double> datos = vdr.datos;
                        TableVenta.setValueAt(formato.format(datos.get(0)), fila, 1);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(1)), fila, 5);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(2)), fila, 6);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(3)), fila, 7);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(4)), fila, 8);
                        calculandoImporte();
                    } else {
                        if (vdr.borrar == true) {
                            modelo = (DefaultTableModel) TableVenta.getModel();
                            modelo.removeRow(TableVenta.getSelectedRow());
                            calculandoImporte();
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_TableVentaMouseClicked

    private void TableVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TableVentaMouseExited

    private void TableVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentaKeyPressed

    }//GEN-LAST:event_TableVentaKeyPressed

    private void btnDescuentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescuentosMouseClicked
        eventoF7();
    }//GEN-LAST:event_btnDescuentosMouseClicked

    private void btnDescuentosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescuentosMouseEntered
        panelReposicion.setBackground(new Color(179, 195, 219));
        if (reponer == true) {
            panelReposicion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelReposicion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnDescuentosMouseEntered

    private void btnDescuentosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescuentosMouseExited
        if (reponer == true) {
            panelReposicion.setBackground(new Color(255, 255, 255));
            panelReposicion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelReposicion.setBackground(new Color(179, 195, 219));
            panelReposicion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnDescuentosMouseExited

    private void panelReposicionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReposicionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelReposicionMouseClicked

    private void panelReposicionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReposicionMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelReposicionMouseEntered

    private void controladorListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_controladorListaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_controladorListaMouseClicked

    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        if (txtBuscarCliente.getText().equals("")) {
            controladorLista.setVisible(false);
            listClientes.setVisible(false);
        } else {
            DefaultListModel model = new DefaultListModel();
            controladorLista.setVisible(true);
            listClientes.setVisible(true);
            controladorLista.setBounds(controladorLista.getX(), controladorLista.getY(), controladorLista.getWidth(), 85);
            listClientes.setBounds(listClientes.getX(), listClientes.getY(), listClientes.getWidth(), 85);
            if (isNumeric(txtBuscarCliente.getText()) == true) {
                Clientes alistar = client.BuscarPorCodigo(Integer.parseInt(txtBuscarCliente.getText()));
                if (alistar.getEstatus() == 1 || alistar.getId() == 0) {
                    controladorLista.setVisible(false);
                    listClientes.setVisible(false);
                } else {
                    if (alistar.getTipoPersona().equals("Persona Física")) {
                        model.addElement(alistar.getNombre() + " " + alistar.getApellidoP() + " " + alistar.getApellidoM());
                    } else {
                        model.addElement(alistar.getNombreComercial());
                    }
                    listClientes.setModel(model);
                }
            } else {
                List<Clientes> lista = client.buscarLetra(txtBuscarCliente.getText());
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getEstatus() == 1) {
                        if (lista.get(i).getTipoPersona().equals("Persona Física")) {
                            model.addElement(lista.get(i).getNombre() + " " + lista.get(i).getApellidoP() + " " + lista.get(i).getApellidoM());
                        } else {
                            model.addElement(lista.get(i).getNombreComercial());
                        }
                    }
                }
                listClientes.setModel(model);
            }
        }

        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F4:
                eventoF4();
                break;

            case KeyEvent.VK_F5:
                eventoF5();
                break;

            case KeyEvent.VK_F6:
                eventoF6();
                break;

            case KeyEvent.VK_F7:
                eventoF7();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtBuscarClienteKeyReleased

    private void txtCFDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCFDIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCFDIActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        rellenarListaProductos();

        Producto p = productoDao.existeProducto(txtCodigo.getText());
        if (p.getId() != 0) {
            double aumentar = 0; //lo de la tabla
            double cantidadAumentar = 1; // lo del jtextEfild
            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                if (TableVenta.getValueAt(i, 0).toString().equals(p.getCodigo())) {
                    aumentar = Double.parseDouble(TableVenta.getValueAt(i, 1).toString());
                    if (p.getInventario() == 1 && (aumentar + cantidadAumentar) <= p.getExistencia()) {
                        tmp = (DefaultTableModel) TableVenta.getModel();
                        tmp.removeRow(i);
                    }
                    if (p.getInventario() == 0) {
                        tmp = (DefaultTableModel) TableVenta.getModel();
                        tmp.removeRow(i);
                    }
                }
            }
            double descuento = 0;

            Object[] o = new Object[9];
            o[0] = p.getCodigo();
            if (!p.getTipoVenta().equals("Kilogramo")) {
                aumentar = redondear(aumentar, 0); // Redondea a 0 decimales
                cantidadAumentar = redondear(cantidadAumentar, 0); // Redondea a 0 decimales
            }
            double cantidadAgregar = aumentar + cantidadAumentar;
            o[1] = formato.format(cantidadAgregar);
            o[2] = p.getTipoVenta();
            o[3] = p.getDescripcion();
            o[4] = "$" + formateador.format(p.getPrecioVenta());
            double importe = cantidadAgregar * p.getPrecioVenta();
            o[5] = "$" + formateador.format(importe);
            importe = importe - descuento;
            double iva = importe * .16;
            o[6] = "$" + formateador.format(descuento);
            o[7] = "$" + formateador.format(iva);
            o[8] = "$" + formateador.format((iva) + importe);

            if (p.getInventario() == 1) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "La cantidad en existencia es: " + p.getExistencia());
                panel.showNotification();
            }

            if (p.getInventario() == 1 && (aumentar + cantidadAumentar) >= (1 + p.getExistencia())) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "La cantidad supera la existencia");
                panel.showNotification();
                txtCodigo.setText("");
                controladorListaProductos.setVisible(false);
            } else {
                if (descuento > ((aumentar + cantidadAumentar) * p.getPrecioVenta())) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "El descuento es mayor al importe");
                    panel.showNotification();
                    txtCodigo.setText("");
                    controladorListaProductos.setVisible(false);
                } else {

                    tmp.addRow(o);
                    TableVenta.setModel(tmp);
                    calculandoImporte();
                    controladorListaProductos.setVisible(false);
                    txtCodigo.setText("");

                }
            }

        }

        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F4:
                eventoF4();
                break;

            case KeyEvent.VK_F5:
                eventoF5();
                break;

            case KeyEvent.VK_F6:
                eventoF6();
                break;

            case KeyEvent.VK_F7:
                eventoF7();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
        txtCodigo.requestFocus();

    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtMetodoPagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtMetodoPagoItemStateChanged
        if (txtMetodoPago.getSelectedIndex() == 0) {
            txtFormaPago.setEnabled(false);
            txtFormaPago.removeAllItems();
            txtFormaPago.addItem("Forma de pago *");
            txtTiempoCredito.setVisible(false);
            txtInteresCredito.setVisible(false);
            lbPlazo.setVisible(false);
            lbInteres.setVisible(false);

        }
        if (txtMetodoPago.getSelectedIndex() == 1) {
            txtFormaPago.setEnabled(true);
            txtFormaPago.removeAllItems();
            txtFormaPago.addItem("01 Efectivo");
            txtFormaPago.addItem("02 Cheque nominativo");
            txtFormaPago.addItem("03 Transferencia electrónica de fondos");
            txtFormaPago.addItem("04 Tarjeta de crédito");
            txtFormaPago.addItem("28 Tarjeta de débito");
            txtFormaPago.addItem("Diferentes formas de pago");
            txtTiempoCredito.setVisible(false);
            txtInteresCredito.setVisible(false);
            lbPlazo.setVisible(false);
            lbInteres.setVisible(false);
            jLabel30.setVisible(false);

        }
        if (txtMetodoPago.getSelectedIndex() == 2) {
            txtFormaPago.setEnabled(true);
            txtFormaPago.removeAllItems();
            txtFormaPago.addItem("99 Por definir"); //a credito
            txtTiempoCredito.setVisible(true);
            txtInteresCredito.setVisible(true);
            lbPlazo.setVisible(true);
            lbInteres.setVisible(true);
            jLabel30.setVisible(true);

        }
    }//GEN-LAST:event_txtMetodoPagoItemStateChanged

    private void txtSubtotalFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubtotalFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubtotalFinalKeyReleased

    private void txtDescuentoFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoFinalKeyReleased

    private void txtIvaFInalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIvaFInalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIvaFInalKeyReleased

    private void txtTotalFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalFinalKeyReleased

    private void txtSaldoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoKeyReleased

    private void txtPagosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPagosKeyReleased

    private void txtNotasCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotasCreditoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNotasCreditoKeyReleased

    private void txtInteresCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteresCreditoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInteresCreditoKeyReleased

    private void txtTiempoCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiempoCreditoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTiempoCreditoKeyReleased

    private void btnPagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagoMouseClicked
        eventoF8();
    }//GEN-LAST:event_btnPagoMouseClicked

    private void btnPagoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagoMouseEntered
        panelPago.setBackground(new Color(179, 195, 219));
        if (cambiandoPrecio == true) {
            panelPago.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelPago.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnPagoMouseEntered

    private void btnPagoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagoMouseExited
        if (cambiandoPrecio == true) {
            panelPago.setBackground(new Color(255, 255, 255));
            panelPago.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelPago.setBackground(new Color(179, 195, 219));
            panelPago.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnPagoMouseExited

    private void panelPagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPagoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelPagoMouseClicked

    private void panelPagoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPagoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelPagoMouseEntered

    private void TableVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentaKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F4:
                eventoF4();
                break;

            case KeyEvent.VK_F5:
                eventoF5();
                break;

            case KeyEvent.VK_F6:
                eventoF6();
                break;

            case KeyEvent.VK_F7:
                eventoF7();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_TableVentaKeyReleased

    private void listClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listClientesKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F4:
                eventoF4();
                break;

            case KeyEvent.VK_F5:
                eventoF5();
                break;

            case KeyEvent.VK_F6:
                eventoF6();
                break;

            case KeyEvent.VK_F7:
                eventoF7();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_listClientesKeyReleased

    private void txtMetodoPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMetodoPagoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F4:
                eventoF4();
                break;

            case KeyEvent.VK_F5:
                eventoF5();
                break;

            case KeyEvent.VK_F6:
                eventoF6();
                break;

            case KeyEvent.VK_F7:
                eventoF7();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtMetodoPagoKeyReleased

    private void txtFormaPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormaPagoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F4:
                eventoF4();
                break;

            case KeyEvent.VK_F5:
                eventoF5();
                break;

            case KeyEvent.VK_F6:
                eventoF6();
                break;

            case KeyEvent.VK_F7:
                eventoF7();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtFormaPagoKeyReleased

    private void txtComentariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComentariosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComentariosKeyReleased

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
            java.util.logging.Logger.getLogger(vistaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaVenta dialog = new vistaVenta(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel APOYOPARAOBTNEREMPELADO;
    private javax.swing.JTable TableVenta;
    private javax.swing.JLabel btnCancelar;
    private javax.swing.JLabel btnClientes;
    private javax.swing.JLabel btnDescuentos;
    private javax.swing.JLabel btnDevolucion;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnImprimir;
    private javax.swing.JLabel btnPago;
    private javax.swing.JLabel cargando;
    private javax.swing.JScrollPane controladorLista;
    private javax.swing.JScrollPane controladorListaProductos;
    private javax.swing.JLabel espacioFecha;
    private javax.swing.JLabel jButton15;
    private javax.swing.JLabel jButton18;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbInteres;
    private javax.swing.JLabel lbInteres1;
    private javax.swing.JLabel lbInteres2;
    private javax.swing.JLabel lbInteres3;
    private javax.swing.JLabel lbInteres4;
    private javax.swing.JLabel lbInteres5;
    private javax.swing.JLabel lbInteres6;
    private javax.swing.JLabel lbInteres7;
    private javax.swing.JLabel lbInteres8;
    private javax.swing.JLabel lbInteres9;
    private javax.swing.JLabel lbPlazo;
    private javax.swing.JLabel lbPlazo2;
    private javax.swing.JList<String> listClientes;
    private javax.swing.JList<String> listProductos;
    private javax.swing.JPanel panelCancelar;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelDevolucion;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JPanel panelImprimir;
    private javax.swing.JPanel panelPago;
    private javax.swing.JPanel panelRegistrarNota1;
    private javax.swing.JPanel panelReporte;
    private javax.swing.JPanel panelReposicion;
    private javax.swing.JTextField txtAdeudo;
    private javax.swing.JTextField txtApoyoeliminar;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtCFDI;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtComentarios;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDescuentoFinal;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JLabel txtFolio;
    private javax.swing.JComboBox<String> txtFormaPago;
    private javax.swing.JTextField txtInteresCredito;
    private javax.swing.JTextField txtIvaFInal;
    private javax.swing.JTextField txtLimCredito;
    private javax.swing.JComboBox<String> txtMetodoPago;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNotasCredito;
    private javax.swing.JTextField txtPagos;
    private javax.swing.JTextField txtRFC;
    private javax.swing.JTextField txtRegimenFiscal;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtSubtotalFinal;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTiempoCredito;
    private javax.swing.JTextField txtTipoPersona;
    private javax.swing.JTextField txtTipoSociedad;
    private javax.swing.JTextField txtTotalFinal;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    public void SetImagenLabel(JLabel labelName, String root) {
        labelName.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(root)).getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), java.awt.Image.SCALE_DEFAULT)));
    }

    private void ticket(int id) throws ParseException {
        try {
            FileOutputStream archivo;
            File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\Tickets\\venta" + txtFolio.getText() + ".pdf");

            //File file = new File("src/pdf/ticket" + id + ".pdf");
            //File file = new File("C:\\Program Files (x86)\\AppLavanderia\\ticket" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Rectangle pageSize = new Rectangle(140.76f, 500f); //ancho y alto
            Document doc = new Document(pageSize, 0, 0, 0, 0);
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            //Image img = Image.getInstance("src/Imagenes/logo 100x100.jpg");
            Image img = Image.getInstance("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg");
            config configura = configuracionesDao.buscarDatos();

            Font negrita = new Font(Font.FontFamily.HELVETICA, configura.getLetraGrande(), Font.UNDERLINE | Font.ITALIC | Font.BOLD);
            Font letra2 = new Font(Font.FontFamily.HELVETICA, configura.getLetraChica() + 2, Font.BOLD);

            PdfPTable logo = new PdfPTable(5);
            logo.setWidthPercentage(100);
            logo.getDefaultCell().setBorder(0);
            float[] columnaEncabezadoLogo = new float[]{20f, 20, 60f, 20f, 20f};
            logo.setWidths(columnaEncabezadoLogo);
            logo.setHorizontalAlignment(Chunk.ALIGN_MIDDLE);
            logo.addCell("");
            logo.addCell("");
            logo.addCell(img);
            logo.addCell("");
            logo.addCell("");
            doc.add(logo);

            String rfc = configura.getRfc();
            String nombre = configura.getNomnbre();
            String tel = configura.getTelefono();
            String direccion = configura.getDireccion();
            String encargado = configura.getEncargado();
            String razonSocial = configura.getRazonSocial();
            String horario = configura.getHorario();

            PdfPTable encabezado = new PdfPTable(1);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            float[] columnaEncabezado = new float[]{100f};
            encabezado.setWidths(columnaEncabezado);
            encabezado.setHorizontalAlignment(Chunk.ALIGN_MIDDLE);
            PdfPCell cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(nombre, letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(encargado, letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase("RFC: " + rfc, letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(razonSocial, letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(horario, letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(direccion, letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(tel, letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);

            doc.add(encabezado);

            letra2 = new Font(Font.FontFamily.HELVETICA, configura.getLetraChica(), Font.BOLD);

            PdfPTable tablapro = new PdfPTable(2);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{60f, 40f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Recepción: " + jLabel63.getText() + " " + jLabel7.getText(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Folio: " + txtFolio.getText(), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(Chunk.NEWLINE);
            doc.add(tablapro);

            tablapro = new PdfPTable(1);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{100f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            Empleados emplead = emple.seleccionarEmpleado("", idEmpleado);
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Recibe: " + String.format("%0" + 2 + "d", Integer.valueOf(emplead.getId())) + " " + emplead.getNombre(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            Clientes miCliente = client.BuscarPorCodigo(clienteElegido);
            String nombreCliente = "";
            if (miCliente.getTipoPersona().equals("Persona Física")) {
                nombreCliente = miCliente.getNombre() + " " + miCliente.getApellidoP() + " " + miCliente.getApellidoM();
            } else {
                nombreCliente = miCliente.getNombreComercial();
            }
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Cliente: " + nombreCliente, new Font(Font.FontFamily.HELVETICA, configura.getLetraGrande() + 1, Font.UNDERLINE | Font.ITALIC | Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(Chunk.NEWLINE);
            doc.add(tablapro);

            tablapro = new PdfPTable(3);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{18f, 40f, 20f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            PdfPCell pro1 = new PdfPCell(new Phrase("Cant", letra2));
            PdfPCell pro2 = new PdfPCell(new Phrase("Concepto", letra2));
            PdfPCell pro3 = new PdfPCell(new Phrase("Precio", letra2));

            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("______", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("_____________", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("_______", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);

            doc.add(tablapro);

            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                tablapro = new PdfPTable(3);
                tablapro.setWidthPercentage(100);
                tablapro.getDefaultCell().setBorder(0);
                columnaEncabezado = new float[]{18f, 40f, 20f};
                tablapro.setWidths(columnaEncabezado);
                tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(TableVenta.getValueAt(i, 1).toString(), letra2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(TableVenta.getValueAt(i, 3).toString(), letra2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(TableVenta.getValueAt(i, 4).toString(), letra2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablapro.addCell(cell);

                doc.add(tablapro);

                tablapro = new PdfPTable(2);
                tablapro.setWidthPercentage(100);
                tablapro.getDefaultCell().setBorder(0);
                float[] columnapro = new float[]{70f, 30f};
                tablapro.setWidths(columnapro);
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("Importe", letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(TableVenta.getValueAt(i, 5).toString() + "  ", letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                doc.add(tablapro);

            }
            tablapro = new PdfPTable(3);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{18f, 40f, 20f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("______", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("_____________", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("_______", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            letra2 = new Font(Font.FontFamily.HELVETICA, configura.getLetraChica() + 4, Font.BOLD);

            tablapro = new PdfPTable(2);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float[] columnapro = new float[]{60f, 40f};
            tablapro.setWidths(columnapro);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Subtotal", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(txtSubtotalFinal.getText(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Descuento", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(txtDescuentoFinal.getText(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("IVA", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(txtIvaFInal.getText(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Total", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(txtTotalFinal.getText(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            letra2 = new Font(Font.FontFamily.HELVETICA, configura.getLetraChica() + 2, Font.BOLD);

            doc.add(tablapro);
            Paragraph comentarios = new Paragraph("\n" + txtComentarios.getText(), letra2);
            comentarios.setAlignment(Element.ALIGN_CENTER);
            doc.add(comentarios);

            Paragraph notasCliente = new Paragraph("\n" + configura.getMensaje(), letra2);
            doc.add(notasCliente);
            doc.close();
            archivo.close();
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

}
