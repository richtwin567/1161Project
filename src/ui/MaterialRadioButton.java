package ui;

import java.awt.FontMetrics;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Canvas;
import javax.swing.text.View;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicRadioButtonUI;

/**
 * A material style radio button
 * @author Anakai Richards - ID: 620132232
 * @author Matthew Palmer - ID: 620131688
 * @author Michael Young - ID: 620131387
 * @version 1.0
 */
public class MaterialRadioButton extends JRadioButton {

    private static final long serialVersionUID = -6020540338356599302L;

    /**
     * Creates a new instance of the material radio button
     * @param label the tex to placed beside the button
     */
    public MaterialRadioButton(String label) {
        super(label);
        setBorder(null);
        setBackground(Colours.bg);
        setFocusPainted(false);
        setForeground(Colours.onBg);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setUI(new MaterialRadioButtonUI());
    }

    /**
     * The ui for the button. Implements the material style appearance
     */
    private static class MaterialRadioButtonUI extends BasicRadioButtonUI {

        private static Dimension size = new Dimension();
        private static Rectangle viewRect = new Rectangle();
        private static Rectangle iconRect = new Rectangle();
        private static Rectangle textRect = new Rectangle();

        /**
         * Grafted from the {@link BasicRadioButtonUI#paint(Graphics, JComponent)} method to override the appearance of the 
         * radio buttons
         * @param g the graphics. Needed so that the button can render correctly
         * @param c this radio button
         */
        @Override
        public synchronized void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();

            Font f = c.getFont();
            g.setFont(f);
            Canvas canvas = new Canvas();
            FontMetrics fm = canvas.getFontMetrics(f);

            Insets i = c.getInsets();
            size = b.getSize(size);
            viewRect.x = i.left;
            viewRect.y = i.top;
            viewRect.width = size.width - (i.right + viewRect.x);
            viewRect.height = size.height - (i.bottom + viewRect.y);
            iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
            textRect.x = textRect.y = textRect.width = textRect.height = 0;

            String dir = System.getProperty("user.dir");
            String path=null;
            if (dir.contains("bin")){
                path = "./ui/icons/";                
            }else{
                path = "./src/ui/icons/";
            }
            Icon altIcon = new ImageIcon(path+"enabled_unselected.png", "enabled unselected");

            //place the labels beside the button icons
            String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(),
                    altIcon != null ? altIcon : getDefaultIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(),
                    b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect,
                    b.getText() == null ? 0 : b.getIconTextGap());

            // fill background
            if (c.isOpaque()) {
                g.setColor(b.getBackground());
                g.fillRect(0, 0, size.width, size.height);
            }

            // Paint the radio button
            if (altIcon != null) {

                if (!model.isEnabled()) {
                    if (model.isSelected()) {
                        altIcon = new ImageIcon(path+"disabled_selected.png", "disabled selected");
                    } else {
                        altIcon = new ImageIcon(path+"disabled_unselected.png", "disabled unselected");
                    }
                } else if (model.isPressed() && model.isArmed()) {
                    altIcon = new ImageIcon(path+"pressed_selected.png", "pressed selected");
                } else if (model.isSelected()) {
                    if (b.isRolloverEnabled() && model.isRollover()) {
                        altIcon = new ImageIcon(path+"hover_selected.png", "hover selected");
                    } else {
                        altIcon = new ImageIcon(path+"enabled_selected.png", "enabled selected");
                    }
                } else if (b.isRolloverEnabled() && model.isRollover()) {
                    altIcon = new ImageIcon(path+"hover_unselected.png", "hover unselected");
                }
            }

            altIcon.paintIcon(c, g, iconRect.x, iconRect.y);
            
            if (text != null) {
                View v = (View) c.getClientProperty(BasicHTML.propertyKey);
                if (v != null) {
                    v.paint(g, textRect);
                } else {
                    paintText(g, b, textRect, text);
                }
                if (b.hasFocus() && b.isFocusPainted() && textRect.width > 0 && textRect.height > 0) {
                    paintFocus(g, textRect, size);
                }
            }
        }

    }
}