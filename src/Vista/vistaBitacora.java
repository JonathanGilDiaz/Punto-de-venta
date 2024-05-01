/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Bitacora;
import Modelo.EmpleadosDao;
import Modelo.bitacoraDao;
import com.raven.swing.Table2;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class vistaBitacora extends javax.swing.JDialog {

    bitacoraDao bitacora = new bitacoraDao();
    DefaultTableModel modelo = new DefaultTableModel();
    EmpleadosDao emple = new EmpleadosDao();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    int fila = -1;
    String fecha, hora;

    public vistaBitacora(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Bitácora");
        Seticon();
        TabkeBitacora.setBackground(Color.WHITE);

        jScrollPane8.getViewport().setBackground(new Color(204, 204, 204));
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        TabkeBitacora.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TabkeBitacora.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TabkeBitacora.getColumnModel().getColumn(2).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TabkeBitacora.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        listarRegistros();
        jTextField1.requestFocus();
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

        // Formatear la fecha como una cadena en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fecha = fechaSQL.toLocalDate().format(formatter);

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

    public void listarRegistros() {
        List<Bitacora> ListaCl = bitacora.listarRegistros();
        modelo = (DefaultTableModel) TabkeBitacora.getModel();
        Object[] ob = new Object[3];
        for (int i = ListaCl.size() - 1; i >= 0; i--) {//las vaciamos de la mas reciente a la mas vieja
            ob[0] = fechaCorrecta(ListaCl.get(i).getFecha());
            ob[1] = ListaCl.get(i).getAccion();
            ob[2] = ListaCl.get(i).getIdRecibe();

            modelo.addRow(ob);
        }
        TabkeBitacora.setModel(modelo);
    }

    public String fechaCorrecta(String fechaMal) {
        Date diaActual = StringADate(fechaMal);
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

    public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void listarPorPeriodoDeFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Bitacora> lsBitacora = bitacora.listarPeriodo(sdf.format(jDateChooser1.getDate()), sdf.format(jDateChooser2.getDate()));
        modelo = (DefaultTableModel) TabkeBitacora.getModel();
        Object[] ob = new Object[3];
        for (int i = lsBitacora.size() - 1; i >= 0; i--) {//las vaciamos de la mas reciente a la mas vieja
            ob[0] = fechaCorrecta(lsBitacora.get(i).getFecha());
            ob[1] = lsBitacora.get(i).getAccion();
            ob[2] = lsBitacora.get(i).getIdRecibe();

            modelo.addRow(ob);
        }
        TabkeBitacora.setModel(modelo);

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
        jPanel4 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TabkeBitacora = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new textfield.TextField();
        primeraFecha = new textfield.TextField();
        segundaFecha = new textfield.TextField();

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
        jLabel1.setText("BITÁCORA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(667, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 960, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TabkeBitacora = new Table2();
        TabkeBitacora.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Acción", "Realizó"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabkeBitacora.setToolTipText("");
        TabkeBitacora.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TabkeBitacora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabkeBitacoraMouseClicked(evt);
            }
        });
        TabkeBitacora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TabkeBitacoraKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(TabkeBitacora);
        if (TabkeBitacora.getColumnModel().getColumnCount() > 0) {
            TabkeBitacora.getColumnModel().getColumn(0).setPreferredWidth(15);
            TabkeBitacora.getColumnModel().getColumn(1).setPreferredWidth(500);
            TabkeBitacora.getColumnModel().getColumn(2).setPreferredWidth(15);
        }

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 450));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 940, 470));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("     Buscar por fecha");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 5, 130, 20));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar acción");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 15, 450, 40));

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
        jPanel2.add(primeraFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 25, 130, 40));

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
        jPanel2.add(segundaFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 25, 130, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 940, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TabkeBitacoraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabkeBitacoraMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TabkeBitacora.rowAtPoint(evt.getPoint());
        }
    }//GEN-LAST:event_TabkeBitacoraMouseClicked

    private void TabkeBitacoraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabkeBitacoraKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_TabkeBitacoraKeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        fila = -1;
        LimpiarTabla();
        if (!"".equals(jTextField1.getText())) {
            List<Bitacora> lista = bitacora.buscarLetra(jTextField1.getText());

            modelo = (DefaultTableModel) TabkeBitacora.getModel();
            Object[] ob = new Object[7];
            for (int i = lista.size() - 1; i >= 0; i--) {//las vaciamos de la mas reciente a la mas vieja
                ob[0] = fechaCorrecta(lista.get(i).getFecha());
                ob[1] = lista.get(i).getAccion();
                ob[2] = lista.get(i).getIdRecibe();

                modelo.addRow(ob);
            }
            TabkeBitacora.setModel(modelo);

        } else {
            listarRegistros();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void primeraFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primeraFechaKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {

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

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
        }
    }//GEN-LAST:event_segundaFechaKeyPressed

    private void segundaFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaFechaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_segundaFechaKeyReleased

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
            java.util.logging.Logger.getLogger(vistaBitacora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaBitacora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaBitacora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaBitacora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                vistaBitacora dialog = new vistaBitacora(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TabkeBitacora;
    private com.raven.datechooser.DateChooser jDateChooser1;
    private com.raven.datechooser.DateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane8;
    private textfield.TextField jTextField1;
    private textfield.TextField primeraFecha;
    private textfield.TextField segundaFecha;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

}
