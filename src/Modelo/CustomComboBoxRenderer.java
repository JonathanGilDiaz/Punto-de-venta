/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author Jonathan Gil
 */
public class CustomComboBoxRenderer extends BasicComboBoxRenderer {
        private final Color disabledColor = Color.GRAY; // Color para el texto deshabilitado

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Verificar si el JComboBox está deshabilitado
            if (!list.isEnabled()) {
                component.setForeground(disabledColor); // Establecer el color del texto deshabilitado
            }

            return component;
        }
    }

