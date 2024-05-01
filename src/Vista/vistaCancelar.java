/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ClienteDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.HistorialCliente;
import Modelo.HistorialClienteDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.Ventas;
import Modelo.VentasDao;
import Modelo.detalleVenta;
import Modelo.notaCredito;
import Modelo.notaCreditoDao;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javaswingdev.Notification;

public class vistaCancelar extends javax.swing.JDialog implements Runnable {

    int folio;
    EmpleadosDao emple = new EmpleadosDao();
    VentasDao ventaDao = new VentasDao();
    ClienteDao client = new ClienteDao();
    CreditoDao creditoDao = new CreditoDao();
    notaCreditoDao notaDao = new notaCreditoDao();
    ProductoDao productoDao = new ProductoDao();
    HistorialClienteDao historialDao = new HistorialClienteDao();

    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    boolean accionRealizada = false;
    String hora, min, seg, ampm;
    String fecha, hora1;
    boolean indicador;
    double saldo;
    Ventas venta;
    Calendar calendario;
    Thread hi;
    int idEmpleado;

    public void vaciarEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public vistaCancelar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Cancelar venta");
        Seticon();
        hi = new Thread(this);
        hi.start();
        txtFecha.setBackground(new Color(240, 240, 240));
        txtHora.setBackground(new Color(240, 240, 240));
        txtMonto.setBackground(new Color(240, 240, 240));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocationRelativeTo(null);
                }

            }
        });
        txtFormaPago.setBackground(Color.WHITE);
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

    public void vaciarDatos(Ventas venta, String fecha, double saldo, boolean indicador, int idCancelacion) {
        this.fecha = fecha;
        this.folio = folio;
        this.venta = venta;
        this.indicador = indicador;
        this.saldo = saldo;
        txtFormaPago.removeAllItems();
        if (indicador == true) { //cancelat
            Date diaActual = StringADate(fecha);
            DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
            String fechaUsar;
            Date fechaNueva;
            Calendar c = Calendar.getInstance();
            c.setTime(diaActual);
            fechaUsar = cam.format(diaActual);
            txtFecha.setText(fechaUsar);
            txtMonto.setText("$" + formateador.format(saldo));
            txtFormaPago.removeAllItems();
            if (venta.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                txtFormaPago.addItem("99 Por definir"); //a credito
            } else {
                txtFormaPago.addItem("01 Efectivo");
                txtFormaPago.addItem("02 Cheque nominativo");
                txtFormaPago.addItem("03 Transferencia electrónica de fondos");
                txtFormaPago.addItem("04 Tarjeta de crédito");
                txtFormaPago.addItem("28 Tarjeta de débito");
            }

        } else { // ver cancelacion
            notaCredito miNota = notaDao.buscarPorId(idCancelacion);
            Date diaActual = StringADate(miNota.getFecha());
            DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
            String fechaUsar;
            Date fechaNueva;
            Calendar c = Calendar.getInstance();
            c.setTime(diaActual);
            fechaUsar = cam.format(diaActual);
            txtFecha.setText(fechaUsar);
            txtHora.setText(removeLastThreeCharacters(miNota.getHora()));
            txtMonto.setText("$" + formateador.format(miNota.getTotal()));
            txtObservaciones.setText(miNota.getRazon());
            txtObservaciones.setEnabled(false);
            panelGuardar.setVisible(false);
            txtFormaPago.addItem(miNota.getFormaPago());
        }

    }

    public static String removeLastThreeCharacters(String str) {
        if (str == null || str.length() <= 3) {
            return ""; // Si el String es nulo o tiene 3 caracteres o menos, retornamos un String vacío.
        }
        return str.substring(0, str.length() - 3);
    }

    public void eventoEnter() {
        if (indicador == true) { //cancelat
            Empleados e = emple.seleccionarEmpleado("", idEmpleado);
            ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
            cc.vaciarEmpleado(e.getId());
            cc.setVisible(true);
            if (cc.contraseñaAceptada == true) {
                notaCredito nota = new notaCredito();
                nota.setFolioVenta(venta.getFolio());
                nota.setFecha(fecha);
                nota.setHora(hora);
                nota.setIdRecibe(e.getId());
                nota.setTotal(saldo);
                nota.setRazon(txtObservaciones.getText());
                nota.setSaldoViejo(saldo);
                nota.setSaldoViejo(-saldo);
                nota.setFormaPago(txtFormaPago.getSelectedItem().toString());

                if (venta.getMetodoPago().equals("PPD Pago en parcialidades o diferido")) {
                    Credito miCredito = creditoDao.BuscarPorCodigoCliente(venta.getIdCliente());
                    creditoDao.aumentarAdeudo(miCredito.getIdCliente(), (venta.getTotal() * -1));
                    HistorialCliente hc = new HistorialCliente();
                    hc.setAbono(nota.getTotal());
                    hc.setCargo(0);
                    hc.setFecha(fecha);
                    hc.setFolio("E" + (notaDao.idNotaCredito() + 1));
                    hc.setIdCliente(venta.getIdCliente());
                    hc.setIdRecibe(idEmpleado);
                    hc.setMovimiento("Nota de crédito - Cancelación");
                    hc.setSaldo(miCredito.getAdeudo() - nota.getTotal());
                    historialDao.registrarMovimiento(hc);
                } // si no saca el dinero
                List<detalleVenta> ListaDetalles = ventaDao.regresarDetalles(venta.getFolio());
                List<detalleVenta> ListaDetallesDevolucion = ListaDetallesDevolucion = notaDao.regresarDetalles(venta.getFolio(), "Devolución");
                List<Producto> lsProducto = new ArrayList<Producto>();

                for (int i = 0; i < ListaDetalles.size(); i++) {
                    boolean agregando = true;
                    double cantidadA = ListaDetalles.get(i).getCantidad();
                    for (int j = 0; j < ListaDetallesDevolucion.size(); j++) {
                        if (ListaDetalles.get(i).getCodigo().equals(ListaDetallesDevolucion.get(j).getCodigo())) {
                            if (ListaDetallesDevolucion.get(j).getCantidadModificar() == ListaDetalles.get(i).getCantidad()) {
                                agregando = false;
                            } else {
                                cantidadA = ListaDetalles.get(i).getCantidad() - ListaDetallesDevolucion.get(j).getCantidadModificar();
                            }
                        }
                    }
                    if (agregando == true) {
                        Producto prod = productoDao.BuscarPorCodigo(ListaDetalles.get(i).getCodigo());
                        prod.setExistencia(-cantidadA);
                        if (prod.getInventario() == 1) {
                            lsProducto.add(prod);
                        }

                    }
                }

                productoDao.ajustarInventarioVenta(lsProducto);
                ventaDao.soloCancelar(folio);
                notaDao.registrarVentaCancelada(nota);

                accionRealizada = true;
                dispose();

            } else {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Contraseña incorrecta");
                panel.showNotification();
            }
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

        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        panelGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        panelCancelar = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JLabel();
        txtObservaciones = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtFormaPago = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setText("Cancelar nota");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setText("¿Está seguro de ejecutar esta acción? ");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

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

        btnGuardar.setBackground(new java.awt.Color(153, 204, 255));
        btnGuardar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnGuardar.setToolTipText("ENTER - Realizar acción");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jPanel4.add(panelGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 50, 50));

        panelCancelar.setBackground(new java.awt.Color(255, 255, 255));
        panelCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCancelarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelCancelarMouseEntered(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnCancelar.setToolTipText("ESCAPE - Cancelar acción y salir");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelCancelarLayout = new javax.swing.GroupLayout(panelCancelar);
        panelCancelar.setLayout(panelCancelarLayout);
        panelCancelarLayout.setHorizontalGroup(
            panelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelCancelarLayout.setVerticalGroup(
            panelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel4.add(panelCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 50, 50));

        txtObservaciones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtObservaciones.setToolTipText("Describa la razón de cancelación");
        txtObservaciones.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtObservaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtObservacionesKeyReleased(evt);
            }
        });
        jPanel4.add(txtObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 130, 200, -1));

        txtMonto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMonto.setToolTipText("Total restante de la venta");
        txtMonto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMonto.setEnabled(false);
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMontoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });
        jPanel4.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 100, 200, -1));

        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFecha.setToolTipText("Fecha de cancelación");
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        jPanel4.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 40, 200, -1));

        txtHora.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtHora.setToolTipText("Hora de cancelación");
        txtHora.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtHora.setEnabled(false);
        txtHora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHoraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraKeyTyped(evt);
            }
        });
        jPanel4.add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 70, 200, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Fecha");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 44, 85, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Hora");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 74, 85, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Total restante");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 104, 85, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Razon");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 134, 85, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Forma de pago");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 163, 90, -1));

        txtFormaPago.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtFormaPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Forma de pago", "01 Efectivo", "02 Cheque nominativo", "03 Transferencia electrónica de fondos", "04 Tarjeta de crédito", "28 Tarjeta de débito" }));
        txtFormaPago.setToolTipText("Seleccione el forma de pago");
        jPanel4.add(txtFormaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 160, 200, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_formKeyPressed

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        eventoEnter();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        panelGuardar.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        panelGuardar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnGuardarKeyPressed

    private void panelGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuardarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGuardarMouseClicked

    private void panelGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGuardarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelGuardarMouseEntered

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        dispose();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        panelCancelar.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        panelCancelar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void panelCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCancelarMouseClicked

    }//GEN-LAST:event_panelCancelarMouseClicked

    private void panelCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCancelarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelCancelarMouseEntered

    private void txtObservacionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionesKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtObservacionesKeyReleased

    private void txtMontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtMontoKeyReleased

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoKeyTyped

    private void txtHoraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoraKeyReleased

    private void txtHoraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoraKeyTyped

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
            java.util.logging.Logger.getLogger(vistaCancelar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaCancelar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaCancelar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaCancelar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vistaCancelar dialog = new vistaCancelar(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnCancelar;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel panelCancelar;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JComboBox<String> txtFormaPago;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtObservaciones;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == hi) {
            calcula();
            hora = String.valueOf(Integer.parseInt(hora));
            txtHora.setText(hora + ":" + min);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }

    private void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraactual = new Date();
        calendario.setTime(fechaHoraactual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);

        min = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        seg = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
    }

}
