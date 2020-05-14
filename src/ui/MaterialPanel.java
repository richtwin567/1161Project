package ui;

import javax.swing.JPanel;

/**Material style JPanel
 * @see JPanel
 */
public class MaterialPanel extends JPanel{

    private static final long serialVersionUID = 717818827457347172L;

    /**
     * Creates a flat planel withe specified colours
     */
    public MaterialPanel(){
        setBackground(Colours.bg);
        setBorder(null);
        setForeground(Colours.onBg);
    }
    
}