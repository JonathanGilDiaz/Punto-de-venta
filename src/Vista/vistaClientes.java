/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ClienteDao;
import Modelo.Clientes;
import Modelo.CorteDiarioDao;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import com.raven.model.StatusType;
import com.raven.swing.Table2;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Cursor;
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
import java.util.regex.Pattern;
import javaswingdev.Notification;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class vistaClientes extends javax.swing.JDialog {

    ClienteDao client = new ClienteDao();
    VentasDao ventas = new VentasDao();
    DefaultTableModel modelo = new DefaultTableModel();
    CorteDiarioDao corteDao = new CorteDiarioDao();
    bitacoraDao bitacora = new bitacoraDao();
    CreditoDao creditoDao = new CreditoDao();
    String hora;
    String fecha = corteDao.getDia();
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat df = new DecimalFormat("$ #,##0.00;($ #,##0.00)");

    int fila = -1;
    Empleados e;

    public void vaciarEmpleado(Empleados e) {
        this.e = e;
    }

    public vistaClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocation(200, 50);
                };

            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Clientes");
        Seticon();
        listarCliente();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocation(200, 50);
                };

            }
        });

        jScrollPane2.getViewport().setBackground(new Color(204, 204, 204));
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        TableClientes.getTableHeader().setBackground(Color.blue);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableClientes.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableClientes.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableClientes.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableClientes.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableClientes.getColumnModel().getColumn(4).setCellRenderer(tcr);
        TableClientes.getColumnModel().getColumn(6).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableClientes.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

        // Formatear la fecha como una cadena en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //     fecha = fechaSQL.toLocalDate().format(formatter);
        panelModificar.setBackground(new Color(179, 195, 219));
        panelEstado.setBackground(new Color(179, 195, 219));
        panelEliminar.setBackground(new Color(179, 195, 219));

    }

    public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private static boolean isSubstring(String s, String seq) {
        return Pattern.compile(Pattern.quote(seq), Pattern.CASE_INSENSITIVE)
                .matcher(s).find();
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

    public void listarCliente() {
        List<Clientes> ListaCl = client.ListarCliente();
        modelo = (DefaultTableModel) TableClientes.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < ListaCl.size(); i++) {
            ob[0] = ListaCl.get(i).getId();
            if (ListaCl.get(i).getTipoPersona().equals("Persona Física")) {
                ob[1] = ListaCl.get(i).getNombre() + " " + ListaCl.get(i).getApellidoP() + " " + ListaCl.get(i).getApellidoM();
            } else {
                ob[1] = ListaCl.get(i).getNombreComercial();
            }
            ob[2] = ListaCl.get(i).getCorreo();
            ob[3] = ListaCl.get(i).getTelefono();
            ob[4] = ListaCl.get(i).getRfc();
            if (ListaCl.get(i).getEstatus() == 1) {
                ob[5] = StatusType.ACTIVE;
            } else {
                ob[5] = StatusType.INACTIVE;
            }
            Credito cre = creditoDao.BuscarPorCodigoCliente(ListaCl.get(i).getId());
            if (cre.getId() != 0) {
                ob[6] = "Autorizado";
                System.out.println(cre.getId());
            } else {
                ob[6] = "No Autorizado";
            }

            modelo.addRow(ob);
        }
        TableClientes.setModel(modelo);
    }

    public void crearCliente() {
        CrearModificarCliente cc = new CrearModificarCliente(new javax.swing.JFrame(), true);
        cc.vaciarEmpleado(e.getId());
        Clientes cp = new Clientes();
        cc.vaciarDatos(fecha, cp, true);
        cc.setVisible(true);
        if (cc.accionCompletada == true) {
            LimpiarTabla();
            listarCliente();
            panelModificar.setBackground(new Color(179, 195, 219));
            panelEstado.setBackground(new Color(179, 195, 219));
            panelEliminar.setBackground(new Color(179, 195, 219));
            fila = -1;
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente creado correctamente");
            panel.showNotification();
        }
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

    public void reporteCliente() {
        vistaReportesClientes cvC = new vistaReportesClientes(new javax.swing.JFrame(), true);
        cvC.setLocation(200, 80);
        cvC.setVisible(true);
    }

    public void modificarCliente() {
        if (fila != -1) {
            CrearModificarCliente cc = new CrearModificarCliente(new javax.swing.JFrame(), true);
            cc.vaciarEmpleado(e.getId());
            Clientes cp = client.BuscarPorCodigo(Integer.parseInt(TableClientes.getValueAt(fila, 0).toString()));
            cc.vaciarDatos(fecha, cp, false);
            cc.setVisible(true);
            if (cc.accionCompletada == true) {
                LimpiarTabla();
                listarCliente();
                panelModificar.setBackground(new Color(179, 195, 219));
                panelEstado.setBackground(new Color(179, 195, 219));
                panelEliminar.setBackground(new Color(179, 195, 219));
                fila = -1;
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente modificado correctamente");
                panel.showNotification();
            }
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
            panel.showNotification();
        }
    }

    public void bloquearCliente() {
        if (fila != -1) {
            ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
            cc.vaciarEmpleado(e.getId());
            cc.setVisible(true);
            if (cc.contraseñaAceptada == true) {

                Clientes abloquear = client.BuscarPorCodigo(Integer.parseInt(TableClientes.getValueAt(fila, 0).toString()));
                if (abloquear.getEstatus() == 1) {
                    client.ModificarEstado(Integer.parseInt(TableClientes.getValueAt(fila, 0).toString()), 0);
                    bitacora.registrarRegistro("Estado de Cliente " + TableClientes.getValueAt(fila, 1).toString() + ": deshabilitado", cc.idEmpleado, fecha);
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente deshabilitada");
                    panel.showNotification();
                } else {
                    client.ModificarEstado(Integer.parseInt(TableClientes.getValueAt(fila, 0).toString()), 1);
                    bitacora.registrarRegistro("Estado de Cliente " + TableClientes.getValueAt(fila, 1).toString() + ": habilitado", cc.idEmpleado, fecha);
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente habilitada");
                    panel.showNotification();
                }
                LimpiarTabla();
                listarCliente();
                panelModificar.setBackground(new Color(179, 195, 219));
                panelEstado.setBackground(new Color(179, 195, 219));
                panelEliminar.setBackground(new Color(179, 195, 219));
                fila = -1;
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Se ha modificado el estado del cliente");
                panel.showNotification();
            }
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
            panel.showNotification();
        }
    }

    public void eliminarCliente() {
        if (fila != -1) {
            if (ventas.ventasCliente(Integer.parseInt(TableClientes.getValueAt(fila, 0).toString())) == false) {//si se va a eliminar
                ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(e.getId());
                cc.setVisible(true);
                if (cc.contraseñaAceptada == true) {
                    bitacora.registrarRegistro("Se eliminó Cliente " + TableClientes.getValueAt(fila, 1).toString(), cc.idEmpleado, fecha);
                    client.EliminarCliente(Integer.parseInt(TableClientes.getValueAt(fila, 0).toString()));
                    LimpiarTabla();
                    panelModificar.setBackground(new Color(179, 195, 219));
                    panelEstado.setBackground(new Color(179, 195, 219));
                    panelEliminar.setBackground(new Color(179, 195, 219));
                    fila = -1;
                    listarCliente();
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente eliminado");
                    panel.showNotification();
                }
            } else {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Ya cuenta con notas registradas");
                panel.showNotification();
            }
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
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
        jScrollPane2 = new javax.swing.JScrollPane();
        TableClientes = new javax.swing.JTable();
        panelModificar = new javax.swing.JPanel();
        btnModificar = new javax.swing.JLabel();
        panelReporte = new javax.swing.JPanel();
        btnReporte = new javax.swing.JLabel();
        panelNuevo = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();
        panelEstado = new javax.swing.JPanel();
        btnEstado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CLIENTES");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(667, 667, 667))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1023, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar cliente");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 440, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 106, 620, 60));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableClientes = new Table2();
        TableClientes.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Correo eléctronico", "Teléfono", "RFC", "Estatus", "Crédito"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableClientes.setToolTipText("Haz doble click para poder hacer modificaciones al cliente seleccionado");
        TableClientes.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClientesMouseClicked(evt);
            }
        });
        TableClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableClientesKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(TableClientes);
        if (TableClientes.getColumnModel().getColumnCount() > 0) {
            TableClientes.getColumnModel().getColumn(0).setResizable(false);
            TableClientes.getColumnModel().getColumn(0).setPreferredWidth(5);
            TableClientes.getColumnModel().getColumn(1).setResizable(false);
            TableClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
            TableClientes.getColumnModel().getColumn(2).setPreferredWidth(120);
            TableClientes.getColumnModel().getColumn(3).setResizable(false);
            TableClientes.getColumnModel().getColumn(3).setPreferredWidth(20);
            TableClientes.getColumnModel().getColumn(4).setPreferredWidth(20);
            TableClientes.getColumnModel().getColumn(5).setResizable(false);
            TableClientes.getColumnModel().getColumn(5).setPreferredWidth(60);
            TableClientes.getColumnModel().getColumn(6).setPreferredWidth(15);
        }

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 980, 430));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 1003, 450));

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
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MODIFICAR.png"))); // NOI18N
        btnModificar.setToolTipText("F3 - Modificar cliente");
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

        jPanel1.add(panelModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 50, 50));

        panelReporte.setBackground(new java.awt.Color(255, 255, 255));
        panelReporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelReporteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelReporteMouseEntered(evt);
            }
        });

        btnReporte.setBackground(new java.awt.Color(255, 255, 255));
        btnReporte.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ESTADISTICAS.png"))); // NOI18N
        btnReporte.setToolTipText("F2 - Reporte");
        btnReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReporteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReporteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReporteMouseExited(evt);
            }
        });
        btnReporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnReporteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelReporteLayout = new javax.swing.GroupLayout(panelReporte);
        panelReporte.setLayout(panelReporteLayout);
        panelReporteLayout.setHorizontalGroup(
            panelReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReporte, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelReporteLayout.setVerticalGroup(
            panelReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReporte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 50, 50));

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
        btnNuevo.setToolTipText("F1 - Nuevo cliente");
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

        panelEliminar.setBackground(new java.awt.Color(255, 255, 255));
        panelEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEliminarMouseEntered(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnEliminar.setToolTipText("F5 - Eliminar cliente");
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });
        btnEliminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelEliminarLayout = new javax.swing.GroupLayout(panelEliminar);
        panelEliminar.setLayout(panelEliminarLayout);
        panelEliminarLayout.setHorizontalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelEliminarLayout.setVerticalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 50, 50));

        panelEstado.setBackground(new java.awt.Color(255, 255, 255));
        panelEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEstadoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEstadoMouseEntered(evt);
            }
        });

        btnEstado.setBackground(new java.awt.Color(255, 255, 255));
        btnEstado.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnEstado.setForeground(new java.awt.Color(255, 255, 255));
        btnEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ESTADO.png"))); // NOI18N
        btnEstado.setToolTipText("F4 - Modificar estado");
        btnEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEstadoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstadoMouseExited(evt);
            }
        });
        btnEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEstadoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 50, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClientesMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TableClientes.rowAtPoint(evt.getPoint());
            panelModificar.setBackground(new Color(255, 255, 255));
            panelEstado.setBackground(new Color(255, 255, 255));
            panelEliminar.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_TableClientesMouseClicked

    private void TableClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableClientesKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                crearCliente();
                break;

            case KeyEvent.VK_F2:
                reporteCliente();
                break;

            case KeyEvent.VK_F3:
                modificarCliente();
                break;

            case KeyEvent.VK_F4:
                bloquearCliente();
                break;

            case KeyEvent.VK_F5:
                eliminarCliente();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_TableClientesKeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                crearCliente();
                break;

            case KeyEvent.VK_F2:
                reporteCliente();
                break;

            case KeyEvent.VK_F3:
                modificarCliente();
                break;

            case KeyEvent.VK_F4:
                bloquearCliente();
                break;

            case KeyEvent.VK_F5:
                eliminarCliente();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        fila = -1;
        panelModificar.setBackground(new Color(179, 195, 219));
        panelEstado.setBackground(new Color(179, 195, 219));
        panelEliminar.setBackground(new Color(179, 195, 219));
        LimpiarTabla();
        if (!"".equals(jTextField1.getText())) {
            if (isNumeric(jTextField1.getText()) == true) {
                Clientes alistar = client.BuscarPorCodigo(Integer.parseInt(jTextField1.getText()));
                if (alistar.getId() == 0) {
                    modelo = (DefaultTableModel) TableClientes.getModel();
                    TableClientes.setModel(modelo);
                } else {
                    modelo = (DefaultTableModel) TableClientes.getModel();
                    Object[] ob = new Object[7];
                    ob[0] = alistar.getId();
                    if (alistar.getTipoPersona().equals("Persona Física")) {
                        ob[1] = alistar.getNombre() + " " + alistar.getApellidoP() + " " + alistar.getApellidoM();
                    } else {
                        ob[1] = alistar.getNombreComercial();
                    }
                    ob[2] = alistar.getCorreo();
                    ob[3] = alistar.getTelefono();
                    ob[4] = alistar.getRfc();
                    if (alistar.getEstatus() == 1) {
                        ob[5] = StatusType.ACTIVE;
                    } else {
                        ob[5] = StatusType.INACTIVE;
                    }
                    Credito cre = creditoDao.BuscarPorCodigoCliente(alistar.getId());
                    if (cre.getId() != 0) {
                        ob[6] = "Autorizado";
                        System.out.println(cre.getId());
                    } else {
                        ob[6] = "No Autorizado";
                    }
                    modelo.addRow(ob);

                    TableClientes.setModel(modelo);
                }
            } else {
                modelo = (DefaultTableModel) TableClientes.getModel();
                Object[] ob = new Object[7];
                List<Clientes> lista = client.buscarLetra(jTextField1.getText());
                for (int i = 0; i < lista.size(); i++) {
                    String nombreCompleto = lista.get(i).getNombre() + " " + lista.get(i).getApellidoP() + " " + lista.get(i).getApellidoM();
                    if (isSubstring(nombreCompleto, jTextField1.getText())) {
                        ob[0] = lista.get(i).getId();
                        if (lista.get(i).getTipoPersona().equals("Persona Física")) {
                            ob[1] = lista.get(i).getNombre() + " " + lista.get(i).getApellidoP() + " " + lista.get(i).getApellidoM();
                        } else {
                            ob[1] = lista.get(i).getNombreComercial();
                        }
                        ob[2] = lista.get(i).getCorreo();
                        ob[3] = lista.get(i).getTelefono();
                        ob[4] = lista.get(i).getRfc();
                        if (lista.get(i).getEstatus() == 1) {
                            ob[5] = StatusType.ACTIVE;
                        } else {
                            ob[5] = StatusType.INACTIVE;
                        }
                        Credito cre = creditoDao.BuscarPorCodigoCliente(lista.get(i).getId());
                        if (cre.getId() != 0) {
                            ob[6] = "Autorizado";
                            System.out.println(cre.getId());
                        } else {
                            ob[6] = "No Autorizado";
                        }
                        modelo.addRow(ob);
                    }
                }
                TableClientes.setModel(modelo);

            }

        } else {
            listarCliente();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                crearCliente();
                break;

            case KeyEvent.VK_F2:
                reporteCliente();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        modificarCliente();
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseEntered
        panelModificar.setBackground(new Color(179, 195, 219));
        if (fila != -1) {
            panelModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnModificarMouseEntered

    private void btnModificarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseExited
        if (fila != -1) {
            panelModificar.setBackground(new Color(255, 255, 255));
            btnModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelModificar.setBackground(new Color(179, 195, 219));
            btnModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnModificarMouseExited

    private void btnModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                crearCliente();
                break;

            case KeyEvent.VK_F2:
                reporteCliente();
                break;

            case KeyEvent.VK_F3:
                modificarCliente();
                break;

            case KeyEvent.VK_F4:
                bloquearCliente();
                break;

            case KeyEvent.VK_F5:
                eliminarCliente();
                break;

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

    private void btnReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseClicked
        reporteCliente();

    }//GEN-LAST:event_btnReporteMouseClicked

    private void btnReporteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseEntered
        panelReporte.setBackground(new Color(179, 195, 219));
        btnReporte.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btnReporteMouseEntered

    private void btnReporteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseExited
        panelReporte.setBackground(new Color(255, 255, 255));

    }//GEN-LAST:event_btnReporteMouseExited

    private void btnReporteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnReporteKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                crearCliente();
                break;

            case KeyEvent.VK_F2:
                reporteCliente();
                break;

            case KeyEvent.VK_F3:
                modificarCliente();
                break;

            case KeyEvent.VK_F4:
                bloquearCliente();
                break;

            case KeyEvent.VK_F5:
                eliminarCliente();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnReporteKeyPressed

    private void panelReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReporteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelReporteMouseClicked

    private void panelReporteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReporteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelReporteMouseEntered

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        crearCliente();
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
                crearCliente();
                break;

            case KeyEvent.VK_F2:
                reporteCliente();
                break;

            case KeyEvent.VK_F3:
                modificarCliente();
                break;

            case KeyEvent.VK_F4:
                bloquearCliente();
                break;

            case KeyEvent.VK_F5:
                eliminarCliente();
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

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        eliminarCliente();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        if (fila != -1) {
            btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            btnEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        panelEliminar.setBackground(new Color(179, 195, 219));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        if (fila != -1) {
            panelEliminar.setBackground(new Color(255, 255, 255));
            btnEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelEliminar.setBackground(new Color(179, 195, 219));
            btnEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                crearCliente();
                break;

            case KeyEvent.VK_F2:
                reporteCliente();
                break;

            case KeyEvent.VK_F3:
                modificarCliente();
                break;

            case KeyEvent.VK_F4:
                bloquearCliente();
                break;

            case KeyEvent.VK_F5:
                eliminarCliente();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnEliminarKeyPressed

    private void panelEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminarMouseClicked

    private void panelEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminarMouseEntered

    private void btnEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoMouseClicked
        bloquearCliente();
    }//GEN-LAST:event_btnEstadoMouseClicked

    private void btnEstadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoMouseEntered
        panelEstado.setBackground(new Color(179, 195, 219));
        if (fila != -1) {
            panelEstado.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelEstado.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnEstadoMouseEntered

    private void btnEstadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoMouseExited
        if (fila != -1) {
            panelEstado.setBackground(new Color(255, 255, 255));
            btnEstado.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            panelEstado.setBackground(new Color(179, 195, 219));
            btnEstado.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnEstadoMouseExited

    private void btnEstadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEstadoKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                crearCliente();
                break;

            case KeyEvent.VK_F2:
                reporteCliente();
                break;

            case KeyEvent.VK_F3:
                modificarCliente();
                break;

            case KeyEvent.VK_F4:
                bloquearCliente();
                break;

            case KeyEvent.VK_F5:
                eliminarCliente();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnEstadoKeyPressed

    private void panelEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEstadoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEstadoMouseClicked

    private void panelEstadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEstadoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEstadoMouseEntered

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
            java.util.logging.Logger.getLogger(vistaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaClientes dialog = new vistaClientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableClientes;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnEstado;
    private javax.swing.JLabel btnModificar;
    private javax.swing.JLabel btnNuevo;
    private javax.swing.JLabel btnReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private textfield.TextField jTextField1;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JPanel panelModificar;
    private javax.swing.JPanel panelNuevo;
    private javax.swing.JPanel panelReporte;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    private static CellStyle getContabilidadCellStyle(Workbook workbook, DecimalFormat df) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat(df.format(0)));
        return style;
    }

}
