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
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.InventarioFueraDao;
import Modelo.Linea;
import Modelo.LineaDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.TextPrompt;
import Modelo.bitacoraDao;
import Modelo.configuraciones;
import static Vista.CrearModificarProveedor.modificarString;
import static Vista.vistaVenta.removefirstChar;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;

/**
 *
 * @author Jonathan Gil
 */
public class RegistrarModificarProducto extends javax.swing.JDialog {

        Eventos event = new Eventos();
        configuraciones config = new configuraciones();
        DecimalFormat formato = new DecimalFormat("#0.00");
        EmpleadosDao emple = new EmpleadosDao();
        ProductoDao productos = new ProductoDao();
        DepartamentoDao departamentoDao = new DepartamentoDao();
        LineaDao lineaDao = new LineaDao();
        bitacoraDao bitacora = new bitacoraDao();
        InventarioFueraDao inventarioF = new InventarioFueraDao();
        CorteDiarioDao corteDao = new CorteDiarioDao();

     
        String fecha = corteDao.getDia();      
           DecimalFormat formateador = new DecimalFormat("#,###,##0.00");     
        boolean indicador;
        boolean accionCompletada=false;
        boolean bloqueando=false;
        Producto original;
        double descuento, utilidad, precioVenta=0;
        int idEmpleado;
    
    public void vaciarEmpleado(int idEmpleado){
        this.idEmpleado=idEmpleado;
    }



    public RegistrarModificarProducto(java.awt.Frame parent, boolean modal) {
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
        txtFecha.setBackground(new Color(240,240,240));
        txtDepartamento.setBackground(new Color(240,240,240));

        txtFecha.setBackground(new Color(240,240,240));
        txtDepartamento.setBackground(new Color(240,240,240));
        txtUtilidad.setBackground(new Color(240,240,240));
        txtDescuento.setBackground(new Color(240,240,240));
        txtPrecio.setBackground(new Color(240,240,240));
         //Datos

       txtLinea.setBackground(new Color(255,255,255));
       txtUnidad.setBackground(new Color(255,255,255));
    }

    
    public void validandoDatos(Producto p, boolean indicador){
          this.indicador=indicador;
            this.original=p;
            listarLinea(p.getLinea());
        if(indicador==true){ //nuevo producto
            this.setTitle("Punto de venta - Registrar producto");
             Date diaActual = StringADate(fecha);
            DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
            String fechaUsar;
            Date fechaNueva;
            Calendar c = Calendar.getInstance();
            c.setTime(diaActual);
            fechaUsar = cam.format(diaActual);
            txtFecha.setText(fechaUsar);
               txtInventario.setSelected(true);
                txtCantidadMinima.setVisible(true);
                txtExistencia.setVisible(true);
            
        }else{ //modificar producto
            this.setTitle("Punto de venta - Modificar producto");
             Date diaActual = StringADate(original.getFechaCreacion());
            DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
            String fechaUsar;
            Date fechaNueva;
            Calendar c = Calendar.getInstance();
            c.setTime(diaActual);
            fechaUsar = cam.format(diaActual);
            txtFecha.setText(fechaUsar);
            txtCodigoBarras.setText(original.getCodigo());
            txtDescripcion.setText(original.getDescripcion());
            txtCantidadMinima.setText(original.getMinimo()+"");
            Departamento deo = departamentoDao.BuscarPorCodigo(original.getDepartamento());
            txtDepartamento.setText(deo.getNombre());
            Linea lin = lineaDao.BuscarPorCodigo(original.getLinea());
            txtLinea.setSelectedItem(lin.getNombre());
            txtPrecioUsuario.setText(formato.format(original.getPrecioUsuario()));
            txtCosto.setText(formato.format(original.getPrecioCompra()));
            txtPrecio.setText("$"+formateador.format(original.getPrecioVenta()));
            txtExistencia.setText(original.getExistencia()+"");
            txtCantidadMinima.setText(original.getMinimo()+"");
            txtUnidad.setSelectedItem(original.getTipoVenta());
            if(original.getInventario()==0){ //no maneja inventario
                txtCantidadMinima.setVisible(false);
                txtExistencia.setVisible(false);
                selecAviso.setEnabled(false);
                lbMinimo.setVisible(false);
                 lbExistencia.setVisible(false);
                txtInventario.setSelected(false);
            }else{
                txtInventario.setSelected(true);
                txtCantidadMinima.setVisible(true);
                txtExistencia.setVisible(true);
                lbMinimo.setVisible(true);
                lbExistencia.setVisible(true);
                txtExistencia.setText(formato.format(original.getExistencia()));
                txtCantidadMinima.setText(formato.format(original.getMinimo()));
                selecAviso.setEnabled(true);
                if(original.getAviso()==1) {
                    selecAviso.setSelected(true);
                    selecAviso1.setSelected(true);
                }
                else selecAviso.setSelected(false);
            }
      
        }
       
    }
    
