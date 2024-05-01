/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Eventos;
import Modelo.Producto;
import Modelo.ProductoDao;
import static Vista.vistaVenta.removefirstChar;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.Notification;

/**
 *
 * @author Jonathan Gil
 */
public class ventaTabla extends javax.swing.JDialog {

    Eventos event = new Eventos();
    ProductoDao productoDao = new ProductoDao();
    List<Double> datos = new ArrayList<Double>();
    double diferencia, cantidadModificada, precioUnitario;
    boolean accionRealizada = false;
    boolean borrar = false;
    int tipo;
    Producto p;
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat formato = new DecimalFormat("0.00");

    public ventaTabla(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
        this.setTitle("Punto de venta - Modificar producto");
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocationRelativeTo(null);
                }

            }
        });
        txtCodigo.setBackground(new Color(240, 240, 240));
        txtDescripcion.setBackground(new Color(240, 240, 240));
        txtUnidad.setBackground(new Color(240, 240, 240));
        txtPrecioUnitario.setBackground(new Color(240, 240, 240));
        txtImporte.setBackground(new Color(240, 240, 240));
        txtIVA.setBackground(new Color(240, 240, 240));
        txtTotal.setBackground(new Color(240, 240, 240));
    }

    public void vaciarProducto(Producto p, double cantidad, double descuento, int tipo) { //2´para cotizacion
        this.p = p;
        this.tipo = tipo;
        txtCodigo.setText(p.getCodigo());
        txtDescripcion.setText(p.getDescripcion());
        if (tipo == 2) {
            txtPrecioUnitario.setEnabled(true);
            txtPrecioUnitario.setText(formateador.format(p.getPrecioVenta()));
            txtPrecioUnitario.setBackground(new Color(255, 255, 255));
        } else {
            txtPrecioUnitario.setText("$" + formateador.format(p.getPrecioVenta()));
        }
        txtUnidad.setText(p.getTipoVenta());
        txtCantidad.setText(formato.format(cantidad));
        double importe = cantidad * p.getPrecioVenta();
        txtImporte.setText("$" + formateador.format(importe));
        txtDescuento.setText(formato.format(descuento));
        importe = importe - descuento;
        double iva = importe * .16;
        txtIVA.setText("$" + formateador.format(iva));
        txtTotal.setText("$" + formateador.format(iva + importe));
    }

    public void eventoEnter() {
        datos = new ArrayList<Double>();
        try {
            if (!"".equals(txtCantidad.getText())) {
                if (!txtCantidad.getText().endsWith(".") && !txtPrecioUnitario.getText().endsWith(".")) {
                    double descuento = 0;
                    if (!"".equals(txtDescuento.getText())) {
                        if (!txtDescuento.getText().endsWith(".")) {
                            descuento = Double.parseDouble(txtDescuento.getText());
                        }
                    }
                    datos.add(Double.parseDouble(txtCantidad.getText()));
                    Number importeN = formateador.parse(removefirstChar(txtImporte.getText()));
                    datos.add(importeN.doubleValue());
                    datos.add(descuento);
                    Number ivaN = formateador.parse(removefirstChar(txtIVA.getText()));
                    datos.add(ivaN.doubleValue());
                    Number totalN = formateador.parse(removefirstChar(txtTotal.getText()));
                    datos.add(totalN.doubleValue());
                    if(tipo==2) datos.add(Double.parseDouble(txtPrecioUnitario.getText()));

                    if (p.getInventario() == 1) {
                        if (tipo == 1) {
                            if (Double.parseDouble(txtCantidad.getText()) <= p.getExistencia()) {
                                accionRealizada = true;
                                dispose();
                            } else {
                                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Inventario no suficiente");
                                panel.showNotification();
                            }
                        } else {
                            accionRealizada = true;
                            dispose();
                        }
                    } else {
                        accionRealizada = true;
                        dispose();
                    }

                } else {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cantida de productos no valida");
                    panel.showNotification();
                }
            } else {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "No se ha ingresado ninguna cantidad");
                panel.showNotification();
            }
        } catch (ParseException ex) {
            Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void eventoF1() {
        borrar = true;
        dispose();
    }

    public void calcularDatos() {
        double cantidad = 0;
        if (!"".equals(txtCantidad.getText())) {
            if (!txtCantidad.getText().endsWith(".")) {
                cantidad = Double.parseDouble(txtCantidad.getText());
            }
        }
        double descuento = 0;
        if (!"".equals(txtDescuento.getText())) {
            if (!txtDescuento.getText().endsWith(".")) {
                descuento = Double.parseDouble(txtDescuento.getText());
            }
        }
        double precioUnitario = 0;
        if(tipo==2){
        if (!"".equals(txtPrecioUnitario.getText())) {
            if (!txtPrecioUnitario.getText().endsWith(".")) {
                precioUnitario = Double.parseDouble(txtPrecioUnitario.getText());
            }
        }
        }else{
            precioUnitario = p.getPrecioVenta();
        }

        double importe = cantidad * precioUnitario;
        txtImporte.setText("$" + formateador.format(importe));
        importe = importe - descuento;
        double iva = importe * .16;
        txtIVA.setText("$" + formateador.format(iva));
        txtTotal.setText("$" + formateador.format(iva + importe));
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
        txtTitulo = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        panelGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        panelCancelar = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtUnidad = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtImporte = new javax.swing.JTextField();
        txtPrecioUnitario = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        txtIVA = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTitulo.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 10)); // NOI18N
        txtTitulo.setForeground(new java.awt.Color(0, 0, 255));
        txtTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTitulo.setText("Modificar producto");
        jPanel4.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 200, -1));

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setText("¿Está seguro de ejecutar esta acción? ");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

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
        btnGuardar.setToolTipText("ENTER - Modificar datos");
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
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelGuardarLayout.setVerticalGroup(
            panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel4.add(panelGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, 50, 50));

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
        btnCancelar.setToolTipText("F1 - Eliminar producto");
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
            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelCancelarLayout.setVerticalGroup(
            panelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel4.add(panelCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 50, 50));

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCodigo.setToolTipText("Código del producto");
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.setEnabled(false);
        jPanel4.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 150, -1));

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescripcion.setToolTipText("Descripción del producto");
        txtDescripcion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcion.setEnabled(false);
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        jPanel4.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 150, -1));

        txtUnidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUnidad.setToolTipText("Tipo de venta");
        txtUnidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUnidad.setEnabled(false);
        txtUnidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUnidadKeyTyped(evt);
            }
        });
        jPanel4.add(txtUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 150, -1));

        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCantidad.setToolTipText("Cantidad a vender");
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel4.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 161, 150, 20));

        txtImporte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtImporte.setToolTipText("Importe");
        txtImporte.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtImporte.setEnabled(false);
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtImporteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });
        jPanel4.add(txtImporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 150, -1));

        txtPrecioUnitario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecioUnitario.setToolTipText("Precio unitario");
        txtPrecioUnitario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecioUnitario.setEnabled(false);
        txtPrecioUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioUnitarioActionPerformed(evt);
            }
        });
        txtPrecioUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioUnitarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioUnitarioKeyTyped(evt);
            }
        });
        jPanel4.add(txtPrecioUnitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 150, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Código");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 40, 70, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Descripción");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 70, 70, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Unidad");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 100, 70, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Cantidad");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 163, 70, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("P. Unitario");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 130, 70, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Importe");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 193, 70, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Descuento");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 223, 70, -1));

        txtDescuento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescuento.setToolTipText("Descuento realizado");
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });
        jPanel4.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 150, -1));

        txtIVA.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtIVA.setToolTipText("IVA");
        txtIVA.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIVA.setEnabled(false);
        txtIVA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIVAKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIVAKeyTyped(evt);
            }
        });
        jPanel4.add(txtIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 150, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("IVA");
        jPanel4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 253, 70, -1));

        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTotal.setToolTipText("Total");
        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal.setEnabled(false);
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalKeyTyped(evt);
            }
        });
        jPanel4.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 150, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Total");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 283, 70, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        eventoF1();
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

    private void txtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionKeyReleased

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtUnidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtUnidadKeyReleased

    private void txtUnidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidadKeyTyped

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
        calcularDatos();

        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

        }
    }//GEN-LAST:event_txtCantidadKeyReleased

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        if (p.getTipoVenta().equals("Pieza"))
            event.numberKeyPress(evt);
        else
            event.numberDecimalKeyPress(evt, txtCantidad);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtImporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImporteKeyTyped

    private void txtImporteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImporteKeyReleased

    private void txtDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyReleased
        calcularDatos();

        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

        }
    }//GEN-LAST:event_txtDescuentoKeyReleased

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        event.numberDecimalKeyPress(evt, txtDescuento);
    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void txtIVAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIVAKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIVAKeyReleased

    private void txtIVAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIVAKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIVAKeyTyped

    private void txtTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalKeyReleased

    private void txtTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalKeyTyped

    private void txtPrecioUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioUnitarioActionPerformed

    private void txtPrecioUnitarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioKeyReleased
        calcularDatos();

        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

            case KeyEvent.VK_F1:
                eventoF1();
                break;

        }
    }//GEN-LAST:event_txtPrecioUnitarioKeyReleased

    private void txtPrecioUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioKeyTyped
        event.numberDecimalKeyPress(evt, txtPrecioUnitario);
    }//GEN-LAST:event_txtPrecioUnitarioKeyTyped

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
            java.util.logging.Logger.getLogger(ventaTabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventaTabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventaTabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventaTabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ventaTabla dialog = new ventaTabla(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel panelCancelar;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtPrecioUnitario;
    private javax.swing.JLabel txtTitulo;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtUnidad;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

}
