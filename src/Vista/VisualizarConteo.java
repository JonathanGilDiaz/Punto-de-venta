/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Clientes;
import Modelo.ClienteDao;
import Modelo.CotizacionDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Ventas;
import Modelo.conteoInventario;
import Modelo.conteoInventarioDao;
import com.raven.swing.Table3;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class VisualizarConteo extends javax.swing.JDialog {

    ClienteDao client = new ClienteDao();
    DefaultTableModel modelo = new DefaultTableModel();
    CotizacionDao ventaDao = new CotizacionDao();
    EmpleadosDao emple = new EmpleadosDao();
    conteoInventarioDao conteoDao = new conteoInventarioDao();
    List<Empleados> lsE = emple.listarEmpleados();
    List<Clientes> lsC = client.ListarCliente();
    List<Integer> listaFolios = new ArrayList<Integer>();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat formato = new DecimalFormat("0.00");
    int apoyoR;
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public VisualizarConteo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Visualizar conteo de inventario");
        Seticon();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocation(200, 50);
                };

            }
        });
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableConteo.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableConteo.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableConteo.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableConteo.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableConteo.getColumnModel().getColumn(4).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableConteo.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        listarConteos();
        spTable.getViewport().setBackground(new Color(204, 204, 204));
        TableConteo.setBackground(Color.WHITE);

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

    public void listarConteos() {//Metodo para vaciar las notas en su respectiva tabla
        List<conteoInventario> lsConteo = conteoDao.listarConteos();
        vaciarATabla(lsConteo);
    }

    public void listarPorPeriodoDeFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<conteoInventario> lsConteo = conteoDao.listarConteosPeriodo(sdf.format(jDateChooser1.getDate()), sdf.format(jDateChooser2.getDate()));
        vaciarATabla(lsConteo);
    }

    public void vaciarATabla(List<conteoInventario> lsConteo) {
        modelo = (DefaultTableModel) TableConteo.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < lsConteo.size(); i++) {//las vaciamos de la mas reciente a la mas vieja
            ob[0] = fechaFormatoCorrecto(lsConteo.get(i).getFecha());
            ob[1] = lsConteo.get(i).getId();
            for (int k = 0; k < lsE.size(); k++) {
                if (lsE.get(k).getId() == lsConteo.get(i).getIdEmpleado()) {
                    ob[2] = lsE.get(k).getNombre();
                }
            }
            ob[3] = "$" + formateador.format(lsConteo.get(i).getDinero());
            ob[4] = lsConteo.get(i).getObservaciones();
            modelo.addRow(ob);
        }
        TableConteo.setModel(modelo);
    }

    private static boolean isSubstring(String s, String seq) {
        return Pattern.compile(Pattern.quote(seq), Pattern.CASE_INSENSITIVE)
                .matcher(s).find();
    }

    public void eventoF1() {
        vistaContarInventario vC1 = new vistaContarInventario(new javax.swing.JFrame(), true);
        vC1.vaciarEmpleado(e.getId());
        vC1.setLocation(200, 115);
        vC1.validar(true, 0);
        vC1.setVisible(true);
        LimpiarTabla();
        listarConteos();
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
        jLabel11 = new javax.swing.JLabel();
        primeraFecha = new textfield.TextField();
        segundaFecha = new textfield.TextField();
        jPanel4 = new javax.swing.JPanel();
        spTable = new javax.swing.JScrollPane();
        TableConteo = new javax.swing.JTable();
        panelNuevaNota = new javax.swing.JPanel();
        btnNota = new javax.swing.JLabel();

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
        jLabel1.setText("VISUALIZAR CONTEO DE INVENTARIO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(403, Short.MAX_VALUE))
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

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("     Buscar por fecha");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 130, 20));

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
        jPanel2.add(primeraFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 130, 40));

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
        jPanel2.add(segundaFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 130, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 1030, 70));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableConteo = new Table3();
        TableConteo.setBackground(new java.awt.Color(204, 204, 255));
        TableConteo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableConteo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Código", "Usuario", "Diferencia ($)", "Observaciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableConteo.setToolTipText("Haga doble click para realizar alguna acción");
        TableConteo.setFocusable(false);
        TableConteo.setGridColor(new java.awt.Color(255, 255, 255));
        TableConteo.setOpaque(false);
        TableConteo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableConteoMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TableConteoMouseExited(evt);
            }
        });
        TableConteo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableConteoKeyPressed(evt);
            }
        });
        spTable.setViewportView(TableConteo);
        if (TableConteo.getColumnModel().getColumnCount() > 0) {
            TableConteo.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableConteo.getColumnModel().getColumn(1).setPreferredWidth(10);
            TableConteo.getColumnModel().getColumn(2).setPreferredWidth(50);
            TableConteo.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableConteo.getColumnModel().getColumn(4).setPreferredWidth(170);
        }

        jPanel4.add(spTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1010, 400));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 1030, 420));

        panelNuevaNota.setBackground(new java.awt.Color(255, 255, 255));
        panelNuevaNota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelNuevaNota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelNuevaNotaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelNuevaNotaMouseEntered(evt);
            }
        });

        btnNota.setBackground(new java.awt.Color(153, 204, 255));
        btnNota.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnNota.setForeground(new java.awt.Color(255, 255, 255));
        btnNota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnNota.setToolTipText("F1 - Nueva nota");
        btnNota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNotaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNotaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNotaMouseExited(evt);
            }
        });
        btnNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNotaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelNuevaNotaLayout = new javax.swing.GroupLayout(panelNuevaNota);
        panelNuevaNota.setLayout(panelNuevaNotaLayout);
        panelNuevaNotaLayout.setHorizontalGroup(
            panelNuevaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNota, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelNuevaNotaLayout.setVerticalGroup(
            panelNuevaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelNuevaNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 50, 50));

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnNotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotaMouseClicked
        eventoF1();
    }//GEN-LAST:event_btnNotaMouseClicked

    private void btnNotaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotaMouseEntered
        panelNuevaNota.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnNotaMouseEntered

    private void btnNotaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotaMouseExited
        panelNuevaNota.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnNotaMouseExited

    private void btnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNotaKeyPressed
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
    }//GEN-LAST:event_btnNotaKeyPressed

    private void panelNuevaNotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevaNotaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevaNotaMouseClicked

    private void panelNuevaNotaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevaNotaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevaNotaMouseEntered

    private void TableConteoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableConteoMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = TableConteo.rowAtPoint(evt.getPoint());
            vistaContarInventario vC1 = new vistaContarInventario(new javax.swing.JFrame(), true);
            vC1.vaciarEmpleado(e.getId());
            vC1.setLocation(200, 115);
            vC1.validar(false, Integer.parseInt(TableConteo.getValueAt(fila, 1).toString()));
            vC1.setVisible(true);
        }
    }//GEN-LAST:event_TableConteoMouseClicked

    private void TableConteoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableConteoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TableConteoMouseExited

    private void TableConteoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableConteoKeyPressed

    }//GEN-LAST:event_TableConteoKeyPressed

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
            java.util.logging.Logger.getLogger(VisualizarConteo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisualizarConteo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisualizarConteo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisualizarConteo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                VisualizarConteo dialog = new VisualizarConteo(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableConteo;
    private javax.swing.JLabel btnNota;
    private com.raven.datechooser.DateChooser jDateChooser1;
    private com.raven.datechooser.DateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel panelNuevaNota;
    private textfield.TextField primeraFecha;
    private textfield.TextField segundaFecha;
    private javax.swing.JScrollPane spTable;
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
