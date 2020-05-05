package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SNIDGUI extends JFrame {

    private static final long serialVersionUID = -8940575551959945422L;

    private JRadioButton searchByIdRadioButton;
    private JRadioButton searchByNameRadioButton;
    private JRadioButton searchByBiometricRadioButton;
    private JPanel radioButtonPanel;
    private GridLayout radioButtonPanelLayout;

    private JTextField searchValueField;
    private JList<String> idList;
    private DefaultListModel<String> idListModel;
    private JScrollPane idListScrollPane;

    private JTextArea citizenDetailArea;

    private JButton searchGoButton;
    private JButton clearButton;
    private JButton quitButton;
    private JPanel buttonPanel;
    private GridLayout buttonPanelLayout;

    private JPanel basePanel;
    private GridBagConstraints basePanelConstraints;
    private GridBagLayout basePanelLayout;

    private JLabel windowTitle;

    // Material Design Colours
    private final Color darkPrimary = new Color(25, 118, 210);
    private final Color onDarkPrimary = new Color(255, 255, 255);
    private final Color primary = new Color(33, 150, 243);
    private final Color onPrimary = new Color(33, 33, 33);
    private final Color bg = new Color(255, 255, 255);
    private final Color onBg = new Color(33, 33, 33);
    private final Color accent = new Color(3, 169, 244);
    private final Color onAccent = new Color(33, 33, 33);
    private final Color error = new Color(211, 27, 47);
    private final Color onError = new Color(255, 255, 255);
    

    public SNIDGUI() {
        init();
    }

    private void init() {
        // set up JFrame
        setBackground(bg);
        setForeground(onBg);
        setResizable(true);
        setLayout(new GridLayout(1, 1));
        setTitle("SYSTEM FOR NATIONAL IDENTIFICATION(SNID)");

        // set up the base panel
        basePanel = new JPanel();
        basePanel.setBorder(null);
        basePanel.setBackground(bg);
        basePanel.setForeground(onBg);
        basePanelLayout = new GridBagLayout();
        basePanelLayout.rowHeights = new int[] { 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30 };
        basePanelLayout.columnWidths = new int[] { 20, 50, 20, 50, 20, 50, 20, 50, 20, 50,20 };
        basePanelLayout.columnWeights = new double[] { 1, 1, 1, 1, 1, 1, 1, 2, 1 };
        basePanelLayout.rowWeights = new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        basePanelConstraints = new GridBagConstraints();
        basePanelConstraints.weightx = 1;
        basePanelConstraints.weighty = 1;
        basePanel.setLayout(basePanelLayout);

        // set up base panel components

        // setup window title label
        windowTitle = new JLabel("SYSTEM FOR NATIONAL IDENTIFICATION(SNID)");
        windowTitle.setHorizontalAlignment(SwingConstants.CENTER);
        windowTitle.setBackground(darkPrimary);
        windowTitle.setForeground(onDarkPrimary);
        windowTitle.setOpaque(true);
        configureGridBagConstraints(basePanelConstraints, 1, 12, 0, 0);
        basePanel.add(windowTitle, basePanelConstraints);
        
        //set up panel for buttons
        buttonPanel =  new JPanel();
        buttonPanel.setBackground(bg);
        buttonPanel.setBorder(null);
        buttonPanel.setForeground(onBg);
        buttonPanelLayout = new GridLayout(3,1);
        buttonPanelLayout.setVgap(10);
        buttonPanel.setLayout(buttonPanelLayout);

        // search button set up
        searchGoButton = new JButton("Search");
        searchGoButton.setBackground(primary);
        searchGoButton.setForeground(onPrimary);
        setUpMaterialButton(searchGoButton);
        //carry out a search in the database using the search value provided
        searchGoButton.addMouseListener(new SearchButtonListener());
        // add search button to panel grid
        buttonPanel.add(searchGoButton);

        // clear button set up
        clearButton = new JButton("Clear");
        clearButton.setBackground(error);
        clearButton.setForeground(onError);
        setUpMaterialButton(clearButton);
        //make search value field clear when this button is pressed
        clearButton.addMouseListener(new ClearButtonListener());
        // add clear button to panel
        buttonPanel.add(clearButton);

        // quit button set up
        quitButton = new JButton("Quit");
        quitButton.setBackground(error);
        quitButton.setForeground(onError);
        setUpMaterialButton(quitButton);
        //exit program when clicked
        quitButton.addMouseListener(new QuitButtonListener());
        // add quit button to panel
        buttonPanel.add(quitButton);

        //add button panel to base panel
        configureGridBagConstraints(basePanelConstraints, 4, 3, 7, 4);
        basePanel.add(buttonPanel, basePanelConstraints);

        //set up radio button panel 
        radioButtonPanel = new JPanel();
        radioButtonPanel.setBackground(bg);
        radioButtonPanel.setForeground(onBg);
        radioButtonPanel.setBorder(null);
        radioButtonPanelLayout = new GridLayout(1,3);
        radioButtonPanelLayout.setHgap(20);
        radioButtonPanel.setLayout(radioButtonPanelLayout);

        // set up the radio buttons
        searchByIdRadioButton = new JRadioButton("Search by Id");
        setUpRadioButton(searchByIdRadioButton);
        //add action listener to toggle selected radio button
        searchByIdRadioButton.addActionListener(new RadioListener());
        radioButtonPanel.add(searchByIdRadioButton);

        searchByNameRadioButton = new JRadioButton("Search by Name");
        setUpRadioButton(searchByNameRadioButton);
        searchByNameRadioButton.addActionListener(new RadioListener());
        radioButtonPanel.add(searchByNameRadioButton);

        searchByBiometricRadioButton = new JRadioButton("Biometric Search");
        setUpRadioButton(searchByBiometricRadioButton);
        searchByBiometricRadioButton.addActionListener(new RadioListener());
        radioButtonPanel.add(searchByBiometricRadioButton);

        //add radio buttons to base panel
        configureGridBagConstraints(basePanelConstraints, 1, 5, 1, 2);
        basePanel.add(radioButtonPanel, basePanelConstraints);

        // set up search text field
        searchValueField = new JTextField("User enters search string here");
        searchValueField.setBackground(bg);
        searchValueField.setBorder(new LineBorder(onBg, 1));
        searchValueField.setForeground(onBg);
        configureGridBagConstraints(basePanelConstraints, 1, 5, 1, 4);
        basePanel.add(searchValueField, basePanelConstraints);

        // set up located IDs list with scroll pane
        idListModel = new DefaultListModel<>();
        idList = new JList<>(idListModel);
        /*  new JTable();
        idList.setModel(new DefaultTableModel(new String[][] { { "" } }, new String[] { "Located Citizen IDs" }) {
        
            private static final long serialVersionUID = -4792612440923892028L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }); */
            
        idList.setBackground(bg);
        idList.setBorder(new LineBorder(bg, 0));
        idList.setForeground(onBg);
        idListScrollPane = new JScrollPane(idList);
        idListScrollPane.setBackground(bg);
        idListScrollPane.setBorder(new LineBorder(onBg, 1));
        configureGridBagConstraints(basePanelConstraints, 9, 1, 1, 6);
        basePanel.add(idListScrollPane, basePanelConstraints);

        // setup citizen detail pane
        citizenDetailArea = new JTextArea(
                "Details of a selected record are displayed here. Select a record from the list to the left after doing a search");
        citizenDetailArea.setLineWrap(true);
        citizenDetailArea.setWrapStyleWord(true);
        citizenDetailArea.setBackground(bg);
        citizenDetailArea.setForeground(onBg);
        citizenDetailArea.setBorder(new LineBorder(onBg, 1));
        citizenDetailArea.setEditable(false);
        citizenDetailArea.setMargin(new Insets(20, 20, 20, 20));
        configureGridBagConstraints(basePanelConstraints, 9, 3, 3, 6);
        basePanel.add(citizenDetailArea, basePanelConstraints);

        add(basePanel);
        pack();

    }

    /**
     * 
     */
    private abstract class ButtonListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            //does nothing

        }   

        @Override
        public abstract void mousePressed(MouseEvent e); 

        
        @Override
        public void mouseReleased(MouseEvent e) {
            //does nothing

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            btn.setBackground(btn.getBackground().darker());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            btn.setBackground(btn.getBackground().brighter());
        }

    }

    /**
     * 
     */
    private class ClearButtonListener extends ButtonListener{

        @Override
        public void mousePressed(MouseEvent e) {
            searchValueField.setText("");
        }     

    }

    /**
     * 
     */
    private class SearchButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            idListModel.addElement("This Method is a stub");
        }

    }

    /**
     * 
     */
    private class QuitButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            System.exit(0);
        }
        
    }

    /**
     * 
     */
    private class RadioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(searchByBiometricRadioButton)){
                searchByBiometricRadioButton.setSelected(true);
                searchByIdRadioButton.setSelected(false);
                searchByNameRadioButton.setSelected(false);
            }else if(e.getSource().equals(searchByIdRadioButton)){
                searchByBiometricRadioButton.setSelected(false);
                searchByIdRadioButton.setSelected(true);
                searchByNameRadioButton.setSelected(false);
            }else{
                searchByBiometricRadioButton.setSelected(false);
                searchByIdRadioButton.setSelected(false);
                searchByNameRadioButton.setSelected(true);
            }
        }
        
    }

    /**
     * Set buttons to similar styling
     * @param button The button to be styled
     */
    private void setUpMaterialButton(JButton button){
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMaximumSize(new Dimension(120,500));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(60,30,60,30));
    }

    /**
     * 
     * @param rButton
     */
    private void setUpRadioButton(JRadioButton rButton){
        rButton.setBorder(null);
        rButton.setBackground(bg);
        rButton.setFocusPainted(false);
        rButton.setForeground(onBg);
        rButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * 
     * @param c
     * @param gridHeight
     * @param gridWidth
     * @param gridx
     * @param gridy
     */
    private void configureGridBagConstraints(GridBagConstraints c, int gridHeight, int gridWidth, int gridx, int gridy){
        c.fill =  GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridheight = gridHeight;
        c.gridwidth = gridWidth;
        c.gridx = gridx;
        c.gridy = gridy;
    }


    public static void main(String[] args) {
        SNIDGUI ui = new SNIDGUI();
        ui.setVisible(true);
    }
}