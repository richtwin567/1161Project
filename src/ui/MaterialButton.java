package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;

/**
 * Material style JButton
 * @author Anakai Richards - ID: 620132232
 * @author Matthew Palmer - ID: 620131688
 * @author Michael Young - ID: 620131387
 * @version 1.0
 * @see JButton
 */
public class MaterialButton extends JButton{

	private static final long serialVersionUID = 5262532734302802208L;

    /**
     * Creates a flat button with the specified label and colours
     * @param label the button text
     * @param fg the text colour
     * @param bg the background colour
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