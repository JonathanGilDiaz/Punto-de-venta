/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Departamento;
import Modelo.DepartamentoDao;
import Modelo.Empleados;
import Modelo.InventarioFueraDao;
import Modelo.Linea;
import Modelo.LineaDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import com.raven.swing.Table3;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class vistaInventario extends javax.swing.JDialog {

    DepartamentoDao departamentoDao = new DepartamentoDao();
    bitacoraDao bitacora = new bitacoraDao();
    DefaultTableModel modelo = new DefaultTableModel();
    ProductoDao productoDao = new ProductoDao();
    LineaDao lineaDao = new LineaDao();
    VentasDao ventaDao = new VentasDao();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat formato = new DecimalFormat("0.00");
    InventarioFueraDao inventarioFueraDao = new InventarioFueraDao();
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    int fila = -1;
    String fecha, hora;
    List<Departamento> lsDepartamento = departamentoDao.listarDepartamento();
    List<Linea> lsLinea = lineaDao.listarLineas();

    public vistaInventario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Inventario");
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocation(200, 50);
                };

            }
        });
        Seticon();
        TableInventario.setBackground(Color.WHITE);

        jScrollPane8.getViewport().setBackground(new Color(204, 204, 204));
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer tcrderecha = new DefaultTableCellRenderer();
        tcrderecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TableInventario.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableInventario.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableInventario.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableInventario.getColumnModel().getColumn(3).setCellRenderer(tcrderecha);
        TableInventario.getColumnModel().getColumn(4).setCellRenderer(tcrderecha);
        TableInventario.getColumnModel().getColumn(5).setCellRenderer(tcrderecha);
        TableInventario.getColumnModel().getColumn(6).setCellRenderer(tcrderecha);
        TableInventario.getColumnModel().getColumn(7).setCellRenderer(tcrderecha);
        TableInventario.getColumnModel().getColumn(8).setCellRenderer(tcrderecha);
        ((DefaultTableCellRenderer) TableInventario.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        listarProductos();
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

    public void listarProductos() {
        List<Producto> ListaCl = productoDao.listarProductosConInventario();
        modelo = (DefaultTableModel) TableInventario.getModel();
        double totalCosto = 0;
        double totalPrecio = 0;
        double ganancia = 0;
        Object[] ob = new Object[9];
        for (int i = 0; i < ListaCl.size(); i++) {
            ob[0] = ListaCl.get(i).getCodigo();
            ob[1] = ListaCl.get(i).getDescripcion();
            ob[2] = formato.format(ListaCl.get(i).getExistencia());
            ob[3] = "$" + formateador.format(ListaCl.get(i).getPrecioCompra());
            totalCosto = totalCosto + (ListaCl.get(i).getPrecioCompra() * ListaCl.get(i).getExistencia());
            ob[4] = "$" + formateador.format(ListaCl.get(i).getPrecioVenta());
            totalPrecio = totalPrecio + (ListaCl.get(i).getPrecioVenta() * ListaCl.get(i).getExistencia());
            Linea lineaActual = new Linea();
            Departamento DepartamentoActual = new Departamento();
            for (int d = 0; d < lsDepartamento.size(); d++) {
                if (lsDepartamento.get(d).getId() == ListaCl.get(i).getDepartamento()) {
                    DepartamentoActual = lsDepartamento.get(d);
                }
            }
            for (int d = 0; d < lsLinea.size(); d++) {
                if (lsLinea.get(d).getId() == ListaCl.get(i).getLinea()) {
                    lineaActual = lsLinea.get(d);
                }
            }
            ob[5] = formato.format(DepartamentoActual.getAumento() + lineaActual.getAumento()) + "%";
            ob[6] = formato.format(DepartamentoActual.getDescuento() + lineaActual.getDescuento()) + "%";
            ob[7] = "$" + formateador.format(ListaCl.get(i).getExistencia() * ListaCl.get(i).getPrecioCompra());
            ob[8] = "$" + formateador.format(ListaCl.get(i).getExistencia() * ListaCl.get(i).getPrecioVenta());

            modelo.addRow(ob);
        }
        TableInventario.setModel(modelo);
        txtCostoTotal.setText("$" + formateador.format(totalCosto));
        txtPrecioTotal.setText("$" + formateador.format(totalPrecio));
        txtGanancia.setText("$" + formateador.format(totalPrecio - totalCosto));

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
        VisualizarConteo vo = new VisualizarConteo(new javax.swing.JFrame(), true);
        vo.setLocation(200, 80);
        vo.vaciarEmpleado(e);
        vo.setVisible(true);
        LimpiarTabla();
        listarProductos();
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
        txtGanancia = new textfield.TextField();
        txtCostoTotal = new textfield.TextField();
        txtPrecioTotal = new textfield.TextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TableInventario = new javax.swing.JTable();
        panelNuevo = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INVENTARIO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(707, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 1000, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar producto");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 330, 40));

        txtGanancia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtGanancia.setEnabled(false);
        txtGanancia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtGanancia.setLabelText("Ganancias esperadas");
        txtGanancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGananciaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGananciaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGananciaKeyTyped(evt);
            }
        });
        jPanel2.add(txtGanancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 170, -1));

        txtCostoTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCostoTotal.setEnabled(false);
        txtCostoTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCostoTotal.setLabelText("Costo total de inventario");
        txtCostoTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostoTotalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostoTotalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoTotalKeyTyped(evt);
            }
        });
        jPanel2.add(txtCostoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 170, -1));

        txtPrecioTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecioTotal.setEnabled(false);
        txtPrecioTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPrecioTotal.setLabelText("Precio total de inventario");
        txtPrecioTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioTotalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioTotalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioTotalKeyTyped(evt);
            }
        });
        jPanel2.add(txtPrecioTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 170, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 980, 60));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableInventario = new Table3();
        TableInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Existencia", "Costo", "Precio", "% Utilidad", "%Descuento", "T. Costo", "T. Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableInventario.setToolTipText("");
        TableInventario.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableInventarioMouseClicked(evt);
            }
        });
        TableInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableInventarioKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(TableInventario);
        if (TableInventario.getColumnModel().getColumnCount() > 0) {
            TableInventario.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableInventario.getColumnModel().getColumn(1).setPreferredWidth(150);
            TableInventario.getColumnModel().getColumn(2).setPreferredWidth(5);
            TableInventario.getColumnModel().getColumn(3).setPreferredWidth(15);
            TableInventario.getColumnModel().getColumn(4).setPreferredWidth(15);
            TableInventario.getColumnModel().getColumn(5).setPreferredWidth(15);
            TableInventario.getColumnModel().getColumn(6).setPreferredWidth(15);
            TableInventario.getColumnModel().getColumn(7).setPreferredWidth(25);
            TableInventario.getColumnModel().getColumn(8).setPreferredWidth(25);
        }

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 960, 430));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 980, 450));

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
        panelNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNuevo.setBackground(new java.awt.Color(153, 204, 255));
        btnNuevo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/contar.png"))); // NOI18N
        btnNuevo.setToolTipText("F1 - Conteo de inventario");
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevo.setPreferredSize(new java.awt.Dimension(48, 44));
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
        panelNuevo.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, -1, 44));

        jPanel1.add(panelNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableInventarioMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TableInventario.rowAtPoint(evt.getPoint());

        }
    }//GEN-LAST:event_TableInventarioMouseClicked

    private void TableInventarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableInventarioKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_TableInventarioKeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        fila = -1;
        LimpiarTabla();
        if (!"".equals(jTextField1.getText())) {
            List<Producto> ListaCl = productoDao.buscarLetraCodigoDescripcionInventario(jTextField1.getText());
            modelo = (DefaultTableModel) TableInventario.getModel();
            Object[] ob = new Object[9];
            for (int i = 0; i < ListaCl.size(); i++) {
                ob[0] = ListaCl.get(i).getCodigo();
                ob[1] = ListaCl.get(i).getDescripcion();
                ob[2] = formato.format(ListaCl.get(i).getExistencia());
                ob[3] = "$" + formateador.format(ListaCl.get(i).getPrecioCompra());
                ob[4] = "$" + formateador.format(ListaCl.get(i).getPrecioVenta());
                Linea lineaActual = new Linea();
                Departamento DepartamentoActual = new Departamento();
                for (int d = 0; d < lsDepartamento.size(); d++) {
                    if (lsDepartamento.get(d).getId() == ListaCl.get(i).getDepartamento()) {
                        DepartamentoActual = lsDepartamento.get(d);
                    }
                }
                for (int d = 0; d < lsLinea.size(); d++) {
                    if (lsLinea.get(d).getId() == ListaCl.get(i).getLinea()) {
                        lineaActual = lsLinea.get(d);
                    }
                }
                ob[5] = formato.format(DepartamentoActual.getAumento() + lineaActual.getAumento()) + "%";
                ob[6] = formato.format(DepartamentoActual.getDescuento() + lineaActual.getDescuento()) + "%";
                ob[7] = "$" + formateador.format(ListaCl.get(i).getExistencia() * ListaCl.get(i).getPrecioCompra());
                ob[8] = "$" + formateador.format(ListaCl.get(i).getExistencia() * ListaCl.get(i).getPrecioVenta());
                if (ListaCl.get(i).getInventario() == 1) {
                    modelo.addRow(ob);
                }
            }
            TableInventario.setModel(modelo);

        } else {
            listarProductos();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        eventoF1();
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
            case KeyEvent.VK_F1:
                eventoF1();
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

    private void txtGananciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGananciaKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;
        }
    }//GEN-LAST:event_txtGananciaKeyPressed

    private void txtGananciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGananciaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGananciaKeyReleased

    private void txtGananciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGananciaKeyTyped

    }//GEN-LAST:event_txtGananciaKeyTyped

    private void txtCostoTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoTotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoTotalKeyPressed

    private void txtCostoTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoTotalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoTotalKeyReleased

    private void txtCostoTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoTotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoTotalKeyTyped

    private void txtPrecioTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioTotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioTotalKeyPressed

    private void txtPrecioTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioTotalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioTotalKeyReleased

    private void txtPrecioTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioTotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioTotalKeyTyped

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
            java.util.logging.Logger.getLogger(vistaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                vistaInventario dialog = new vistaInventario(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableInventario;
    private javax.swing.JLabel btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane8;
    private textfield.TextField jTextField1;
    private javax.swing.JPanel panelNuevo;
    private textfield.TextField txtCostoTotal;
    private textfield.TextField txtGanancia;
    private textfield.TextField txtPrecioTotal;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

}
