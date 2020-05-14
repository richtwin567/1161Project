package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;

public class MaterialButton extends JButton{

	private static final long serialVersionUID = 5262532734302802208L;

    /**
     * 
     */
    public MaterialButton(String label, Color fg, Color bg){
        super(label);
        setBorder(null);
	    setBorderPainted(false);
	    setContentAreaFilled(false);
	    setOpaque(true);
	    setFocusPainted(false);
	    setHorizontalAlignment(SwingConstants.CENTER);
	    setMaximumSize(new Dimension(120, 500));
	    setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(60, 30, 60, 30));
        setBackground(bg);
        setForeground(fg);
    }
    
}