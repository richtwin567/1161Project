package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import app.SNIDApp;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SNIDGUI extends JFrame {

    private static final long serialVersionUID = -8940575551959945422L;

    SNIDApp appController;
    private ArrayList<SearchResult> results;

    private JRadioButton searchByIdRadioButton;
    private JRadioButton searchByNameRadioButton;
    private JRadioButton searchByBiometricRadioButton;
    private ButtonGroup radioButtonGroup;
    private JPanel radioButtonPanel;
    private GridLayout radioButtonPanelLayout;

    private JTextField searchValueField;
    private JList<String> idList;
    private JLabel idListLabel;
    private DefaultListModel<String> idListModel;
    private JScrollPane idListScrollPane;

    private JEditorPane citizenDetailArea;
    private JScrollPane citizenDetailAreaScrollPane;

    private JButton searchGoButton;
    private JButton clearButton;
    private JButton quitButton;
    private JPanel buttonPanel;
    private GridLayout buttonPanelLayout;

    private JPanel basePanel;
    private GridBagConstraints basePanelConstraints;
    private GridBagLayout basePanelLayout;

    private JLabel windowTitle;

    private final JFrame thisFrame = this;

    // Material Design Colours
    protected static final Color darkPrimary = new Color(25, 118, 210);
    protected static final Color onDarkPrimary = new Color(255, 255, 255);
    protected static final Color primary = new Color(33, 150, 243);
    protected static final Color onPrimary = new Color(33, 33, 33);
    protected static final Color bg = new Color(255, 255, 255);
    protected static final Color onBg = new Color(33, 33, 33);
    protected static final Color accent = new Color(255, 152, 0);
    protected static final Color onAccent = new Color(255, 255, 255);
    protected static final Color error = new Color(211, 27, 47);
    protected static final Color onError = new Color(255, 255, 255);

    public SNIDGUI(SNIDApp appController) {
        this.appController = appController;
        results = new ArrayList<>();
        init();
    }

    private void init() {
        // set up JFrame
        setBackground(bg);
        setForeground(onBg);
        setResizable(true);
        setLayout(new GridLayout(1, 1));
        setTitle("SYSTEM FOR NATIONAL IDENTIFICATION(SNID)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up the base panel
        basePanel = new JPanel();
        basePanel.setBorder(null);
        basePanel.setBackground(bg);
        basePanel.setForeground(onBg);
        basePanelLayout = new GridBagLayout();
        basePanelLayout.rowHeights = new int[] { 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30 };
        basePanelLayout.columnWidths = new int[] { 20, 50, 20, 50, 20, 50, 20, 50, 20, 50, 20 };
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

        // set up panel for buttons
        buttonPanel = new JPanel();
        buttonPanel.setBackground(bg);
        buttonPanel.setBorder(null);
        buttonPanel.setForeground(onBg);
        buttonPanelLayout = new GridLayout(3, 1);
        buttonPanelLayout.setVgap(10);
        buttonPanel.setLayout(buttonPanelLayout);

        // search button set up
        searchGoButton = new JButton("Search");
        searchGoButton.setBackground(primary);
        searchGoButton.setForeground(onPrimary);
        setUpMaterialButton(searchGoButton);
        // carry out a search in the database using the search value provided
        searchGoButton.addMouseListener(new SearchButtonListener());
        // add search button to panel grid
        buttonPanel.add(searchGoButton);

        // clear button set up
        clearButton = new JButton("Clear");
        clearButton.setBackground(error);
        clearButton.setForeground(onError);
        setUpMaterialButton(clearButton);
        // make search value field clear when this button is pressed
        clearButton.addMouseListener(new ClearButtonListener());
        // add clear button to panel
        buttonPanel.add(clearButton);

        // quit button set up
        quitButton = new JButton("Quit");
        quitButton.setBackground(error);
        quitButton.setForeground(onError);
        setUpMaterialButton(quitButton);
        // exit program when clicked
        quitButton.addMouseListener(new QuitButtonListener());
        // add quit button to panel
        buttonPanel.add(quitButton);

        // add button panel to base panel
        configureGridBagConstraints(basePanelConstraints, 4, 3, 7, 4);
        basePanel.add(buttonPanel, basePanelConstraints);

        // set up radio button panel
        radioButtonPanel = new JPanel();
        radioButtonPanel.setBackground(bg);
        radioButtonPanel.setForeground(onBg);
        radioButtonPanel.setBorder(null);
        radioButtonPanelLayout = new GridLayout(1, 3);
        radioButtonPanelLayout.setHgap(20);
        radioButtonPanel.setLayout(radioButtonPanelLayout);

        // set up radio button group so that the buttons auto toggle
        radioButtonGroup = new ButtonGroup();

        // set up the radio buttons
        searchByIdRadioButton = new JRadioButton("Search by Id");
        setUpRadioButton(searchByIdRadioButton);
        // add button to group
        radioButtonGroup.add(searchByIdRadioButton);
        // add button to panel
        radioButtonPanel.add(searchByIdRadioButton);

        searchByNameRadioButton = new JRadioButton("Search by Name");
        setUpRadioButton(searchByNameRadioButton);
        radioButtonGroup.add(searchByNameRadioButton);
        radioButtonPanel.add(searchByNameRadioButton);

        searchByBiometricRadioButton = new JRadioButton("Biometric Search");
        setUpRadioButton(searchByBiometricRadioButton);
        radioButtonGroup.add(searchByBiometricRadioButton);
        radioButtonPanel.add(searchByBiometricRadioButton);

        // add radio buttons to base panel
        configureGridBagConstraints(basePanelConstraints, 1, 5, 1, 2);
        basePanel.add(radioButtonPanel, basePanelConstraints);

        // set up search text field
        searchValueField = new JTextField("User enters search string here");
        searchValueField.setBackground(bg);
        searchValueField.setBorder(new LineBorder(onBg, 1));
        searchValueField.setForeground(onBg);
        searchValueField.addFocusListener(new SearchValueFieldFocusListener());
        configureGridBagConstraints(basePanelConstraints, 1, 5, 1, 4);
        basePanel.add(searchValueField, basePanelConstraints);

        idListLabel = new JLabel("List of Found IDs");
        idListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idListLabel.setForeground(onBg);
        idListLabel.setBackground(bg);
        idListLabel.setBorder(new LineBorder(onBg, 1));
        configureGridBagConstraints(basePanelConstraints, 1, 1, 1, 6);
        basePanel.add(idListLabel, basePanelConstraints);

        // set up located IDs list with scroll pane
        idListModel = new DefaultListModel<>();
        idList = new JList<>(idListModel);
        idList.setBackground(bg);
        idList.setBorder(new LineBorder(bg, 0));
        idList.setForeground(onBg);
        idList.setSelectionBackground(accent);
        idList.setSelectionForeground(onAccent);
        idList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        idList.setCellRenderer(new DefaultListCellRenderer() {

            private static final long serialVersionUID = -7668408883782835744L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Border border = null;
                setBorder(border);
                return c;
            }
        });
        idList.addListSelectionListener(new IdListSelectionListener());
        idListScrollPane = new JScrollPane(idList);
        idListScrollPane.setBackground(bg);
        idListScrollPane.setBorder(new LineBorder(onBg, 1));
        configureGridBagConstraints(basePanelConstraints, 8, 1, 1, 7);
        basePanel.add(idListScrollPane, basePanelConstraints);

        // setup citizen detail pane
        citizenDetailArea = new JEditorPane("text/html",
                "Details of a selected record are displayed here.<br/> Select a record from the list to the left after doing a search");
        citizenDetailArea.setEditable(false);
        citizenDetailArea.setBackground(bg);
        citizenDetailArea.setForeground(onBg);
        citizenDetailArea.setBorder(new LineBorder(onBg, 0));
        citizenDetailAreaScrollPane = new JScrollPane(citizenDetailArea);
        citizenDetailAreaScrollPane.setBackground(bg);
        citizenDetailAreaScrollPane.setBorder(new LineBorder(onBg, 1));
        citizenDetailAreaScrollPane.setForeground(onBg);
        configureGridBagConstraints(basePanelConstraints, 9, 3, 3, 6);
        basePanel.add(citizenDetailAreaScrollPane, basePanelConstraints);

        add(basePanel);
        pack();

    }

    private class SearchResult {
        String id;
        String info;

        public SearchResult(String id, String info) {
            this.id = id;
            this.info = info;
        }

        public String getId() {
            return id;
        }

        public String getInfo() {
            return info;
        }

    }

    /**
     * 
     */
    protected static abstract class ButtonListener implements MouseListener {

        private Color oB;
        private Color oF;

        @Override
        public void mouseClicked(MouseEvent e) {
            // does nothing

        }

        @Override
        public abstract void mousePressed(MouseEvent e);

        @Override
        public void mouseReleased(MouseEvent e) {
            // does nothing

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            oB = btn.getBackground();
            oF = btn.getForeground();
            btn.setBackground(accent);
            btn.setForeground(onAccent);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            btn.setBackground(oB);
            btn.setForeground(oF);
        }

    }

    /**
     * 
     */
    private class ClearButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            searchValueField.setText("");
            searchValueField.requestFocus();
        }

    }

    /**
     * 
     */
    private class SearchButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            citizenDetailArea.setText("");
            idListModel.clear();
            results = new ArrayList<>();
            try {
                if (searchByIdRadioButton.isSelected()) {
                    String t = appController.searchGUI(searchValueField.getText());
                    String[] temp = t.split(",");
                    String id = temp[0];
                    String info = temp[1];
                    SearchResult result = new SearchResult(id, info);
                    results.add(result);
                    idListModel.addElement(result.getId());
                } else if (searchByBiometricRadioButton.isSelected()) {
                    String t = appController.searchGUI(searchValueField.getText().charAt(0),
                            searchValueField.getText().substring(1));
                    String[] temp = t.split(",");
                    String id = temp[0];
                    String info = temp[1];
                    SearchResult result = new SearchResult(id, info);
                    results.add(result);
                    idListModel.addElement(result.getId());
                } else if (searchByNameRadioButton.isSelected()) {
                    String fn = searchValueField.getText().split(" ")[0];
                    String ln = searchValueField.getText().split(" ")[1];
                    String[] t = appController.searchGUI(fn, ln);
                    String[][] temp = new String[t.length][5];
                    for (int x = 0; x < t.length; x++) {
                        temp[x] = t[x].split(",");
                        String id = temp[x][0];
                        String info = temp[x][1];
                        SearchResult result = new SearchResult(id, info);
                        results.add(result);
                        idListModel.addElement(result.getId());
                    }
                } else {
                    MessageDialog dialog = new MessageDialog(thisFrame, "Select a method to search by");
                    dialog.setVisible(true);
                    citizenDetailArea.setText("");
                }
            } catch (Exception ex) {
                MessageDialog dialog = new MessageDialog(thisFrame, "Not Found");
                dialog.setVisible(true);
                citizenDetailArea.setText("");
            }
        }

    }

    private class IdListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                JList<String> list = (JList<String>) e.getSource();
                int index = list.getSelectedIndex();
                citizenDetailArea.setText(results.get(index).getInfo());
            } catch (Exception ex) {
            }
        }

    }

    /**
     * Allows the program to terminate when the quit button is pressed
     */
    private class QuitButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            ConfirmQuit exit = new ConfirmQuit(thisFrame);
            exit.setVisible(true);
        }

    }

    /**
     * Highlights the search field with the accent colour when it gains focus
     */
    private class SearchValueFieldFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            JTextField field = (JTextField) e.getSource();
            field.setBorder(new LineBorder(accent, 1));
            // field.setText("");
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField field = (JTextField) e.getSource();
            field.setBorder(new LineBorder(onBg, 1));
        }

    }

    /**
     * Set buttons to similar styling
     * 
     * @param button The button to be styled
     */
    protected static void setUpMaterialButton(JButton button) {
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMaximumSize(new Dimension(120, 500));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(60, 30, 60, 30));
    }

    /**
     * Sets common attribute for the radio buttons
     * 
     * @param rButton the radio button to be set up
     */
    private void setUpRadioButton(JRadioButton rButton) {
        rButton.setBorder(null);
        rButton.setBackground(bg);
        rButton.setFocusPainted(false);
        rButton.setForeground(onBg);
        rButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Configures the GridBag Constraints according to the specifications passed to
     * the method
     * 
     * @param c          the GridBagConstraints object
     * @param gridHeight the number of rows the object should span
     * @param gridWidth  the number of columns the object should span
     * @param gridx      the location along the horizontal axis to place the object
     * @param gridy      the location along the vertical axis to place the object
     */
    private void configureGridBagConstraints(GridBagConstraints c, int gridHeight, int gridWidth, int gridx,
            int gridy) {
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridheight = gridHeight;
        c.gridwidth = gridWidth;
        c.gridx = gridx;
        c.gridy = gridy;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        SNIDApp appController = new SNIDApp("data.db", ',');
        SNIDGUI ui = new SNIDGUI(appController);
        ui.setVisible(true);
    }
}