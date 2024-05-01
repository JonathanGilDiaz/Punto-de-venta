/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.CorteDiario;
import Modelo.CorteDiarioDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.configuraciones;
import Modelo.reportes;
import com.raven.swing.Table3;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import com.itextpdf.text.DocumentException;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.Notification;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonathan Gil
 */
public class vistaReportesConcentradoGeneral extends javax.swing.JDialog {

    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
    DefaultTableCellRenderer tcrderecha = new DefaultTableCellRenderer();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modeloTotales = new DefaultTableModel();
    reportes reporte = new reportes();
    String fecha;
    configuraciones configuracionesDao = new configuraciones();
    CorteDiarioDao corteDao = new CorteDiarioDao();
    EmpleadosDao emple = new EmpleadosDao();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    int apoyoR;
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public vistaReportesConcentradoGeneral(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
        this.setTitle("Punto de venta - Concentrado general");
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocationRelativeTo(null);
                }

            }
        });
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tcrderecha.setHorizontalAlignment(SwingConstants.RIGHT);
        ((DefaultTableCellRenderer) TableLista.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableLista.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableLista.getColumnModel().getColumn(1).setCellRenderer(tcrDerecha);
        TableLista.getColumnModel().getColumn(2).setCellRenderer(tcrDerecha);
        TableLista.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableLista.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        ((DefaultTableCellRenderer) TableLista.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        TableTotales.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableTotales.getColumnModel().getColumn(1).setCellRenderer(tcrDerecha);
        TableTotales.getColumnModel().getColumn(2).setCellRenderer(tcrDerecha);
        TableTotales.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableTotales.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        ((DefaultTableCellRenderer) TableTotales.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

        // Formatear la fecha como una cadena en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fecha = fechaSQL.toLocalDate().format(formatter);
        listarPorPeriodoDeFecha();

    }

    public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        for (int i = 0; i < modeloTotales.getRowCount(); i++) {
            modeloTotales.removeRow(i);
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

    public void listarPorPeriodoDeFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<CorteDiario> lsVentas = corteDao.listarCortesPeriodo(sdf.format(jDateChooser1.getDate()), sdf.format(jDateChooser2.getDate()));
        vaciarATabla(lsVentas);
    }

    public void vaciarATabla(List<CorteDiario> lsVentas) {
        double venta = 0;
        double salidas = 0;
        double entradas = 0;
        double retiro = 0;
        modelo = (DefaultTableModel) TableLista.getModel();
        Object[] ob = new Object[5];
        for (int i = lsVentas.size() - 1; i >= 0; i--) {//las vaciamos de la mas reciente a la mas vieja
            ob[0] = fechaFormatoCorrecto(lsVentas.get(i).getFecha());
            ob[1] = "$" + formateador.format(lsVentas.get(i).getVentasContado() + lsVentas.get(i).getVentasCredito() - lsVentas.get(i).getTotalNotasCredito());
            venta = venta + (lsVentas.get(i).getVentasContado() + lsVentas.get(i).getVentasCredito() - lsVentas.get(i).getTotalNotasCredito());
            ob[2] = "$" + formateador.format(lsVentas.get(i).getTotalEntradas());
            entradas = entradas + lsVentas.get(i).getTotalEntradas();
            ob[3] = "$" + formateador.format(lsVentas.get(i).getTotalSalidas());
            salidas = salidas + lsVentas.get(i).getTotalSalidas();
            ob[4] = "$" + formateador.format(lsVentas.get(i).getRetiro());
            retiro = retiro + lsVentas.get(i).getRetiro();
            modelo.addRow(ob);
        }
        TableLista.setModel(modelo);

        modeloTotales = (DefaultTableModel) TableTotales.getModel();
        ob[0] = "";
        ob[1] = "$" + formateador.format(venta);
        ob[2] = "$" + formateador.format(entradas);
        ob[3] = "$" + formateador.format(salidas);
        ob[4] = "$" + formateador.format(retiro);

        modeloTotales.addRow(ob);
        TableTotales.setModel(modeloTotales);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser2 = new com.raven.datechooser.DateChooser();
        jDateChooser1 = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TableTotales = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        TableLista = new javax.swing.JTable();
        panelNuevo = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        primeraFecha = new textfield.TextField();
        segundaFecha = new textfield.TextField();

        jDateChooser2.setForeground(new java.awt.Color(51, 102, 255));
        jDateChooser2.setTextRefernce(segundaFecha);
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        jDateChooser1.setForeground(new java.awt.Color(51, 102, 255));
        jDateChooser1.setTextRefernce(primeraFecha);
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        Titulo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setText("CONCENTRADO GENERAL");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(414, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 960, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableTotales = new Table3();
        TableTotales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "-", "Total", "Total", "Total", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableTotales.setToolTipText("");
        TableTotales.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableTotalesMouseClicked(evt);
            }
        });
        TableTotales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableTotalesKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(TableTotales);
        if (TableTotales.getColumnModel().getColumnCount() > 0) {
            TableTotales.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableTotales.getColumnModel().getColumn(1).setPreferredWidth(80);
            TableTotales.getColumnModel().getColumn(2).setPreferredWidth(80);
            TableTotales.getColumnModel().getColumn(3).setPreferredWidth(80);
            TableTotales.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 920, 40));

        TableLista = new Table3();
        TableLista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "V Netas", "Entradas", "Salidas", "Retiros"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableLista.setToolTipText("");
        TableLista.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableListaMouseClicked(evt);
            }
        });
        TableLista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableListaKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(TableLista);
        if (TableLista.getColumnModel().getColumnCount() > 0) {
            TableLista.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableLista.getColumnModel().getColumn(1).setPreferredWidth(80);
            TableLista.getColumnModel().getColumn(2).setPreferredWidth(80);
            TableLista.getColumnModel().getColumn(3).setPreferredWidth(80);
            TableLista.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel4.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 380));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 173, 940, 450));

        panelNuevo.setBackground(new java.awt.Color(255, 255, 255));
        panelNuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelNuevoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelNuevoMouseEntered(evt);
            }
        });

        btnNuevo.setBackground(new java.awt.Color(153, 204, 255));
        btnNuevo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen1.png"))); // NOI18N
        btnNuevo.setToolTipText("F1 - Visualizar en PDF");
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNuevoMouseExited(evt);
            }
        });
        btnNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelNuevoLayout = new javax.swing.GroupLayout(panelNuevo);
        panelNuevo.setLayout(panelNuevoLayout);
        panelNuevoLayout.setHorizontalGroup(
            panelNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelNuevoLayout.setVerticalGroup(
            panelNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("     Buscar por fecha");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 130, 20));

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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 940, 60));

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

    private void TableTotalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableTotalesMouseClicked
        if (evt.getClickCount() == 2) {

        }
    }//GEN-LAST:event_TableTotalesMouseClicked

    private void TableTotalesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableTotalesKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_TableTotalesKeyPressed

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Formatter obj = new Formatter();
        Formatter obj2 = new Formatter();
        LocalDateTime m = LocalDateTime.now(); //Obtenemos la fecha actual
        String mes = String.valueOf(obj.format("%02d", m.getMonthValue()));//Modificamos la fecha al formato que queremos 
        String dia = String.valueOf(obj2.format("%02d", m.getDayOfMonth()));
        String DiagHoy = dia + "-" + mes + "-" + m.getYear();
        String fechaInicialBien = fechaFormatoCorrecto(sdf.format(jDateChooser1.getDate()));
        String fechaFInalBien = fechaFormatoCorrecto(sdf.format(jDateChooser2.getDate()));
        String tituloEncabezado = " DEL " + fechaInicialBien + " AL " + fechaFInalBien;
        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Generando reporte");
        panel.showNotification();
        try {
            reporte.reportePDF("Concentrado general", TableLista, TableTotales, fecha, fecha, "\nCONCENTRADO GENERAL\n" + tituloEncabezado + "\nFECHA DE EMISION " + DiagHoy);
        } catch (DocumentException ex) {
            Logger.getLogger(vistaReportesConcentradoGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnNuevoMouseClicked

    private void btnNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseEntered
        panelNuevo.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnNuevoMouseEntered

    private void btnNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseExited
        panelNuevo.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnNuevoMouseExited

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnNuevoKeyPressed

    private void panelNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevoMouseClicked

    private void panelNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevoMouseEntered

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

    private void TableListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableListaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableListaMouseClicked

    private void TableListaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableListaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TableListaKeyPressed

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
            java.util.logging.Logger.getLogger(vistaReportesConcentradoGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaReportesConcentradoGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaReportesConcentradoGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaReportesConcentradoGeneral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaReportesConcentradoGeneral dialog = new vistaReportesConcentradoGeneral(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableLista;
    private javax.swing.JTable TableTotales;
    private javax.swing.JLabel Titulo;
    private javax.swing.JLabel btnNuevo;
    private com.raven.datechooser.DateChooser jDateChooser1;
    private com.raven.datechooser.DateChooser jDateChooser2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel panelNuevo;
    private textfield.TextField primeraFecha;
    private textfield.TextField segundaFecha;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

}
