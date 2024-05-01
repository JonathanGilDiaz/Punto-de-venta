/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Clientes;
import Modelo.DepartamentoDao;
import Modelo.DetalleOrdenCompra;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.Proveedores;
import Modelo.ProveedoresDao;
import Modelo.TextPrompt;
import Modelo.bitacoraDao;
import Modelo.config;
import Modelo.configuraciones;
import Modelo.ordenCompra;
import Modelo.ordenCompraDao;
import static Vista.vistaVenta.removefirstChar;
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
import com.raven.swing.ScrollBar;
import com.raven.swing.Table2;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.WIDTH;
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
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jonathan Gil
 */
public class crearVerOrdenCompra extends javax.swing.JDialog {

    ProductoDao productoDao = new ProductoDao();
    Producto productoActual = new Producto();
    Eventos event = new Eventos();
    bitacoraDao bitacora = new bitacoraDao();
    EmpleadosDao emple = new EmpleadosDao();
    ProveedoresDao provedorDao = new ProveedoresDao();
    ordenCompraDao ordenDao = new ordenCompraDao();
    DepartamentoDao departamentoDao = new DepartamentoDao();
        configuraciones configuracionesDao = new configuraciones();

    boolean indicador;
    boolean accionCompletada=false;
    int fila = -1;
    ordenCompra ordenActual;
    String fecha;
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");   
    DecimalFormat formato = new DecimalFormat("0.00");
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp;
        int idEmpleado;
    
    public void vaciarEmpleado(int idEmpleado){
        this.idEmpleado=idEmpleado;
    }
  
