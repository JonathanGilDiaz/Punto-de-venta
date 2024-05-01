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
import static Vista.vistaVenta.isNumeric;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class vistaCreditos extends javax.swing.JDialog {

    DefaultTableModel modelo = new DefaultTableModel();
    CreditoDao creditoDao = new CreditoDao();
    ClienteDao clienteDao = new ClienteDao();
    DecimalFormat df = new DecimalFormat("00.00");
    int fila = -1;
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    String fecha;
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public vistaCreditos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(150, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Créditos");
        Seticon();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocation(150, 50);
                };
            }
        });

        jScrollPane9.getViewport().setBackground(new Color(204, 204, 204));
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TableCreditos.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableCreditos.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableCreditos.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableCreditos.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableCreditos.getColumnModel().getColumn(4).setCellRenderer(tcr);
        TableCreditos.getColumnModel().getColumn(5).setCellRenderer(tcr);
        TableCreditos.getColumnModel().getColumn(6).setCellRenderer(tcr);
        TableCreditos.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);

        ((DefaultTableCellRenderer) TableCreditos.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        listarCreditos();

        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fecha = fechaSQL.toLocalDate().format(formatter);

    }

    public void listarCreditos() {
        List<Credito> lsCredito = creditoDao.listarCreditos();//Obtenemos gastos de la BD
        modelo = (DefaultTableModel) TableCreditos.getModel();
        Object[] ob = new Object[8];
        for (int i = lsCredito.size() - 1; i >= 0; i--) { //vaciamos los datos en la tabla
            Clientes cliente = clienteDao.BuscarPorCodigo(lsCredito.get(i).getIdCliente());
            ob[0] = lsCredito.get(i).getId();
            if (cliente.getTipoPersona().equals("Persona Física")) {
                ob[1] = lsCredito.get(i).getNombreCliente();
            } else {
                ob[1] = cliente.getNombreComercial();
            }
            ob[2] = lsCredito.get(i).getNombre() + " " + lsCredito.get(i).getApellidoP() + " " + lsCredito.get(i).getApellidoM();
            ob[3] = determinarLimite(lsCredito.get(i).getFecha(), lsCredito.get(i).getVigencia());
            ob[4] = df.format(lsCredito.get(i).getInteresMoratorio()) + "%";
            ob[5] = df.format(lsCredito.get(i).getInteresOrdinario()) + "%";
            ob[6] = "$" + formateador.format(lsCredito.get(i).getLimite());
            ob[7] = "$" + formateador.format(lsCredito.get(i).getAdeudo());
            modelo.addRow(ob);
        }
        TableCreditos.setModel(modelo);
    }

    public String determinarLimite(String fechaInicial, int dias) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfResultado = new SimpleDateFormat("dd-MM-yyyy");  // Nuevo formato
        String fechaFormateada = "";

        try {
            // Convertir el String a una fecha
            Date fecha = sdf.parse(fechaInicial);
            // Crear una instancia de Calendar y establecer la fecha
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);

            // Aumentar la cantidad de días
            calendar.add(Calendar.DAY_OF_MONTH, dias);

            // Obtener la nueva fecha
            Date fechaAumentada = calendar.getTime();

            // Formatear la fecha en el formato deseado
            fechaFormateada = sdfResultado.format(fechaAumentada);  // Usar el nuevo formato

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fechaFormateada;
    }

    public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new textfield.TextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        TableCreditos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CRÉDITOS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(787, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 1080, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar crédito");
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

        TableCreditos = new Table3();
        TableCreditos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Cliente", "Representante", "Vigencia", "Moratorio(%)", "Ordinario (%)", "Límite", "Adeudo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableCreditos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableCreditos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableCreditosMouseClicked(evt);
            }
        });
        TableCreditos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableCreditosKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(TableCreditos);
        if (TableCreditos.getColumnModel().getColumnCount() > 0) {
            TableCreditos.getColumnModel().getColumn(0).setPreferredWidth(2);
            TableCreditos.getColumnModel().getColumn(1).setResizable(false);
            TableCreditos.getColumnModel().getColumn(1).setPreferredWidth(150);
            TableCreditos.getColumnModel().getColumn(2).setPreferredWidth(120);
            TableCreditos.getColumnModel().getColumn(3).setPreferredWidth(10);
            TableCreditos.getColumnModel().getColumn(4).setPreferredWidth(25);
            TableCreditos.getColumnModel().getColumn(5).setPreferredWidth(25);
            TableCreditos.getColumnModel().getColumn(6).setPreferredWidth(25);
            TableCreditos.getColumnModel().getColumn(7).setPreferredWidth(25);
        }

        jPanel4.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 17, 1020, 420));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 1040, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1072, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableCreditosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableCreditosMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TableCreditos.rowAtPoint(evt.getPoint());
            vistaHistorialCliente vh = new vistaHistorialCliente(new javax.swing.JFrame(), true);
            Credito cre = creditoDao.BuscarPorCodigo(Integer.parseInt(TableCreditos.getValueAt(fila, 0).toString()));
            vh.vaciarDatos(clienteDao.BuscarPorCodigo(cre.getIdCliente()));
            vh.vaciarEmpleado(e.getId());
            vh.setLocation(200, 80);
            vh.setVisible(true);
        }
    }//GEN-LAST:event_TableCreditosMouseClicked

    private void TableCreditosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableCreditosKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
        }
    }//GEN-LAST:event_TableCreditosKeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        LimpiarTabla();
        if (!"".equals(jTextField1.getText())) {
            if (isNumeric(jTextField1.getText()) == true) {
                Credito alistar = creditoDao.BuscarPorCodigo(Integer.parseInt(jTextField1.getText()));
                if (alistar.getId() == 0) {
                    modelo = (DefaultTableModel) TableCreditos.getModel();
                    TableCreditos.setModel(modelo);
                } else {
                    modelo = (DefaultTableModel) TableCreditos.getModel();
                    Object[] ob = new Object[8];
                    ob[0] = alistar.getId();
                    Clientes cliente = clienteDao.BuscarPorCodigo(alistar.getIdCliente());
                    ob[0] = alistar.getId();
                    if (cliente.getTipoPersona().equals("Persona Física")) {
                        ob[1] = alistar.getNombreCliente();
                    } else {
                        ob[1] = cliente.getNombreComercial();
                    }
                    ob[2] = alistar.getNombre() + " " + alistar.getApellidoP() + " " + alistar.getApellidoM();
                    ob[3] = determinarLimite(alistar.getFecha(), alistar.getVigencia());
                    ob[4] = "%" + df.format(alistar.getInteresMoratorio());
                    ob[5] = "%" + df.format(alistar.getInteresOrdinario());
                    ob[6] = "$" + formateador.format(alistar.getLimite());
                    ob[7] = "$" + formateador.format(alistar.getAdeudo());
                    modelo.addRow(ob);

                    TableCreditos.setModel(modelo);
                }
            } else {
                List<Credito> lsCredito = creditoDao.buscarLetra(jTextField1.getText());

                modelo = (DefaultTableModel) TableCreditos.getModel();
                Object[] ob = new Object[8];
                for (int i = 0; i < lsCredito.size(); i++) {
                    ob[0] = lsCredito.get(i).getId();
                    Clientes cliente = clienteDao.BuscarPorCodigo(lsCredito.get(i).getIdCliente());
                    ob[0] = lsCredito.get(i).getId();
                    if (cliente.getTipoPersona().equals("Persona Física")) {
                        ob[1] = lsCredito.get(i).getNombreCliente();
                    } else {
                        ob[1] = cliente.getNombreComercial();
                    }
                    ob[2] = lsCredito.get(i).getNombre() + " " + lsCredito.get(i).getApellidoP() + " " + lsCredito.get(i).getApellidoM();
                    ob[3] = determinarLimite(lsCredito.get(i).getFecha(), lsCredito.get(i).getVigencia());
                    ob[4] = "%" + df.format(lsCredito.get(i).getInteresMoratorio());
                    ob[5] = "%" + df.format(lsCredito.get(i).getInteresOrdinario());
                    ob[6] = "$" + formateador.format(lsCredito.get(i).getLimite());
                    ob[7] = "$" + formateador.format(lsCredito.get(i).getAdeudo());
                    modelo.addRow(ob);
                }
                TableCreditos.setModel(modelo);
            }
        } else {
            listarCreditos();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

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
            java.util.logging.Logger.getLogger(vistaCreditos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaCreditos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaCreditos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaCreditos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaCreditos dialog = new vistaCreditos(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableCreditos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane9;
    private textfield.TextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
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

}
