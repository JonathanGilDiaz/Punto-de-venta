/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package punto.de.venta;

import Vista.Login;

/**
 *
 * @author Jonathan Gil
 */
public class PuntoDeVenta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                //Al comenzar el programa, invocamoa al login

        Login lg = new Login();
       lg.setVisible(true);
    }
    
}
