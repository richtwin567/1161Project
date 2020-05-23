package ui;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import app.SNIDApp;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

/**
 * The GUI that allows users to search the SNID database
 */
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

    /**
     * Constructor for the GUI. 
     * @param appController an instance of SNIDApp to access the database
     */
    public SNIDGUI(SNIDApp appController) {
        this.appController = appController;
        results = new ArrayList<>();
        init();
    }

    /**
     * Sets up the components of the GUI and places them in the JFrame.
     */
    private void init() {
        // set up JFrame
        /* setBackground(Colours.bg);
        setForeground(Colours.onBg); */
        setResizable(true);
        setLayout(new GridLayout(1, 1));
        setTitle("SYSTEM FOR NATIONAL IDENTIFICATION(SNID)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up the base panel
        basePanel = new JPanel();
        basePanelLayout = new GridBagLayout();
        basePanelLayout.rowHeights = new int[] { 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30, 10, 30 };
        basePanelLayout.columnWidths = new int[] { 35, 35, 35, 35, 35, 35, 35, 35, 35, 35 };
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
        /* windowTitle.setBackground(Colours.darkPrimary);
        windowTitle.setForeground(Colours.onDarkPrimary); */
        windowTitle.setOpaque(true);
        configureGridBagConstraints(basePanelConstraints, 1, 11, 0, 0);
        basePanel.add(windowTitle, basePanelConstraints);

        // set up panel for buttons
        buttonPanel = new JPanel();
        buttonPanelLayout = new GridLayout(3, 1);
        buttonPanelLayout.setVgap(10);
        buttonPanel.setLayout(buttonPanelLayout);

        // search button set up
        searchGoButton = new JButton("Search");
        // carry out a search in the database using the search value provided
        searchGoButton.addMouseListener(new SearchButtonListener());
        // add search button to panel grid
        buttonPanel.add(searchGoButton);

        // clear button set up
        clearButton = new JButton("Clear");
        // make search value field clear when this button is pressed
        clearButton.addMouseListener(new ClearButtonListener());
        // add clear button to panel
        buttonPanel.add(clearButton);

        // quit button set up
        quitButton = new JButton("Quit");
        // exit program when clicked
        quitButton.addMouseListener(new QuitButtonListener());
        // add quit button to panel
        buttonPanel.add(quitButton);

        // add button panel to base panel
        configureGridBagConstraints(basePanelConstraints, 5, 2, 7, 4);
        basePanel.add(buttonPanel, basePanelConstraints);

        // set up radio button panel
        radioButtonPanel = new JPanel();
        radioButtonPanelLayout = new GridLayout(1, 3);
        radioButtonPanelLayout.setHgap(20);
        radioButtonPanel.setLayout(radioButtonPanelLayout);

        // set up radio button group so that the buttons auto toggle
        radioButtonGroup = new ButtonGroup();

        // set up the radio buttons
        searchByIdRadioButton = new JRadioButton("Search by Id");
        // add button to group
        radioButtonGroup.add(searchByIdRadioButton);
        // add button to panel
        radioButtonPanel.add(searchByIdRadioButton);

        searchByNameRadioButton = new JRadioButton("Search by Name");
        radioButtonGroup.add(searchByNameRadioButton);
        radioButtonPanel.add(searchByNameRadioButton);

        searchByBiometricRadioButton = new JRadioButton("Biometric Search");
        radioButtonGroup.add(searchByBiometricRadioButton);
        radioButtonPanel.add(searchByBiometricRadioButton);

        // add radio buttons to base panel
        configureGridBagConstraints(basePanelConstraints, 1, 5, 1, 2);
        basePanel.add(radioButtonPanel, basePanelConstraints);

        // set up search text field
        searchValueField = new JTextField("User enters search string here");
        /* searchValueField.setBackground(Colours.bg);
        searchValueField.setBorder(new LineBorder(Colours.onBg, 1)); */
        //searchValueField.setForeground(Colours.onBg);
        searchValueField.addFocusListener(new SearchValueFieldFocusListener());
        configureGridBagConstraints(basePanelConstraints, 1, 5, 1, 4);
        basePanel.add(searchValueField, basePanelConstraints);

        idListLabel = new JLabel("List of Found IDs");
        idListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        /* idListLabel.setForeground(Colours.onBg);
        idListLabel.setBackground(Colours.bg); */
        idListLabel.setBorder(new LineBorder(Colours.onBg, 1));
        configureGridBagConstraints(basePanelConstraints, 1, 1, 1, 6);
        basePanel.add(idListLabel, basePanelConstraints);

        // set up located IDs list with scroll pane
        idListModel = new DefaultListModel<>();
        idList = new JList<>(idListModel);
        //idList.setBackground(Colours.bg);
        //idList.setBorder(new LineBorder(Colours.bg, 0));
        /* idList.setForeground(Colours.onBg);
        idList.setSelectionBackground(Colours.accent);
        idList.setSelectionForeground(Colours.onAccent); */
        idList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        // remove border around cell on selection
        /* idList.setCellRenderer(new DefaultListCellRenderer() {

            private static final long serialVersionUID = -7668408883782835744L;

            /**
             * Remove blue outline from selected list items
             
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Border border = null;
                setBorder(border);
                return c;
            }
        }); */
        idList.addListSelectionListener(new IdListSelectionListener());
        idListScrollPane = new JScrollPane(idList);
        configureGridBagConstraints(basePanelConstraints, 8, 1, 1, 7);
        basePanel.add(idListScrollPane, basePanelConstraints);

        // setup citizen detail pane
        citizenDetailArea = new JEditorPane("text/html", "Details of a selected record are displayed here. Select a record from the list to the left after doing a search");
        /* citizenDetailArea = new JEditorPane("text/html",
                "<html><head><style> body {font-family: Dialog;font-size: 10px}</style></head><body>Details of a selected record are displayed here.<br/> Select a record from the list to the left after doing a search</body></html>");
        citizenDetailArea.setEditable(false);
        citizenDetailArea.setBackground(Colours.bg);
        citizenDetailArea.setForeground(Colours.onBg); */
        //citizenDetailArea.setBorder(new LineBorder(Colours.onBg, 0));
        citizenDetailAreaScrollPane = new JScrollPane(citizenDetailArea);
        configureGridBagConstraints(basePanelConstraints, 9, 3, 3, 6);
        basePanel.add(citizenDetailAreaScrollPane, basePanelConstraints);

        add(basePanel);
        pack();

    }

    /**
     * Results class to help manage the search results
     */
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
     * Clearsthe search field and gives it focus
     */
    private class ClearButtonListener extends ButtonListener {

        @Override
        public void mousePressed(MouseEvent e) {
            searchValueField.setText("");
            searchValueField.requestFocus();
        }

    }

    /**
     * Searches for a citizen based on the criteria specified by the user. The
     * citizen's data is added to the results list if found.
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
                    String ln = searchValueField.getText();
                    String[] t = appController.searchGUI(null, ln);
                    if (t.length==0){
                        throw new IndexOutOfBoundsException("No persons with the last name found.");
                    }
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

    /**
     * Allows for the corresponding citizen information to be displayed in the
     * editor pane to the right when an ID in the list is selected.
     */
    private class IdListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                int index = idList.getSelectedIndex();
                citizenDetailArea.setText(results.get(index).getInfo());
                citizenDetailArea.setCaretPosition(0);
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
            ConfirmQuitDialog exit = new ConfirmQuitDialog(thisFrame);
            exit.setVisible(true);
        }

    }

    /**
     * Highlights the search field with the accent colour when it gains focus
     */
    private class SearchValueFieldFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            /* JTextField field = (JTextField) e.getSource();
            field.setBorder(new MatteBorder(new Insets(0, 0, 2, 0), Colours.accent));
            field.setBackground(new Color(255, 245, 168)); */
        }

        @Override
        public void focusLost(FocusEvent e) {
            /* JTextField field = (JTextField) e.getSource();
            field.setBorder(new LineBorder(Colours.onBg, 1));
            field.setBackground(Colours.bg); */
        }

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

    public static void main(String[] args) {
        SNIDApp appController;
        try {
            appController = new SNIDApp("data.db", ',');
            SNIDGUI ui = new SNIDGUI(appController);
            ui.setVisible(true);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}