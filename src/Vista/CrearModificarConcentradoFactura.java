/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Clientes;
import Modelo.ClienteDao;
import Modelo.CorteDiarioDao;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.Factura;
import Modelo.FacturaDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.ReferenciasCliente;
import Modelo.ReferenciasClienteDao;
import Modelo.Ventas;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import Modelo.detalleVenta;
import static Vista.vistaVenta.isNumeric;
import static Vista.vistaVenta.removefirstChar;
import com.raven.swing.Table3;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.Notification;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Jonathan Gil
 */
public class CrearModificarConcentradoFactura extends javax.swing.JDialog {

     ClienteDao client = new ClienteDao();
      Eventos event = new Eventos();
      VentasDao ventaDao = new VentasDao();
      EmpleadosDao emple = new EmpleadosDao();
      ProductoDao productoDao = new ProductoDao();
      bitacoraDao bitacora = new bitacoraDao();
      FacturaDao facturaDao = new FacturaDao();
      CorteDiarioDao corteDao = new CorteDiarioDao();
      DefaultTableModel modeloVentas = new DefaultTableModel();
      DefaultTableModel modeloProductos = new DefaultTableModel();
      DefaultTableModel modeloAgregadas = new DefaultTableModel();
      DefaultTableModel modeloFinal = new DefaultTableModel();
      DefaultTableModel modeloFinalbase = new DefaultTableModel();
      boolean accionCompletada=false;
           DecimalFormat formato = new DecimalFormat("#0.00");
      DecimalFormat formateador = new DecimalFormat("#,###,##0.00");     
         int idEmpleado, apoyoR;
    
