/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javaswingdev.Notification;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Jonathan Gil
 */
public class CrearModificarProveedor extends javax.swing.JDialog {

   ProveedoresDao provee = new ProveedoresDao();
   EmpleadosDao emple = new EmpleadosDao();
   Eventos event = new Eventos();
   bitacoraDao bitacora = new bitacoraDao();


    boolean bandera;
    boolean accionCompletada=false;
    Proveedores original;
    String fecha;
       int idEmpleado;
    
    public void vaciarEmpleado(int idEmpleado){
        this.idEmpleado=idEmpleado;
    }
    

    public CrearModificarProveedor(java.awt.Frame parent, boolean modal) {
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
      txtFolio.setBackground(new Color(240,240,240));
      txtRegimenFiscal.setBackground(new Color(255,255,255));
      txtCFDI.setBackground(new Color(255,255,255));
          listarCFDI();
       listarRegimen();
       //Datos
      
         ((AbstractDocument) txtTelefono.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength + text.length()) - 10;
                if (overLimit > 0) {
                    text = text.substring(0, text.length() - overLimit);
                }
                if (text.length() > 0) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
         
         
           ((AbstractDocument) txtCodigoPostal.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength + text.length()) - 5;
                if (overLimit > 0) {
                    text = text.substring(0, text.length() - overLimit);
                }
                if (text.length() > 0) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
           
           
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
    
    
     public void vaciarDatos(String fecha,Proveedores e, boolean bandera){
        this.bandera=bandera;
        this.original=e;
        this.fecha=fecha;

        if(bandera==true){//crear
            txtFolio.setText(String.valueOf(provee.idProveedor()+1));
            
            Date diaActual = StringADate(fecha);
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar c = Calendar.getInstance();
        c.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
            txtFecha.setText(fechaUsar);
            this.setTitle("Punto de venta - Crear Proveedor");
            selectFisica.setSelected(true);
         txtNombre.setVisible(true);
           txtApellidoPaterno.setVisible(true);
           txtApellidoMaterno.setVisible(true);
           txtRazonSocial.setVisible(false);
           txtTipoSociedad.setEnabled(false);
           txtTipoSociedad.setBackground(new Color(240, 240, 240));
           selectMoral.setSelected(false);
           JTextFieldUtils.configureTextField(txtRfc, false);
           
        }else{ //Modificar
             Date diaActual = StringADate(original.getFecha());
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar c = Calendar.getInstance();
        c.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
           txtFolio.setText(String.valueOf(original.getId()));
           txtFecha.setText(fechaUsar);
           this.setTitle("Punto de venta - Modificar Proveedor");
           vaciarDatos();
        }
    }
     
         public void vaciarDatos(){
        if(original.getTipoPersona().equals("Persona Física")){
            selectFisica.setSelected(true);
         txtNombre.setVisible(true);
           txtApellidoPaterno.setVisible(true);
           txtApellidoMaterno.setVisible(true);
           txtRazonSocial.setVisible(false);
           txtTipoSociedad.setEnabled(false);
           txtTipoSociedad.setBackground(new Color(240, 240, 240));
           selectMoral.setSelected(false);
           JTextFieldUtils.configureTextField(txtRfc, false);
           txtNombre.setText(original.getNombre());
           txtApellidoPaterno.setText(original.getApellidoP());
           txtApellidoMaterno.setText(original.getApellidoM());    
        }else{ //moral
            selectMoral.setSelected(true);
         txtNombre.setVisible(false);
           txtApellidoPaterno.setVisible(false);
           txtApellidoMaterno.setVisible(false);
           txtRazonSocial.setVisible(true);
           txtTipoSociedad.setEnabled(true);
           txtTipoSociedad.setBackground(new Color(255, 255, 255));
            selectFisica.setSelected(false);
           JTextFieldUtils.configureTextField(txtRfc, true);
           txtRazonSocial.setText(original.getNombreComercial());
           txtTipoSociedad.setText(original.getTipoSociedad());
        }
        txtRfc.setText(original.getRfc());
        txtTelefono.setText(original.getTelefono());
        txtRegimenFiscal.setSelectedItem(original.getRegimenFiscal());
        txtCFDI.setSelectedItem(original.getCfdi());
        txtCorreo.setText(original.getCorreo());
        txtCalle.setText(original.getCalle());
        txtCodigoPostal.setText(original.getCodigoPostal());
        txtInterior.setText(original.getNumeroInterior());
        txtExterior.setText(original.getNumeroExterior());
        txtMunicipio.setText(original.getMunicipio());
        txtColonia.setText(original.getColonia());
        txtEstado.setText(original.getEstado());
        Empleados empl = emple.BuscarPorCodigo(original.getIdRecibe());
          if(!txtRfc.getText().equals("")){
            lbRFC.setText("RFC*");
            LBREGIMEM.setText("Régimen*");
            lbCFDI.setText("CFDI*");
        }else{
          lbRFC.setText("RFC");
          LBREGIMEM.setText("Régimen");
          lbCFDI.setText("CFDI");
        }
            if(!txtCalle.getText().equals("")){
            lbCalle.setText("Calle*");
            lbInterior.setText("N.interior*");
            lbColonia.setText("Colonia*");
            lbEstado.setText("Estado*");
            lbCP.setText("C.P.*");
            lbMunicipio.setText("Municipio*");
        }else{
            lbCalle.setText("Calle");
            lbInterior.setText("N.interior");
            lbColonia.setText("Colonia");
            lbEstado.setText("Estado");
            lbCP.setText("C.P.");
            lbMunicipio.setText("Municipio");
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
        boolean datosNombre, fiscalRellenos, direccionRelleno, direccionDatos, fiscalDatos, contraseñaA, rfcCompleto;
        datosNombre = fiscalRellenos  = direccionRelleno = direccionDatos = fiscalDatos =contraseñaA=rfcCompleto=false;
        Empleados empleado = emple.seleccionarEmpleado("",idEmpleado);
        ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                cc.vaciarEmpleado(empleado.getId());
                   cc.setVisible(true);
                   if(cc.contraseñaAceptada==true){
             contraseñaA=true;
        }
        
      
        
        if(selectMoral.isSelected()){
            if(!"".equals(txtRazonSocial.getText()) && !"".equals(txtTipoSociedad.getText())) {
                datosNombre=true;
            }    
        }else{
          if(!"".equals(txtNombre.getText()) && !"".equals(txtApellidoPaterno.getText()) && !"".equals(txtApellidoMaterno.getText())) {
               datosNombre=true;
          }
        }
        
        if(!"".equals(txtCalle.getText())){
        if(!"".equals(txtEstado.getText()) && !"".equals(txtColonia.getText()) &&!"".equals(txtCalle.getText()) && !"".equals(txtCodigoPostal.getText()) && !"".equals(txtInterior.getText()) && !"".equals(txtMunicipio.getText())){
            direccionDatos=true;
            direccionRelleno=true;
        }
        }
        
        if("".equals(txtCalle.getText())){
        if("".equals(txtEstado.getText()) && "".equals(txtColonia.getText()) &&"".equals(txtCalle.getText()) && "".equals(txtCodigoPostal.getText()) && "".equals(txtInterior.getText()) && "".equals(txtMunicipio.getText()) && "".equals(txtCalle.getText())){
                        direccionDatos=true;
        }
          }
        
        if(!"".equals(txtRfc.getText())){
        if(txtCFDI.getSelectedIndex()!=0 && txtRegimenFiscal.getSelectedIndex()!=0){
             fiscalDatos=true;
             fiscalRellenos=true;
        }}
        
                if("".equals(txtRfc.getText())){
        if(txtCFDI.getSelectedIndex()==0 && txtRegimenFiscal.getSelectedIndex()==0){
            fiscalDatos=true;
        }}
        
         
         if(selectMoral.isSelected()){
            if("".equals(txtRfc.getText()) || txtRfc.getText().length()==12) {
                rfcCompleto=true;
            }    
        }else{
            if("".equals(txtRfc.getText()) || txtRfc.getText().length()==13) {
               rfcCompleto=true;
          }
        }
         
         
         if(datosNombre==false ||direccionDatos==false || fiscalDatos==false || contraseñaA==false || rfcCompleto==false){
             if(datosNombre==false){
                   Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Rellene los espacios de nombre del cliente");
                                 panel.showNotification();
             }
             if(direccionDatos==false){
                   Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Complete los datos de dirección del cliente");
                                 panel.showNotification();
             }
             if(fiscalDatos==false){
                   Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Complete los datos fiscales del cliente");
                                 panel.showNotification();
             }

             if(contraseñaA==false){
                   Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Contraseña incorrecta");
                                 panel.showNotification();
             }
             if(rfcCompleto==false){
                   Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "RFC incompleto");
                                 panel.showNotification();
             }
         }else{
              Proveedores c = new Proveedores();
              if(selectMoral.isSelected()) c.setTipoPersona("Persona Moral");
              else c.setTipoPersona("Persona Física");
              c.setNombre(modificarString(txtNombre.getText().trim()));
              c.setApellidoP(modificarString(txtApellidoPaterno.getText().trim()));
              c.setApellidoM(modificarString(txtApellidoMaterno.getText().trim()));
              c.setNombreComercial(txtRazonSocial.getText().trim());
              c.setTipoSociedad(txtTipoSociedad.getText().trim());
              if(rfcCompleto==true && fiscalDatos==true){
                  c.setRfc(txtRfc.getText());
                  c.setRegimenFiscal(txtRegimenFiscal.getSelectedItem().toString());
                  c.setCfdi(txtCFDI.getSelectedItem().toString());
              }
              c.setTelefono(txtTelefono.getText());
              c.setCorreo(txtCorreo.getText().trim());
              if(direccionDatos==true ){
                  c.setCalle(txtCalle.getText());
                  c.setCodigoPostal(txtCodigoPostal.getText());
                  c.setNumeroInterior(txtInterior.getText());
                  c.setNumeroExterior(txtExterior.getText());
                  c.setMunicipio(txtMunicipio.getText());
                  c.setColonia(txtColonia.getText());
                  c.setEstado(txtEstado.getText());
              }
             c.setIdRecibe(empleado.getId());
             c.setFecha(fecha);
             
                if(bandera==true){//crear cliente 
                    if(c.getTipoPersona().equals("Persona Física")){
                       if (provee.ProveedorRepetido(c) == true) {
         Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Proveedor repetido");
                                 panel.showNotification();
                        } else {
                            provee.RegistrarProveedor(c);
                            accionCompletada=true;
                            bitacora.registrarRegistro("Nuevo Proveedor: "+c.getNombre(), empleado.getId(),fecha);
                            dispose();
                            }
                    }else{
                           if (provee.ProveedorRepetidoMoral(c) == true) {
         Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Proveedor repetido");
                                 panel.showNotification();
                        } else {
                            provee.RegistrarProveedor(c);
                            accionCompletada=true;
                            bitacora.registrarRegistro("Nuevo Proveedor: "+c.getNombre(), empleado.getId(),fecha);
                            dispose();
                            }
                    }
                 
                }else{//actualizar cliente
                    c.setId(Integer.parseInt(txtFolio.getText()));
                      if(c.getTipoPersona().equals("Persona Física")){
                                if (provee.ProveedorRepetidaActualizar(c) == true) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Proveedor repetido");
                                 panel.showNotification();
                            } else {
                                provee.ModificarProveedor(c);
                                accionCompletada=true;
                                   if(!original.getTelefono().equals(c.getTelefono())){
                                    bitacora.registrarRegistro("Se modificó el número de contacto del Proveedor "+original.getNombre(),empleado.getId(),fecha);
                                    }
                                   if(!original.getCorreo().equals(c.getCorreo())){
                                    bitacora.registrarRegistro("Se modificó el correo de contacto del Proveedor "+original.getNombre(),empleado.getId(),fecha);
                                    }
                                     if(!original.getNombre().equals(c.getNombre())){
                                              bitacora.registrarRegistro("Se modificó el nombre del Proveedor "+original.getNombre()+" a "+c.getNombre(),empleado.getId(),fecha);
                                      }
                                dispose();
                                    }
                      }else{
                                if (provee.ProveedorRepetidaActualizarMoral(c) == true) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Proveedor repetido");
                                 panel.showNotification();
                            } else {
                                provee.ModificarProveedor(c);
                                accionCompletada=true;
                                   if(!original.getTelefono().equals(c.getTelefono())){
                                    bitacora.registrarRegistro("Se modificó el número de contacto del Proveedor "+original.getNombre(),empleado.getId(),fecha);
                                    }
                                   if(!original.getCorreo().equals(c.getCorreo())){
                                    bitacora.registrarRegistro("Se modificó el correo de contacto del Proveedor "+original.getNombre(),empleado.getId(),fecha);
                                    }
                                     if(!original.getNombre().equals(c.getNombre())){
                                              bitacora.registrarRegistro("Se modificó el nombre del Proveedor "+original.getNombre()+" a "+c.getNombre(),empleado.getId(),fecha);
                                      }
                                dispose();
                                    }
                      }
              
                                }                           

                        }
                        }
                
     
    public void  eventoF1Datos(){
         txtRegimenFiscal.setSelectedIndex(0);
         txtCFDI.setSelectedIndex(0);
         txtNombre.setText("");
       txtApellidoPaterno.setText("");
       txtApellidoMaterno.setText("");
       txtRazonSocial.setText("");
       txtRfc.setText("");
       txtTipoSociedad.setText("");
       txtTelefono.setText("");
       txtCorreo.setText("");
      txtCalle.setText("");
     txtCodigoPostal.setText("");
      txtInterior.setText("");
      txtExterior.setText("");
      txtMunicipio.setText("");
      txtColonia.setText("");
      txtEstado.setText("");
       lbCalle.setText("Calle");
            lbInterior.setText("N. interior");
            lbColonia.setText("Colonia");
            lbEstado.setText("Estado");
            lbCP.setText("C.P.");
            lbMunicipio.setText("Municipio");
            lbRFC.setText("RFC");
          LBREGIMEM.setText("Régimen");
          lbCFDI.setText("CFDI");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDatos = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        panelAceptar = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JLabel();
        panelLimpiar = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JLabel();
        panelEliminar = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JLabel();
        selectFisica = new javax.swing.JCheckBox();
        selectMoral = new javax.swing.JCheckBox();
        txtFolio = new javax.swing.JTextField();
        txtApellidoMaterno = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtTipoSociedad = new javax.swing.JTextField();
        txtApellidoPaterno = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtRfc = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtCalle = new javax.swing.JTextField();
        txtCodigoPostal = new javax.swing.JTextField();
        txtExterior = new javax.swing.JTextField();
        txtMunicipio = new javax.swing.JTextField();
        txtInterior = new javax.swing.JTextField();
        txtColonia = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        txtRegimenFiscal = new javax.swing.JComboBox<>();
        txtCFDI = new javax.swing.JComboBox<>();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lbNombre = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        LBApellidoPaterno = new javax.swing.JLabel();
        LBApellidoPaterno1 = new javax.swing.JLabel();
        lbNombre1 = new javax.swing.JLabel();
        lbRFC = new javax.swing.JLabel();
        lbNombre4 = new javax.swing.JLabel();
        lbEstado = new javax.swing.JLabel();
        lbCalle = new javax.swing.JLabel();
        lbColonia = new javax.swing.JLabel();
        lbSociedad = new javax.swing.JLabel();
        LBREGIMEM = new javax.swing.JLabel();
        lbCFDI = new javax.swing.JLabel();
        lbNombre11 = new javax.swing.JLabel();
        lbCP = new javax.swing.JLabel();
        lbInterior = new javax.swing.JLabel();
        lbMunicipio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        panelDatos.setBackground(new java.awt.Color(255, 255, 255));
        panelDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setBackground(new java.awt.Color(0, 0, 204));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("¿Está seguro de ejecutar esta acción? ");
        panelDatos.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, -1, -1));

        jLabel25.setBackground(new java.awt.Color(0, 0, 204));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Datos del Proveedor");
        panelDatos.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 240, -1));

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
        btnAceptar.setToolTipText("ENTER - Guardar datos del proveedor");
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

        panelDatos.add(panelAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 50, 50));

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

        panelDatos.add(panelLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 50, 50));

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

        panelDatos.add(panelEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, 50, 50));

        selectFisica.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        selectFisica.setText("Persona Física");
        selectFisica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectFisicaMouseClicked(evt);
            }
        });
        selectFisica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                selectFisicaKeyReleased(evt);
            }
        });
        panelDatos.add(selectFisica, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, -1, -1));

        selectMoral.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        selectMoral.setText("Persona Moral");
        selectMoral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectMoralMouseClicked(evt);
            }
        });
        selectMoral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                selectMoralKeyReleased(evt);
            }
        });
        panelDatos.add(selectMoral, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        txtFolio.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFolio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFolio.setEnabled(false);
        panelDatos.add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 50, 90, -1));

        txtApellidoMaterno.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtApellidoMaterno.setToolTipText("Apellido Materno del cliente");
        txtApellidoMaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoKeyTyped(evt);
            }
        });
        panelDatos.add(txtApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 90, 110, -1));

        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        panelDatos.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 50, 90, -1));

        txtTipoSociedad.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTipoSociedad.setToolTipText("Tipo de sociedad");
        txtTipoSociedad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTipoSociedad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTipoSociedadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTipoSociedadKeyTyped(evt);
            }
        });
        panelDatos.add(txtTipoSociedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 120, 230, -1));

        txtApellidoPaterno.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtApellidoPaterno.setToolTipText("Apellido Paterno del cliente");
        txtApellidoPaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoKeyTyped(evt);
            }
        });
        panelDatos.add(txtApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 90, 110, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombre.setToolTipText("Nombre del cliente");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        panelDatos.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 170, -1));

        txtRfc.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtRfc.setToolTipText("Introduzca el RFC");
        txtRfc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRfcKeyReleased(evt);
            }
        });
        panelDatos.add(txtRfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 220, -1));

        txtTelefono.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTelefono.setToolTipText("Teléfono (10 digítos)");
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        panelDatos.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 220, -1));

        txtCorreo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCorreo.setToolTipText("Correo eléctronico");
        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoKeyReleased(evt);
            }
        });
        panelDatos.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 220, -1));

        txtCalle.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCalle.setToolTipText("Calle");
        txtCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCalleKeyReleased(evt);
            }
        });
        panelDatos.add(txtCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 220, -1));

        txtCodigoPostal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCodigoPostal.setToolTipText("Código postal");
        txtCodigoPostal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoPostalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalKeyTyped(evt);
            }
        });
        panelDatos.add(txtCodigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 240, 230, -1));

        txtExterior.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtExterior.setToolTipText("Número exteior");
        txtExterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExteriorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExteriorKeyTyped(evt);
            }
        });
        panelDatos.add(txtExterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(493, 210, 80, -1));

        txtMunicipio.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtMunicipio.setToolTipText("Municipio");
        txtMunicipio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMunicipioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMunicipioKeyTyped(evt);
            }
        });
        panelDatos.add(txtMunicipio, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 270, 230, -1));

        txtInterior.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInterior.setToolTipText("Número interior");
        txtInterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteriorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteriorKeyTyped(evt);
            }
        });
        panelDatos.add(txtInterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 210, 80, -1));

        txtColonia.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtColonia.setToolTipText("Colonia");
        txtColonia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtColoniaKeyReleased(evt);
            }
        });
        panelDatos.add(txtColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 220, -1));

        txtEstado.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtEstado.setToolTipText("Estado");
        txtEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEstadoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoKeyTyped(evt);
            }
        });
        panelDatos.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 220, -1));

        txtRegimenFiscal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Régimen Fiscal", "Item 2", "Item 3", "Item 4" }));
        txtRegimenFiscal.setToolTipText("Régimen Fiscal");
        txtRegimenFiscal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRegimenFiscalKeyReleased(evt);
            }
        });
        panelDatos.add(txtRegimenFiscal, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 150, 230, -1));

        txtCFDI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Uso de CFDI", "Item 2", "Item 3", "Item 4" }));
        txtCFDI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCFDIKeyReleased(evt);
            }
        });
        panelDatos.add(txtCFDI, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 180, 230, -1));

        txtRazonSocial.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtRazonSocial.setToolTipText("Nombre comercial de la empresa");
        txtRazonSocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRazonSocialKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonSocialKeyTyped(evt);
            }
        });
        panelDatos.add(txtRazonSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 520, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Código");
        panelDatos.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 55, 40, -1));

        lbNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre.setText("Teléfono");
        panelDatos.add(lbNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 153, 55, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Fecha");
        panelDatos.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, 40, -1));

        LBApellidoPaterno.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno.setText("Paterno*");
        panelDatos.add(LBApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 95, 55, -1));

        LBApellidoPaterno1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno1.setText("Materno*");
        panelDatos.add(LBApellidoPaterno1, new org.netbeans.lib.awtextra.AbsoluteConstraints(409, 95, 55, -1));

        lbNombre1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre1.setText("Nombre*");
        panelDatos.add(lbNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 95, 55, -1));

        lbRFC.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbRFC.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbRFC.setText("RFC");
        panelDatos.add(lbRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 123, 55, -1));

        lbNombre4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre4.setText("Correo");
        panelDatos.add(lbNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 183, 55, -1));

        lbEstado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbEstado.setText("Estado");
        panelDatos.add(lbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 273, 55, -1));

        lbCalle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbCalle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCalle.setText("Calle");
        panelDatos.add(lbCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 213, 55, -1));

        lbColonia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbColonia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbColonia.setText("Colonia");
        panelDatos.add(lbColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 243, 55, -1));

        lbSociedad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbSociedad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbSociedad.setText("Sociedad");
        panelDatos.add(lbSociedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 123, 58, -1));

        LBREGIMEM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBREGIMEM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBREGIMEM.setText("Régimen");
        panelDatos.add(LBREGIMEM, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 153, 58, -1));

        lbCFDI.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbCFDI.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCFDI.setText("CFDI");
        panelDatos.add(lbCFDI, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 183, 58, -1));

        lbNombre11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre11.setText("N. Interior");
        panelDatos.add(lbNombre11, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 213, 60, -1));

        lbCP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbCP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCP.setText("C.P.");
        panelDatos.add(lbCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 243, 58, -1));

        lbInterior.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbInterior.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInterior.setText("N.Exterior");
        panelDatos.add(lbInterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 213, 60, -1));

        lbMunicipio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbMunicipio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbMunicipio.setText("Municipio");
        panelDatos.add(lbMunicipio, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 273, 58, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void selectFisicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectFisicaMouseClicked
        if(selectFisica.isSelected()){
            lbSociedad.setText("Sociedad");
            txtNombre.setVisible(true);
            txtApellidoPaterno.setVisible(true);
            txtApellidoMaterno.setVisible(true);
            txtRazonSocial.setVisible(false);
            txtTipoSociedad.setEnabled(false);
            txtTipoSociedad.setBackground(new Color(240, 240, 240));
            selectMoral.setSelected(false);
            JTextFieldUtils.configureTextField(txtRfc, false);
        }else{
            lbSociedad.setText("Sociedad*");
            selectMoral.setSelected(true);
            txtNombre.setVisible(false);
            txtApellidoPaterno.setVisible(false);
            txtApellidoMaterno.setVisible(false);
            txtRazonSocial.setVisible(true);
            txtTipoSociedad.setEnabled(true);
            txtTipoSociedad.setBackground(new Color(255, 250, 250));
            selectFisica.setSelected(false);
            JTextFieldUtils.configureTextField(txtRfc, true);
        }
    }//GEN-LAST:event_selectFisicaMouseClicked

    private void selectFisicaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selectFisicaKeyReleased
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
    }//GEN-LAST:event_selectFisicaKeyReleased

    private void selectMoralMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectMoralMouseClicked
        if(selectMoral.isSelected()){
            lbSociedad.setText("Sociedad*");
            txtNombre.setVisible(false);
            txtApellidoPaterno.setVisible(false);
            txtApellidoMaterno.setVisible(false);
            txtRazonSocial.setVisible(true);
            txtTipoSociedad.setEnabled(true);
            txtTipoSociedad.setBackground(new Color(255, 250, 250));
            selectFisica.setSelected(false);
            JTextFieldUtils.configureTextField(txtRfc, true);
        }else{
            lbSociedad.setText("Sociedad");
            selectFisica.setSelected(true);
            txtNombre.setVisible(true);
            txtApellidoPaterno.setVisible(true);
            txtApellidoMaterno.setVisible(true);
            txtRazonSocial.setVisible(false);
            txtTipoSociedad.setEnabled(false);
            txtTipoSociedad.setBackground(new Color(240, 240, 240));
            selectMoral.setSelected(false);
            JTextFieldUtils.configureTextField(txtRfc, false);

        }
    }//GEN-LAST:event_selectMoralMouseClicked

    private void selectMoralKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selectMoralKeyReleased
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
    }//GEN-LAST:event_selectMoralKeyReleased

    private void txtApellidoMaternoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoKeyReleased
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
    }//GEN-LAST:event_txtApellidoMaternoKeyReleased

    private void txtApellidoMaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtApellidoMaternoKeyTyped

    private void txtTipoSociedadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoSociedadKeyReleased
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
    }//GEN-LAST:event_txtTipoSociedadKeyReleased

    private void txtTipoSociedadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoSociedadKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtTipoSociedadKeyTyped

    private void txtApellidoPaternoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoKeyReleased
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
    }//GEN-LAST:event_txtApellidoPaternoKeyReleased

    private void txtApellidoPaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtApellidoPaternoKeyTyped

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
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
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtRfcKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRfcKeyReleased
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
        if(!txtRfc.getText().equals("")){
            lbRFC.setText("RFC*");
            LBREGIMEM.setText("Régimen*");
            lbCFDI.setText("CFDI*");
        }else{
          lbRFC.setText("RFC");
          LBREGIMEM.setText("Régimen");
          lbCFDI.setText("CFDI");
        }
    }//GEN-LAST:event_txtRfcKeyReleased

    private void txtTelefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyReleased
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
    }//GEN-LAST:event_txtTelefonoKeyReleased

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCorreoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyReleased
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
    }//GEN-LAST:event_txtCorreoKeyReleased

    private void txtCalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyReleased
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
        
        if(!txtCalle.getText().equals("")){
            lbCalle.setText("Calle*");
            lbInterior.setText("N.Exterior*");
            lbColonia.setText("Colonia*");
            lbEstado.setText("Estado*");
            lbCP.setText("C.P.*");
            lbMunicipio.setText("Municip*");
        }else{
            lbCalle.setText("Calle");
            lbInterior.setText("N.Exterior");
            lbColonia.setText("Colonia");
            lbEstado.setText("Estado");
            lbCP.setText("C.P.");
            lbMunicipio.setText("Municipio");
        }
    }//GEN-LAST:event_txtCalleKeyReleased

    private void txtCodigoPostalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalKeyReleased
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
    }//GEN-LAST:event_txtCodigoPostalKeyReleased

    private void txtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoPostalKeyTyped

    private void txtExteriorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorKeyReleased
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
    }//GEN-LAST:event_txtExteriorKeyReleased

    private void txtMunicipioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioKeyReleased
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
    }//GEN-LAST:event_txtMunicipioKeyReleased

    private void txtMunicipioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtMunicipioKeyTyped

    private void txtInteriorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorKeyReleased
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
    }//GEN-LAST:event_txtInteriorKeyReleased

    private void txtColoniaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaKeyReleased
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
    }//GEN-LAST:event_txtColoniaKeyReleased

    private void txtEstadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoKeyReleased
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
    }//GEN-LAST:event_txtEstadoKeyReleased

    private void txtEstadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtEstadoKeyTyped

    private void txtRegimenFiscalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscalKeyReleased
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
    }//GEN-LAST:event_txtRegimenFiscalKeyReleased

    private void txtCFDIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCFDIKeyReleased
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
    }//GEN-LAST:event_txtCFDIKeyReleased

    private void txtRazonSocialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocialKeyReleased
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
    }//GEN-LAST:event_txtRazonSocialKeyReleased

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtRazonSocialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocialKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocialKeyTyped

    private void txtInteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtInteriorKeyTyped

    private void txtExteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorKeyTyped
         event.numberKeyPress(evt);
    }//GEN-LAST:event_txtExteriorKeyTyped

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
            java.util.logging.Logger.getLogger(CrearModificarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearModificarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearModificarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearModificarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CrearModificarProveedor dialog = new CrearModificarProveedor(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel LBApellidoPaterno;
    private javax.swing.JLabel LBApellidoPaterno1;
    private javax.swing.JLabel LBREGIMEM;
    private javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnLimpiar;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel lbCFDI;
    private javax.swing.JLabel lbCP;
    private javax.swing.JLabel lbCalle;
    private javax.swing.JLabel lbColonia;
    private javax.swing.JLabel lbEstado;
    private javax.swing.JLabel lbInterior;
    private javax.swing.JLabel lbMunicipio;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbNombre1;
    private javax.swing.JLabel lbNombre11;
    private javax.swing.JLabel lbNombre4;
    private javax.swing.JLabel lbRFC;
    private javax.swing.JLabel lbSociedad;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelLimpiar;
    private javax.swing.JCheckBox selectFisica;
    private javax.swing.JCheckBox selectMoral;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JComboBox<String> txtCFDI;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtExterior;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JTextField txtInterior;
    private javax.swing.JTextField txtMunicipio;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JComboBox<String> txtRegimenFiscal;
    private javax.swing.JTextField txtRfc;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoSociedad;
    // End of variables declaration//GEN-END:variables

private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
   }
}




