package ui;

import java.awt.Cursor;
//import java.awt.Color;
import javax.swing.JRadioButton;

public class MaterialRadioButton extends JRadioButton {

	private static final long serialVersionUID = -6020540338356599302L;

    public MaterialRadioButton(String label){
        super(label);
        setBorder(null);
	    setBackground(Colours.bg);
	    setFocusPainted(false);
	    setForeground(Colours.onBg);
	    setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    
}