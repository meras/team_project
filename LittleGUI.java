import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *Referee instance variable for the GUI which is 
 *Save button for edit part, add for the add part 
 *When adding a refe
 *Split qualification into a string and integer
 *Search should all work
 *Check if referee has been allocated any matches so he/she can't be deleted
 */
public class LittleGUI extends JFrame implements ActionListener {
	private JButton editButton, saveAddButton, deleteButton, clearButton, cancelButton;
	private final String[] qualificationTypeList = {"Type", "NJB","IJB"};
	private final String[] qualificationList = {"Level","1","2","3","4"}; 
	private JTextField fNameField, lNameField, idField, matchField;
	private JComboBox qualificationTypeCombo, qualificationsCombo;
	private ButtonGroup homeGroup;
	private JRadioButton northRadio, centralRadio, southRadio;
	private Checkbox northCheck, centralCheck, southCheck;
	private Referee referee;
	private RefList refList;
	private MainGUI mainGUI;
	private Enumeration<AbstractButton> homeRadios;


	private JPanel bottom;

	/* following constants are used to set the GUI mode */
	public static final int ADD = 0;
	public static final int EDIT = 1;
	public static final int SEARCH = 1;

	///ADD ref - everything is editable
	//SEARCH-

	public LittleGUI(int mode, RefList refereeList, MainGUI refGUI) 
	{
		constructGui(mode);
		refList = refereeList;
		mainGUI = refGUI;
	}

	public LittleGUI(int mode, Referee ref, RefList refereeList, MainGUI refGUI) 
	{
		constructGui(mode);
		refList = refereeList;
		referee = ref;
		mainGUI = refGUI;


		if (referee != null) {
			fNameField.setText(referee.getFName());
			lNameField.setText(referee.getLName());
			idField.setText(referee.getRefID());
			matchField.setText("" + referee.getNumAllocs());     
			qualificationTypeCombo.setSelectedItem(referee.getQualificationType());
			qualificationsCombo.setSelectedItem(String.valueOf(referee.getQualificationLevel()));   

			if (referee.getHomeArea() == 0)
				northRadio.setSelected(true);
			else if (referee.getHomeArea() == 1)
				centralRadio.setSelected(true);
			else
				southRadio.setSelected(true);      

			northCheck.setState(referee.getTravelInfo(Referee.NORTH));
			centralCheck.setState(referee.getTravelInfo(Referee.CENTRAL));
			southCheck.setState(referee.getTravelInfo(Referee.SOUTH));     
		}
	}

