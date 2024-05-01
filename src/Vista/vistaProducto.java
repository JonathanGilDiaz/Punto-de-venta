/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.CorteDiarioDao;
import Modelo.Departamento;
import Modelo.DepartamentoDao;
import Modelo.Empleados;
import Modelo.Linea;
import Modelo.LineaDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import Modelo.detalleVenta;
import com.raven.model.StatusType;
import com.raven.swing.Table2;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import javaswingdev.Notification;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class vistaProducto extends javax.swing.JDialog {

   DepartamentoDao departamentoDao = new DepartamentoDao();
   bitacoraDao bitacora = new bitacoraDao();
   CorteDiarioDao corteDao = new CorteDiarioDao();
        DefaultTableModel modelo = new DefaultTableModel();
        ProductoDao productoDao = new ProductoDao();
        LineaDao lineaDao = new LineaDao();
        VentasDao ventaDao = new VentasDao();
            DecimalFormat formateador = new DecimalFormat("#,###,##0.00");   
        int fila=-1;
        String hora;
        String fecha = corteDao.getDia();
             Empleados e;
        List<Departamento> lsDepartamento = departamentoDao.listarDepartamento();
        List<Linea> lsLineas = lineaDao.listarLineas();
        
    public void vaciarEmpleado(Empleados e){
                this.e=e;
    }
        
    public vistaProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       this.setLocation(200, 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Punto de venta - Productos");
        Seticon();
        TablaDepartamento.setBackground(Color.WHITE);

        jScrollPane8.getViewport().setBackground(new Color(204, 204, 204));
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
                tcr.setHorizontalAlignment(SwingConstants.CENTER);
         DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
                tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        TablaDepartamento.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TablaDepartamento.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TablaDepartamento.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TablaDepartamento.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TablaDepartamento.getColumnModel().getColumn(4).setCellRenderer(tcr);
        TablaDepartamento.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TablaDepartamento.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);
        ((DefaultTableCellRenderer) TablaDepartamento.getTableHeader().getDefaultRenderer())
                       .setHorizontalAlignment(SwingConstants.CENTER);
        listarProductos();
        jTextField1.requestFocus();
        LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

        // Formatear la fecha como una cadena en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       // fecha = fechaSQL.toLocalDate().format(formatter);
        addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    Point currentLocation = getLocation();
                       if (currentLocation.y < 50) 
                          setLocation(200, 50);;
                    
                }
            });
              panelModificar.setBackground(new Color(179,195,219));
            panelEstado.setBackground(new Color(179,195,219));
             panelEliminar.setBackground(new Color(179,195,219));                  
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
    
    public void listarProductos() {
        List<Producto> ListaCl = productoDao.listarProductos();
        modelo = (DefaultTableModel) TablaDepartamento.getModel();
        Object[] ob = new Object[8];
        for (int i = 0; i < ListaCl.size(); i++) {
            ob[0] = ListaCl.get(i).getCodigo();
            ob[1] = ListaCl.get(i).getDescripcion();
            ob[2] = ListaCl.get(i).getTipoVenta();
            for(int tabulador = 0; tabulador<lsDepartamento.size(); tabulador++){
                if(lsDepartamento.get(tabulador).getId()==ListaCl.get(i).getDepartamento())
                   ob[3] = lsDepartamento.get(tabulador).getNombre();
            }
            for(int tabulador = 0; tabulador<lsLineas.size(); tabulador++){
                if(lsLineas.get(tabulador).getId()==ListaCl.get(i).getDepartamento())
                   ob[4] = lsLineas.get(tabulador).getNombre();
            }
            if(ListaCl.get(i).getEstado()==1){
             ob[5] = StatusType.ACTIVE;
            }else ob[5] = StatusType.INACTIVE;         
            ob[6] = "$"+formateador.format(ListaCl.get(i).getPrecioCompra());
            ob[7] = "$"+formateador.format(ListaCl.get(i).getPrecioVenta());

            
            modelo.addRow(ob);
        }
        TablaDepartamento.setModel(modelo);
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
        
        
        public void eventoF4(){
            if(fila!=-1){
                boolean yaUsado=false;
                List<detalleVenta> lsProducto = ventaDao.regresarDetallesPorProducto(TablaDepartamento.getValueAt(fila, 0).toString());
                if(lsProducto.size()>0) yaUsado=true;
               if(yaUsado==false){//si se va a eliminar
                   ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                    cc.vaciarEmpleado(e.getId());
                   cc.setVisible(true);
                   if(cc.contraseñaAceptada==true){
                       bitacora.registrarRegistro("Se eliminó Producto "+TablaDepartamento.getValueAt(fila, 1).toString(),cc.idEmpleado,fecha);
                       productoDao.EliminarProducto(TablaDepartamento.getValueAt(fila, 0).toString());
                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Producto eliminado exitosamente");
                                 panel.showNotification();
                       LimpiarTabla();
                   listarProductos();
                   panelModificar.setBackground(new Color(179,195,219));
            panelEstado.setBackground(new Color(179,195,219));
             panelEliminar.setBackground(new Color(179,195,219));
             fila=-1;
                   }
            }else{
                 Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "El producto tiene ventas registradas");
                                 panel.showNotification();
               }
            }else{
                  Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
                                 panel.showNotification();
            }
        }
        
        public void eventoF3(){
            if(fila!=-1){
              ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                    cc.vaciarEmpleado(e.getId());
                    cc.setVisible(true);
            if(cc.contraseñaAceptada==true){
                Producto aCambiar = productoDao.BuscarPorCodigo(TablaDepartamento.getValueAt(fila, 0).toString());
                if(aCambiar.getEstado()==1){
                    productoDao.ModificarEstado(TablaDepartamento.getValueAt(fila, 0).toString(), 0);
                    bitacora.registrarRegistro("Estado de Producto "+TablaDepartamento.getValueAt(fila, 1).toString()+": deshabilitado",cc.idEmpleado,fecha);
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Producto deshabilitada");
                                 panel.showNotification();
                }else{
                    productoDao.ModificarEstado(TablaDepartamento.getValueAt(fila, 0).toString(), 1);
                    bitacora.registrarRegistro("Estado de Producto "+TablaDepartamento.getValueAt(fila, 1).toString()+": habilitado",cc.idEmpleado,fecha);
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Producto habilitada");
                                 panel.showNotification();
                }
                LimpiarTabla();
                listarProductos();
                panelModificar.setBackground(new Color(179,195,219));
            panelEstado.setBackground(new Color(179,195,219));
             panelEliminar.setBackground(new Color(179,195,219));
             fila=-1;
                 }
            }else{
                 Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
                                 panel.showNotification();
            }
        }
        
        public void eventoF2(){
            if(fila!=-1){
                 RegistrarModificarProducto cc = new RegistrarModificarProducto(new javax.swing.JFrame(), true);
                                cc.vaciarEmpleado(e.getId());
            List<Producto> ls = productoDao.listarProductos();
            for(int i=0;i<ls.size();i++){
                if(ls.get(i).getCodigo().equals(TablaDepartamento.getValueAt(fila, 0).toString())){
                    cc.validandoDatos(ls.get(i), false);
                cc.setVisible(true);
                if(cc.accionCompletada==true){
                   Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Producto modificado exitosamente");
                                 panel.showNotification();
                LimpiarTabla();
                listarProductos();
                panelModificar.setBackground(new Color(179,195,219));
            panelEstado.setBackground(new Color(179,195,219));
             panelEliminar.setBackground(new Color(179,195,219));
             fila=-1;
            } 
                }
            }
            }else{
                  Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe seleccionar una fila antes");
                                 panel.showNotification();
            }
        }
        
        public void eventoF1(){
             RegistrarModificarProducto cc = new RegistrarModificarProducto(new javax.swing.JFrame(), true);
                            cc.vaciarEmpleado(e.getId());
        Producto cp = new Producto();
        cc.validandoDatos(cp, true);
        cc.setVisible(true);
        if(cc.accionCompletada==true){        
            LimpiarTabla();
            listarProductos();
            panelModificar.setBackground(new Color(179,195,219));
            panelEstado.setBackground(new Color(179,195,219));
             panelEliminar.setBackground(new Color(179,195,219));
             fila=-1;
             Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Producto creado exitosamente");
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
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaDepartamento = new javax.swing.JTable();
        panelNuevo = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JLabel();
        panelModificar = new javax.swing.JPanel();
        btnModificar = new javax.swing.JLabel();
        panelEstado = new javax.swing.JPanel();
        btnEstado = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PRODUCTO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(667, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 960, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar producto");
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

        TablaDepartamento = new Table2();
        TablaDepartamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Unidad", "Linea", "Departamento", "Estado", "Costo", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaDepartamento.setToolTipText("");
        TablaDepartamento.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TablaDepartamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDepartamentoMouseClicked(evt);
            }
        });
        TablaDepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaDepartamentoKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(TablaDepartamento);
        if (TablaDepartamento.getColumnModel().getColumnCount() > 0) {
            TablaDepartamento.getColumnModel().getColumn(0).setPreferredWidth(40);
            TablaDepartamento.getColumnModel().getColumn(1).setPreferredWidth(100);
            TablaDepartamento.getColumnModel().getColumn(2).setPreferredWidth(15);
            TablaDepartamento.getColumnModel().getColumn(3).setPreferredWidth(10);
            TablaDepartamento.getColumnModel().getColumn(4).setPreferredWidth(10);
            TablaDepartamento.getColumnModel().getColumn(5).setPreferredWidth(15);
            TablaDepartamento.getColumnModel().getColumn(6).setPreferredWidth(15);
            TablaDepartamento.getColumnModel().getColumn(7).setPreferredWidth(15);
        }

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 430));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 940, 450));

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
        btnNuevo.setToolTipText("F1 - Nuevo producto");
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
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MODIFICAR.png"))); // NOI18N
        btnModificar.setToolTipText("F3 - Modificar producto");
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

        jPanel1.add(panelEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 50, 50));

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
        btnEliminar.setToolTipText("F5 - Eliminar producto");
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

        jPanel1.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 50, 50));

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

    private void TablaDepartamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDepartamentoMouseClicked
        if (evt.getClickCount() == 2) {
            fila = TablaDepartamento.rowAtPoint(evt.getPoint());
              panelModificar.setBackground(new Color(255,255,255));
            panelEstado.setBackground(new Color(255,255,255));
             panelEliminar.setBackground(new Color(255,255,255));
        }
    }//GEN-LAST:event_TablaDepartamentoMouseClicked

    private void TablaDepartamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaDepartamentoKeyPressed
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
    }//GEN-LAST:event_TablaDepartamentoKeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
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
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
    fila=-1;
    panelModificar.setBackground(new Color(179,195,219));
            panelEstado.setBackground(new Color(179,195,219));
             panelEliminar.setBackground(new Color(179,195,219));
        LimpiarTabla();
        if(!"".equals(jTextField1.getText())){
                List<Producto> ListaCl = productoDao.buscarLetraCodigoDescripcion(jTextField1.getText());

                modelo = (DefaultTableModel) TablaDepartamento.getModel();
                Object[] ob = new Object[8];
                for (int i = 0; i < ListaCl.size(); i++) {
                   ob[0] = ListaCl.get(i).getCodigo();
            ob[1] = ListaCl.get(i).getDescripcion();
            ob[2] = ListaCl.get(i).getTipoVenta();
             for(int tabulador = 0; tabulador<lsDepartamento.size(); tabulador++){
                if(lsDepartamento.get(tabulador).getId()==ListaCl.get(i).getDepartamento())
                   ob[3] = lsDepartamento.get(tabulador).getNombre();
            }
            for(int tabulador = 0; tabulador<lsLineas.size(); tabulador++){
                if(lsLineas.get(tabulador).getId()==ListaCl.get(i).getDepartamento())
                   ob[4] = lsLineas.get(tabulador).getNombre();
            }
            if(ListaCl.get(i).getEstado()==1){
             ob[5] = StatusType.ACTIVE;
            }else ob[5] = StatusType.INACTIVE;         
            ob[6] = "$"+formateador.format(ListaCl.get(i).getPrecioCompra());
            ob[7] = "$"+formateador.format(ListaCl.get(i).getPrecioVenta());
                    modelo.addRow(ob);
                }
                TablaDepartamento.setModel(modelo);
            
        }else{
            listarProductos();
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
       eventoF1();
    }//GEN-LAST:event_btnNuevoMouseClicked

    private void btnNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseEntered
       panelNuevo.setBackground(new Color(179,195,219));
    }//GEN-LAST:event_btnNuevoMouseEntered

    private void btnNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseExited
        panelNuevo.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnNuevoMouseExited

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

    private void panelNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevoMouseClicked

    private void panelNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNuevoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelNuevoMouseEntered

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
       eventoF2();
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseEntered
           panelModificar.setBackground(new Color(179,195,219));
          if(fila!=-1){
              panelModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
            panelModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
    }//GEN-LAST:event_btnModificarMouseEntered

    private void btnModificarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseExited
        if(fila!=-1)  {
            panelModificar.setBackground(new Color(255,255,255));
           btnModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }else{
             panelModificar.setBackground(new Color(179,195,219));
             btnModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }//GEN-LAST:event_btnModificarMouseExited

    private void btnModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarKeyPressed
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
    }//GEN-LAST:event_btnModificarKeyPressed

    private void panelModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelModificarMouseClicked

    private void panelModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelModificarMouseEntered

    private void btnEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoMouseClicked
      eventoF3();
    }//GEN-LAST:event_btnEstadoMouseClicked

    private void btnEstadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoMouseEntered
         panelEstado.setBackground(new Color(179,195,219));
            if(fila!=-1){
              panelEstado.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
            panelEstado.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
    }//GEN-LAST:event_btnEstadoMouseEntered

    private void btnEstadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoMouseExited
       if(fila!=-1)  {
            panelEstado.setBackground(new Color(255,255,255));
           btnEstado.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }else{
             panelEstado.setBackground(new Color(179,195,219));
             btnEstado.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }//GEN-LAST:event_btnEstadoMouseExited

    private void btnEstadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEstadoKeyPressed
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
    }//GEN-LAST:event_btnEstadoKeyPressed

    private void panelEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEstadoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEstadoMouseClicked

    private void panelEstadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEstadoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEstadoMouseEntered

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
     eventoF4();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
         if(fila!=-1){
              btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
            btnEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            panelEliminar.setBackground(new Color(179,195,219));
        
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
         if(fila!=-1)  {
            panelEliminar.setBackground(new Color(255,255,255));
           btnEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }else{
             panelEliminar.setBackground(new Color(179,195,219));
             btnEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
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
    private javax.swing.JTable TablaDepartamento;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnEstado;
    private javax.swing.JLabel btnModificar;
    private javax.swing.JLabel btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane8;
    private textfield.TextField jTextField1;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JPanel panelModificar;
    private javax.swing.JPanel panelNuevo;
    // End of variables declaration//GEN-END:variables

  private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
   }
    
}
