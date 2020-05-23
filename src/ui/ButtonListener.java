package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

/**
 * Super class for all button listeners in the SNID project
 */
public abstract class ButtonListener implements MouseListener {
/* 
    private Color oB;
    private Color oF; */

    @Override
    public void mouseClicked(MouseEvent e) {
        // does nothing
    }

    // to be implemented by subclasses
    @Override
    public abstract void mousePressed(MouseEvent e);

    @Override
    public void mouseReleased(MouseEvent e) {
        // does nothing

    }

    /**changes background and foreground colour of buttons on hover*/
    @Override
    public void mouseEntered(MouseEvent e) {/* 
        JButton btn = (JButton) e.getSource();
        oB = btn.getBackground();
        oF = btn.getForeground();
        btn.setBackground(Colours.accent);
        btn.setForeground(Colours.onAccent); */
    }

    /**restores original colours on exit*/
    @Override
    public void mouseExited(MouseEvent e) {/* 
        JButton btn = (JButton) e.getSource();
        btn.setBackground(oB);
        btn.setForeground(oF); */
    }

}