	private void constructGui(int mode) {
		setTitle("Referee Details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 400);
		setLocationRelativeTo(null);

		layoutCenter();
		layoutBottom();

		if (mode == ADD) {
			showAdd();
		}else if (mode == SEARCH) {
			showSearch();
		}
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
		qualificationTypeCombo = new JComboBox(qualificationTypeList);
		qualificationsCombo = new JComboBox(qualificationList);

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
		homeRadios = homeGroup.getElements();
		home.add(new JLabel("Home:"));  
		while (homeRadios.hasMoreElements())
		{
			home.add(homeRadios.nextElement());
		}

		JPanel preferrence = new JPanel();

		//TO DO Needs to automatically spot when a referee radiobutton is clicked to highlight which is the default preference, 
		//needs also to be modified for search to automatically show results.

		northCheck = new Checkbox("North", false);
		centralCheck = new Checkbox("Central", false);
		southCheck = new Checkbox("South", false);

		preferrence.add(new JLabel("Preference:"));
		preferrence.add(northCheck);
		preferrence.add(centralCheck);
		preferrence.add(southCheck);


		//add all the panels to the center JPanel
		center.add(fname);
		center.add(lname);
		center.add(info);
		center.add(qualification);
		center.add(home);
		center.add(preferrence);
		add(center, BorderLayout.CENTER);

	}

	private void layoutBottom() {
		bottom = new JPanel();

		clearButton = new JButton("Clear");
		editButton = new JButton("Edit");
		saveAddButton = new JButton("Save");
		deleteButton = new JButton("Delete");
		cancelButton = new JButton("Cancel");


		clearButton.addActionListener(this);
		editButton.addActionListener(this);
		saveAddButton.addActionListener(this);
		deleteButton.addActionListener(this);
		cancelButton.addActionListener(this);

		bottom.add(saveAddButton);
		bottom.add(clearButton);
		bottom.add(editButton);
		bottom.add(deleteButton);
		bottom.add(cancelButton);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Hides all bottom panel components
	 */
	private void hideBottomComponents() {
		for (Component c : bottom.getComponents()) {
			c.setVisible(false);
		}
	}

	private void showAdd() {
		hideBottomComponents();

		saveAddButton.setVisible(true);
		saveAddButton.setText("Add");
		cancelButton.setVisible(true);
		matchField.setEditable(true);
		centralRadio.setSelected(true);

	}

	private void showSearch() {
		hideBottomComponents();

		fNameField.setEditable(false);
		lNameField.setEditable(false);
		matchField.setEditable(false);
		qualificationTypeCombo.setEnabled(false);
		qualificationsCombo.setEnabled(false);

		while (homeRadios.hasMoreElements())
		{
			homeRadios.nextElement().setEnabled(false);
		}
		northCheck.setEnabled(false);
		centralCheck.setEnabled(false);
		southCheck.setEnabled(false);

		editButton.setVisible(true);
		deleteButton.setVisible(true);
		cancelButton.setVisible(true);
	}

	private void showEdit() {
		hideBottomComponents();

		saveAddButton.setVisible(true);
		saveAddButton.setText("Save");
		cancelButton.setVisible(true);
		matchField.setEditable(true);
		qualificationTypeCombo.setEnabled(true);
		qualificationsCombo.setEnabled(true);
		while (homeRadios.hasMoreElements())
		{
			homeRadios.nextElement().setEnabled(true);
		}
		northCheck.setEnabled(true);
		centralCheck.setEnabled(true);
		southCheck.setEnabled(true);


	}

	public void actionPerformed(ActionEvent e) 
	{

		if (e.getSource() == editButton) {
			showEdit();
		}

		if (e.getSource() == saveAddButton)
		{
			if (saveAddButton.getText().equals("Save"))
			{
				if (validateFields())
				{
					setFields();
					mainGUI.updateTable();
					dispose();
					JOptionPane.showMessageDialog(this, "The referee details have been updated.",
							"Success", JOptionPane.INFORMATION_MESSAGE);
				}		
			}

			//the Add button is pressed
			else
			{
				if (validateFields())
				{
					if (refList.findRef(fNameField.getText(), lNameField.getText()) != null)
					{
						JOptionPane.showMessageDialog(this, "Adding referee failed. The referee already exists in the database.",
								"Error", JOptionPane.ERROR_MESSAGE);	
					}
					else if (!refList.checkForSpace())
					{
						JOptionPane.showMessageDialog(this, "Adding referee failed. There can't be more than 12 referees in the database.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						refList.addRefFromGui(fNameField.getText(), lNameField.getText(), (String)qualificationTypeCombo.getSelectedItem() + Integer.parseInt((String)(qualificationsCombo.getSelectedItem())),  
								Integer.parseInt(matchField.getText()) , getHomeArea(), getTravelInfo()); 

						mainGUI.updateTable();
						JOptionPane.showMessageDialog(this, "The referee has been added to the database.",
								"Success", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}	
		}

		if (e.getSource() == deleteButton)
		{
			int dialogResult = JOptionPane.showConfirmDialog (this, "Would you like to delete this referee?","Warning", JOptionPane.YES_NO_OPTION);

			if (dialogResult == JOptionPane.YES_OPTION)
			{

				boolean deleted = false;
				deleted = refList.deleteRef(referee.getFName(), referee.getLName());
				if (deleted)
				{
					mainGUI.updateTable();
					dispose();
					JOptionPane.showMessageDialog(this, "The referee has been deleted from the database.",
							"Success", JOptionPane.INFORMATION_MESSAGE);		
				}
				else
					dispose();
					JOptionPane.showMessageDialog(this, "There was a problem deleting the referee. Please check if the referee still exists in the dabase.",
							"Error", JOptionPane.ERROR_MESSAGE);		
			}
		}

		if (e.getSource() == cancelButton)
		{
			dispose(); 
		}
	}


	private void setFields()
	{
		referee.setNumAllocs(Integer.parseInt(matchField.getText()));
		referee.setQualificationType((String)qualificationTypeCombo.getSelectedItem());
		referee.setQualificationLevel(Integer.parseInt((String)(qualificationsCombo.getSelectedItem())));
		referee.setHomeArea(getHomeArea());	
		referee.setTravelInfo(getTravelInfo());		
	}

	private boolean validateFields()
	{	

		if (fNameField.getText().equals("") || lNameField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "The referee names cannot be empty strings.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!(isAlpha(fNameField.getText()) || isAlpha(lNameField.getText())))
		{		
			JOptionPane.showMessageDialog(this, "The referee names could only contain letters.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try
		{
			Integer.parseInt(matchField.getText()); 		
		}

		//Catches any exceptions if the user enters something else than an integer
		catch (NumberFormatException nfe)
		{
			JOptionPane.showMessageDialog(this, "Please enter an integer number for the number of matches.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (qualificationTypeCombo.getSelectedItem().equals("Type"))
		{
			JOptionPane.showMessageDialog(this, "Please select a qualification type.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (qualificationsCombo.getSelectedItem().equals("Level"))
		{
			JOptionPane.showMessageDialog(this, "Please select a qualification level.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (getHomeArea().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please select a home area.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (getTravelInfo().equals("NNN"))
		{	
			JOptionPane.showMessageDialog(this, "Please select at least one preference.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!checkHomePreference())
		{
			JOptionPane.showMessageDialog(this, "The preferences should include the home area of the referee.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private boolean isAlpha(String name) {
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c)) {
				return false;
			}
		}

		return true;
	}

	private String getHomeArea()
	{
		if (northRadio.isSelected())
			return "North";
		else if (centralRadio.isSelected())
			return "Central";
		else if (southRadio.isSelected())
			return ("South");
		else
			return "";
	}

	private String getTravelInfo()
	{
		String travelInfo = "";

		if (northCheck.getState())
			travelInfo += "Y";
		else
			travelInfo += "N";
		if (centralCheck.getState())
			travelInfo += "Y";
		else
			travelInfo += "N";
		if (southCheck.getState())
			travelInfo += "Y";
		else
			travelInfo += "N";
		return travelInfo;
	}

	private boolean checkHomePreference()
	{
		if (northRadio.isSelected()&&northCheck.getState())
			return true;
		if (centralRadio.isSelected()&&centralCheck.getState())
			return true;
		if (southRadio.isSelected()&&southCheck.getState())
			return true;

		return false;

	}
}
