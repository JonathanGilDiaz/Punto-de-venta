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
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.HistorialCliente;
import Modelo.HistorialClienteDao;
import Modelo.Pagos;
import Modelo.PagosDao;
import Modelo.TextPrompt;
import Modelo.Ventas;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import Modelo.config;
import Modelo.configuraciones;
import Modelo.imprimiendo;
import static Vista.vistaVenta.removefirstChar;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.Notification;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonathan Gil
 */
public class pagarAdeudo extends javax.swing.JDialog {

    Eventos event = new Eventos();
    EmpleadosDao emple = new EmpleadosDao();
    ClienteDao clienteDao = new ClienteDao();
    bitacoraDao bitacora = new bitacoraDao();
    CreditoDao creditoDao = new CreditoDao();
    PagosDao pagosDao = new PagosDao();
    CorteDiarioDao corteDao = new CorteDiarioDao();
    HistorialClienteDao historialDao = new HistorialClienteDao();
    configuraciones configuracionesDao = new configuraciones();
    VentasDao ventaDao = new VentasDao();

    Ventas miVenta;
    
    String fecha = corteDao.getDia();
    Clientes c;
    boolean accionRealizada=false;
    Empleados empleado;
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
   int idEmpleado;
    
    public void vaciarEmpleado(int idEmpleado){
        this.idEmpleado=idEmpleado;
    }
            
