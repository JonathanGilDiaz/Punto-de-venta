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
import Modelo.configuraciones;
import Modelo.reportes;
import com.raven.swing.Table3;
import java.awt.Color;
import java.awt.Cursor;
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
import java.util.ArrayList;
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
public class vistaReportesClientes extends javax.swing.JDialog {

    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
    DefaultTableCellRenderer tcrderecha = new DefaultTableCellRenderer();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modeloTotales = new DefaultTableModel();
    reportes reporte = new reportes();
    ClienteDao client = new ClienteDao();
    String fecha;
    CreditoDao creditoDao = new CreditoDao();
    configuraciones configuracionesDao = new configuraciones();
    EmpleadosDao emple = new EmpleadosDao();
    List<Clientes> lsC = client.ListarCliente();
    List<Credito> lsCredito = creditoDao.listarCreditos();
    List<Integer> listaFolios = new ArrayList<Integer>();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    int apoyoR;
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public vistaReportesClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
        this.setTitle("Punto de venta - Datos de clientes");
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
        TableLista.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableLista.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableLista.getColumnModel().getColumn(4).setCellRenderer(tcr);
        TableLista.getColumnModel().getColumn(5).setCellRenderer(tcr);
        TableLista.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TableLista.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);

        ((DefaultTableCellRenderer) TableLista.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        TableTotales.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableTotales.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableTotales.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableTotales.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableTotales.getColumnModel().getColumn(4).setCellRenderer(tcr);
        TableTotales.getColumnModel().getColumn(5).setCellRenderer(tcr);
        TableTotales.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TableTotales.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);

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
        vaciarATabla(lsC);
    }

    public void vaciarATabla(List<Clientes> lsVentas) {
        double credito = 0;
        double adeudo = 0;

        modelo = (DefaultTableModel) TableLista.getModel();
        Object[] ob = new Object[8];
        for (int i = lsVentas.size() - 1; i >= 0; i--) {//las vaciamos de la mas reciente a la mas vieja
            if (lsVentas.get(i).getTipoPersona().equals("Persona Física")) {
                ob[0] = lsVentas.get(i).getNombre() + " " + lsVentas.get(i).getApellidoP() + " " + lsVentas.get(i).getApellidoM();
                ob[1] = "Física";
            } else {
                ob[0] = lsVentas.get(i).getNombreComercial();
                ob[1] = "Moral";
            }
            ob[2] = lsVentas.get(i).getRfc();
            ob[3] = lsVentas.get(i).getTelefono();
            ob[4] = lsVentas.get(i).getCorreo();
            ob[5] = lsVentas.get(i).getCalle() + " " + lsVentas.get(i).getNumeroInterior() + " " + lsVentas.get(i).getNumeroExterior() + " " + lsVentas.get(i).getColonia() + " " + lsVentas.get(i).getMunicipio() + " " + lsVentas.get(i).getEstado() + " " + lsVentas.get(i).getCodigoPostal();
            ob[6] = "-";
            ob[7] = "-";
            for (int k = 0; k < lsCredito.size(); k++) {
                if (lsVentas.get(i).getId() == lsCredito.get(k).getIdCliente()) {
                    ob[6] = "$" + formateador.format(lsCredito.get(k).getLimite());
                    credito = credito + lsCredito.get(k).getLimite();
                    ob[7] = "$" + formateador.format(lsCredito.get(k).getAdeudo());
                    adeudo = adeudo + lsCredito.get(k).getAdeudo();
                }
            }

            modelo.addRow(ob);
        }
        TableLista.setModel(modelo);

        modeloTotales = (DefaultTableModel) TableTotales.getModel();
        ob[0] = "";
        ob[1] = "";
        ob[2] = "";
        ob[3] = "";
        ob[4] = "";
        ob[5] = "";
        ob[6] = "$" + formateador.format(credito);
        ob[7] = "$" + formateador.format(adeudo);

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
        panelModificar = new javax.swing.JPanel();
        btnModificar = new javax.swing.JLabel();

        jDateChooser2.setForeground(new java.awt.Color(51, 102, 255));
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        jDateChooser1.setForeground(new java.awt.Color(51, 102, 255));
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
        Titulo.setText("DATOS DE CLIENTES");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(520, Short.MAX_VALUE))
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
                "-", "-", "-", "-", "-", "-", "Total", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            TableTotales.getColumnModel().getColumn(0).setPreferredWidth(60);
            TableTotales.getColumnModel().getColumn(1).setPreferredWidth(15);
            TableTotales.getColumnModel().getColumn(2).setPreferredWidth(20);
            TableTotales.getColumnModel().getColumn(3).setPreferredWidth(20);
            TableTotales.getColumnModel().getColumn(4).setPreferredWidth(35);
            TableTotales.getColumnModel().getColumn(5).setPreferredWidth(35);
            TableTotales.getColumnModel().getColumn(6).setPreferredWidth(25);
            TableTotales.getColumnModel().getColumn(7).setPreferredWidth(25);
        }

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 920, 40));

        TableLista = new Table3();
        TableLista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Persona", "RFC", "Teléfono", "Correo", "Dirección", "Crédito", "Adeudo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            TableLista.getColumnModel().getColumn(0).setPreferredWidth(70);
            TableLista.getColumnModel().getColumn(1).setPreferredWidth(10);
            TableLista.getColumnModel().getColumn(2).setPreferredWidth(20);
            TableLista.getColumnModel().getColumn(3).setPreferredWidth(15);
            TableLista.getColumnModel().getColumn(4).setPreferredWidth(35);
            TableLista.getColumnModel().getColumn(5).setPreferredWidth(35);
            TableLista.getColumnModel().getColumn(6).setPreferredWidth(15);
            TableLista.getColumnModel().getColumn(7).setPreferredWidth(15);
        }

        jPanel4.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 440));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 113, 940, 510));

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

        panelModificar.setBackground(new java.awt.Color(255, 255, 255));
        panelModificar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelModificarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelModificarMouseEntered(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(255, 255, 255));
        btnModificar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/excel.png"))); // NOI18N
        btnModificar.setToolTipText("F2 - Visualizar en Excel");
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarMouseExited(evt);
            }
        });
        btnModificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelModificarLayout = new javax.swing.GroupLayout(panelModificar);
        panelModificar.setLayout(panelModificarLayout);
        panelModificarLayout.setHorizontalGroup(
            panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelModificarLayout.setVerticalGroup(
            panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 50, 50));

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

        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Generando reporte");
        panel.showNotification();
        try {
            reporte.reportePDFSinTotal("Datos de clientes", TableLista, fecha, fecha, "\nDATOS DE CLIENTES" + "\nFECHA DE EMISION " + DiagHoy);
        } catch (DocumentException ex) {
            Logger.getLogger(vistaReportesClientes.class.getName()).log(Level.SEVERE, null, ex);
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

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        reporte.reporteExcelSinTotales("Datos de clientes", TableLista);
        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Generando reporte");
        panel.showNotification();
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseEntered
        panelModificar.setBackground(new Color(153, 204, 255));
        btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }//GEN-LAST:event_btnModificarMouseEntered

    private void btnModificarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseExited
        panelModificar.setBackground(new Color(255, 255, 255));
        btnModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnModificarMouseExited

    private void btnModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnModificarKeyPressed

    private void panelModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelModificarMouseClicked

    private void panelModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelModificarMouseEntered

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
            java.util.logging.Logger.getLogger(vistaReportesClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaReportesClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaReportesClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaReportesClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaReportesClientes dialog = new vistaReportesClientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnModificar;
    private javax.swing.JLabel btnNuevo;
    private com.raven.datechooser.DateChooser jDateChooser1;
    private com.raven.datechooser.DateChooser jDateChooser2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel panelModificar;
    private javax.swing.JPanel panelNuevo;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

}
