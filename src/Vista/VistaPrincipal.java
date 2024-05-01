/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Credito;
import Modelo.CreditoDao;
import Modelo.Empleados;
import Modelo.EmpleadosDao;
import Modelo.HistorialCliente;
import Modelo.HistorialClienteDao;
import Modelo.PagosDao;
import Modelo.Perfil;
import Modelo.PerfilDao;
import Modelo.Producto;
import Modelo.ProductoDao;
import Modelo.Respaldos;
import Modelo.Ventas;
import Modelo.VentasDao;
import Modelo.bitacoraDao;
import Modelo.configuraciones;
import Modelo.notaCreditoDao;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import javaswingdev.GradientDropdownMenu;
import javaswingdev.MenuEvent;
import javaswingdev.Notification;

/**
 *
 * @author Jonathan Gil
 */
public class VistaPrincipal extends javax.swing.JFrame implements Runnable {

    Empleados e = new Empleados();
    PerfilDao perfilDao = new PerfilDao();
    EmpleadosDao empleadosDao = new EmpleadosDao();
    CreditoDao creditoDao = new CreditoDao();
    ProductoDao productoDao = new ProductoDao();
    VentasDao ventaDao = new VentasDao();
    bitacoraDao bitacora = new bitacoraDao();
    configuraciones config = new configuraciones();
    PagosDao pagosDao = new PagosDao();
    notaCreditoDao notasDao = new notaCreditoDao();
    HistorialClienteDao historialDao = new HistorialClienteDao();

    DecimalFormat formateador = new DecimalFormat("#,###,##0.00");
    DecimalFormat df = new DecimalFormat("$ #,##0.00;($ #,##0.00)");
    DefaultListModel model;
    String horas, min, seg, ampm, diaActual;
    Calendar calendario;
    Thread hi;
    Perfil perfil;

