/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ClienteDao;
import Modelo.Clientes;
import Modelo.CorteDiarioDao;
import Modelo.Cotizacion;
import Modelo.CotizacionDao;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.VistaVentaListener;
import Modelo.bitacoraDao;
import Modelo.config;
import Modelo.configuraciones;
import Modelo.detalleVenta;
import Modelo.notaCreditoDao;
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
import java.awt.Color;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.DefaultListModel;
import com.raven.swing.Table3;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.Notification;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class vistaCotizacion extends javax.swing.JDialog {

    private VistaVentaListener vistaVentaListener;

    ClienteDao client = new ClienteDao();
    CotizacionDao ventaDao = new CotizacionDao();
    Eventos event = new Eventos();
    ProductoDao productoDao = new ProductoDao();
    EmpleadosDao emple = new EmpleadosDao();
    CorteDiarioDao corteDao = new CorteDiarioDao();
    CreditoDao creditoDao = new CreditoDao();
    notaCreditoDao notaDao = new notaCreditoDao();
    bitacoraDao bitacora = new bitacoraDao();
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

    public vistaCotizacion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocation(250, 80);
        Seticon();

        controladorLista.setVisible(false);
        listClientes.setVisible(false);
        controladorListaProductos.setVisible(false);
        txtSubtotalFinal.setBackground(new Color(240, 240, 240));
        txtIvaFInal.setBackground(new Color(240, 240, 240));
        txtDescuentoFinal.setBackground(new Color(240, 240, 240));
        txtTotalFinal.setBackground(new Color(240, 240, 240));

        //Datos del cliente
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
        this.setTitle("Punto de venta - Cotización");
        cargando.setVisible(false);
        SetImagenLabel(cargando, "/Imagenes/loading.gif");
        tmp = (DefaultTableModel) TableVenta.getModel();
        if (indicador == 1) { //Nueva venta
            panelPdf.setBackground(new Color(179, 195, 219));
            panelDevolucion.setBackground(new Color(179, 195, 219));
            int i = ventaDao.idCotizacion() + 1; //Se obtiene el numero actual que contendra la siguiente nota
            txtFolio.setText("C" + String.valueOf(i));
            ver = imprimir = reponer = devolver = cancelar = cancelar = false;
            guardar = cambiandoPrecio = btnEliminar = btnModificar = true;
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
        panelPdf.setBackground(new Color(255, 255, 255));
        panelDevolucion.setBackground(new Color(255, 255, 255));
        panelGuardar.setBackground(new Color(179, 195, 219));
        Cotizacion ven = ventaDao.buscarPorFolio(folioBuscar);//Obtenemos los datos de la nota de la BD
        txtFolio.setText("C" + String.valueOf(ven.getFolio())); //Colocamos la fecha de recepcion de la nota y su folio
        cambiandoPrecio = btnEliminar = btnModificar = false;
        jLabel63.setText(fechaFormatoCorrecto(ven.getFecha()));
        clienteElegido = ven.getIdCliente();
        Date horaFecha = StringADateHora(ven.getHora());
        Formatter obj2 = new Formatter();
        String horaModificada = horaFecha.getHours() + ":" + String.valueOf(obj2.format("%02d", horaFecha.getMinutes()));
        jLabel7.setText(horaModificada);
        ver = imprimir = devolver = true;
        guardar = false;
        espacioFecha.setText("REGISTRADA ");
        //vaciamos cada uno de los datos de la nota como si se acabara de generar la nota, bloqueando los espacios de texto para que no se puedan modificar
        Clientes alistar = client.BuscarPorCodigo(ven.getIdCliente());
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

        txtSubtotalFinal.setText("$" + formateador.format(ven.getSubTotal()));
        txtDescuentoFinal.setText("$" + formateador.format(ven.getDescuentos()));
        txtIvaFInal.setText("$" + formateador.format(ven.getIva()));
        txtTotalFinal.setText("$" + formateador.format(ven.getTotal()));
        txtValidacion.setText(ven.getTiempoValidacion());
        txtAnticipo.setText("$" + formateador.format(ven.getAnticipo()));

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

    public void eventoEnterAgregar() {
        Producto p = productoDao.existeProductoDescripcion(txtCodigo.getText());
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

            if (p.getInventario() == 1 && (aumentar + cantidadAumentar) >= p.getExistencia()) {
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
    }

    public void eventoF1() {
        bloquearDatos(true);
        limpiarDatosNota();
        LimpiarTabla();
        txtFolio.setText("C" + (ventaDao.idCotizacion() + 1));
        ver = imprimir = reponer = devolver = cancelar = cancelar = false;
        guardar = cambiandoPrecio = btnEliminar = btnModificar = true;
        jLabel63.setText(fechaFormatoCorrecto(corteDao.getDia()));
        indicador = 1;
        panelPdf.setBackground(new Color(179, 195, 219));
        panelDevolucion.setBackground(new Color(179, 195, 219));
        panelGuardar.setBackground(new Color(255, 255, 255));

    }

    public void limpiarDatosNota() {
        txtBuscarCliente.setText("");
        txtNombreCliente.setText("");
        txtRFC.setText("");
        txtCorreo.setText("");
        txtTipoSociedad.setText("");
        txtRegimenFiscal.setText("");
        txtTelefono.setText("");
        txtDomicilio.setText("");
        txtTipoPersona.setText("");
        txtCFDI.setText("");
        txtCodigoPostal.setText("");
        txtLimCredito.setText("");
        txtAdeudo.setText("");
        espacioFecha.setText("-");
        txtSubtotalFinal.setText("");
        txtIvaFInal.setText("");
        txtTotalFinal.setText("");
        txtDescuentoFinal.setText("");
        txtCodigo.setText("");
        controladorListaProductos.setVisible(false);
        listClientes.setVisible(false);
        clienteElegido = 0;
        totalAPagar = 0.00;
        LimpiarTabla();
        jLabel7.setText("-");
        txtValidacion.setText("");
        txtAnticipo.setText("");
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
            if (!"".equals(txtNombreCliente.getText()) && !"".equals(txtSubtotalFinal.getText())) {
                Empleados empleado = emple.seleccionarEmpleado("", idEmpleado);
                ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(empleado.getId());
                cc.setVisible(true);
                if (cc.contraseñaAceptada == true) {
                    new Thread() {
                        public void run() {
                            try {
                                cargando.setVisible(true);
                                proceso();
                                cargando.setVisible(false);
                                Cotizacion venta = new Cotizacion();
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
                                Number totalC = formateador.parse(removefirstChar(txtTotalFinal.getText()));
                                venta.setTotal(totalC.doubleValue());
                                venta.setTiempoValidacion(txtValidacion.getText());
                                if (!"".equals(txtAnticipo.getText())) {
                                    venta.setAnticipo(Double.parseDouble(txtAnticipo.getText()));
                                } else {
                                    venta.setAnticipo(0);
                                }
                                venta.setIdEmpleado(empleado.getId());
                                bitacora.registrarRegistro("Cotización registrada de " + txtNombreCliente.getText() + " por $" + venta.getTotal(), idEmpleado, corteDao.getDia());
                                ventaDao.registrarCotizacion(venta);
                                registrarDetalle();
                                bloquearDatos(false);
                                guardar = btnEliminar = cambiandoPrecio = btnModificar = false;
                                ver = imprimir = reponer = devolver = cancelar = cancelar = true;
                                LimpiarTabla();
                                vaciarDatos(Integer.parseInt(txtFolio.getText().substring(1)));
                                folioMostrar = Integer.parseInt(txtFolio.getText().substring(1));
                                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Venta registrada exitosamente");
                                panel.showNotification();
                                indicador = 0;
                                panelPdf.setBackground(new Color(255, 255, 255));
                                panelDevolucion.setBackground(new Color(255, 255, 255));

                            } catch (ParseException ex) {
                                Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }.start();
                } else {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Contraseña incorrecta");
                    panel.showNotification();
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
        txtValidacion.setEnabled(cambiar);
        txtAnticipo.setEnabled(cambiar);

    }

    private void registrarDetalle() throws ParseException {
        int id = ventaDao.idCotizacion();
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
    }

    public void proceso() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eventoF4() {
        if (devolver == true) {
            vistaVenta vC1 = new vistaVenta(new javax.swing.JFrame(), true);
            vC1.setLocation(200, 50);
            vC1.validandoDatos(0, 1);
            vC1.vaciarEmpleado(idEmpleado);
            Cotizacion ven = ventaDao.buscarPorFolio(folioMostrar);
            vC1.vaciarCotizacion((DefaultTableModel) TableVenta.getModel(), ven.getIdCliente());
            vC1.setVisible(true);

        }
    }

    public void eventoF3() {
        if (ver == true) {
            try {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Generando PDF");
                panel.showNotification();
                int id = Integer.parseInt(txtFolio.getText().substring(1));
                File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\PDF\\cotizacion" + txtFolio.getText() + ".pdf");
                pdfCotizacion(id);
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
            } catch (ParseException ex) {
                Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
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
        panelPdf = new javax.swing.JPanel();
        btnReporte = new javax.swing.JLabel();
        panelDevolucion = new javax.swing.JPanel();
        btnDevolucion = new javax.swing.JLabel();
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
        jPanel2 = new javax.swing.JPanel();
        txtSubtotalFinal = new javax.swing.JTextField();
        txtDescuentoFinal = new javax.swing.JTextField();
        txtIvaFInal = new javax.swing.JTextField();
        txtTotalFinal = new javax.swing.JTextField();
        lbInteres3 = new javax.swing.JLabel();
        lbInteres5 = new javax.swing.JLabel();
        lbInteres6 = new javax.swing.JLabel();
        lbInteres7 = new javax.swing.JLabel();
        txtApoyoeliminar = new javax.swing.JTextField();
        espacioFecha = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cargando = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtAnticipo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtValidacion = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        controladorListaProductos = new javax.swing.JScrollPane();
        listProductos = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        txtCodigo = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("COTIZACIÓN");

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
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, -1, 30));

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
        btnGuardar.setToolTipText("F2 - Registrar Cotización");
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

        panelPdf.setBackground(new java.awt.Color(255, 255, 255));
        panelPdf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelPdfMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelPdfMouseEntered(evt);
            }
        });

        btnReporte.setBackground(new java.awt.Color(255, 255, 255));
        btnReporte.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen1.png"))); // NOI18N
        btnReporte.setToolTipText("F3 - Visualizar PDF");
        btnReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReporteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReporteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReporteMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelPdfLayout = new javax.swing.GroupLayout(panelPdf);
        panelPdf.setLayout(panelPdfLayout);
        panelPdfLayout.setHorizontalGroup(
            panelPdfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReporte, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelPdfLayout.setVerticalGroup(
            panelPdfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReporte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 50, 50));

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
        btnDevolucion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen4.png"))); // NOI18N
        btnDevolucion.setToolTipText("F5 - Vaciar cotización");
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

        jPanel1.add(panelDevolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 50, 50));

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSubtotalFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSubtotalFinal.setToolTipText("Subtotal");
        txtSubtotalFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubtotalFinal.setEnabled(false);
        txtSubtotalFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSubtotalFinalKeyReleased(evt);
            }
        });
        jPanel2.add(txtSubtotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 5, 140, -1));

        txtDescuentoFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDescuentoFinal.setToolTipText("Descuentos acumulados");
        txtDescuentoFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescuentoFinal.setEnabled(false);
        txtDescuentoFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoFinalKeyReleased(evt);
            }
        });
        jPanel2.add(txtDescuentoFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 32, 140, -1));

        txtIvaFInal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtIvaFInal.setToolTipText("IVA");
        txtIvaFInal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIvaFInal.setEnabled(false);
        txtIvaFInal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIvaFInalKeyReleased(evt);
            }
        });
        jPanel2.add(txtIvaFInal, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 59, 140, -1));

        txtTotalFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTotalFinal.setToolTipText("Total de la venta");
        txtTotalFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalFinal.setEnabled(false);
        txtTotalFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalFinalKeyReleased(evt);
            }
        });
        jPanel2.add(txtTotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 86, 140, -1));

        lbInteres3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres3.setText("Total");
        jPanel2.add(lbInteres3, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 87, 60, 20));

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

        txtApoyoeliminar.setEditable(false);
        txtApoyoeliminar.setEnabled(false);
        jPanel2.add(txtApoyoeliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 20, 10));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 535, 240, 115));

        espacioFecha.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        espacioFecha.setForeground(new java.awt.Color(0, 0, 153));
        jPanel1.add(espacioFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 330, 30));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setText(" HORA:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, -1, 20));

        cargando.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(cargando, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 70, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Anticipo requerido");
        jPanel6.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 120, 20));

        txtAnticipo.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        txtAnticipo.setToolTipText("Anticipo ($) dejado");
        txtAnticipo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAnticipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnticipoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnticipoKeyTyped(evt);
            }
        });
        jPanel6.add(txtAnticipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 17, 130, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Tiempo de validacion");
        jPanel6.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, 20));

        txtValidacion.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        txtValidacion.setToolTipText("Periodo valido");
        txtValidacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtValidacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValidacionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValidacionKeyTyped(evt);
            }
        });
        jPanel6.add(txtValidacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 50, 130, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 535, 280, 120));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        controladorListaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                controladorListaProductosKeyReleased(evt);
            }
        });

        listProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProductosMouseClicked(evt);
            }
        });
        listProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listProductosKeyReleased(evt);
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
        TableVenta.setToolTipText("Haga doble click para realizar alguna acción");
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
            TableVenta.getColumnModel().getColumn(7).setPreferredWidth(17);
            TableVenta.getColumnModel().getColumn(8).setPreferredWidth(20);
        }

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 830, 210));

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

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 850, 270));

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

    private void btnReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseClicked
        eventoF3();
    }//GEN-LAST:event_btnReporteMouseClicked

    private void btnReporteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseEntered
        panelPdf.setBackground(new Color(179, 195, 219));
        if (ver == true) {
            panelPdf.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelPdf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

    }//GEN-LAST:event_btnReporteMouseEntered

    private void btnReporteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseExited
        if (ver == true) {
            panelPdf.setBackground(new Color(255, 255, 255));
            panelPdf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelPdf.setBackground(new Color(179, 195, 219));
            panelPdf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnReporteMouseExited

    private void panelPdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPdfMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelPdfMouseClicked

    private void panelPdfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPdfMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelPdfMouseEntered

    private void btnDevolucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDevolucionMouseClicked
        eventoF4();
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

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtBuscarClienteKeyReleased

    private void txtCFDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCFDIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCFDIActionPerformed

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

    private void txtAnticipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnticipoKeyReleased
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

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtAnticipoKeyReleased

    private void txtAnticipoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnticipoKeyTyped
        event.numberDecimalKeyPress(evt, txtAnticipo);
    }//GEN-LAST:event_txtAnticipoKeyTyped

    private void txtValidacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValidacionKeyReleased
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

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtValidacionKeyReleased

    private void txtValidacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValidacionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValidacionKeyTyped

    private void listProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listProductosMouseClicked
        if (evt.getClickCount() == 2) {
            Producto p = productoDao.existeProductoDescripcion(listProductos.getSelectedValue());
            if (p.getId() != 0) {
                double aumentar = 0; //lo de la tabla
                double cantidadAumentar = 1; // lo del jtextEfild
                double nuevoPrecio = -1;
                for (int i = 0; i < TableVenta.getRowCount(); i++) {
                    if (TableVenta.getValueAt(i, 0).toString().equals(p.getCodigo())) {
                        aumentar = Double.parseDouble(TableVenta.getValueAt(i, 1).toString());
                        Number elUnitario;
                        try {
                            elUnitario = formateador.parse(removefirstChar(TableVenta.getModel().getValueAt(i, 4).toString()));
                            p.setPrecioVenta(elUnitario.doubleValue());
                        } catch (ParseException ex) {
                            Logger.getLogger(vistaCotizacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (p.getInventario() == 1) {
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
                    vdr.vaciarProducto(productoDao.BuscarPorCodigo(TableVenta.getValueAt(fila, 0).toString()), cantidad, elDescuento.doubleValue(), 2);
                    vdr.setVisible(true);
                    if (vdr.accionRealizada == true) {
                        List<Double> datos = vdr.datos;
                        TableVenta.setValueAt(formato.format(datos.get(0)), fila, 1);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(1)), fila, 5);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(2)), fila, 6);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(3)), fila, 7);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(4)), fila, 8);
                        TableVenta.setValueAt("$" + formateador.format(datos.get(5)), fila, 4);
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

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        rellenarListaProductos();

        Producto p = productoDao.existeProducto(txtCodigo.getText());
        if (p.getId() != 0) {
            double aumentar = 0; //lo de la tabla
            double cantidadAumentar = 1; // lo del jtextEfild
            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                if (TableVenta.getValueAt(i, 0).toString().equals(p.getCodigo())) {
                    aumentar = Double.parseDouble(TableVenta.getValueAt(i, 1).toString());
                    Number elUnitario;
                    try {
                        elUnitario = formateador.parse(removefirstChar(TableVenta.getModel().getValueAt(i, 4).toString()));
                        p.setPrecioVenta(elUnitario.doubleValue());
                    } catch (ParseException ex) {
                        Logger.getLogger(vistaCotizacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (p.getInventario() == 1) {
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

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
        txtCodigo.requestFocus();

    }//GEN-LAST:event_txtCodigoKeyReleased

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

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_listClientesKeyReleased

    private void controladorListaProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_controladorListaProductosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_controladorListaProductosKeyReleased

    private void listProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listProductosKeyReleased
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

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_listProductosKeyReleased

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

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_TableVentaKeyReleased

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
    private javax.swing.JTable TableVenta;
    private javax.swing.JLabel btnClientes;
    private javax.swing.JLabel btnDevolucion;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnReporte;
    private javax.swing.JLabel cargando;
    private javax.swing.JScrollPane controladorLista;
    private javax.swing.JScrollPane controladorListaProductos;
    private javax.swing.JLabel espacioFecha;
    private javax.swing.JLabel jButton15;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbInteres3;
    private javax.swing.JLabel lbInteres5;
    private javax.swing.JLabel lbInteres6;
    private javax.swing.JLabel lbInteres7;
    private javax.swing.JList<String> listClientes;
    private javax.swing.JList<String> listProductos;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelDevolucion;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JPanel panelPdf;
    private javax.swing.JPanel panelRegistrarNota1;
    private javax.swing.JTextField txtAdeudo;
    private javax.swing.JTextField txtAnticipo;
    private javax.swing.JTextField txtApoyoeliminar;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtCFDI;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDescuentoFinal;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JLabel txtFolio;
    private javax.swing.JTextField txtIvaFInal;
    private javax.swing.JTextField txtLimCredito;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtRFC;
    private javax.swing.JTextField txtRegimenFiscal;
    private javax.swing.JTextField txtSubtotalFinal;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoPersona;
    private javax.swing.JTextField txtTipoSociedad;
    private javax.swing.JTextField txtTotalFinal;
    private javax.swing.JTextField txtValidacion;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    public void SetImagenLabel(JLabel labelName, String root) {
        labelName.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(root)).getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), java.awt.Image.SCALE_DEFAULT)));
    }

    private void pdfCotizacion(int id) throws ParseException {
        try {
            FileOutputStream archivo;
            File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\PDF\\cotizacion" + txtFolio.getText() + ".pdf");

            //File file = new File("src/pdf/ticket" + id + ".pdf");
            //File file = new File("C:\\Program Files (x86)\\AppLavanderia\\ticket" + id + ".pdf");
            //File file = new File("C:\\Program Files (x86)\\AppLavanderia\\corte" + fechaHoy + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            //Image img = Image.getInstance("src/Imagenes/logo 100x100.jpg");
            Image img = Image.getInstance("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg");
            config configura = configuracionesDao.buscarDatos();

            Font letra2 = new Font(Font.FontFamily.TIMES_ROMAN, 13);
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);

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
            cell = new PdfPCell(new Phrase(nombre, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(encargado, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase("RFC: " + rfc, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(razonSocial, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(horario, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(direccion, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(tel + "\nCOTIZACIÓN", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);

            Paragraph dejarEspacio = new Paragraph();
            dejarEspacio.add(Chunk.NEWLINE);
            doc.add(dejarEspacio);

            doc.add(encabezado);
            doc.add(dejarEspacio);

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
            cell = new PdfPCell(new Phrase("Cliente: " + nombreCliente, letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            doc.add(dejarEspacio);

            letra2 = new Font(Font.FontFamily.TIMES_ROMAN, 11);
            tablapro = new PdfPTable(9);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{27f, 14f, 17f, 28f, 20f, 20f, 20f, 20f, 20f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            PdfPCell pro1 = new PdfPCell(new Phrase("Código", letra2));
            PdfPCell pro2 = new PdfPCell(new Phrase("Cant", letra2));
            PdfPCell pro3 = new PdfPCell(new Phrase("Unidad", letra2));
            PdfPCell pro4 = new PdfPCell(new Phrase("Descripción", letra2));
            PdfPCell pro5 = new PdfPCell(new Phrase("P/U", letra2));
            PdfPCell pro6 = new PdfPCell(new Phrase("Importe", letra2));
            PdfPCell pro7 = new PdfPCell(new Phrase("Descuento", letra2));
            PdfPCell pro8 = new PdfPCell(new Phrase("IVA", letra2));
            PdfPCell pro9 = new PdfPCell(new Phrase("Total", letra2));

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

            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                for (int k = 0; k < 9; k++) {
                    cell = new PdfPCell();
                    cell = new PdfPCell(new Phrase(TableVenta.getValueAt(i, k).toString(), letra2));
                    if (k > 3) {
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    } else {
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    }
                    cell.setBorder(0);
                    tablapro.addCell(cell);
                }
            }

            doc.add(tablapro);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{25f, 65f, 14f, 17f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

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
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
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
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
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
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
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

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Anticipo", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(txtAnticipo.getText(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Restante", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            Cotizacion miCotizacion = ventaDao.buscarPorFolio(id);
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("$" + formateador.format(miCotizacion.getTotal() - miCotizacion.getAnticipo()), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            doc.add(dejarEspacio);
            doc.add(dejarEspacio);
            doc.add(dejarEspacio);
            doc.add(dejarEspacio);
            
             PdfPTable tablaFinal = new PdfPTable(3);
            tablaFinal.setWidthPercentage(100);
            tablaFinal.getDefaultCell().setBorder(0);
            float[] columnapro = new float[]{50f,50f,50f};
            tablaFinal.setWidths(columnapro);
            tablaFinal.setHorizontalAlignment(Element.ALIGN_LEFT);
            
             cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(""));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell); 
            
                 cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("______________________"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell); 
            
               cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(""));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell); 
            
               cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(""));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell); 
            
             cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Firma: "+nombreCliente,letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell); 
            
               cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(""));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell); 
            doc.add(tablaFinal);            

            Font letraChiqui = new Font(Font.FontFamily.TIMES_ROMAN, 9);
            Paragraph notasCliente = new Paragraph("\nEn caso de no requerir el producto, será devuelto el 50.00% del anticipo\n" + configura.getMensaje(), letraChiqui);
            doc.add(notasCliente);
            doc.close();
            archivo.close();
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

}
