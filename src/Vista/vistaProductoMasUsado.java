/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Conexion;
import Modelo.Departamento;
import Modelo.DepartamentoDao;
import Modelo.Empleados;
import Modelo.Linea;
import Modelo.LineaDao;
import com.raven.swing.Table3;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonathan Gil
 */
public class vistaProductoMasUsado extends javax.swing.JDialog {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
    DefaultTableCellRenderer tcrderecha = new DefaultTableCellRenderer();
    DefaultTableModel modelo = new DefaultTableModel();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat formato = new DecimalFormat("0.00");
    DepartamentoDao departamentoDao = new DepartamentoDao();
    LineaDao lineaDao = new LineaDao();
    List<Departamento> lsDepartamento = departamentoDao.listarDepartamento();
    List<Linea> lsLineas = lineaDao.listarLineas();
    int apoyoR;
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public vistaProductoMasUsado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
        this.setTitle("Punto de venta - Reporte de ventas");
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
        TableLista.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableLista.getColumnModel().getColumn(2).setCellRenderer(tcrDerecha);
        TableLista.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableLista.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableLista.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TableLista.getColumnModel().getColumn(6).setCellRenderer(tcr);
        TableLista.getColumnModel().getColumn(7).setCellRenderer(tcr);
        TableLista.getColumnModel().getColumn(8).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableLista.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);
        listarProductos(true);
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

    public void listarProductos(boolean bandera) {
        String sql = "";
        Object[] ob = new Object[9];
        modelo = (DefaultTableModel) TableLista.getModel();
        if (bandera == true) {
            sql = "SELECT p.*, SUM(dc.cantidad) AS Cantidad_Usada\n"
                    + "FROM producto p\n"
                    + "JOIN detalleventa dc ON p.codigo = dc.codigo\n"
                    + "GROUP BY p.id, p.descripcion, p.precioCompra, p.precioVenta, p.departamento, p.linea, p.tipoVenta\n"
                    + "ORDER BY Cantidad_Usada DESC;";
        } else {
            sql = "SELECT p.*, SUM(dc.cantidad) AS Cantidad_Usada\n"
                    + "FROM producto p\n"
                    + "JOIN detalleventa dc ON p.codigo = dc.codigo\n"
                    + "WHERE p.descripcion LIKE '%" + txtNombre.getText() + "%' OR p.codigo LIKE '%" + txtNombre.getText() + "%'\n"
                    + "GROUP BY p.id, p.descripcion, p.precioCompra, p.precioVenta, p.departamento, p.linea, p.tipoVenta\n"
                    + "ORDER BY Cantidad_Usada DESC\n"
                    + ";";
            System.out.println(sql);
        }
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ob[0] = rs.getString("codigo");
                ob[1] = rs.getString("descripcion");
                ob[2] = "$" + formateador.format(rs.getDouble("precioCompra"));
                ob[3] = "$" + formateador.format(rs.getDouble("precioVenta"));
                ob[4] = formato.format(rs.getDouble("Cantidad_Usada"));
                ob[5] = "$"+formateador.format((rs.getDouble("precioVenta") - rs.getDouble("precioCompra")) * rs.getDouble("Cantidad_Usada"));
                for (int tabulador = 0; tabulador < lsDepartamento.size(); tabulador++) {
                    if (lsDepartamento.get(tabulador).getId() == rs.getInt("departamento")) {
                        ob[6] = lsDepartamento.get(tabulador).getNombre();
                    }
                }
                for (int tabulador = 0; tabulador < lsLineas.size(); tabulador++) {
                    if (lsLineas.get(tabulador).getId() == rs.getInt("linea")) {
                        ob[7] = lsLineas.get(tabulador).getNombre();
                    }
                }
                ob[8] = rs.getString("tipoVenta");
                modelo.addRow(ob);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.toString() + "Error al listar productos");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                 JOptionPane.showMessageDialog(null,ex.toString() + "UltimoCatch");
            }
        }
        TableLista.setModel(modelo);
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
        Titulo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        TableLista = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtNombre = new textfield.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        Titulo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setText("PRODUCTOS MÁS VENDIDOS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(407, Short.MAX_VALUE))
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

        TableLista = new Table3();
        TableLista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Costo", "Precio", "Vendido", "Ganancia", "Departamento", "Linea", "Unidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableLista.setToolTipText("Doble clic para visualizar ventas en donde se vendio el producto");
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
            TableLista.getColumnModel().getColumn(0).setPreferredWidth(80);
            TableLista.getColumnModel().getColumn(1).setPreferredWidth(90);
            TableLista.getColumnModel().getColumn(2).setPreferredWidth(20);
            TableLista.getColumnModel().getColumn(3).setPreferredWidth(20);
            TableLista.getColumnModel().getColumn(4).setPreferredWidth(20);
            TableLista.getColumnModel().getColumn(5).setPreferredWidth(35);
            TableLista.getColumnModel().getColumn(6).setPreferredWidth(50);
            TableLista.getColumnModel().getColumn(7).setPreferredWidth(50);
            TableLista.getColumnModel().getColumn(8).setPreferredWidth(5);
        }

        jPanel4.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 460));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 940, 480));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre.setLabelText("Buscar producto");
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
        });
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 410, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 620, 60));

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

    private void TableListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableListaMouseClicked
            VisualizarVentasPorProducto vN = new VisualizarVentasPorProducto(new javax.swing.JFrame(), true);
            vN.vaciarEmpleado(e,TableLista.getValueAt(TableLista.getSelectedRow(), 0).toString());
            vN.setVisible(true);
    }//GEN-LAST:event_TableListaMouseClicked

    private void TableListaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableListaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TableListaKeyPressed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased

        LimpiarTabla();
        if (!"".equals(txtNombre.getText())) {
            listarProductos(false);
        } else {
            listarProductos(true);
        }
    }//GEN-LAST:event_txtNombreKeyReleased

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
            java.util.logging.Logger.getLogger(vistaProductoMasUsado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaProductoMasUsado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaProductoMasUsado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaProductoMasUsado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaProductoMasUsado dialog = new vistaProductoMasUsado(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel Titulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane9;
    private textfield.TextField txtNombre;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

}
