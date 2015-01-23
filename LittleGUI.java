import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class LittleGUI extends JFrame implements ActionListener {
    private JButton editButton, saveButton, deleteButton, clearButton, cancelButton;
    private final String[] qualificationList = {"", "First", "Second", "Third"};
    private JTextField fNameField;
    private JTextField lNameField;
    private JTextField idField;
    private JTextField matchField;
    private JComboBox qualificationsCombo;
    private JRadioButton northRadio;
    private JRadioButton centerRadio;
    private JRadioButton southRadio;
    private JPanel bottom;

    ///ADD ref - everything is editable
    //SEARCH-

    public LittleGUI(String mode) {
        setTitle("edit info");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 400);
        setLocationRelativeTo(null);

        layoutCenter();
        layoutBottom(mode);
    }

    /**
     * center grid to house other grids
     */
    private void layoutCenter() {
        JPanel center = new JPanel(new GridLayout(6,1,2,2));

        fNameField = new JTextField(15);
        lNameField = new JTextField(15);
        idField = new JTextField(6);
        idField.setEditable(false);
        matchField = new JTextField(7);
        matchField.setEditable(false);
        qualificationsCombo = new JComboBox(qualificationList);

        ButtonGroup homeGroup = new ButtonGroup();

        JPanel fname = new JPanel();
        fname.add(new JLabel("First name:"));
        fname.add(fNameField);

        JPanel lname = new JPanel();
        lname.add(new JLabel("Last name:"));
        lname.add(lNameField);

        JPanel info = new JPanel();
        info.add(new JLabel("ID:"));
        info.add(idField);
        info.add(new JLabel("Matches:"));
        info.add(matchField);

        JPanel qualification = new JPanel();
        qualification.add(new JLabel("Qualification:"));
        qualification.add(qualificationsCombo);

        JPanel home = new JPanel();
        northRadio = new JRadioButton("North");
        centerRadio = new JRadioButton("Center");
        southRadio = new JRadioButton("South");
        homeGroup.add(northRadio);
        homeGroup.add(centerRadio);
        homeGroup.add(southRadio);
        home.add(new JLabel("Home:"));
        home.add(northRadio);
        home.add(centerRadio);
        home.add(southRadio);

        JPanel preferrence = new JPanel();
        northRadio = new JRadioButton("North");
        centerRadio = new JRadioButton("Center");
        southRadio = new JRadioButton("South");
        homeGroup.add(northRadio);
        homeGroup.add(centerRadio);
        homeGroup.add(southRadio);
        preferrence.add(new JLabel("Home:"));
        preferrence.add(northRadio);
        preferrence.add(centerRadio);
        preferrence.add(southRadio);

        //add all the panels to the center JPanel
        center.add(fname);
        center.add(lname);
        center.add(info);
        center.add(qualification);
        center.add(home);
        center.add(preferrence);
        add(center, BorderLayout.CENTER);

    }

    private void layoutBottom(String mode) {
            bottom = new JPanel();

        clearButton = new JButton("Clear");
        editButton = new JButton("Edit");
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");


        clearButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);

        if (mode.equals("Add")) {
            bottom.add(saveButton);
            bottom.add(cancelButton);
            //bottom.add(clearButton);
        }
        else if (mode.equals("Search")){
            bottom.add(editButton);
            bottom.add(deleteButton);
            bottom.add(cancelButton);
        }
        else if (mode.equals("Edit")) {
            bottom.add(saveButton);
            bottom.add(cancelButton);
        }

        add(bottom, BorderLayout.SOUTH);
    }

    private void showEdit() {
        for (Component c : bottom.getComponents()) {
            c.setVisible(false);
        }
        editButton.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            System.out.println("Are you sure you want to delete this entry");
        }
        if (e.getSource() == editButton) {
            showEdit();
        }

    }
}
