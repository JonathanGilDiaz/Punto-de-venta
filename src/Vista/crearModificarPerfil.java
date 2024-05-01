/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.CorteDiarioDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.Perfil;
import Modelo.PerfilDao;
import Modelo.TextPrompt;
import Modelo.bitacoraDao;
import com.raven.swing.Table;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javaswingdev.Notification;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Jonathan Gil
 */
public class crearModificarPerfil extends javax.swing.JDialog {

   Eventos event = new Eventos();
   bitacoraDao bitacora = new bitacoraDao();
   EmpleadosDao emple = new EmpleadosDao();
   PerfilDao perfilDao = new PerfilDao();
   CorteDiarioDao corteDao = new CorteDiarioDao();
    
   boolean accionCompletada=false;
   boolean indicador;
   String fecha = corteDao.getDia();
   Perfil p;
   DefaultTableModel modelo = new DefaultTableModel();
        int idEmpleado;
    
    public void vaciarEmpleado(int idEmpleado){
        this.idEmpleado=idEmpleado;
    }

    public crearModificarPerfil(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    Point currentLocation = getLocation();
                       if (currentLocation.y < 50) 
                         setLocationRelativeTo(null);
                    
                }
            });
        Seticon();
        txtFecha.setBackground(new Color(240,240,240));
        txtFolio.setBackground(new Color(240,240,240));
        establecerTabla();
 

        
    }
    
    public void establecerTabla(){
         Object[][] dataVentas = {
                {"Nueva venta", false},
                {"Control de ventas", false},
                 {"Cotizaciones", false}
        };
        String[] columnNames = {"Área", "Acceso"};
        modelo = new DefaultTableModel(dataVentas, columnNames);
        TableVentas.setModel(modelo);
       TableColumn column = TableVentas.getColumnModel().getColumn(1);
        column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        column.setCellRenderer(TableVentas.getDefaultRenderer(Boolean.class));
        TableColumn column0 = TableVentas.getColumnModel().getColumn(0);
        TableColumn column1 = TableVentas.getColumnModel().getColumn(1);
        column0.setPreferredWidth(120); // Asignar un ancho de 100 píxeles a la columna 0
        column1.setPreferredWidth(20); //
        TableVentas.setDefaultRenderer(Object.class, new CustomCellRenderer());

        
         Object[][] dataCatalogo = {
                {"Proveedores", false},
                {"Empleados", false},
                  {"Productos", false},
                {"Clientes", false},
                 {"Departamentos", false},
                {"Lineas", false},
                 {"Gastos", false},
                {"Perfiles", false},
                 {"Créditos", false}
        }; 
          modelo = new DefaultTableModel(dataCatalogo, columnNames);
        TableCatalogo.setModel(modelo);
        column = TableCatalogo.getColumnModel().getColumn(1);
        column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        column.setCellRenderer(TableCatalogo.getDefaultRenderer(Boolean.class));
         column0 = TableCatalogo.getColumnModel().getColumn(0);
         column1 = TableCatalogo.getColumnModel().getColumn(1);
        column0.setPreferredWidth(120); // Asignar un ancho de 100 píxeles a la columna 0
        column1.setPreferredWidth(20); //
        TableCatalogo.setDefaultRenderer(Object.class, new CustomCellRenderer());
      
               Object[][] dataControl = {
                {"Corte de caja", false},
                {"Bitácora", false},
                  {"Inventario", false},
                {"Ordenes de compra", false},
                {"Factura", false}
        }; 
          modelo = new DefaultTableModel(dataControl, columnNames);
        TableControl.setModel(modelo);
        column = TableControl.getColumnModel().getColumn(1);
        column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        column.setCellRenderer(TableControl.getDefaultRenderer(Boolean.class));
         column0 = TableControl.getColumnModel().getColumn(0);
         column1 = TableControl.getColumnModel().getColumn(1);
        column0.setPreferredWidth(120); // Asignar un ancho de 100 píxeles a la columna 0
        column1.setPreferredWidth(20); //
        TableControl.setDefaultRenderer(Object.class, new CustomCellRenderer());
        
               Object[][] dstaConfig = {
                {"Datos de la empresa", false},
                {"Respaldar datos", false},
                  {"Restaurar datos", false}
        }; 
          modelo = new DefaultTableModel(dstaConfig, columnNames);
        TableConfiguraciones.setModel(modelo);
        column = TableConfiguraciones.getColumnModel().getColumn(1);
        column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        column.setCellRenderer(TableConfiguraciones.getDefaultRenderer(Boolean.class));
         column0 = TableConfiguraciones.getColumnModel().getColumn(0);
         column1 = TableConfiguraciones.getColumnModel().getColumn(1);
        column0.setPreferredWidth(120); // Asignar un ancho de 100 píxeles a la columna 0
        column1.setPreferredWidth(20); //
        TableConfiguraciones.setDefaultRenderer(Object.class, new CustomCellRenderer());
     
         Object[][] dstaReportes = {
              {"Ventas", false},
              {"Notas de crédito", false},
              {"Cobranza", false},
              {"Productos", false},
              {"Inventario", false},
              {"Concentrado General", false},
             {"Datos de clientes", false}
        }; 
          modelo = new DefaultTableModel(dstaReportes, columnNames);
        TableReportes.setModel(modelo);
        column = TableReportes.getColumnModel().getColumn(1);
        column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        column.setCellRenderer(TableReportes.getDefaultRenderer(Boolean.class));
         column0 = TableReportes.getColumnModel().getColumn(0);
         column1 = TableReportes.getColumnModel().getColumn(1);
        column0.setPreferredWidth(120); // Asignar un ancho de 100 píxeles a la columna 0
        column1.setPreferredWidth(20); //
        TableReportes.setDefaultRenderer(Object.class, new CustomCellRenderer());
      
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
         
         public void vaciarDatos(boolean indicador, Perfil p){
             this.indicador=indicador;
             this.p=p;
             if(indicador==true){ //nuevo
                 this.setTitle("Punto de venta - Crear perfil");
                 txtFecha.setText(fechaFormatoCorrecto(fecha));
                 txtFolio.setText(""+(perfilDao.idPerfil()+1));
             }else{
                 this.setTitle("Punto de venta - Modificar perfil");
                 txtFecha.setText(fechaFormatoCorrecto(p.getFecha()));
                 txtFolio.setText(p.getId()+"");
                 txtNombre.setText(p.getNombre());
                 txtDescripcion.setText(p.getDescripcion());
                Empleados empleado = emple.seleccionarEmpleado("", p.getIdRecibe());
                if(p.getNuevaVenta()==1) TableVentas.setValueAt(true, 0, 1);
                if(p.getControlVentas()==1) TableVentas.setValueAt(true, 1, 1);
                if(p.getCotizaciones()==1) TableVentas.setValueAt(true, 2,1);
                
                if(p.getProveedores()==1) TableCatalogo.setValueAt(true, 0, 1);
                if(p.getEmpleados()==1) TableCatalogo.setValueAt(true, 1, 1);
                if(p.getProductos()==1) TableCatalogo.setValueAt(true, 2, 1);
                if(p.getClientes()==1) TableCatalogo.setValueAt(true, 3, 1);
                if(p.getDepartamento()==1) TableCatalogo.setValueAt(true, 4, 1);
                if(p.getLinea()==1) TableCatalogo.setValueAt(true, 5, 1);
                if(p.getGastos()==1) TableCatalogo.setValueAt(true, 6, 1);
                if(p.getPerfil()==1) TableCatalogo.setValueAt(true, 7, 1);
                if(p.getCredito()==1) TableCatalogo.setValueAt(true,8,1);
                
                if(p.getCorteVenta()==1) TableControl.setValueAt(true, 0, 1);
                if(p.getBitacora()==1) TableControl.setValueAt(true, 1, 1);
                if(p.getInventario()==1) TableControl.setValueAt(true, 2, 1);
                if(p.getOrdenesCompra()==1) TableControl.setValueAt(true, 3, 1);
                if(p.getFacturando()==1) TableControl.setValueAt(true, 4, 1);
           
                if(p.getDatosEmpresa()==1) TableConfiguraciones.setValueAt(true, 0, 1);
                if(p.getRespaldar()==1) TableConfiguraciones.setValueAt(true, 1, 1);
                if(p.getRestaurar()==1) TableConfiguraciones.setValueAt(true, 2, 1);
                
                if(p.getVentasR()==1) TableReportes.setValueAt(true, 0, 1);
                if(p.getNotasCreditoR()==1) TableReportes.setValueAt(true, 1, 1);
                if(p.getCobranzaR()==1) TableReportes.setValueAt(true, 2, 1);
                if(p.getProductosR()==1) TableReportes.setValueAt(true, 3, 1);
                if(p.getInventarioR()==1) TableReportes.setValueAt(true, 4, 1);
                if(p.getConcentradoGeneralR()==1) TableReportes.setValueAt(true, 5, 1);
                if(p.getDatosClientesR()==1) TableReportes.setValueAt(true, 6, 1);

             }
         }
         
         public void addCheckBox(int column, JTable table){
             TableColumn tc = table.getColumnModel().getColumn(column);
             tc.setCellEditor(table.getDefaultEditor(Boolean.class));
             tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
         }
         
        public void eventoEnter(){
     if(!"".equals(txtNombre.getText()) ){
             Empleados empleado = emple.seleccionarEmpleado("",idEmpleado);
                ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(empleado.getId());
                   cc.setVisible(true);
                   if(cc.contraseñaAceptada==true){
                 Perfil perfil = new Perfil();
                p.setNombre(modificarString(txtNombre.getText()));
                p.setFecha(fecha);
                p.setId(Integer.parseInt(txtFolio.getText()));
                p.setDescripcion(txtDescripcion.getText());
                p.setIdRecibe(empleado.getId());
                //ventas
                if((boolean)TableVentas.getValueAt(0, 1)==true) p.setNuevaVenta(1);
                else p.setNuevaVenta(0);
                 if((boolean)TableVentas.getValueAt(1, 1)==true) p.setControlVentas(1);
                else p.setControlVentas(0);
                 if((boolean)TableVentas.getValueAt(2, 1)==true) p.setCotizaciones(1);
                else p.setCotizaciones(0);
                 
                //catalogo 
                  if((boolean)TableCatalogo.getValueAt(0, 1)==true) p.setProveedores(1);
                else p.setProveedores(0);
                  if((boolean)TableCatalogo.getValueAt(1, 1)==true) p.setEmpleados(1);
                else p.setEmpleados(0);
                  if((boolean)TableCatalogo.getValueAt(2, 1)==true) p.setProductos(1);
                else p.setProductos(0);
                  if((boolean)TableCatalogo.getValueAt(3, 1)==true) p.setClientes(1);
                else p.setClientes(0);
                 if((boolean)TableCatalogo.getValueAt(4, 1)==true) p.setDepartamento(1);
                else p.setDepartamento(0);
                 if((boolean)TableCatalogo.getValueAt(5, 1)==true) p.setLinea(1);
                else p.setLinea(0);
                if((boolean)TableCatalogo.getValueAt(6, 1)==true) p.setGastos(1);
                else p.setGastos(0);
                 if((boolean)TableCatalogo.getValueAt(7, 1)==true) p.setPerfil(1);
                else p.setPerfil(0);
                  if((boolean)TableCatalogo.getValueAt(8, 1)==true) p.setCredito(1);
                else p.setCredito(0);
                    
                ///Control
                if((boolean)TableControl.getValueAt(0, 1)==true) p.setCorteVenta(1);
                else p.setCorteVenta(0);
                 if((boolean)TableControl.getValueAt(1, 1)==true) p.setBitacora(1);
                else p.setBitacora(0);
                  if((boolean)TableControl.getValueAt(2, 1)==true) p.setInventario(1);
                else p.setInventario(0);
                   if((boolean)TableControl.getValueAt(3, 1)==true) p.setOrdenesCompra(1);
                else p.setOrdenesCompra(0);
                      if((boolean)TableControl.getValueAt(4, 1)==true) p.setFacturando(1);
                else p.setFacturando(0);

                //configuraciones
                 if((boolean)TableConfiguraciones.getValueAt(0, 1)==true) p.setDatosEmpresa(1);
                else p.setDatosEmpresa(0);
                  if((boolean)TableConfiguraciones.getValueAt(1, 1)==true) p.setRespaldar(1);
                else p.setRespaldar(0);
                   if((boolean)TableConfiguraciones.getValueAt(2, 1)==true) p.setRestaurar(1);
                else p.setRestaurar(0);
                   
                   if((boolean)TableReportes.getValueAt(0, 1)==true) p.setVentasR(1);
                else p.setVentasR(0);
                  if((boolean)TableReportes.getValueAt(1, 1)==true) p.setNotasCreditoR(1);
                else p.setNotasCreditoR(0);
                   if((boolean)TableReportes.getValueAt(2, 1)==true) p.setCobranzaR(1);
                else p.setCobranzaR(0);
                       if((boolean)TableReportes.getValueAt(3, 1)==true) p.setProductosR(1);
                else p.setProductosR(0);
                  if((boolean)TableReportes.getValueAt(4, 1)==true) p.setInventarioR(1);
                else p.setInventarioR(0);
                   if((boolean)TableReportes.getValueAt(5, 1)==true) p.setConcentradoGeneralR(1);
                else p.setConcentradoGeneralR(0);
                  if((boolean)TableReportes.getValueAt(6, 1)==true) p.setDatosClientesR(1);
                else p.setDatosClientesR(0);

                 //reportes
                
                if(indicador==true){//Nuevo perfil  
                    if (perfilDao.PerfilRepetido(p) == true) {
         Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nombre de Perfil repetido");
                                 panel.showNotification();
                        } else {
                            perfilDao.RegistrarPerfil(p);
                            accionCompletada=true;
                            bitacora.registrarRegistro("Nuevo Perfil: "+p.getNombre(), empleado.getId(),fecha);
                            dispose();
                            }
                }else{//actualizar cliente
                    if (perfilDao.PerfilRepetidaActualizar(p) == true) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nombre de Perfil repetido");
                                 panel.showNotification();
                            } else {
                                perfilDao.ModificarPerfil(p);
                                accionCompletada=true;
                                    bitacora.registrarRegistro("Se modificó los permisos del Perfil "+p.getNombre(),empleado.getId(),fecha);
                               
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
           txtDescripcion.setText("");
           establecerTabla();
       }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelDatos = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        panelAceptar = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JLabel();
        panelLimpiar = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtFolio = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        panelVentas = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        panelAceptar1 = new javax.swing.JPanel();
        btnAceptar1 = new javax.swing.JLabel();
        panelLimpiar1 = new javax.swing.JPanel();
        btnLimpiar1 = new javax.swing.JLabel();
        panelEliminar1 = new javax.swing.JPanel();
        btnEliminar1 = new javax.swing.JLabel();
        panelCatalogo = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableCatalogo = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        panelAceptar2 = new javax.swing.JPanel();
        btnAceptar2 = new javax.swing.JLabel();
        panelLimpiar2 = new javax.swing.JPanel();
        btnLimpiar2 = new javax.swing.JLabel();
        panelEliminar2 = new javax.swing.JPanel();
        btnEliminar2 = new javax.swing.JLabel();
        panelControl = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableControl = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        panelAceptar3 = new javax.swing.JPanel();
        btnAceptar3 = new javax.swing.JLabel();
        panelLimpiar3 = new javax.swing.JPanel();
        btnLimpiar3 = new javax.swing.JLabel();
        panelEliminar3 = new javax.swing.JPanel();
        btnEliminar3 = new javax.swing.JLabel();
        panelConfiguraciones = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TableConfiguraciones = new javax.swing.JTable();
        jLabel32 = new javax.swing.JLabel();
        panelAceptar4 = new javax.swing.JPanel();
        btnAceptar4 = new javax.swing.JLabel();
        panelLimpiar4 = new javax.swing.JPanel();
        btnLimpiar4 = new javax.swing.JLabel();
        panelEliminar4 = new javax.swing.JPanel();
        btnEliminar4 = new javax.swing.JLabel();
        panelReportes = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TableReportes = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        panelAceptar5 = new javax.swing.JPanel();
        btnAceptar5 = new javax.swing.JLabel();
        panelLimpiar5 = new javax.swing.JPanel();
        btnLimpiar5 = new javax.swing.JLabel();
        panelEliminar5 = new javax.swing.JPanel();
        btnEliminar5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelDatos.setBackground(new java.awt.Color(255, 255, 255));
        panelDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setBackground(new java.awt.Color(0, 0, 204));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("¿Está seguro de ejecutar esta acción? ");
        panelDatos.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 250, -1));

        jLabel25.setBackground(new java.awt.Color(0, 0, 204));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Datos del perfil");
        panelDatos.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 160, -1));

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
        btnAceptar.setToolTipText("ENTER - Guardar datos del perfil");
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

        panelDatos.add(panelAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 50, 50));

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

        panelDatos.add(panelLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 50, 50));

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

        panelDatos.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 50, 50));

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDescripcion.setToolTipText("Descripción adicional del perfil");
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        panelDatos.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 180, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombre.setToolTipText("Nombre con el que se registrará");
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        panelDatos.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 180, -1));

        txtFolio.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFolio.setToolTipText("Código del perfil");
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
        panelDatos.add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 180, -1));

        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFecha.setToolTipText("Fecha de creación del perfil");
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
        panelDatos.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 180, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Fecha");
        panelDatos.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 45, 73, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Código");
        panelDatos.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 75, 73, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Nombre*");
        panelDatos.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 104, 73, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Descripción");
        panelDatos.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 132, 73, -1));

        jTabbedPane1.addTab("Datos", panelDatos);

        panelVentas.setBackground(new java.awt.Color(255, 255, 255));
        panelVentas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Permisos para ventas");
        panelVentas.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 160, -1));

        TableVentas = new Table();
        TableVentas.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Área", "Acceso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVentas.setToolTipText("Determine los permisos para el área de Ventas ");
        TableVentas.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        TableVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableVentasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableVentasKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(120);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(20);
        }

        panelVentas.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 270, 200));

        jLabel24.setBackground(new java.awt.Color(0, 0, 204));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 255));
        jLabel24.setText("¿Está seguro de ejecutar esta acción? ");
        panelVentas.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        panelAceptar1.setBackground(new java.awt.Color(255, 255, 255));
        panelAceptar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAceptar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAceptar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelAceptar1MouseEntered(evt);
            }
        });

        btnAceptar1.setBackground(new java.awt.Color(153, 204, 255));
        btnAceptar1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAceptar1.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAceptar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnAceptar1.setToolTipText("ENTER - Guardar datos del perfil");
        btnAceptar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptar1MouseExited(evt);
            }
        });
        btnAceptar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptar1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAceptar1Layout = new javax.swing.GroupLayout(panelAceptar1);
        panelAceptar1.setLayout(panelAceptar1Layout);
        panelAceptar1Layout.setHorizontalGroup(
            panelAceptar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelAceptar1Layout.setVerticalGroup(
            panelAceptar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelVentas.add(panelAceptar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 50, 50));

        panelLimpiar1.setBackground(new java.awt.Color(255, 255, 255));
        panelLimpiar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelLimpiar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLimpiar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLimpiar1MouseEntered(evt);
            }
        });

        btnLimpiar1.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnLimpiar1.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnLimpiar1.setToolTipText("F1 - Limpiar todos los campos");
        btnLimpiar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiar1MouseExited(evt);
            }
        });
        btnLimpiar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLimpiar1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelLimpiar1Layout = new javax.swing.GroupLayout(panelLimpiar1);
        panelLimpiar1.setLayout(panelLimpiar1Layout);
        panelLimpiar1Layout.setHorizontalGroup(
            panelLimpiar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelLimpiar1Layout.setVerticalGroup(
            panelLimpiar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelVentas.add(panelLimpiar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 50, 50));

        panelEliminar1.setBackground(new java.awt.Color(255, 255, 255));
        panelEliminar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEliminar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEliminar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEliminar1MouseEntered(evt);
            }
        });

        btnEliminar1.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnEliminar1.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnEliminar1.setToolTipText("ESCAPE - Cancelar accion y salir");
        btnEliminar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminar1MouseExited(evt);
            }
        });
        btnEliminar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelEliminar1Layout = new javax.swing.GroupLayout(panelEliminar1);
        panelEliminar1.setLayout(panelEliminar1Layout);
        panelEliminar1Layout.setHorizontalGroup(
            panelEliminar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelEliminar1Layout.setVerticalGroup(
            panelEliminar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelVentas.add(panelEliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 50, 50));

        jTabbedPane1.addTab("Ventas", panelVentas);

        panelCatalogo.setBackground(new java.awt.Color(255, 255, 255));
        panelCatalogo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setBackground(new java.awt.Color(0, 0, 204));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Permisos para Catálogo");
        panelCatalogo.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 160, -1));

        TableCatalogo = new Table();
        TableCatalogo.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableCatalogo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Área", "Acceso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableCatalogo.setToolTipText("Determine los permisos para el área de Catálogo");
        TableCatalogo.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableCatalogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableCatalogoMouseClicked(evt);
            }
        });
        TableCatalogo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableCatalogoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableCatalogoKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(TableCatalogo);
        if (TableCatalogo.getColumnModel().getColumnCount() > 0) {
            TableCatalogo.getColumnModel().getColumn(0).setPreferredWidth(120);
            TableCatalogo.getColumnModel().getColumn(1).setPreferredWidth(20);
        }

        panelCatalogo.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 270, 200));

        jLabel28.setBackground(new java.awt.Color(0, 0, 204));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 255));
        jLabel28.setText("¿Está seguro de ejecutar esta acción? ");
        panelCatalogo.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        panelAceptar2.setBackground(new java.awt.Color(255, 255, 255));
        panelAceptar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAceptar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAceptar2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelAceptar2MouseEntered(evt);
            }
        });

        btnAceptar2.setBackground(new java.awt.Color(153, 204, 255));
        btnAceptar2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAceptar2.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAceptar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnAceptar2.setToolTipText("ENTER - Guardar datos del perfil");
        btnAceptar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptar2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptar2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptar2MouseExited(evt);
            }
        });
        btnAceptar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptar2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAceptar2Layout = new javax.swing.GroupLayout(panelAceptar2);
        panelAceptar2.setLayout(panelAceptar2Layout);
        panelAceptar2Layout.setHorizontalGroup(
            panelAceptar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelAceptar2Layout.setVerticalGroup(
            panelAceptar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelCatalogo.add(panelAceptar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 50, 50));

        panelLimpiar2.setBackground(new java.awt.Color(255, 255, 255));
        panelLimpiar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelLimpiar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLimpiar2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLimpiar2MouseEntered(evt);
            }
        });

        btnLimpiar2.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnLimpiar2.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnLimpiar2.setToolTipText("F1 - Limpiar todos los campos");
        btnLimpiar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiar2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiar2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiar2MouseExited(evt);
            }
        });
        btnLimpiar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLimpiar2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelLimpiar2Layout = new javax.swing.GroupLayout(panelLimpiar2);
        panelLimpiar2.setLayout(panelLimpiar2Layout);
        panelLimpiar2Layout.setHorizontalGroup(
            panelLimpiar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelLimpiar2Layout.setVerticalGroup(
            panelLimpiar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelCatalogo.add(panelLimpiar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 50, 50));

        panelEliminar2.setBackground(new java.awt.Color(255, 255, 255));
        panelEliminar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEliminar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEliminar2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEliminar2MouseEntered(evt);
            }
        });

        btnEliminar2.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnEliminar2.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnEliminar2.setToolTipText("ESCAPE - Cancelar accion y salir");
        btnEliminar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminar2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminar2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminar2MouseExited(evt);
            }
        });
        btnEliminar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelEliminar2Layout = new javax.swing.GroupLayout(panelEliminar2);
        panelEliminar2.setLayout(panelEliminar2Layout);
        panelEliminar2Layout.setHorizontalGroup(
            panelEliminar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelEliminar2Layout.setVerticalGroup(
            panelEliminar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelCatalogo.add(panelEliminar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 50, 50));

        jTabbedPane1.addTab("Catálogo", panelCatalogo);

        panelControl.setBackground(new java.awt.Color(255, 255, 255));
        panelControl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setBackground(new java.awt.Color(0, 0, 204));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Permisos para Control");
        panelControl.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 160, -1));

        TableControl = new Table();
        TableControl.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableControl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Área", "Acceso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableControl.setToolTipText("Determine los permisos para el área de Control");
        TableControl.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableControl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableControlMouseClicked(evt);
            }
        });
        TableControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableControlKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(TableControl);
        if (TableControl.getColumnModel().getColumnCount() > 0) {
            TableControl.getColumnModel().getColumn(0).setPreferredWidth(120);
            TableControl.getColumnModel().getColumn(1).setPreferredWidth(20);
        }

        panelControl.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 270, 200));

        jLabel30.setBackground(new java.awt.Color(0, 0, 204));
        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 255));
        jLabel30.setText("¿Está seguro de ejecutar esta acción? ");
        panelControl.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        panelAceptar3.setBackground(new java.awt.Color(255, 255, 255));
        panelAceptar3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAceptar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAceptar3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelAceptar3MouseEntered(evt);
            }
        });

        btnAceptar3.setBackground(new java.awt.Color(153, 204, 255));
        btnAceptar3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAceptar3.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAceptar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnAceptar3.setToolTipText("ENTER - Guardar datos del perfil");
        btnAceptar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptar3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptar3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptar3MouseExited(evt);
            }
        });
        btnAceptar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptar3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAceptar3Layout = new javax.swing.GroupLayout(panelAceptar3);
        panelAceptar3.setLayout(panelAceptar3Layout);
        panelAceptar3Layout.setHorizontalGroup(
            panelAceptar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar3, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelAceptar3Layout.setVerticalGroup(
            panelAceptar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelControl.add(panelAceptar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 50, 50));

        panelLimpiar3.setBackground(new java.awt.Color(255, 255, 255));
        panelLimpiar3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelLimpiar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLimpiar3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLimpiar3MouseEntered(evt);
            }
        });

        btnLimpiar3.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnLimpiar3.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnLimpiar3.setToolTipText("F1 - Limpiar todos los campos");
        btnLimpiar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiar3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiar3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiar3MouseExited(evt);
            }
        });
        btnLimpiar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLimpiar3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelLimpiar3Layout = new javax.swing.GroupLayout(panelLimpiar3);
        panelLimpiar3.setLayout(panelLimpiar3Layout);
        panelLimpiar3Layout.setHorizontalGroup(
            panelLimpiar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar3, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelLimpiar3Layout.setVerticalGroup(
            panelLimpiar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelControl.add(panelLimpiar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 50, 50));

        panelEliminar3.setBackground(new java.awt.Color(255, 255, 255));
        panelEliminar3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEliminar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEliminar3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEliminar3MouseEntered(evt);
            }
        });

        btnEliminar3.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnEliminar3.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnEliminar3.setToolTipText("ESCAPE - Cancelar accion y salir");
        btnEliminar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminar3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminar3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminar3MouseExited(evt);
            }
        });
        btnEliminar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelEliminar3Layout = new javax.swing.GroupLayout(panelEliminar3);
        panelEliminar3.setLayout(panelEliminar3Layout);
        panelEliminar3Layout.setHorizontalGroup(
            panelEliminar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar3, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelEliminar3Layout.setVerticalGroup(
            panelEliminar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelControl.add(panelEliminar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 50, 50));

        jTabbedPane1.addTab("Control", panelControl);

        panelConfiguraciones.setBackground(new java.awt.Color(255, 255, 255));
        panelConfiguraciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setBackground(new java.awt.Color(0, 0, 204));
        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Permisos para Configuraciones");
        panelConfiguraciones.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 200, -1));

        TableConfiguraciones = new Table();
        TableConfiguraciones.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableConfiguraciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Área", "Acceso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableConfiguraciones.setToolTipText("Determine los permisos para el área de Configuraciones");
        TableConfiguraciones.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableConfiguraciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableConfiguracionesMouseClicked(evt);
            }
        });
        TableConfiguraciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableConfiguracionesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableConfiguracionesKeyReleased(evt);
            }
        });
        jScrollPane7.setViewportView(TableConfiguraciones);
        if (TableConfiguraciones.getColumnModel().getColumnCount() > 0) {
            TableConfiguraciones.getColumnModel().getColumn(0).setPreferredWidth(120);
            TableConfiguraciones.getColumnModel().getColumn(1).setPreferredWidth(20);
        }

        panelConfiguraciones.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 270, 200));

        jLabel32.setBackground(new java.awt.Color(0, 0, 204));
        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 255));
        jLabel32.setText("¿Está seguro de ejecutar esta acción? ");
        panelConfiguraciones.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        panelAceptar4.setBackground(new java.awt.Color(255, 255, 255));
        panelAceptar4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAceptar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAceptar4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelAceptar4MouseEntered(evt);
            }
        });

        btnAceptar4.setBackground(new java.awt.Color(153, 204, 255));
        btnAceptar4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAceptar4.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAceptar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnAceptar4.setToolTipText("ENTER - Guardar datos del perfil");
        btnAceptar4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptar4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptar4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptar4MouseExited(evt);
            }
        });
        btnAceptar4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptar4KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAceptar4Layout = new javax.swing.GroupLayout(panelAceptar4);
        panelAceptar4.setLayout(panelAceptar4Layout);
        panelAceptar4Layout.setHorizontalGroup(
            panelAceptar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar4, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelAceptar4Layout.setVerticalGroup(
            panelAceptar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelConfiguraciones.add(panelAceptar4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 50, 50));

        panelLimpiar4.setBackground(new java.awt.Color(255, 255, 255));
        panelLimpiar4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelLimpiar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLimpiar4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLimpiar4MouseEntered(evt);
            }
        });

        btnLimpiar4.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnLimpiar4.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnLimpiar4.setToolTipText("F1 - Limpiar todos los campos");
        btnLimpiar4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiar4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiar4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiar4MouseExited(evt);
            }
        });
        btnLimpiar4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLimpiar4KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelLimpiar4Layout = new javax.swing.GroupLayout(panelLimpiar4);
        panelLimpiar4.setLayout(panelLimpiar4Layout);
        panelLimpiar4Layout.setHorizontalGroup(
            panelLimpiar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar4, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelLimpiar4Layout.setVerticalGroup(
            panelLimpiar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelConfiguraciones.add(panelLimpiar4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 50, 50));

        panelEliminar4.setBackground(new java.awt.Color(255, 255, 255));
        panelEliminar4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEliminar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEliminar4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEliminar4MouseEntered(evt);
            }
        });

        btnEliminar4.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnEliminar4.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnEliminar4.setToolTipText("ESCAPE - Cancelar accion y salir");
        btnEliminar4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminar4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminar4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminar4MouseExited(evt);
            }
        });
        btnEliminar4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar4KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelEliminar4Layout = new javax.swing.GroupLayout(panelEliminar4);
        panelEliminar4.setLayout(panelEliminar4Layout);
        panelEliminar4Layout.setHorizontalGroup(
            panelEliminar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar4, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelEliminar4Layout.setVerticalGroup(
            panelEliminar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelConfiguraciones.add(panelEliminar4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 50, 50));

        jTabbedPane1.addTab("Configuraciones", panelConfiguraciones);

        panelReportes.setBackground(new java.awt.Color(255, 255, 255));
        panelReportes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setBackground(new java.awt.Color(0, 0, 204));
        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Permisos para Reportes");
        panelReportes.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 160, -1));

        TableReportes = new Table();
        TableReportes.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        TableReportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Área", "Acceso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableReportes.setToolTipText("Determine los permisos para el área de Reportes");
        TableReportes.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TableReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableReportesMouseClicked(evt);
            }
        });
        TableReportes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableReportesKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(TableReportes);
        if (TableReportes.getColumnModel().getColumnCount() > 0) {
            TableReportes.getColumnModel().getColumn(0).setPreferredWidth(120);
            TableReportes.getColumnModel().getColumn(1).setPreferredWidth(20);
        }

        panelReportes.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 270, 200));

        jLabel34.setBackground(new java.awt.Color(0, 0, 204));
        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 255));
        jLabel34.setText("¿Está seguro de ejecutar esta acción? ");
        panelReportes.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        panelAceptar5.setBackground(new java.awt.Color(255, 255, 255));
        panelAceptar5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAceptar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAceptar5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelAceptar5MouseEntered(evt);
            }
        });

        btnAceptar5.setBackground(new java.awt.Color(153, 204, 255));
        btnAceptar5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAceptar5.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAceptar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnAceptar5.setToolTipText("ENTER - Guardar datos del perfil");
        btnAceptar5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptar5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptar5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptar5MouseExited(evt);
            }
        });
        btnAceptar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptar5KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAceptar5Layout = new javax.swing.GroupLayout(panelAceptar5);
        panelAceptar5.setLayout(panelAceptar5Layout);
        panelAceptar5Layout.setHorizontalGroup(
            panelAceptar5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar5, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelAceptar5Layout.setVerticalGroup(
            panelAceptar5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelReportes.add(panelAceptar5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 50, 50));

        panelLimpiar5.setBackground(new java.awt.Color(255, 255, 255));
        panelLimpiar5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelLimpiar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLimpiar5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLimpiar5MouseEntered(evt);
            }
        });

        btnLimpiar5.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnLimpiar5.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 22.png"))); // NOI18N
        btnLimpiar5.setToolTipText("F1 - Limpiar todos los campos");
        btnLimpiar5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiar5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiar5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiar5MouseExited(evt);
            }
        });
        btnLimpiar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLimpiar5KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelLimpiar5Layout = new javax.swing.GroupLayout(panelLimpiar5);
        panelLimpiar5.setLayout(panelLimpiar5Layout);
        panelLimpiar5Layout.setHorizontalGroup(
            panelLimpiar5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar5, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelLimpiar5Layout.setVerticalGroup(
            panelLimpiar5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelReportes.add(panelLimpiar5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 50, 50));

        panelEliminar5.setBackground(new java.awt.Color(255, 255, 255));
        panelEliminar5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEliminar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEliminar5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEliminar5MouseEntered(evt);
            }
        });

        btnEliminar5.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar5.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnEliminar5.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"))); // NOI18N
        btnEliminar5.setToolTipText("ESCAPE - Cancelar accion y salir");
        btnEliminar5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminar5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminar5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminar5MouseExited(evt);
            }
        });
        btnEliminar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminar5KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelEliminar5Layout = new javax.swing.GroupLayout(panelEliminar5);
        panelEliminar5.setLayout(panelEliminar5Layout);
        panelEliminar5Layout.setHorizontalGroup(
            panelEliminar5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar5, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelEliminar5Layout.setVerticalGroup(
            panelEliminar5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelReportes.add(panelEliminar5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 50, 50));

        jTabbedPane1.addTab("Reportes", panelReportes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked

    }//GEN-LAST:event_TableVentasMouseClicked

    private void TableVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentasKeyPressed

        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_F1:
            eventoF1();
            break;

     

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_TableVentasKeyPressed

    private void TableCatalogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableCatalogoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableCatalogoMouseClicked

    private void TableCatalogoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableCatalogoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TableCatalogoKeyPressed

    private void TableControlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableControlMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableControlMouseClicked

    private void TableControlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableControlKeyPressed
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
    }//GEN-LAST:event_TableControlKeyPressed

    private void TableConfiguracionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableConfiguracionesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableConfiguracionesMouseClicked

    private void TableConfiguracionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableConfiguracionesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TableConfiguracionesKeyPressed

    private void btnAceptar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar4MouseClicked
      eventoEnter();
    }//GEN-LAST:event_btnAceptar4MouseClicked

    private void btnAceptar4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar4MouseEntered
      panelAceptar4.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAceptar4MouseEntered

    private void btnAceptar4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar4MouseExited
        panelAceptar4.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnAceptar4MouseExited

    private void btnAceptar4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptar4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptar4KeyPressed

    private void panelAceptar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar4MouseClicked

    private void panelAceptar4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar4MouseEntered

    private void btnLimpiar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar4MouseClicked
      eventoF1();
    }//GEN-LAST:event_btnLimpiar4MouseClicked

    private void btnLimpiar4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar4MouseEntered
      panelLimpiar4.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiar4MouseEntered

    private void btnLimpiar4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar4MouseExited
       panelLimpiar4.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiar4MouseExited

    private void btnLimpiar4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiar4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiar4KeyPressed

    private void panelLimpiar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar4MouseClicked

    private void panelLimpiar4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar4MouseEntered

    private void btnEliminar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar4MouseClicked
    dispose();
    }//GEN-LAST:event_btnEliminar4MouseClicked

    private void btnEliminar4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar4MouseEntered
       panelEliminar4.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar4MouseEntered

    private void btnEliminar4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar4MouseExited
      panelEliminar4.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar4MouseExited

    private void btnEliminar4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar4KeyPressed

    private void panelEliminar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar4MouseClicked

    private void panelEliminar4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar4MouseEntered

    private void TableReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableReportesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableReportesMouseClicked

    private void TableReportesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableReportesKeyPressed
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
    }//GEN-LAST:event_TableReportesKeyPressed

    private void btnAceptar5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar5MouseClicked
        eventoEnter();
    }//GEN-LAST:event_btnAceptar5MouseClicked

    private void btnAceptar5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar5MouseEntered
       panelAceptar5.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAceptar5MouseEntered

    private void btnAceptar5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar5MouseExited
        panelAceptar5.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnAceptar5MouseExited

    private void btnAceptar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptar5KeyPressed

    private void panelAceptar5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar5MouseClicked

    private void panelAceptar5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar5MouseEntered

    private void btnLimpiar5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar5MouseClicked
      eventoF1();
    }//GEN-LAST:event_btnLimpiar5MouseClicked

    private void btnLimpiar5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar5MouseEntered
        panelLimpiar5.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiar5MouseEntered

    private void btnLimpiar5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar5MouseExited
       panelLimpiar5.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiar5MouseExited

    private void btnLimpiar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiar5KeyPressed

    private void panelLimpiar5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar5MouseClicked

    private void panelLimpiar5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar5MouseEntered

    private void btnEliminar5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar5MouseClicked
       dispose();
    }//GEN-LAST:event_btnEliminar5MouseClicked

    private void btnEliminar5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar5MouseEntered
        panelEliminar5.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar5MouseEntered

    private void btnEliminar5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar5MouseExited
      panelEliminar5.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnEliminar5MouseExited

    private void btnEliminar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar5KeyPressed

    private void panelEliminar5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar5MouseClicked

    private void panelEliminar5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar5MouseEntered

    private void panelEliminar3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar3MouseEntered

    private void panelEliminar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar3MouseClicked

    private void btnEliminar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar3KeyPressed

    private void btnEliminar3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar3MouseExited
        panelEliminar.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnEliminar3MouseExited

    private void btnEliminar3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar3MouseEntered
       panelEliminar3.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar3MouseEntered

    private void btnEliminar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar3MouseClicked
       dispose();
    }//GEN-LAST:event_btnEliminar3MouseClicked

    private void panelLimpiar3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar3MouseEntered

    private void panelLimpiar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar3MouseClicked

    private void btnLimpiar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiar3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiar3KeyPressed

    private void btnLimpiar3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar3MouseExited
       panelLimpiar3.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnLimpiar3MouseExited

    private void btnLimpiar3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar3MouseEntered
      panelLimpiar3.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiar3MouseEntered

    private void btnLimpiar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar3MouseClicked
      eventoF1();
    }//GEN-LAST:event_btnLimpiar3MouseClicked

    private void panelAceptar3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar3MouseEntered

    private void panelAceptar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar3MouseClicked

    private void btnAceptar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptar3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptar3KeyPressed

    private void btnAceptar3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar3MouseExited
      panelAceptar3.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnAceptar3MouseExited

    private void btnAceptar3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar3MouseEntered
       panelAceptar3.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAceptar3MouseEntered

    private void btnAceptar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar3MouseClicked
       eventoEnter();
    }//GEN-LAST:event_btnAceptar3MouseClicked

    private void panelEliminar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar2MouseEntered

    private void panelEliminar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar2MouseClicked

    private void btnEliminar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar2KeyPressed

    private void btnEliminar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar2MouseExited
       panelLimpiar1.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnEliminar2MouseExited

    private void btnEliminar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar2MouseEntered
       panelEliminar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar2MouseEntered

    private void btnEliminar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar2MouseClicked
       dispose();
    }//GEN-LAST:event_btnEliminar2MouseClicked

    private void panelLimpiar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar2MouseEntered

    private void panelLimpiar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar2MouseClicked

    private void btnLimpiar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiar2KeyPressed

    private void btnLimpiar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar2MouseExited
        panelLimpiar2.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnLimpiar2MouseExited

    private void btnLimpiar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar2MouseEntered
         panelLimpiar2.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiar2MouseEntered

    private void btnLimpiar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar2MouseClicked
      eventoF1();
    }//GEN-LAST:event_btnLimpiar2MouseClicked

    private void panelAceptar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar2MouseEntered

    private void panelAceptar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar2MouseClicked

    private void btnAceptar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptar2KeyPressed

    private void btnAceptar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar2MouseExited
       panelAceptar2.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnAceptar2MouseExited

    private void btnAceptar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar2MouseEntered
        panelAceptar2.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAceptar2MouseEntered

    private void btnAceptar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar2MouseClicked
       eventoEnter();
    }//GEN-LAST:event_btnAceptar2MouseClicked

    private void panelEliminar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar1MouseEntered

    private void panelEliminar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar1MouseClicked

    private void btnEliminar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar1KeyPressed

    private void btnEliminar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseExited
     panelEliminar1.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar1MouseExited

    private void btnEliminar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseEntered
      panelEliminar1.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar1MouseEntered

    private void btnEliminar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseClicked
      dispose();
    }//GEN-LAST:event_btnEliminar1MouseClicked

    private void panelLimpiar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar1MouseEntered

    private void panelLimpiar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar1MouseClicked

    private void btnLimpiar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiar1KeyPressed

    private void btnLimpiar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar1MouseExited
        panelLimpiar1.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnLimpiar1MouseExited

    private void btnLimpiar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar1MouseEntered
        panelLimpiar1.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiar1MouseEntered

    private void btnLimpiar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar1MouseClicked
      eventoF1();
    }//GEN-LAST:event_btnLimpiar1MouseClicked

    private void panelAceptar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar1MouseEntered

    private void panelAceptar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar1MouseClicked

    private void btnAceptar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptar1KeyPressed

    private void btnAceptar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar1MouseExited
       panelAceptar1.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnAceptar1MouseExited

    private void btnAceptar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar1MouseEntered
      panelAceptar1.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAceptar1MouseEntered

    private void btnAceptar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar1MouseClicked
          eventoEnter();
    }//GEN-LAST:event_btnAceptar1MouseClicked

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
        event.textKeyPress(evt);
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

    private void TableVentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentasKeyReleased
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
    }//GEN-LAST:event_TableVentasKeyReleased

    private void TableCatalogoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableCatalogoKeyReleased
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
    }//GEN-LAST:event_TableCatalogoKeyReleased

    private void TableConfiguracionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableConfiguracionesKeyReleased
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
    }//GEN-LAST:event_TableConfiguracionesKeyReleased

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
            java.util.logging.Logger.getLogger(crearModificarPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crearModificarPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crearModificarPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crearModificarPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                crearModificarPerfil dialog = new crearModificarPerfil(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TableCatalogo;
    private javax.swing.JTable TableConfiguraciones;
    private javax.swing.JTable TableControl;
    private javax.swing.JTable TableReportes;
    private javax.swing.JTable TableVentas;
    private javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnAceptar1;
    private javax.swing.JLabel btnAceptar2;
    private javax.swing.JLabel btnAceptar3;
    private javax.swing.JLabel btnAceptar4;
    private javax.swing.JLabel btnAceptar5;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnEliminar1;
    private javax.swing.JLabel btnEliminar2;
    private javax.swing.JLabel btnEliminar3;
    private javax.swing.JLabel btnEliminar4;
    private javax.swing.JLabel btnEliminar5;
    private javax.swing.JLabel btnLimpiar;
    private javax.swing.JLabel btnLimpiar1;
    private javax.swing.JLabel btnLimpiar2;
    private javax.swing.JLabel btnLimpiar3;
    private javax.swing.JLabel btnLimpiar4;
    private javax.swing.JLabel btnLimpiar5;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelAceptar1;
    private javax.swing.JPanel panelAceptar2;
    private javax.swing.JPanel panelAceptar3;
    private javax.swing.JPanel panelAceptar4;
    private javax.swing.JPanel panelAceptar5;
    private javax.swing.JPanel panelCatalogo;
    private javax.swing.JPanel panelConfiguraciones;
    private javax.swing.JPanel panelControl;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelEliminar1;
    private javax.swing.JPanel panelEliminar2;
    private javax.swing.JPanel panelEliminar3;
    private javax.swing.JPanel panelEliminar4;
    private javax.swing.JPanel panelEliminar5;
    private javax.swing.JPanel panelLimpiar;
    private javax.swing.JPanel panelLimpiar1;
    private javax.swing.JPanel panelLimpiar2;
    private javax.swing.JPanel panelLimpiar3;
    private javax.swing.JPanel panelLimpiar4;
    private javax.swing.JPanel panelLimpiar5;
    private javax.swing.JPanel panelReportes;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
   }
    
}

class CustomCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cellComponent.setForeground(Color.BLACK);
            return cellComponent;
        }
    }