    public pagarAdeudo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
          addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    Point currentLocation = getLocation();
                       if (currentLocation.y < 50) 
                         setLocationRelativeTo(null);
                    
                }
            });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
        this.setTitle("Punto de venta - pagar adeudo");
         txtFormaPago.removeAllItems();
        txtFormaPago.addItem("01 Efectivo");
        txtFormaPago.addItem("02 Cheque");
        txtFormaPago.addItem("03 Transferencia");
        txtFormaPago.addItem("04 Tarjeta");
        txtFormaPago.addItem("99 Por definir");
         LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

       txtSaldoRestante.setBackground(new Color(240, 240, 240));
       txtFecha.setBackground(new Color(240, 240, 240));
       txtTotal.setBackground(new Color(240, 240, 240));
       txtSaldo.setBackground(new Color(240, 240, 240));
       txtSaldoRestante.setBackground(new Color(240, 240, 240));
       txtFecha.setText(fechaFormatoCorrecto(fecha));
       txtFormaPago.setBackground(new Color(255,255,255));
        // Formatear la fecha como una cadena en el formato deseado
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

    public String fechaCorrecta(String fechaMala){
            Date diaActual = StringADate(fechaMala);
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar ca = Calendar.getInstance();
        ca.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
        return fechaUsar;
    }
    
    public void vaciarDatos(Ventas venta){
        this.miVenta=venta;
        txtSaldo.setText("$"+formateador.format(miVenta.getSaldo()));
        txtTotal.setText("$"+formateador.format(miVenta.getTotal()));
    }
    
    public void eventoEnter(){
        if(!txtCantidad.getText().equals("")){
            if(!txtCantidad.getText().endsWith(".")){
                if(Double.parseDouble(txtCantidad.getText())<=miVenta.getSaldo()){
                    Empleados empleadoInf = emple.seleccionarEmpleado("",idEmpleado);
                     ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(empleadoInf.getId());
                   cc.setVisible(true);
                   if(cc.contraseñaAceptada==true){
                        
                        Pagos p = new Pagos();
                        p.setFecha(fecha);
                        p.setAldoAnterior(miVenta.getSaldo());
                        p.setNuevoSaldo(miVenta.getSaldo()-Double.parseDouble(txtCantidad.getText()));
                        p.setCantidad(Double.parseDouble(txtCantidad.getText()));
                        p.setFormaPago(txtFormaPago.getSelectedItem().toString());
                        p.setFolioVenta(miVenta.getFolio());
                        p.setIdRecibe(idEmpleado);
                        pagosDao.registrarPago(p);
                    
                        c=clienteDao.BuscarPorCodigo(miVenta.getIdCliente());
                        creditoDao.aumentarAdeudo(c.getId(),-Double.parseDouble(txtCantidad.getText()));
                        
                        if(Double.parseDouble(txtCantidad.getText())==miVenta.getSaldo()) bitacora.registrarRegistro("El cliente "+c.getNombre()+" liquidó su venta a crédito con folio", empleadoInf.getId(), fecha);
                        else bitacora.registrarRegistro("El cliente "+c.getNombre()+" abonó $"+formateador.format(Double.parseDouble(txtCantidad.getText()))+"a venta a crédito con folio "+miVenta.getFolio(), empleadoInf.getId(), fecha);
                         
                        Credito miCredito = creditoDao.BuscarPorCodigoCliente(miVenta.getIdCliente());
                             HistorialCliente hc = new HistorialCliente();
                                    hc.setAbono(p.getCantidad());
                                    hc.setCargo(0);
                                    hc.setFecha(p.getFecha());
                                    hc.setFolio("P"+pagosDao.idPagos());
                                    hc.setIdCliente(miVenta.getIdCliente());
                                    hc.setIdRecibe(idEmpleado);
                                    hc.setMovimiento("Abono a venta crédito F"+miVenta.getFolio());
                                    hc.setSaldo(miCredito.getAdeudo());
                                    historialDao.registrarMovimiento(hc);
                                    
                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Abono registrado exitosamente");
                        panel.showNotification();
                        try {
                            ticketPagos(pagosDao.idPagos());
                            File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\Tickets\\pago" + pagosDao.idPagos() + ".pdf"); 
                            imprimiendo m = new imprimiendo();
                            if (file.isFile()) {
                                m.imprimir(file);
                            } else {
                                 Notification panel2 = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Error inesperado");
                                                 panel2.showNotification();
                            }
                         } catch (PrinterException ex) {
                                JOptionPane.showMessageDialog(null, "Error de impresion", "Error", JOptionPane.ERROR_MESSAGE);
                           } catch (IOException ex) {
                                           JOptionPane.showMessageDialog(null,ex.getMessage());

                           }  catch (ParseException ex) {
                                  Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
                              }
                        accionRealizada=true;
                        dispose();
                    }else{
                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Contraseña incorrecta");
                        panel.showNotification();
                    }
                }else{
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "La cantidad es mayor al adeudo");
                    panel.showNotification();
                }
       
          }else{
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cantidad incorrecta");
                                 panel.showNotification();
        }      
        }else{
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "No se ha ingresado ninguna cantidad");
                                 panel.showNotification();
        }
    }
    
         public String fechaFormatoCorrecto(String fecha){
            Date diaActual = StringADate(fecha);
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar c = Calendar.getInstance();
        c.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
        return fechaUsar;
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
        txtFecha = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtSaldo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtSaldoRestante = new javax.swing.JTextField();
        txtFormaPago = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setText("Pagar adeudo");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setText("¿Está seguro de ejecutar esta acción? ");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

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
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelGuardarLayout.setVerticalGroup(
            panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel4.add(panelGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 50, 50));

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
            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelCancelarLayout.setVerticalGroup(
            panelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel4.add(panelCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 50, 50));

        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFecha.setToolTipText("Fecha en la que se generará el pago");
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFechaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaKeyTyped(evt);
            }
        });
        jPanel4.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 50, 180, -1));

        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTotal.setToolTipText("Total de la venta");
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
        jPanel4.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 80, 180, -1));

        txtSaldo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSaldo.setToolTipText("Cantidad restante del adeudo");
        txtSaldo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldo.setEnabled(false);
        txtSaldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSaldoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSaldoKeyTyped(evt);
            }
        });
        jPanel4.add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 110, 180, -1));

        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCantidad.setToolTipText("Cantidad que pago el cliente");
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel4.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 170, 180, -1));

        txtSaldoRestante.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSaldoRestante.setToolTipText("Saldo restante de la venta");
        txtSaldoRestante.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldoRestante.setEnabled(false);
        txtSaldoRestante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoRestanteActionPerformed(evt);
            }
        });
        txtSaldoRestante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSaldoRestanteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSaldoRestanteKeyTyped(evt);
            }
        });
        jPanel4.add(txtSaldoRestante, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 200, 180, -1));

        txtFormaPago.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFormaPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Forma de pago *", "01 Efectivo", "02 Cheque nominativo", "03 Transferencia electrónica de fondos", "04 Tarjeta de crédito", "28 Tarjeta de débito" }));
        txtFormaPago.setToolTipText("Seleccione el forma de pago");
        jPanel4.add(txtFormaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 140, 180, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Fecha");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 54, 110, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Total de la venta");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 84, 110, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Saldo actual");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 115, 110, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Forma de pago*");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 144, 110, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Saldo restante");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 205, 110, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Cantidad a abonar*");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 174, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        eventoEnter();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        panelGuardar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        panelGuardar.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
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
        panelCancelar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        panelCancelar.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
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

    private void txtFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

        }
    }//GEN-LAST:event_txtFechaKeyReleased

    private void txtFechaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtFechaKeyTyped

    private void txtTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

        }
    }//GEN-LAST:event_txtTotalKeyReleased

    private void txtTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtTotalKeyTyped

    private void txtSaldoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

 
        }
    }//GEN-LAST:event_txtSaldoKeyReleased

    private void txtSaldoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtSaldoKeyTyped

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

        }
        if(!"".equals(txtCantidad.getText()))  txtSaldoRestante.setText("$"+formateador.format(miVenta.getSaldo()-Double.parseDouble(txtCantidad.getText())));
        else txtSaldoRestante.setText("");
    }//GEN-LAST:event_txtCantidadKeyReleased

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        event.numberDecimalKeyPress(evt, txtCantidad);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtSaldoRestanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoRestanteKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

        }
    }//GEN-LAST:event_txtSaldoRestanteKeyReleased

    private void txtSaldoRestanteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoRestanteKeyTyped
        event.numberDecimalKeyPress(evt, txtSaldoRestante);
    }//GEN-LAST:event_txtSaldoRestanteKeyTyped

    private void txtSaldoRestanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoRestanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoRestanteActionPerformed

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
            java.util.logging.Logger.getLogger(pagarAdeudo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pagarAdeudo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pagarAdeudo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pagarAdeudo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                pagarAdeudo dialog = new pagarAdeudo(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel panelCancelar;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JComboBox<String> txtFormaPago;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtSaldoRestante;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

 private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
   }
 
         public void ticketPagos(int id) throws ParseException {
        try {
            FileOutputStream archivo;
           File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\Tickets\\pago" + id + ".pdf"); 
           Pagos miPago = pagosDao.buscarPorId(id);
           Ventas miVenta = ventaDao.buscarPorFolio(miPago.getFolioVenta());
                        //File file = new File("src/pdf/ticket" + id + ".pdf");
            //File file = new File("C:\\Program Files (x86)\\AppLavanderia\\ticket" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Rectangle pageSize = new Rectangle(140.76f, 500f); //ancho y alto
            Document doc = new Document(pageSize, 0, 0, 0, 0);
            PdfWriter.getInstance(doc, archivo);
            doc.open();
                //Image img = Image.getInstance("src/Imagenes/logo 100x100.jpg");
            Image img = Image.getInstance("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg");
            config configura = configuracionesDao.buscarDatos();

            Font negrita = new Font(Font.FontFamily.HELVETICA, configura.getLetraGrande(), Font.UNDERLINE | Font.ITALIC | Font.BOLD);
            Font letra2 = new Font(Font.FontFamily.HELVETICA, configura.getLetraChica()+2, Font.BOLD );
           

            
            PdfPTable logo = new PdfPTable(5);
            logo.setWidthPercentage(100);
            logo.getDefaultCell().setBorder(0);
            float[] columnaEncabezadoLogo = new float[]{20f,20,60f,20f,20f};
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
             cell = new PdfPCell(new Phrase(nombre,letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(encargado,letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase("RFC: "+rfc,letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(razonSocial,letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(horario,letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(direccion,letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(tel,letra2));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            
             doc.add(encabezado);
            
             letra2 = new Font(Font.FontFamily.HELVETICA, configura.getLetraChica()+1, Font.BOLD );
             
            PdfPTable tablapro = new PdfPTable(2);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{60f,40f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);
            
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Recepción: "+fechaFormatoCorrecto(miPago.getFecha()),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Folio: P"+miPago.getId(),negrita));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
           doc.add(Chunk.NEWLINE);
           doc.add(tablapro);
             
            tablapro = new PdfPTable(1);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{100f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);
            
            Empleados emplead= emple.seleccionarEmpleado("",miPago.getIdRecibe());
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Recibe: "+String.format("%0" + 2 + "d", Integer.valueOf(emplead.getId()))+" "+emplead.getNombre(),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
            
            Clientes miCliente = clienteDao.BuscarPorCodigo(miVenta.getIdCliente());
            String nombreCliente="";
            if(miCliente.getTipoPersona().equals("Persona Física")) nombreCliente = miCliente.getNombre()+" "+miCliente.getApellidoP()+" "+miCliente.getApellidoM();
            else nombreCliente = miCliente.getNombreComercial();
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Cliente: "+nombreCliente,new Font(Font.FontFamily.HELVETICA, configura.getLetraGrande()+1, Font.UNDERLINE | Font.ITALIC | Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
            doc.add(Chunk.NEWLINE);
           doc.add(tablapro);
           
            tablapro = new PdfPTable(2);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{50f, 50f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);
            
            
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Venta",letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
                  cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("F"+miPago.getFolioVenta(),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
                 cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Total venta",letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
                  cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("$"+formateador.format(miVenta.getTotal()),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
               cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Saldo anterior",letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
                  cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("$"+formateador.format(miPago.getAldoAnterior()),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
                           cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Pago",letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
                  cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("$"+formateador.format(miPago.getCantidad()),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
           cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Saldo Restante",letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
                  cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("$"+formateador.format(miPago.getNuevoSaldo()),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
             cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Adeudo",letra2));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
            HistorialCliente his = historialDao.buscarPorFolio("P"+miPago.getId());
                  cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("$"+formateador.format(his.getSaldo()),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
            
            doc.add(tablapro);
            
             letra2 = new Font(Font.FontFamily.HELVETICA, configura.getLetraChica(), Font.BOLD );

               Paragraph notasCliente = new Paragraph("\n"+configura.getMensaje(),letra2);
            doc.add(notasCliente);
            doc.close();
            archivo.close();
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }


}
