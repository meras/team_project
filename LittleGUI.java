import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

/**
 * LittleGUI is a view class which is responsible for displaying and informing the {@link RefList} controller class
 * about any inputs from the user. It has 3 distinct modes Add, Edit and Search which change the available options to the user
 * based on what inputs have been passed from the MainGUI. The class is also responsible for validating any input for deleting,
 * adding or editing referees which is then passed to the controller.
 *
 * @see RefList
 */
public class LittleGUI extends JFrame implements ActionListener {
    //Reference variables for all the buttons
    private JButton editButton, saveButton, addButton, deleteButton, clearButton, exitButton;
    //Reference variables for the two comboboxes
    private final String[] qualificationTypeList = {"Type", "NJB", "IJB"};
    private final String[] qualificationList = {"Level", "1", "2", "3", "4"};
    private JComboBox qualificationTypeCombo, qualificationsCombo;
    //Reference variables for all the text fields
    private JTextField fNameField, lNameField, idField, matchField;
    private JLabel idLabel;
    //Reference variables for the radio and check buttons.
    private JRadioButton northRadio, centralRadio, southRadio;
    private JCheckBox northCheck, centralCheck, southCheck;
    //Reference variables for the radio button group. Check buttons don't have a group so an enumeration is used instead.
    private ButtonGroup homeGroup;
    /**
     * Reference variables for the {@link Referee}, {@link RefList}, {@link MainGUI} classes
     */
    private final Referee referee;
    private final RefList refList;
    private final MainGUI mainGUI;
    //Reference variable to the bottom JPanel which changes in different modes of LittleGUI
    private JPanel bottom;

    /* following constants are used to set the GUI mode */
    public static final int ADD = 0;
    public static final int SEARCH = 1;

