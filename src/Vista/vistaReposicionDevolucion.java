/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ClienteDao;
import Modelo.CorteDiarioDao;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.HistorialCliente;
import Modelo.HistorialClienteDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.Ventas;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import Modelo.detalleVenta;
import Modelo.notaCredito;
import Modelo.notaCreditoDao;
import static Vista.vistaVenta.removefirstChar;
import com.raven.swing.Table;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Jonathan Gil
 */
public class vistaReposicionDevolucion extends javax.swing.JDialog {

    int folio, tipo, idNotaCredito;
    boolean revision, accionRealizada;
    DefaultTableModel tmp;
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat formato = new DecimalFormat("0.00");

    notaCreditoDao notaCredito = new notaCreditoDao();
    EmpleadosDao emple = new EmpleadosDao();
    VentasDao ventaDao = new VentasDao();
    CorteDiarioDao corteDao = new CorteDiarioDao();
    ClienteDao client = new ClienteDao();
    ProductoDao productoDao = new ProductoDao();
    bitacoraDao bitacora = new bitacoraDao();
    CreditoDao creditoDao = new CreditoDao();
    HistorialClienteDao historialDao = new HistorialClienteDao();

    int idEmpleado;

    public void vaciarEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public vistaReposicionDevolucion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocationRelativeTo(null);
                }

            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
        txtTotal.setBackground(new Color(240, 240, 240));
        txtIva.setBackground(new Color(240, 240, 240));
        txtSubTotal.setBackground(new Color(240, 240, 240));
        txtSaldo.setBackground(new Color(240, 240, 240));
        txtNuevoSaldo.setBackground(new Color(240, 240, 240));

        TableVenta.setBackground(Color.WHITE);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TableVenta.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableVenta.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TableVenta.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        ((DefaultTableCellRenderer) TableVenta.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        txtFormaPago.setBackground(new Color(255, 255, 255));

    }

    public void validandoDatos(int tipo, int folio, boolean revision, int idNotaCredito) {
        this.folio = folio;
        this.tipo = tipo;
        this.revision = revision;
        this.idNotaCredito = idNotaCredito;
        notaCredito nota = notaCredito.buscarPorId(idNotaCredito);
        TableColumnModel columnModel = TableVenta.getColumnModel();
        TableColumn sixthColumn = columnModel.getColumn(5);
        TableColumn seventhColumn = columnModel.getColumn(6);
        Ventas venta = ventaDao.buscarPorFolio(folio);
        if (venta.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
            txtFormaPago.setVisible(false);
            jLabel15.setVisible(false);
        }
        List<detalleVenta> ListaDetalles = ventaDao.regresarDetalles(folio);
        List<detalleVenta> ListaDetallesDevolucion = new ArrayList<detalleVenta>();
        if (tipo == 1) { //descuento
            txtSubTotal.setToolTipText("Suma de descuentos");
            txtTitulo.setText("Descuento");
            this.setTitle("Punto de venta - Descuento a venta");
            sixthColumn.setHeaderValue("Descuento");
            seventhColumn.setHeaderValue("Restante");
            if (revision == false) { // registrar
                txtSaldo.setText("$" + formateador.format(notaCredito.retornarSumaNotas(venta.getFolio())));
                ListaDetallesDevolucion = notaCredito.regresarDetalles(folio, "descuento");
            }
        }
        if (tipo == 2) { //Bonificacion
            txtSubTotal.setToolTipText("Suma de bonificaciones");
            txtTitulo.setText("Bonificación");
            this.setTitle("Punto de venta - Bonificación a venta");
            sixthColumn.setHeaderValue("Bonif");
            seventhColumn.setHeaderValue("Restante");
            if (revision == false) { // registrar
                txtSaldo.setText("$" + formateador.format(notaCredito.retornarSumaNotas(venta.getFolio())));
                ListaDetallesDevolucion = notaCredito.regresarDetalles(folio, "Bonificación");
            }
        }
        if (tipo == 3) { //Devolucion
            txtSubTotal.setToolTipText("Suma de importe por devoluciones");
            txtTitulo.setText("Devolución");
            this.setTitle("Punto de venta - Devolución de venta");
            sixthColumn.setHeaderValue("Devolver");
            seventhColumn.setHeaderValue("Importe");
            if (revision == false) { // registrar
                txtSaldo.setText("$" + formateador.format(notaCredito.retornarSumaNotas(venta.getFolio())));
                ListaDetallesDevolucion = notaCredito.regresarDetalles(folio, "Devolución");

            }
        }

        TableVenta.getTableHeader().repaint();

        //vaciarDatos    
        if (revision == false) { //registrar
            txtFolio.setText("FOLIO E" + (1 + notaCredito.idNotaCredito()));
            txtSaldo.setToolTipText("Saldo de la venta");
            txtNuevoSaldo.setToolTipText("Nuevo saldo en notas de crédito");
            tmp = (DefaultTableModel) TableVenta.getModel();
            for (int i = 0; i < ListaDetalles.size(); i++) {
                boolean agregando = true;
                double cantidadA = ListaDetalles.get(i).getCantidad();
                for (int j = 0; j < ListaDetallesDevolucion.size(); j++) {
                    if (ListaDetalles.get(i).getCodigo().equals(ListaDetallesDevolucion.get(j).getCodigo())) {
                        if (ListaDetallesDevolucion.get(j).getCantidadModificar() == ListaDetalles.get(i).getCantidad()) {
                            agregando = false;
                        } else {
                            cantidadA = ListaDetalles.get(i).getCantidad() - ListaDetallesDevolucion.get(j).getCantidadModificar();
                        }
                    }
                }
                if (agregando == true) {
                    Object[] o = new Object[7];
                    o[0] = ListaDetalles.get(i).getCodigo();
                    o[1] = formato.format(cantidadA);
                    o[2] = ListaDetalles.get(i).getDescripcion();
                    o[3] = "$" + formateador.format(ListaDetalles.get(i).getPrecioUnitario());
                    o[4] = "$" + formateador.format(cantidadA * ListaDetalles.get(i).getPrecioUnitario());
                    if (tipo == 3) {
                        o[5] = formato.format(ListaDetalles.get(i).getCantidadModificar());
                        o[6] = "$" + formateador.format(0);
                    } else {
                        o[5] = "$" + formateador.format(ListaDetalles.get(i).getCantidadModificar());
                        o[6] = "$" + formateador.format(cantidadA * ListaDetalles.get(i).getPrecioUnitario());
                    }
                    tmp.addRow(o);
                }
            }
        } else {//ver
            txtFormaPago.removeAllItems();
            txtFormaPago.addItem(nota.getFormaPago());
            txtSaldo.setToolTipText("Saldo anterior");
            txtNuevoSaldo.setToolTipText("Saldo acumulado en notas de crédito");
            txtSaldo.setToolTipText("Saldo anterior");
            txtObservaciones.setEnabled(false);
            txtObservaciones.setBackground(new Color(240, 240, 240));
            txtFolio.setText("FOLIO E" + nota.getId());
            txtSubTotal.setText("$" + formateador.format(nota.getSubtotal()));
            txtIva.setText("$" + formateador.format(nota.getIva()));
            txtTotal.setText("$" + formateador.format(nota.getTotal()));
            txtSaldo.setText("$" + formateador.format(nota.getSaldoViejo()));
            txtNuevoSaldo.setText("$" + formateador.format(nota.getNuevoSaldo()));
            txtObservaciones.setText(nota.getRazon());
            Date horaFecha = StringADateHora(nota.getHora());
            Formatter obj2 = new Formatter();
            String horaModificada = horaFecha.getHours() + ":" + String.valueOf(obj2.format("%02d", horaFecha.getMinutes()));
            labelTexto.setText("FECHA: " + fechaFormatoCorrecto(nota.getFecha()) + "    HORA: " + horaModificada);
            tmp = (DefaultTableModel) TableVenta.getModel();
            List<detalleVenta> listar = notaCredito.regresarDetallesSoloUna(idNotaCredito);
            for (int i = 0; i < listar.size(); i++) {
                Object[] o = new Object[7];
                o[0] = listar.get(i).getCodigo();
                o[1] = formato.format(listar.get(i).getCantidad());
                o[2] = listar.get(i).getDescripcion();
                o[3] = "$" + formateador.format(listar.get(i).getPrecioUnitario());
                o[4] = "$" + formateador.format(listar.get(i).getImporte());
                if (tipo == 3) {
                    o[5] = formato.format(listar.get(i).getCantidadModificar());
                } else {
                    o[5] = "$" + formateador.format(listar.get(i).getCantidadModificar());
                }
                o[6] = "$" + formateador.format(listar.get(i).getDescuento());
                tmp.addRow(o);
            }
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

    public void eventoEnter() {
        try {
            if (revision == false) {
                if (!"".equals(txtTotal.getText()) && !"".equals(txtNuevoSaldo.getText())) {

                    Empleados empleado = emple.seleccionarEmpleado("", idEmpleado);
                    ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                    cc.vaciarEmpleado(empleado.getId());
                    cc.setVisible(true);
                    if (cc.contraseñaAceptada == true) {
                        notaCredito notaG = new notaCredito();
                        notaG.setFolioVenta(folio);
                        Number subtotalN = formateador.parse(removefirstChar(txtSubTotal.getText()));
                        notaG.setSubtotal(subtotalN.doubleValue());
                        Number ivaN = formateador.parse(removefirstChar(txtIva.getText()));
                        notaG.setIva(ivaN.doubleValue());
                        Number totalN = formateador.parse(removefirstChar(txtTotal.getText()));
                        notaG.setTotal(totalN.doubleValue());
                        Number saldoV = formateador.parse(removefirstChar(txtSaldo.getText()));
                        notaG.setSaldoViejo(saldoV.doubleValue());
                        Number saldoN = formateador.parse(removefirstChar(txtNuevoSaldo.getText()));
                        notaG.setNuevoSaldo(saldoN.doubleValue());
                        notaG.setIdRecibe(idEmpleado);
                        notaG.setRazon(txtObservaciones.getText());
                        LocalDate fechaActual = LocalDate.now();
                        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

                        // Formatear la fecha como una cadena en el formato deseado
                        notaG.setFecha(corteDao.getDia());
                        LocalTime horaActual = LocalTime.now();
                        // horaActual.minusHours(1);
                        // Formatear la hora en formato hh:mm:ss
                        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
                        notaG.setHora(horaActual.format(formatoHora));
                        Ventas venta = ventaDao.buscarPorFolio(folio);
                        if (tipo == 3) {
                            notaG.setTipo("Devolución");
                            bitacora.registrarRegistro("Se genero una nota de crédito de Devolución por $" + formateador.format(notaG.getTotal()) + " a la venta F" + notaG.getFolioVenta(), idEmpleado, corteDao.getDia());
                            if (venta.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                                creditoDao.aumentarAdeudo(venta.getIdCliente(), -notaG.getTotal());
                                Credito miCredito = creditoDao.BuscarPorCodigoCliente(venta.getIdCliente());
                                HistorialCliente hc = new HistorialCliente();
                                hc.setAbono(notaG.getTotal());
                                hc.setCargo(0);
                                hc.setFecha(notaG.getFecha());
                                hc.setFolio("E" + (notaCredito.idNotaCredito() + 1));
                                hc.setIdCliente(venta.getIdCliente());
                                hc.setIdRecibe(idEmpleado);
                                hc.setMovimiento("Nota de crédito - Devolución");
                                hc.setSaldo(miCredito.getAdeudo());
                                historialDao.registrarMovimiento(hc);
                                notaG.setFormaPago("99 Por definir");
                            } else {
                                notaG.setFormaPago(txtFormaPago.getSelectedItem().toString());
                            }
                        }
                        if (tipo == 2) {
                            notaG.setTipo("Bonificación");
                            notaG.setFormaPago(txtFormaPago.getSelectedItem().toString());
                            bitacora.registrarRegistro("Se genero una nota de crédito de Bonificación por $" + formateador.format(notaG.getTotal()) + " a la venta F" + notaG.getFolioVenta(), idEmpleado, corteDao.getDia());
                        }
                        if (tipo == 1) {
                            notaG.setTipo("Descuento");
                            bitacora.registrarRegistro("Se genero una nota de crédito de Devolución por $" + formateador.format(notaG.getTotal()) + " a la venta F" + notaG.getFolioVenta(), idEmpleado, corteDao.getDia());
                            creditoDao.aumentarAdeudo(venta.getIdCliente(), -notaG.getTotal());
                            Credito miCredito = creditoDao.BuscarPorCodigoCliente(venta.getIdCliente());
                            HistorialCliente hc = new HistorialCliente();
                            hc.setAbono(notaG.getTotal());
                            hc.setCargo(0);
                            hc.setFecha(notaG.getFecha());
                            hc.setFolio("E" + (notaCredito.idNotaCredito() + 1));
                            hc.setIdCliente(venta.getIdCliente());
                            hc.setIdRecibe(idEmpleado);
                            hc.setMovimiento("Nota de crédito - Descuento");
                            hc.setSaldo(miCredito.getAdeudo());
                            historialDao.registrarMovimiento(hc);
                            notaG.setFormaPago("99 Por definir");
                        }

                        notaCredito.registrarVenta(notaG);
                        accionRealizada = true;
                        registrarDetalle();
                        dispose();

                    }
                } else {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Rellene todos los campos");
                    panel.showNotification();
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(vistaReposicionDevolucion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void registrarDetalle() throws ParseException {
        List<detalleVenta> LsDeatlle = new ArrayList<detalleVenta>();
        List<Producto> lsProducto = new ArrayList<Producto>();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            Producto prod = productoDao.BuscarPorCodigo(TableVenta.getValueAt(i, 0).toString());
            if (tipo == 3) {
                prod.setExistencia(-Double.parseDouble(TableVenta.getValueAt(i, 5).toString()));
                if (prod.getInventario() == 1 && tipo == 3) {
                    lsProducto.add(prod);
                }
            }

            detalleVenta detalle = new detalleVenta();
            detalle.setFolioNotaCredito(notaCredito.idNotaCredito());
            detalle.setFolioVenta(folio);
            detalle.setCodigo(TableVenta.getValueAt(i, 0).toString());
            detalle.setCantidad(Double.parseDouble(TableVenta.getValueAt(i, 1).toString()));
            detalle.setDescripcion(TableVenta.getValueAt(i, 2).toString());
            Number cUnitario = 0;
            Number montoA = 0;
            Number ultimoD = 0;
            try {
                cUnitario = formateador.parse(removefirstChar(TableVenta.getValueAt(i, 3).toString()));
                montoA = formateador.parse(removefirstChar(TableVenta.getValueAt(i, 5).toString()));
                ultimoD = formateador.parse(removefirstChar(TableVenta.getValueAt(i, 6).toString()));
            } catch (ParseException ex) {
                Logger.getLogger(vistaReposicionDevolucion.class.getName()).log(Level.SEVERE, null, ex);
            }
            detalle.setPrecioUnitario(cUnitario.doubleValue());
            detalle.setImporte(cUnitario.doubleValue() * detalle.getCantidad());

            if (tipo == 3) {
                detalle.setCantidadModificar(Double.parseDouble(TableVenta.getValueAt(i, 5).toString()));
                detalle.setTipo("Devolución");
            } else {
                detalle.setCantidadModificar(montoA.doubleValue());
                if (tipo == 2) {
                    detalle.setTipo("Descuento");
                } else {
                    detalle.setTipo("Bonificación");
                }
            }
            notaCredito.registrarDetalle(detalle, ultimoD.doubleValue());
        }
        productoDao.ajustarInventarioVenta(lsProducto);
    }

    public void eventoF1() {
        if (revision == false) {
            txtObservaciones.setText("");
            txtTotal.setText("");
            txtSubTotal.setText("");
            txtIva.setText("");
            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                TableVenta.setValueAt("0", i, 4);
            }
        }
    }

    public void calcularCosto() throws ParseException {
        boolean bandera = true;
        double totalDescuento = 0;
        double costo = 0;
        double saldoA = 0;
        try {
            Number saldoN = formateador.parse(removefirstChar(txtSaldo.getText()));
            saldoA = saldoN.doubleValue();
            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                if (tipo == 3) {
                    Number unitarioP = formateador.parse(removefirstChar(TableVenta.getValueAt(i, 3).toString()));
                    costo = costo + (unitarioP.doubleValue() * Double.parseDouble(TableVenta.getValueAt(i, 5).toString()));
                } else {
                    Number descuentoP = formateador.parse(removefirstChar(TableVenta.getValueAt(i, 5).toString()));
                    totalDescuento = totalDescuento + descuentoP.doubleValue();
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(vistaReposicionDevolucion.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (tipo == 3) {
            txtSubTotal.setText("$" + formateador.format(costo));
            double ivaC = costo * .16;
            txtIva.setText("$" + formateador.format(ivaC));
            txtTotal.setText("$" + formateador.format(ivaC + costo));
            txtNuevoSaldo.setText("$" + formateador.format(saldoA + (ivaC + costo)));
        } else {
            txtSubTotal.setText("$" + formateador.format(totalDescuento));
            double ivaC = totalDescuento * .16;
            txtIva.setText("$" + formateador.format(ivaC));
            txtTotal.setText("$" + formateador.format(ivaC + totalDescuento));
            txtNuevoSaldo.setText("$" + formateador.format(saldoA + (ivaC + totalDescuento)));
        }
        Number verificarSaldo = formateador.parse(removefirstChar(txtNuevoSaldo.getText()));

        if (verificarSaldo.doubleValue() < (-1)) {
            bandera = false;
            txtNuevoSaldo.setText("");
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

        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        txtTitulo = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        panelLimpiar = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();
        panelAceptar = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JLabel();
        labelTexto = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        txtSubTotal = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtNuevoSaldo = new javax.swing.JTextField();
        txtObservaciones = new javax.swing.JTextField();
        txtFolio = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtFormaPago = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jScrollPane1PropertyChange(evt);
            }
        });
        jScrollPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jScrollPane1KeyReleased(evt);
            }
        });

        TableVenta = new Table();
        TableVenta.setBackground(new java.awt.Color(204, 204, 255));
        TableVenta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cant", "Descripción", "P/U", "Subtotal", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        TableVenta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TableVentaPropertyChange(evt);
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
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(60);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(10);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(80);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(15);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(25);
            TableVenta.getColumnModel().getColumn(5).setPreferredWidth(28);
            TableVenta.getColumnModel().getColumn(6).setPreferredWidth(25);
        }

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 600, 200));

        txtTitulo.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        txtTitulo.setForeground(new java.awt.Color(0, 0, 255));
        txtTitulo.setText("-");
        jPanel5.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 140, -1));

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setText("¿Está seguro de ejecutar esta acción? ");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 410, -1, -1));

        panelLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        panelLimpiar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLimpiarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLimpiarMouseEntered(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnLimpiar.setToolTipText("F1 - Limpiar todos los campos");
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseExited(evt);
            }
        });
        btnLimpiar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLimpiarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelLimpiarLayout = new javax.swing.GroupLayout(panelLimpiar);
        panelLimpiar.setLayout(panelLimpiarLayout);
        panelLimpiarLayout.setHorizontalGroup(
            panelLimpiarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelLimpiarLayout.setVerticalGroup(
            panelLimpiarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel5.add(panelLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 430, 50, 50));

        panelEliminar.setBackground(new java.awt.Color(255, 255, 255));
        panelEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEliminarMouseEntered(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnEliminar.setToolTipText("ESCAPE - Cancelar accion y salir");
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });
        btnEliminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelEliminarLayout = new javax.swing.GroupLayout(panelEliminar);
        panelEliminar.setLayout(panelEliminarLayout);
        panelEliminarLayout.setHorizontalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelEliminarLayout.setVerticalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel5.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 430, 50, 50));

        panelAceptar.setBackground(new java.awt.Color(255, 255, 255));
        panelAceptar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAceptarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelAceptarMouseEntered(evt);
            }
        });

        btnAceptar.setBackground(new java.awt.Color(153, 204, 255));
        btnAceptar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnAceptar.setToolTipText("ENTER - Guardar empleado");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptarMouseExited(evt);
            }
        });
        btnAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAceptarLayout = new javax.swing.GroupLayout(panelAceptar);
        panelAceptar.setLayout(panelAceptarLayout);
        panelAceptarLayout.setHorizontalGroup(
            panelAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelAceptarLayout.setVerticalGroup(
            panelAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel5.add(panelAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 430, 50, 50));

        labelTexto.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        labelTexto.setText("-");
        jPanel5.add(labelTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 240, 30));

        txtSaldo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtSaldo.setToolTipText("Notas de crédito generadas previamente");
        txtSaldo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldo.setEnabled(false);
        txtSaldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSaldoKeyReleased(evt);
            }
        });
        jPanel5.add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 180, -1));

        txtIva.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtIva.setToolTipText("IVA");
        txtIva.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIva.setEnabled(false);
        txtIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIvaKeyReleased(evt);
            }
        });
        jPanel5.add(txtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 180, -1));

        txtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtSubTotal.setToolTipText("Teléfono (10 digítos)");
        txtSubTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubTotal.setEnabled(false);
        txtSubTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSubTotalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSubTotalKeyTyped(evt);
            }
        });
        jPanel5.add(txtSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 180, -1));

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtTotal.setToolTipText("Total de la nota");
        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal.setEnabled(false);
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalKeyReleased(evt);
            }
        });
        jPanel5.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, 180, -1));

        txtNuevoSaldo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtNuevoSaldo.setToolTipText("Nuevo saldo en notas de crédito");
        txtNuevoSaldo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNuevoSaldo.setEnabled(false);
        txtNuevoSaldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNuevoSaldoKeyReleased(evt);
            }
        });
        jPanel5.add(txtNuevoSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, 180, -1));

        txtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtObservaciones.setToolTipText("Introduzca un comentario");
        txtObservaciones.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtObservaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtObservacionesKeyReleased(evt);
            }
        });
        jPanel5.add(txtObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 340, 180, -1));

        txtFolio.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtFolio.setForeground(new java.awt.Color(204, 0, 0));
        txtFolio.setText("-");
        jPanel5.add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 150, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Forma de pago");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 385, 110, -1));

        txtFormaPago.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFormaPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01 Efectivo", "02 Cheque nominativo", "03 Transferencia electrónica de fondos", "04 Tarjeta de crédito", "28 Tarjeta de débito" }));
        txtFormaPago.setToolTipText("Seleccione el forma de pago");
        jPanel5.add(txtFormaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, 180, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Observaciones");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 343, 85, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Notas anterior");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 283, 85, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Nuevo saldo");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 313, 85, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Subtotal");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 283, 60, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Total");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 343, 60, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("IVA");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 313, 60, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentaMouseClicked
        int fila = TableVenta.rowAtPoint(evt.getPoint());
        if (revision == false) {
            try {
                cantidadProductos vdr = new cantidadProductos(new javax.swing.JFrame(), true);
                Number precioU = formateador.parse(removefirstChar(TableVenta.getValueAt(TableVenta.getSelectedRow(), 3).toString()));
                Number subTotalU = formateador.parse(removefirstChar(TableVenta.getValueAt(TableVenta.getSelectedRow(), 4).toString()));
                vdr.vaciarProducto(tipo, Double.parseDouble(TableVenta.getValueAt(TableVenta.getSelectedRow(), 1).toString()), precioU.doubleValue(), TableVenta.getValueAt(TableVenta.getSelectedRow(), 0).toString());
                vdr.setVisible(true);
                if (vdr.accionRealizada == true) {
                    if (tipo == 3) {
                        TableVenta.setValueAt(formato.format(vdr.cantidadModificada), TableVenta.getSelectedRow(), 5);
                        TableVenta.setValueAt(formato.format(vdr.cantidadModificada * precioU.doubleValue()), TableVenta.getSelectedRow(), 6);
                    } else {
                        TableVenta.setValueAt("$" + formateador.format(vdr.cantidadModificada), TableVenta.getSelectedRow(), 5);
                        TableVenta.setValueAt("$" + formateador.format(subTotalU.doubleValue() - vdr.cantidadModificada), TableVenta.getSelectedRow(), 6);
                    }
                }

                calcularCosto();
            } catch (ParseException ex) {
                Logger.getLogger(vistaReposicionDevolucion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_TableVentaMouseClicked

    private void TableVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TableVentaMouseExited

    private void TableVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentaKeyPressed

    }//GEN-LAST:event_TableVentaKeyPressed

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        eventoF1();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void btnLimpiarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseEntered

        panelLimpiar.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnLimpiarMouseEntered

    private void btnLimpiarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseExited
        panelLimpiar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnLimpiarMouseExited

    private void btnLimpiarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER: {
                eventoEnter();
            }
            break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnLimpiarKeyPressed

    private void panelLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiarMouseClicked

    private void panelLimpiarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiarMouseEntered

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        dispose();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        panelEliminar.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        panelEliminar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER: {
                eventoEnter();
            }
            break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnEliminarKeyPressed

    private void panelEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminarMouseClicked

    private void panelEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminarMouseEntered

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        eventoEnter();
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
        panelAceptar.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnAceptarMouseEntered

    private void btnAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseExited
        panelAceptar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnAceptarMouseExited

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER: {
                eventoEnter();
            }
            break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void panelAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptarMouseClicked

    private void panelAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptarMouseEntered

    private void TableVentaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TableVentaPropertyChange

    }//GEN-LAST:event_TableVentaPropertyChange

    private void TableVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentaKeyReleased


    }//GEN-LAST:event_TableVentaKeyReleased

    private void jScrollPane1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jScrollPane1PropertyChange

    }//GEN-LAST:event_jScrollPane1PropertyChange

    private void jScrollPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrollPane1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1KeyReleased

    private void txtSaldoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtSaldoKeyReleased

    private void txtIvaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIvaKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtIvaKeyReleased

    private void txtSubTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubTotalKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtSubTotalKeyReleased

    private void txtSubTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubTotalKeyTyped

    }//GEN-LAST:event_txtSubTotalKeyTyped

    private void txtTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalKeyReleased

    private void txtNuevoSaldoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoSaldoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoSaldoKeyReleased

    private void txtObservacionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionesKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
        }
    }//GEN-LAST:event_txtObservacionesKeyReleased

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
            java.util.logging.Logger.getLogger(vistaReposicionDevolucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaReposicionDevolucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaReposicionDevolucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaReposicionDevolucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaReposicionDevolucion dialog = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnLimpiar;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTexto;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelLimpiar;
    private javax.swing.JLabel txtFolio;
    private javax.swing.JComboBox<String> txtFormaPago;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNuevoSaldo;
    private javax.swing.JTextField txtObservaciones;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JLabel txtTitulo;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

}
