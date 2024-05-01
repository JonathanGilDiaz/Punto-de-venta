/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ClienteDao;
import Modelo.Clientes;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.HistorialCliente;
import Modelo.HistorialClienteDao;
import Modelo.Ventas;
import Modelo.imprimiendo;
import Modelo.notaCredito;
import Modelo.notaCreditoDao;
import com.raven.swing.Table3;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javaswingdev.Notification;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class vistaHistorialCliente extends javax.swing.JDialog {

    HistorialClienteDao historial = new HistorialClienteDao();
    DefaultTableModel modelo = new DefaultTableModel();
    EmpleadosDao emple = new EmpleadosDao();
    ClienteDao clienteDao = new ClienteDao();
    CreditoDao creditoDao = new CreditoDao();
    notaCreditoDao notaCreditodao = new notaCreditoDao();

    Clientes c;
    String fecha, hora;
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat df = new DecimalFormat("$ #,##0.00;($ #,##0.00)");
    List<Empleados> lsEmpleados = emple.listarEmpleados();

    int fila = -1;
    int idEmpleado;

    public void vaciarEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public vistaHistorialCliente(java.awt.Frame parent, boolean modal) {
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
        this.setTitle("Punto de venta - Historial de cliente");
        Seticon();
        jScrollPane2.getViewport().setBackground(new Color(204, 204, 204));
        TableHistorial.getTableHeader().setBackground(Color.blue);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TableHistorial.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableHistorial.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableHistorial.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableHistorial.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableHistorial.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableHistorial.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TableHistorial.getColumnModel().getColumn(6).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableHistorial.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        panelImprimir.setBackground(new Color(179, 195, 219));
        panelReporte.setBackground(new Color(179, 195, 219));

    }

    public void vaciarDatos(Clientes c) {
        this.c = c;
        listarHistorial();
    }

    public void listarHistorial() {
        List<HistorialCliente> ListaCl = historial.retornarMovimientos(c.getId());
        vaciarTabla(ListaCl);
    }

    public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private static boolean isSubstring(String s, String seq) {
        return Pattern.compile(Pattern.quote(seq), Pattern.CASE_INSENSITIVE)
                .matcher(s).find();
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

    public void eventoF1() {
        if (fila != -1) {
            char letra = TableHistorial.getValueAt(fila, 1).toString().charAt(0);
            if (letra == 'P') {
                try {
                    pagarAdeudo vdr = new pagarAdeudo(new javax.swing.JFrame(), true);
                    vdr.ticketPagos(Integer.parseInt(TableHistorial.getValueAt(TableHistorial.getSelectedRow(), 1).toString().substring(1)));
                    File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\Tickets\\pago" + Integer.parseInt(TableHistorial.getValueAt(TableHistorial.getSelectedRow(), 1).toString().substring(1)) + ".pdf");
                    Desktop.getDesktop().open(file);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (ParseException ex) {
                    Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Seleccione un pago");
                panel.showNotification();
            }
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
            panel.showNotification();
        }
    }

    public void vaciarTabla(List<HistorialCliente> ListaCl) {
        modelo = (DefaultTableModel) TableHistorial.getModel();
        Object[] ob = new Object[7];
        for (int i = ListaCl.size() - 1; i >= 0; i--) {
            ob[0] = fechaFormatoCorrecto(ListaCl.get(i).getFecha());
            ob[1] = ListaCl.get(i).getFolio();
            ob[2] = ListaCl.get(i).getMovimiento();
            if (ListaCl.get(i).getCargo() == 0) {
                ob[3] = "-";
            } else {
                ob[3] = "$" + formateador.format(ListaCl.get(i).getCargo());
            }
            if (ListaCl.get(i).getAbono() == 0) {
                ob[4] = "-";
            } else {
                ob[4] = "$" + formateador.format(ListaCl.get(i).getAbono());
            }
            ob[5] = "$" + formateador.format(ListaCl.get(i).getSaldo());
            Empleados e = new Empleados();
            for (int j = 0; j < lsEmpleados.size(); j++) {
                if (lsEmpleados.get(j).getId() == ListaCl.get(i).getIdRecibe()) {
                    e = lsEmpleados.get(j);
                }
            }
            ob[6] = e.getNombre();

            modelo.addRow(ob);
        }
        TableHistorial.setModel(modelo);
        Credito cre = creditoDao.BuscarPorCodigoCliente(c.getId());
        txtLimite.setText("$" + formateador.format(cre.getLimite()));
        txtAdeudo.setText("$" + formateador.format(cre.getAdeudo()));
    }

    public void eventoF2() {
        if (fila != -1) {
            char letra = TableHistorial.getValueAt(fila, 1).toString().charAt(0);
            if (letra == 'P') {
                try {
                    pagarAdeudo vdr = new pagarAdeudo(new javax.swing.JFrame(), true);
                    vdr.ticketPagos(Integer.parseInt(TableHistorial.getValueAt(TableHistorial.getSelectedRow(), 1).toString().substring(1)));
                    File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\Tickets\\pago" + Integer.parseInt(TableHistorial.getValueAt(TableHistorial.getSelectedRow(), 1).toString().substring(1)) + ".pdf");
                    imprimiendo m = new imprimiendo();
                    if (file.isFile()) {
                        m.imprimir(file);
                    } else {
                        Notification panel2 = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Error inesperado");
                        panel2.showNotification();
                    }
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(null, "Error de impresion", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());

                } catch (ParseException ex) {
                    Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Seleccione un pago");
                panel.showNotification();
            }
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
            panel.showNotification();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableHistorial = new javax.swing.JTable();
        panelImprimir = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JLabel();
        panelReporte = new javax.swing.JPanel();
        btnReporte = new javax.swing.JLabel();
        txtLimite = new textfield.TextField();
        txtAdeudo = new textfield.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CARTERA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(667, 667, 667))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1023, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableHistorial = new Table3();
        TableHistorial.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Folio", "Movimiento", "Cargo", "Abono", "Saldo", "Encargado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableHistorial.setToolTipText("Haz doble click para visualizar");
        TableHistorial.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableHistorialMouseClicked(evt);
            }
        });
        TableHistorial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableHistorialKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableHistorialKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(TableHistorial);
        if (TableHistorial.getColumnModel().getColumnCount() > 0) {
            TableHistorial.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableHistorial.getColumnModel().getColumn(1).setPreferredWidth(35);
            TableHistorial.getColumnModel().getColumn(2).setPreferredWidth(150);
            TableHistorial.getColumnModel().getColumn(3).setPreferredWidth(30);
            TableHistorial.getColumnModel().getColumn(4).setPreferredWidth(30);
            TableHistorial.getColumnModel().getColumn(5).setPreferredWidth(30);
            TableHistorial.getColumnModel().getColumn(6).setPreferredWidth(10);
        }

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 980, 430));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 1003, 450));

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
        btnImprimir.setToolTipText("F2 - Imprimir comprobante de pago");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImprimirMouseClicked(evt);
                btnMOdificarMouseClicked2(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnImprimirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnImprimirMouseExited(evt);
            }
        });
        btnImprimir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnImprimirKeyPressed(evt);
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

        jPanel1.add(panelImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 50, 50));

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

        btnReporte.setBackground(new java.awt.Color(255, 255, 255));
        btnReporte.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen1.png"))); // NOI18N
        btnReporte.setToolTipText("F1 - Visualizar pago");
        btnReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        btnReporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnReporteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelReporteLayout = new javax.swing.GroupLayout(panelReporte);
        panelReporte.setLayout(panelReporteLayout);
        panelReporteLayout.setHorizontalGroup(
            panelReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReporte, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelReporteLayout.setVerticalGroup(
            panelReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReporte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, 50));

        txtLimite.setToolTipText("Límite de crédito permitido");
        txtLimite.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtLimite.setEnabled(false);
        txtLimite.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtLimite.setLabelText("Límite de crédito");
        txtLimite.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLimiteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLimiteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLimiteKeyTyped(evt);
            }
        });
        jPanel1.add(txtLimite, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 60, 170, -1));

        txtAdeudo.setToolTipText("Adeudo total de cliente");
        txtAdeudo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAdeudo.setEnabled(false);
        txtAdeudo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtAdeudo.setLabelText("Adeudo");
        txtAdeudo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdeudoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAdeudoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAdeudoKeyTyped(evt);
            }
        });
        jPanel1.add(txtAdeudo, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 60, 170, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableHistorialMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TableHistorial.rowAtPoint(evt.getPoint());
            char letra = TableHistorial.getValueAt(fila, 1).toString().charAt(0);
            if (letra == 'F') {
                int buscando = Integer.parseInt(TableHistorial.getValueAt(fila, 1).toString().substring(1));
                vistaVenta vN = new vistaVenta(new javax.swing.JFrame(), true);
                vN.vaciarEmpleado(idEmpleado);
                vN.validandoDatos(buscando, 2);
                vN.setVisible(true);
                fila = -1;
                panelImprimir.setBackground(new Color(179, 195, 219));
                panelReporte.setBackground(new Color(179, 195, 219));
                LimpiarTabla();
                listarHistorial();
            } else {
                if (letra == 'E') {
                    notaCredito miNota = notaCreditodao.buscarPorId(Integer.parseInt(TableHistorial.getValueAt(fila, 1).toString().substring(1)));

                    if (miNota.getTipo().equals("Cancelación")) {
                        vistaCancelar cc = new vistaCancelar(new javax.swing.JFrame(), true);
                        cc.vaciarEmpleado(idEmpleado);
                        cc.vaciarDatos(new Ventas(), "", 0, false, miNota.getId());
                        cc.setVisible(true);
                    }
                    if (miNota.getTipo().equals("Devolución")) {
                        vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                        vdr.vaciarEmpleado(idEmpleado);
                        vdr.validandoDatos(3, miNota.getFolioVenta(), true, Integer.parseInt(TableHistorial.getValueAt(TableHistorial.getSelectedRow(), 1).toString().substring(1)));
                        vdr.setVisible(true);
                    }
                    if (miNota.getTipo().equals("Bonificación")) {
                        vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                        vdr.vaciarEmpleado(idEmpleado);
                        vdr.validandoDatos(2, miNota.getFolioVenta(), true, Integer.parseInt(TableHistorial.getValueAt(TableHistorial.getSelectedRow(), 1).toString().substring(1)));
                        vdr.setVisible(true);
                    }
                    if (miNota.getTipo().equals("Descuento")) {
                        vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                        vdr.vaciarEmpleado(idEmpleado);
                        vdr.validandoDatos(1, miNota.getFolioVenta(), true, Integer.parseInt(TableHistorial.getValueAt(TableHistorial.getSelectedRow(), 1).toString().substring(1)));
                        vdr.setVisible(true);
                    }
                    fila = -1;
                    panelImprimir.setBackground(new Color(179, 195, 219));
                    panelReporte.setBackground(new Color(179, 195, 219));
                    LimpiarTabla();
                    listarHistorial();
                } else {
                    if (letra == '-') {
                        fila = -1;
                        panelImprimir.setBackground(new Color(179, 195, 219));
                        panelReporte.setBackground(new Color(179, 195, 219));
                    } else {
                        panelImprimir.setBackground(new Color(255, 255, 255));
                        panelReporte.setBackground(new Color(255, 255, 255));
                    }
                }
            }
        }
    }//GEN-LAST:event_TableHistorialMouseClicked

    private void TableHistorialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableHistorialKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_TableHistorialKeyPressed

    private void btnImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseClicked
    }//GEN-LAST:event_btnImprimirMouseClicked

    private void btnImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseEntered
        panelImprimir.setBackground(new Color(179, 195, 219));
        if (fila != -1) {
            panelImprimir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelImprimir.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnImprimirMouseEntered

    private void btnImprimirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseExited
        if (fila != -1) {
            panelImprimir.setBackground(new Color(255, 255, 255));
            btnImprimir.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelImprimir.setBackground(new Color(179, 195, 219));
            btnImprimir.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnImprimirMouseExited

    private void btnImprimirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnImprimirKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnImprimirKeyPressed

    private void panelImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelImprimirMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelImprimirMouseClicked

    private void panelImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelImprimirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelImprimirMouseEntered

    private void btnReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseClicked
        eventoF1();
    }//GEN-LAST:event_btnReporteMouseClicked

    private void btnReporteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseEntered
        panelReporte.setBackground(new Color(179, 195, 219));
        if (fila != -1) {
            panelReporte.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnReporteMouseEntered

    private void btnReporteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseExited
        if (fila != -1) {
            panelReporte.setBackground(new Color(255, 255, 255));
            btnReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelReporte.setBackground(new Color(179, 195, 219));
            btnReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnReporteMouseExited

    private void btnReporteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnReporteKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnReporteKeyPressed

    private void panelReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReporteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelReporteMouseClicked

    private void panelReporteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReporteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelReporteMouseEntered

    private void txtLimiteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLimiteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLimiteKeyPressed

    private void txtLimiteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLimiteKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;
        }
    }//GEN-LAST:event_txtLimiteKeyReleased

    private void txtLimiteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLimiteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLimiteKeyTyped

    private void txtAdeudoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdeudoKeyPressed

    }//GEN-LAST:event_txtAdeudoKeyPressed

    private void txtAdeudoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdeudoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;
        }
    }//GEN-LAST:event_txtAdeudoKeyReleased

    private void txtAdeudoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdeudoKeyTyped

    }//GEN-LAST:event_txtAdeudoKeyTyped

    private void TableHistorialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableHistorialKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;
        }
    }//GEN-LAST:event_TableHistorialKeyReleased

    private void btnMOdificarMouseClicked2(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMOdificarMouseClicked2
        eventoF2();
    }//GEN-LAST:event_btnMOdificarMouseClicked2

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
            java.util.logging.Logger.getLogger(HistorialCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistorialCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistorialCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistorialCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaHistorialCliente dialog = new vistaHistorialCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableHistorial;
    private javax.swing.JLabel btnImprimir;
    private javax.swing.JLabel btnReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelImprimir;
    private javax.swing.JPanel panelReporte;
    private textfield.TextField txtAdeudo;
    private textfield.TextField txtLimite;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    private static CellStyle getContabilidadCellStyle(Workbook workbook, DecimalFormat df) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat(df.format(0)));
        return style;
    }

}