    /**
     * The constructor for LittleGUI.
     *
     * @param mode        use ADD or SEARCH to set the window mode. Search mode displays referee details, Add produces an empty window
     * @param ref         Referee object passed from the MainGUI. Can be null, used for creating an add window
     * @param refereeList The referee list
     * @param refGUI      the main GUI window
     */
    public LittleGUI(int mode, Referee ref, RefList refereeList, MainGUI refGUI) {
        // assign the instance variables the values passed from MainGUI
        refList = refereeList;
        referee = ref;
        mainGUI = refGUI;

        // create the window
        setTitle("Referee Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 400);
        setLocationRelativeTo(null);

        //layout the components of the JFrame
        layoutCenter();
        layoutBottom();

        //based on the input from the MainGUI construct the corresponding LittleGUI mode
        if (mode == ADD) {
            showAdd();
        } else if (mode == SEARCH) {
            showSearch();
        }
    }

    /**
     * A method which lays out all the components in the center JPanel
     */
    private void layoutCenter() {
        JPanel center = new JPanel(new GridLayout(6, 1, 2, 2));

        fNameField = new JTextField(15);
        lNameField = new JTextField(15);
        idField = new JTextField(6);
        idField.setEditable(false);
        matchField = new JTextField(7);
        matchField.setEditable(false);
        qualificationTypeCombo = new JComboBox<>(qualificationTypeList);
        qualificationsCombo = new JComboBox<>(qualificationList);
        qualificationsCombo.setLightWeightPopupEnabled(false);

        JPanel fname = new JPanel();
        fname.add(new JLabel("First name:"));
        fname.add(fNameField);

        JPanel lname = new JPanel();
        lname.add(new JLabel("Last name:"));
        lname.add(lNameField);

        JPanel info = new JPanel();
        idLabel = new JLabel("ID: ");
        info.add(idLabel);
        info.add(idField);
        info.add(new JLabel("Matches:"));
        info.add(matchField);

        JPanel qualification = new JPanel();

        qualification.add(new JLabel("Qualification: "));
        qualification.add(qualificationTypeCombo);
        qualification.add(qualificationsCombo);

        JPanel home = new JPanel();
        northRadio = new JRadioButton("North");
        centralRadio = new JRadioButton("Central");
        southRadio = new JRadioButton("South");

        homeGroup = new ButtonGroup();
        homeGroup.add(northRadio);
        homeGroup.add(centralRadio);
        homeGroup.add(southRadio);
        home.add(new JLabel("Home:"));
        home.add(northRadio);
        home.add(centralRadio);
        home.add(southRadio);

        JPanel preference = new JPanel();
        northCheck = new JCheckBox("North", false);
        centralCheck = new JCheckBox("Central", false);
        southCheck = new JCheckBox("South", false);

        //add all the preference buttons
        preference.add(new JLabel("Preference:"));
        preference.add(northCheck);
        preference.add(centralCheck);
        preference.add(southCheck);

        //add all the panels to the center JPanel
        center.add(fname);
        center.add(lname);
        center.add(info);
        center.add(qualification);
        center.add(home);
        center.add(preference);
        add(center, BorderLayout.CENTER);
    }

    /**
     * A method which lays down all the components from the bottom JPanel.
     */
    private void layoutBottom() {
        bottom = new JPanel();

        clearButton = new JButton("Clear");
        editButton = new JButton("Edit");
        saveButton = new JButton("Save");
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        exitButton = new JButton("Exit");

        clearButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        exitButton.addActionListener(this);

        bottom.add(saveButton);
        bottom.add(addButton);
        bottom.add(clearButton);
        bottom.add(editButton);
        bottom.add(deleteButton);
        bottom.add(exitButton);

        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * A method which hides all bottom panel components
     */
    private void hideBottomComponents() {
        for (Component c : bottom.getComponents()) {
            c.setVisible(false);
        }
    }

    /**
     * A method which edits the visibility and the editable property of certain components to show the appropriate Add mode.
     */
    private void showAdd() {
        hideBottomComponents();
        addButton.setVisible(true);
        exitButton.setVisible(true);
        matchField.setEditable(true);
        clearButton.setVisible(true);
        idField.setVisible(false);
        idLabel.setVisible(false);
    }

    /**
     * A method which edits the visibility and the editable property of certain components to show the appropriate Search mode.
     */
    private void showSearch() {
        hideBottomComponents();
        // set up the relevant GUI components
        fNameField.setEditable(false);
        lNameField.setEditable(false);
        matchField.setEditable(false);
        qualificationTypeCombo.setEnabled(false);
        qualificationsCombo.setEnabled(false);
        editButton.setVisible(true);
        deleteButton.setVisible(true);
        exitButton.setVisible(true);

        for (Component homeRadio : Collections.list(homeGroup.getElements())) {
            homeRadio.setEnabled(false);
        }
        northCheck.setEnabled(false);
        centralCheck.setEnabled(false);
        southCheck.setEnabled(false);

        //set Referee details
        fNameField.setText(referee.getFName());
        lNameField.setText(referee.getLName());
        idField.setText(referee.getRefID());
        matchField.setText("" + referee.getNumAllocs());
        qualificationTypeCombo.setSelectedItem(referee.getQualificationType());
        qualificationsCombo.setSelectedItem(String.valueOf(referee.getQualificationLevel()));

        //find home area
        if (referee.getHomeArea() == 0) {
            northRadio.setSelected(true);
        } else if (referee.getHomeArea() == 1) {
            centralRadio.setSelected(true);
        } else {
            southRadio.setSelected(true);
        }

        //find preferences
        northCheck.setSelected(referee.getTravelInfo(Referee.NORTH));
        centralCheck.setSelected(referee.getTravelInfo(Referee.CENTRAL));
        southCheck.setSelected(referee.getTravelInfo(Referee.SOUTH));
    }

    /**
     * A method which edits the visibility and the editable property of certain components to show the appropriate Edit mode.
     */
    private void showEdit() {
        hideBottomComponents();

        saveButton.setVisible(true);
        exitButton.setVisible(true);
        matchField.setEditable(true);
        qualificationTypeCombo.setEnabled(true);
        qualificationsCombo.setEnabled(true);

        for (Component homeRadio : Collections.list(homeGroup.getElements())) {
            homeRadio.setEnabled(true);
        }

        northCheck.setEnabled(true);
        centralCheck.setEnabled(true);
        southCheck.setEnabled(true);
    }

    /**
     * A method which handles any events submitted from the action listeners
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            showEdit();
        }
        if (e.getSource() == saveButton) {
            processSave();
        }
        if (e.getSource() == addButton) {
            processAdd();
        }
        if (e.getSource() == deleteButton) {
            processDelete();
        }
        if (e.getSource() == exitButton) {
            dispose();
        }
        if (e.getSource() == clearButton) {
            clearFields();
        }
    }

    /**
     * A method which processes the editing of the details of a referee based on the input from LittleGUI.
     */
    private void processSave() {
        if (validateFields()) {
            setFields();
            mainGUI.updateTable();
            dispose();
            JOptionPane.showMessageDialog(this, "The referee details have been updated.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * A method which processes the addition of a new referee to RefList based on the input from LittleGUI. THe ID is calculated automatically in RefList.
     */
    private void processAdd() {
        if (validateFields()) {
            //If the referee with the same names has already been added to the database return an error
            if (refList.findRef(fNameField.getText(), lNameField.getText()) != null) {
                errorPane("Adding referee failed. The referee already exists in the database.");
            }
            /**
             * If the {@link RefList} has already have 12 referees added return an error
             */
            else if (!refList.checkForSpace()) {
                errorPane("Adding referee failed. There can't be more than 12 referees in the database.");
            }
            /**
             * if all checks have been passed add a new referee to {@link RefList}, update the MainGUI and display a message to feedback sucess
             */
            else {
                refList.addRefFromGui(fNameField.getText(), lNameField.getText(), (String) qualificationTypeCombo.getSelectedItem() + Integer.parseInt((String) (qualificationsCombo.getSelectedItem())),
                        Integer.parseInt(matchField.getText()), getHomeArea(), getPreferences());

                mainGUI.updateTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "The referee has been added to the database.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * A method which processes the deletion of a referee from the reflist based on the input (first and last name) from the user from LittleGUI
     */
    private void processDelete() {
        //first check if ref has been allocated
        if (referee.isAllocated()) {
            errorPane("The referee cannot be deleted as he has been allocated to a match");
            return;
        }

        //Display a prompt to the user to confirm that they want to delete the referee
        int dialogResult = JOptionPane.showConfirmDialog(this, "Would you like to delete this referee?", "Warning", JOptionPane.YES_NO_OPTION);

        //if yes call a method from RefList to delete the referee if the deletion was unsuccessful, return an error
        if (dialogResult == JOptionPane.YES_OPTION) {
            boolean deleted = refList.deleteRef(referee.getFName(), referee.getLName());
            if (deleted) {
                mainGUI.updateTable();
                dispose();
                JOptionPane.showMessageDialog(this, "The referee has been deleted from the database.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                dispose();
                errorPane("There was a problem deleting the referee. Please check if the referee still exists in the database.");
            }
        }
    }

    /**
     * A method which sets all the instance variables in referee based on the current inputs in the JComponents of LittleGUI
     */
    private void setFields() {
        referee.setNumAllocs(Integer.parseInt(matchField.getText()));
        referee.setQualificationType((String) qualificationTypeCombo.getSelectedItem());
        referee.setQualificationLevel(Integer.parseInt((String) (qualificationsCombo.getSelectedItem())));
        referee.setHomeArea(getHomeArea());
        referee.setTravelInfo(getPreferences());
    }

    /**
     * A method which validates the input from the JComponents of LittleGUI and returns an error if there is any discrepancy
     *
     * @return boolean
     */
    private boolean validateFields() {
        //Checks if either of the name fields are empty strings
        if (fNameField.getText().equals("") || lNameField.getText().equals("")) {
            errorPane("The referee names cannot be empty strings.");
            return false;
        }

        //Checks that the name of referee contains only letters
        if (!(validName(fNameField.getText())) || !(validName(lNameField.getText()))) {
            errorPane("The referee names should only contain letters.");
            return false;
        }

        //Catches any exceptions if the user enters something else than an integer
        try {
            Integer.parseInt(matchField.getText());
        } catch (NumberFormatException nfe) {
            errorPane("Please enter an integer number for the number of matches.");
            return false;
        }

        //Checks if a qualification type has been selected
        if (qualificationTypeCombo.getSelectedItem().equals("Type")) {
            errorPane("Please select a qualification type.");
            return false;
        }

        //Checks if a qualification level has been selected
        if (qualificationsCombo.getSelectedItem().equals("Level")) {
            errorPane("Please select a qualification level.");
            return false;
        }

        //Checks if a home area has been selected
        if (getHomeArea().equals("")) {
            errorPane("Please select a home area.");
            return false;
        }

        //Checks if at least one preference has been selected
        if (getPreferences().equals("NNN")) {
            errorPane("Please select at least one preference.");
            return false;
        }

        //Calls a method to check if the home are is reflected in the preferences
        if (!checkHomePreference()) {
            errorPane("The preferences should include the home area of the referee.");
            return false;
        }

        return true;
    }

    /**
     * Returns true if name is alphabetic with an optional single hyphen only if it is not the first or last character
     *
     * @param name First or last name
     * @return true if name is valid
     */
    private boolean validName(String name) {
        return name.matches("^(?!-)[a-zA-Z]+-?[a-zA-Z]*$(?<!-)");
    }

    /**
     * A method which checks the current states of the Home Area Radio buttons in LittleGUI and returns a string suitable to be passed to {@link RefList}
     *
     * @return home area as string, empty if not selected
     */
    private String getHomeArea() {
        if (northRadio.isSelected())
            return "North";
        else if (centralRadio.isSelected())
            return "Central";
        else if (southRadio.isSelected())
            return ("South");
        else
            return "";
    }

    /**
     * A method which checks the current states of the Preference check buttons in LittleGUI and returns a string suitable to be passed to {@link RefList}
     *
     * @return a three letter string representing preferred locations
     */
    private String getPreferences() {
        char[] travelInfo = {'N', 'N', 'N'};

        if (northCheck.isSelected()) {
            travelInfo[0] = 'Y';
        }
        if (centralCheck.isSelected()) {
            travelInfo[1] = 'Y';
        }
        if (southCheck.isSelected()) {
            travelInfo[2] = 'Y';
        }
        return new String(travelInfo);
    }

    /**
     * Check if home area and preferred area are both selected.
     *
     * @return true if home area and preference both match
     */
    private boolean checkHomePreference() {
        return (northRadio.isSelected() && northCheck.isSelected()) ||
                (centralRadio.isSelected() && centralCheck.isSelected()) ||
                (southRadio.isSelected() && southCheck.isSelected());
    }

    /**
     * A method which clears all the fields in LittleGUI
     */
    private void clearFields() {
        fNameField.setText("");
        lNameField.setText("");
        matchField.setText("");
        qualificationTypeCombo.setSelectedItem("Type");
        qualificationsCombo.setSelectedItem("Level");

        homeGroup.clearSelection();

        northCheck.setSelected(false);
        centralCheck.setSelected(false);
        southCheck.setSelected(false);
    }
    /**
     * Creates a JOption pane with a custom error message
     *
     * @param errorMessage Message to display on the JOptionPane
     */
    private void errorPane(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
