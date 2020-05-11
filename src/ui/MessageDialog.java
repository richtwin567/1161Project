package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.SNIDGUI.ButtonListener;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;

public class MessageDialog extends JDialog {

    private static final long serialVersionUID = -3373577856205025419L;

    private JLabel messageText;
    private final JDialog thisDialog = this;
    private JButton ok;
    private JPanel base;

    public MessageDialog(JFrame parent, String message) {
        super(parent, true);
        setLocationRelativeTo(parent);
        init(message);
    }

    public void init(String message) {
        setBackground(SNIDGUI.bg);
        setForeground(SNIDGUI.onBg);
        setLayout(new GridLayout());

        base = new JPanel(new GridLayout(2, 1));
        ((GridLayout) base.getLayout()).setVgap(20);
        base.setBackground(SNIDGUI.bg);
        base.setForeground(SNIDGUI.onBg);

        messageText = new JLabel(message);
        messageText.setForeground(SNIDGUI.onBg);
        messageText.setBackground(SNIDGUI.bg);

        base.add(messageText);

        ok = new JButton("OK");
        SNIDGUI.setUpMaterialButton(ok);
        ok.setBackground(SNIDGUI.primary);
        ok.setForeground(SNIDGUI.onPrimary);
        ok.addMouseListener(new OkButtonListener());
        
        base.add(ok);

        add(base);
        pack();

    }

    private class OkButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            thisDialog.setVisible(false);

        }

    }
}