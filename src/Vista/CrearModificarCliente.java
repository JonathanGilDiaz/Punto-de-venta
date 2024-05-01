/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Clientes;
import Modelo.ClienteDao;
import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.Eventos;
import Modelo.ReferenciasCliente;
import Modelo.ReferenciasClienteDao;
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
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Jonathan Gil
 */
public class CrearModificarCliente extends javax.swing.JDialog {

    Clientes original;
    boolean bandera;
    boolean accionCompletada = false;
    ClienteDao client = new ClienteDao();
    Eventos event = new Eventos();
    EmpleadosDao emple = new EmpleadosDao();
    CreditoDao creditoDao = new CreditoDao();
    String fecha, fechaCliente, fechaCredito;
    int idCliente, idCredito;
    bitacoraDao bitacora = new bitacoraDao();
    ReferenciasClienteDao referenciaDao = new ReferenciasClienteDao();

    DecimalFormat formato = new DecimalFormat("#0.00");
    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    int idEmpleado;

    public void vaciarEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public CrearModificarCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        txtCFDI.setBackground(new Color(255, 255, 255));
        txtRegimenFiscal.setBackground(new Color(255, 255, 255));
        Seticon();
        txtFecha.setBackground(new Color(240, 240, 240));
        txtFolio.setBackground(new Color(240, 240, 240));
        txtFechaCrediito.setBackground(new Color(240, 240, 240));
        txtFolioCredito.setBackground(new Color(240, 240, 240));
        listarCFDI();
        listarRegimen();
        //Datos
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                Point currentLocation = getLocation();
                if (currentLocation.y < 50) {
                    setLocationRelativeTo(null);
                }

            }
        });

        ((AbstractDocument) txtTelefono.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
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

        ((AbstractDocument) txttelefonoCredito.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
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

        ((AbstractDocument) txttelefonoReferencia1.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ((AbstractDocument) txttelefonoReferencia2.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ((AbstractDocument) txttelefonoReferencia3.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ((AbstractDocument) txttelefonoReferencia4.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ((AbstractDocument) txtCodigoPostalCredito.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ((AbstractDocument) txtCodigoPostalReferencia1.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ((AbstractDocument) txtCodigoPostalReferencia2.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ((AbstractDocument) txtCodigoPostalReferencia3.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ((AbstractDocument) txtCodigoPostalReferencia4.getDocument()).setDocumentFilter(new DocumentFilter() {
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

    public void listarCFDI() {
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

    public void listarRegimen() {
        txtRegimenFiscal.removeAllItems();
        txtRegimenFiscal.addItem("Régimen Fiscal");
        txtRegimenFiscal.addItem("601 General de Ley Personas Morales");
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

    public void vaciarDatos(String fecha, Clientes c, boolean bandera) {
        this.bandera = bandera;
        this.original = c;
        this.fecha = fecha;
        if (bandera == true) {//crear
            txtFolio.setText(String.valueOf(client.idCliente() + 1));
            txtFecha.setText(fechaCorrecta(fecha));
            this.setTitle("Punto de venta - Crear cliente");
            selectFisica.setSelected(true);
            txtNombre.setVisible(true);
            txtApellidoPaterno.setVisible(true);
            txtApellidoMaterno.setVisible(true);
            txtRazonSocial.setVisible(false);
            txtTipoSociedad.setEnabled(false);
            txtTipoSociedad.setBackground(new Color(240, 240, 240));
            selectMoral.setSelected(false);
            JTextFieldUtils.configureTextField(txtRfc, false);
            txtFolioCredito.setText((creditoDao.idCredito() + 1) + "");
            txtFechaCrediito.setText(fechaCorrecta(fecha));

        } else { //Modificar
            this.idCliente = c.getId();
            txtFolio.setText(String.valueOf(original.getId()));
            txtFecha.setText(fechaCorrecta(original.getFechaCreacion()));
            this.setTitle("Punto de venta - Modificar cliente");
            vaciarDatos();
        }
    }

    public String fechaCorrecta(String fechaMala) {
        Date diaActual = StringADate(fechaMala);
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar ca = Calendar.getInstance();
        ca.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
        return fechaUsar;
    }

    public void vaciarDatos() {
        if (original.getTipoPersona().equals("Persona Física")) {
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
        } else { //moral
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
        if (!"".equals(original.getRegimenFiscal())) {
            txtRegimenFiscal.setSelectedItem(original.getRegimenFiscal());
        }
        if (!"".equals(original.getCfdi())) {
            txtCFDI.setSelectedItem(original.getCfdi());
        }
        txtCorreo.setText(original.getCorreo());
        txtCalle.setText(original.getCalle());
        txtCodigoPostal.setText(original.getCodigoPostal());
        txtInterior.setText(original.getNumeroInterior());
        txtExterior.setText(original.getNumeroExterior());
        txtMunicipio.setText(original.getMunicipio());
        txtColonia.setText(original.getColonia());
        txtEstado.setText(original.getEstado());
        Empleados empl = emple.BuscarPorCodigo(original.getIdRecibe());

        //datos de credito
        Credito credito = creditoDao.BuscarPorCodigoCliente(original.getId());
        if (credito.getId() != 0) {
            txtFolioCredito.setText(credito.getId() + "");
            txtFechaCrediito.setText(fechaCorrecta(credito.getFecha()));
            txtNombreCredito.setText(credito.getNombre());
            txtApellidoPaternoCredito.setText(credito.getApellidoP());
            txtApellidoMaternoCredito.setText(credito.getApellidoM());
            txtCalleCredito.setText(credito.getCalle());
            txtCodigoPostalCredito.setText(credito.getCodigoPostal());
            txtInteriorCredito.setText(credito.getNumeroInterior());
            txtExteriorCredito.setText(credito.getNumeroExterior());
            txtMunicipioCredito.setText(credito.getMunicipio());
            txtColoniaCredito.setText(credito.getColonia());
            txtEstadoCredito.setText(credito.getEstado());
            txtCorreoCredito.setText(credito.getCorreo());
            txttelefonoCredito.setText(credito.getTelefono());
            txtGiroOcupacionCredito.setText(credito.getGiroOcupacion());
            txtAntiguedad.setText(credito.getAntiguedad());
            txtVigenciaCredito.setText(String.valueOf(credito.getVigencia()));
            txtAcredatario.setText(credito.getAcreditado());
            txtPlazoCredito.setText(String.valueOf(credito.getPlazo()));
            txtDocumentoAcreditacion.setText(credito.getDocumentoAcreditacion());
            txtLimiteCredito.setText(formato.format(credito.getLimite()));
            txtInteresMoratorio.setText(formato.format(credito.getInteresMoratorio()));
            txtInteresOrdinario.setText(formato.format(credito.getInteresOrdinario()));
        } else {
            txtFolioCredito.setText("Folio: " + (creditoDao.idCredito() + 1));
            txtFechaCrediito.setText("Fecha: " + fechaCorrecta(fecha));
        }

        //referencias
        ReferenciasCliente referencia1 = referenciaDao.BuscarPorCodigoClienteTipo(original.getId(), "Referencia personal 1");
        txtNombreReferencia1.setText(referencia1.getNombre());
        txtApellidoPaternoReferencia1.setText(referencia1.getApellidoP());
        txtApellidoMaternoReferencia1.setText(referencia1.getApellidoM());
        txtCalleReferencia1.setText(referencia1.getCalle());
        txtCodigoPostalReferencia1.setText(referencia1.getCodigoPostal());
        txtInteriorReferencia1.setText(referencia1.getNumeroInterior());
        txtExteriorReferencia1.setText(referencia1.getNumeroExterior());
        txtMunicipioReferencia1.setText(referencia1.getMunicipio());
        txtColoniaReferencia1.setText(referencia1.getColonia());
        txtEstadoReferencia1.setText(referencia1.getEstado());
        txtGiroOcupacionReferencia1.setText(referencia1.getGiroOcupacional());
        txtAntiguedadReferencia1.setText(referencia1.getAntiguedad());
        txtCorreoReferencia1.setText(referencia1.getCorreo());
        txttelefonoReferencia1.setText(referencia1.getTelefono());

        ReferenciasCliente referencia2 = referenciaDao.BuscarPorCodigoClienteTipo(original.getId(), "Referencia personal 2");
        txtNombreReferencia2.setText(referencia2.getNombre());
        txtApellidoPaternoReferencia2.setText(referencia2.getApellidoP());
        txtApellidoMaternoReferencia2.setText(referencia2.getApellidoM());
        txtCalleReferencia2.setText(referencia2.getCalle());
        txtCodigoPostalReferencia2.setText(referencia2.getCodigoPostal());
        txtInteriorReferencia2.setText(referencia2.getNumeroInterior());
        txtExteriorReferencia2.setText(referencia2.getNumeroExterior());
        txtMunicipioReferencia2.setText(referencia2.getMunicipio());
        txtColoniaReferencia2.setText(referencia2.getColonia());
        txtEstadoReferencia2.setText(referencia2.getEstado());
        txtGiroOcupacionReferencia2.setText(referencia2.getGiroOcupacional());
        txtAntiguedadReferencia2.setText(referencia2.getAntiguedad());
        txtCorreoReferencia2.setText(referencia2.getCorreo());
        txttelefonoReferencia2.setText(referencia2.getTelefono());

        ReferenciasCliente referencia3 = referenciaDao.BuscarPorCodigoClienteTipo(original.getId(), "Referencia comercial 1");
        txtNombreComercial3.setText(referencia3.getNombreComercial());
        txtCalleReferencia3.setText(referencia3.getCalle());
        txtCodigoPostalReferencia3.setText(referencia3.getCodigoPostal());
        txtInteriorReferencia3.setText(referencia3.getNumeroInterior());
        txtExteriorReferencia3.setText(referencia3.getNumeroExterior());
        txtMunicipioReferencia3.setText(referencia3.getMunicipio());
        txtColoniaReferencia3.setText(referencia3.getColonia());
        txtEstadoReferencia3.setText(referencia3.getEstado());
        txtGiroOcupacionReferencia3.setText(referencia3.getGiroOcupacional());
        txtAntiguedadReferencia3.setText(referencia3.getAntiguedad());
        txtCorreoReferencia3.setText(referencia3.getCorreo());
        txttelefonoReferencia3.setText(referencia3.getTelefono());

        ReferenciasCliente referencia4 = referenciaDao.BuscarPorCodigoClienteTipo(original.getId(), "Referencia comercial 2");
        txtNombreComercial4.setText(referencia4.getNombreComercial());
        txtCalleReferencia4.setText(referencia4.getCalle());
        txtCodigoPostalReferencia4.setText(referencia4.getCodigoPostal());
        txtInteriorReferencia4.setText(referencia4.getNumeroInterior());
        txtExteriorReferencia4.setText(referencia4.getNumeroExterior());
        txtMunicipioReferencia4.setText(referencia4.getMunicipio());
        txtColoniaReferencia4.setText(referencia4.getColonia());
        txtEstadoReferencia4.setText(referencia4.getEstado());
        txtGiroOcupacionReferencia4.setText(referencia4.getGiroOcupacional());
        txtAntiguedadReferencia4.setText(referencia4.getAntiguedad());
        txtCorreoReferencia4.setText(referencia4.getCorreo());
        txttelefonoReferencia4.setText(referencia4.getTelefono());

    }

    public void eventoEnter() {
        boolean datosNombre, creditoRellenos, fiscalRellenos, direccionRelleno, direccionDatos, fiscalDatos, datosCredito, contraseñaA, rfcCompleto;
        datosNombre = fiscalRellenos = creditoRellenos = direccionRelleno = direccionDatos = fiscalDatos = datosCredito = contraseñaA = rfcCompleto = false;
        Empleados empleado = emple.seleccionarEmpleado("", idEmpleado);
        ContraseñaConUsuario cc = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
        cc.vaciarEmpleado(empleado.getId());
        cc.setVisible(true);
        if (cc.contraseñaAceptada == true) {
            contraseñaA = true;
        }

        if (selectMoral.isSelected()) {
            if (!"".equals(txtRazonSocial.getText()) && !"".equals(txtTipoSociedad.getText())) {
                datosNombre = true;
            }
        } else {
            if (!"".equals(txtNombre.getText()) && !"".equals(txtApellidoPaterno.getText()) && !"".equals(txtApellidoMaterno.getText())) {
                datosNombre = true;
            }
        }

        if (!"".equals(txtCalle.getText())) {
            if (!"".equals(txtEstado.getText()) && !"".equals(txtColonia.getText()) && !"".equals(txtCalle.getText()) && !"".equals(txtCodigoPostal.getText()) && !"".equals(txtInterior.getText()) && !"".equals(txtMunicipio.getText())) {
                direccionDatos = true;
                direccionRelleno = true;
            }
        }

        if ("".equals(txtCalle.getText())) {
            if ("".equals(txtEstado.getText()) && "".equals(txtColonia.getText()) && "".equals(txtCalle.getText()) && "".equals(txtCodigoPostal.getText()) && "".equals(txtInterior.getText()) && "".equals(txtMunicipio.getText()) && "".equals(txtCalle.getText())) {
                direccionDatos = true;
            }
        }

        if (!"".equals(txtRfc.getText())) {
            if (txtCFDI.getSelectedIndex() != 0 && txtRegimenFiscal.getSelectedIndex() != 0) {
                fiscalDatos = true;
                fiscalRellenos = true;
            }
        }

        if ("".equals(txtRfc.getText())) {
            if (txtCFDI.getSelectedIndex() == 0 && txtRegimenFiscal.getSelectedIndex() == 0) {
                fiscalDatos = true;
            }
        }

        if (!"".equals(txtNombreCredito.getText()) && !"".equals(txtEstadoCredito.getText()) && !"".equals(txtColoniaCredito.getText()) && !"".equals(txtCalleCredito.getText()) && !"".equals(txtCodigoPostalCredito.getText()) && !"".equals(txtInteriorCredito.getText()) && !"".equals(txtMunicipioCredito.getText()) && !"".equals(txtCalleCredito.getText()) && !"".equals(txtVigenciaCredito.getText()) && !"".equals(txtPlazoCredito.getText()) && !"".equals(txtLimiteCredito.getText()) && !"".equals(txtInteresMoratorio.getText()) && !"".equals(txtInteresOrdinario.getText())) {
            datosCredito = true;
            creditoRellenos = true;
        }
        if ("".equals(txtNombreCredito.getText()) && "".equals(txtEstadoCredito.getText()) && "".equals(txtColoniaCredito.getText()) && "".equals(txtCalleCredito.getText()) && "".equals(txtCodigoPostalCredito.getText()) && "".equals(txtInteriorCredito.getText()) && "".equals(txtMunicipioCredito.getText()) && "".equals(txtCalleCredito.getText()) && "".equals(txtVigenciaCredito.getText()) && "".equals(txtPlazoCredito.getText()) && "".equals(txtLimiteCredito.getText()) && "".equals(txtInteresMoratorio.getText()) && "".equals(txtInteresOrdinario.getText())) {
            datosCredito = true;
        }

        if (selectMoral.isSelected()) {
            if ("".equals(txtRfc.getText()) || txtRfc.getText().length() == 12) {
                rfcCompleto = true;
            }
        } else {
            if ("".equals(txtRfc.getText()) || txtRfc.getText().length() == 13) {
                rfcCompleto = true;
            }
        }

        if (datosNombre == false || direccionDatos == false || fiscalDatos == false || datosCredito == false || contraseñaA == false || rfcCompleto == false) {
            if (datosNombre == false) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Rellene los espacios de nombre del cliente");
                panel.showNotification();
            }
            if (direccionDatos == false) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Complete los datos de dirección del cliente");
                panel.showNotification();
            }
            if (fiscalDatos == false) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Complete los datos fiscales del cliente");
                panel.showNotification();
            }
            if (datosCredito == false) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Complete los datos del Crédito");
                panel.showNotification();
            }
            if (contraseñaA == false) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Contraseña incorrecta");
                panel.showNotification();
            }
            if (rfcCompleto == false) {
                Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "RFC incompleto");
                panel.showNotification();
            }
        } else {
            Clientes c = new Clientes();
            if (selectMoral.isSelected()) {
                c.setTipoPersona("Persona Moral");
            } else {
                c.setTipoPersona("Persona Física");
            }
            c.setNombre(modificarString(txtNombre.getText().trim()));
            c.setApellidoP(modificarString(txtApellidoPaterno.getText().trim()));
            c.setApellidoM(modificarString(txtApellidoMaterno.getText().trim()));
            c.setNombreComercial(txtRazonSocial.getText().trim());
            c.setTipoSociedad(txtTipoSociedad.getText().trim());
            if (rfcCompleto == true && fiscalDatos == true) {
                c.setRfc(txtRfc.getText());
                if (txtRegimenFiscal.getSelectedIndex() != 0) {
                    c.setRegimenFiscal(txtRegimenFiscal.getSelectedItem().toString());
                } else {
                    c.setRegimenFiscal("");
                }
                if (txtCFDI.getSelectedIndex() != 0) {
                    c.setCfdi(txtCFDI.getSelectedItem().toString());
                } else {
                    c.setCfdi("");
                }

            }
            c.setTelefono(txtTelefono.getText());
            c.setCorreo(txtCorreo.getText().trim());
            if (direccionDatos == true) {
                c.setCalle(txtCalle.getText());
                c.setCodigoPostal(txtCodigoPostal.getText());
                c.setNumeroInterior(txtInterior.getText());
                c.setNumeroExterior(txtExterior.getText());
                c.setMunicipio(txtMunicipio.getText());
                c.setColonia(txtColonia.getText());
                c.setEstado(txtEstado.getText());
            }
            c.setIdRecibe(empleado.getId());
            c.setFechaCreacion(fecha);

            Credito cre = new Credito();
            if (datosCredito == true && creditoRellenos == true) {
                cre.setNombre(modificarString(txtNombreCredito.getText().trim()));
                cre.setApellidoP(modificarString(txtApellidoPaternoCredito.getText().trim()));
                cre.setApellidoM(modificarString(txtApellidoMaternoCredito.getText().trim()));
                cre.setCalle(txtCalleCredito.getText());
                cre.setCodigoPostal(txtCodigoPostalCredito.getText());
                cre.setNumeroInterior(txtInteriorCredito.getText());
                cre.setNumeroExterior(txtExteriorCredito.getText());
                cre.setMunicipio(txtMunicipioCredito.getText());
                cre.setColonia(txtColoniaCredito.getText());
                cre.setEstado(txtEstadoCredito.getText());
                cre.setCorreo(txtCorreoCredito.getText());
                cre.setTelefono(txttelefonoCredito.getText());
                cre.setGiroOcupacion(txtGiroOcupacionCredito.getText());
                cre.setAntiguedad(txtAntiguedad.getText());
                cre.setVigencia(Integer.parseInt(txtVigenciaCredito.getText()));
                cre.setPlazo(Integer.parseInt(txtPlazoCredito.getText()));
                cre.setAcreditado(txtAcredatario.getText());
                cre.setDocumentoAcreditacion(txtDocumentoAcreditacion.getText());
                cre.setLimite(Double.parseDouble(txtLimiteCredito.getText()));
                cre.setInteresMoratorio(Double.parseDouble(txtInteresMoratorio.getText()));
                cre.setInteresOrdinario(Double.parseDouble(txtInteresOrdinario.getText()));
            }

            ReferenciasCliente referencia1 = new ReferenciasCliente();
            referencia1.setNombre(modificarString(txtNombreReferencia1.getText().trim()));
            referencia1.setApellidoP(modificarString(txtApellidoPaternoReferencia1.getText().trim()));
            referencia1.setApellidoM(modificarString(txtApellidoMaternoReferencia1.getText().trim()));
            referencia1.setCalle(txtCalleReferencia1.getText());
            referencia1.setCodigoPostal(txtCodigoPostalReferencia1.getText());
            referencia1.setNumeroInterior(txtInteriorReferencia1.getText());
            referencia1.setNumeroExterior(txtExteriorReferencia1.getText());
            referencia1.setMunicipio(txtMunicipioReferencia1.getText());
            referencia1.setColonia(txtColoniaReferencia1.getText());
            referencia1.setEstado(txtEstadoReferencia1.getText());
            referencia1.setCorreo(txtCorreoReferencia1.getText());
            referencia1.setTelefono(txttelefonoReferencia1.getText());
            referencia1.setGiroOcupacional(txtGiroOcupacionReferencia1.getText());
            referencia1.setAntiguedad(txtAntiguedadReferencia1.getText());
            referencia1.setTipo("Referencia personal 1");

            ReferenciasCliente referencia2 = new ReferenciasCliente();
            referencia2.setNombre(modificarString(txtNombreReferencia2.getText().trim()));
            referencia2.setApellidoP(modificarString(txtApellidoPaternoReferencia2.getText().trim()));
            referencia2.setApellidoM(modificarString(txtApellidoMaternoReferencia2.getText().trim()));
            referencia2.setCalle(txtCalleReferencia2.getText());
            referencia2.setCodigoPostal(txtCodigoPostalReferencia2.getText());
            referencia2.setNumeroInterior(txtInteriorReferencia2.getText());
            referencia2.setNumeroExterior(txtExteriorReferencia2.getText());
            referencia2.setMunicipio(txtMunicipioReferencia2.getText());
            referencia2.setColonia(txtColoniaReferencia2.getText());
            referencia2.setEstado(txtEstadoReferencia2.getText());
            referencia2.setCorreo(txtCorreoReferencia2.getText());
            referencia2.setTelefono(txttelefonoReferencia2.getText());
            referencia2.setGiroOcupacional(txtGiroOcupacionReferencia2.getText());
            referencia2.setAntiguedad(txtAntiguedadReferencia2.getText());
            referencia2.setTipo("Referencia personal 2");

            ReferenciasCliente referencia3 = new ReferenciasCliente();
            referencia3.setNombreComercial(txtNombreComercial3.getText().trim());
            referencia3.setCalle(txtCalleReferencia3.getText());
            referencia3.setCodigoPostal(txtCodigoPostalReferencia3.getText());
            referencia3.setNumeroInterior(txtInteriorReferencia3.getText());
            referencia3.setNumeroExterior(txtExteriorReferencia3.getText());
            referencia3.setMunicipio(txtMunicipioReferencia3.getText());
            referencia3.setColonia(txtColoniaReferencia3.getText());
            referencia3.setEstado(txtEstadoReferencia3.getText());
            referencia3.setCorreo(txtCorreoReferencia3.getText());
            referencia3.setTelefono(txttelefonoReferencia3.getText());
            referencia3.setGiroOcupacional(txtGiroOcupacionReferencia3.getText());
            referencia3.setAntiguedad(txtAntiguedadReferencia3.getText());
            referencia3.setTipo("Referencia comercial 1");

            ReferenciasCliente referencia4 = new ReferenciasCliente();
            referencia4.setNombreComercial(txtNombreComercial4.getText().trim());
            referencia4.setCalle(txtCalleReferencia4.getText());
            referencia4.setCodigoPostal(txtCodigoPostalReferencia4.getText());
            referencia4.setNumeroInterior(txtInteriorReferencia4.getText());
            referencia4.setNumeroExterior(txtExteriorReferencia4.getText());
            referencia4.setMunicipio(txtMunicipioReferencia4.getText());
            referencia4.setColonia(txtColoniaReferencia4.getText());
            referencia4.setEstado(txtEstadoReferencia4.getText());
            referencia4.setCorreo(txtCorreoReferencia4.getText());
            referencia4.setTelefono(txttelefonoReferencia4.getText());
            referencia4.setGiroOcupacional(txtGiroOcupacionReferencia4.getText());
            referencia4.setAntiguedad(txtAntiguedadReferencia4.getText());
            referencia4.setTipo("Referencia comercial 2");

            if (bandera == true) {//crear cliente  
                c.setId(client.idCliente() + 1);
                if (client.insertarCliente(c) == false) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cliente repetido");
                    panel.showNotification();
                } else {
                    String mensaje = "Cliente registrado exitosamente ";
                    accionCompletada = true;
                    bitacora.registrarRegistro("Nuevo Cliente: " + c.getNombre(), empleado.getId(), fecha);
                    if (datosCredito == true && creditoRellenos == true) {
                        cre.setFecha(fecha);
                        cre.setIdCliente(c.getId());
                        creditoDao.RegistrarCredito(cre);
                        bitacora.registrarRegistro("Se autorizo un Crédito para el Cliente: " + c.getNombre(), empleado.getId(), fecha);
                        mensaje += "con Crédito";
                    }
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, mensaje);
                    panel.showNotification();
                    referencia1.setIdCliente(c.getId());
                    referencia2.setIdCliente(c.getId());
                    referencia3.setIdCliente(c.getId());
                    referencia4.setIdCliente(c.getId());
                    referenciaDao.RegistrarReferencia(referencia1);
                    referenciaDao.RegistrarReferencia(referencia2);
                    referenciaDao.RegistrarReferencia(referencia3);
                    referenciaDao.RegistrarReferencia(referencia4);

                    dispose();
                }
            } else {//modificar
                if (datosCredito == false && !txtFolioCredito.getText().equals("Folio: " + (creditoDao.idCredito() + 1))) {
                    Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Debe completar los datos del crédito");
                    panel.showNotification();
                } else {
                    c.setId(original.getId());
                    if (client.ModificarCliente(c) == false) {
                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cliente repetido");
                        panel.showNotification();
                    } else {
                        accionCompletada = true;
                        bitacora.registrarRegistro("Actualización de información de Cliente: " + c.getNombre(), empleado.getId(), fecha);
                        if (creditoRellenos == true && datosCredito == true && !txtFolioCredito.getText().equals("Folio: " + (creditoDao.idCredito() + 1))) {
                            cre.setFecha(fecha);
                            cre.setIdCliente(original.getId());
                            creditoDao.actualizarCredito(cre);
                        } else {
                            if (creditoRellenos == true && datosCredito == true && txtFolioCredito.getText().equals("Folio: " + (creditoDao.idCredito() + 1))) {
                                cre.setFecha(fecha);
                                cre.setIdCliente(original.getId());
                                creditoDao.RegistrarCredito(cre);
                                bitacora.registrarRegistro("Se autorizo un Crédito para el Cliente: " + c.getNombre(), empleado.getId(), fecha);
                            }

                        }
                        referenciaDao.EliminarReferencias(original.getId());
                        referencia1.setIdCliente(original.getId());
                        referencia2.setIdCliente(original.getId());
                        referencia3.setIdCliente(original.getId());
                        referencia4.setIdCliente(original.getId());
                        referenciaDao.RegistrarReferencia(referencia1);
                        referenciaDao.RegistrarReferencia(referencia2);
                        referenciaDao.RegistrarReferencia(referencia3);
                        referenciaDao.RegistrarReferencia(referencia4);
                        dispose();
                    }
                }
            }
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

    public void eventoF1Datos() {
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
    }

    public void eventoF1Credito() {
        txtNombreCredito.setText("");
        txtApellidoPaternoCredito.setText("");
        txtApellidoMaternoCredito.setText("");
        txtCalleCredito.setText("");
        txtCodigoPostalCredito.setText("");
        txtInteriorCredito.setText("");
        txtExteriorCredito.setText("");
        txtMunicipioCredito.setText("");
        txtColoniaCredito.setText("");
        txtEstadoCredito.setText("");
        txttelefonoCredito.setText("");
        txtCorreoCredito.setText("");
        txtGiroOcupacionCredito.setText("");
        txtAntiguedad.setText("");
        txtVigenciaCredito.setText("");
        txtPlazoCredito.setText("");
        txtAcredatario.setText("");
        txtDocumentoAcreditacion.setText("");
        txtLimiteCredito.setText("");
        txtInteresMoratorio.setText("");
        txtInteresOrdinario.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtNombre1 = new textfield.TextField();
        txtCorreo1 = new textfield.TextField();
        txtRegimenFiscal1 = new textfield.TextField();
        txtCorreo3 = new textfield.TextField();
        txtRazonSocial1 = new textfield.TextField();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        txtNombre2 = new textfield.TextField();
        txtNombre3 = new textfield.TextField();
        txtNombre4 = new textfield.TextField();
        txtNombre5 = new textfield.TextField();
        txtCorreo2 = new textfield.TextField();
        txtTelefono2 = new textfield.TextField();
        txtRazonSocial2 = new textfield.TextField();
        txtRegimenFiscal2 = new textfield.TextField();
        txtNombre6 = new textfield.TextField();
        txtCorreo4 = new textfield.TextField();
        txtTelefono1 = new textfield.TextField();
        txtTelefono3 = new textfield.TextField();
        txtCorreo5 = new textfield.TextField();
        txtCorreo11 = new textfield.TextField();
        txtNombre14 = new textfield.TextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtFolioCredito = new javax.swing.JTextField();
        txtFechaCrediito = new javax.swing.JTextField();
        txtNombreCredito = new javax.swing.JTextField();
        txtApellidoPaternoCredito = new javax.swing.JTextField();
        txtApellidoMaternoCredito = new javax.swing.JTextField();
        txtColoniaCredito = new javax.swing.JTextField();
        txtInteriorCredito = new javax.swing.JTextField();
        txtCalleCredito = new javax.swing.JTextField();
        txtExteriorCredito = new javax.swing.JTextField();
        txtMunicipioCredito = new javax.swing.JTextField();
        txtCodigoPostalCredito = new javax.swing.JTextField();
        txtEstadoCredito = new javax.swing.JTextField();
        txtCorreoCredito = new javax.swing.JTextField();
        txtGiroOcupacionCredito = new javax.swing.JTextField();
        txtVigenciaCredito = new javax.swing.JTextField();
        txtAcredatario = new javax.swing.JTextField();
        txtPlazoCredito = new javax.swing.JTextField();
        txtAntiguedad = new javax.swing.JTextField();
        txttelefonoCredito = new javax.swing.JTextField();
        txtDocumentoAcreditacion = new javax.swing.JTextField();
        txtInteresOrdinario = new javax.swing.JTextField();
        txtInteresMoratorio = new javax.swing.JTextField();
        txtLimiteCredito = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        panelAceptar2 = new javax.swing.JPanel();
        btnAceptar2 = new javax.swing.JLabel();
        panelLimpiar2 = new javax.swing.JPanel();
        btnLimpiar2 = new javax.swing.JLabel();
        panelEliminar2 = new javax.swing.JPanel();
        btnEliminar2 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        LBApellidoPaterno2 = new javax.swing.JLabel();
        LBApellidoPaterno3 = new javax.swing.JLabel();
        lbNombre3 = new javax.swing.JLabel();
        lbNombre16 = new javax.swing.JLabel();
        lbNombre17 = new javax.swing.JLabel();
        lbNombre18 = new javax.swing.JLabel();
        lbNombre19 = new javax.swing.JLabel();
        lbNombre20 = new javax.swing.JLabel();
        lbNombre21 = new javax.swing.JLabel();
        lbNombre22 = new javax.swing.JLabel();
        lbNombre23 = new javax.swing.JLabel();
        lbNombre24 = new javax.swing.JLabel();
        lbNombre25 = new javax.swing.JLabel();
        lbNombre26 = new javax.swing.JLabel();
        lbNombre27 = new javax.swing.JLabel();
        lbNombre28 = new javax.swing.JLabel();
        lbNombre29 = new javax.swing.JLabel();
        lbNombre30 = new javax.swing.JLabel();
        lbNombre31 = new javax.swing.JLabel();
        LBApellidoPaterno4 = new javax.swing.JLabel();
        LBApellidoPaterno5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtRegimenFiscal9 = new textfield.TextField();
        txtRazonSocial9 = new textfield.TextField();
        txtNombre20 = new textfield.TextField();
        txtCorreo21 = new textfield.TextField();
        txtNombre21 = new textfield.TextField();
        txtTelefono11 = new textfield.TextField();
        txtTelefono12 = new textfield.TextField();
        txtCorreo22 = new textfield.TextField();
        txtCorreo23 = new textfield.TextField();
        txtNombreReferencia1 = new javax.swing.JTextField();
        txtApellidoPaternoReferencia1 = new javax.swing.JTextField();
        txtApellidoMaternoReferencia1 = new javax.swing.JTextField();
        txtCodigoPostalReferencia1 = new javax.swing.JTextField();
        txtCalleReferencia1 = new javax.swing.JTextField();
        txtInteriorReferencia1 = new javax.swing.JTextField();
        txtExteriorReferencia1 = new javax.swing.JTextField();
        txtMunicipioReferencia1 = new javax.swing.JTextField();
        txtEstadoReferencia1 = new javax.swing.JTextField();
        txttelefonoReferencia1 = new javax.swing.JTextField();
        txtCorreoReferencia1 = new javax.swing.JTextField();
        txtColoniaReferencia1 = new javax.swing.JTextField();
        txtGiroOcupacionReferencia1 = new javax.swing.JTextField();
        txtAntiguedadReferencia1 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtNombreReferencia2 = new javax.swing.JTextField();
        txtApellidoPaternoReferencia2 = new javax.swing.JTextField();
        txtApellidoMaternoReferencia2 = new javax.swing.JTextField();
        txtCodigoPostalReferencia2 = new javax.swing.JTextField();
        txtCalleReferencia2 = new javax.swing.JTextField();
        txtExteriorReferencia2 = new javax.swing.JTextField();
        txtInteriorReferencia2 = new javax.swing.JTextField();
        txtMunicipioReferencia2 = new javax.swing.JTextField();
        txtEstadoReferencia2 = new javax.swing.JTextField();
        txtColoniaReferencia2 = new javax.swing.JTextField();
        txtCorreoReferencia2 = new javax.swing.JTextField();
        txtGiroOcupacionReferencia2 = new javax.swing.JTextField();
        txtAntiguedadReferencia2 = new javax.swing.JTextField();
        txttelefonoReferencia2 = new javax.swing.JTextField();
        lbNombre32 = new javax.swing.JLabel();
        lbNombre33 = new javax.swing.JLabel();
        lbNombre34 = new javax.swing.JLabel();
        lbNombre35 = new javax.swing.JLabel();
        lbNombre36 = new javax.swing.JLabel();
        lbNombre37 = new javax.swing.JLabel();
        lbNombre38 = new javax.swing.JLabel();
        LBApellidoPaterno6 = new javax.swing.JLabel();
        LBApellidoPaterno7 = new javax.swing.JLabel();
        lbNombre39 = new javax.swing.JLabel();
        lbNombre40 = new javax.swing.JLabel();
        lbNombre41 = new javax.swing.JLabel();
        lbNombre42 = new javax.swing.JLabel();
        lbNombre43 = new javax.swing.JLabel();
        LBApellidoPaterno8 = new javax.swing.JLabel();
        lbNombre56 = new javax.swing.JLabel();
        LBApellidoPaterno9 = new javax.swing.JLabel();
        lbNombre57 = new javax.swing.JLabel();
        lbNombre58 = new javax.swing.JLabel();
        lbNombre59 = new javax.swing.JLabel();
        lbNombre60 = new javax.swing.JLabel();
        lbNombre61 = new javax.swing.JLabel();
        lbNombre62 = new javax.swing.JLabel();
        lbNombre63 = new javax.swing.JLabel();
        lbNombre64 = new javax.swing.JLabel();
        lbNombre65 = new javax.swing.JLabel();
        lbNombre66 = new javax.swing.JLabel();
        lbNombre67 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtRegimenFiscal10 = new textfield.TextField();
        txtRazonSocial10 = new textfield.TextField();
        txtNombre22 = new textfield.TextField();
        txtCorreo24 = new textfield.TextField();
        txtNombre23 = new textfield.TextField();
        txtTelefono13 = new textfield.TextField();
        txtTelefono14 = new textfield.TextField();
        txtCorreo25 = new textfield.TextField();
        txtCorreo26 = new textfield.TextField();
        jLabel37 = new javax.swing.JLabel();
        txtNombreComercial3 = new javax.swing.JTextField();
        txtCalleReferencia3 = new javax.swing.JTextField();
        txtCodigoPostalReferencia3 = new javax.swing.JTextField();
        txtMunicipioReferencia3 = new javax.swing.JTextField();
        txtExteriorReferencia3 = new javax.swing.JTextField();
        txtInteriorReferencia3 = new javax.swing.JTextField();
        txtColoniaReferencia3 = new javax.swing.JTextField();
        txtCorreoReferencia3 = new javax.swing.JTextField();
        txtGiroOcupacionReferencia3 = new javax.swing.JTextField();
        txtAntiguedadReferencia3 = new javax.swing.JTextField();
        txttelefonoReferencia3 = new javax.swing.JTextField();
        txtEstadoReferencia3 = new javax.swing.JTextField();
        txtNombreComercial4 = new javax.swing.JTextField();
        txtCalleReferencia4 = new javax.swing.JTextField();
        txtExteriorReferencia4 = new javax.swing.JTextField();
        txtInteriorReferencia4 = new javax.swing.JTextField();
        txtMunicipioReferencia4 = new javax.swing.JTextField();
        txtCodigoPostalReferencia4 = new javax.swing.JTextField();
        txtEstadoReferencia4 = new javax.swing.JTextField();
        txttelefonoReferencia4 = new javax.swing.JTextField();
        txtAntiguedadReferencia4 = new javax.swing.JTextField();
        txtGiroOcupacionReferencia4 = new javax.swing.JTextField();
        txtCorreoReferencia4 = new javax.swing.JTextField();
        txtColoniaReferencia4 = new javax.swing.JTextField();
        lbNombre44 = new javax.swing.JLabel();
        lbNombre45 = new javax.swing.JLabel();
        lbNombre46 = new javax.swing.JLabel();
        lbNombre47 = new javax.swing.JLabel();
        lbNombre48 = new javax.swing.JLabel();
        lbNombre49 = new javax.swing.JLabel();
        lbNombre50 = new javax.swing.JLabel();
        lbNombre51 = new javax.swing.JLabel();
        lbNombre52 = new javax.swing.JLabel();
        lbNombre53 = new javax.swing.JLabel();
        lbNombre54 = new javax.swing.JLabel();
        lbNombre55 = new javax.swing.JLabel();
        lbNombre68 = new javax.swing.JLabel();
        lbNombre69 = new javax.swing.JLabel();
        lbNombre70 = new javax.swing.JLabel();
        lbNombre71 = new javax.swing.JLabel();
        lbNombre72 = new javax.swing.JLabel();
        lbNombre73 = new javax.swing.JLabel();
        lbNombre74 = new javax.swing.JLabel();
        lbNombre75 = new javax.swing.JLabel();
        lbNombre76 = new javax.swing.JLabel();
        lbNombre77 = new javax.swing.JLabel();
        lbNombre78 = new javax.swing.JLabel();
        lbNombre79 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setBackground(new java.awt.Color(0, 0, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Datos Fiscales");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 240, -1));

        txtNombre1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre1.setLabelText("Régimen Fiscal *");
        txtNombre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre1KeyTyped(evt);
            }
        });
        jPanel2.add(txtNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 225, 250, 40));

        txtCorreo1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo1.setLabelText("Calle *");
        txtCorreo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo1KeyReleased(evt);
            }
        });
        jPanel2.add(txtCorreo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 280, 40));

        txtRegimenFiscal1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRegimenFiscal1.setLabelText("Apellido Paterno *");
        txtRegimenFiscal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal1KeyTyped(evt);
            }
        });
        jPanel2.add(txtRegimenFiscal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 135, 127, 40));

        txtCorreo3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo3.setLabelText("Colonia *");
        txtCorreo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo3KeyReleased(evt);
            }
        });
        jPanel2.add(txtCorreo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, 240, 40));

        txtRazonSocial1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRazonSocial1.setLabelText("Apellido Materno *");
        txtRazonSocial1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRazonSocial1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRazonSocial1KeyReleased(evt);
            }
        });
        jPanel2.add(txtRazonSocial1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 135, 127, 40));

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox3.setText("Persona Moral");
        jPanel2.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jCheckBox4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox4.setText("Persona Física");
        jPanel2.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, -1, -1));

        txtNombre2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre2.setLabelText("Nombre(s) *");
        txtNombre2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre2KeyTyped(evt);
            }
        });
        jPanel2.add(txtNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 260, 40));

        txtNombre3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre3.setLabelText("Denominación o Razón Social *");
        txtNombre3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre3KeyTyped(evt);
            }
        });
        jPanel2.add(txtNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 250, 40));

        txtNombre4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre4.setLabelText("Tipo de sociedad *");
        txtNombre4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre4KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre4KeyTyped(evt);
            }
        });
        jPanel2.add(txtNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 135, 250, 40));

        txtNombre5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre5.setLabelText("RFC *");
        txtNombre5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre5KeyTyped(evt);
            }
        });
        jPanel2.add(txtNombre5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 250, 40));

        txtCorreo2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo2.setLabelText("Régimen Fiscal *");
        txtCorreo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo2KeyReleased(evt);
            }
        });
        jPanel2.add(txtCorreo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 225, 250, 40));

        txtTelefono2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono2.setLabelText("RFC *");
        txtTelefono2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefono2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefono2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono2KeyTyped(evt);
            }
        });
        jPanel2.add(txtTelefono2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 250, 40));

        txtRazonSocial2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRazonSocial2.setLabelText("Código postal *");
        txtRazonSocial2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRazonSocial2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRazonSocial2KeyReleased(evt);
            }
        });
        jPanel2.add(txtRazonSocial2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 315, 170, 40));

        txtRegimenFiscal2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRegimenFiscal2.setLabelText("Número interior");
        txtRegimenFiscal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal2KeyTyped(evt);
            }
        });
        jPanel2.add(txtRegimenFiscal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 315, 170, 40));

        txtNombre6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre6.setLabelText("Número exterior *");
        txtNombre6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre6KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre6KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre6KeyTyped(evt);
            }
        });
        jPanel2.add(txtNombre6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 315, 170, 40));

        txtCorreo4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo4.setLabelText("Uso de CFDI *");
        txtCorreo4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo4KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo4KeyReleased(evt);
            }
        });
        jPanel2.add(txtCorreo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 250, 40));

        txtTelefono1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono1.setLabelText("Correo");
        txtTelefono1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefono1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefono1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono1KeyTyped(evt);
            }
        });
        jPanel2.add(txtTelefono1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 405, 250, 40));

        txtTelefono3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono3.setLabelText("Correo");
        txtTelefono3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefono3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefono3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono3KeyTyped(evt);
            }
        });
        jPanel2.add(txtTelefono3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 405, 250, 40));

        txtCorreo5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo5.setLabelText("Uso de CFDI *");
        txtCorreo5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo5KeyReleased(evt);
            }
        });
        jPanel2.add(txtCorreo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 450, 250, 40));

        txtCorreo11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo11.setLabelText("Municipio *");
        txtCorreo11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo11KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo11KeyReleased(evt);
            }
        });
        jPanel2.add(txtCorreo11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 250, 40));

        txtNombre14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre14.setLabelText("Estado *");
        txtNombre14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre14KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre14KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre14KeyTyped(evt);
            }
        });
        jPanel2.add(txtNombre14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 250, 40));

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 530, 150, -1));

        jTextField2.setText("jTextField2");
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jTextField3.setText("jTextField3");
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 260, 30));

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
        jLabel25.setText("Datos del Cliente");
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
        btnAceptar.setToolTipText("ENTER - Guardar datos del cliente");
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
        lbInterior.setText("N.exterior");
        panelDatos.add(lbInterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 213, 60, -1));

        lbMunicipio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbMunicipio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbMunicipio.setText("Municipio");
        panelDatos.add(lbMunicipio, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 273, 58, -1));

        jTabbedPane1.addTab("Datos", panelDatos);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setBackground(new java.awt.Color(0, 0, 204));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Datos del Crédito");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 140, -1));

        jLabel31.setBackground(new java.awt.Color(0, 0, 204));
        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Datos del solicitante");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 140, -1));

        txtFolioCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFolioCredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFolioCredito.setEnabled(false);
        jPanel3.add(txtFolioCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 30, 90, -1));

        txtFechaCrediito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtFechaCrediito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFechaCrediito.setEnabled(false);
        jPanel3.add(txtFechaCrediito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 30, 110, -1));

        txtNombreCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombreCredito.setToolTipText("Nombre del solicitante");
        txtNombreCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtNombreCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 90, 185, -1));

        txtApellidoPaternoCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtApellidoPaternoCredito.setToolTipText("Apellido Paterno del solicitante");
        txtApellidoPaternoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtApellidoPaternoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 90, 110, -1));

        txtApellidoMaternoCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtApellidoMaternoCredito.setToolTipText("Apellido materno del solicitante");
        txtApellidoMaternoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtApellidoMaternoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 120, -1));

        txtColoniaCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtColoniaCredito.setToolTipText("Colonia");
        txtColoniaCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtColoniaCreditoKeyReleased(evt);
            }
        });
        jPanel3.add(txtColoniaCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 150, 220, -1));

        txtInteriorCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteriorCredito.setToolTipText("Número interior");
        txtInteriorCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteriorCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteriorCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtInteriorCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 120, 80, -1));

        txtCalleCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCalleCredito.setToolTipText("Calle");
        txtCalleCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCalleCreditoKeyReleased(evt);
            }
        });
        jPanel3.add(txtCalleCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 120, 220, -1));

        txtExteriorCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtExteriorCredito.setToolTipText("Número exterior");
        txtExteriorCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExteriorCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExteriorCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtExteriorCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 80, -1));

        txtMunicipioCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtMunicipioCredito.setToolTipText("Municipio");
        txtMunicipioCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMunicipioCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMunicipioCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtMunicipioCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 180, 230, -1));

        txtCodigoPostalCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCodigoPostalCredito.setToolTipText("Código postal");
        txtCodigoPostalCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoPostalCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtCodigoPostalCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 150, 230, -1));

        txtEstadoCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtEstadoCredito.setToolTipText("Estado");
        txtEstadoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEstadoCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtEstadoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 180, 220, -1));

        txtCorreoCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCorreoCredito.setToolTipText("Correo electronido");
        txtCorreoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoCreditoKeyReleased(evt);
            }
        });
        jPanel3.add(txtCorreoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 210, 220, -1));

        txtGiroOcupacionCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtGiroOcupacionCredito.setToolTipText("Giro u ocupacion del solicitante");
        txtGiroOcupacionCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiroOcupacionCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGiroOcupacionCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtGiroOcupacionCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 240, 220, -1));

        txtVigenciaCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtVigenciaCredito.setToolTipText("Plazo de vigencia del crédito (en días)");
        txtVigenciaCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtVigenciaCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVigenciaCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtVigenciaCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 270, 220, -1));

        txtAcredatario.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtAcredatario.setToolTipText("Nombre de acreditado");
        txtAcredatario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAcredatarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAcredatarioKeyTyped(evt);
            }
        });
        jPanel3.add(txtAcredatario, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 270, 230, -1));

        txtPlazoCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtPlazoCredito.setToolTipText("Plazo del crédito (en días)");
        txtPlazoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPlazoCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlazoCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtPlazoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 300, 220, -1));

        txtAntiguedad.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtAntiguedad.setToolTipText("Antigüedad en ocupación");
        txtAntiguedad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAntiguedadKeyReleased(evt);
            }
        });
        jPanel3.add(txtAntiguedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 240, 230, -1));

        txttelefonoCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txttelefonoCredito.setToolTipText("Teléfono (10 digítos)");
        txttelefonoCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttelefonoCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelefonoCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txttelefonoCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 210, 230, -1));

        txtDocumentoAcreditacion.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDocumentoAcreditacion.setToolTipText("Documento que lo valide");
        txtDocumentoAcreditacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDocumentoAcreditacionKeyReleased(evt);
            }
        });
        jPanel3.add(txtDocumentoAcreditacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 300, 230, -1));

        txtInteresOrdinario.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteresOrdinario.setToolTipText("Interés Ordinario (%)");
        txtInteresOrdinario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInteresOrdinarioActionPerformed(evt);
            }
        });
        txtInteresOrdinario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteresOrdinarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteresOrdinarioKeyTyped(evt);
            }
        });
        jPanel3.add(txtInteresOrdinario, new org.netbeans.lib.awtextra.AbsoluteConstraints(453, 330, 130, -1));

        txtInteresMoratorio.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteresMoratorio.setToolTipText("Interés moratorio (%)");
        txtInteresMoratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteresMoratorioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteresMoratorioKeyTyped(evt);
            }
        });
        jPanel3.add(txtInteresMoratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 330, 130, -1));

        txtLimiteCredito.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtLimiteCredito.setToolTipText("Límite del crédito");
        txtLimiteCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLimiteCreditoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLimiteCreditoKeyTyped(evt);
            }
        });
        jPanel3.add(txtLimiteCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 330, 140, -1));

        jLabel29.setBackground(new java.awt.Color(0, 0, 204));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 255));
        jLabel29.setText("¿Está seguro de ejecutar esta acción? ");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, -1, -1));

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
        btnAceptar2.setToolTipText("ENTER - Guardar datos del cliente");
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
            .addComponent(btnAceptar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelAceptar2Layout.setVerticalGroup(
            panelAceptar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel3.add(panelAceptar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 50, 50));

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
            .addComponent(btnLimpiar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLimpiar2Layout.setVerticalGroup(
            panelLimpiar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel3.add(panelLimpiar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, 50, 50));

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
            .addComponent(btnEliminar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelEliminar2Layout.setVerticalGroup(
            panelEliminar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel3.add(panelEliminar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 50, 50));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Fecha");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 33, 40, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Código");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 33, 40, -1));

        LBApellidoPaterno2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno2.setText("Paterno");
        jPanel3.add(LBApellidoPaterno2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 95, 50, -1));

        LBApellidoPaterno3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno3.setText("Materno");
        jPanel3.add(LBApellidoPaterno3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 95, 50, -1));

        lbNombre3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre3.setText("Nombre");
        jPanel3.add(lbNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 95, 50, -1));

        lbNombre16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre16.setText("N. Exterior");
        jPanel3.add(lbNombre16, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 123, 67, -1));

        lbNombre17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre17.setText("N. interior");
        jPanel3.add(lbNombre17, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 123, 60, -1));

        lbNombre18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre18.setText("C.P.");
        jPanel3.add(lbNombre18, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 153, 67, -1));

        lbNombre19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre19.setText("Municipio");
        jPanel3.add(lbNombre19, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 183, 67, -1));

        lbNombre20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre20.setText("Colonia");
        jPanel3.add(lbNombre20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 153, 50, -1));

        lbNombre21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre21.setText("Calle");
        jPanel3.add(lbNombre21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 123, 50, -1));

        lbNombre22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre22.setText("Estado");
        jPanel3.add(lbNombre22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 183, 50, -1));

        lbNombre23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre23.setText("Teléfono");
        jPanel3.add(lbNombre23, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 213, 67, -1));

        lbNombre24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre24.setText("Correo");
        jPanel3.add(lbNombre24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 213, 50, -1));

        lbNombre25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre25.setText("Giro");
        jPanel3.add(lbNombre25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 243, 50, -1));

        lbNombre26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre26.setText("Antigüedad");
        jPanel3.add(lbNombre26, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 243, 67, -1));

        lbNombre27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre27.setText("Plazo");
        jPanel3.add(lbNombre27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 303, 50, -1));

        lbNombre28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre28.setText("Vigencia");
        jPanel3.add(lbNombre28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 273, 50, -1));

        lbNombre29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre29.setText("Documento");
        jPanel3.add(lbNombre29, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 303, 67, -1));

        lbNombre30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre30.setText("Acreditado");
        jPanel3.add(lbNombre30, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 273, 67, -1));

        lbNombre31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre31.setText("Crédito");
        jPanel3.add(lbNombre31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 333, 50, -1));

        LBApellidoPaterno4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno4.setText("Moratorio");
        jPanel3.add(LBApellidoPaterno4, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 333, 60, -1));

        LBApellidoPaterno5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno5.setText("Ordinario");
        jPanel3.add(LBApellidoPaterno5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 333, 60, -1));

        jTabbedPane1.addTab("Crédito", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setBackground(new java.awt.Color(0, 0, 204));
        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Referencia 1");
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 140, -1));

        jLabel32.setBackground(new java.awt.Color(0, 0, 204));
        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Datos del referencia personales");
        jPanel4.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 210, -1));

        txtRegimenFiscal9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRegimenFiscal9.setLabelText("Número interior");
        txtRegimenFiscal9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal9KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal9KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal9KeyTyped(evt);
            }
        });
        jPanel4.add(txtRegimenFiscal9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, 170, 40));

        txtRazonSocial9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRazonSocial9.setLabelText("Código postal *");
        txtRazonSocial9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRazonSocial9KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRazonSocial9KeyReleased(evt);
            }
        });
        jPanel4.add(txtRazonSocial9, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, 170, 40));

        txtNombre20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre20.setLabelText("Número exterior *");
        txtNombre20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre20KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre20KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre20KeyTyped(evt);
            }
        });
        jPanel4.add(txtNombre20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 170, 40));

        txtCorreo21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo21.setLabelText("Municipio *");
        txtCorreo21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo21KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo21KeyReleased(evt);
            }
        });
        jPanel4.add(txtCorreo21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 485, 250, 40));

        txtNombre21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre21.setLabelText("Estado *");
        txtNombre21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre21KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre21KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre21KeyTyped(evt);
            }
        });
        jPanel4.add(txtNombre21, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 485, 250, 40));

        txtTelefono11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono11.setLabelText("Teléfono");
        txtTelefono11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefono11ActionPerformed(evt);
            }
        });
        txtTelefono11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefono11KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefono11KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono11KeyTyped(evt);
            }
        });
        jPanel4.add(txtTelefono11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 530, 250, 40));

        txtTelefono12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono12.setLabelText("Correo");
        txtTelefono12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefono12KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefono12KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono12KeyTyped(evt);
            }
        });
        jPanel4.add(txtTelefono12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 250, 40));

        txtCorreo22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo22.setLabelText("Giro de ocupación");
        txtCorreo22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo22KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo22KeyReleased(evt);
            }
        });
        jPanel4.add(txtCorreo22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 575, 250, 40));

        txtCorreo23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo23.setLabelText("Antigüedad");
        txtCorreo23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo23KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo23KeyReleased(evt);
            }
        });
        jPanel4.add(txtCorreo23, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 575, 250, 40));

        txtNombreReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombreReferencia1.setToolTipText("Nombre");
        txtNombreReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txtNombreReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 50, 185, -1));

        txtApellidoPaternoReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtApellidoPaternoReferencia1.setToolTipText("Apellido paterno");
        txtApellidoPaternoReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txtApellidoPaternoReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 50, 110, -1));

        txtApellidoMaternoReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtApellidoMaternoReferencia1.setToolTipText("Apellido Materno");
        txtApellidoMaternoReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txtApellidoMaternoReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 50, 110, -1));

        txtCodigoPostalReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCodigoPostalReferencia1.setToolTipText("Código postal");
        txtCodigoPostalReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoPostalReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txtCodigoPostalReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 110, 230, -1));

        txtCalleReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCalleReferencia1.setToolTipText("Calle");
        txtCalleReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCalleReferencia1KeyReleased(evt);
            }
        });
        jPanel4.add(txtCalleReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 80, 220, -1));

        txtInteriorReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteriorReferencia1.setToolTipText("Número interior");
        txtInteriorReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteriorReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteriorReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txtInteriorReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 80, -1));

        txtExteriorReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtExteriorReferencia1.setToolTipText("Número exterior");
        txtExteriorReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExteriorReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExteriorReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txtExteriorReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 80, -1));

        txtMunicipioReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtMunicipioReferencia1.setToolTipText("Municipio");
        txtMunicipioReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMunicipioReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMunicipioReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txtMunicipioReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 140, 230, -1));

        txtEstadoReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtEstadoReferencia1.setToolTipText("Estado");
        txtEstadoReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEstadoReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txtEstadoReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 140, 220, -1));

        txttelefonoReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txttelefonoReferencia1.setToolTipText("Teléfono (10 digítos)");
        txttelefonoReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttelefonoReferencia1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelefonoReferencia1KeyTyped(evt);
            }
        });
        jPanel4.add(txttelefonoReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 170, 230, -1));

        txtCorreoReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCorreoReferencia1.setToolTipText("Correo electrónico");
        txtCorreoReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoReferencia1KeyReleased(evt);
            }
        });
        jPanel4.add(txtCorreoReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 170, 220, -1));

        txtColoniaReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtColoniaReferencia1.setToolTipText("Colonia");
        txtColoniaReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtColoniaReferencia1KeyReleased(evt);
            }
        });
        jPanel4.add(txtColoniaReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 110, 220, -1));

        txtGiroOcupacionReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtGiroOcupacionReferencia1.setToolTipText("Giro u ocupación");
        txtGiroOcupacionReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiroOcupacionReferencia1KeyReleased(evt);
            }
        });
        jPanel4.add(txtGiroOcupacionReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 200, 220, -1));

        txtAntiguedadReferencia1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtAntiguedadReferencia1.setToolTipText("Antigüedad");
        txtAntiguedadReferencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAntiguedadReferencia1KeyReleased(evt);
            }
        });
        jPanel4.add(txtAntiguedadReferencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 200, 230, -1));

        jLabel34.setBackground(new java.awt.Color(0, 0, 204));
        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Referencia 2");
        jPanel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 140, -1));

        txtNombreReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombreReferencia2.setToolTipText("Nombre");
        txtNombreReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txtNombreReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 260, 185, -1));

        txtApellidoPaternoReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtApellidoPaternoReferencia2.setToolTipText("Apellido paterno");
        txtApellidoPaternoReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txtApellidoPaternoReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 260, 110, -1));

        txtApellidoMaternoReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtApellidoMaternoReferencia2.setToolTipText("Apellido Materno");
        txtApellidoMaternoReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txtApellidoMaternoReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 260, 110, -1));

        txtCodigoPostalReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCodigoPostalReferencia2.setToolTipText("Código postal");
        txtCodigoPostalReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoPostalReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txtCodigoPostalReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 320, 230, -1));

        txtCalleReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCalleReferencia2.setToolTipText("Calle");
        txtCalleReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCalleReferencia2KeyReleased(evt);
            }
        });
        jPanel4.add(txtCalleReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 290, 220, -1));

        txtExteriorReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtExteriorReferencia2.setToolTipText("Número exterior");
        txtExteriorReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExteriorReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExteriorReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txtExteriorReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, 80, -1));

        txtInteriorReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteriorReferencia2.setToolTipText("Número interior");
        txtInteriorReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteriorReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteriorReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txtInteriorReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 290, 80, -1));

        txtMunicipioReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtMunicipioReferencia2.setToolTipText("Municipio");
        txtMunicipioReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMunicipioReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMunicipioReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txtMunicipioReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 350, 230, -1));

        txtEstadoReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtEstadoReferencia2.setToolTipText("Estado");
        txtEstadoReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEstadoReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txtEstadoReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 350, 220, -1));

        txtColoniaReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtColoniaReferencia2.setToolTipText("Colonia");
        txtColoniaReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtColoniaReferencia2KeyReleased(evt);
            }
        });
        jPanel4.add(txtColoniaReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 320, 220, -1));

        txtCorreoReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCorreoReferencia2.setToolTipText("Correo electrónico");
        txtCorreoReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoReferencia2KeyReleased(evt);
            }
        });
        jPanel4.add(txtCorreoReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 380, 220, -1));

        txtGiroOcupacionReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtGiroOcupacionReferencia2.setToolTipText("Giro u ocupación");
        txtGiroOcupacionReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiroOcupacionReferencia2KeyReleased(evt);
            }
        });
        jPanel4.add(txtGiroOcupacionReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 410, 220, -1));

        txtAntiguedadReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtAntiguedadReferencia2.setToolTipText("antigüedad");
        txtAntiguedadReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAntiguedadReferencia2KeyReleased(evt);
            }
        });
        jPanel4.add(txtAntiguedadReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 410, 230, -1));

        txttelefonoReferencia2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txttelefonoReferencia2.setToolTipText("Teléfono");
        txttelefonoReferencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttelefonoReferencia2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelefonoReferencia2KeyTyped(evt);
            }
        });
        jPanel4.add(txttelefonoReferencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 380, 230, -1));

        lbNombre32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre32.setText("Giro");
        jPanel4.add(lbNombre32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 203, 50, -1));

        lbNombre33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre33.setText("Antigüedad");
        jPanel4.add(lbNombre33, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 203, 67, -1));

        lbNombre34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre34.setText("Teléfono");
        jPanel4.add(lbNombre34, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 173, 67, -1));

        lbNombre35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre35.setText("Municipio");
        jPanel4.add(lbNombre35, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 143, 67, -1));

        lbNombre36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre36.setText("C.P.");
        jPanel4.add(lbNombre36, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 113, 67, -1));

        lbNombre37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre37.setText("N. Exterior");
        jPanel4.add(lbNombre37, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 83, 67, -1));

        lbNombre38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre38.setText("N. Interior");
        jPanel4.add(lbNombre38, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 83, 60, -1));

        LBApellidoPaterno6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno6.setText("Materno");
        jPanel4.add(LBApellidoPaterno6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 53, 50, -1));

        LBApellidoPaterno7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno7.setText("Paterno");
        jPanel4.add(LBApellidoPaterno7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 53, 50, -1));

        lbNombre39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre39.setText("Nombre");
        jPanel4.add(lbNombre39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 53, 50, -1));

        lbNombre40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre40.setText("Calle");
        jPanel4.add(lbNombre40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 83, 50, -1));

        lbNombre41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre41.setText("Colonia");
        jPanel4.add(lbNombre41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 113, 50, -1));

        lbNombre42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre42.setText("Estado");
        jPanel4.add(lbNombre42, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 143, 50, -1));

        lbNombre43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre43.setText("Correo");
        jPanel4.add(lbNombre43, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 173, 50, -1));

        LBApellidoPaterno8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno8.setText("Materno");
        jPanel4.add(LBApellidoPaterno8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 263, 50, -1));

        lbNombre56.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre56.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre56.setText("N. Interior");
        jPanel4.add(lbNombre56, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 293, 60, -1));

        LBApellidoPaterno9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LBApellidoPaterno9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LBApellidoPaterno9.setText("Paterno");
        jPanel4.add(LBApellidoPaterno9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 263, 50, -1));

        lbNombre57.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre57.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre57.setText("N. Exterior");
        jPanel4.add(lbNombre57, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 293, 67, -1));

        lbNombre58.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre58.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre58.setText("C.P.");
        jPanel4.add(lbNombre58, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 323, 67, -1));

        lbNombre59.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre59.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre59.setText("Municipio");
        jPanel4.add(lbNombre59, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 353, 67, -1));

        lbNombre60.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre60.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre60.setText("Teléfono");
        jPanel4.add(lbNombre60, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 383, 67, -1));

        lbNombre61.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre61.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre61.setText("Antigüedad");
        jPanel4.add(lbNombre61, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 413, 67, -1));

        lbNombre62.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre62.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre62.setText("Estado");
        jPanel4.add(lbNombre62, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 353, 50, -1));

        lbNombre63.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre63.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre63.setText("Correo");
        jPanel4.add(lbNombre63, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 383, 50, -1));

        lbNombre64.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre64.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre64.setText("Giro");
        jPanel4.add(lbNombre64, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 413, 50, -1));

        lbNombre65.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre65.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre65.setText("Colonia");
        jPanel4.add(lbNombre65, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 323, 50, -1));

        lbNombre66.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre66.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre66.setText("Calle");
        jPanel4.add(lbNombre66, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 293, 50, -1));

        lbNombre67.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre67.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre67.setText("Nombre");
        jPanel4.add(lbNombre67, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 263, 50, -1));

        jTabbedPane1.addTab("Referencias Personales", jPanel4);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setBackground(new java.awt.Color(0, 0, 204));
        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Referencia 1");
        jPanel6.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 140, -1));

        jLabel36.setBackground(new java.awt.Color(0, 0, 204));
        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Datos del referencia comerciales");
        jPanel6.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 210, -1));

        txtRegimenFiscal10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRegimenFiscal10.setLabelText("Número interior");
        txtRegimenFiscal10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal10KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal10KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRegimenFiscal10KeyTyped(evt);
            }
        });
        jPanel6.add(txtRegimenFiscal10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, 170, 40));

        txtRazonSocial10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRazonSocial10.setLabelText("Código postal *");
        txtRazonSocial10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRazonSocial10KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRazonSocial10KeyReleased(evt);
            }
        });
        jPanel6.add(txtRazonSocial10, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, 170, 40));

        txtNombre22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre22.setLabelText("Número exterior *");
        txtNombre22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre22KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre22KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre22KeyTyped(evt);
            }
        });
        jPanel6.add(txtNombre22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 170, 40));

        txtCorreo24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo24.setLabelText("Municipio *");
        txtCorreo24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo24KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo24KeyReleased(evt);
            }
        });
        jPanel6.add(txtCorreo24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 485, 250, 40));

        txtNombre23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre23.setLabelText("Estado *");
        txtNombre23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre23KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre23KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre23KeyTyped(evt);
            }
        });
        jPanel6.add(txtNombre23, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 485, 250, 40));

        txtTelefono13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono13.setLabelText("Teléfono");
        txtTelefono13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefono13ActionPerformed(evt);
            }
        });
        txtTelefono13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefono13KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefono13KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono13KeyTyped(evt);
            }
        });
        jPanel6.add(txtTelefono13, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 530, 250, 40));

        txtTelefono14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono14.setLabelText("Correo");
        txtTelefono14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefono14KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefono14KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefono14KeyTyped(evt);
            }
        });
        jPanel6.add(txtTelefono14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 250, 40));

        txtCorreo25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo25.setLabelText("Giro de ocupación");
        txtCorreo25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo25KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo25KeyReleased(evt);
            }
        });
        jPanel6.add(txtCorreo25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 575, 250, 40));

        txtCorreo26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCorreo26.setLabelText("Antigüedad");
        txtCorreo26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo26KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreo26KeyReleased(evt);
            }
        });
        jPanel6.add(txtCorreo26, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 575, 250, 40));

        jLabel37.setBackground(new java.awt.Color(0, 0, 204));
        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Referencia 2");
        jPanel6.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 140, -1));

        txtNombreComercial3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombreComercial3.setToolTipText("Nombre comercial");
        txtNombreComercial3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreComercial3KeyReleased(evt);
            }
        });
        jPanel6.add(txtNombreComercial3, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 50, 525, -1));

        txtCalleReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCalleReferencia3.setToolTipText("Calle");
        txtCalleReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCalleReferencia3KeyReleased(evt);
            }
        });
        jPanel6.add(txtCalleReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 80, 220, -1));

        txtCodigoPostalReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCodigoPostalReferencia3.setToolTipText("Código postal");
        txtCodigoPostalReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoPostalReferencia3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalReferencia3KeyTyped(evt);
            }
        });
        jPanel6.add(txtCodigoPostalReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 110, 230, -1));

        txtMunicipioReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtMunicipioReferencia3.setToolTipText("Municipio");
        txtMunicipioReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMunicipioReferencia3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMunicipioReferencia3KeyTyped(evt);
            }
        });
        jPanel6.add(txtMunicipioReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 140, 230, -1));

        txtExteriorReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtExteriorReferencia3.setToolTipText("Número exterior");
        txtExteriorReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExteriorReferencia3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExteriorReferencia3KeyTyped(evt);
            }
        });
        jPanel6.add(txtExteriorReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 80, -1));

        txtInteriorReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteriorReferencia3.setToolTipText("Número interior");
        txtInteriorReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteriorReferencia3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteriorReferencia3KeyTyped(evt);
            }
        });
        jPanel6.add(txtInteriorReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 80, 80, -1));

        txtColoniaReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtColoniaReferencia3.setToolTipText("Colonia");
        txtColoniaReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtColoniaReferencia3KeyReleased(evt);
            }
        });
        jPanel6.add(txtColoniaReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 110, 220, -1));

        txtCorreoReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCorreoReferencia3.setToolTipText("Correo electrónico");
        txtCorreoReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoReferencia3KeyReleased(evt);
            }
        });
        jPanel6.add(txtCorreoReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 170, 220, -1));

        txtGiroOcupacionReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtGiroOcupacionReferencia3.setToolTipText("Giro u ocupación");
        txtGiroOcupacionReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiroOcupacionReferencia3KeyReleased(evt);
            }
        });
        jPanel6.add(txtGiroOcupacionReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 200, 220, -1));

        txtAntiguedadReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtAntiguedadReferencia3.setToolTipText("Antigüedad");
        txtAntiguedadReferencia3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAntiguedadReferencia3ActionPerformed(evt);
            }
        });
        txtAntiguedadReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAntiguedadReferencia3KeyReleased(evt);
            }
        });
        jPanel6.add(txtAntiguedadReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 200, 230, -1));

        txttelefonoReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txttelefonoReferencia3.setToolTipText("Teléfono (10 digítos)");
        txttelefonoReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttelefonoReferencia3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelefonoReferencia3KeyTyped(evt);
            }
        });
        jPanel6.add(txttelefonoReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 170, 230, -1));

        txtEstadoReferencia3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtEstadoReferencia3.setToolTipText("Estado");
        txtEstadoReferencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEstadoReferencia3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoReferencia3KeyTyped(evt);
            }
        });
        jPanel6.add(txtEstadoReferencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 140, 220, -1));

        txtNombreComercial4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNombreComercial4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreComercial4KeyReleased(evt);
            }
        });
        jPanel6.add(txtNombreComercial4, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 260, 525, -1));

        txtCalleReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCalleReferencia4.setToolTipText("Calle");
        txtCalleReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCalleReferencia4KeyReleased(evt);
            }
        });
        jPanel6.add(txtCalleReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 290, 220, -1));

        txtExteriorReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtExteriorReferencia4.setToolTipText("Número exterior");
        txtExteriorReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExteriorReferencia4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExteriorReferencia4KeyTyped(evt);
            }
        });
        jPanel6.add(txtExteriorReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, 80, -1));

        txtInteriorReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtInteriorReferencia4.setToolTipText("Número interior");
        txtInteriorReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInteriorReferencia4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInteriorReferencia4KeyTyped(evt);
            }
        });
        jPanel6.add(txtInteriorReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 290, 80, -1));

        txtMunicipioReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtMunicipioReferencia4.setToolTipText("Municipio");
        txtMunicipioReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMunicipioReferencia4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMunicipioReferencia4KeyTyped(evt);
            }
        });
        jPanel6.add(txtMunicipioReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 350, 230, -1));

        txtCodigoPostalReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCodigoPostalReferencia4.setToolTipText("Código postal");
        txtCodigoPostalReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoPostalReferencia4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoPostalReferencia4KeyTyped(evt);
            }
        });
        jPanel6.add(txtCodigoPostalReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 320, 230, -1));

        txtEstadoReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtEstadoReferencia4.setToolTipText("Estado");
        txtEstadoReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEstadoReferencia4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoReferencia4KeyTyped(evt);
            }
        });
        jPanel6.add(txtEstadoReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 350, 220, -1));

        txttelefonoReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txttelefonoReferencia4.setToolTipText("Teléfono (10 digítos)");
        txttelefonoReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttelefonoReferencia4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelefonoReferencia4KeyTyped(evt);
            }
        });
        jPanel6.add(txttelefonoReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 380, 230, -1));

        txtAntiguedadReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtAntiguedadReferencia4.setToolTipText("Antigüedad");
        txtAntiguedadReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAntiguedadReferencia4KeyReleased(evt);
            }
        });
        jPanel6.add(txtAntiguedadReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 410, 230, -1));

        txtGiroOcupacionReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtGiroOcupacionReferencia4.setToolTipText("Giro u ocupación");
        txtGiroOcupacionReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiroOcupacionReferencia4KeyReleased(evt);
            }
        });
        jPanel6.add(txtGiroOcupacionReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 410, 220, -1));

        txtCorreoReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCorreoReferencia4.setToolTipText("Correo electrónico");
        txtCorreoReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoReferencia4KeyReleased(evt);
            }
        });
        jPanel6.add(txtCorreoReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 380, 220, -1));

        txtColoniaReferencia4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtColoniaReferencia4.setToolTipText("Colonia");
        txtColoniaReferencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtColoniaReferencia4KeyReleased(evt);
            }
        });
        jPanel6.add(txtColoniaReferencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 320, 220, -1));

        lbNombre44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre44.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre44.setText("Nombre");
        jPanel6.add(lbNombre44, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 53, 50, -1));

        lbNombre45.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre45.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre45.setText("Calle");
        jPanel6.add(lbNombre45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 83, 50, -1));

        lbNombre46.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre46.setText("Colonia");
        jPanel6.add(lbNombre46, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 113, 50, -1));

        lbNombre47.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre47.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre47.setText("Estado");
        jPanel6.add(lbNombre47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 143, 50, -1));

        lbNombre48.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre48.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre48.setText("Correo");
        jPanel6.add(lbNombre48, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 173, 50, -1));

        lbNombre49.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre49.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre49.setText("Giro");
        jPanel6.add(lbNombre49, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 203, 50, -1));

        lbNombre50.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre50.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre50.setText("Teléfono");
        jPanel6.add(lbNombre50, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 173, 67, -1));

        lbNombre51.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre51.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre51.setText("Municipio");
        jPanel6.add(lbNombre51, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 143, 67, -1));

        lbNombre52.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre52.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre52.setText("C.P.");
        jPanel6.add(lbNombre52, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 113, 67, -1));

        lbNombre53.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre53.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre53.setText("N. Exterior");
        jPanel6.add(lbNombre53, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 83, 67, -1));

        lbNombre54.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre54.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre54.setText("N. Interior");
        jPanel6.add(lbNombre54, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 83, 60, -1));

        lbNombre55.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre55.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre55.setText("Antigüedad");
        jPanel6.add(lbNombre55, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 203, 67, -1));

        lbNombre68.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre68.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre68.setText("Nombre");
        jPanel6.add(lbNombre68, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 263, 50, -1));

        lbNombre69.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre69.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre69.setText("Calle");
        jPanel6.add(lbNombre69, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 293, 50, -1));

        lbNombre70.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre70.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre70.setText("N. Exterior");
        jPanel6.add(lbNombre70, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 293, 67, -1));

        lbNombre71.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre71.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre71.setText("N. Interior");
        jPanel6.add(lbNombre71, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 293, 60, -1));

        lbNombre72.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre72.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre72.setText("C.P.");
        jPanel6.add(lbNombre72, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 323, 67, -1));

        lbNombre73.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre73.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre73.setText("Municipio");
        jPanel6.add(lbNombre73, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 353, 67, -1));

        lbNombre74.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre74.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre74.setText("Teléfono");
        jPanel6.add(lbNombre74, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 383, 67, -1));

        lbNombre75.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre75.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre75.setText("Antigüedad");
        jPanel6.add(lbNombre75, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 413, 67, -1));

        lbNombre76.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre76.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre76.setText("Giro");
        jPanel6.add(lbNombre76, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 413, 50, -1));

        lbNombre77.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre77.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre77.setText("Correo");
        jPanel6.add(lbNombre77, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 383, 50, -1));

        lbNombre78.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre78.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre78.setText("Estado");
        jPanel6.add(lbNombre78, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 353, 50, -1));

        lbNombre79.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbNombre79.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNombre79.setText("Colonia");
        jPanel6.add(lbNombre79, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 323, 50, -1));

        jTabbedPane1.addTab("Referencias Comerciales", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1KeyPressed

    private void txtNombre1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1KeyReleased

    private void txtNombre1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1KeyTyped

    private void txtCorreo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo1KeyPressed

    private void txtCorreo1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo1KeyReleased

    private void txtRegimenFiscal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal1KeyPressed

    private void txtRegimenFiscal1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal1KeyReleased

    private void txtRegimenFiscal1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal1KeyTyped

    private void txtRazonSocial1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocial1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocial1KeyPressed

    private void txtRazonSocial1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocial1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocial1KeyReleased

    private void txtNombre2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2KeyPressed

    private void txtNombre2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2KeyReleased

    private void txtNombre2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2KeyTyped

    private void txtNombre3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre3KeyPressed

    private void txtNombre3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre3KeyReleased

    private void txtNombre3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre3KeyTyped

    private void txtNombre4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre4KeyPressed

    private void txtNombre4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre4KeyReleased

    private void txtNombre4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre4KeyTyped

    private void txtNombre5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre5KeyPressed

    private void txtNombre5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre5KeyReleased

    private void txtNombre5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre5KeyTyped

    private void txtCorreo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo2KeyPressed

    private void txtCorreo2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo2KeyReleased

    private void txtCorreo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo3KeyPressed

    private void txtCorreo3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo3KeyReleased

    private void txtTelefono2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono2KeyPressed

    private void txtTelefono2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono2KeyReleased

    private void txtTelefono2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono2KeyTyped

    private void txtRazonSocial2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocial2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocial2KeyPressed

    private void txtRazonSocial2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocial2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocial2KeyReleased

    private void txtRegimenFiscal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal2KeyPressed

    private void txtRegimenFiscal2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal2KeyReleased

    private void txtRegimenFiscal2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal2KeyTyped

    private void txtNombre6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre6KeyPressed

    private void txtNombre6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre6KeyReleased

    private void txtNombre6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre6KeyTyped

    private void txtCorreo4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo4KeyPressed

    private void txtCorreo4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo4KeyReleased

    private void txtTelefono1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono1KeyPressed

    private void txtTelefono1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono1KeyReleased

    private void txtTelefono1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono1KeyTyped

    private void txtTelefono3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono3KeyPressed

    private void txtTelefono3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono3KeyReleased

    private void txtTelefono3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono3KeyTyped

    private void txtCorreo5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo5KeyPressed

    private void txtCorreo5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo5KeyReleased

    private void txtCorreo11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo11KeyPressed

    private void txtCorreo11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo11KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo11KeyReleased

    private void txtNombre14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre14KeyPressed

    private void txtNombre14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre14KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre14KeyReleased

    private void txtNombre14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre14KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre14KeyTyped

    private void txtRegimenFiscal9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal9KeyPressed

    private void txtRegimenFiscal9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal9KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal9KeyReleased

    private void txtRegimenFiscal9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal9KeyTyped

    private void txtRazonSocial9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocial9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocial9KeyPressed

    private void txtRazonSocial9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocial9KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocial9KeyReleased

    private void txtNombre20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre20KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre20KeyPressed

    private void txtNombre20KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre20KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre20KeyReleased

    private void txtNombre20KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre20KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre20KeyTyped

    private void txtCorreo21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo21KeyPressed

    private void txtCorreo21KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo21KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo21KeyReleased

    private void txtNombre21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre21KeyPressed

    private void txtNombre21KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre21KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre21KeyReleased

    private void txtNombre21KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre21KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre21KeyTyped

    private void txtTelefono11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefono11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono11ActionPerformed

    private void txtTelefono11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono11KeyPressed

    private void txtTelefono11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono11KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono11KeyReleased

    private void txtTelefono11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono11KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono11KeyTyped

    private void txtTelefono12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono12KeyPressed

    private void txtTelefono12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono12KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono12KeyReleased

    private void txtTelefono12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono12KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono12KeyTyped

    private void txtCorreo22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo22KeyPressed

    private void txtCorreo22KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo22KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo22KeyReleased

    private void txtCorreo23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo23KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo23KeyPressed

    private void txtCorreo23KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo23KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo23KeyReleased

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void btnAceptar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar2MouseClicked
        eventoEnter();
    }//GEN-LAST:event_btnAceptar2MouseClicked

    private void btnAceptar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar2MouseEntered
        panelAceptar2.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnAceptar2MouseEntered

    private void btnAceptar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptar2MouseExited
        panelAceptar2.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnAceptar2MouseExited

    private void btnAceptar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptar2KeyPressed

    private void panelAceptar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar2MouseClicked

    private void panelAceptar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAceptar2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelAceptar2MouseEntered

    private void btnLimpiar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar2MouseClicked
        eventoF1Credito();
    }//GEN-LAST:event_btnLimpiar2MouseClicked

    private void btnLimpiar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar2MouseEntered
        panelLimpiar2.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnLimpiar2MouseEntered

    private void btnLimpiar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiar2MouseExited
        panelLimpiar2.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnLimpiar2MouseExited

    private void btnLimpiar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiar2KeyPressed

    private void panelLimpiar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar2MouseClicked

    private void panelLimpiar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLimpiar2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelLimpiar2MouseEntered

    private void btnEliminar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar2MouseClicked
        dispose();
    }//GEN-LAST:event_btnEliminar2MouseClicked

    private void btnEliminar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar2MouseEntered
        panelEliminar2.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnEliminar2MouseEntered

    private void btnEliminar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar2MouseExited
        panelEliminar2.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnEliminar2MouseExited

    private void btnEliminar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar2KeyPressed

    private void panelEliminar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar2MouseClicked

    private void panelEliminar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEliminar2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEliminar2MouseEntered

    private void txtRegimenFiscal10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal10KeyPressed

    private void txtRegimenFiscal10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal10KeyReleased

    private void txtRegimenFiscal10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegimenFiscal10KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegimenFiscal10KeyTyped

    private void txtRazonSocial10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocial10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocial10KeyPressed

    private void txtRazonSocial10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocial10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocial10KeyReleased

    private void txtNombre22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre22KeyPressed

    private void txtNombre22KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre22KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre22KeyReleased

    private void txtNombre22KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre22KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre22KeyTyped

    private void txtCorreo24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo24KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo24KeyPressed

    private void txtCorreo24KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo24KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo24KeyReleased

    private void txtNombre23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre23KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre23KeyPressed

    private void txtNombre23KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre23KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre23KeyReleased

    private void txtNombre23KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre23KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre23KeyTyped

    private void txtTelefono13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefono13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono13ActionPerformed

    private void txtTelefono13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono13KeyPressed

    private void txtTelefono13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono13KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono13KeyReleased

    private void txtTelefono13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono13KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono13KeyTyped

    private void txtTelefono14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono14KeyPressed

    private void txtTelefono14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono14KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono14KeyReleased

    private void txtTelefono14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefono14KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono14KeyTyped

    private void txtCorreo25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo25KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo25KeyPressed

    private void txtCorreo25KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo25KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo25KeyReleased

    private void txtCorreo26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo26KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo26KeyPressed

    private void txtCorreo26KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo26KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo26KeyReleased

    private void txttelefonoCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoCreditoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txttelefonoCreditoKeyTyped

    private void txttelefonoReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoReferencia1KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txttelefonoReferencia1KeyTyped

    private void txttelefonoReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoReferencia2KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txttelefonoReferencia2KeyTyped

    private void txttelefonoReferencia4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoReferencia4KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txttelefonoReferencia4KeyTyped

    private void txttelefonoReferencia3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoReferencia3KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txttelefonoReferencia3KeyTyped

    private void txtCodigoPostalReferencia3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalReferencia3KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoPostalReferencia3KeyTyped

    private void txtCodigoPostalReferencia4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalReferencia4KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoPostalReferencia4KeyTyped

    private void txtCodigoPostalReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalReferencia1KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoPostalReferencia1KeyTyped

    private void txtCodigoPostalReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalReferencia2KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoPostalReferencia2KeyTyped

    private void txtCodigoPostalCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalCreditoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoPostalCreditoKeyTyped

    private void txtNombreCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCreditoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreCreditoKeyTyped

    private void txtApellidoPaternoCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoCreditoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtApellidoPaternoCreditoKeyTyped

    private void txtApellidoMaternoCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoCreditoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtApellidoMaternoCreditoKeyTyped

    private void txtMunicipioCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioCreditoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtMunicipioCreditoKeyTyped

    private void txtEstadoCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoCreditoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtEstadoCreditoKeyTyped

    private void txtGiroOcupacionCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiroOcupacionCreditoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtGiroOcupacionCreditoKeyTyped

    private void txtAcredatarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAcredatarioKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtAcredatarioKeyTyped

    private void txtVigenciaCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVigenciaCreditoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtVigenciaCreditoKeyTyped

    private void txtPlazoCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoCreditoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtPlazoCreditoKeyTyped

    private void txtLimiteCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLimiteCreditoKeyTyped
        event.numberDecimalKeyPress(evt, txtLimiteCredito);
    }//GEN-LAST:event_txtLimiteCreditoKeyTyped

    private void txtInteresMoratorioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteresMoratorioKeyTyped
        event.numberDecimalKeyPress(evt, txtInteresMoratorio);
    }//GEN-LAST:event_txtInteresMoratorioKeyTyped

    private void txtInteresOrdinarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteresOrdinarioKeyTyped
        event.numberDecimalKeyPress(evt, txtInteresOrdinario);
    }//GEN-LAST:event_txtInteresOrdinarioKeyTyped

    private void txtNombreReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreReferencia1KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreReferencia1KeyTyped

    private void txtApellidoPaternoReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoReferencia1KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtApellidoPaternoReferencia1KeyTyped

    private void txtApellidoMaternoReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoReferencia1KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtApellidoMaternoReferencia1KeyTyped

    private void txtNombreReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreReferencia2KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreReferencia2KeyTyped

    private void txtApellidoPaternoReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoReferencia2KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtApellidoPaternoReferencia2KeyTyped

    private void txtApellidoMaternoReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoReferencia2KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtApellidoMaternoReferencia2KeyTyped

    private void txtMunicipioReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioReferencia1KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtMunicipioReferencia1KeyTyped

    private void txtEstadoReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoReferencia1KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtEstadoReferencia1KeyTyped

    private void txtMunicipioReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioReferencia2KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtMunicipioReferencia2KeyTyped

    private void txtEstadoReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoReferencia2KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtEstadoReferencia2KeyTyped

    private void txtMunicipioReferencia3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioReferencia3KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtMunicipioReferencia3KeyTyped

    private void txtEstadoReferencia3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoReferencia3KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtEstadoReferencia3KeyTyped

    private void txtMunicipioReferencia4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioReferencia4KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtMunicipioReferencia4KeyTyped

    private void txtEstadoReferencia4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoReferencia4KeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtEstadoReferencia4KeyTyped

    private void txtNombreCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtNombreCreditoKeyReleased

    private void txtApellidoPaternoCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtApellidoPaternoCreditoKeyReleased

    private void txtApellidoMaternoCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtApellidoMaternoCreditoKeyReleased

    private void txtCalleCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCalleCreditoKeyReleased

    private void txtCodigoPostalCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCodigoPostalCreditoKeyReleased

    private void txtInteriorCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtInteriorCreditoKeyReleased

    private void txtExteriorCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtExteriorCreditoKeyReleased

    private void txtMunicipioCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtMunicipioCreditoKeyReleased

    private void txtColoniaCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtColoniaCreditoKeyReleased

    private void txtEstadoCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtEstadoCreditoKeyReleased

    private void txtCorreoCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCorreoCreditoKeyReleased

    private void txttelefonoCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txttelefonoCreditoKeyReleased

    private void txtGiroOcupacionCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiroOcupacionCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtGiroOcupacionCreditoKeyReleased

    private void txtAntiguedadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAntiguedadKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtAntiguedadKeyReleased

    private void txtVigenciaCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVigenciaCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtVigenciaCreditoKeyReleased

    private void txtAcredatarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAcredatarioKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtAcredatarioKeyReleased

    private void txtPlazoCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtPlazoCreditoKeyReleased

    private void txtDocumentoAcreditacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocumentoAcreditacionKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtDocumentoAcreditacionKeyReleased

    private void txtLimiteCreditoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLimiteCreditoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtLimiteCreditoKeyReleased

    private void txtInteresMoratorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteresMoratorioKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtInteresMoratorioKeyReleased

    private void txtInteresOrdinarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteresOrdinarioKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_F1:
                eventoF1Credito();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtInteresOrdinarioKeyReleased

    private void txtNombreReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtNombreReferencia1KeyReleased

    private void txtApellidoPaternoReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtApellidoPaternoReferencia1KeyReleased

    private void txtApellidoMaternoReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtApellidoMaternoReferencia1KeyReleased

    private void txtCalleReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCalleReferencia1KeyReleased

    private void txtCodigoPostalReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCodigoPostalReferencia1KeyReleased

    private void txtExteriorReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtExteriorReferencia1KeyReleased

    private void txtInteriorReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtInteriorReferencia1KeyReleased

    private void txtMunicipioReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtMunicipioReferencia1KeyReleased

    private void txtColoniaReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtColoniaReferencia1KeyReleased

    private void txtEstadoReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtEstadoReferencia1KeyReleased

    private void txtCorreoReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCorreoReferencia1KeyReleased

    private void txttelefonoReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txttelefonoReferencia1KeyReleased

    private void txtGiroOcupacionReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiroOcupacionReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtGiroOcupacionReferencia1KeyReleased

    private void txtAntiguedadReferencia1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAntiguedadReferencia1KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtAntiguedadReferencia1KeyReleased

    private void txtNombreReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtNombreReferencia2KeyReleased

    private void txtApellidoPaternoReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtApellidoPaternoReferencia2KeyReleased

    private void txtApellidoMaternoReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtApellidoMaternoReferencia2KeyReleased

    private void txtCalleReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCalleReferencia2KeyReleased

    private void txtCodigoPostalReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCodigoPostalReferencia2KeyReleased

    private void txtInteriorReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtInteriorReferencia2KeyReleased

    private void txtExteriorReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtExteriorReferencia2KeyReleased

    private void txtMunicipioReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtMunicipioReferencia2KeyReleased

    private void txtColoniaReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtColoniaReferencia2KeyReleased

    private void txtEstadoReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtEstadoReferencia2KeyReleased

    private void txtCorreoReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCorreoReferencia2KeyReleased

    private void txttelefonoReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txttelefonoReferencia2KeyReleased

    private void txtGiroOcupacionReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiroOcupacionReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtGiroOcupacionReferencia2KeyReleased

    private void txtAntiguedadReferencia2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAntiguedadReferencia2KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtAntiguedadReferencia2KeyReleased

    private void txtNombreComercial3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComercial3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtNombreComercial3KeyReleased

    private void txtCalleReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCalleReferencia3KeyReleased

    private void txtCodigoPostalReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCodigoPostalReferencia3KeyReleased

    private void txtInteriorReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtInteriorReferencia3KeyReleased

    private void txtExteriorReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtExteriorReferencia3KeyReleased

    private void txtMunicipioReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtMunicipioReferencia3KeyReleased

    private void txtColoniaReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtColoniaReferencia3KeyReleased

    private void txtEstadoReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtEstadoReferencia3KeyReleased

    private void txtCorreoReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCorreoReferencia3KeyReleased

    private void txttelefonoReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txttelefonoReferencia3KeyReleased

    private void txtGiroOcupacionReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiroOcupacionReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtGiroOcupacionReferencia3KeyReleased

    private void txtAntiguedadReferencia3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAntiguedadReferencia3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAntiguedadReferencia3ActionPerformed

    private void txtAntiguedadReferencia3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAntiguedadReferencia3KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtAntiguedadReferencia3KeyReleased

    private void txtNombreComercial4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComercial4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtNombreComercial4KeyReleased

    private void txtCalleReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCalleReferencia4KeyReleased

    private void txtCodigoPostalReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCodigoPostalReferencia4KeyReleased

    private void txtInteriorReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtInteriorReferencia4KeyReleased

    private void txtExteriorReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtExteriorReferencia4KeyReleased

    private void txtMunicipioReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMunicipioReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtMunicipioReferencia4KeyReleased

    private void txtColoniaReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtColoniaReferencia4KeyReleased

    private void txtEstadoReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtEstadoReferencia4KeyReleased

    private void txtCorreoReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtCorreoReferencia4KeyReleased

    private void txttelefonoReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txttelefonoReferencia4KeyReleased

    private void txtGiroOcupacionReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiroOcupacionReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtGiroOcupacionReferencia4KeyReleased

    private void txtAntiguedadReferencia4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAntiguedadReferencia4KeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case KeyEvent.VK_ENTER:
                eventoEnter();
                break;

            case KeyEvent.VK_ESCAPE:
                dispose();
                break;

        }
    }//GEN-LAST:event_txtAntiguedadReferencia4KeyReleased

    private void txtInteresOrdinarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInteresOrdinarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInteresOrdinarioActionPerformed

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        eventoEnter();
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
        panelAceptar.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnAceptarMouseEntered

    private void btnAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseExited
        panelAceptar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnAceptarMouseExited

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
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

        panelLimpiar.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnLimpiarMouseEntered

    private void btnLimpiarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseExited
        panelLimpiar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnLimpiarMouseExited

    private void btnLimpiarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLimpiarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        panelEliminar.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        panelEliminar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        if (selectFisica.isSelected()) {
            lbSociedad.setText("Sociedad");
            txtNombre.setVisible(true);
            txtApellidoPaterno.setVisible(true);
            txtApellidoMaterno.setVisible(true);
            txtRazonSocial.setVisible(false);
            txtTipoSociedad.setEnabled(false);
            txtTipoSociedad.setBackground(new Color(240, 240, 240));
            selectMoral.setSelected(false);
            JTextFieldUtils.configureTextField(txtRfc, false);
            txtTipoSociedad.setText("");
            txtRazonSocial.setText("");
        } else {
            txtNombre.setText("");
            txtApellidoMaterno.setText("");
            txtApellidoPaterno.setText("");
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        if (selectMoral.isSelected()) {
            lbSociedad.setText("Sociedad*");
            txtNombre.setVisible(false);
            txtApellidoPaterno.setVisible(false);
            txtApellidoMaterno.setVisible(false);
            txtRazonSocial.setVisible(true);
            txtTipoSociedad.setEnabled(true);
            txtTipoSociedad.setBackground(new Color(255, 250, 250));
            selectFisica.setSelected(false);
            JTextFieldUtils.configureTextField(txtRfc, true);
            txtNombre.setText("");
            txtApellidoMaterno.setText("");
            txtApellidoPaterno.setText("");
        } else {
            txtTipoSociedad.setText("");
            txtRazonSocial.setText("");
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        if (!txtRfc.getText().equals("")) {
            lbRFC.setText("RFC*");
            LBREGIMEM.setText("Régimen*");
            lbCFDI.setText("CFDI*");
        } else {
            lbRFC.setText("RFC");
            LBREGIMEM.setText("Régimen");
            lbCFDI.setText("CFDI");
        }
    }//GEN-LAST:event_txtRfcKeyReleased

    private void txtTelefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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

        if (!txtCalle.getText().equals("")) {
            lbCalle.setText("Calle*");
            lbInterior.setText("N.Exterior*");
            lbColonia.setText("Colonia*");
            lbEstado.setText("Estado*");
            lbCP.setText("C.P.*");
            lbMunicipio.setText("Municip*");
        } else {
            lbCalle.setText("Calle");
            lbInterior.setText("N.Exterior");
            lbColonia.setText("Colonia");
            lbEstado.setText("Estado");
            lbCP.setText("C.P.");
            lbMunicipio.setText("Municipio");
        }
    }//GEN-LAST:event_txtCalleKeyReleased

    private void txtCodigoPostalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoPostalKeyReleased
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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
        int codigo = evt.getKeyCode();
        switch (codigo) {
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

    private void txtRazonSocialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSocialKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSocialKeyTyped

    private void txtInteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtInteriorKeyTyped

    private void txtExteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtExteriorKeyTyped

    private void txtInteriorCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorCreditoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtInteriorCreditoKeyTyped

    private void txtExteriorCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorCreditoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtExteriorCreditoKeyTyped

    private void txtInteriorReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorReferencia1KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtInteriorReferencia1KeyTyped

    private void txtExteriorReferencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorReferencia1KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtExteriorReferencia1KeyTyped

    private void txtInteriorReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorReferencia2KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtInteriorReferencia2KeyTyped

    private void txtExteriorReferencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorReferencia2KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtExteriorReferencia2KeyTyped

    private void txtInteriorReferencia3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorReferencia3KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtInteriorReferencia3KeyTyped

    private void txtExteriorReferencia3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorReferencia3KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtExteriorReferencia3KeyTyped

    private void txtInteriorReferencia4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInteriorReferencia4KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtInteriorReferencia4KeyTyped

    private void txtExteriorReferencia4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExteriorReferencia4KeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtExteriorReferencia4KeyTyped

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
            java.util.logging.Logger.getLogger(CrearModificarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearModificarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearModificarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearModificarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CrearModificarCliente dialog = new CrearModificarCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel LBApellidoPaterno2;
    private javax.swing.JLabel LBApellidoPaterno3;
    private javax.swing.JLabel LBApellidoPaterno4;
    private javax.swing.JLabel LBApellidoPaterno5;
    private javax.swing.JLabel LBApellidoPaterno6;
    private javax.swing.JLabel LBApellidoPaterno7;
    private javax.swing.JLabel LBApellidoPaterno8;
    private javax.swing.JLabel LBApellidoPaterno9;
    private javax.swing.JLabel LBREGIMEM;
    private javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnAceptar2;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JLabel btnEliminar2;
    private javax.swing.JLabel btnLimpiar;
    private javax.swing.JLabel btnLimpiar2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
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
    private javax.swing.JLabel lbNombre16;
    private javax.swing.JLabel lbNombre17;
    private javax.swing.JLabel lbNombre18;
    private javax.swing.JLabel lbNombre19;
    private javax.swing.JLabel lbNombre20;
    private javax.swing.JLabel lbNombre21;
    private javax.swing.JLabel lbNombre22;
    private javax.swing.JLabel lbNombre23;
    private javax.swing.JLabel lbNombre24;
    private javax.swing.JLabel lbNombre25;
    private javax.swing.JLabel lbNombre26;
    private javax.swing.JLabel lbNombre27;
    private javax.swing.JLabel lbNombre28;
    private javax.swing.JLabel lbNombre29;
    private javax.swing.JLabel lbNombre3;
    private javax.swing.JLabel lbNombre30;
    private javax.swing.JLabel lbNombre31;
    private javax.swing.JLabel lbNombre32;
    private javax.swing.JLabel lbNombre33;
    private javax.swing.JLabel lbNombre34;
    private javax.swing.JLabel lbNombre35;
    private javax.swing.JLabel lbNombre36;
    private javax.swing.JLabel lbNombre37;
    private javax.swing.JLabel lbNombre38;
    private javax.swing.JLabel lbNombre39;
    private javax.swing.JLabel lbNombre4;
    private javax.swing.JLabel lbNombre40;
    private javax.swing.JLabel lbNombre41;
    private javax.swing.JLabel lbNombre42;
    private javax.swing.JLabel lbNombre43;
    private javax.swing.JLabel lbNombre44;
    private javax.swing.JLabel lbNombre45;
    private javax.swing.JLabel lbNombre46;
    private javax.swing.JLabel lbNombre47;
    private javax.swing.JLabel lbNombre48;
    private javax.swing.JLabel lbNombre49;
    private javax.swing.JLabel lbNombre50;
    private javax.swing.JLabel lbNombre51;
    private javax.swing.JLabel lbNombre52;
    private javax.swing.JLabel lbNombre53;
    private javax.swing.JLabel lbNombre54;
    private javax.swing.JLabel lbNombre55;
    private javax.swing.JLabel lbNombre56;
    private javax.swing.JLabel lbNombre57;
    private javax.swing.JLabel lbNombre58;
    private javax.swing.JLabel lbNombre59;
    private javax.swing.JLabel lbNombre60;
    private javax.swing.JLabel lbNombre61;
    private javax.swing.JLabel lbNombre62;
    private javax.swing.JLabel lbNombre63;
    private javax.swing.JLabel lbNombre64;
    private javax.swing.JLabel lbNombre65;
    private javax.swing.JLabel lbNombre66;
    private javax.swing.JLabel lbNombre67;
    private javax.swing.JLabel lbNombre68;
    private javax.swing.JLabel lbNombre69;
    private javax.swing.JLabel lbNombre70;
    private javax.swing.JLabel lbNombre71;
    private javax.swing.JLabel lbNombre72;
    private javax.swing.JLabel lbNombre73;
    private javax.swing.JLabel lbNombre74;
    private javax.swing.JLabel lbNombre75;
    private javax.swing.JLabel lbNombre76;
    private javax.swing.JLabel lbNombre77;
    private javax.swing.JLabel lbNombre78;
    private javax.swing.JLabel lbNombre79;
    private javax.swing.JLabel lbRFC;
    private javax.swing.JLabel lbSociedad;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelAceptar2;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelEliminar2;
    private javax.swing.JPanel panelLimpiar;
    private javax.swing.JPanel panelLimpiar2;
    private javax.swing.JCheckBox selectFisica;
    private javax.swing.JCheckBox selectMoral;
    private javax.swing.JTextField txtAcredatario;
    private javax.swing.JTextField txtAntiguedad;
    private javax.swing.JTextField txtAntiguedadReferencia1;
    private javax.swing.JTextField txtAntiguedadReferencia2;
    private javax.swing.JTextField txtAntiguedadReferencia3;
    private javax.swing.JTextField txtAntiguedadReferencia4;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoMaternoCredito;
    private javax.swing.JTextField txtApellidoMaternoReferencia1;
    private javax.swing.JTextField txtApellidoMaternoReferencia2;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtApellidoPaternoCredito;
    private javax.swing.JTextField txtApellidoPaternoReferencia1;
    private javax.swing.JTextField txtApellidoPaternoReferencia2;
    private javax.swing.JComboBox<String> txtCFDI;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCalleCredito;
    private javax.swing.JTextField txtCalleReferencia1;
    private javax.swing.JTextField txtCalleReferencia2;
    private javax.swing.JTextField txtCalleReferencia3;
    private javax.swing.JTextField txtCalleReferencia4;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtCodigoPostalCredito;
    private javax.swing.JTextField txtCodigoPostalReferencia1;
    private javax.swing.JTextField txtCodigoPostalReferencia2;
    private javax.swing.JTextField txtCodigoPostalReferencia3;
    private javax.swing.JTextField txtCodigoPostalReferencia4;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtColoniaCredito;
    private javax.swing.JTextField txtColoniaReferencia1;
    private javax.swing.JTextField txtColoniaReferencia2;
    private javax.swing.JTextField txtColoniaReferencia3;
    private javax.swing.JTextField txtColoniaReferencia4;
    private javax.swing.JTextField txtCorreo;
    private textfield.TextField txtCorreo1;
    private textfield.TextField txtCorreo11;
    private textfield.TextField txtCorreo2;
    private textfield.TextField txtCorreo21;
    private textfield.TextField txtCorreo22;
    private textfield.TextField txtCorreo23;
    private textfield.TextField txtCorreo24;
    private textfield.TextField txtCorreo25;
    private textfield.TextField txtCorreo26;
    private textfield.TextField txtCorreo3;
    private textfield.TextField txtCorreo4;
    private textfield.TextField txtCorreo5;
    private javax.swing.JTextField txtCorreoCredito;
    private javax.swing.JTextField txtCorreoReferencia1;
    private javax.swing.JTextField txtCorreoReferencia2;
    private javax.swing.JTextField txtCorreoReferencia3;
    private javax.swing.JTextField txtCorreoReferencia4;
    private javax.swing.JTextField txtDocumentoAcreditacion;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtEstadoCredito;
    private javax.swing.JTextField txtEstadoReferencia1;
    private javax.swing.JTextField txtEstadoReferencia2;
    private javax.swing.JTextField txtEstadoReferencia3;
    private javax.swing.JTextField txtEstadoReferencia4;
    private javax.swing.JTextField txtExterior;
    private javax.swing.JTextField txtExteriorCredito;
    private javax.swing.JTextField txtExteriorReferencia1;
    private javax.swing.JTextField txtExteriorReferencia2;
    private javax.swing.JTextField txtExteriorReferencia3;
    private javax.swing.JTextField txtExteriorReferencia4;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFechaCrediito;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JTextField txtFolioCredito;
    private javax.swing.JTextField txtGiroOcupacionCredito;
    private javax.swing.JTextField txtGiroOcupacionReferencia1;
    private javax.swing.JTextField txtGiroOcupacionReferencia2;
    private javax.swing.JTextField txtGiroOcupacionReferencia3;
    private javax.swing.JTextField txtGiroOcupacionReferencia4;
    private javax.swing.JTextField txtInteresMoratorio;
    private javax.swing.JTextField txtInteresOrdinario;
    private javax.swing.JTextField txtInterior;
    private javax.swing.JTextField txtInteriorCredito;
    private javax.swing.JTextField txtInteriorReferencia1;
    private javax.swing.JTextField txtInteriorReferencia2;
    private javax.swing.JTextField txtInteriorReferencia3;
    private javax.swing.JTextField txtInteriorReferencia4;
    private javax.swing.JTextField txtLimiteCredito;
    private javax.swing.JTextField txtMunicipio;
    private javax.swing.JTextField txtMunicipioCredito;
    private javax.swing.JTextField txtMunicipioReferencia1;
    private javax.swing.JTextField txtMunicipioReferencia2;
    private javax.swing.JTextField txtMunicipioReferencia3;
    private javax.swing.JTextField txtMunicipioReferencia4;
    private javax.swing.JTextField txtNombre;
    private textfield.TextField txtNombre1;
    private textfield.TextField txtNombre14;
    private textfield.TextField txtNombre2;
    private textfield.TextField txtNombre20;
    private textfield.TextField txtNombre21;
    private textfield.TextField txtNombre22;
    private textfield.TextField txtNombre23;
    private textfield.TextField txtNombre3;
    private textfield.TextField txtNombre4;
    private textfield.TextField txtNombre5;
    private textfield.TextField txtNombre6;
    private javax.swing.JTextField txtNombreComercial3;
    private javax.swing.JTextField txtNombreComercial4;
    private javax.swing.JTextField txtNombreCredito;
    private javax.swing.JTextField txtNombreReferencia1;
    private javax.swing.JTextField txtNombreReferencia2;
    private javax.swing.JTextField txtPlazoCredito;
    private javax.swing.JTextField txtRazonSocial;
    private textfield.TextField txtRazonSocial1;
    private textfield.TextField txtRazonSocial10;
    private textfield.TextField txtRazonSocial2;
    private textfield.TextField txtRazonSocial9;
    private javax.swing.JComboBox<String> txtRegimenFiscal;
    private textfield.TextField txtRegimenFiscal1;
    private textfield.TextField txtRegimenFiscal10;
    private textfield.TextField txtRegimenFiscal2;
    private textfield.TextField txtRegimenFiscal9;
    private javax.swing.JTextField txtRfc;
    private javax.swing.JTextField txtTelefono;
    private textfield.TextField txtTelefono1;
    private textfield.TextField txtTelefono11;
    private textfield.TextField txtTelefono12;
    private textfield.TextField txtTelefono13;
    private textfield.TextField txtTelefono14;
    private textfield.TextField txtTelefono2;
    private textfield.TextField txtTelefono3;
    private javax.swing.JTextField txtTipoSociedad;
    private javax.swing.JTextField txtVigenciaCredito;
    private javax.swing.JTextField txttelefonoCredito;
    private javax.swing.JTextField txttelefonoReferencia1;
    private javax.swing.JTextField txttelefonoReferencia2;
    private javax.swing.JTextField txttelefonoReferencia3;
    private javax.swing.JTextField txttelefonoReferencia4;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
    }

}

class JTextFieldUtils {

    public static void configureTextField(JTextField textField, boolean allow13Characters) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null) {
                    text = text.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
                }

                int maxLength = allow13Characters ? 12 : 13;
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength + text.length()) - maxLength;
                if (overLimit > 0) {
                    text = text.substring(0, text.length() - overLimit);
                }

                super.replace(fb, offset, length, text, attrs);
            }
        });
    }
}
