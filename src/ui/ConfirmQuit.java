package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.SNIDGUI.ButtonListener;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;

public class ConfirmQuit extends JDialog {

    private JDialog thisDialog = this;
    private JPanel base;
    private JPanel buttons;
    private JButton yes;
    private JButton no;
    private JLabel message;
    private GridLayout grid;
    

    private static final long serialVersionUID = 6300291949093588173L;

    public ConfirmQuit(JFrame parent) {
        super(parent, "Confirm Exit", true);
        setLocationRelativeTo(parent);
        init();
    }

    public void init() {
        
        grid = new GridLayout();
        setLayout(grid);
        setBackground(SNIDGUI.bg);
        setForeground(SNIDGUI.onBg);
        

        base = new JPanel();
        base.setBackground(SNIDGUI.bg);
        base.setForeground(SNIDGUI.onBg);
        grid = new GridLayout(2, 1);
        grid.setHgap(20);
        grid.setVgap(30);
        base.setLayout(grid);

        message = new JLabel("Are you sure you want to quit?");
        message.setBackground(SNIDGUI.bg);
        message.setForeground(SNIDGUI.onBg);
        base.add(message);

        buttons = new JPanel();
        buttons.setBackground(SNIDGUI.bg);
        buttons.setForeground(SNIDGUI.onBg);
        grid = new GridLayout(1, 2);
        grid.setHgap(30);
        grid.setVgap(20);
        buttons.setLayout(grid);

        yes = new JButton("Yes");
        SNIDGUI.setUpMaterialButton(yes);
        yes.setBackground(SNIDGUI.error);
        yes.setForeground(SNIDGUI.onError);
        yes.addMouseListener(new YesButtonListener());

        buttons.add(yes);

        no = new JButton("NO");
        SNIDGUI.setUpMaterialButton(no);
        no.setBackground(SNIDGUI.primary);
        no.setForeground(SNIDGUI.onPrimary);
        no.addMouseListener(new NoButtonListener());

        buttons.add(no);

        base.add(buttons);

        add(base);
        pack();
    }

    private class YesButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            System.exit(0);
        }
        
    }


    private class NoButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            JDialog dialog = thisDialog;
            dialog.setVisible(false);

        }
        
    }
}