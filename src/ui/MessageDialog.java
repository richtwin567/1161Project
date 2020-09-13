package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;

public class MessageDialog extends JDialog {

    private static final long serialVersionUID = -3373577856205025419L;

    private JLabel messageText;
    private final JDialog thisDialog = this;
    private JButton ok;
    private JPanel base;

    /**
     * Creates a message dialog with the specified message
     * @param parent the parent window
     * @param message the message for the user
     */
    public MessageDialog(JFrame parent, String message) {
        super(parent, true);
        setLocationRelativeTo(parent);
        init(message);
    }

    /**
     * Sets up components
     * @param message the message for the user
     */
    public void init(String message) {
        setBackground(Colours.bg);
        setForeground(Colours.onBg);
        setLayout(new GridLayout());

        base = new JPanel(new GridLayout(2, 1));
        ((GridLayout) base.getLayout()).setVgap(20);
        base.setBackground(Colours.bg);
        base.setForeground(Colours.onBg);

        messageText = new JLabel(message);
        messageText.setForeground(Colours.onBg);
        messageText.setBackground(Colours.bg);

        base.add(messageText);

        ok = new MaterialButton("OK",Colours.onPrimary,Colours.primary);
        ok.addMouseListener(new OkButtonListener());
        
        base.add(ok);

        add(base);
        pack();

    }

    /**CLose the message when the ok button is pressed */
    private class OkButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            thisDialog.setVisible(false);

        }

    }
}