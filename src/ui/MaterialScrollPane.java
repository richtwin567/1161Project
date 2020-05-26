package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Cursor;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 * Material style JScrollPane. The JScrollbar is style as well.
 * 
 * @author Anakai Richards - ID: 620132232
 * @author Matthew Palmer - ID: 620131688
 * @author Michael Young - ID: 620131387
 * @version 1.0
 * @see JScrollPane
 * @see JScrollBar
 */
public class MaterialScrollPane extends JScrollPane {

    private static final long serialVersionUID = -1465113132340505079L;

    /**
     * Create a material style scroll pane and set the component to be scrolled
     * 
     * @param view component to be scrolled
     */
    public MaterialScrollPane(Component view) {
        super(view);
        setBorder(new LineBorder(Colours.onBg, 1));
        setBackground(Colours.bg);
        setForeground(Colours.onBg);
        getVerticalScrollBar().setUI(new MaterialScrollBarUI("UP", "DOWN"));
        getVerticalScrollBar().setPreferredSize(new Dimension(10, this.getHeight()));
        getHorizontalScrollBar().setUI(new MaterialScrollBarUI("LEFT", "RIGHT"));
        getHorizontalScrollBar().setPreferredSize(new Dimension(this.getWidth(), 10));
    }

    /**
     * Implements the Material style of the ScrollPane.
     */
    private class MaterialScrollBarUI extends BasicScrollBarUI {

        String arrow1;
        String arrow2;

        /**
         * Create the ui and set the arrow icons
         * 
         * @param arrow1 first arrow icon
         * @param arrow2 second arrow icon
         */
        public MaterialScrollBarUI(String arrow1, String arrow2) {
            super();
            this.arrow1 = arrow1;
            this.arrow2 = arrow2;
        }

        /**
         * Make the thumb flat, thinner and a rounded rectangle
         */
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color color = Colours.primary;
            if (isDragging) {
                color = Colours.darkPrimary;
            } else if (isThumbRollover()) {
                color = Colours.accent;
            }
            g2d.setPaint(color);
            g2d.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 20, 15);
        }

        /**Make the track colour the same as the background */
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setPaint(Colours.bg);
            g2d.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }

        /**
         * Set the bounds for the thumb
         */
        @Override
        protected void setThumbBounds(int x, int y, int width, int height) {
            super.setThumbBounds(x, y, width, height);
        }

        /**Create the button for scrolling up or to the left */
        @Override
        protected JButton createDecreaseButton(int orientation) {
            JButton btn = new JButton();
            String dir = System.getProperty("user.dir");
            String path=null;
            if (dir.contains("bin")){
                path = "./ui/icons/";                
            }else{
                path = "./src/ui/icons/";
            }
            switch(arrow1){
                case "UP":
                    btn.setIcon(new ImageIcon(path+"up_arrow.png"));
                    break;
                case "LEFT":
                    btn.setIcon(new ImageIcon(path+"left_arrow.png"));
                    break;
            }
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setForeground(Colours.onBg);
            btn.setBorder(null);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.setBackground(Colours.bg);
            btn.setPreferredSize(new Dimension(this.scrollBarWidth, 10));
            return btn;
        }

        /**Create button for scrolling down or to the right */
        @Override
        protected JButton createIncreaseButton(int orientation) {
            JButton btn = new JButton();
            String dir = System.getProperty("user.dir");
            String path=null;
            if (dir.contains("bin")){
                path = "./ui/icons/";                
            }else{
                path = "./src/ui/icons/";
            }
            switch(arrow2){
                case "DOWN":
                    btn.setIcon(new ImageIcon(path+"down_arrow.png"));
                    break;
                case "RIGHT":
                    btn.setIcon(new ImageIcon(path+"right_arrow.png"));
                    break;
            }
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setFont(new Font("Calibri", Font.PLAIN, 10));
            btn.setForeground(Colours.onBg);
            btn.setBorder(null);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.setBackground(Colours.bg);
            btn.setPreferredSize(new Dimension(this.scrollBarWidth, 10));
            return btn;
        }

    }

}