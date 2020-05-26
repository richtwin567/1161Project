package ui;

import javax.swing.JPanel;

/**Material style JPanel
 * @author Anakai Richards - ID: 620132232
 * @author Matthew Palmer - ID: 620131688
 * @author Michael Young - ID: 620131387
 * @version 1.0
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