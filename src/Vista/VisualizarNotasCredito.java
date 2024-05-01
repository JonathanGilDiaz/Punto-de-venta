/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Clientes;
import Modelo.ClienteDao;
import Modelo.VentasDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.Ventas;
import Modelo.notaCredito;
import Modelo.notaCreditoDao;
import static Vista.vistaClientes.isNumeric;
import com.raven.model.StatusType;
import com.raven.swing.Table2;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class VisualizarNotasCredito extends javax.swing.JDialog {

    ClienteDao client = new ClienteDao();
    DefaultTableModel modelo = new DefaultTableModel();
    notaCreditoDao notaCredito = new notaCreditoDao();
    VentasDao ventaDao = new VentasDao();
    EmpleadosDao emple = new EmpleadosDao();
    Eventos event = new Eventos();
    List<Empleados> lsE = emple.listarEmpleados();
    List<Clientes> lsC = client.ListarCliente();
    List<Ventas> todasVentas = ventaDao.listarVentas();
    List<Integer> listaFolios = new ArrayList<Integer>();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    int apoyoR;
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public VisualizarNotasCredito(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Control de notas de crédito");
        Seticon();

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableVentas.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(8).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(9).setCellRenderer(tcrDerecha);
        ((DefaultTableCellRenderer) TableVentas.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocation(200, 50);
                };

            }
        });
        listarNotas();
        controladorLista.setVisible(false);
        jList1.setVisible(false);
        spTable.getViewport().setBackground(new Color(204, 204, 204));
        TableVentas.setBackground(Color.WHITE);
        /*         
  spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        jTextField1.requestFocus();
         */
    }

    public void LimpiarTabla() {
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

    public void listarNotasPorNombre(int idCliente) {
        List<notaCredito> lsVentas = notaCredito.listarNotas();
        List<notaCredito> lsVentasFin = new ArrayList<notaCredito>();
        for (int i = 0; i < lsVentas.size(); i++) {
            for (int k = 0; k < todasVentas.size(); k++) {
                System.out.println(lsVentas.size() + "       " + todasVentas.size());
                if (lsVentas.get(i).getFolioVenta() == todasVentas.get(k).getFolio()) {
                    if (todasVentas.get(k).getIdEmpleado() != idCliente) {
                        lsVentasFin.add(lsVentas.get(i));
                    }
                }
            }
        }

        vaciarATabla(lsVentasFin);
    }

    public void listarNotas() {//Metodo para vaciar las notas en su respectiva tabla
        List<notaCredito> lsVentas = notaCredito.listarNotas();
        vaciarATabla(lsVentas);
    }

    public void buscarPorFolioTabla(int folio) {
        List<notaCredito> lsVentas = notaCredito.listarNotasSoloUna(folio);
        vaciarATabla(lsVentas);
    }

    public void listarPorPeriodoDeFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<notaCredito> lsVentas = notaCredito.listarNotasPeriodo(sdf.format(jDateChooser1.getDate()), sdf.format(jDateChooser2.getDate()));
        vaciarATabla(lsVentas);
    }

    public void vaciarATabla(List<notaCredito> lsVentas) {
        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[10];
        for (int i = lsVentas.size() - 1; i >= 0; i--) {//las vaciamos de la mas reciente a la mas vieja
            ob[0] = fechaFormatoCorrecto(lsVentas.get(i).getFecha());
            ob[1] = "E" + lsVentas.get(i).getId();
            ob[2] = "F" + lsVentas.get(i).getFolioVenta();
            String nombreCliente = "";
            for (int k = 0; k < todasVentas.size(); k++) {
                if (lsVentas.get(i).getFolioVenta() == todasVentas.get(k).getFolio()) {
                    for (int m = 0; m < lsC.size(); m++) {
                        if (lsC.get(m).getId() == todasVentas.get(k).getIdCliente()) {
                            if (lsC.get(m).getTipoPersona().equals("Persona Física")) {
                                nombreCliente = lsC.get(m).getNombre() + " " + lsC.get(m).getApellidoP() + " " + lsC.get(m).getApellidoM();
                            } else {
                                nombreCliente = lsC.get(m).getNombreComercial();
                            }
                        }
                    }
                }
            }
            System.out.println(nombreCliente + "aqui");
            ob[3] = nombreCliente;
            ob[4] = "$" + formateador.format(lsVentas.get(i).getSaldoViejo());

            if (lsVentas.get(i).getTipo().equals("Cancelación")) {
                ob[5] = StatusType.CANCELACION;
            }
            if (lsVentas.get(i).getTipo().equals("Devolución")) {
                ob[5] = StatusType.DEVOLUCION;
            }
            if (lsVentas.get(i).getTipo().equals("Bonificación")) {
                ob[5] = StatusType.BONIFICACION;
            }
            if (lsVentas.get(i).getTipo().equals("Descuento")) {
                ob[5] = StatusType.DESCUENTO;
            }

            ob[6] = "$" + formateador.format(lsVentas.get(i).getSubtotal());
            ob[7] = "$" + formateador.format(lsVentas.get(i).getIva());
            ob[8] = "$" + formateador.format(lsVentas.get(i).getTotal());
            ob[9] = "$" + formateador.format(lsVentas.get(i).getNuevoSaldo());

            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);

    }

    private static boolean isSubstring(String s, String seq) {
        return Pattern.compile(Pattern.quote(seq), Pattern.CASE_INSENSITIVE)
                .matcher(s).find();
    }

    public void eventoF1() {
        vistaVenta vC1 = new vistaVenta(new javax.swing.JFrame(), true);
        vC1.setLocation(200, 80);
        vC1.validandoDatos(0, 1);
        vC1.setVisible(true);
    }

    public void eventoF2() {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.raven.datechooser.DateChooser();
        jDateChooser2 = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        controladorLista = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new textfield.TextField();
        primeraFecha = new textfield.TextField();
        segundaFecha = new textfield.TextField();
        jTextField2 = new textfield.TextField();
        txtBuscarVenta = new textfield.TextField();
        jPanel4 = new javax.swing.JPanel();
        spTable = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();

        jDateChooser1.setForeground(new java.awt.Color(51, 102, 255));
        jDateChooser1.setTextRefernce(primeraFecha);
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jDateChooser2.setForeground(new java.awt.Color(51, 102, 255));
        jDateChooser2.setTextRefernce(segundaFecha);
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NOTAS DE CRÉDITO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(644, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 1050, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        controladorLista.setViewportView(jList1);

        jPanel2.add(controladorLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 240, 10));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("     Buscar por fecha");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 130, 20));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar por nota de crédito");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 230, 40));

        primeraFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        primeraFecha.setLabelText("Fecha de inicio");
        primeraFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                primeraFechaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                primeraFechaKeyReleased(evt);
            }
        });
        jPanel2.add(primeraFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 130, 40));

        segundaFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        segundaFecha.setLabelText("Fecha de fin");
        segundaFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                segundaFechaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                segundaFechaKeyReleased(evt);
            }
        });
        jPanel2.add(segundaFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, 130, 40));

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField2.setLabelText("Buscar nota");
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 240, 40));

        txtBuscarVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBuscarVenta.setLabelText("Buscar por venta");
        txtBuscarVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtBuscarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 230, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 1030, 70));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVentas = new Table2();
        TableVentas.setBackground(new java.awt.Color(204, 204, 255));
        TableVentas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Folio", "Venta", "Cliente", "Saldo", "Tipo", "SubTotal", "IVA", "Total", "S. Restante"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVentas.setToolTipText("Haga doble click para realizar alguna acción");
        TableVentas.setFocusable(false);
        TableVentas.setGridColor(new java.awt.Color(255, 255, 255));
        TableVentas.setOpaque(false);
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TableVentasMouseExited(evt);
            }
        });
        TableVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableVentasKeyPressed(evt);
            }
        });
        spTable.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(10);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(10);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(140);
            TableVentas.getColumnModel().getColumn(4).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(5).setPreferredWidth(35);
            TableVentas.getColumnModel().getColumn(6).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(7).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(8).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(9).setPreferredWidth(20);
        }

        jPanel4.add(spTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1010, 460));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 1030, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1044, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (evt.getClickCount() == 2) {
            List<Clientes> lista = client.buscarLetra(jTextField1.getText());
            int k = jList1.getSelectedIndex();
            jTextField1.setText(jList1.getSelectedValue());
            jList1.setVisible(false);
            LimpiarTabla();
            listarNotasPorNombre(lista.get(k).getId());
            apoyoR = lista.get(k).getId();
            controladorLista.setVisible(false);

            jList1.setVisible(false);
            jPanel2.setBounds(jPanel2.getX(), jPanel2.getY(), jPanel2.getWidth(), 70);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        jDateChooser2.setMinSelectableDate(jDateChooser1.getDate());
        jDateChooser2.setSelectedDate(jDateChooser1.getDate());
        if (jDateChooser1.getDate() != null) {
            try {
                LimpiarTabla();
                listarPorPeriodoDeFecha();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ALGO SUCEDIO MAL, PRUEBA OTRA FECHA" + e);
            }
        }
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if (jDateChooser2.getDate() != null) {
            try {
                LimpiarTabla();
                listarPorPeriodoDeFecha();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ALGO SUCEDIO MAL, PRUEBA OTRA VEZ" + e);
            }
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if ("".equals(jTextField1.getText())) {
            controladorLista.setVisible(false);
            jList1.setVisible(false);
            jPanel2.setBounds(jPanel2.getX(), jPanel2.getY(), jPanel2.getWidth(), 70);
            LimpiarTabla();
            listarNotas();
        } else {
            if (isNumeric(jTextField1.getText()) == true) {
                notaCredito n = notaCredito.buscarPorId(Integer.parseInt(jTextField1.getText()));
                LimpiarTabla();
                buscarPorFolioTabla(Integer.parseInt(jTextField1.getText()));

            } else {

                DefaultListModel model = new DefaultListModel();
                controladorLista.setVisible(true);
                jList1.setVisible(true);
                jPanel2.setBounds(jPanel2.getX(), jPanel2.getY(), jPanel2.getWidth(), 145);
                controladorLista.setBounds(controladorLista.getX(), controladorLista.getY(), controladorLista.getWidth(), 85);
                jList1.setBounds(jList1.getX(), jList1.getY(), jList1.getWidth(), 85);
                List<Clientes> lista = client.buscarLetra(jTextField1.getText());
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getEstatus() == 1) {
                        if (lista.get(i).getTipoPersona().equals("Persona Física")) {
                            model.addElement(lista.get(i).getNombre() + " " + lista.get(i).getApellidoP() + " " + lista.get(i).getApellidoM());
                        } else {
                            model.addElement(lista.get(i).getNombreComercial());
                        }
                    }
                }
                jList1.setModel(model);
            }
        }

    }//GEN-LAST:event_jTextField1KeyReleased

    private void primeraFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primeraFechaKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
        }
    }//GEN-LAST:event_primeraFechaKeyPressed

    private void primeraFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primeraFechaKeyReleased

    }//GEN-LAST:event_primeraFechaKeyReleased

    private void segundaFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaFechaKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
        }
    }//GEN-LAST:event_segundaFechaKeyPressed

    private void segundaFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaFechaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_segundaFechaKeyReleased

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyReleased

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = TableVentas.rowAtPoint(evt.getPoint());
            notaCredito miNota = notaCredito.buscarPorId(Integer.parseInt(TableVentas.getValueAt(fila, 1).toString().substring(1)));

            if (miNota.getTipo().equals("Cancelación")) {
                vistaCancelar cc = new vistaCancelar(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(e.getId());
                cc.vaciarDatos(new Ventas(), "", 0, false, miNota.getId());
                cc.setVisible(true);
            }
            if (miNota.getTipo().equals("Devolución")) {
                vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                vdr.vaciarEmpleado(e.getId());
                vdr.validandoDatos(3, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 2).toString().substring(1)), true, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 1).toString().substring(1)));
                vdr.setVisible(true);
            }
            if (miNota.getTipo().equals("Bonificación")) {
                vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                vdr.vaciarEmpleado(e.getId());
                vdr.validandoDatos(2, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 2).toString().substring(1)), true, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 1).toString().substring(1)));
                vdr.setVisible(true);
            }
            if (miNota.getTipo().equals("Descuento")) {
                vistaReposicionDevolucion vdr = new vistaReposicionDevolucion(new javax.swing.JFrame(), true);
                vdr.vaciarEmpleado(e.getId());
                vdr.validandoDatos(1, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 2).toString().substring(1)), true, Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 1).toString().substring(1)));
                vdr.setVisible(true);
            }
        }

    }//GEN-LAST:event_TableVentasMouseClicked

    private void TableVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TableVentasMouseExited

    private void TableVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentasKeyPressed

    }//GEN-LAST:event_TableVentasKeyPressed

    private void txtBuscarVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarVentaKeyPressed

    private void txtBuscarVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarVentaKeyReleased
        if (!"".equals(txtBuscarVenta.getText())) {
            List<notaCredito> lsNota = notaCredito.listarNotasPorVenta(Integer.parseInt(txtBuscarVenta.getText()));
            LimpiarTabla();
            vaciarATabla(lsNota);
        } else {
            LimpiarTabla();
            listarNotas();
        }
    }//GEN-LAST:event_txtBuscarVentaKeyReleased

    private void txtBuscarVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtBuscarVentaKeyTyped

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
            java.util.logging.Logger.getLogger(VisualizarNotasCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisualizarNotasCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisualizarNotasCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisualizarNotasCredito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VisualizarNotasCredito dialog = new VisualizarNotasCredito(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableVentas;
    private javax.swing.JScrollPane controladorLista;
    private com.raven.datechooser.DateChooser jDateChooser1;
    private com.raven.datechooser.DateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private textfield.TextField jTextField1;
    private textfield.TextField jTextField2;
    private textfield.TextField primeraFecha;
    private textfield.TextField segundaFecha;
    private javax.swing.JScrollPane spTable;
    private textfield.TextField txtBuscarVenta;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    public static void quicksort(List<Ventas> ventas, int low, int high) {
        if (low < high) {
            int pi = partition(ventas, low, high);
            quicksort(ventas, low, pi - 1);
            quicksort(ventas, pi + 1, high);
        }
    }

    public static int partition(List<Ventas> ventas, int low, int high) {
        Ventas pivot = ventas.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (ventas.get(j).getFecha().compareTo(pivot.getFecha()) > 0 || (ventas.get(j).getFecha().compareTo(pivot.getFecha()) == 0 && ventas.get(j).getFolio() > pivot.getFolio())) {
                i++;
                swap(ventas, i, j);
            } else if (ventas.get(j).getFecha().compareTo(pivot.getFecha()) == 0 && ventas.get(j).getFolio() == pivot.getFolio()) {
                if (ventas.get(j).getHora().equals("VENTAS")) {
                    i++;
                    swap(ventas, i, j);
                }
            }
        }

        swap(ventas, i + 1, high);

        return i + 1;
    }

    public static void swap(List<Ventas> ventas, int i, int j) {
        Ventas temp = ventas.get(i);
        ventas.set(i, ventas.get(j));
        ventas.set(j, temp);
    }

}
