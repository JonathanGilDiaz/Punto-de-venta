/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.CorteDiarioDao;
import Modelo.Empleados;
import Modelo.Factura;
import Modelo.FacturaDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.bitacoraDao;
import Modelo.detalleVenta;
import Modelo.reportes;
import com.itextpdf.text.Font;
import com.raven.swing.Table3;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import javaswingdev.Notification;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class vitaGenerarFactura extends javax.swing.JDialog {

    bitacoraDao bitacora = new bitacoraDao();
    DefaultTableModel modelo = new DefaultTableModel();
    CorteDiarioDao corteDao = new CorteDiarioDao();
    FacturaDao facturaDao = new FacturaDao();
    ProductoDao productoDao = new ProductoDao();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    int fila = -1;
    String fecha = corteDao.getDia();
    String hora;
    DecimalFormat df = new DecimalFormat("$ #,##0.00;($ #,##0.00)");
    DecimalFormat df2 = new DecimalFormat("#,##0.00;($ #,##0.00)");
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public vitaGenerarFactura(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Para facturar");
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
        tableFactura.setBackground(Color.WHITE);

        jScrollPane8.getViewport().setBackground(new Color(204, 204, 204));
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tableFactura.getColumnModel().getColumn(0).setCellRenderer(tcr);
        tableFactura.getColumnModel().getColumn(1).setCellRenderer(tcr);
        tableFactura.getColumnModel().getColumn(2).setCellRenderer(tcr);
        tableFactura.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        tableFactura.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        tableFactura.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        tableFactura.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        ((DefaultTableCellRenderer) tableFactura.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        listarFacturas();
        jTextField1.requestFocus();
        panelExcel.setBackground(new Color(179, 195, 219));
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

    public void eventoF2() {
        if (fila != -1) {
            reporteExcel(Integer.parseInt(tableFactura.getValueAt(fila, 0).toString()));
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
            panel.showNotification();
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

    public void listarFacturas() {
        List<Factura> ListaCl = facturaDao.listarFacturas();
        modelo = (DefaultTableModel) tableFactura.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < ListaCl.size(); i++) {
            ob[0] = ListaCl.get(i).getId();
            ob[1] = fechaFormatoCorrecto(ListaCl.get(i).getFecha());
            ob[2] = ListaCl.get(i).getNombre();
            ob[3] = "$" + formateador.format(ListaCl.get(i).getSubtotal());
            ob[4] = "$" + formateador.format(ListaCl.get(i).getDescuento());
            ob[5] = "$" + formateador.format(ListaCl.get(i).getIva());
            ob[6] = "$" + formateador.format(ListaCl.get(i).getTotal());
            modelo.addRow(ob);
        }
        tableFactura.setModel(modelo);
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

    public void eventoF1() {
        CrearModificarConcentradoFactura cc = new CrearModificarConcentradoFactura(new javax.swing.JFrame(), true);
        cc.vaciarEmpleado(e.getId());
        cc.setVisible(true);
        if (cc.accionCompletada == true) {
            LimpiarTabla();
            listarFacturas();
            panelExcel.setBackground(new Color(179, 195, 219));
            fila = -1;

            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Acción realizada exitosamente");
            panel.showNotification();
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
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new textfield.TextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableFactura = new javax.swing.JTable();
        panelNuevo = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JLabel();
        panelExcel = new javax.swing.JPanel();
        btnExcel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PARA FACTURAR");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(625, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 960, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar por código o nombre");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 410, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 620, 60));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableFactura = new Table3();
        tableFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha", "Nombre", "SubTotal", "Descuento", "IVA", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableFactura.setToolTipText("Doble click para hacer modificaciones");
        tableFactura.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tableFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFacturaMouseClicked(evt);
            }
        });
        tableFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableFacturaKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tableFactura);
        if (tableFactura.getColumnModel().getColumnCount() > 0) {
            tableFactura.getColumnModel().getColumn(0).setPreferredWidth(5);
            tableFactura.getColumnModel().getColumn(1).setResizable(false);
            tableFactura.getColumnModel().getColumn(1).setPreferredWidth(35);
            tableFactura.getColumnModel().getColumn(2).setPreferredWidth(140);
            tableFactura.getColumnModel().getColumn(3).setPreferredWidth(20);
            tableFactura.getColumnModel().getColumn(4).setPreferredWidth(20);
            tableFactura.getColumnModel().getColumn(5).setPreferredWidth(20);
            tableFactura.getColumnModel().getColumn(6).setPreferredWidth(20);
        }

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 430));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 940, 450));

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
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnNuevo.setToolTipText("F1 - Nuevo linea");
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

        panelExcel.setBackground(new java.awt.Color(255, 255, 255));
        panelExcel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelExcelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelExcelMouseEntered(evt);
            }
        });

        btnExcel.setBackground(new java.awt.Color(255, 255, 255));
        btnExcel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/excel.png"))); // NOI18N
        btnExcel.setToolTipText("F2 - Ver en Excel");
        btnExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExcelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcelMouseExited(evt);
            }
        });
        btnExcel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnExcelKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelExcelLayout = new javax.swing.GroupLayout(panelExcel);
        panelExcel.setLayout(panelExcelLayout);
        panelExcelLayout.setHorizontalGroup(
            panelExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnExcel, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelExcelLayout.setVerticalGroup(
            panelExcelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnExcel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 50, 50));

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

    private void tableFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFacturaMouseClicked
        if (evt.getClickCount() == 2) {
            fila = tableFactura.rowAtPoint(evt.getPoint());
            panelExcel.setBackground(new Color(255, 255, 255));

        }
    }//GEN-LAST:event_tableFacturaMouseClicked

    private void tableFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableFacturaKeyPressed
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
    }//GEN-LAST:event_tableFacturaKeyPressed

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
        fila = -1;
        panelExcel.setBackground(new Color(179, 195, 219));

        LimpiarTabla();
        if (!"".equals(jTextField1.getText())) {
            if (isNumeric(jTextField1.getText()) == true) {
                Factura alistar = facturaDao.BuscarPorCodigo(Integer.parseInt(jTextField1.getText()));
                if (alistar.getId() == 0) {
                    modelo = (DefaultTableModel) tableFactura.getModel();
                    tableFactura.setModel(modelo);
                } else {
                    modelo = (DefaultTableModel) tableFactura.getModel();
                    Object[] ob = new Object[7];
                    ob[0] = alistar.getId();
                    ob[1] = fechaFormatoCorrecto(alistar.getFecha());
                    ob[2] = alistar.getNombre();
                    ob[3] = "$" + formateador.format(alistar.getSubtotal());
                    ob[4] = "$" + formateador.format(alistar.getDescuento());
                    ob[5] = "$" + formateador.format(alistar.getIva());
                    ob[6] = "$" + formateador.format(alistar.getTotal());
                    modelo.addRow(ob);

                    tableFactura.setModel(modelo);
                }
            } else {
                List<Factura> ListaCl = facturaDao.buscarLetra(jTextField1.getText());

                modelo = (DefaultTableModel) tableFactura.getModel();
                Object[] ob = new Object[7];
                for (int i = 0; i < ListaCl.size(); i++) {
                    ob[0] = ListaCl.get(i).getId();
                    ob[1] = fechaFormatoCorrecto(ListaCl.get(i).getFecha());
                    ob[2] = ListaCl.get(i).getNombre();
                    ob[3] = "$" + formateador.format(ListaCl.get(i).getSubtotal());
                    ob[4] = "$" + formateador.format(ListaCl.get(i).getDescuento());
                    ob[5] = "$" + formateador.format(ListaCl.get(i).getIva());
                    ob[6] = "$" + formateador.format(ListaCl.get(i).getTotal());
                    modelo.addRow(ob);
                }
                tableFactura.setModel(modelo);
            }
        } else {
            listarFacturas();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        eventoF1();
    }//GEN-LAST:event_btnNuevoMouseClicked

    private void btnNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseEntered
        panelNuevo.setBackground(new Color(179, 195, 219));
    }//GEN-LAST:event_btnNuevoMouseEntered

    private void btnNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseExited
        panelNuevo.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnNuevoMouseExited

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
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
    }//GEN-LAST:event_btnNuevoKeyPressed

    private void panelNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevoMouseClicked

    private void panelNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevoMouseEntered

    private void btnExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseClicked
        eventoF2();
    }//GEN-LAST:event_btnExcelMouseClicked

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        panelExcel.setBackground(new Color(179, 195, 219));
        if (fila != -1) {
            panelExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelExcel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        if (fila != -1) {
            panelExcel.setBackground(new Color(255, 255, 255));
            btnExcel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelExcel.setBackground(new Color(179, 195, 219));
            btnExcel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnExcelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcelKeyPressed
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
    }//GEN-LAST:event_btnExcelKeyPressed

    private void panelExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelExcelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelExcelMouseClicked

    private void panelExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelExcelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelExcelMouseEntered

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
            java.util.logging.Logger.getLogger(vistaLinea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaLinea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaLinea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaLinea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                vitaGenerarFactura dialog = new vitaGenerarFactura(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnExcel;
    private javax.swing.JLabel btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane8;
    private textfield.TextField jTextField1;
    private javax.swing.JPanel panelExcel;
    private javax.swing.JPanel panelNuevo;
    private javax.swing.JTable tableFactura;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    public void reporteExcel(int folio) {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Para factura");
        Factura factura = facturaDao.BuscarPorCodigo(folio);
        List<detalleVenta> lsDetalles = facturaDao.regresarDetalles(folio);

        try {
            InputStream is = new FileInputStream("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg");
            byte[] bytes = IOUtils.toByteArray(is);
            int imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            is.close();

            CreationHelper help = book.getCreationHelper();
            Drawing draw = sheet.createDrawingPatriarch();

            ClientAnchor anchor = help.createClientAnchor();
            anchor.setCol1(0);//posicionar en que columna
            anchor.setRow1(1); // en que fila va a estar
            Picture pict = draw.createPicture(anchor, imgIndex);
            pict.resize(1, 3);

            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
            org.apache.poi.ss.usermodel.Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName(Font.FontFamily.TIMES_ROMAN.name());
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short) 12);
            tituloEstilo.setFont(fuenteTitulo);

            CellStyle tituloEstilo2 = book.createCellStyle();
            tituloEstilo2.setAlignment(HorizontalAlignment.LEFT);
            tituloEstilo2.setVerticalAlignment(VerticalAlignment.CENTER);
            org.apache.poi.ss.usermodel.Font fuenteTitulo2 = book.createFont();
            fuenteTitulo2.setFontName(Font.FontFamily.TIMES_ROMAN.name());
            fuenteTitulo2.setBold(false);
            fuenteTitulo2.setFontHeightInPoints((short) 11);
            tituloEstilo2.setFont(fuenteTitulo2);

            Formatter obj = new Formatter();
            Formatter obj2 = new Formatter();

            LocalDateTime m = LocalDateTime.now(); //Obtenemos la fecha actual
            String mes = String.valueOf(obj.format("%02d", m.getMonthValue()));//Modificamos la fecha al formato que queremos 
            String dia = String.valueOf(obj2.format("%02d", m.getDayOfMonth()));
            String DiagHoy = dia + "-" + mes + "-" + m.getYear();

            Row filaTitulo = sheet.createRow(1);
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Nombre:");
            celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo2);
            celdaTitulo.setCellValue(factura.getNombre());
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 7));

            filaTitulo = sheet.createRow(2);
            celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("RFC:");
            celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo2);
            celdaTitulo.setCellValue(factura.getRfc());
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 7));

            filaTitulo = sheet.createRow(3);
            celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Domicilio fiscal:");
            celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo2);
            celdaTitulo.setCellValue(factura.getDireccion());
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 7));

            filaTitulo = sheet.createRow(4);
            celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Correo:");
            celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo2);
            celdaTitulo.setCellValue(factura.getCorreo());
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 7));

            filaTitulo = sheet.createRow(5);
            celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Régimen fiscal:");
            celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo2);
            celdaTitulo.setCellValue(factura.getRegimen());
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 7));

            filaTitulo = sheet.createRow(6);
            celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Uso de CFDI:");
            celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo2);
            celdaTitulo.setCellValue(factura.getCfdi());
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 2, 7));

            filaTitulo = sheet.createRow(7);
            celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Forma de pago:");
            celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo2);
            celdaTitulo.setCellValue(factura.getFormaPago());
            sheet.addMergedRegion(new CellRangeAddress(7, 7, 2, 7));
            //hacemos zoom

            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.NONE);
            datosEstilo.setBorderRight(BorderStyle.NONE);
            datosEstilo.setBorderLeft(BorderStyle.NONE);
            datosEstilo.setBorderTop(BorderStyle.NONE);
            datosEstilo.setAlignment(HorizontalAlignment.CENTER);
            datosEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            org.apache.poi.ss.usermodel.Font font = book.createFont();
            font.setFontName(Font.FontFamily.TIMES_ROMAN.name());
            font.setBold(true);
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);

            int rowCount = lsDetalles.size();
            int columnCount = 9;
            List<String> titulos = new ArrayList<String>();
            titulos.add("Código");
            titulos.add("Cantidad");
            titulos.add("Unidad");
            titulos.add("Descripción");
            titulos.add("P/U");
            titulos.add("Importe");
            titulos.add("Descuento");
            titulos.add("IVA");
            titulos.add("Total");

            // Crear el encabezado en el archivo Excel
            Row headerRow = sheet.createRow(9);
            for (int col = 0; col < columnCount; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(titulos.get(col));
            }

            int numFilaDatos = 10;
            for (int row = 0; row < rowCount; row++) {
                Row excelRow = sheet.createRow(numFilaDatos);
                Producto proc = productoDao.BuscarPorCodigo(lsDetalles.get(row).getCodigo());
                Cell celdaDatos = excelRow.createCell(0);
                celdaDatos.setCellStyle(datosEstilo);
                celdaDatos.setCellValue(lsDetalles.get(row).getCodigo());

                celdaDatos = excelRow.createCell(1, CellType.NUMERIC);
                celdaDatos.setCellValue(lsDetalles.get(row).getCantidad());
                celdaDatos.setCellStyle(getContabilidadCellStyle(book, df2));

                celdaDatos = excelRow.createCell(2);
                celdaDatos.setCellStyle(datosEstilo);
                celdaDatos.setCellValue(proc.getTipoVenta());

                celdaDatos = excelRow.createCell(3);
                celdaDatos.setCellStyle(datosEstilo);
                celdaDatos.setCellValue(lsDetalles.get(row).getDescripcion());

                double importeT = lsDetalles.get(row).getPrecioUnitario() * lsDetalles.get(row).getCantidad();
                double ivaT = (importeT - lsDetalles.get(row).getDescuento()) * .16;

                celdaDatos = excelRow.createCell(4, CellType.NUMERIC);
                celdaDatos.setCellValue(lsDetalles.get(row).getPrecioUnitario());
                celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));

                celdaDatos = excelRow.createCell(5, CellType.NUMERIC);
                celdaDatos.setCellValue(lsDetalles.get(row).getPrecioUnitario() * lsDetalles.get(row).getCantidad());
                celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));

                celdaDatos = excelRow.createCell(6, CellType.NUMERIC);
                celdaDatos.setCellValue(lsDetalles.get(row).getDescuento());
                celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));

                celdaDatos = excelRow.createCell(7, CellType.NUMERIC);
                celdaDatos.setCellValue(ivaT);
                celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));

                celdaDatos = excelRow.createCell(8, CellType.NUMERIC);
                celdaDatos.setCellValue(ivaT + (importeT - lsDetalles.get(row).getDescuento()));
                celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));

                numFilaDatos++;
            }

            numFilaDatos++;
            Row excelRow = sheet.createRow(numFilaDatos);
            Cell celdaDatos = excelRow.createCell(7);
            celdaDatos.setCellStyle(tituloEstilo);
            celdaDatos.setCellValue("Subtotal: ");
            celdaDatos = excelRow.createCell(8);
            celdaDatos.setCellValue(factura.getSubtotal());
            celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));
            numFilaDatos++;

            excelRow = sheet.createRow(numFilaDatos);
            celdaDatos = excelRow.createCell(7);
            celdaDatos.setCellStyle(tituloEstilo);
            celdaDatos.setCellValue("Descuento: ");
            celdaDatos = excelRow.createCell(8);
            celdaDatos.setCellValue(factura.getDescuento());
            celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));
            numFilaDatos++;

            excelRow = sheet.createRow(numFilaDatos);
            celdaDatos = excelRow.createCell(7);
            celdaDatos.setCellStyle(tituloEstilo);
            celdaDatos.setCellValue("IVA: ");
            celdaDatos = excelRow.createCell(8);
            celdaDatos.setCellValue(factura.getIva());
            celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));
            numFilaDatos++;

            excelRow = sheet.createRow(numFilaDatos);
            celdaDatos = excelRow.createCell(7);
            celdaDatos.setCellStyle(tituloEstilo);
            celdaDatos.setCellValue("Total: ");
            celdaDatos = excelRow.createCell(8);
            celdaDatos.setCellValue(factura.getTotal());
            celdaDatos.setCellStyle(getContabilidadCellStyle(book, df));
            numFilaDatos++;

            sheet.setZoom(150);

            for (int col = 0; col < 11; col++) {
                sheet.autoSizeColumn(col);
            }

            FileOutputStream fileOut = new FileOutputStream("C:\\Program Files (x86)\\AppPinturasOssel\\Excel\\Para factura " + folio + ".xlsx");
            book.write(fileOut);
            fileOut.close();
            File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\Excel\\Para factura " + folio + ".xlsx");
            Desktop.getDesktop().open(file);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static CellStyle getContabilidadCellStyle(Workbook workbook, DecimalFormat df) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat(df.format(0)));
        return style;
    }

}
