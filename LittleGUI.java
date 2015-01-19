import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class LittleGUI extends JFrame implements ActionListener {
    private JButton editButton, saveButton, deleteButton, clearButton;
    private final String[] qualificationList = {"", "First", "Second", "Third"};


    public LittleGUI() {
        setTitle("edit info");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);

        layoutCenter();
        layoutBottom();

    }

    /**
     * center grid to house other grids
     */
    private void layoutCenter() {
        JPanel center = new JPanel(new GridLayout(5,1,2,2));

        JTextField fNameField = new JTextField(15);
        JTextField lNameField = new JTextField(15);
        JTextField idField = new JTextField(6);
        idField.setEditable(false);
        JTextField matchField = new JTextField(7);
        matchField.setEditable(false);
        JComboBox qualificationsCombo = new JComboBox(qualificationList);

        JRadioButton northRadio, centerRadio, southRadio;
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

        //add all the components to the center JPanel
        center.add(fname);
        center.add(lname);
        center.add(info);
        center.add(qualification);
        center.add(home);
        add(center, BorderLayout.CENTER);

    }

    private void layoutBottom() {
        JPanel bottom = new JPanel();

        clearButton = new JButton("Clear");
        editButton = new JButton("Edit");
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");

        clearButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);

        bottom.add(clearButton);
        bottom.add(editButton);
        bottom.add(saveButton);
        bottom.add(deleteButton);
        add(bottom, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {

    }
}
