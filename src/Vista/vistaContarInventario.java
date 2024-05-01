/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.HistorialCliente;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.bitacoraDao;
import Modelo.config;
import Modelo.configuraciones;
import Modelo.conteoInventario;
import Modelo.conteoInventarioDao;
import Modelo.detalleConteo;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.Notification;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class vistaContarInventario extends javax.swing.JDialog {

    DefaultTableModel modelo = new DefaultTableModel();
    String fecha, hora;
    DecimalFormat formato = new DecimalFormat("0.00");
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    int fila = -1;
    int idEmpleado;
    boolean bandera;
    int folio;

    ProductoDao productoDao = new ProductoDao();
    conteoInventarioDao conteoDao = new conteoInventarioDao();
    bitacoraDao bitacora = new bitacoraDao();
    EmpleadosDao emple = new EmpleadosDao();
    configuraciones configuracionesDao = new configuraciones();
    conteoInventario miConteo;

    public void vaciarEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public vistaContarInventario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Conteo de inventario fisico");
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
        jScrollPane2.getViewport().setBackground(new Color(204, 204, 204));
        TableProductos.getTableHeader().setBackground(Color.blue);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TableProductos.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableProductos.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableProductos.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableProductos.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableProductos.getColumnModel().getColumn(4).setCellRenderer(tcr);
        TableProductos.getColumnModel().getColumn(5).setCellRenderer(tcr);
        ((DefaultTableCellRenderer) TableProductos.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

        // Formatear la fecha como una cadena en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fecha = fechaSQL.toLocalDate().format(formatter);
    }

    public void validar(boolean bandera, int folio) {
        this.bandera = bandera;
        this.folio = folio;
        if (bandera == true) {//nuevo
            vaciarTabla();
            txtFolio.setText((1 + conteoDao.idLinea()) + "");
            txtFecha.setText(fechaFormatoCorrecto(fecha));
            panelGuardar.setBackground(new Color(255, 255, 255));
            panelModificar.setBackground(new Color(255, 255, 255));
            panelPdf.setBackground(new Color(179, 195, 219));
        } else {
            panelGuardar.setBackground(new Color(179, 195, 219));
            panelModificar.setBackground(new Color(179, 195, 219));
            panelPdf.setBackground(new Color(255, 255, 255));
            conteoInventario conte = conteoDao.buscarPorFolio(folio);
            this.miConteo = conte;
            txtFecha.setText(fechaFormatoCorrecto(conte.getFecha()));
            txtFolio.setText("" + conte.getId());
            modelo = (DefaultTableModel) TableProductos.getModel();
            List<detalleConteo> lsConteo = conteoDao.listarDetalles(conte.getId());

            for (int i = 0; i < lsConteo.size(); i++) {
                Object[] o = new Object[6];
                o[0] = lsConteo.get(i).getCodigo();
                o[1] = lsConteo.get(i).getDescripcion();
                o[2] = lsConteo.get(i).getUnidad();
                o[3] = formato.format(lsConteo.get(i).getPiezas());
                o[4] = formato.format(lsConteo.get(i).getFisico());
                o[5] = formato.format(lsConteo.get(i).getDiferencia());
                modelo.addRow(o);
            }
            TableProductos.setModel(modelo);
        }
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
        if (bandera == true) {
            boolean bandera2 = true;
            for (int i = 0; i < TableProductos.getRowCount(); i++) {
                if (TableProductos.getValueAt(i, 4).toString().equals("")) {
                    bandera2 = false;
                }
                if (TableProductos.getValueAt(i, 5).toString().equals("")) {
                    bandera2 = false;
                }
            }
            if (bandera2 == true) {
                double diferencia = 0;
                List<Producto> lsProducto = productoDao.listarProductosConInventario();
                for (int i = 0; i < TableProductos.getRowCount(); i++) {
                    for (int k = 0; k < lsProducto.size(); k++) {
                        if (TableProductos.getValueAt(i, 0).toString().equals(lsProducto.get(k).getCodigo())) {
                            diferencia = diferencia + (lsProducto.get(k).getPrecioCompra() * Double.parseDouble(TableProductos.getValueAt(i, 5).toString()));
                        }
                    }
                }
                guardarConteo vC = new guardarConteo(new javax.swing.JFrame(), true);
                vC.vaciarEmpleado(idEmpleado);
                vC.vaciarDatos(fechaFormatoCorrecto(fecha), diferencia);
                vC.setVisible(true);
                if (vC.accionRealizada == true) {
                    conteoInventario con = new conteoInventario();
                    con.setDiferencia(diferencia);
                    con.setObservaciones(vC.observacion);
                    con.setDinero(vC.dinero);
                    con.setFecha(fecha);
                    con.setIdEmpleado(idEmpleado);
                    conteoDao.registrarConteo(con);
                    registrarDetalles();
                    bitacora.registrarRegistro("Se realizo un conteo del inventario, actualizando existencias", idEmpleado, fecha);
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Conteo registrado");
                    panel.showNotification();
                    dispose();
                }
            } else {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Har articulos que no han sido contados");
                panel.showNotification();
            }
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Solo se visualiza el conteo");
            panel.showNotification();
        }
    }

    public void eventoF1() {
        if (bandera == true) {
            for (int i = 0; i < TableProductos.getRowCount(); i++) {
                TableProductos.setValueAt("", i, 4);
                TableProductos.setValueAt("", i, 5);
            }
        } else {
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Solo se visualiza el conteo");
            panel.showNotification();
        }
    }

    public void eventoF3() {
        if (bandera == false) {
            try {
                int id = Integer.parseInt(txtFolio.getText());
                File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\PDF\\conteoInventario" + txtFolio.getText() + ".pdf");
                pdfConteo(id);
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
            } catch (ParseException ex) {
                Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void registrarDetalles() {
        int id = conteoDao.idLinea();
        List<detalleConteo> lsConteo = new ArrayList<detalleConteo>();
        for (int i = 0; i < TableProductos.getRowCount(); i++) {
            detalleConteo conteo = new detalleConteo();
            conteo.setIdConteo(id);
            conteo.setCodigo(TableProductos.getValueAt(i, 0).toString());
            conteo.setDescripcion(TableProductos.getValueAt(i, 1).toString());
            conteo.setUnidad(TableProductos.getValueAt(i, 2).toString());
            conteo.setPiezas(Double.parseDouble(TableProductos.getValueAt(i, 3).toString()));
            conteo.setFisico(Double.parseDouble(TableProductos.getValueAt(i, 4).toString()));
            conteo.setDiferencia(Double.parseDouble(TableProductos.getValueAt(i, 5).toString()));
            conteoDao.registrarDetalle(conteo);
            Producto p = productoDao.BuscarPorCodigo(conteo.getCodigo());
            p.setExistencia(conteo.getFisico());
            productoDao.conteoNuevaExistencia(p);
        }

    }

    public void vaciarTabla() {
        modelo = (DefaultTableModel) TableProductos.getModel();
        List<Producto> lsProducto = productoDao.listarProductosConInventario();
        Object[] ob = new Object[6];
        for (int i = lsProducto.size() - 1; i >= 0; i--) {
            ob[0] = lsProducto.get(i).getCodigo();
            ob[1] = lsProducto.get(i).getDescripcion();
            ob[2] = lsProducto.get(i).getTipoVenta();
            ob[3] = formato.format(lsProducto.get(i).getExistencia());
            ob[4] = "";
            ob[5] = "";
            modelo.addRow(ob);
        }
        TableProductos.setModel(modelo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableProductos = new javax.swing.JTable();
        panelModificar = new javax.swing.JPanel();
        btnModificar = new javax.swing.JLabel();
        panelGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        txtFecha = new textfield.TextField();
        txtFolio = new textfield.TextField();
        panelPdf = new javax.swing.JPanel();
        btnReporte1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CONTEO DE INVENTARIO");

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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProductos = new Table3();
        TableProductos.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Unidad", "Piezas", "Físico", "Diferencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableProductos.setToolTipText("Haz doble click para introducir las piezas en Físico");
        TableProductos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductosMouseClicked(evt);
            }
        });
        TableProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableProductosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableProductosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(TableProductos);
        if (TableProductos.getColumnModel().getColumnCount() > 0) {
            TableProductos.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableProductos.getColumnModel().getColumn(1).setPreferredWidth(80);
            TableProductos.getColumnModel().getColumn(2).setPreferredWidth(20);
            TableProductos.getColumnModel().getColumn(3).setPreferredWidth(20);
            TableProductos.getColumnModel().getColumn(4).setPreferredWidth(30);
            TableProductos.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 980, 430));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 1003, 450));

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
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnModificar.setToolTipText("F1 - Limpiar datos");
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarMouseClicked(evt);
                btnMOdificarMouseClicked2(evt);
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

        jPanel1.add(panelModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 50, 50));

        panelGuardar.setBackground(new java.awt.Color(255, 255, 255));
        panelGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelGuardarMouseEntered(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnGuardar.setToolTipText("F2 - Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGuardarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelGuardarLayout = new javax.swing.GroupLayout(panelGuardar);
        panelGuardar.setLayout(panelGuardarLayout);
        panelGuardarLayout.setHorizontalGroup(
            panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelGuardarLayout.setVerticalGroup(
            panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 55, 50, 50));

        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFecha.setLabelText("Fecha");
        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFechaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFechaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaKeyTyped(evt);
            }
        });
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 60, 170, -1));

        txtFolio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFolio.setEnabled(false);
        txtFolio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFolio.setLabelText("Folio");
        txtFolio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFolioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFolioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFolioKeyTyped(evt);
            }
        });
        jPanel1.add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 170, -1));

        panelPdf.setBackground(new java.awt.Color(255, 255, 255));
        panelPdf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelPdfMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelPdfMouseEntered(evt);
            }
        });

        btnReporte1.setBackground(new java.awt.Color(255, 255, 255));
        btnReporte1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnReporte1.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnReporte1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen1.png"))); // NOI18N
        btnReporte1.setToolTipText("F3 - Visualizar PDF");
        btnReporte1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReporte1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReporte1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReporte1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReporte1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelPdfLayout = new javax.swing.GroupLayout(panelPdf);
        panelPdf.setLayout(panelPdfLayout);
        panelPdfLayout.setHorizontalGroup(
            panelPdfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReporte1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelPdfLayout.setVerticalGroup(
            panelPdfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReporte1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 55, 50, 50));

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

    private void TableProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductosMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TableProductos.rowAtPoint(evt.getPoint());
            if (bandera == true) {
                piezasExistentes vC1 = new piezasExistentes(new javax.swing.JFrame(), true);
                vC1.vaciarProducto(productoDao.BuscarPorCodigo(TableProductos.getValueAt(fila, 0).toString()));
                vC1.setVisible(true);
                if (vC1.accionRealizada == true) {
                    TableProductos.setValueAt(formato.format(vC1.cantidadModificada), fila, 4);
                    TableProductos.setValueAt(formato.format(vC1.diferencia), fila, 5);
                }
            }
        }

    }//GEN-LAST:event_TableProductosMouseClicked

    private void TableProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableProductosKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_TableProductosKeyPressed

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseEntered
        if (bandera == true) {
            panelModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        panelModificar.setBackground(new Color(179, 195, 219));
    }//GEN-LAST:event_btnModificarMouseEntered

    private void btnModificarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseExited
        if (bandera == true) {
            panelModificar.setBackground(new Color(255, 255, 255));
        } else
            panelModificar.setBackground(new Color(179, 195, 219));
    }//GEN-LAST:event_btnModificarMouseExited

    private void btnModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;
        }
    }//GEN-LAST:event_btnModificarKeyPressed

    private void panelModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelModificarMouseClicked

    private void panelModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelModificarMouseEntered

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        eventoF2();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        if (bandera == true) {
            panelGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelGuardar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        panelGuardar.setBackground(new Color(179, 195, 219));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        if (bandera == true) {
            panelGuardar.setBackground(new Color(255, 255, 255));
        } else
            panelGuardar.setBackground(new Color(179, 195, 219));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

        }
    }//GEN-LAST:event_btnGuardarKeyPressed

    private void panelGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuardarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGuardarMouseClicked

    private void panelGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuardarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGuardarMouseEntered

    private void TableProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableProductosKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

        }
    }//GEN-LAST:event_TableProductosKeyReleased

    private void btnMOdificarMouseClicked2(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMOdificarMouseClicked2
        eventoF1();
    }//GEN-LAST:event_btnMOdificarMouseClicked2

    private void txtFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyPressed

    }//GEN-LAST:event_txtFechaKeyPressed

    private void txtFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;
        }
    }//GEN-LAST:event_txtFechaKeyReleased

    private void txtFechaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyTyped

    }//GEN-LAST:event_txtFechaKeyTyped

    private void txtFolioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFolioKeyPressed

    private void txtFolioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

            case KeyEvent.VK_F2:
                eventoF2();
                break;

            case KeyEvent.VK_F3:
                eventoF3();
                break;
        }
    }//GEN-LAST:event_txtFolioKeyReleased

    private void txtFolioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFolioKeyTyped

    private void btnReporte1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporte1MouseClicked
        eventoF3();
    }//GEN-LAST:event_btnReporte1MouseClicked

    private void btnReporte1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporte1MouseEntered
        if (bandera == false) {
            panelPdf.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            panelPdf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        panelPdf.setBackground(new Color(179, 195, 219));
    }//GEN-LAST:event_btnReporte1MouseEntered

    private void btnReporte1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporte1MouseExited
        if (bandera == false) {
            panelPdf.setBackground(new Color(255, 255, 255));
        } else
            panelPdf.setBackground(new Color(179, 195, 219));
    }//GEN-LAST:event_btnReporte1MouseExited

    private void panelPdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPdfMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelPdfMouseClicked

    private void panelPdfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPdfMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelPdfMouseEntered

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
            java.util.logging.Logger.getLogger(HistorialCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistorialCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistorialCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistorialCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaContarInventario dialog = new vistaContarInventario(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableProductos;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnModificar;
    private javax.swing.JLabel btnReporte1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JPanel panelModificar;
    private javax.swing.JPanel panelPdf;
    private textfield.TextField txtFecha;
    private textfield.TextField txtFolio;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    private static CellStyle getContabilidadCellStyle(Workbook workbook, DecimalFormat df) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat(df.format(0)));
        return style;
    }

    private void pdfConteo(int id) throws ParseException {
        try {
            FileOutputStream archivo;
            File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\PDF\\conteoInventario" + txtFolio.getText() + ".pdf");

            //File file = new File("src/pdf/ticket" + id + ".pdf");
            //File file = new File("C:\\Program Files (x86)\\AppLavanderia\\ticket" + id + ".pdf");
            //File file = new File("C:\\Program Files (x86)\\AppLavanderia\\corte" + fechaHoy + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            //Image img = Image.getInstance("src/Imagenes/logo 100x100.jpg");
            Image img = Image.getInstance("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg");
            config configura = configuracionesDao.buscarDatos();

            Font letra2 = new Font(Font.FontFamily.TIMES_ROMAN, 13);
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);

            PdfPTable logo = new PdfPTable(5);
            logo.setWidthPercentage(100);
            logo.getDefaultCell().setBorder(0);
            float[] columnaEncabezadoLogo = new float[]{20f, 20, 60f, 20f, 20f};
            logo.setWidths(columnaEncabezadoLogo);
            logo.setHorizontalAlignment(Chunk.ALIGN_MIDDLE);
            logo.addCell("");
            logo.addCell("");
            logo.addCell(img);
            logo.addCell("");
            logo.addCell("");
            doc.add(logo);

            String rfc = configura.getRfc();
            String nombre = configura.getNomnbre();
            String tel = configura.getTelefono();
            String direccion = configura.getDireccion();
            String encargado = configura.getEncargado();
            String razonSocial = configura.getRazonSocial();
            String horario = configura.getHorario();

            PdfPTable encabezado = new PdfPTable(1);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            float[] columnaEncabezado = new float[]{100f};
            encabezado.setWidths(columnaEncabezado);
            encabezado.setHorizontalAlignment(Chunk.ALIGN_MIDDLE);
            PdfPCell cell = new PdfPCell();
            cell = new PdfPCell(new Phrase(nombre, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(encargado, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase("RFC: " + rfc, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(razonSocial, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(horario, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(direccion, negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(tel + "\nCONTEO DE INVENTARIO", negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);

            Paragraph dejarEspacio = new Paragraph();
            dejarEspacio.add(Chunk.NEWLINE);
            doc.add(dejarEspacio);

            doc.add(encabezado);
            doc.add(dejarEspacio);

            PdfPTable tablapro = new PdfPTable(2);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{60f, 40f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Recepción: " + txtFecha.getText(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Folio: " + txtFolio.getText(), negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            tablapro = new PdfPTable(1);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{100f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            Empleados emplead = emple.seleccionarEmpleado("", miConteo.getIdEmpleado());
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Recibe: " + String.format("%0" + 2 + "d", Integer.valueOf(emplead.getId())) + " " + emplead.getNombre(), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);

            doc.add(dejarEspacio);

            letra2 = new Font(Font.FontFamily.TIMES_ROMAN, 11);
            tablapro = new PdfPTable(6);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{40f, 40f, 20f, 25f, 25f, 30f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            PdfPCell pro1 = new PdfPCell(new Phrase("Código", letra2));
            PdfPCell pro2 = new PdfPCell(new Phrase("Descripción", letra2));
            PdfPCell pro3 = new PdfPCell(new Phrase("Unidad", letra2));
            PdfPCell pro4 = new PdfPCell(new Phrase("Piezas", letra2));
            PdfPCell pro5 = new PdfPCell(new Phrase("Físico", letra2));
            PdfPCell pro6 = new PdfPCell(new Phrase("Diferencia", letra2));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro6.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);
            tablapro.addCell(pro6);

            for (int i = 0; i < TableProductos.getRowCount(); i++) {
                for (int k = 0; k < 6; k++) {
                    cell = new PdfPCell();
                    cell = new PdfPCell(new Phrase(TableProductos.getValueAt(i, k).toString(), letra2));
                    if (k > 3) {
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    } else {
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    }
                    cell.setBorder(0);
                    tablapro.addCell(cell);
                }
            }

            doc.add(tablapro);
            doc.add(dejarEspacio);

            tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{25f, 65f, 14f, 17f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Diferencia ($)", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("$" + formateador.format(miConteo.getDinero()), letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);

            doc.add(tablapro);
            doc.add(dejarEspacio);
            doc.add(dejarEspacio);

            PdfPTable tablaFinal = new PdfPTable(3);
            tablaFinal.setWidthPercentage(100);
            tablaFinal.getDefaultCell().setBorder(0);
            float[] columnapro = new float[]{50f, 50f, 50f};
            tablaFinal.setWidths(columnapro);
            tablaFinal.setHorizontalAlignment(Element.ALIGN_LEFT);
            for (int i = 0; i < 3; i++) {
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("____________________"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                tablaFinal.addCell(cell);
            }

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Elaboró", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Gerencia", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell);

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Auditoria", letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            tablaFinal.addCell(cell);

            Paragraph primero = new Paragraph();
            primero.setFont(negrita);
            primero.add(Chunk.NEWLINE);
            primero.add(Chunk.NEWLINE);
            primero.add(Chunk.NEWLINE);
            primero.add(Chunk.NEWLINE);
            doc.add(primero);

            doc.add(tablaFinal);
            doc.add(dejarEspacio);

            Paragraph notasCliente = new Paragraph("Observaciones: " + miConteo.getObservaciones(), letra2);
            doc.add(notasCliente);
            doc.close();
            archivo.close();
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

}
