/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Departamento;
import Modelo.DepartamentoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.Proveedores;
import Modelo.ProveedoresDao;
import Modelo.TextPrompt;
import Modelo.bitacoraDao;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javaswingdev.Notification;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Jonathan Gil
 */
public class CrearModificarDepartamento extends javax.swing.JDialog {

   DepartamentoDao departamentoDao = new DepartamentoDao();
   EmpleadosDao emple = new EmpleadosDao();
   Eventos event = new Eventos();
   DecimalFormat formato = new DecimalFormat("#0.00");
   bitacoraDao bitacora = new bitacoraDao();

    boolean bandera;
    boolean accionCompletada=false;
    Departamento original;
    String fecha;
       int idEmpleado;
    
    public void vaciarEmpleado(int idEmpleado){
        this.idEmpleado=idEmpleado;
    }

    public CrearModificarDepartamento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
      txtFecha.setBackground(new Color(240,240,240));
      txtFolio.setBackground(new Color(240,240,240));
       addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    Point currentLocation = getLocation();
                       if (currentLocation.y < 50) 
                         setLocationRelativeTo(null);
                    
                }
            });

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
    
    
     public void vaciarDatos(String fecha,Departamento e, boolean bandera){
        this.bandera=bandera;
        this.original=e;
        this.fecha=fecha;

        if(bandera==true){//crear
            jLabel25.setText("Crear Departamento");
            txtFolio.setText(String.valueOf(departamentoDao.idDepartamento()+1));
            
            Date diaActual = StringADate(fecha);
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar c = Calendar.getInstance();
        c.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
            txtFecha.setText(fechaUsar);
            this.setTitle("Punto de venta - Crear Departamento");
        }else{ //Modificar
             Date diaActual = StringADate(original.getFechs());
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar c = Calendar.getInstance();
        c.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
           jLabel25.setText("Modificar Departamento");
           txtFolio.setText(String.valueOf(e.getId()));
           txtFecha.setText(fechaUsar);
           txtNombre.setText(original.getNombre());
           txtDescripcion.setText(original.getDescripcion());
           txtAumento.setText(formato.format(original.getAumento()));
           txtDescuento.setText(formato.format(original.getDescuento()));
           Empleados empleado = emple.seleccionarEmpleado("", original.getIdRecibe());
           this.setTitle("Punto de venta - Modificar Departamento");
        }
    }
     
      public static String modificarString(String string) {
        string = string.trim(); // Elimina los espacios vacíos al inicio y al final
        StringBuilder sb = new StringBuilder();
        String[] palabras = string.split("\\s+"); // Divide el string en una matriz de palabras

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                String primeraLetra = palabra.substring(0, 1).toUpperCase();
                String restoPalabra = palabra.substring(1).toLowerCase();
                sb.append(primeraLetra).append(restoPalabra).append(" ");
            }
        }

        return sb.toString().trim();
    }
     
    public void eventoEnter(){
     if(!"".equals(txtNombre.getText()) && !"".equals(txtAumento.getText()) && !"".equals(txtDescuento.getText())){
            Empleados empleado = emple.seleccionarEmpleado("",idEmpleado);
        ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(empleado.getId());
                   cc.setVisible(true);
                   if(cc.contraseñaAceptada==true){
                 Departamento e = new Departamento();
                e.setNombre(modificarString(txtNombre.getText()));
                e.setFechs(fecha);
                e.setDescripcion(txtDescripcion.getText());
                e.setId(Integer.parseInt(txtFolio.getText()));
                e.setAumento(Double.parseDouble(txtAumento.getText()));
                e.setDescuento(Double.parseDouble(txtDescuento.getText()));
               e.setIdRecibe(empleado.getId());
                if(bandera==true){//crear cliente  
                    if (departamentoDao.DepartamentoRepetido(e) == true) {
         Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Departamento repetido");
                                 panel.showNotification();
                        } else {
                            departamentoDao.RegistrarDepartamento(e);
                            bitacora.registrarRegistro("Nuevo departamento: "+e.getNombre(), empleado.getId(),fecha);
                            accionCompletada=true;
                            dispose();
                            }
                }else{//actualizar cliente
                    if (departamentoDao.DepartamentoRepetidaActualizar(e) == true) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Departamento repetido");
                                 panel.showNotification();
                            } else{
                         departamentoDao.ModificarDepartamento(e);
                                accionCompletada=true;
                                if(original.getAumento()!=e.getAumento()){
                                 bitacora.registrarRegistro("Se modificó el procentaje de utilidad de los precios del Departamento "+original.getNombre()+" de "+formato.format(original.getAumento())+"% a "+formato.format(e.getAumento())+"%",empleado.getId(),fecha);
                                 departamentoDao.ajustarAumento(original.getId());
                                }
                                if(original.getDescuento()!=e.getDescuento()){
                                 bitacora.registrarRegistro("Se modificó el procentaje de descuento de los precios del Departamento "+original.getNombre()+" de "+formato.format(original.getAumento())+"% a "+formato.format(e.getAumento())+"%",empleado.getId(),fecha);
                                 departamentoDao.ajustarDescuento(original.getId());
                                }
                                if(!original.getDescripcion().equals(e.getDescripcion()))
                                bitacora.registrarRegistro("Descripción de Departamento "+original.getNombre()+"modificado",empleado.getId(),fecha);
                                if(!original.getNombre().equals(e.getNombre()))
                                bitacora.registrarRegistro("Nombre de Departamento "+original.getNombre()+"modificado a "+e.getNombre(),empleado.getId(),fecha);
       
                               
                                dispose();
                                    }
                                }
                           }else{  
                 Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Contraseña incorrecta");
                                                    panel.showNotification();
                                   }

                        
                        }else{
                     Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Rellene todos los campos");
                                 panel.showNotification();
                }
                }
     
    public void eventoF1(){
        txtNombre.setText("");
        txtAumento.setText("");
        txtDescripcion.setText("");
        txtDescuento.setText("");
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
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        panelAceptar = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JLabel();
        panelLimpiar = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();
        txtAumento = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtFolio = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        lbNombre4 = new javax.swing.JLabel();
        lbNombre6 = new javax.swing.JLabel();
        lbNombre = new javax.swing.JLabel();
        lbNombre2 = new javax.swing.JLabel();
        lbNombre5 = new javax.swing.JLabel();
        lbNombre1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setBackground(new java.awt.Color(0, 0, 204));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("¿Está seguro de ejecutar esta acción? ");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        jLabel25.setBackground(new java.awt.Color(0, 0, 204));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("-");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 240, -1));

        panelAceptar.setBackground(new java.awt.Color(255, 255, 255));
        panelAceptar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAceptarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelAceptarMouseEntered(evt);
            }
        });

        btnAceptar.setBackground(new java.awt.Color(153, 204, 255));
        btnAceptar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnAceptar.setToolTipText("ENTER - Guardar datos del departamento");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptarMouseExited(evt);
            }
        });
        btnAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAceptarLayout = new javax.swing.GroupLayout(panelAceptar);
        panelAceptar.setLayout(panelAceptarLayout);
        panelAceptarLayout.setHorizontalGroup(
            panelAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelAceptarLayout.setVerticalGroup(
            panelAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 50, 50));

        panelLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        panelLimpiar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLimpiarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLimpiarMouseEntered(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnLimpiar.setToolTipText("F1 - Limpiar todos los campos");
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseExited(evt);
            }
        });
        btnLimpiar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLimpiarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelLimpiarLayout = new javax.swing.GroupLayout(panelLimpiar);
        panelLimpiar.setLayout(panelLimpiarLayout);
        panelLimpiarLayout.setHorizontalGroup(
            panelLimpiarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelLimpiarLayout.setVerticalGroup(
            panelLimpiarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel1.add(panelLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 50, 50));

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
        btnEliminar.setToolTipText("ESCAPE - Cancelar accion y salir");
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jPanel1.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 50, 50));

        txtAumento.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtAumento.setToolTipText("Porcentaje de utilidad (%)");
        txtAumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAumentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAumentoKeyTyped(evt);
            }
        });
        jPanel1.add(txtAumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 150, 180, -1));

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDescripcion.setToolTipText("Descripción");
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 120, 180, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombre.setToolTipText("Nombre del departamento");
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 90, 180, -1));

        txtFolio.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFolio.setToolTipText("Código");
        txtFolio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFolio.setEnabled(false);
        txtFolio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFolioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFolioKeyTyped(evt);
            }
        });
        jPanel1.add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 60, 180, -1));

        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFecha.setToolTipText("Fecha");
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
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 30, 180, -1));

        txtDescuento.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDescuento.setToolTipText("Porcentaje de descuento (%)");
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });
        jPanel1.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 180, 180, -1));

        lbNombre4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre4.setText("Nombre*");
        jPanel1.add(lbNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 93, 70, -1));

        lbNombre6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre6.setText("Descripción");
        jPanel1.add(lbNombre6, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 123, 70, -1));

        lbNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre.setText("Código");
        jPanel1.add(lbNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 63, 70, -1));

        lbNombre2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre2.setText("Fecha");
        jPanel1.add(lbNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 33, 70, -1));

        lbNombre5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre5.setText("Descuento*");
        jPanel1.add(lbNombre5, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 183, 70, -1));

        lbNombre1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre1.setText("Utilidad*");
        jPanel1.add(lbNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 153, 70, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        eventoEnter();
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
        panelAceptar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAceptarMouseEntered

    private void btnAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseExited
        panelAceptar.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnAceptarMouseExited

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void panelAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptarMouseClicked

    private void panelAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptarMouseEntered

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        eventoF1();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void btnLimpiarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseEntered

        panelLimpiar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiarMouseEntered

    private void btnLimpiarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseExited
        panelLimpiar.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnLimpiarMouseExited

    private void btnLimpiarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiarKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_btnLimpiarKeyPressed

    private void panelLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiarMouseClicked

    private void panelLimpiarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiarMouseEntered

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        dispose();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        panelEliminar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        panelEliminar.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
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

    private void txtAumentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAumentoKeyReleased
         int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
        }
    }//GEN-LAST:event_txtAumentoKeyReleased

    private void txtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyReleased
      int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
        }
    }//GEN-LAST:event_txtDescripcionKeyReleased

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
       
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
       int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
        }
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
      
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtFolioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
        }
    }//GEN-LAST:event_txtFolioKeyReleased

    private void txtFolioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtFolioKeyTyped

    private void txtFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyReleased
         int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
        }
    }//GEN-LAST:event_txtFechaKeyReleased

    private void txtFechaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtFechaKeyTyped

    private void txtDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyReleased
          int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
        }
    }//GEN-LAST:event_txtDescuentoKeyReleased

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
     event.numberDecimalKeyPress(evt, txtDescuento);
    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void txtAumentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAumentoKeyTyped
        event.numberDecimalKeyPress(evt, txtAumento);
    }//GEN-LAST:event_txtAumentoKeyTyped

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
            java.util.logging.Logger.getLogger(CrearModificarDepartamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearModificarDepartamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearModificarDepartamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearModificarDepartamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                CrearModificarDepartamento dialog = new CrearModificarDepartamento(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnLimpiar;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbNombre1;
    private javax.swing.JLabel lbNombre2;
    private javax.swing.JLabel lbNombre4;
    private javax.swing.JLabel lbNombre5;
    private javax.swing.JLabel lbNombre6;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelLimpiar;
    private javax.swing.JTextField txtAumento;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
   }


}
