package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;

/**A prompt to ensure that user really wants to quit the program 
 * @author Anakai Richards - ID: 620132232
 * @author Matthew Palmer - ID: 620131688
 * @author Michael Young - ID: 620131387
 * @version 1.0
*/
public class ConfirmQuitDialog extends JDialog {

    private JDialog thisDialog = this;
    private JPanel base;
    private JPanel buttons;
    private JButton yes;
    private JButton no;
    private JLabel message;
    private GridLayout grid;
    private JFrame parent;

    private static final long serialVersionUID = 6300291949093588173L;

    public ConfirmQuitDialog(JFrame parent) {
        super(parent, "Confirm Exit", true);
        setLocationRelativeTo(parent);
        this.parent = parent;
        init();
    }

    /**
     * Set up dialog components
     */
    public void init() {
        
        grid = new GridLayout();
        setLayout(grid);
        setBackground(Colours.bg);
        setForeground(Colours.onBg);
        base = new JPanel();
        base.setBackground(Colours.bg);
        base.setForeground(Colours.onBg);
        grid = new GridLayout(2, 1);
        grid.setHgap(20);
        grid.setVgap(30);
        base.setLayout(grid);

        message = new JLabel("Are you sure you want to quit?");
        message.setBackground(Colours.bg);
        message.setForeground(Colours.onBg);
        base.add(message);

        buttons = new JPanel();
        buttons.setBackground(Colours.bg);
        buttons.setForeground(Colours.onBg);
        grid = new GridLayout(1, 2);
        grid.setHgap(30);
        grid.setVgap(20);
        buttons.setLayout(grid);

        yes = new MaterialButton("Yes",Colours.onCritical,Colours.critical);
        yes.addMouseListener(new YesButtonListener());

        buttons.add(yes);

        no = new MaterialButton("NO",Colours.onPrimary,Colours.primary);
        no.addMouseListener(new NoButtonListener());

        buttons.add(no);

        base.add(buttons);

        add(base);
        pack();
    }

    /**Exits the program when yes is clicked
     * 
     */
    private class YesButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            parent.dispose();
            thisDialog.dispose();
        }
        
    }


    /**
     * Returns to the program when no is clicked
     */
    private class NoButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            JDialog dialog = thisDialog;
            dialog.setVisible(false);

        }
        
    }
}