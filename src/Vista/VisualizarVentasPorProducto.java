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
import Modelo.Ventas;
import static Vista.vistaClientes.isNumeric;
import com.raven.swing.Table3;
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

public class VisualizarVentasPorProducto extends javax.swing.JDialog {

    ClienteDao client = new ClienteDao();
    DefaultTableModel modelo = new DefaultTableModel();
    VentasDao ventaDao = new VentasDao();
    EmpleadosDao emple = new EmpleadosDao();
    List<Empleados> lsE = emple.listarEmpleados();
    List<Clientes> lsC = client.ListarCliente();
    List<Integer> listaFolios = new ArrayList<Integer>();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    int apoyoR;
    Empleados e;
    String codigoProducto;

    public void vaciarEmpleado(Empleados e,String codigoProducto) {
        this.e = e;
        this.codigoProducto=codigoProducto;
                listarVentas();
    }

    public VisualizarVentasPorProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Control de ventas");
        Seticon();

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableVentas.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(8).setCellRenderer(tcr);
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
        spTable.getViewport().setBackground(new Color(204, 204, 204));
        TableVentas.setBackground(Color.WHITE);
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

    public void listarVentas() {//Metodo para vaciar las notas en su respectiva tabla
        List<Ventas> lsVentas = ventaDao.listarVentasPorProducto(codigoProducto);
        vaciarATabla(lsVentas);
    }

    public void vaciarATabla(List<Ventas> lsVentas) {
        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[9];
        for (int i = lsVentas.size() - 1; i >= 0; i--) {//las vaciamos de la mas reciente a la mas vieja
            ob[0] = fechaFormatoCorrecto(lsVentas.get(i).getFecha());
            ob[1] = "F" + lsVentas.get(i).getFolio();
            Clientes clie = client.BuscarPorCodigo(lsVentas.get(i).getIdCliente());
            if (clie.getTipoPersona().equals("Persona Física")) {
                ob[2] = clie.getNombre() + " " + clie.getApellidoP() + " " + clie.getApellidoM();
            } else {
                ob[2] = clie.getNombreComercial();
            }
            ob[3] = "$" + formateador.format(lsVentas.get(i).getSubTotal());
            ob[4] = "$" + formateador.format(lsVentas.get(i).getDescuentos());
            ob[5] = "$" + formateador.format(lsVentas.get(i).getIva());
            ob[6] = "$" + formateador.format(lsVentas.get(i).getTotal());
            ob[7] = "$" + formateador.format(lsVentas.get(i).getSaldo());
            ob[8] = lsVentas.get(i).getFormaPago();

            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);

    }

    private static boolean isSubstring(String s, String seq) {
        return Pattern.compile(Pattern.quote(seq), Pattern.CASE_INSENSITIVE)
                .matcher(s).find();
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
        jPanel4 = new javax.swing.JPanel();
        spTable = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("VENTAS POR PRODUCTO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(625, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 1050, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVentas = new Table3();
        TableVentas.setBackground(new java.awt.Color(204, 204, 255));
        TableVentas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Folio", "Cliente", "SubTotal", "Descuentos", "IVA", "Total", "Saldo", "FormaPago"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(140);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(4).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(5).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(6).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(7).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(8).setPreferredWidth(40);
        }

        jPanel4.add(spTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1010, 460));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1030, 480));

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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = TableVentas.rowAtPoint(evt.getPoint());
            int buscando = Integer.parseInt(TableVentas.getValueAt(fila, 1).toString().substring(1));
            vistaVenta vN = new vistaVenta(new javax.swing.JFrame(), true);
            vN.vaciarEmpleado(e.getId());
            vN.validandoDatos(buscando, 2);
            vN.setLocation(200, 50);
            vN.setVisible(true);
        }
    }//GEN-LAST:event_TableVentasMouseClicked

    private void TableVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TableVentasMouseExited

    private void TableVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentasKeyPressed

    }//GEN-LAST:event_TableVentasKeyPressed

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
            java.util.logging.Logger.getLogger(VisualizarVentasPorProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisualizarVentasPorProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisualizarVentasPorProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisualizarVentasPorProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                VisualizarVentasPorProducto dialog = new VisualizarVentasPorProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
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