    public void vaciarEmpleado(int idEmpleado){
        this.idEmpleado=idEmpleado;
    }

    
    public CrearModificarConcentradoFactura(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Punto de venta - Generar para facturar");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Seticon();
        
              DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer tcrDerecha = new DefaultTableCellRenderer();
        tcrDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        TableVentas.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TableVentas.getColumnModel().getColumn(4).setCellRenderer(tcr);
        TableVentas.getColumnModel().getColumn(5).setCellRenderer(tcr);
         ((DefaultTableCellRenderer) TableVentas.getTableHeader().getDefaultRenderer())
                       .setHorizontalAlignment(SwingConstants.CENTER);
         
        TablaProductos.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TablaProductos.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TablaProductos.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TablaProductos.getColumnModel().getColumn(3).setCellRenderer(tcrDerecha);
        TablaProductos.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
         ((DefaultTableCellRenderer) TablaProductos.getTableHeader().getDefaultRenderer())
                       .setHorizontalAlignment(SwingConstants.CENTER);
         
        TablaAgregadas.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TablaAgregadas.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TablaAgregadas.getColumnModel().getColumn(2).setCellRenderer(tcrDerecha);
         ((DefaultTableCellRenderer) TablaAgregadas.getTableHeader().getDefaultRenderer())
                       .setHorizontalAlignment(SwingConstants.CENTER);
         
         TabaFinal.getColumnModel().getColumn(0).setCellRenderer(tcr);
        TabaFinal.getColumnModel().getColumn(1).setCellRenderer(tcr);
        TabaFinal.getColumnModel().getColumn(2).setCellRenderer(tcr);
        TabaFinal.getColumnModel().getColumn(3).setCellRenderer(tcr);
        TabaFinal.getColumnModel().getColumn(4).setCellRenderer(tcrDerecha);
        TabaFinal.getColumnModel().getColumn(5).setCellRenderer(tcrDerecha);
        TabaFinal.getColumnModel().getColumn(6).setCellRenderer(tcrDerecha);
        TabaFinal.getColumnModel().getColumn(7).setCellRenderer(tcrDerecha);
        TabaFinal.getColumnModel().getColumn(8).setCellRenderer(tcrDerecha);
        ((DefaultTableCellRenderer) TabaFinal.getTableHeader().getDefaultRenderer())
                       .setHorizontalAlignment(SwingConstants.CENTER);
         
        spTable.getViewport().setBackground(new Color(204, 204, 204));
        TableVentas.setBackground(Color.WHITE);
         spTableFinal.getViewport().setBackground(new Color(204, 204, 204));
        TabaFinal.setBackground(Color.WHITE);
         spProductos.getViewport().setBackground(new Color(204, 204, 204));
        TablaProductos.setBackground(Color.WHITE);
         spTable1.getViewport().setBackground(new Color(204, 204, 204));
        TablaAgregadas.setBackground(Color.WHITE);
         
         addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    Point currentLocation = getLocation();
                       if (currentLocation.y < 50) 
                          setLocation(200, 50);;
                    
                }
            });
         
         listarVentas();
         panelAgregar.setBackground(new Color(153,204,255));
         panelEliminar.setBackground(new Color(153,204,255));
         jList1.setVisible(false);
         modeloFinalbase = (DefaultTableModel) TabaFinal.getModel();
         listarCFDI();
         listarRegimen();
         txtRegimenFiscal.setBackground(new Color(255,255,255));
         txtCFDI.setBackground(new Color(255,255,255));
         txtFormaPago1.setBackground(new Color(255,255,255));
         jList1.setVisible(false);
         controladorLista.setVisible(false);
          controladorListaNombres.setVisible(false);
         listaNombres.setVisible(false);
         txtCodigo.setText(""+(facturaDao.idFactura()+1));
         txtFechaF.setText(fechaCorrecta(corteDao.getDia()));
    }
    
        public void LimpiarTablaVenta() {
        for (int i = 0; i < modeloVentas.getRowCount(); i++) {
            modeloVentas.removeRow(i);
            i = i - 1;
        }
    }
        
           public void LimpiarTablaProductos() {
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            modeloProductos.removeRow(i);
            i = i - 1;
        }
    }
          public void listarNotasPorNombre(int idCliente){
         List<Ventas> lsVentas = ventaDao.listarVentasCliente(idCliente);   
        vaciarATabla(lsVentas);
     }
          
           public void listarVentas() {//Metodo para vaciar las notas en su respectiva tabla
        List<Ventas> lsVentas = ventaDao.listarVentas();
        vaciarATabla(lsVentas);
    }
    
    public void buscarPorFolioTabla(int folio){
            List<Ventas> lsVentas = ventaDao.listarVentasSoloUna(folio);
     
        vaciarATabla(lsVentas); 
    }
    
    public void listarPorPeriodoDeFecha() {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<Ventas> lsVentas = ventaDao.listarVentasPeriodo(sdf.format(jDateChooser1.getDate()), sdf.format(jDateChooser2.getDate()));
        vaciarATabla(lsVentas); 
    }

    
    public void vaciarATabla(List<Ventas> lsVentas){
        modeloVentas = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[6];
        for (int i = lsVentas.size()-1; i>=0; i--) {//las vaciamos de la mas reciente a la mas vieja
           ob[0] = fechaFormatoCorrecto(lsVentas.get(i).getFecha());
           ob[1] = "F"+lsVentas.get(i).getFolio();
           Clientes clie = client.BuscarPorCodigo(lsVentas.get(i).getIdCliente());
            if(clie.getTipoPersona().equals("Persona Física")) ob[2]=clie.getNombre()+" "+clie.getApellidoP()+" "+clie.getApellidoM();
            else ob[2]=clie.getNombreComercial();
           ob[3] = "$"+formateador.format(lsVentas.get(i).getTotal());
           ob[4] = lsVentas.get(i).getFormaPago();
           if(lsVentas.get(i).getFacturado()==0) ob[5] = "NO";
           else ob[5] = "SI"; 
                           modeloVentas.addRow(ob);
        }
            TableVentas.setModel(modeloVentas);


    }
    
         public String fechaFormatoCorrecto(String fechaHoy){
    Date diaActual = StringADate(fechaHoy);
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar c = Calendar.getInstance();
        c.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
        return fechaUsar;
    }
         
    public void previsualizarVenta(int folio){
        Ventas verVenta = ventaDao.buscarPorFolio(folio);
        LimpiarTablaProductos();
        txtFolio.setText("F"+verVenta.getFolio());
        txtFecha.setText(fechaFormatoCorrecto(verVenta.getFecha()));
        txtTotal.setText("$"+formateador.format(verVenta.getTotal()));
         Clientes clie = client.BuscarPorCodigo(verVenta.getIdCliente());
            if(clie.getTipoPersona().equals("Persona Física")) txtNombre.setText(clie.getNombre()+" "+clie.getApellidoP()+" "+clie.getApellidoM());
            txtNombre.setText(clie.getNombreComercial());
        txtFormaPago.setText(verVenta.getFormaPago());
        List<detalleVenta> ListaDetalles = ventaDao.regresarDetalles(verVenta.getFolio());
        modeloProductos = (DefaultTableModel) TablaProductos.getModel();
          for (int i = 0; i < ListaDetalles.size(); i++) {
            
            Producto proc = productoDao.BuscarPorCodigo(ListaDetalles.get(i).getCodigo());
            Object[] o = new Object[9];
            o[0] = ListaDetalles.get(i).getCodigo();
            o[1] = formato.format(ListaDetalles.get(i).getCantidad());
            o[2] = ListaDetalles.get(i).getDescripcion();
            o[3] = "$"+formateador.format(ListaDetalles.get(i).getPrecioUnitario());
            double importeT = ListaDetalles.get(i).getPrecioUnitario()*ListaDetalles.get(i).getCantidad();
            double ivaT = (importeT-ListaDetalles.get(i).getDescuento())*.16;
            o[4] = "$"+formateador.format(ivaT+(importeT-ListaDetalles.get(i).getDescuento()));

            modeloProductos.addRow(o);
        }
        TablaProductos.setModel(modeloProductos);
    }

    
    public void listarCFDI(){
        txtCFDI.removeAllItems();
        txtCFDI.addItem("Uso de CFDI");
        txtCFDI.addItem("G01 Adquisición de mercancías");
txtCFDI.addItem("G02 Devoluciones, descuentos o bonificaciones");
txtCFDI.addItem("G03 Gastos en general");
txtCFDI.addItem("I01 Construcciones");
txtCFDI.addItem("I02 Mobiliario y equipo de oficina por inversiones");
txtCFDI.addItem("I03 Equipo de transporte");
txtCFDI.addItem("I04 Equipo de computo y accesorios");
txtCFDI.addItem("I05 Dados, troqueles, moldes, matrices y herramental");
txtCFDI.addItem("I06 Comunicaciones telefónicas");
txtCFDI.addItem("I07 Comunicaciones satelitales");
txtCFDI.addItem("I08 Otra maquinaria y equipo");
txtCFDI.addItem("D01 Honorarios médicos, dentales y gastos hospitalarios");
txtCFDI.addItem("D02 Gastos médicos por incapacidad o discapacidad");
txtCFDI.addItem("D03 Gastos funerales.	");
txtCFDI.addItem("D04 Donativos.	");
txtCFDI.addItem("D05 Intereses reales efectivamente pagados por créditos hipotecarios (casa habitación)");
txtCFDI.addItem("D06 Aportaciones voluntarias al SAR.	");
txtCFDI.addItem("D07 Primas por seguros de gastos médicos.	");
txtCFDI.addItem("D08 Gastos de transportación escolar obligatoria.	");
txtCFDI.addItem("D09 Depósitos en cuentas para el ahorro, primas que tengan como base planes de pensiones");
txtCFDI.addItem("D10 Pagos por servicios educativos (colegiaturas)");
txtCFDI.addItem("S01 Sin efectos fiscales. ");
txtCFDI.addItem("CP01 Pago");
txtCFDI.addItem("CN01 Nómin");

    }
    
     public void listarRegimen(){
        txtRegimenFiscal.removeAllItems();
        txtRegimenFiscal.addItem("Régimen Fiscal");
        txtRegimenFiscal.addItem("601 General de Ley Personas Morales	");
txtRegimenFiscal.addItem("603 Personas Morales con Fines no Lucrativos");
txtRegimenFiscal.addItem("605 Sueldos y Salarios e Ingresos Asimilados a Salarios");
txtRegimenFiscal.addItem("606 Arrendamiento");
txtRegimenFiscal.addItem("607 Régimen de Enajenación o Adquisición de Bienes");
txtRegimenFiscal.addItem("608 Demás ingresos");
txtRegimenFiscal.addItem("610 Residentes en el Extranjero sin Establecimiento Permanente en México");
txtRegimenFiscal.addItem("611 Ingresos por Dividendos (socios y accionistas)");
txtRegimenFiscal.addItem("612 Personas Físicas con Actividades Empresariales y Profesionales");
txtRegimenFiscal.addItem("614 Ingresos por intereses");
txtRegimenFiscal.addItem("615 Régimen de los ingresos por obtención de premios");
txtRegimenFiscal.addItem("616 Sin obligaciones fiscales");
txtRegimenFiscal.addItem("620 Sociedades Cooperativas de Producción que optan por diferir sus ingresos");
txtRegimenFiscal.addItem("621 Incorporación Fiscal");
txtRegimenFiscal.addItem("622 Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras");
txtRegimenFiscal.addItem("623 Opcional para Grupos de Sociedades");
txtRegimenFiscal.addItem("624 Coordinados");
txtRegimenFiscal.addItem("625 Régimen de las Actividades Empresariales con ingresos a través de Plataformas Tecnológicas");
txtRegimenFiscal.addItem("626 Régimen Simplificado de Confianza");

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
        
    public void eventoEnter(){
        
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
      
      public void concentradoProductos(){
          List<List<String>> listaDeListas = new ArrayList<>();
          double totalIva= 0;
          double totalDescuento= 0;
          double totalSubtotal= 0;
          double totalFinal = 0;
          for(int folioVentas = 0; folioVentas<TablaAgregadas.getRowCount();folioVentas++){
              Ventas miVenta = ventaDao.buscarPorFolio(Integer.parseInt(TablaAgregadas.getValueAt(folioVentas, 0).toString().substring(1)));
              totalSubtotal =  totalSubtotal+miVenta.getSubTotal();
              totalDescuento = totalDescuento+ miVenta.getDescuentos();
              totalIva = totalIva+miVenta.getIva();
              totalFinal = totalFinal + miVenta.getTotal();
              List<detalleVenta> ListaDetalles = ventaDao.regresarDetalles(miVenta.getFolio());
              for(int listaDetalle = 0; listaDetalle<ListaDetalles.size();listaDetalle++){
                  boolean existe=false;
                  Producto proc = productoDao.BuscarPorCodigo(ListaDetalles.get(listaDetalle).getCodigo());
                 for(int checandoListas = 0; checandoListas<listaDeListas.size();checandoListas++){
                          if(listaDeListas.get(checandoListas).get(0).equals(proc.getCodigo())){
                            existe=true;
                            listaDeListas.get(checandoListas).set(1, formato.format(Double.parseDouble(listaDeListas.get(checandoListas).get(1))+ListaDetalles.get(listaDetalle).getCantidad()));
                            listaDeListas.get(checandoListas).set(5, formato.format(Double.parseDouble(listaDeListas.get(checandoListas).get(5))+(ListaDetalles.get(listaDetalle).getPrecioUnitario()*ListaDetalles.get(listaDetalle).getCantidad())));
                            listaDeListas.get(checandoListas).set(6, formato.format(Double.parseDouble(listaDeListas.get(checandoListas).get(6))+ListaDetalles.get(listaDetalle).getDescuento()));
                            double importeT = ListaDetalles.get(listaDetalle).getPrecioUnitario()*ListaDetalles.get(listaDetalle).getCantidad();
                            double ivaT = (importeT-ListaDetalles.get(listaDetalle).getDescuento())*.16;
                            listaDeListas.get(checandoListas).set(7, formato.format(Double.parseDouble(listaDeListas.get(checandoListas).get(7))+ivaT));
                            listaDeListas.get(checandoListas).set(8, formato.format(Double.parseDouble(listaDeListas.get(checandoListas).get(8))+ivaT+(importeT-ListaDetalles.get(listaDetalle).getDescuento())));

                          }
                  }
                  if(existe==false){
                    List<String> lista1Nueva = new ArrayList<>();
                    lista1Nueva.add(ListaDetalles.get(listaDetalle).getCodigo());
                    lista1Nueva.add(formato.format(ListaDetalles.get(listaDetalle).getCantidad()));
                    lista1Nueva.add(proc.getTipoVenta());
                    lista1Nueva.add(ListaDetalles.get(listaDetalle).getDescripcion());
                   lista1Nueva.add(formato.format(ListaDetalles.get(listaDetalle).getPrecioUnitario()));
                    lista1Nueva.add(formato.format(ListaDetalles.get(listaDetalle).getPrecioUnitario()*ListaDetalles.get(listaDetalle).getCantidad()));
                    double importeT = ListaDetalles.get(listaDetalle).getPrecioUnitario()*ListaDetalles.get(listaDetalle).getCantidad();
                    double ivaT = (importeT-ListaDetalles.get(listaDetalle).getDescuento())*.16;
                    lista1Nueva.add(formato.format(ListaDetalles.get(listaDetalle).getDescuento()));
                    lista1Nueva.add(formato.format(ivaT));
                    lista1Nueva.add(formato.format(ivaT+(importeT-ListaDetalles.get(listaDetalle).getDescuento())));
                    listaDeListas.add(lista1Nueva);
                  }
              }
          }
          modeloFinal = (DefaultTableModel) TabaFinal.getModel();
          modeloFinal.setRowCount(0);
          for(int i=0; i<listaDeListas.size();i++){
            Object[] o = new Object[9];
            o[0] = listaDeListas.get(i).get(0);
            o[1] = formato.format(Double.parseDouble(listaDeListas.get(i).get(1)));
            o[2] = listaDeListas.get(i).get(2);
            o[3] = listaDeListas.get(i).get(3);
            o[4] = "$"+formateador.format(Double.parseDouble(listaDeListas.get(i).get(4)));
            o[5] = "$"+formateador.format(Double.parseDouble(listaDeListas.get(i).get(5)));
            o[6] = "$"+formateador.format(Double.parseDouble(listaDeListas.get(i).get(6)));
            o[7] = "$"+formateador.format(Double.parseDouble(listaDeListas.get(i).get(7)));
            o[8] = "$"+formateador.format(Double.parseDouble(listaDeListas.get(i).get(8)));
            modeloFinal.addRow(o);
          }
          TabaFinal.setModel(modeloFinal);
          txtSubtotalFinal.setText("$"+formateador.format(totalSubtotal));
          txtDescuentoFinal.setText("$"+formateador.format(totalDescuento));
          txtIvaFInal.setText("$"+formateador.format(totalIva));
          txtTotalFinal.setText("$"+formateador.format(totalFinal));

      
      }
    
                private void registrarDetalle() throws ParseException {
        int id = facturaDao.idFactura();
        for (int i = 0; i < TabaFinal.getRowCount(); i++) {
            String codigo = TabaFinal.getValueAt(i, 0).toString();
            String descripcion = TabaFinal.getValueAt(i, 3).toString();       
            Number cambiando = formateador.parse(removefirstChar(TabaFinal.getValueAt(i, 4).toString()));          
            double precioUnitario = cambiando.doubleValue();
            double cantidad = Double.parseDouble(TabaFinal.getValueAt(i, 1).toString());
            cambiando = formateador.parse(removefirstChar(TabaFinal.getValueAt(i, 5).toString()));
            double precioFinal = cambiando.doubleValue();
            cambiando = formateador.parse(removefirstChar(TabaFinal.getValueAt(i, 6).toString()));
            double descuentosD = cambiando.doubleValue();

            Producto prod = productoDao.BuscarPorCodigo(codigo);
            prod.setCodigo(codigo);
            prod.setExistencia(cantidad);
            
            detalleVenta detalle = new detalleVenta();
            detalle.setCodigo(codigo);
            detalle.setDescripcion(descripcion);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setImporte(precioFinal);
            detalle.setFolioVenta(id);
            detalle.setCantidad(cantidad);
            detalle.setDescuento(descuentosD);
            facturaDao.registrarDetalle(detalle);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelDatos = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        panelSeleccionNotas1 = new javax.swing.JPanel();
        spTable1 = new javax.swing.JScrollPane();
        TablaAgregadas = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        panelSeleccionNotas = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        spProductos = new javax.swing.JScrollPane();
        TablaProductos = new javax.swing.JTable();
        lbInteres5 = new javax.swing.JLabel();
        txtFolio = new javax.swing.JTextField();
        lbInteres2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lbInteres6 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lbInteres7 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        lbInteres8 = new javax.swing.JLabel();
        txtFormaPago = new javax.swing.JTextField();
        panelSeleccionNotas2 = new javax.swing.JPanel();
        spTable = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        jTextField1 = new textfield.TextField();
        controladorLista = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel13 = new javax.swing.JLabel();
        primeraFecha = new textfield.TextField();
        segundaFecha = new textfield.TextField();
        jLabel33 = new javax.swing.JLabel();
        panelAgregar = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtFechaF = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        panelSeleccionNotas3 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        spTableFinal = new javax.swing.JScrollPane();
        TabaFinal = new javax.swing.JTable();
        lbInteres9 = new javax.swing.JLabel();
        lbInteres10 = new javax.swing.JLabel();
        txtDescuentoFinal = new javax.swing.JTextField();
        txtSubtotalFinal = new javax.swing.JTextField();
        txtIvaFInal = new javax.swing.JTextField();
        lbInteres11 = new javax.swing.JLabel();
        lbInteres3 = new javax.swing.JLabel();
        txtTotalFinal = new javax.swing.JTextField();
        panelAceptar = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JLabel();
        panelEliminar1 = new javax.swing.JPanel();
        btnEliminar1 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        controladorListaNombres = new javax.swing.JScrollPane();
        listaNombres = new javax.swing.JList<>();
        jLabel32 = new javax.swing.JLabel();
        lbNombre1 = new javax.swing.JLabel();
        lbRFC = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField();
        lbNombre4 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lbCalle = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtCFDI = new javax.swing.JComboBox<>();
        lbCFDI = new javax.swing.JLabel();
        LBREGIMEM = new javax.swing.JLabel();
        txtRegimenFiscal = new javax.swing.JComboBox<>();
        txtNombreCliente = new javax.swing.JTextField();
        lbInteres12 = new javax.swing.JLabel();
        txtFormaPago1 = new javax.swing.JComboBox<>();

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
        setResizable(false);

        panelDatos.setBackground(new java.awt.Color(255, 255, 255));
        panelDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setBackground(new java.awt.Color(0, 0, 204));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Selector de ventas");
        panelDatos.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 240, -1));

        panelSeleccionNotas1.setBackground(new java.awt.Color(255, 255, 255));
        panelSeleccionNotas1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelSeleccionNotas1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaAgregadas = new Table3();
        TablaAgregadas.setBackground(new java.awt.Color(204, 204, 255));
        TablaAgregadas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TablaAgregadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Folio", "Cliente", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaAgregadas.setToolTipText("Ventas agregadas");
        TablaAgregadas.setFocusable(false);
        TablaAgregadas.setGridColor(new java.awt.Color(255, 255, 255));
        TablaAgregadas.setOpaque(false);
        TablaAgregadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaAgregadasMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TablaAgregadasMouseExited(evt);
            }
        });
        TablaAgregadas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaAgregadasKeyPressed(evt);
            }
        });
        spTable1.setViewportView(TablaAgregadas);
        if (TablaAgregadas.getColumnModel().getColumnCount() > 0) {
            TablaAgregadas.getColumnModel().getColumn(0).setPreferredWidth(5);
            TablaAgregadas.getColumnModel().getColumn(1).setPreferredWidth(80);
            TablaAgregadas.getColumnModel().getColumn(2).setPreferredWidth(13);
        }

        panelSeleccionNotas1.add(spTable1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 40, 310, 320));

        jLabel27.setBackground(new java.awt.Color(0, 0, 204));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Eliminar venta");
        panelSeleccionNotas1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 100, -1));

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
        btnEliminar.setToolTipText("Borrar venta seleccionada de la lista");
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
            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelEliminarLayout.setVerticalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelSeleccionNotas1.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 50, 50));

        jLabel29.setBackground(new java.awt.Color(0, 0, 204));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Ventas agregadas");
        panelSeleccionNotas1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 240, -1));

        panelDatos.add(panelSeleccionNotas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, 320, 430));

        panelSeleccionNotas.setBackground(new java.awt.Color(255, 255, 255));
        panelSeleccionNotas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelSeleccionNotas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Vista previa de venta seleccionada");
        panelSeleccionNotas.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 240, -1));

        TablaProductos = new Table3();
        TablaProductos.setBackground(new java.awt.Color(204, 204, 255));
        TablaProductos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cant", "Descripción", "P/U", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProductos.setToolTipText("Articulos de la venta");
        TablaProductos.setFocusable(false);
        TablaProductos.setGridColor(new java.awt.Color(255, 255, 255));
        TablaProductos.setOpaque(false);
        TablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProductosMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TablaProductosMouseExited(evt);
            }
        });
        TablaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaProductosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaProductosKeyReleased(evt);
            }
        });
        spProductos.setViewportView(TablaProductos);
        if (TablaProductos.getColumnModel().getColumnCount() > 0) {
            TablaProductos.getColumnModel().getColumn(0).setPreferredWidth(15);
            TablaProductos.getColumnModel().getColumn(1).setPreferredWidth(5);
            TablaProductos.getColumnModel().getColumn(2).setPreferredWidth(25);
            TablaProductos.getColumnModel().getColumn(3).setPreferredWidth(15);
            TablaProductos.getColumnModel().getColumn(4).setPreferredWidth(15);
        }

        panelSeleccionNotas.add(spProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 440, 100));

        lbInteres5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres5.setText("Folio");
        panelSeleccionNotas.add(lbInteres5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 42, 30, 20));

        txtFolio.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFolio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFolio.setToolTipText("Folio");
        txtFolio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFolio.setEnabled(false);
        txtFolio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFolioKeyReleased(evt);
            }
        });
        panelSeleccionNotas.add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 40, 120, -1));

        lbInteres2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres2.setText("Cliente");
        panelSeleccionNotas.add(lbInteres2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 42, 52, 20));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setToolTipText("Nombre completo o comercial del cliente");
        txtNombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombre.setEnabled(false);
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
        });
        panelSeleccionNotas.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 220, -1));

        lbInteres6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres6.setText("Fecha");
        panelSeleccionNotas.add(lbInteres6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, 60, 20));

        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setToolTipText("Fecha de registro de la venta");
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFechaKeyReleased(evt);
            }
        });
        panelSeleccionNotas.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 100, -1));

        lbInteres7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres7.setText("Total");
        panelSeleccionNotas.add(lbInteres7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 60, 20));

        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setToolTipText("Total de la venta (con descuentos e IVA)");
        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal.setEnabled(false);
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalKeyReleased(evt);
            }
        });
        panelSeleccionNotas.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 100, -1));

        lbInteres8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres8.setText("Forma pago");
        panelSeleccionNotas.add(lbInteres8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, 80, 20));

        txtFormaPago.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFormaPago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFormaPago.setToolTipText("Forma de pago registrada");
        txtFormaPago.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFormaPago.setEnabled(false);
        txtFormaPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFormaPagoKeyReleased(evt);
            }
        });
        panelSeleccionNotas.add(txtFormaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 160, -1));

        panelDatos.add(panelSeleccionNotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 345, 630, 180));

        panelSeleccionNotas2.setBackground(new java.awt.Color(255, 255, 255));
        panelSeleccionNotas2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelSeleccionNotas2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVentas = new Table3();
        TableVentas.setBackground(new java.awt.Color(204, 204, 255));
        TableVentas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Folio", "Cliente", "Total", "FormaPago", "Facturado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVentas.setToolTipText("Listado de ventas");
        TableVentas.setFocusable(false);
        TableVentas.setGridColor(new java.awt.Color(255, 255, 255));
        TableVentas.setOpaque(false);
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TableVentasMouseExited(evt);
            }
        });
        TableVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableVentasKeyPressed(evt);
            }
        });
        spTable.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(5);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(80);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(15);
            TableVentas.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableVentas.getColumnModel().getColumn(5).setPreferredWidth(4);
        }

        panelSeleccionNotas2.add(spTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 610, 150));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setLabelText("Buscar nota");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        panelSeleccionNotas2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 190, 40));

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        controladorLista.setViewportView(jList1);

        panelSeleccionNotas2.add(controladorLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 190, 10));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("     Buscar por fecha");
        panelSeleccionNotas2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 130, 20));

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
        panelSeleccionNotas2.add(primeraFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 130, 40));

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
        panelSeleccionNotas2.add(segundaFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 130, 40));

        jLabel33.setBackground(new java.awt.Color(0, 0, 204));
        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Ventas a agregar");
        panelSeleccionNotas2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 240, -1));

        panelAgregar.setBackground(new java.awt.Color(255, 255, 255));
        panelAgregar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAgregarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelAgregarMouseEntered(evt);
            }
        });

        btnAgregar.setBackground(new java.awt.Color(153, 204, 255));
        btnAgregar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar nota 2.png"))); // NOI18N
        btnAgregar.setToolTipText("Agregar venta a la lista");
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarMouseExited(evt);
            }
        });
        btnAgregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAgregarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAgregarLayout = new javax.swing.GroupLayout(panelAgregar);
        panelAgregar.setLayout(panelAgregarLayout);
        panelAgregarLayout.setHorizontalGroup(
            panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );
        panelAgregarLayout.setVerticalGroup(
            panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        panelSeleccionNotas2.add(panelAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 250, 50, 50));

        jLabel28.setBackground(new java.awt.Color(0, 0, 204));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Agregar venta");
        panelSeleccionNotas2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 90, -1));

        panelDatos.add(panelSeleccionNotas2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 630, 310));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("CÓDIGO");
        jLabel18.setToolTipText("");
        panelDatos.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 23, 90, -1));

        txtCodigo.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtCodigo.setToolTipText("Código único");
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.setEnabled(false);
        panelDatos.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, 110, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("FECHA");
        jLabel19.setToolTipText("");
        panelDatos.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 53, 90, -1));

        txtFechaF.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtFechaF.setToolTipText("Fecha del sistema");
        txtFechaF.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFechaF.setEnabled(false);
        panelDatos.add(txtFechaF, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 50, 110, -1));

        jTabbedPane1.addTab("Ventas", panelDatos);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSeleccionNotas3.setBackground(new java.awt.Color(255, 255, 255));
        panelSeleccionNotas3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelSeleccionNotas3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setBackground(new java.awt.Color(0, 0, 204));
        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Resumen de ventas seleccionadas");
        panelSeleccionNotas3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 240, -1));

        TabaFinal = new Table3();
        TabaFinal.setBackground(new java.awt.Color(204, 204, 255));
        TabaFinal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TabaFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cant", "Unidad", "Descripción", "P/U", "Importe", "Descuento", "IVA", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabaFinal.setToolTipText("Haga doble click para modificar");
        TabaFinal.setFocusable(false);
        TabaFinal.setGridColor(new java.awt.Color(255, 255, 255));
        TabaFinal.setOpaque(false);
        TabaFinal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabaFinalMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TabaFinalMouseExited(evt);
            }
        });
        TabaFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TabaFinalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TabaFinalKeyReleased(evt);
            }
        });
        spTableFinal.setViewportView(TabaFinal);

        panelSeleccionNotas3.add(spTableFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 940, 200));

        lbInteres9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres9.setText("Subtotal");
        panelSeleccionNotas3.add(lbInteres9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 250, 60, 20));

        lbInteres10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres10.setText("Descuento");
        panelSeleccionNotas3.add(lbInteres10, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 280, 60, 20));

        txtDescuentoFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDescuentoFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuentoFinal.setToolTipText("Descuentos acumulados");
        txtDescuentoFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescuentoFinal.setEnabled(false);
        txtDescuentoFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoFinalKeyReleased(evt);
            }
        });
        panelSeleccionNotas3.add(txtDescuentoFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 280, 170, -1));

        txtSubtotalFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtSubtotalFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotalFinal.setToolTipText("Subtotal de ventas");
        txtSubtotalFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubtotalFinal.setEnabled(false);
        txtSubtotalFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubtotalFinalActionPerformed(evt);
            }
        });
        txtSubtotalFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSubtotalFinalKeyReleased(evt);
            }
        });
        panelSeleccionNotas3.add(txtSubtotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 250, 170, -1));

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
        panelSeleccionNotas3.add(txtIvaFInal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 310, 170, -1));

        lbInteres11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres11.setText("IVA");
        panelSeleccionNotas3.add(lbInteres11, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 310, 60, 20));

        lbInteres3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres3.setText("Total");
        panelSeleccionNotas3.add(lbInteres3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 340, 60, 20));

        txtTotalFinal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTotalFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalFinal.setToolTipText("Total de la venta");
        txtTotalFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalFinal.setEnabled(false);
        txtTotalFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalFinalKeyReleased(evt);
            }
        });
        panelSeleccionNotas3.add(txtTotalFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 340, 170, -1));

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
        btnAceptar.setToolTipText("ENTER - Guardar concentrado");
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

        panelSeleccionNotas3.add(panelAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, 50, 50));

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

        panelSeleccionNotas3.add(panelEliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 310, 50, 50));

        jLabel23.setBackground(new java.awt.Color(0, 0, 204));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("¿Está seguro de ejecutar esta acción? ");
        panelSeleccionNotas3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, -1, -1));

        jPanel1.add(panelSeleccionNotas3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 960, 370));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listaNombres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaNombresMouseClicked(evt);
            }
        });
        controladorListaNombres.setViewportView(listaNombres);

        jPanel2.add(controladorListaNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 310, 10));

        jLabel32.setBackground(new java.awt.Color(0, 0, 204));
        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Datos fiscales");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 5, 130, 20));

        lbNombre1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre1.setText("Nombre*");
        jPanel2.add(lbNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 33, 55, -1));

        lbRFC.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbRFC.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbRFC.setText("RFC*");
        jPanel2.add(lbRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 33, 40, -1));

        txtRfc.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtRfc.setToolTipText("Introduzca el RFC");
        txtRfc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRfcKeyReleased(evt);
            }
        });
        jPanel2.add(txtRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 180, -1));

        lbNombre4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre4.setText("Correo*");
        jPanel2.add(lbNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 104, 55, -1));

        txtCorreo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCorreo.setToolTipText("Correo eléctronico");
        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoKeyReleased(evt);
            }
        });
        jPanel2.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 270, -1));

        lbCalle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbCalle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCalle.setText("Dirección*");
        jPanel2.add(lbCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 74, 60, -1));

        txtDireccion.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDireccion.setToolTipText("Dirección fiscal del cliente");
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
        });
        jPanel2.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 270, -1));

        txtCFDI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Uso de CFDI", "Item 2", "Item 3", "Item 4" }));
        txtCFDI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCFDIKeyReleased(evt);
            }
        });
        jPanel2.add(txtCFDI, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 100, 225, -1));

        lbCFDI.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbCFDI.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCFDI.setText("CFDI*");
        jPanel2.add(lbCFDI, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 104, 58, -1));

        LBREGIMEM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBREGIMEM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBREGIMEM.setText("Régimen*");
        jPanel2.add(LBREGIMEM, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 74, 58, -1));

        txtRegimenFiscal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Régimen Fiscal", "Item 2", "Item 3", "Item 4" }));
        txtRegimenFiscal.setToolTipText("Régimen Fiscal");
        txtRegimenFiscal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRegimenFiscalKeyReleased(evt);
            }
        });
        jPanel2.add(txtRegimenFiscal, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 70, 225, -1));

        txtNombreCliente.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombreCliente.setToolTipText("Nombre completo o comercial del cliente");
        txtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteActionPerformed(evt);
            }
        });
        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });
        jPanel2.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 310, -1));

        lbInteres12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbInteres12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInteres12.setText("Forma*");
        jPanel2.add(lbInteres12, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 50, 20));

        txtFormaPago1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Forma de pago", "01 Efectivo", "02 Cheque nominativo", "03 Transferencia electrónica de fondos", "04 Tarjeta de crédito", "28 Tarjeta de débito", "99 Por definir" }));
        txtFormaPago1.setToolTipText("Seleccione el forma de pago");
        txtFormaPago1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFormaPago1KeyReleased(evt);
            }
        });
        jPanel2.add(txtFormaPago1, new org.netbeans.lib.awtextra.AbsoluteConstraints(725, 70, 200, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, 950, 150));

        jTabbedPane1.addTab("Concentrado", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        jDateChooser2.setMinSelectableDate(jDateChooser1.getDate());
        jDateChooser2.setSelectedDate(jDateChooser1.getDate());
        if(jDateChooser1.getDate()!=null){
            try {
                LimpiarTablaVenta();
                listarPorPeriodoDeFecha();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ALGO SUCEDIO MAL, PRUEBA OTRA FECHA" + e);
            }}
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if(jDateChooser2.getDate()!=null){
            try {
                LimpiarTablaVenta();
                listarPorPeriodoDeFecha();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ALGO SUCEDIO MAL, PRUEBA OTRA VEZ" + e);
            }}
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void panelAgregarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAgregarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAgregarMouseEntered

    private void panelAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAgregarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAgregarMouseClicked

    private void btnAgregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarKeyPressed

    private void btnAgregarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseExited
      if(TableVentas.getSelectedRow()!=-1){
          panelAgregar.setBackground(new Color(255,255,255));
      }else panelAgregar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAgregarMouseExited

    private void btnAgregarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseEntered
       panelAgregar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnAgregarMouseEntered

    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked
      if(TableVentas.getSelectedRow()!=-1){
        boolean agregando=true;
      modeloAgregadas = (DefaultTableModel) TablaAgregadas.getModel();
      int ventaSeleccionada = Integer.parseInt(TableVentas.getValueAt(TableVentas.getSelectedRow(), 1).toString().substring(1));
        for(int i = 0; i<TablaAgregadas.getRowCount();i++){
          int folioActual = Integer.parseInt(TablaAgregadas.getValueAt(i, 0).toString().substring(1));
          if(folioActual==ventaSeleccionada)
              agregando=false;
      }
      
      if(agregando==true){
             Ventas notaAgregar = ventaDao.buscarPorFolio(ventaSeleccionada);
             Object[] ob = new Object[3];
             ob[0] = "F"+notaAgregar.getFolio();
              Clientes clie = client.BuscarPorCodigo(notaAgregar.getIdCliente());
            if(clie.getTipoPersona().equals("Persona Física")) ob[1]=clie.getNombre()+" "+clie.getApellidoP()+" "+clie.getApellidoM();
            else ob[1]=clie.getNombreComercial();
             ob[2] = "$"+formateador.format(notaAgregar.getTotal());
             modeloAgregadas.addRow(ob);
             TablaAgregadas.setModel(modeloAgregadas);
             panelAgregar.setBackground(new Color(153,204,255));
             concentradoProductos();
               Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Venta agregada");
                                 panel.showNotification();
      }else{
             Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Venta agregada repetiva");
                                 panel.showNotification();
      }
      }else{
          Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Seleccione una fila antes");
                                 panel.showNotification();
      }
    }//GEN-LAST:event_btnAgregarMouseClicked

    private void segundaFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaFechaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_segundaFechaKeyReleased

    private void segundaFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaFechaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_segundaFechaKeyPressed

    private void primeraFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primeraFechaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_primeraFechaKeyReleased

    private void primeraFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primeraFechaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_primeraFechaKeyPressed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
      if (evt.getClickCount() == 2) {
            List<Clientes> lista = client.buscarLetra(jTextField1.getText());
            int k = jList1.getSelectedIndex();
            jTextField1.setText(jList1.getSelectedValue());
                jList1.setVisible(false);
                LimpiarTablaVenta();
                listarNotasPorNombre(lista.get(k).getId());
                            apoyoR =lista.get(k).getId();

            
            controladorLista.setVisible(false);

            jList1.setVisible(false);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if("".equals(jTextField1.getText())){
            controladorLista.setVisible(false);
            jList1.setVisible(false);
            LimpiarTablaVenta();
            listarVentas();
        }else{
                if(isNumeric(jTextField1.getText())==true){
                Ventas n = ventaDao.buscarPorFolio(Integer.parseInt(jTextField1.getText()));
                LimpiarTablaVenta();
                buscarPorFolioTabla(Integer.parseInt(jTextField1.getText()));

            }else{

                    DefaultListModel  model = new DefaultListModel();
                controladorLista.setVisible(true);
                jList1.setVisible(true);
                controladorLista.setBounds(controladorLista.getX(), controladorLista.getY(), controladorLista.getWidth(), 85);
                jList1.setBounds(jList1.getX(), jList1.getY(), jList1.getWidth(), 85);
                List<Clientes> lista = client.buscarLetra(jTextField1.getText());
                for (int i = 0; i < lista.size(); i++) {
                    if(lista.get(i).getEstatus()==1){
                        if(lista.get(i).getTipoPersona().equals("Persona Física")) model.addElement(lista.get(i).getNombre()+" "+lista.get(i).getApellidoP()+" "+lista.get(i).getApellidoM());
                        else model.addElement(lista.get(i).getNombreComercial());
                    }
                }
                jList1.setModel(model);
                }
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

    private void TableVentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableVentasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TableVentasKeyPressed

    private void TableVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TableVentasMouseExited

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
         if (evt.getClickCount() == 2) {
            int fila = TableVentas.rowAtPoint(evt.getPoint());
            int buscando = Integer.parseInt(TableVentas.getValueAt(fila, 1).toString().substring(1));
            previsualizarVenta(buscando);
              panelAgregar.setBackground(new Color(255,255,255));
         }
    }//GEN-LAST:event_TableVentasMouseClicked

    private void txtFormaPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormaPagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFormaPagoKeyReleased

    private void txtTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalKeyReleased

    private void txtFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaKeyReleased

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtFolioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFolioKeyReleased

    private void TablaProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaProductosKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_TablaProductosKeyReleased

    private void TablaProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaProductosKeyPressed

    }//GEN-LAST:event_TablaProductosKeyPressed

    private void TablaProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProductosMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaProductosMouseExited

    private void TablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProductosMouseClicked
 
    }//GEN-LAST:event_TablaProductosMouseClicked

    private void panelEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminarMouseEntered

    private void panelEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminarMouseClicked

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){
            
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_btnEliminarKeyPressed

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
         if(TablaAgregadas.getSelectedRow()!=-1){
          panelEliminar.setBackground(new Color(255,255,255));
      }else panelEliminar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        panelEliminar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
      if(TablaAgregadas.getSelectedRow()!=-1){
        modeloAgregadas = (DefaultTableModel) TablaAgregadas.getModel();
        modeloAgregadas.removeRow(TablaAgregadas.getSelectedRow());
        TablaAgregadas.setModel(modeloAgregadas);
        panelEliminar.setBackground(new Color(153,204,255));
                     concentradoProductos();
         Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Venta eliminada");
                                 panel.showNotification();
      }else{
          Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Seleccione una fila antes");
                                 panel.showNotification();
      }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void TablaAgregadasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaAgregadasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaAgregadasKeyPressed

    private void TablaAgregadasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaAgregadasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaAgregadasMouseExited

    private void TablaAgregadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaAgregadasMouseClicked
        if (evt.getClickCount() == 2) {
            int fila = TablaAgregadas.rowAtPoint(evt.getPoint());
            int buscando = Integer.parseInt(TablaAgregadas.getValueAt(fila, 0).toString().substring(1));
            previsualizarVenta(buscando);
            panelEliminar.setBackground(new Color(255,255,255));
         }
    }//GEN-LAST:event_TablaAgregadasMouseClicked

    private void TabaFinalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabaFinalMouseClicked
        if (evt.getClickCount() == 2) {
           
        }
    }//GEN-LAST:event_TabaFinalMouseClicked

    private void TabaFinalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabaFinalMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TabaFinalMouseExited

    private void TabaFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabaFinalKeyPressed

    }//GEN-LAST:event_TabaFinalKeyPressed

    private void TabaFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabaFinalKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_TabaFinalKeyReleased

    private void txtDescuentoFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoFinalKeyReleased

    private void txtSubtotalFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubtotalFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubtotalFinalKeyReleased

    private void txtIvaFInalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIvaFInalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIvaFInalKeyReleased

    private void txtTotalFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalFinalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalFinalKeyReleased

    private void txtSubtotalFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubtotalFinalActionPerformed

    private void txtRfcKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRfcKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){
            
            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtRfcKeyReleased

    private void txtCorreoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtCorreoKeyReleased

    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }    
    }//GEN-LAST:event_txtDireccionKeyReleased

    private void txtCFDIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCFDIKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtCFDIKeyReleased

    private void txtRegimenFiscalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscalKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtRegimenFiscalKeyReleased

    private void txtNombreClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
        
           if(txtNombreCliente.getText().equals("")){
            controladorListaNombres.setVisible(false);
            listaNombres.setVisible(false);
        }else{
            DefaultListModel  model = new DefaultListModel();
            controladorLista.setVisible(true);
            listaNombres.setVisible(true);
            controladorListaNombres.setBounds(controladorListaNombres.getX(), controladorListaNombres.getY(), controladorListaNombres.getWidth(), 85);
            listaNombres.setBounds(listaNombres.getX(), listaNombres.getY(), listaNombres.getWidth(), 85);
                List<Clientes> lista = client.buscarLetra(txtNombreCliente.getText());
                if(lista.size()>0){
                for (int i = 0; i < lista.size(); i++) {
                    if(lista.get(i).getEstatus()==1){
                         if(lista.get(i).getTipoPersona().equals("Persona Física")) model.addElement(lista.get(i).getNombre()+" "+lista.get(i).getApellidoP()+" "+lista.get(i).getApellidoM());
                         else model.addElement(lista.get(i).getNombreComercial());
                    }
                }
                listaNombres.setModel(model);
                 controladorListaNombres.setVisible(true);
                     listaNombres.setVisible(true);
                }else {
                     controladorListaNombres.setVisible(false);
                     listaNombres.setVisible(false);
                }
            
        }
    }//GEN-LAST:event_txtNombreClienteKeyReleased

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void listaNombresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaNombresMouseClicked
                if (evt.getClickCount() == 2) {   
        int k = listaNombres.getSelectedIndex();
                List<Clientes> lista = client.buscarLetra(txtNombreCliente.getText());
                 if(lista.get(k).getTipoPersona().equals("Persona Física")) txtNombreCliente.setText(lista.get(k).getNombre()+" "+lista.get(k).getApellidoP()+" "+lista.get(k).getApellidoM());
                else {
                    txtNombreCliente.setText(lista.get(k).getNombreComercial());
                }
                txtCorreo.setText(lista.get(k).getCorreo());
                
                String direccion ="";
               if(!"".equals(lista.get(k).getCalle())){
                direccion += lista.get(k).getCalle()+" Exterior "+lista.get(k).getNumeroExterior();
                if(!lista.get(k).getNumeroInterior().equals("")) direccion+=" Interior "+lista.get(k).getNumeroInterior();
                direccion += lista.get(k).getColonia()+", "+lista.get(k).getMunicipio()+", "+lista.get(k).getEstado()+", C.P. "+lista.get(k).getCodigoPostal();
               }
                txtDireccion.setText(direccion);
                txtRegimenFiscal.setSelectedItem(lista.get(k).getRegimenFiscal());
                txtRfc.setText(lista.get(k).getRfc());
                txtCFDI.setSelectedItem(lista.get(k).getCfdi());

            listaNombres.setVisible(false);
            controladorListaNombres.setVisible(false);
                }
    }//GEN-LAST:event_listaNombresMouseClicked

    private void txtFormaPago1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormaPago1KeyReleased
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_txtFormaPago1KeyReleased

    private void txtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteActionPerformed

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        if(modeloFinal.getRowCount()>0 && !"".equals(txtNombreCliente.getText()) && !"".equals(txtDireccion.getText()) && !"".equals(txtCorreo.getText()) && !"".equals(txtRfc.getText()) && txtRegimenFiscal.getSelectedIndex()!=0 && txtCFDI.getSelectedIndex()!=0 && txtFormaPago1.getSelectedIndex()!=0){
              Empleados empleado = emple.seleccionarEmpleado("", idEmpleado);
               ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(empleado.getId());
                   cc.setVisible(true);
                   if(cc.contraseñaAceptada==true){
            try {
                Factura factura = new Factura();
                factura.setFecha(corteDao.getDia());
                factura.setNombre(txtNombreCliente.getText());
                factura.setDireccion(txtDireccion.getText());
                factura.setCorreo(txtCorreo.getText());
                factura.setRfc(txtRfc.getText());
                factura.setRegimen(txtRegimenFiscal.getSelectedItem().toString());
                factura.setCfdi(txtCFDI.getSelectedItem().toString());
                factura.setFormaPago(txtFormaPago1.getSelectedItem().toString());
                Number subtotalC = formateador.parse(removefirstChar(txtSubtotalFinal.getText()));
                Number ivaC = formateador.parse(removefirstChar(txtIvaFInal.getText()));
                Number descuentoC = formateador.parse(removefirstChar(txtDescuentoFinal.getText()));
                Number totalC = formateador.parse(removefirstChar(txtTotalFinal.getText()));
                factura.setSubtotal(subtotalC.doubleValue());
                factura.setIva(ivaC.doubleValue());
                factura.setDescuento(descuentoC.doubleValue());
                factura.setTotal(totalC.doubleValue());
                String folioS="";
                for(int i = 0; i<modeloAgregadas.getRowCount();i++){
                    folioS += modeloAgregadas.getValueAt(i, 0)+", ";
                }
                factura.setFolios(folioS);
                facturaDao.registrrFactura(factura);
                registrarDetalle();
                for (int i = 0; i < TablaAgregadas.getRowCount(); i++) {
                    facturaDao.cambiarIndicador(Integer.parseInt(TablaAgregadas.getValueAt(i, 0).toString().substring(1)));
                }
                  Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Acción realizada");
                                 panel.showNotification();
                  bitacora.registrarRegistro("Para facturar con folio "+factura.getId()+" de "+factura.getNombre()+" por $"+formateador.format(factura.getTotal()), idEmpleado, corteDao.getDia());
                  accionCompletada=true;
                  dispose();
            } catch (ParseException ex) {
                Logger.getLogger(CrearModificarConcentradoFactura.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }else{
               Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Rellene todos los campos");
                                 panel.showNotification();
        }
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

    private void btnEliminar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseClicked
        dispose();
    }//GEN-LAST:event_btnEliminar1MouseClicked

    private void btnEliminar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseEntered
        panelEliminar.setBackground(new Color(153,204,255));
    }//GEN-LAST:event_btnEliminar1MouseEntered

    private void btnEliminar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseExited
        panelEliminar.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnEliminar1MouseExited

    private void btnEliminar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar1KeyPressed
        int codigo=evt.getKeyCode();
        switch(codigo){

            case KeyEvent.VK_ESCAPE:
            dispose();
            break;

        }
    }//GEN-LAST:event_btnEliminar1KeyPressed

    private void panelEliminar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar1MouseClicked

    private void panelEliminar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar1MouseEntered

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
            java.util.logging.Logger.getLogger(CrearModificarConcentradoFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearModificarConcentradoFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearModificarConcentradoFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearModificarConcentradoFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CrearModificarConcentradoFactura dialog = new CrearModificarConcentradoFactura(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel LBREGIMEM;
    private javax.swing.JTable TabaFinal;
    private javax.swing.JTable TablaAgregadas;
    private javax.swing.JTable TablaProductos;
    private javax.swing.JTable TableVentas;
    private javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnAgregar;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnEliminar1;
    private javax.swing.JScrollPane controladorLista;
    private javax.swing.JScrollPane controladorListaNombres;
    private com.raven.datechooser.DateChooser jDateChooser1;
    private com.raven.datechooser.DateChooser jDateChooser2;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private textfield.TextField jTextField1;
    private javax.swing.JLabel lbCFDI;
    private javax.swing.JLabel lbCalle;
    private javax.swing.JLabel lbInteres10;
    private javax.swing.JLabel lbInteres11;
    private javax.swing.JLabel lbInteres12;
    private javax.swing.JLabel lbInteres2;
    private javax.swing.JLabel lbInteres3;
    private javax.swing.JLabel lbInteres5;
    private javax.swing.JLabel lbInteres6;
    private javax.swing.JLabel lbInteres7;
    private javax.swing.JLabel lbInteres8;
    private javax.swing.JLabel lbInteres9;
    private javax.swing.JLabel lbNombre1;
    private javax.swing.JLabel lbNombre4;
    private javax.swing.JLabel lbRFC;
    private javax.swing.JList<String> listaNombres;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelAgregar;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelEliminar1;
    private javax.swing.JPanel panelSeleccionNotas;
    private javax.swing.JPanel panelSeleccionNotas1;
    private javax.swing.JPanel panelSeleccionNotas2;
    private javax.swing.JPanel panelSeleccionNotas3;
    private textfield.TextField primeraFecha;
    private textfield.TextField segundaFecha;
    private javax.swing.JScrollPane spProductos;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JScrollPane spTable1;
    private javax.swing.JScrollPane spTableFinal;
    private javax.swing.JComboBox<String> txtCFDI;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDescuentoFinal;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFechaF;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JTextField txtFormaPago;
    private javax.swing.JComboBox<String> txtFormaPago1;
    private javax.swing.JTextField txtIvaFInal;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JComboBox<String> txtRegimenFiscal;
    private javax.swing.JTextField txtRfc;
    private javax.swing.JTextField txtSubtotalFinal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalFinal;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
   }

}