    public void bloqueandoTodo(){
        this.setTitle("Punto de venta - Visualizar información de producto");
        jLabel25.setText("Visualizar producto");
        this.bloqueando=true;
        txtCodigoBarras.setEnabled(false);
        txtDescripcion.setEnabled(false);
        txtLinea.setEnabled(false);
        txtCantidadMinima.setEnabled(false);
        selecAviso.setEnabled(false);
        jLabel23.setVisible(false);
        panelAceptar.setVisible(false);
        panelLimpiar.setVisible(false);
        panelEliminar.setVisible(false);
        txtLinea.setBackground(new Color(240,240,240));
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

    
  

   
    public void listarLinea(int linea) {
        List<Linea> lsLinea = lineaDao.listarLineas(); //Se obtienen los empleados de la base de datos (para generar nota
        txtLinea.removeAllItems();//Se borran los datos del ComboBox para evitar que se sobreencimen
        txtLinea.addItem("Linea *");
        for (int i = 0; i < lsLinea.size(); i++) {//vaciamos los datos
            if(lsLinea.get(i).getEstado()==1 || lsLinea.get(i).getId()==linea)
            txtLinea.addItem(lsLinea.get(i).getNombre());
        }
    } 
    
    public void eventoF1Datos(){
            if(bloqueando==false){
        txtCodigoBarras.setText("");
        txtDescripcion.setText("");
        txtUnidad.setSelectedIndex(0);
               selecAviso1.setSelected(false);
        selecAviso.setSelected(false);
        txtInventario.setEnabled(true);
        txtInventario.setSelected(true);
        txtCantidadMinima.setVisible(true);
        txtExistencia.setVisible(true);
            }
    }
    
    public void eventoF1Costo(){
     if(bloqueando==false){
        txtCosto.setText("");
        txtLinea.setSelectedIndex(0);
        txtCantidadMinima.setText("");
        txtExistencia.setText("");
        selecAviso1.setSelected(false);
        selecAviso.setSelected(false);
     }
    }
    
        public void eventoEnter(){
            if(bloqueando==false){
     if(txtLinea.getSelectedIndex()!=0 && txtUnidad.getSelectedIndex()!=0 && !"".equals(txtDescripcion.getText()) && !"".equals(txtCodigoBarras.getText()) && ((txtInventario.isSelected() && !"".equals(txtCantidadMinima.getText()) && !"".equals(txtExistencia.getText())) || (!txtInventario.isSelected() && "".equals(txtCantidadMinima.getText()) && "".equals(txtExistencia.getText())))){
               Empleados empleado = emple.seleccionarEmpleado("",idEmpleado);
                ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(empleado.getId());
                   cc.setVisible(true);
                   if(cc.contraseñaAceptada==true){
                 Producto p = new Producto();
                p.setId(original.getId());
                p.setCodigo(txtCodigoBarras.getText());
                p.setDescripcion(modificarString(txtDescripcion.getText()));
                p.setFechaCreacion(fecha);
                p.setIdEmpleado(empleado.getId());
                if(txtInventario.isSelected()){
                  p.setMinimo(Double.parseDouble(txtCantidadMinima.getText()));
                  p.setExistencia(Double.parseDouble(txtExistencia.getText()));
                }else{
                      p.setMinimo(0);
                  p.setExistencia(0);
                }
                if(selecAviso.isSelected())
                p.setAviso(1);
                else p.setAviso(0);
                if(txtInventario.isSelected()) p.setInventario(1);
                else p.setInventario(0);
                Departamento dep = departamentoDao.seleccionarDepartamento(txtDepartamento.getText(), 0);
                p.setDepartamento(dep.getId());
                Linea lin = lineaDao.seleccionarDepartamento(txtLinea.getSelectedItem().toString(), 0);
                p.setLinea(lin.getId());
                p.setTipoVenta(txtUnidad.getSelectedItem().toString());
                 Number precioT=0;
                 Number costoT=0;
                 Number precioUsuarioT=0;
                 try {
                     precioT = formateador.parse(removefirstChar(txtPrecio.getText()));
                     costoT = formateador.parse(txtCosto.getText());
                     precioUsuarioT = formateador.parse(txtPrecioUsuario.getText());
                 } catch (ParseException ex) {
                     Logger.getLogger(RegistrarModificarProducto.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 p.setPrecioVenta(precioT.doubleValue());
                 p.setPrecioCompra(costoT.doubleValue());
                 p.setPrecioUsuario(precioUsuarioT.doubleValue());
                 
                if(indicador==true){//crear producto  
                    if (productos.ProductoRepetido(p) == true) {
         Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Producto repetido");
                                 panel.showNotification();
                        } else {
                            productos.RegistrarProducto(p);
                            accionCompletada=true;
                             bitacora.registrarRegistro("Nuevo Producto: "+p.getDescripcion(), empleado.getId(),fecha);
   
                            dispose();
                            
                            }
                }else{//actualizar producto
                    if (productos.ProductoRepetidaActualizar(p) == true) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Producto repetido");
                                 panel.showNotification();
                            } else {
                                productos.ModificarProducto(p);
                                accionCompletada=true;
                                
                              
                                  if(!original.getTipoVenta().equals(p.getTipoVenta())){
                                 bitacora.registrarRegistro("Se modificó el modo en que se vende el producto "+original.getDescripcion()+" de "+original.getTipoVenta()+" a "+p.getTipoVenta(),empleado.getId(),fecha);
                                }
                                  if(original.getDepartamento()!=p.getDepartamento()){
                                      Departamento departamentoViejo = departamentoDao.BuscarPorCodigo(original.getDepartamento());
                                      Departamento departamentoNuevo = departamentoDao.BuscarPorCodigo(p.getDepartamento());
                                    bitacora.registrarRegistro("Se paso el producto "+original.getDescripcion()+" del Departamento "+departamentoViejo.getNombre()+" al departamento "+departamentoNuevo.getNombre(),empleado.getId(),fecha);
                                  }
                                  
                                  if(original.getLinea()!=p.getLinea()){
                                      Linea lineaViejo = lineaDao.BuscarPorCodigo(original.getLinea());
                                      Linea lineaNuevo = lineaDao.BuscarPorCodigo(p.getLinea());
                                    bitacora.registrarRegistro("Se paso el producto "+original.getDescripcion()+" de la Linea "+lineaViejo.getNombre()+" a la Linea "+lineaNuevo.getNombre(),empleado.getId(),fecha);
                                  }
                                   if(original.getMinimo()!= p.getMinimo()){
                                 bitacora.registrarRegistro("Se modificó stock minímo del producto "+original.getDescripcion()+" de "+original.getMinimo()+" a "+p.getMinimo(),empleado.getId(),fecha);
                                }
                                   
                                     if(!original.getDescripcion().equals(p.getDescripcion())){
                                 bitacora.registrarRegistro("Se modificó la descripción del producto "+original.getDescripcion()+" a "+p.getDescripcion(),empleado.getId(),fecha);
                                }
                                      if(!original.getCodigo().equals(p.getCodigo())){
                                 bitacora.registrarRegistro("Se modificó el código del producto "+original.getDescripcion()+" de "+original.getCodigo()+" a "+p.getCodigo(),empleado.getId(),fecha);
                                 inventarioF.actualizarCodigo(original.getCodigo(), p.getCodigo());
                                      }
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
        jPanel1 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        panelAceptar = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JLabel();
        panelLimpiar = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();
        selecAviso = new javax.swing.JCheckBox();
        txtFecha = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtUnidad = new javax.swing.JComboBox<>();
        txtCodigoBarras = new javax.swing.JTextField();
        txtInventario = new javax.swing.JCheckBox();
        lbNombre2 = new javax.swing.JLabel();
        lbNombre = new javax.swing.JLabel();
        lbNombre4 = new javax.swing.JLabel();
        lbNombre6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        panelAceptar1 = new javax.swing.JPanel();
        btnAceptar1 = new javax.swing.JLabel();
        panelLimpiar1 = new javax.swing.JPanel();
        btnLimpiar1 = new javax.swing.JLabel();
        panelEliminar1 = new javax.swing.JPanel();
        btnEliminar1 = new javax.swing.JLabel();
        selecAviso1 = new javax.swing.JCheckBox();
        txtPrecio = new javax.swing.JTextField();
        txtExistencia = new javax.swing.JTextField();
        txtDepartamento = new javax.swing.JTextField();
        txtLinea = new javax.swing.JComboBox<>();
        txtUtilidad = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        txtCosto = new javax.swing.JTextField();
        txtCantidadMinima = new javax.swing.JTextField();
        lbNombre3 = new javax.swing.JLabel();
        lbNombre5 = new javax.swing.JLabel();
        lbNombre7 = new javax.swing.JLabel();
        lbNombre8 = new javax.swing.JLabel();
        lbNombre9 = new javax.swing.JLabel();
        lbExistencia = new javax.swing.JLabel();
        lbMinimo = new javax.swing.JLabel();
        lbNombre12 = new javax.swing.JLabel();
        lbNombre10 = new javax.swing.JLabel();
        txtPrecioUsuario = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setBackground(new java.awt.Color(0, 0, 204));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("¿Está seguro de ejecutar esta acción? ");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, -1, -1));

        jLabel25.setBackground(new java.awt.Color(0, 0, 204));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Datos del producto");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 180, -1));

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
        btnAceptar.setToolTipText("ENTER - Guardar datos del producto");
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

        jPanel1.add(panelAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 50, 50));

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

        jPanel1.add(panelLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 50, 50));

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

        jPanel1.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 50, 50));

        selecAviso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        selecAviso.setText("Notificar inventario bajo");
        selecAviso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selecAvisoMouseClicked(evt);
            }
        });
        selecAviso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                selecAvisoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                selecAvisoKeyReleased(evt);
            }
        });
        jPanel1.add(selecAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, -1, -1));

        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFecha.setToolTipText("Fecha");
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 50, 220, -1));

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescripcion.setToolTipText("Descripción");
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyReleased(evt);
            }
        });
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 110, 220, -1));

        txtUnidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUnidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unidad *", "Pieza", "Kilogramo", "Servicio" }));
        txtUnidad.setToolTipText("La forma en la que se venderá");
        txtUnidad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtUnidadItemStateChanged(evt);
            }
        });
        txtUnidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnidadKeyReleased(evt);
            }
        });
        jPanel1.add(txtUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 140, 220, -1));

        txtCodigoBarras.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCodigoBarras.setToolTipText("Código de barras");
        txtCodigoBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoBarrasKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoBarrasKeyTyped(evt);
            }
        });
        jPanel1.add(txtCodigoBarras, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 80, 220, -1));

        txtInventario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtInventario.setText("Maneja inventario");
        txtInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtInventarioMouseClicked(evt);
            }
        });
        txtInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtInventarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInventarioKeyReleased(evt);
            }
        });
        jPanel1.add(txtInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, -1, -1));

        lbNombre2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre2.setText("Fecha");
        jPanel1.add(lbNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 53, 70, -1));

        lbNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre.setText("Código de barras*");
        jPanel1.add(lbNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 83, 102, -1));

        lbNombre4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre4.setText("Descripción*");
        jPanel1.add(lbNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 113, -1, -1));

        lbNombre6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre6.setText("Unidad*");
        jPanel1.add(lbNombre6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 143, 70, -1));

        jTabbedPane1.addTab("Producto", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setBackground(new java.awt.Color(0, 0, 204));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 255));
        jLabel24.setText("¿Está seguro de ejecutar esta acción? ");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, -1, -1));

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Precio y costo del producto");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 220, -1));

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
        btnAceptar1.setToolTipText("ENTER - Guardar datos del producto");
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

        jPanel2.add(panelAceptar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 50, 50));

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

        jPanel2.add(panelLimpiar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, 50, 50));

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

        jPanel2.add(panelEliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 50, 50));

        selecAviso1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        selecAviso1.setText("Notificar inventario bajo");
        selecAviso1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selecAviso1MouseClicked(evt);
            }
        });
        selecAviso1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                selecAviso1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                selecAviso1KeyReleased(evt);
            }
        });
        jPanel2.add(selecAviso1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, -1, -1));

        txtPrecio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecio.setToolTipText("Precio de venta del producto");
        txtPrecio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecio.setEnabled(false);
        jPanel2.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 250, -1));

        txtExistencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtExistencia.setToolTipText("En existencia");
        txtExistencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExistenciaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExistenciaKeyTyped(evt);
            }
        });
        jPanel2.add(txtExistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 250, -1));

        txtDepartamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDepartamento.setToolTipText("Departamento");
        txtDepartamento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDepartamento.setEnabled(false);
        jPanel2.add(txtDepartamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 250, -1));

        txtLinea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLinea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Linea", "Item 2", "Item 3", "Item 4" }));
        txtLinea.setToolTipText("Selecciona la linea a la que pertenecerá");
        txtLinea.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtLineaItemStateChanged(evt);
            }
        });
        txtLinea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLineaKeyReleased(evt);
            }
        });
        jPanel2.add(txtLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 250, -1));

        txtUtilidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUtilidad.setToolTipText("Porcentaje de utilidad (%)");
        txtUtilidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUtilidad.setEnabled(false);
        jPanel2.add(txtUtilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 90, -1));

        txtDescuento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescuento.setToolTipText("Porcentaje de descuento (%)");
        txtDescuento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescuento.setEnabled(false);
        txtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoActionPerformed(evt);
            }
        });
        jPanel2.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 90, -1));

        txtCosto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCosto.setToolTipText("Costo dle producto");
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });
        jPanel2.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 250, -1));

        txtCantidadMinima.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCantidadMinima.setToolTipText("Cantidad miníma en inventario");
        txtCantidadMinima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadMinimaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadMinimaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCantidadMinima, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 250, -1));

        lbNombre3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre3.setText("Costo*");
        jPanel2.add(lbNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 53, 85, -1));

        lbNombre5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre5.setText("Linea*");
        jPanel2.add(lbNombre5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 85, -1));

        lbNombre7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre7.setText("Departamento");
        jPanel2.add(lbNombre7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 85, -1));

        lbNombre8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre8.setText("Descuento");
        jPanel2.add(lbNombre8, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 85, -1));

        lbNombre9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre9.setText("Precio Final");
        jPanel2.add(lbNombre9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 85, -1));

        lbExistencia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbExistencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbExistencia.setText("Existencia*");
        jPanel2.add(lbExistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 85, -1));

        lbMinimo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbMinimo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbMinimo.setText("Mínimo*");
        jPanel2.add(lbMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 85, -1));

        lbNombre12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre12.setText("Utilidad");
        jPanel2.add(lbNombre12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 85, -1));

        lbNombre10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre10.setText("Precio*");
        jPanel2.add(lbNombre10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 83, 85, -1));

        txtPrecioUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecioUsuario.setToolTipText("Costo dle producto");
        txtPrecioUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioUsuarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioUsuarioKeyTyped(evt);
            }
        });
        jPanel2.add(txtPrecioUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 250, -1));

        jTabbedPane1.addTab("Costo", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
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
            eventoF1Datos();
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
        eventoF1Datos();
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
            eventoF1Datos();
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
            eventoF1Datos();
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

    private void selecAvisoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selecAvisoKeyPressed
   
    }//GEN-LAST:event_selecAvisoKeyPressed

    private void btnAceptar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar1MouseClicked
           eventoEnter();
    }//GEN-LAST:event_btnAceptar1MouseClicked

    private void btnAceptar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar1MouseEntered
       panelAceptar1.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAceptar1MouseEntered

    private void btnAceptar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar1MouseExited
       panelAceptar1.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnAceptar1MouseExited

    private void btnAceptar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptar1KeyPressed

    private void panelAceptar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar1MouseClicked
    
    }//GEN-LAST:event_panelAceptar1MouseClicked

    private void panelAceptar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar1MouseEntered

    private void btnLimpiar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar1MouseClicked
      eventoF1Costo();
    }//GEN-LAST:event_btnLimpiar1MouseClicked

    private void btnLimpiar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar1MouseEntered
        panelLimpiar1.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnLimpiar1MouseEntered

    private void btnLimpiar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar1MouseExited
       panelLimpiar1.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnLimpiar1MouseExited

    private void btnLimpiar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiar1KeyPressed

    private void panelLimpiar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar1MouseClicked

    private void panelLimpiar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar1MouseEntered
         panelLimpiar1.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_panelLimpiar1MouseEntered

    private void btnEliminar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseClicked
       dispose();
    }//GEN-LAST:event_btnEliminar1MouseClicked

    private void btnEliminar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseEntered
               panelEliminar1.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar1MouseEntered

    private void btnEliminar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseExited
          panelEliminar1.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnEliminar1MouseExited

    private void btnEliminar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar1KeyPressed

    private void panelEliminar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar1MouseClicked

    private void panelEliminar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar1MouseEntered

    private void selecAviso1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selecAviso1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_selecAviso1KeyPressed

    private void txtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoActionPerformed

    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
     event.numberDecimalKeyPress(evt, txtCosto);
    }//GEN-LAST:event_txtCostoKeyTyped

    private void txtUnidadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtUnidadItemStateChanged
       if(txtUnidad.getSelectedIndex()==1 ){ //pieza
               txtCantidadMinima.setText("");
               txtExistencia.setText("");
       }
       
       if(txtUnidad.getSelectedIndex()!=3){
           txtInventario.setEnabled(true);
           if(txtInventario.isSelected()){
            txtCantidadMinima.setVisible(true);
           txtExistencia.setVisible(true);
           selecAviso.setEnabled(true);
           selecAviso1.setEnabled(true);
           lbMinimo.setVisible(true);
           lbExistencia.setVisible(true);
           }
       }
       
       if(txtUnidad.getSelectedIndex()==3){
           txtCantidadMinima.setVisible(false);
           txtExistencia.setVisible(false);
           txtInventario.setSelected(false);
           txtInventario.setEnabled(false);
           selecAviso.setEnabled(false);
           selecAviso.setSelected(false);
           selecAviso1.setEnabled(false);
           selecAviso1.setSelected(false);
           lbMinimo.setVisible(false);
           lbExistencia.setVisible(false);
           txtCantidadMinima.setText("");
           txtExistencia.setText("");
       }
    }//GEN-LAST:event_txtUnidadItemStateChanged

    private void txtCantidadMinimaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadMinimaKeyReleased
                int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Costo();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtCantidadMinimaKeyReleased

    private void txtCantidadMinimaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadMinimaKeyTyped
       if(txtUnidad.getSelectedIndex()==1) event.numberKeyPress(evt);
       else event.numberDecimalKeyPress(evt, txtCantidadMinima);
    }//GEN-LAST:event_txtCantidadMinimaKeyTyped

    private void txtExistenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExistenciaKeyTyped
         if(txtUnidad.getSelectedIndex()==1) event.numberKeyPress(evt);
       else event.numberDecimalKeyPress(evt, txtExistencia);
    }//GEN-LAST:event_txtExistenciaKeyTyped

    private void txtLineaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtLineaItemStateChanged
        if(txtLinea.getItemCount()!=0){
            if(txtLinea.getSelectedIndex()!=0){
        Linea ln = lineaDao.seleccionarDepartamento(txtLinea.getSelectedItem().toString(), 0);
       Departamento dep = departamentoDao.BuscarPorCodigo(ln.getDepartamento());
       txtDepartamento.setText(dep.getNombre());
       txtUtilidad.setText(formato.format(ln.getAumento()+dep.getAumento())+"%");
       txtDescuento.setText(formato.format(ln.getDescuento()+dep.getDescuento())+"%");
       utilidad = ln.getAumento()+dep.getAumento();
       descuento = ln.getDescuento()+dep.getDescuento();
       if(!"".equals(txtPrecioUsuario.getText())){
           precioVenta = Double.parseDouble(txtPrecioUsuario.getText())+(Double.parseDouble(txtPrecioUsuario.getText())*(utilidad/100));
           precioVenta = precioVenta - ((descuento)*precioVenta/100);
           txtPrecio.setText("$"+formateador.format(precioVenta));
       }
            }else{
                  txtDepartamento.setText("");
        }
        }
    }//GEN-LAST:event_txtLineaItemStateChanged

    private void txtCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyReleased
     
        
             int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Costo();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtCostoKeyReleased

    private void selecAvisoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selecAvisoMouseClicked
       if(selecAviso.isSelected()) selecAviso1.setSelected(true);
       else selecAviso1.setSelected(false);
    }//GEN-LAST:event_selecAvisoMouseClicked

    private void selecAviso1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selecAviso1MouseClicked
         if(selecAviso1.isSelected()) selecAviso.setSelected(true);
       else selecAviso.setSelected(false);
    }//GEN-LAST:event_selecAviso1MouseClicked

    private void txtCodigoBarrasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarrasKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Datos();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtCodigoBarrasKeyReleased

    private void txtCodigoBarrasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarrasKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoBarrasKeyTyped

    private void txtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyReleased
           int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Datos();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtDescripcionKeyReleased

    private void txtUnidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadKeyReleased
            int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Datos();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtUnidadKeyReleased

    private void selecAvisoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selecAvisoKeyReleased
           int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Datos();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_selecAvisoKeyReleased

    private void txtLineaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLineaKeyReleased
                 int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Costo();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtLineaKeyReleased

    private void txtExistenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExistenciaKeyReleased
                 int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Costo();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtExistenciaKeyReleased

    private void selecAviso1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selecAviso1KeyReleased
                  int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Costo();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_selecAviso1KeyReleased

    private void txtInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtInventarioMouseClicked
     if(txtInventario.isSelected()){
         txtCantidadMinima.setVisible(true);
         txtExistencia.setVisible(true);
        selecAviso.setEnabled(true);
        selecAviso1.setEnabled(true);
        lbMinimo.setVisible(true);
        lbExistencia.setVisible(true);
     }else{
        txtCantidadMinima.setVisible(false);
        txtExistencia.setVisible(false);  
        selecAviso.setEnabled(false);
        selecAviso1.setEnabled(false);
        selecAviso.setSelected(false);
        selecAviso1.setSelected(false);
        lbMinimo.setVisible(false);
        lbExistencia.setVisible(false);
         txtCantidadMinima.setText("");
           txtExistencia.setText("");
     }
    }//GEN-LAST:event_txtInventarioMouseClicked

    private void txtInventarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInventarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInventarioKeyPressed

    private void txtInventarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInventarioKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInventarioKeyReleased

    private void txtPrecioUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioUsuarioKeyReleased
         if(txtLinea.getSelectedIndex()==0){
            txtPrecio.setText("$"+formateador.format(Double.parseDouble(txtPrecioUsuario.getText())));
        }else{
            precioVenta = Double.parseDouble(txtPrecioUsuario.getText())+(Double.parseDouble(txtPrecioUsuario.getText())*(utilidad/100));
           precioVenta = precioVenta - ((descuento)*precioVenta/100);
           txtPrecio.setText("$"+formateador.format(precioVenta));
        }
         
             
             int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1Costo();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtPrecioUsuarioKeyReleased

    private void txtPrecioUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioUsuarioKeyTyped
        event.numberDecimalKeyPress(evt, txtPrecioUsuario);
    }//GEN-LAST:event_txtPrecioUsuarioKeyTyped

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
            java.util.logging.Logger.getLogger(RegistrarModificarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarModificarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarModificarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarModificarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegistrarModificarProducto dialog = new RegistrarModificarProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnAceptar1;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnEliminar1;
    private javax.swing.JLabel btnLimpiar;
    private javax.swing.JLabel btnLimpiar1;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbExistencia;
    private javax.swing.JLabel lbMinimo;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbNombre10;
    private javax.swing.JLabel lbNombre12;
    private javax.swing.JLabel lbNombre2;
    private javax.swing.JLabel lbNombre3;
    private javax.swing.JLabel lbNombre4;
    private javax.swing.JLabel lbNombre5;
    private javax.swing.JLabel lbNombre6;
    private javax.swing.JLabel lbNombre7;
    private javax.swing.JLabel lbNombre8;
    private javax.swing.JLabel lbNombre9;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelAceptar1;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelEliminar1;
    private javax.swing.JPanel panelLimpiar;
    private javax.swing.JPanel panelLimpiar1;
    private javax.swing.JCheckBox selecAviso;
    private javax.swing.JCheckBox selecAviso1;
    private javax.swing.JTextField txtCantidadMinima;
    private javax.swing.JTextField txtCodigoBarras;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtDepartamento;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtExistencia;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JCheckBox txtInventario;
    private javax.swing.JComboBox<String> txtLinea;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPrecioUsuario;
    private javax.swing.JComboBox<String> txtUnidad;
    private javax.swing.JTextField txtUtilidad;
    // End of variables declaration//GEN-END:variables

private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
   }

}