    public VistaPrincipal() {
        initComponents();
        //this.setLocation(-5, 0);
        this.setTitle("Punto de venta");
        Seticon();
        model = new DefaultListModel();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;

        int width = screenSize.width;
        int height = screenSize.height - taskBarSize;

        this.setSize(width, height);
        this.setLocation(0, 0);
        hi = new Thread(this);
        hi.start();
        bitacora.borrarRegistros();

        GradientDropdownMenu menu = new GradientDropdownMenu();
        menu.setBackground(new Color(0, 0, 102));
        menu.setGradientColor(new Color(0, 0, 204), new Color(51, 51, 255));
        menu.addItem("Ventas ", "Nueva venta", "Control de ventas", "Cotizaciones");
        menu.addItem("Catálogo ", "Proveedores", "Usuarios", "Productos", "Clientes", "Departamentos", "Lineas", "Gastos", "Perfiles", "Créditos");
        menu.addItem("Control ", "Corte de venta", "Bitácora", "Inventario", "Ordenes de compra", "Factura");
        menu.addItem("Configuraciones ", "Datos de la empresa", "Respaldar datos", "Restaurar datos", "Cerrar Sesión", "Licencia");
        menu.addItem("Reportes ", "Ventas", "Notas de crédito", "Cobranza", "Productos", "Inventario", "Corte diario", "Concentrado general", "Datos de clientes", "Producto más vendido");
        menu.setFont(new java.awt.Font("sansserif", 1, 13));
        menu.setMenuHeight(30);
        menu.setFont(new java.awt.Font("Tahoma", 1, 13));
        menu.setHeaderGradient(false);
        menu.applay(this);

        menu.addEvent(new MenuEvent() {
            @Override
            public void selected(int index, int subIndex, boolean menuItem) {
                if (menuItem) {
                    switch (index) {
                        case 0:
                            switch (subIndex) {
                                case 1:
                                    if (perfil.getNuevaVenta() == 1) {
                                        vistaVenta vC1 = new vistaVenta(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 50);
                                        vC1.validandoDatos(0, 1);
                                        vC1.vaciarEmpleado(e.getId());
                                        vC1.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 2:
                                    if (perfil.getControlVentas() == 1) {
                                        VisualizarVentas vn = new VisualizarVentas(new javax.swing.JFrame(), true);
                                        vn.vaciarEmpleado(e);
                                        vn.setLocation(150, 51);
                                        vn.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 3:
                                    if (perfil.getCotizaciones() == 1) {
                                        VisualizarCotizacion vn = new VisualizarCotizacion(new javax.swing.JFrame(), true);
                                        vn.vaciarEmpleado(e);
                                        vn.setLocation(150, 51);
                                        vn.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                            }
                            break;

                        case 1:
                            switch (subIndex) {
                                case 1:
                                    if (perfil.getProveedores() == 1) {
                                        vistaProveedores vC1 = new vistaProveedores(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 51);
                                        vC1.vaciarEmpleado(e);
                                        vC1.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 2:
                                    if (perfil.getEmpleados() == 1) {
                                        vistaEmpleados vC1 = new vistaEmpleados(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 51);
                                        vC1.vaciarEmpleado(e);
                                        vC1.setVisible(true);
                                        e = empleadosDao.BuscarPorCodigo(e.getId());
                                        perfil = perfilDao.BuscarPorCodigo(e.getIdPerfil());
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }

                                    break;
                                case 3:
                                    if (perfil.getProductos() == 1) {
                                        vistaProducto vp = new vistaProducto(new javax.swing.JFrame(), true);
                                        vp.setLocation(200, 51);
                                        vp.vaciarEmpleado(e);
                                        vp.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 4:
                                    if (perfil.getClientes() == 1) {
                                        vistaClientes vC1 = new vistaClientes(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 51);
                                        vC1.vaciarEmpleado(e);
                                        vC1.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 5:
                                    if (perfil.getDepartamento() == 1) {
                                        vistaDepartamento vC1 = new vistaDepartamento(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 51);
                                        vC1.vaciarEmpleado(e);
                                        vC1.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 6:
                                    if (perfil.getLinea() == 1) {
                                        vistaLinea vC1 = new vistaLinea(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 51);
                                        vC1.vaciarEmpleado(e);
                                        vC1.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 7:
                                    if (perfil.getGastos() == 1) {
                                        vistaGastos vG = new vistaGastos(new javax.swing.JFrame(), true);
                                        vG.setLocation(200, 51);
                                        vG.vaciarEmpleado(e);
                                        vG.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 8:
                                    if (perfil.getPerfil() == 1) {
                                        VistaPerfil vperfil = new VistaPerfil(new javax.swing.JFrame(), true);
                                        vperfil.setLocation(200, 51);
                                        vperfil.vaciarEmpleado(e);
                                        vperfil.setVisible(true);
                                        perfil = perfilDao.BuscarPorCodigo(e.getIdPerfil());
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 9:
                                    if (perfil.getCredito() == 1) {
                                        vistaCreditos vc = new vistaCreditos(new javax.swing.JFrame(), true);
                                        vc.setLocation(150, 51);
                                        vc.vaciarEmpleado(e);
                                        vc.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                            }
                            break;

                        case 2:
                            switch (subIndex) {
                                case 1:
                                    if (perfil.getCorteVenta() == 1) {
                                        vistaCorte vc = new vistaCorte(new javax.swing.JFrame(), true);
                                        vc.setLocation(200, 51);
                                        vc.vaciarEmpleado(e);
                                        vc.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 2:
                                    if (perfil.getBitacora() == 1) {
                                        vistaBitacora vC1 = new vistaBitacora(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 51);
                                        vC1.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 3:
                                    if (perfil.getInventario() == 1) {
                                        vistaInventario vi = new vistaInventario(new javax.swing.JFrame(), true);
                                        vi.setLocation(200, 51);
                                        vi.vaciarEmpleado(e);
                                        vi.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 4:
                                    if (perfil.getOrdenesCompra() == 1) {
                                        vistaOrdenCompra vo = new vistaOrdenCompra(new javax.swing.JFrame(), true);
                                        vo.setLocation(200, 51);
                                        vo.vaciarEmpleado(e);
                                        vo.setVisible(true);
                                        vaciarListaCreditos();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;

                                case 5:
                                    if (perfil.getFacturando() == 1) {
                                        vitaGenerarFactura vC1 = new vitaGenerarFactura(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 51);
                                        vC1.vaciarEmpleado(e);
                                        vC1.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;

                            }
                            break;
                        case 3:

                            switch (subIndex) {
                                case 1:
                                    if (perfil.getDatosEmpresa() == 1) {
                                        DatosEmpresa vC = new DatosEmpresa(new javax.swing.JFrame(), true);
                                        vC.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 2:
                                    if (perfil.getRespaldar() == 1) {
                                        Respaldos r = new Respaldos();
                                        r.respaldo();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;

                                case 3:
                                    if (perfil.getRestaurar() == 1) {
                                        Respaldos ra = new Respaldos();
                                        ra.restaurar();
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 4:
                                    vistaConfirmacion vC = new vistaConfirmacion(new javax.swing.JFrame(), true);
                                    vC.setVisible(true);
                                    if (vC.accionRealizada == true) {
                                        Login lg = new Login();
                                        dispose();
                                        lg.setVisible(true);
                                    }
                                    break;
                                case 5:
                                    ContraseñaConUsuario vCU = new ContraseñaConUsuario(new javax.swing.JFrame(), true);
                                    vCU.paraLicencia();
                                    vCU.setVisible(true);
                                    if (vCU.contraseñaAceptada == true) {
                                        abrirReporteDiario cvR = new abrirReporteDiario(new javax.swing.JFrame(), true);
                                        cvR.setVisible(true);
                                        if (cvR.accionRealizada == 1) {
                                            config.cambiarLicencia(cvR.fechaAUsar);
                                            Login lg = new Login();
                                            Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Licencia actualizada");
                                            panel.showNotification();
                                            dispose();
                                            lg.setVisible(true);

                                        }
                                    }
                                    break;

                            }

                            break;

                        case 4:
                            switch (subIndex) {
                                case 1://ventas
                                    if (perfil.getVentasR() == 1) {
                                        vistaReportesVentas vC1 = new vistaReportesVentas(new javax.swing.JFrame(), true);
                                        vC1.setLocation(200, 51);
                                        vC1.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 2://notas de credito
                                    if (perfil.getNotasCreditoR() == 1) {
                                        vistaReportesNotasCredito ncr = new vistaReportesNotasCredito(new javax.swing.JFrame(), true);
                                        ncr.setLocation(150, 51);
                                        ncr.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 3://cobranza
                                    if (perfil.getCobranzaR() == 1) {
                                        vistaReportesCobranza vCC = new vistaReportesCobranza(new javax.swing.JFrame(), true);
                                        vCC.setLocation(200, 51);
                                        vCC.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 4://Productos
                                    if (perfil.getProductosR() == 1) {
                                        vistaReportesProductos vCp = new vistaReportesProductos(new javax.swing.JFrame(), true);
                                        vCp.setLocation(200, 51);
                                        vCp.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 5://inventario
                                    if (perfil.getInventarioR() == 1) {
                                        vistaReportesInventario vCi = new vistaReportesInventario(new javax.swing.JFrame(), true);
                                        vCi.setLocation(200, 51);
                                        vCi.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 6://corte
                                    abrirReporteDiario cvR = new abrirReporteDiario(new javax.swing.JFrame(), true);
                                    cvR.setVisible(true);
                                    if (cvR.accionRealizada == 1) {
                                        try {
                                            vistaCorte vc = new vistaCorte(new javax.swing.JFrame(), true);
                                            vc.vaciarEmpleado(e);
                                            vc.corteDiafinal(cvR.fechaAUsar);
                                        } catch (DocumentException ex) {
                                            Logger.getLogger(VistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    break;
                                case 7://corte
                                    if (perfil.getConcentradoGeneralR() == 1) {
                                        vistaReportesConcentradoGeneral cvc = new vistaReportesConcentradoGeneral(new javax.swing.JFrame(), true);
                                        cvc.setLocation(200, 51);
                                        cvc.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;

                                case 8://corte
                                    if (perfil.getDatosClientesR() == 1) {
                                        vistaReportesClientes cvC = new vistaReportesClientes(new javax.swing.JFrame(), true);
                                        cvC.setLocation(200, 51);
                                        cvC.setVisible(true);
                                    } else {
                                        Notification panel = new Notification(new javax.swing.JFrame(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Permiso denegado");
                                        panel.showNotification();
                                    }
                                    break;
                                case 9:
                                    vistaProductoMasUsado vpms = new vistaProductoMasUsado(new javax.swing.JFrame(), true);
                                    vpms.setLocation(200, 51);
                                    vpms.vaciarEmpleado(e);
                                    vpms.setVisible(true);

                            }
                            break;

                    }
                }
            }
        });

    }

    public void vaciarListaCreditos() {
        System.out.println("nombre: " + e.getNombre());
        List<Credito> lscreditos = creditoDao.listarCreditos(); // Obtén tu lista de créditos desde la base de datos
        DefaultListModel model = new DefaultListModel();
        LocalDate fechaHoy = LocalDate.now();

        String fechaString = config.retornarLicencia(); // Tu fecha en formato yyyy-MM-dd
        LocalDate fechaIngresada = LocalDate.parse(fechaString, DateTimeFormatter.ISO_DATE);
        LocalDate fechaActual = LocalDate.now();

        if (fechaIngresada.isBefore(fechaActual)) {
            model.addElement("Su licencia a caducado, favor de renovar");
        } else {
            long diasDiferencia = ChronoUnit.DAYS.between(fechaIngresada, fechaActual) * (-1);
            if (diasDiferencia == 0) {
                model.addElement("Su licencia termina el dia de hoy");
            }
            if (diasDiferencia < 31 && diasDiferencia > 0) {
                model.addElement("Su licencia termina en " + diasDiferencia + " días");
            }
        }

        List<Ventas> lsVentas = ventaDao.listarVentas();
        for (int i = 0; i < lsVentas.size(); i++) {
            if (lsVentas.get(i).getMetodoPago().equals("PPD Pago en parcialidades o diferido") && (lsVentas.get(i).getTotal() - pagosDao.regresarPagos(lsVentas.get(i).getFolio()) - notasDao.retornarSumaNotas(lsVentas.get(i).getFolio())) > 0.9) {

                Credito cre = creditoDao.BuscarPorCodigoCliente(lsVentas.get(i).getIdCliente());
                LocalDate fechaCreacion = LocalDate.parse(lsVentas.get(i).getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate fechaVencimiento = fechaCreacion.plusDays(cre.getPlazo());
                for (int k = 0; k < lscreditos.size(); k++) {
                    if (lscreditos.get(k).getId() == cre.getId()) {
                        cre = lscreditos.get(k);
                    }
                }
                if (fechaVencimiento.isBefore(fechaHoy)) {
                    long diasDiferencia = ChronoUnit.DAYS.between(fechaVencimiento, fechaHoy);
                    if (diasDiferencia < 10) {
                        System.out.println(diasDiferencia);
                        model.addElement("Se terminó el plazo de pago de la venta a crédito F" + lsVentas.get(i).getFolio() + " hace menos de 10 días");
                    }
                    if (lsVentas.get(i).getInteresAplicado() == 0) {
                        double incremento = lsVentas.get(i).getTotal() * (cre.getInteresMoratorio() / 100);
                        lsVentas.get(i).setTotal(lsVentas.get(i).getTotal() + incremento);
                        creditoDao.aumentarAdeudo(cre.getIdCliente(), incremento);
                        ventaDao.interesMotatorio(lsVentas.get(i));
                        HistorialCliente hc = new HistorialCliente();

                        hc.setAbono(0);
                        hc.setCargo(lsVentas.get(i).getTotal());
                        hc.setFecha(fechaHoy.toString());
                        hc.setFolio("-");
                        hc.setIdCliente(lsVentas.get(i).getIdCliente());
                        hc.setIdRecibe(e.getId());
                        hc.setMovimiento("Interés aplicado a F" + lsVentas.get(i).getFolio());
                        cre = creditoDao.BuscarPorCodigoCliente(lsVentas.get(i).getIdCliente());
                        hc.setSaldo(cre.getAdeudo());
                        historialDao.registrarMovimiento(hc);
                        bitacora.registrarRegistro("Se agregaron intéres por la venta a crédito F" + lsVentas.get(i).getFolio(), e.getId(), fechaHoy.toString());
                    }
                } else if (fechaVencimiento.isEqual(fechaHoy)) {
                    model.addElement("El plazo para pagar la venta a crédito de " + cre.getNombreCliente() + " con folio F" + lsVentas.get(i).getFolio() + " termina hoy");
                } else if (fechaVencimiento.minusDays(5).isBefore(fechaHoy)) {
                    model.addElement("El plazo para pagar la venta a crédito de " + cre.getNombreCliente() + " con folio F" + lsVentas.get(i).getFolio() + " esta por terminar");
                }

            }
        }

        for (int i = 0; i < lscreditos.size(); i++) {
            LocalDate fechaCreacion = LocalDate.parse(lscreditos.get(i).getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate fechaVencimiento = fechaCreacion.plusDays(lscreditos.get(i).getVigencia());

            if (fechaVencimiento.isBefore(fechaHoy)) {
                model.addElement("Se termino la vigencia del crédito de " + lscreditos.get(i).getNombreCliente());
                if (lscreditos.get(i).getInteresAplicado() == 0) {
                    creditoDao.agregarInteresMoratorio(lscreditos.get(i));
                }
            } else if (fechaVencimiento.isEqual(fechaHoy)) {
                model.addElement("La vigenciao del crédito de " + lscreditos.get(i).getNombreCliente() + " termina hoy");
            } else if (fechaVencimiento.minusDays(5).isBefore(fechaHoy)) {
                model.addElement("La vigencia del crédito de " + lscreditos.get(i).getNombreCliente() + " esta por terminar");
            }
        }

        List<Producto> lsProducto = productoDao.listarProductos();
        for (int i = 0; i < lsProducto.size(); i++) {
            if (lsProducto.get(i).getExistencia() <= lsProducto.get(i).getMinimo() && lsProducto.get(i).getInventario() == 1) {
                model.addElement("Producto " + lsProducto.get(i).getDescripcion() + " bajo en inventario");
            }
        }
        listaAvisos.setModel(model);
    }

    public void usuarioAccedido(Empleados e) {
        String fechaString = config.retornarLicencia(); // Tu fecha en formato yyyy-MM-dd
        LocalDate fechaIngresada = LocalDate.parse(fechaString, DateTimeFormatter.ISO_DATE);
        LocalDate fechaActual = LocalDate.now();

        if (fechaIngresada.isBefore(fechaActual)) {
            //  long diasDiferencia = ChronoUnit.DAYS.between(fechaIngresada, fechaActual);
            this.e = new Empleados();
            this.perfil = new Perfil();
        } else {
            this.e = e;
            this.perfil = perfilDao.BuscarPorCodigo(e.getIdPerfil());
            lbUsuario.setText(e.getNombre());
            vaciarListaCreditos();

        }

    }

    public void eventoF1() {

    }

    public void eventoF2() {

    }

    public void eventoF3() {

    }

    public void eventoF4() {

    }

    public void eventoF5() {

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
        jScrollPane1 = new javax.swing.JScrollPane();
        listaAvisos = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbUsuario = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setViewportView(listaAvisos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 430, 360, 250));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1370, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1370, -1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 450, 280));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 153));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 100, 310, 50));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 160, 190, 50));

        lbUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario.jpg"))); // NOI18N
        jPanel1.add(lbUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 50, 170, 40));

        jLabel23.setBackground(new java.awt.Color(0, 0, 204));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("Avisos");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 400, -1, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1390, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int codigo = evt.getKeyCode();
        switch (codigo) {
            case java.awt.event.KeyEvent.VK_F1:
                eventoF1();
                break;

            case java.awt.event.KeyEvent.VK_F2:
                eventoF2();
                break;

            case java.awt.event.KeyEvent.VK_F3:
                eventoF3();
                break;

            case java.awt.event.KeyEvent.VK_F4:
                eventoF4();
                break;

            case java.awt.event.KeyEvent.VK_F5:
                eventoF5();
                break;

        }
    }//GEN-LAST:event_formKeyPressed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed

    }//GEN-LAST:event_jPanel1KeyPressed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseClicked

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
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbUsuario;
    private javax.swing.JList<String> listaAvisos;
    // End of variables declaration//GEN-END:variables

    private void Seticon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files (x86)\\AppPinturasOssel\\Iconos\\Osel.jpg"));
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

    private static CellStyle getContabilidadCellStyle(Workbook workbook, DecimalFormat df) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat(df.format(0)));
        return style;
    }

    public String fechaFormatoCorrecto(String fechaHoy) {
        Date diaActual = StringADate(fechaHoy);
        DateFormat cam = new SimpleDateFormat("dd-MM-yyyy");
        String fechaUsar;
        Date fechaNueva;
        Calendar c = Calendar.getInstance();
        c.setTime(diaActual);
        fechaUsar = cam.format(diaActual);
        return fechaUsar;

    }

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == hi) {
            calcula();
            horas = String.valueOf(Integer.parseInt(horas));
            jLabel7.setText(horas + ":" + min);
            jLabel8.setText(diaActual);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }

    private void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraactual = new Date();
        calendario.setTime(fechaHoraactual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        horas = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        Formatter obj2 = new Formatter();
        Formatter obj = new Formatter();
        min = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        seg = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
        diaActual = "" + String.valueOf(obj2.format("%02d", calendario.get(Calendar.DAY_OF_MONTH))) + "-" + String.valueOf(obj.format("%02d", 1 + calendario.get(Calendar.MONTH))) + "-" + calendario.get(Calendar.YEAR);
    }

}