    public crearVerOrdenCompra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtExistencia.setBackground(new Color(240,240,240));
        txtUnidad.setBackground(new Color(240,240,240));
        txtSubtotal.setBackground(new Color(240,240,240));
        txtDescripcion.setBackground(new Color(240,240,240));
        txtSubtotalFinal.setBackground(new Color(240,240,240));
        txtDescuentoFinal.setBackground(new Color(240,240,240));
        txtIvaFInal.setBackground(new Color(240,240,240));
        txtTotalFinal.setBackground(new Color(240,240,240));
        txtFormaPago.setBackground(new Color(255,255,255));
        txtMetodoPago.setBackground(new Color(255,255,255));
        txtProveedor.setBackground(new Color(255,255,255));
        this.setLocation(200, 50);
         addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    Point currentLocation = getLocation();
                       if (currentLocation.y < 50) 
                          setLocation(200, 50);;
                    
                }
            });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
        JpCompras.getViewport().setBackground(new Color(204, 204, 204));
        JpRecomendacion.getViewport().setBackground(new Color(204, 204, 204));
          DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
           TablaRecomendaciones.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TablaRecomendaciones.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TablaRecomendaciones.getColumnModel().getColumn(2).setCellRenderer(tcr);
        
        TablaCompras.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TablaCompras.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TablaCompras.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TablaCompras.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TablaCompras.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TablaCompras.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TablaCompras.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TablaCompras.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);
        TablaCompras.getColumnModel().getColumn(8).setCellRenderer(tcrDerecha);
        
        
          LocalDate fechaActual = LocalDate.now();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fecha = fechaSQL.toLocalDate().format(formatter);
        listaProducto.setVisible(false);
      panelModificar.setBackground(new Color(179,195,219));
            panelEliminar.setBackground(new Color(179,195,219));
    }
    
    public void vaciarDatos(ordenCompra ordenActual, boolean indicador){
        this.ordenActual=ordenActual;
        this.indicador=indicador;
        listarProveedores(ordenActual.getIdProveedor());
        tmp = (DefaultTableModel) TablaCompras.getModel();
        if(indicador==true){ //nueva orden
            listarProductosRecomendados();
            txtFecha.setText(fechaCorrecta(fecha));
            txtFolio.setText(""+(ordenDao.idCompra()+1));
            this.setTitle("Punto de venta - Nueva orden de compra");
            //indicador=true;
            txtFormaPago.setEnabled(false);
            txtPlazoCredito.setVisible(false);
            txtInteresCredito.setVisible(false);
            jLabel29.setVisible(false);
            lbPlazo.setVisible(false);
            lbInteres.setVisible(false);
                    listaProducto.setVisible(false);
             panelReporte.setBackground(new Color(179,195,219));

                    
        }else{//Ver orden
             this.setTitle("Punto de venta - Visualizar orden de compra");
            txtCodigo.setEnabled(false);
            txtCantidad.setEnabled(false);
            txtCosto.setEnabled(false);
            txtDescuento.setEnabled(false);
            txtPlazoCredito.setEnabled(false);
            txtInteresCredito.setEnabled(false);

            //indicador=false;

            Proveedores prov = provedorDao.BuscarPorCodigo(ordenActual.getIdProveedor());
            txtProveedor.removeAllItems();
               if(prov.getTipoPersona().equals("Persona Física"))
                        txtProveedor.addItem(prov.getNombre()+" "+prov.getApellidoP()+" "+prov.getApellidoM());
                        else txtProveedor.addItem(prov.getNombreComercial());
            txtMetodoPago.setSelectedItem(ordenActual.getMetodoPago());
            if(txtMetodoPago.getSelectedIndex()==2){
                txtPlazoCredito.setVisible(true);
                txtInteresCredito.setVisible(true);
                lbPlazo.setEnabled(true);
               lbInteres.setEnabled(true);
                txtPlazoCredito.setText(ordenActual.getPlazoCredito()+"");
                txtInteresCredito.setText("%"+formato.format(ordenActual.getInteresCredito()));
            }else{
                 txtPlazoCredito.setEnabled(false);
                txtInteresCredito.setEnabled(false);
                lbPlazo.setVisible(false);
                lbInteres.setVisible(false);
            }
            txtFormaPago.removeAllItems();
            txtFormaPago.addItem(ordenActual.getFormaPago());
            txtSubtotalFinal.setText("$"+formateador.format(ordenActual.getSubtotal()));
            txtDescuentoFinal.setText("$"+formateador.format(ordenActual.getDescuento()));
            txtIvaFInal.setText("$"+formateador.format(ordenActual.getIva()));
            txtTotalFinal.setText("$"+formateador.format(ordenActual.getTotal()));
            txtFolio.setText(ordenActual.getId()+"");
            panelGuardar.setBackground(new Color(179,195,219));
            txtFecha.setText(fechaCorrecta(ordenActual.getFecha()));
            List<DetalleOrdenCompra> lsDetalles = ordenDao.regresarDetalles(ordenActual.getId());
            tmp = (DefaultTableModel) TablaCompras.getModel();
  
                             Object[] o = new Object[9];
            for(int i = 0; i<lsDetalles.size();i++){
             o[0] = lsDetalles.get(i).getCodigo();
             o[1] = formato.format(lsDetalles.get(i).getCantidad());
             o[2] = lsDetalles.get(i).getUnidad();
             o[3] = lsDetalles.get(i).getDescripcion();
             o[4] = "$"+formateador.format(lsDetalles.get(i).getPrecioUnitario());
             double cantidad = lsDetalles.get(i).getCantidad();
             double costo = lsDetalles.get(i).getPrecioUnitario();
             double descuento = lsDetalles.get(i).getDescuento();
             o[5] = "$"+formateador.format(cantidad*costo);
             o[6] = "$"+formateador.format(lsDetalles.get(i).getDescuento());
             o[7] = "$"+formateador.format(((costo*cantidad)-descuento)*.16);
             o[8] = "$"+formateador.format((((costo*cantidad)-descuento))+((costo*cantidad)-descuento)*.16);
             tmp.addRow(o);
            }
              TablaCompras.setModel(tmp);
        }
    }
    
    public String fechaCorrecta(String fecha){
         Date diaActual = StringADate(fecha);
            DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
            String fechaUsar;
            Date fechaNueva;
            Calendar c = Calendar.getInstance();
            c.setTime(diaActual);
            fechaUsar = cam.format(diaActual);
            return fechaUsar;
    }
    
    public boolean verificarAgregado(String codigo){
        boolean agregado=false;
        for(int i=0; i<TablaCompras.getRowCount();i++){
            if(TablaCompras.getValueAt(i, 0).toString().equals(codigo)){
                agregado=true;
            }
        }
        return agregado;
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
    
    public void listarProductosRecomendados(){
        List<Producto> lsProducto = productoDao.listarProductosInventarioBajo();
          modelo = (DefaultTableModel) TablaRecomendaciones.getModel();
            Object[] ob = new Object[3];
            for (int i = 0; i < lsProducto.size(); i++) {
                if(lsProducto.get(i).getInventario()==1){
                if(verificarAgregado(lsProducto.get(i).getCodigo())==false){
                  ob[0] = lsProducto.get(i).getCodigo();
                ob[1] = lsProducto.get(i).getDescripcion();
                ob[2] = lsProducto.get(i).getExistencia();

            modelo.addRow(ob);  
                }
            }
        }
        TablaRecomendaciones.setModel(modelo);
    }
    
      public void LimpiarTablaRecomendaciones() {
        TablaRecomendaciones.setModel(modelo);
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
          
       public void listarProveedores(int idProveedor) {
        List<Proveedores> lsProveedor = provedorDao.listarProveedores(); //Se obtienen los empleados de la base de datos (para generar nota
        txtProveedor.removeAllItems();//Se borran los datos del ComboBox para evitar que se sobreencimen
        txtProveedor.addItem("Proveedores *");
        for (int i = 0; i < lsProveedor.size(); i++) {//vaciamos los datos
            if(lsProveedor.get(i).getEstatus()==1 || lsProveedor.get(i).getId()==idProveedor)
                if(lsProveedor.get(i).getTipoPersona().equals("Persona Física"))
                txtProveedor.addItem(lsProveedor.get(i).getNombre()+" "+lsProveedor.get(i).getApellidoP()+" "+lsProveedor.get(i).getApellidoM());
                else txtProveedor.addItem(lsProveedor.get(i).getNombreComercial());
        }
    } 
       
    public void limpiarDatos(boolean delCodigo){
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtExistencia.setText("");
        txtCosto.setText("");
        txtSubtotal.setText("");
        txtDescuento.setText("");
                    txtUnidad.setText("");
    }
    
    public void eventoEnter(){
        if(indicador==true){
        if(!"".equals(txtCodigo.getText()) && !"".equals(txtSubtotal.getText()) && !txtSubtotal.getText().equals("$0.00")){
            Object[] o = new Object[9];
             o[0] = txtCodigo.getText();
             o[1] = txtCantidad.getText();
             o[2] = txtUnidad.getText();
             o[3] = txtDescripcion.getText();
             o[4] = "$"+formateador.format(Double.parseDouble(txtCosto.getText()));
             double cantidad = Double.parseDouble(txtCantidad.getText());
             double costo = Double.parseDouble(txtCosto.getText());
             double descuento = 0.00;
             
             o[5] = "$"+formateador.format(cantidad*costo);
             if(!"".equals(txtDescuento.getText())){
             descuento = Double.parseDouble(txtDescuento.getText());
             o[6] = "$"+formateador.format(descuento);
             }
             else o[6] = "$0.00";
             o[7] = "$"+formateador.format(((costo*cantidad)-descuento)*.16);
             o[8] = "$"+formateador.format((((costo*cantidad)-descuento))+((costo*cantidad)-descuento)*.16);
             
             tmp.addRow(o);
             TablaCompras.setModel(tmp);
             calcularTotal();
            limpiarDatos(false);
            LimpiarTablaRecomendaciones();
            listarProductosRecomendados();
               fila=-1;
            panelModificar.setBackground(new Color(179,195,219));
            panelEliminar.setBackground(new Color(179,195,219));
        }
        }
    }
    
    public void calcularTotal(){
        double subtotal = 0.00;
        double descuento = 0.00;
        double totalF = 0.00;
        for(int i =0; i< TablaCompras.getRowCount();i++){
            try {          
                Number unitarioT = formateador.parse(removefirstChar(TablaCompras.getValueAt(i, 4).toString()));
                subtotal = subtotal + (Double.parseDouble(TablaCompras.getValueAt(i, 1).toString())*unitarioT.doubleValue());
                Number descuentoSuma = formateador.parse(removefirstChar(TablaCompras.getValueAt(i, 6).toString()));
                descuento = descuento + descuentoSuma.doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(crearVerOrdenCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        txtSubtotalFinal.setText("$"+formateador.format(subtotal));

        txtDescuentoFinal.setText("$"+formateador.format(descuento));
        double cantidad = subtotal-descuento;
        double ivaC = cantidad*0.16;
        txtIvaFInal.setText("$"+formateador.format(ivaC));
        txtTotalFinal.setText("$"+formateador.format(cantidad+ivaC));
       
    }
    
     public void registrarDetalle(int idEmpleado) throws ParseException {
        int id = ordenDao.idCompra();
        for (int i = 0; i < TablaCompras.getRowCount(); i++) {
            
            DetalleOrdenCompra detalle = new DetalleOrdenCompra();
            detalle.setIdCompra(id);
            detalle.setCodigo(TablaCompras.getValueAt(i, 0).toString());
            detalle.setCantidad(Double.parseDouble(TablaCompras.getValueAt(i, 1).toString()));
            detalle.setUnidad(TablaCompras.getValueAt(i, 2).toString());
            detalle.setDescripcion(TablaCompras.getValueAt(i, 3).toString());
            Number unitario = formateador.parse(removefirstChar(TablaCompras.getValueAt(i, 4).toString()));
            detalle.setPrecioUnitario(unitario.doubleValue());
            Number descuento = formateador.parse(removefirstChar(TablaCompras.getValueAt(i, 6).toString()));
            detalle.setDescuento(descuento.doubleValue());
             Number importe = formateador.parse(removefirstChar(TablaCompras.getValueAt(i, 8).toString()));
            detalle.setImporte(importe.doubleValue());
            ordenDao.registrarDetalle(detalle);
            Producto p = productoDao.BuscarPorCodigo(TablaCompras.getValueAt(i, 0).toString());
            String textoBitacora = "Se compro articulos de "+p.getDescripcion();
            p.setExistencia(detalle.getCantidad());
            if(p.getPrecioCompra()!=unitario.doubleValue()){   
                p.setPrecioCompra(unitario.doubleValue());
                if(p.getInventario()==1)  productoDao.IntroducirProductos(p);
                textoBitacora += " y se modifico su costo a $"+formateador.format(unitario.doubleValue());
            }else{
                p.setPrecioCompra(unitario.doubleValue());
                if(p.getInventario()==1) productoDao.IntroducirProductos(p);
            }
            bitacora.registrarRegistro(textoBitacora, idEmpleado, fecha);
            
        }
    }

         public void eventoF2(){
           if(indicador==false){
           try {
            int id = Integer.parseInt(txtFolio.getText());
           File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\PDF\\OrdenComora" + txtFolio.getText() + ".pdf"); 
                pdfoRDEN(id);
                Desktop.getDesktop().open(file);
        } catch (IOException ex) {
        }   catch (ParseException ex) {
                Logger.getLogger(vistaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
     }
    }

    
    public void eventoF1(){
        if(indicador==true){
            if(txtMetodoPago.getSelectedIndex()!=0 && txtProveedor.getSelectedIndex()!=0 && !"".equals(txtTotalFinal.getText()) && !txtTotalFinal.getText().equals("$0.00")){
            try {
                    Empleados empleado = emple.seleccionarEmpleado("", idEmpleado);
                     ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                    cc.vaciarEmpleado(empleado.getId());
                    cc.setVisible(true);
                    if(cc.contraseñaAceptada==true){
                        ordenCompra ordenNueva = new ordenCompra();
                        ordenNueva.setFecha(fecha);
                             ordenNueva.setMetodoPago(txtMetodoPago.getSelectedItem().toString());
                             ordenNueva.setFormaPago(txtFormaPago.getSelectedItem().toString());
                             List<Proveedores> lsProveedor = provedorDao.listarProveedores();
                                ordenNueva.setIdProveedor(lsProveedor.get(txtProveedor.getSelectedIndex()-1).getId());
                                ordenNueva.setIdRecibe(empleado.getId());
                               if(txtMetodoPago.getSelectedIndex()==2){
                                   if(!"".equals(txtPlazoCredito.getText()))
                                   ordenNueva.setPlazoCredito(Integer.parseInt(txtPlazoCredito.getText()));
                                     if(!"".equals(txtInteresCredito.getText()))
                                   ordenNueva.setInteresCredito(Double.parseDouble(txtInteresCredito.getText()));
                               }
                               Number subtotalF = formateador.parse(removefirstChar(txtSubtotalFinal.getText()));
                               ordenNueva.setSubtotal(subtotalF.doubleValue());
                               Number descuentosF = formateador.parse(removefirstChar(txtDescuentoFinal.getText()));
                               ordenNueva.setDescuento(descuentosF.doubleValue());
                               Number ivaF = formateador.parse(removefirstChar(txtIvaFInal.getText()));
                               ordenNueva.setIva(ivaF.doubleValue());
                               Number totalF = formateador.parse(removefirstChar(txtTotalFinal.getText()));
                               ordenNueva.setTotal(totalF.doubleValue());
                                ordenDao.registrarOrden(ordenNueva);
                                bitacora.registrarRegistro("Se registró una nueva orden de compra por "+txtTotalFinal.getText(), empleado.getId(), fecha);
                                registrarDetalle(empleado.getId());
                                ordenCompra ordenRegistrada = ordenDao.buscarPorId(Integer.parseInt(txtFolio.getText()));
                                vaciarDatos(ordenRegistrada,false);
                                accionCompletada=true;
                                dispose();
                    }
            } catch (ParseException ex) {
                Logger.getLogger(crearVerOrdenCompra.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        }else{
            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Rellene todos los campos");
                                 panel.showNotification();
            }
    }
    }
    /*
    public void  eventoF3(){
         RegistrarModificarProducto cc = new RegistrarModificarProducto(new javax.swing.JFrame(), true);
        Producto cp = new Producto();
        cc.validandoDatos(cp, true);
        cc.setVisible(true);
        if(cc.accionCompletada==true){        
            LimpiarTablaRecomendaciones();
            listarProductosRecomendados();
             Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Producto creado exitosamente");
                                 panel.showNotification();
        }
    }*/


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        panelReporte = new javax.swing.JPanel();
        btnReporte = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        JpCompras = new javax.swing.JScrollPane();
        TablaCompras = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        JpRecomendacion = new javax.swing.JScrollPane();
        TablaRecomendaciones = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JLabel();
        txtFolio = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        controladorLista = new javax.swing.JScrollPane();
        listaProducto = new javax.swing.JList<>();
        jLabel28 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtExistencia = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        txtUnidad = new javax.swing.JTextField();
        txtCosto = new javax.swing.JTextField();
        txtSubtotal = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        panelModificar = new javax.swing.JPanel();
        btnModificar = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();
        panelAceptar = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JLabel();
        lbNombre = new javax.swing.JLabel();
        lbNombre4 = new javax.swing.JLabel();
        lbNombre5 = new javax.swing.JLabel();
        lbNombre1 = new javax.swing.JLabel();
        lbNombre6 = new javax.swing.JLabel();
        lbNombre2 = new javax.swing.JLabel();
        lbNombre7 = new javax.swing.JLabel();
        lbExistencia = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtMetodoPago = new javax.swing.JComboBox<>();
        txtProveedor = new javax.swing.JComboBox<>();
        txtPlazoCredito = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtInteresCredito = new javax.swing.JTextField();
        txtFormaPago = new javax.swing.JComboBox<>();
        lbPlazo = new javax.swing.JLabel();
        lbInteres = new javax.swing.JLabel();
        lbNombre14 = new javax.swing.JLabel();
        lbNombre15 = new javax.swing.JLabel();
        lbNombre16 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtTotalFinal = new javax.swing.JTextField();
        txtIvaFInal = new javax.swing.JTextField();
        txtDescuentoFinal = new javax.swing.JTextField();
        txtSubtotalFinal = new javax.swing.JTextField();
        lbNombre3 = new javax.swing.JLabel();
        lbNombre9 = new javax.swing.JLabel();
        lbNombre10 = new javax.swing.JLabel();
        lbNombre11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ORDEN DE COMPRA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(634, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 1040, 50));

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
        btnGuardar.setToolTipText("F1 - Guardar orden");
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

        jPanel1.add(panelGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 50, 50));

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
        btnReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jPanel1.add(panelReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 52, 50, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaCompras = new Table2();
        TablaCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cant", "Unidad", "Descripcion", "P/U", "Importe", "Desct", "IVA", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCompras.setToolTipText("");
        TablaCompras.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TablaCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaComprasMouseClicked(evt);
            }
        });
        TablaCompras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaComprasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaComprasKeyReleased(evt);
            }
        });
        JpCompras.setViewportView(TablaCompras);
        if (TablaCompras.getColumnModel().getColumnCount() > 0) {
            TablaCompras.getColumnModel().getColumn(0).setPreferredWidth(50);
            TablaCompras.getColumnModel().getColumn(1).setPreferredWidth(5);
            TablaCompras.getColumnModel().getColumn(2).setPreferredWidth(25);
            TablaCompras.getColumnModel().getColumn(3).setPreferredWidth(120);
            TablaCompras.getColumnModel().getColumn(4).setPreferredWidth(30);
            TablaCompras.getColumnModel().getColumn(5).setPreferredWidth(40);
            TablaCompras.getColumnModel().getColumn(6).setPreferredWidth(30);
            TablaCompras.getColumnModel().getColumn(7).setPreferredWidth(30);
            TablaCompras.getColumnModel().getColumn(8).setPreferredWidth(30);
        }

        jPanel2.add(JpCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 50, 765, 320));

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Resumen");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 240, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 110, 780, 380));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaRecomendaciones = new Table2();
        TablaRecomendaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Piezas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaRecomendaciones.setToolTipText("");
        TablaRecomendaciones.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TablaRecomendaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaRecomendacionesMouseClicked(evt);
            }
        });
        TablaRecomendaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaRecomendacionesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaRecomendacionesKeyReleased(evt);
            }
        });
        JpRecomendacion.setViewportView(TablaRecomendaciones);
        if (TablaRecomendaciones.getColumnModel().getColumnCount() > 0) {
            TablaRecomendaciones.getColumnModel().getColumn(0).setPreferredWidth(45);
            TablaRecomendaciones.getColumnModel().getColumn(1).setPreferredWidth(160);
            TablaRecomendaciones.getColumnModel().getColumn(2).setPreferredWidth(35);
        }
        TablaRecomendaciones.getAccessibleContext().setAccessibleName("");

        jPanel6.add(JpRecomendacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 20, 340, 105));

        jLabel27.setBackground(new java.awt.Color(0, 0, 204));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Productos recomendados a agregar");
        jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 5, 240, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 495, 350, 130));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel5.setText("FECHA:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 100, 20));

        txtFecha.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        txtFecha.setText("-");
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 130, 20));

        txtFolio.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        txtFolio.setForeground(new java.awt.Color(204, 51, 0));
        txtFolio.setText("-");
        jPanel1.add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 70, 100, 20));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 51, 0));
        jLabel6.setText("FOLIO");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 70, -1, 20));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaProductoMouseClicked(evt);
            }
        });
        controladorLista.setViewportView(listaProducto);

        jPanel7.add(controladorLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 70, 140, 10));

        jLabel28.setBackground(new java.awt.Color(0, 0, 204));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Productos a agregar");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 220, -1));

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCodigo.setToolTipText("Introduzca el código o descripción del producto");
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });
        jPanel7.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 50, 140, -1));

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
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
        jPanel7.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 80, 140, -1));

        txtExistencia.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtExistencia.setToolTipText("Cantidad en existencia en inventario");
        txtExistencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtExistencia.setEnabled(false);
        txtExistencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExistenciaKeyReleased(evt);
            }
        });
        jPanel7.add(txtExistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 140, 140, -1));

        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCantidad.setToolTipText("Cantidad a comprar");
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel7.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 170, 140, -1));

        txtDescuento.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDescuento.setToolTipText("Descuentos");
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });
        jPanel7.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 260, 140, -1));

        txtUnidad.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtUnidad.setToolTipText("Unidad en la que se vende");
        txtUnidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUnidad.setEnabled(false);
        txtUnidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnidadKeyReleased(evt);
            }
        });
        jPanel7.add(txtUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 110, 140, -1));

        txtCosto.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCosto.setToolTipText("Costo unitario del producto");
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });
        jPanel7.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 200, 140, -1));

        txtSubtotal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setToolTipText("SubTotal de compra del producto");
        txtSubtotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubtotal.setEnabled(false);
        txtSubtotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSubtotalKeyReleased(evt);
            }
        });
        jPanel7.add(txtSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 230, 140, -1));

        jLabel23.setBackground(new java.awt.Color(0, 0, 204));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("¿Está seguro de agregar el producto? ");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

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
        btnModificar.setToolTipText("F1 - Limpiar todos los campos");
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jPanel7.add(panelModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, -1, -1));

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

        jPanel7.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, -1, -1));

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
        btnAceptar.setToolTipText("ENTER - Guardar empleado");
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

        jPanel7.add(panelAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, -1));

        lbNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre.setText("Código");
        jPanel7.add(lbNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 53, 70, -1));

        lbNombre4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre4.setText("Descripción");
        jPanel7.add(lbNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 83, 70, -1));

        lbNombre5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre5.setText("Cantidad");
        jPanel7.add(lbNombre5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 173, 70, -1));

        lbNombre1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre1.setText("Unidad");
        jPanel7.add(lbNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 113, 70, -1));

        lbNombre6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre6.setText("Descuento");
        jPanel7.add(lbNombre6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 263, 70, -1));

        lbNombre2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre2.setText("Costo");
        jPanel7.add(lbNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 203, 70, -1));

        lbNombre7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre7.setText("Subtotal");
        jPanel7.add(lbNombre7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 233, 70, -1));

        lbExistencia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbExistencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbExistencia.setText("Existencia");
        jPanel7.add(lbExistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 143, 70, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 230, 380));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMetodoPago.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Método de pago *", "PUE Pago en una sola exhibición", "PPD Pago en parcialidades o diferido" }));
        txtMetodoPago.setToolTipText("Elija el método de pago");
        txtMetodoPago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtMetodoPagoItemStateChanged(evt);
            }
        });
        txtMetodoPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMetodoPagoKeyReleased(evt);
            }
        });
        jPanel8.add(txtMetodoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 30, 180, -1));

        txtProveedor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Proveedor", "Item 2", "Item 3", "Item 4" }));
        txtProveedor.setToolTipText("Elija el proveedor");
        txtProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProveedorKeyReleased(evt);
            }
        });
        jPanel8.add(txtProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 90, 180, -1));

        txtPlazoCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtPlazoCredito.setToolTipText("Plazo del crédito (días)");
        txtPlazoCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPlazoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPlazoCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlazoCreditoKeyTyped(evt);
            }
        });
        jPanel8.add(txtPlazoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 30, 110, -1));

        jLabel29.setBackground(new java.awt.Color(0, 0, 204));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Datos a Crédito");
        jPanel8.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 140, -1));

        txtInteresCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteresCredito.setToolTipText("Porcentaje de interés");
        txtInteresCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtInteresCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteresCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteresCreditoKeyTyped(evt);
            }
        });
        jPanel8.add(txtInteresCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 60, 110, -1));

        txtFormaPago.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtFormaPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Forma de pago", "Item 2", "Item 3", "Item 4" }));
        txtFormaPago.setToolTipText("elija el método de pago");
        txtFormaPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFormaPagoKeyReleased(evt);
            }
        });
        jPanel8.add(txtFormaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 60, 180, -1));

        lbPlazo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbPlazo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbPlazo.setText("Plazo");
        jPanel8.add(lbPlazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 33, 50, -1));

        lbInteres.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres.setText("Intéres");
        jPanel8.add(lbInteres, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 63, 50, -1));

        lbNombre14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre14.setText("Forma pago");
        jPanel8.add(lbNombre14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 63, 70, -1));

        lbNombre15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre15.setText("Método pago");
        jPanel8.add(lbNombre15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 33, 80, -1));

        lbNombre16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre16.setText("Proveedor");
        jPanel8.add(lbNombre16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 93, 70, -1));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 495, 430, 130));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTotalFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTotalFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalFinal.setToolTipText("Total de la compra");
        txtTotalFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalFinal.setEnabled(false);
        txtTotalFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalFinalKeyReleased(evt);
            }
        });
        jPanel9.add(txtTotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 96, 108, -1));

        txtIvaFInal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtIvaFInal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIvaFInal.setToolTipText("IVA");
        txtIvaFInal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIvaFInal.setEnabled(false);
        txtIvaFInal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIvaFInalKeyReleased(evt);
            }
        });
        jPanel9.add(txtIvaFInal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 68, 108, -1));

        txtDescuentoFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDescuentoFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuentoFinal.setToolTipText("Descuentos acumulados");
        txtDescuentoFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescuentoFinal.setEnabled(false);
        txtDescuentoFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoFinalActionPerformed(evt);
            }
        });
        txtDescuentoFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoFinalKeyReleased(evt);
            }
        });
        jPanel9.add(txtDescuentoFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 108, -1));

        txtSubtotalFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSubtotalFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotalFinal.setToolTipText("SubTotal de la compra");
        txtSubtotalFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubtotalFinal.setEnabled(false);
        txtSubtotalFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSubtotalFinalKeyReleased(evt);
            }
        });
        jPanel9.add(txtSubtotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 12, 108, -1));

        lbNombre3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre3.setText("Subtotal");
        jPanel9.add(lbNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 15, 60, -1));

        lbNombre9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre9.setText("Descuento");
        jPanel9.add(lbNombre9, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 45, 60, -1));

        lbNombre10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre10.setText("IVA");
        jPanel9.add(lbNombre10, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 75, 60, -1));

        lbNombre11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre11.setText("Total");
        jPanel9.add(lbNombre11, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 100, 60, -1));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 495, 215, 130));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        eventoEnter();
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
               if(indicador==true)
        panelAceptar.setBackground(new Color(179,195,219));
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

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        if(indicador==true){
            if(TablaCompras.getSelectedRow()!=(-1)){
            try {
                tmp = (DefaultTableModel) TablaCompras.getModel();
                txtCodigo.setText(TablaCompras.getValueAt(TablaCompras.getSelectedRow(), 0).toString());
                txtCantidad.setText(TablaCompras.getValueAt(TablaCompras.getSelectedRow(), 1).toString());
                txtUnidad.setText(TablaCompras.getValueAt(TablaCompras.getSelectedRow(), 2).toString());
                txtDescripcion.setText(TablaCompras.getValueAt(TablaCompras.getSelectedRow(), 3).toString());
                Producto p = productoDao.BuscarPorCodigo(txtCodigo.getText());
                productoActual.setTipoVenta(p.getTipoVenta());
                if(p.getInventario()==0) {
                    txtExistencia.setVisible(false);
                    lbExistencia.setVisible(false);
                }else {
                    txtExistencia.setVisible(true);
                    lbExistencia.setVisible(true);
                }
                txtExistencia.setText(formato.format(p.getExistencia()));
                Number unitario = formateador.parse(removefirstChar(TablaCompras.getValueAt(TablaCompras.getSelectedRow(), 4).toString()));
                txtCosto.setText(unitario.doubleValue()+"");
                txtSubtotal.setText("$"+formateador.format(unitario.doubleValue()*Double.parseDouble(TablaCompras.getValueAt(TablaCompras.getSelectedRow(), 1).toString())));
                 Number descuentoT = formateador.parse(removefirstChar(TablaCompras.getValueAt(TablaCompras.getSelectedRow(), 6).toString()));
                txtDescuento.setText(descuentoT.doubleValue()+"");
                tmp.removeRow(TablaCompras.getSelectedRow());
                TablaCompras.setModel(tmp);
                  LimpiarTablaRecomendaciones();
            listarProductosRecomendados();
                calcularTotal();
                   fila=-1;
            panelModificar.setBackground(new Color(179,195,219));
            panelEliminar.setBackground(new Color(179,195,219));
            } catch (ParseException ex) {
                Logger.getLogger(crearVerOrdenCompra.class.getName()).log(Level.SEVERE, null, ex);
            }

            } else {
                  Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Seleccione una fila para modificar");
                                 panel.showNotification();
        }
    }
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseEntered
        panelModificar.setBackground(new Color(179,195,219));
          if(fila!=-1 && indicador==true){
              panelModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
            panelModificar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
    }//GEN-LAST:event_btnModificarMouseEntered

    private void btnModificarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseExited
          if(fila!=-1 && indicador==true){
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
    }//GEN-LAST:event_btnModificarKeyPressed

    private void panelModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelModificarMouseClicked

    private void panelModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelModificarMouseEntered

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        if(indicador==true){
          if(TablaCompras.getSelectedRow()!=(-1)){
                tmp = (DefaultTableModel) TablaCompras.getModel();
                tmp.removeRow(TablaCompras.getSelectedRow());
                  LimpiarTablaRecomendaciones();
            listarProductosRecomendados();
                calcularTotal();
                   fila=-1;
            panelModificar.setBackground(new Color(179,195,219));
            panelEliminar.setBackground(new Color(179,195,219));
            } else {
                  Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Seleccione una fila para eliminar");
                                 panel.showNotification();
            }
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
              panelEliminar.setBackground(new Color(179,195,219));
          if(fila!=-1 && indicador==true){
              panelEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
            panelEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        if(fila!=-1 && indicador==true){
            panelEliminar.setBackground(new Color(255,255,255));
           panelEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }else{
             panelEliminar.setBackground(new Color(179,195,219));
             panelEliminar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
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

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        eventoF1();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
             panelGuardar.setBackground(new Color(179,195,219));
          if(indicador==true){
              panelGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
            panelGuardar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
          if(indicador==true){
            panelGuardar.setBackground(new Color(255,255,255));
           panelGuardar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }else{
             panelGuardar.setBackground(new Color(179,195,219));
             panelGuardar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_F1:
            eventoF1();
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

    private void btnReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseClicked
    eventoF2();
    }//GEN-LAST:event_btnReporteMouseClicked

    private void btnReporteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseEntered
           panelReporte.setBackground(new Color(179,195,219));
          if(indicador==false)  {
              panelReporte.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
            panelReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

    }//GEN-LAST:event_btnReporteMouseEntered

    private void btnReporteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReporteMouseExited
          if(indicador==false)  {
            panelReporte.setBackground(new Color(255,255,255));
           panelReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }else{
             panelReporte.setBackground(new Color(179,195,219));
             panelReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }//GEN-LAST:event_btnReporteMouseExited

    private void btnReporteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnReporteKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
            case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;
        }
    }//GEN-LAST:event_btnReporteKeyPressed

    private void panelReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReporteMouseClicked

    }//GEN-LAST:event_panelReporteMouseClicked

    private void panelReporteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReporteMouseEntered
         panelReporte.setBackground(new Color(179,195,219));
          if(fila!=-1){
              panelReporte.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
            panelReporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
    }//GEN-LAST:event_panelReporteMouseEntered

    private void TablaComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaComprasMouseClicked
        if (evt.getClickCount() == 2) {
            fila=1;
            panelModificar.setBackground(new Color(255,255,255));
            panelEliminar.setBackground(new Color(255,255,255));
        }
    }//GEN-LAST:event_TablaComprasMouseClicked

    private void TablaComprasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaComprasKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_F1:
            eventoF1();
            break;

             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_TablaComprasKeyPressed

    private void TablaRecomendacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaRecomendacionesMouseClicked
       if (evt.getClickCount() == 2) {
            Producto p=productoDao.existeProducto(TablaRecomendaciones.getValueAt(TablaRecomendaciones.getSelectedRow(), 0).toString());
            if(p.getInventario()==0){
                txtExistencia.setVisible(false);
                lbExistencia.setVisible(false);
            }else {
                txtExistencia.setVisible(true);
                 lbExistencia.setVisible(true);
            }
            
             txtCodigo.setText(p.getCodigo());
            txtDescripcion.setText(p.getDescripcion());
            txtExistencia.setText(p.getExistencia()+"");
            txtCantidad.setText("1");
            txtSubtotal.setText("$"+formateador.format(p.getPrecioCompra()));
            txtCosto.setText(formato.format(p.getPrecioCompra()));
            txtDescuento.setText("");
            txtUnidad.setText(p.getTipoVenta());
            productoActual.setTipoVenta(p.getTipoVenta());
        }
    }//GEN-LAST:event_TablaRecomendacionesMouseClicked

    private void TablaRecomendacionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaRecomendacionesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaRecomendacionesKeyPressed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
   int codigo=evt.getKeyCode();
        switch(codigo){
       

            case KeyEvent.VK_F1:
            eventoF1();
            break;

             case KeyEvent.VK_F2:
            eventoF2();
            break;
            
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }

        Producto p=productoDao.existeProducto(txtCodigo.getText());
        
        if(p.getId()!=0 && !p.getTipoVenta().equals("Servicio")){
            txtUnidad.setText(p.getTipoVenta());
            txtDescripcion.setText(p.getDescripcion());
            txtExistencia.setText(p.getExistencia()+"");
            txtSubtotal.setText("$"+formateador.format(p.getPrecioCompra()));
            txtCosto.setText(formato.format(p.getPrecioCompra()));
            txtCantidad.setText("1");
            if(p.getInventario()==1){
                txtExistencia.setVisible(true);
                txtExistencia.setText(formato.format(p.getExistencia()));
                lbExistencia.setVisible(true);
            }else{
            txtExistencia.setVisible(false);
            txtExistencia.setText("");
            lbExistencia.setVisible(false);
            }
            listaProducto.setVisible(false);
            controladorLista.setVisible(false);
            productoActual.setTipoVenta(p.getTipoVenta());
        }else{
            txtDescripcion.setText("");
            txtExistencia.setText("");
            txtSubtotal.setText("");
            txtCosto.setText("");
            txtCantidad.setText("");
            txtUnidad.setText("");
            txtExistencia.setText("");
            txtExistencia.setVisible(true);
            lbExistencia.setVisible(true);
            List<Producto> lsProducto = productoDao.buscarLetraCodigoDescripcion(txtCodigo.getText());
            if(lsProducto.isEmpty()){
              listaProducto.setVisible(false);
              controladorLista.setVisible(false);
            }else{
                 listaProducto.setVisible(true);
              controladorLista.setVisible(true);
               controladorLista.setBounds(controladorLista.getX(), controladorLista.getY(), controladorLista.getWidth(), 85);
               listaProducto.setBounds(listaProducto.getX(), listaProducto.getY(), listaProducto.getWidth(), 85);
               DefaultListModel  listModel = new DefaultListModel();
                for (int i = 0; i < lsProducto.size(); i++) {
                    if(lsProducto.get(i).getEstado()==1 && !lsProducto.get(i).getTipoVenta().equals("Servicio")){
                        listModel.addElement(lsProducto.get(i).getDescripcion());
                    }
                }
               listaProducto.setModel(listModel);
            }
        }
        if(p.getId()!=0 && p.getTipoVenta().equals("Servicio")){
               Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "El objeto es un servicio");
                                 panel.showNotification();
        }
        if(txtCodigo.getText().equals("")){
             listaProducto.setVisible(false);
              controladorLista.setVisible(false);
        }
       
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtDescripcionKeyReleased

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtExistenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExistenciaKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtExistenciaKeyReleased

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
          if("".equals(txtCantidad.getText()) || txtCantidad.getText().endsWith(".") || "".equals(txtCosto.getText()) || txtCosto.getText().endsWith(".")){
            txtSubtotal.setText("");
        }else{
            txtSubtotal.setText("$"+formateador.format((Double.parseDouble(txtCantidad.getText())*Double.parseDouble(txtCosto.getText()))));
        }
          
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
    }//GEN-LAST:event_txtCantidadKeyReleased

    private void txtDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyReleased
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
    }//GEN-LAST:event_txtDescuentoKeyReleased

    private void txtUnidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidadKeyReleased

    private void txtCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyReleased
   int codigo=evt.getKeyCode();
        switch(codigo){
            case KeyEvent.VK_ENTER:
            eventoEnter();
            break;

            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
        
        if("".equals(txtCantidad.getText()) || txtCantidad.getText().endsWith(".") || "".equals(txtCosto.getText()) || txtCosto.getText().endsWith(".")){
            txtSubtotal.setText("$0.00");
        }else{
            txtSubtotal.setText("$"+formateador.format((Double.parseDouble(txtCantidad.getText())*Double.parseDouble(txtCosto.getText()))));
        }
    }//GEN-LAST:event_txtCostoKeyReleased

    private void txtSubtotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubtotalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubtotalKeyReleased

    private void txtPlazoCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoCreditoKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_F1:
            eventoF1();
            break;

             case KeyEvent.VK_F2:
            eventoF2();
            break;
            
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtPlazoCreditoKeyReleased

    private void txtTotalFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalFinalKeyReleased

    private void txtIvaFInalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIvaFInalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIvaFInalKeyReleased

    private void txtDescuentoFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoFinalKeyReleased

    private void txtSubtotalFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubtotalFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubtotalFinalKeyReleased

    private void txtInteresCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteresCreditoKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtInteresCreditoKeyReleased

    private void txtMetodoPagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtMetodoPagoItemStateChanged
      if(txtMetodoPago.getSelectedIndex()==0) {
          txtFormaPago.setEnabled(false);
          txtFormaPago.removeAllItems();
          txtFormaPago.addItem("Forma de pago *");
             txtPlazoCredito.setVisible(false);
          txtInteresCredito.setVisible(false);
           txtPlazoCredito.setText("");
          txtInteresCredito.setText("");
            lbPlazo.setVisible(false);
          lbInteres.setVisible(false);
          jLabel29.setVisible(false);
      }
    if(txtMetodoPago.getSelectedIndex()==1) {
          txtFormaPago.setEnabled(true);
          txtFormaPago.removeAllItems();
          txtFormaPago.addItem("01 Efectivo");
          txtFormaPago.addItem("02 Cheque nominativo");
          txtFormaPago.addItem("03 Transferencia electrónica de fondos");
          txtFormaPago.addItem("04 Tarjeta de crédito");
          txtFormaPago.addItem("28 Tarjeta de débito");
           txtPlazoCredito.setVisible(false);
          txtInteresCredito.setVisible(false);
           txtPlazoCredito.setText("");
          txtInteresCredito.setText("");
            lbPlazo.setVisible(false);
          lbInteres.setVisible(false);
           jLabel29.setVisible(false);
      }
     if(txtMetodoPago.getSelectedIndex()==2) {
          txtFormaPago.setEnabled(true);
          txtFormaPago.removeAllItems();
          txtFormaPago.addItem("99 Por definir"); //a credito
          txtPlazoCredito.setVisible(true);
          txtInteresCredito.setVisible(true);
          lbPlazo.setVisible(true);
          lbInteres.setVisible(true);
          jLabel29.setVisible(true);
      }
    }//GEN-LAST:event_txtMetodoPagoItemStateChanged

    private void listaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaProductoMouseClicked
     if (evt.getClickCount() == 2) {
         Producto deLista = productoDao.existeProductoDescripcion(listaProducto.getSelectedValue());
            txtCodigo.setText(deLista.getCodigo());
             txtDescripcion.setText(deLista.getDescripcion());
            txtExistencia.setText(deLista.getExistencia()+"");
           txtUnidad.setText(deLista.getTipoVenta());
            lbExistencia.setVisible(true);
            txtSubtotal.setText("$"+formateador.format(deLista.getPrecioCompra()));
            txtCosto.setText(formato.format(deLista.getPrecioCompra()));
            txtCantidad.setText("1");
            if(deLista.getInventario()==0){
            txtExistencia.setText("");
            txtExistencia.setVisible(false);
             lbExistencia.setVisible(false);
            }
             listaProducto.setVisible(false);
            controladorLista.setVisible(false);
            productoActual.setTipoVenta(deLista.getTipoVenta());
     }
    }//GEN-LAST:event_listaProductoMouseClicked

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
      if(productoActual.getTipoVenta().equals("Kilogramo")) event.numberDecimalKeyPress(evt, txtCantidad);
      else event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
       event.numberDecimalKeyPress(evt, txtCosto);
    }//GEN-LAST:event_txtCostoKeyTyped

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
       event.numberDecimalKeyPress(evt, txtDescuento);
    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void txtPlazoCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoCreditoKeyTyped
       event.numberKeyPress(evt);
    }//GEN-LAST:event_txtPlazoCreditoKeyTyped

    private void txtInteresCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteresCreditoKeyTyped
       event.numberDecimalKeyPress(evt, txtInteresCredito);
    }//GEN-LAST:event_txtInteresCreditoKeyTyped

    private void TablaComprasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaComprasKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){


            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_TablaComprasKeyReleased

    private void TablaRecomendacionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaRecomendacionesKeyReleased
         int codigo=evt.getKeyCode();
        switch(codigo){
   

            case KeyEvent.VK_F1:
            eventoF1();
            break;

             case KeyEvent.VK_F2:
            eventoF2();
            break;
            
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_TablaRecomendacionesKeyReleased

    private void txtMetodoPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMetodoPagoKeyReleased
         int codigo=evt.getKeyCode();
        switch(codigo){
            
            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtMetodoPagoKeyReleased

    private void txtFormaPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormaPagoKeyReleased
         int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtFormaPagoKeyReleased

    private void txtProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProveedorKeyReleased
         int codigo=evt.getKeyCode();
        switch(codigo){


            case KeyEvent.VK_F1:
            eventoF1();
            break;
            
             case KeyEvent.VK_F2:
            eventoF2();
            break;

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtProveedorKeyReleased

    private void txtDescuentoFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoFinalActionPerformed

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
            java.util.logging.Logger.getLogger(crearVerOrdenCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crearVerOrdenCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crearVerOrdenCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crearVerOrdenCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                crearVerOrdenCompra dialog = new crearVerOrdenCompra(new javax.swing.JFrame(), true);
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
    private javax.swing.JScrollPane JpCompras;
    private javax.swing.JScrollPane JpRecomendacion;
    private javax.swing.JTable TablaCompras;
    private javax.swing.JTable TablaRecomendaciones;
    private javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnModificar;
    private javax.swing.JLabel btnReporte;
    private javax.swing.JScrollPane controladorLista;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbExistencia;
    private javax.swing.JLabel lbInteres;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbNombre1;
    private javax.swing.JLabel lbNombre10;
    private javax.swing.JLabel lbNombre11;
    private javax.swing.JLabel lbNombre14;
    private javax.swing.JLabel lbNombre15;
    private javax.swing.JLabel lbNombre16;
    private javax.swing.JLabel lbNombre2;
    private javax.swing.JLabel lbNombre3;
    private javax.swing.JLabel lbNombre4;
    private javax.swing.JLabel lbNombre5;
    private javax.swing.JLabel lbNombre6;
    private javax.swing.JLabel lbNombre7;
    private javax.swing.JLabel lbNombre9;
    private javax.swing.JLabel lbPlazo;
    private javax.swing.JList<String> listaProducto;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JPanel panelModificar;
    private javax.swing.JPanel panelReporte;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDescuentoFinal;
    private javax.swing.JTextField txtExistencia;
    private javax.swing.JLabel txtFecha;
    private javax.swing.JLabel txtFolio;
    private javax.swing.JComboBox<String> txtFormaPago;
    private javax.swing.JTextField txtInteresCredito;
    private javax.swing.JTextField txtIvaFInal;
    private javax.swing.JComboBox<String> txtMetodoPago;
    private javax.swing.JTextField txtPlazoCredito;
    private javax.swing.JComboBox<String> txtProveedor;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtSubtotalFinal;
    private javax.swing.JTextField txtTotalFinal;
    private javax.swing.JTextField txtUnidad;
    // End of variables declaration//GEN-END:variables

 private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
   }
    
            private void pdfoRDEN(int id) throws ParseException {
        try {
            FileOutputStream archivo;
           File file = new File("C:\\Program Files (x86)\\AppPinturasOssel\\PDF\\OrdenComora" + txtFolio.getText() + ".pdf"); 

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
             cell = new PdfPCell(new Phrase(nombre,negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(encargado,negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase("RFC: "+rfc,negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(razonSocial,negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(horario,negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(direccion,negrita));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            encabezado.addCell(cell);
            cell = new PdfPCell(new Phrase(tel+"\nORDEN DE COMPRA",negrita));
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
            columnaEncabezado = new float[]{60f,40f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);
            
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Recepción: "+txtFecha.getText(),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Folio: "+txtFolio.getText(),negrita));
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

            cell = new PdfPCell();
            cell = new PdfPCell(new Phrase("Proveedor: "+txtProveedor.getSelectedItem().toString(),letra2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablapro.addCell(cell);
            
           doc.add(tablapro);
           
            doc.add(dejarEspacio);
            
           letra2 = new Font(Font.FontFamily.TIMES_ROMAN, 11);
            tablapro = new PdfPTable(9);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{27f,11f,20f,28f,20f,20f,20f,20f,20f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);
            
            PdfPCell pro1 = new PdfPCell(new Phrase("Código",letra2));
            PdfPCell pro2 = new PdfPCell(new Phrase("Cant",letra2));
            PdfPCell pro3 = new PdfPCell(new Phrase("Unidad",letra2));
            PdfPCell pro4 = new PdfPCell(new Phrase("Descripción",letra2));
            PdfPCell pro5 = new PdfPCell(new Phrase("P/U",letra2));
            PdfPCell pro6 = new PdfPCell(new Phrase("Importe",letra2));
            PdfPCell pro7 = new PdfPCell(new Phrase("Descuento",letra2));
            PdfPCell pro8 = new PdfPCell(new Phrase("IVA",letra2));
            PdfPCell pro9 = new PdfPCell(new Phrase("Total",letra2));

            pro1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro5.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro6.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro7.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro8.setHorizontalAlignment(Element.ALIGN_CENTER);
            pro9.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);
            tablapro.addCell(pro6);
            tablapro.addCell(pro7);
            tablapro.addCell(pro8);
            tablapro.addCell(pro9);

            
            for (int i = 0; i < TablaCompras.getRowCount(); i++) {
                for(int k = 0; k<9;k++){
                     cell = new PdfPCell();
                     cell = new PdfPCell(new Phrase(TablaCompras.getValueAt(i, k).toString(),letra2));
                     if(k>3) cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     else cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorder(0);
                     tablapro.addCell(cell); 
                }
               }

            doc.add(tablapro);
            doc.add(dejarEspacio);
            
               tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            columnaEncabezado = new float[]{25f,65f,14f,17f};
            tablapro.setWidths(columnaEncabezado);
            tablapro.setHorizontalAlignment(Chunk.ALIGN_LEFT);
            
                cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
               cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
                  cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("Subtotal",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
               cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(txtSubtotalFinal.getText(),letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
                        cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
               cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
                  cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("Descuentos",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
               cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(txtDescuentoFinal.getText(),letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
             cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
               cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
                  cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("IVA",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
               cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(txtIvaFInal.getText(),letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
                             cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
               cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
                  cell = new PdfPCell();
                cell = new PdfPCell(new Phrase("Total",letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
               cell = new PdfPCell();
                cell = new PdfPCell(new Phrase(txtTotalFinal.getText(),letra2));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                tablapro.addCell(cell);
                
                doc.add(tablapro);
            
            doc.add(dejarEspacio);
            doc.add(dejarEspacio);
            doc.add(dejarEspacio);

               /*Paragraph notasCliente = new Paragraph("\n"+configura.getMensaje(),letra2);
            doc.add(notasCliente);*/
            doc.close();
            archivo.close();
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

}
