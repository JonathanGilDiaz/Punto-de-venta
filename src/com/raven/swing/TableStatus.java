package com.raven.swing;

import com.raven.model.StatusType;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

public class TableStatus extends JLabel {

    public StatusType getType() {
        return type;
    }

    public TableStatus() {
        setForeground(Color.WHITE);
    }

    private StatusType type;

    public void setType(StatusType type) {
        this.type = type;

          if(type==StatusType.ACTIVE)
        setText("ACTIVO");
           if(type==StatusType.INACTIVE)
        setText("INACTIVO");
            if(type==StatusType.VENTAS)
        setText("VENTA");
             if(type==StatusType.CANCELACION)
        setText("CANCELACIÓN");
              if(type==StatusType.DEVOLUCION)
        setText("DEVOLUCIÓN");
              if(type==StatusType.DESCUENTO)
        setText("DESCUENTO");
                if(type==StatusType.BONIFICACION)
        setText("BONIFICACIÓN");

        repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (type != null) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint g;
            if (type == StatusType.DESCUENTO || type == StatusType.BONIFICACION) {
                g = new GradientPaint(0, 0, Color.YELLOW.darker(), 0, getHeight(), Color.yellow.darker());
            } else if (type == StatusType.VENTAS || type == StatusType.ACTIVE)  {
                g = new GradientPaint(0, 0, Color.GREEN.darker(), 0, getHeight(), Color.green.brighter().darker());
            } else if(type == StatusType.DEVOLUCION){
                 g = new GradientPaint(0, 0, Color.BLUE, 0, getHeight(), Color.blue);
            }else { //cancelacion
                g = new GradientPaint(0, 0, new Color(213, 64, 12), 0, getHeight(), new Color(191, 59, 13));
            }
            g2.setPaint(g);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 1, 1);
        }
        super.paintComponent(grphcs);
    }
}
