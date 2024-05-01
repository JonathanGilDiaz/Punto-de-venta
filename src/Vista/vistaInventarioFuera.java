/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Diseños.ButtonCustom;
import Modelo.Departamento;
import Modelo.DepartamentoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.InventarioFuera;
import Modelo.InventarioFueraDao;
import Modelo.Linea;
import Modelo.LineaDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.Proveedores;
import Modelo.ProveedoresDao;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import Modelo.detalleVenta;
import Modelo.ordenCompra;
import Modelo.ordenCompraDao;
import com.raven.model.StatusType;
import com.raven.swing.Table2;
import com.raven.swing.Table3;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import javaswingdev.Notification;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class vistaInventarioFuera extends javax.swing.JDialog {

   bitacoraDao bitacora = new bitacoraDao();
   ordenCompraDao ordenCompra = new ordenCompraDao();
   EmpleadosDao empleadoDao = new EmpleadosDao();
   ProveedoresDao proveedorDao = new ProveedoresDao();
   InventarioFueraDao inventario = new InventarioFueraDao();
   ProductoDao productoDao = new ProductoDao();
   
        DefaultTableModel modelo = new DefaultTableModel();
            DecimalFormat formateador = new DecimalFormat("#,###,##0.00");   
        int fila=-1;
        String fecha, hora;
        
        List<Proveedores> lsProveedor = proveedorDao.listarProveedores();
        
    public vistaInventarioFuera(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Inventario fuera");
        Seticon();
        TableInventarioFuera.setBackground(Color.WHITE);

        jScrollPane8.getViewport().setBackground(new Color(204, 204, 204));
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
                tcr.setHorizontalAlignment(SwingConstants.CENTER);
                 DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
                tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TableInventarioFuera.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableInventarioFuera.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableInventarioFuera.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableInventarioFuera.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TableInventarioFuera.getColumnModel().getColumn(4).setCellRenderer(tcr);
        TableInventarioFuera.getColumnModel().getColumn(5).setCellRenderer(tcr);

        ((DefaultTableCellRenderer) TableInventarioFuera.getTableHeader().getDefaultRenderer())
                       .setHorizontalAlignment(SwingConstants.CENTER);
        listarInventario();
        jTextField1.requestFocus();
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

        // Formatear la fecha como una cadena en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fecha = fechaSQL.toLocalDate().format(formatter);
                             
    }
    
       public static boolean isNumeric(String s)
    {
        if (s == null || s.equals("")) {
            return false;
        }
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    
    public void listarInventario() {
        List<InventarioFuera> ListaCl = inventario.listarInventario();
        modelo = (DefaultTableModel) TableInventarioFuera.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListaCl.size(); i++) {
            ob[0] = ListaCl.get(i).getCodigo();
            ob[1] = ListaCl.get(i).getDescripcion();
            ob[2] = ListaCl.get(i).getExistencia();
           ob[3] = fechaCorrecta(ListaCl.get(i).getFecha());
            ob[4] = ListaCl.get(i).getRazon();
            Empleados e = empleadoDao.BuscarPorCodigo(ListaCl.get(i).getIdRecibe());
            ob[5] = e.getNombre();         
            modelo.addRow(ob);
        }
        TableInventarioFuera.setModel(modelo);
    }
    
     public void listarPorPeriodoDeFecha() {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       List<InventarioFuera> ListaCl = inventario.listarPeriodo(sdf.format(jDateChooser1.getDate()), sdf.format(jDateChooser2.getDate()));
               modelo = (DefaultTableModel) TableInventarioFuera.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListaCl.size(); i++) {
          ob[0] = ListaCl.get(i).getCodigo();
            ob[1] = ListaCl.get(i).getDescripcion();
            ob[2] = ListaCl.get(i).getExistencia();
           ob[3] = fechaCorrecta(ListaCl.get(i).getFecha());
            ob[4] = ListaCl.get(i).getRazon();
            Empleados e = empleadoDao.BuscarPorCodigo(ListaCl.get(i).getIdRecibe());
            ob[5] = e.getNombre();         
            modelo.addRow(ob);
        }
        TableInventarioFuera.setModel(modelo);
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
        
        public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
        
     public String fechaCorrecta(String fechaMal){
         Date diaActual = StringADate(fechaMal);
            DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
            String fechaUsar;
            Date fechaNueva;
            Calendar c = Calendar.getInstance();
            c.setTime(diaActual);
            fechaUsar = cam.format(diaActual);
            return fechaUsar;
    }
        
        
        public void eventoF4(){
            if(fila!=-1){
               

            }else{
                  Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
                                 panel.showNotification();
            }
        }
        
        public void eventoF3(){
            if(fila!=-1){
           
            }else{
                 Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
                                 panel.showNotification();
            }
        }
        
        public void eventoF2(){
            if(fila!=-1){
             RegistrarModificarProducto cc = new RegistrarModificarProducto(new javax.swing.JFrame(), true);
                Producto p = productoDao.BuscarPorCodigo(TableInventarioFuera.getValueAt(fila, 0).toString());
                cc.validandoDatos(p, false);
                cc.bloqueandoTodo();
                cc.setVisible(true);
            }else{
                  Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
                                 panel.showNotification();
            }
        }
        
        public void eventoF1(){
            if(fila!=-1){
                    moverInventarioFuera vdr = new moverInventarioFuera(new javax.swing.JFrame(), true);
                    vdr.vaciarProducto(false, Double.parseDouble(TableInventarioFuera.getValueAt(fila, 2).toString()));
                    vdr.setVisible(true);
                 if(vdr.accionRealizada==true){
                     InventarioFuera inv = new InventarioFuera();
                     String codigo = TableInventarioFuera.getValueAt(TableInventarioFuera.getSelectedRow(), 0).toString();
                     double cantidadM = vdr.cantidadModificada;
                     inventario.actualizarProducto(codigo, cantidadM);
                     bitacora.registrarRegistro("Se regresaron "+inv.getExistencia()+" unidades del producto "+TableInventarioFuera.getValueAt(TableInventarioFuera.getSelectedRow(), 1).toString(), vdr.empleado.getId(), fecha);
                     Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "El producto se regreso");
                                 panel.showNotification();
                     LimpiarTabla();
                     listarInventario();
                 }
            }else{
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

        jDateChooser1 = new com.raven.datechooser.DateChooser();
        jDateChooser2 = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TableInventarioFuera = new javax.swing.JTable();
        panelNuevo = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new textfield.TextField();
        primeraFecha = new textfield.TextField();
        segundaFecha = new textfield.TextField();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();

        jDateChooser1.setForeground(new java.awt.Color(51, 102, 255));
        jDateChooser1.setTextRefernce(primeraFecha);
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jDateChooser2.setForeground(new java.awt.Color(51, 102, 255));
        jDateChooser2.setTextRefernce(segundaFecha);
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Inventario fuera");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(637, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 960, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableInventarioFuera = new Table3();
        TableInventarioFuera.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Existencia", "Fecha", "Razón", "Encargado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableInventarioFuera.setToolTipText("");
        TableInventarioFuera.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableInventarioFuera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableInventarioFueraMouseClicked(evt);
            }
        });
        TableInventarioFuera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableInventarioFueraKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(TableInventarioFuera);
        if (TableInventarioFuera.getColumnModel().getColumnCount() > 0) {
            TableInventarioFuera.getColumnModel().getColumn(0).setPreferredWidth(80);
            TableInventarioFuera.getColumnModel().getColumn(1).setPreferredWidth(110);
            TableInventarioFuera.getColumnModel().getColumn(2).setPreferredWidth(10);
            TableInventarioFuera.getColumnModel().getColumn(3).setPreferredWidth(10);
            TableInventarioFuera.getColumnModel().getColumn(4).setPreferredWidth(150);
            TableInventarioFuera.getColumnModel().getColumn(5).setPreferredWidth(10);
        }

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 430));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 183, 940, 450));

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
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen3.png"))); // NOI18N
        btnNuevo.setToolTipText("F1 - regresa");
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("     Buscar por fecha");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 5, 130, 20));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 15, 450, 40));

        primeraFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        primeraFecha.setLabelText("Fecha de inicio");
        primeraFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                primeraFechaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                primeraFechaKeyReleased(evt);
            }
        });
        jPanel2.add(primeraFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 25, 130, 40));

        segundaFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        segundaFecha.setLabelText("Fecha de fin");
        segundaFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                segundaFechaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                segundaFechaKeyReleased(evt);
            }
        });
        jPanel2.add(segundaFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 25, 130, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 940, 70));

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
        btnEliminar.setToolTipText("F4 - Visualizar");
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

        jPanel1.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 50, 50, 50));

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

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        jDateChooser2.setMinSelectableDate(jDateChooser1.getDate());
        jDateChooser2.setSelectedDate(jDateChooser1.getDate());
        if(jDateChooser1.getDate()!=null){
            try {
                LimpiarTabla();
                listarPorPeriodoDeFecha();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ALGO SUCEDIO MAL, PRUEBA OTRA FECHA" + e);
            }}
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if(jDateChooser2.getDate()!=null){
            try {
                LimpiarTabla();
                listarPorPeriodoDeFecha();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ALGO SUCEDIO MAL, PRUEBA OTRA VEZ" + e);
            }}
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void segundaFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaFechaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_segundaFechaKeyReleased

    private void segundaFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaFechaKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
        }
    }//GEN-LAST:event_segundaFechaKeyPressed

    private void primeraFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primeraFechaKeyReleased

    }//GEN-LAST:event_primeraFechaKeyReleased

    private void primeraFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primeraFechaKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
        }
    }//GEN-LAST:event_primeraFechaKeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        fila=-1;
        LimpiarTabla();
        if(!"".equals(jTextField1.getText())){
            List<InventarioFuera> lista = inventario.buscarLetraCodigoDescripcion(jTextField1.getText());

            modelo = (DefaultTableModel) TableInventarioFuera.getModel();
            Object[] ob = new Object[6];
            for (int i = 0; i < lista.size(); i++) {
                ob[0] = lista.get(i).getCodigo();
                ob[1] = lista.get(i).getDescripcion();
                ob[2] = lista.get(i).getExistencia();
                ob[3] = fechaCorrecta(lista.get(i).getFecha());
                ob[4] = lista.get(i).getRazon();
                Empleados e = empleadoDao.BuscarPorCodigo(lista.get(i).getIdRecibe());
                ob[5] = e.getNombre();
                modelo.addRow(ob);

                modelo.addRow(ob);
            }
            TableInventarioFuera.setModel(modelo);

        }else{
            listarInventario();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void panelNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevoMouseEntered

    private void panelNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevoMouseClicked

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_F1:
            eventoF1();
            break;

            case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_F3:
            eventoF3();
            break;

            case KeyEvent.VK_F4:
            eventoF4();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_btnNuevoKeyPressed

    private void btnNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseExited
        panelNuevo.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnNuevoMouseExited

    private void btnNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseEntered
        panelNuevo.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnNuevoMouseEntered

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        eventoF1();
    }//GEN-LAST:event_btnNuevoMouseClicked

    private void TableInventarioFueraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableInventarioFueraKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_F1:
            eventoF1();
            break;

            case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_F3:
            eventoF3();
            break;

            case KeyEvent.VK_F4:
            eventoF4();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_TableInventarioFueraKeyPressed

    private void TableInventarioFueraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableInventarioFueraMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TableInventarioFuera.rowAtPoint(evt.getPoint());
           
        }
    }//GEN-LAST:event_TableInventarioFueraMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        eventoF2();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        if(fila!=-1){
            panelEliminar.setBackground(new Color(153,204,255));
            btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        panelEliminar.setBackground(new Color(255,255,255));
        btnEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_F1:
            eventoF1();
            break;

            case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_F3:
            eventoF3();
            break;

            case KeyEvent.VK_F4:
            eventoF4();
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
            java.util.logging.Logger.getLogger(vistaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                vistaProducto dialog = new vistaProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableInventarioFuera;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnNuevo;
    private com.raven.datechooser.DateChooser jDateChooser1;
    private com.raven.datechooser.DateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane8;
    private textfield.TextField jTextField1;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelNuevo;
    private textfield.TextField primeraFecha;
    private textfield.TextField segundaFecha;
    // End of variables declaration//GEN-END:variables

  private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppLavanderia\\Iconos\\logo 100x100.jpg"));
   }
    
}
