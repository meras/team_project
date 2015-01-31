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
 * LittleGUI is a view class which is responsible for displaying and informing the {@link RefList} controller class 
 * about any inputs from the user. It has 3 distinct modes Add, Edit and Search which change the available options to the user
 * based on what inputs have been passed from the MainGUI. The class is also responsible for validating any input for deleting,
 * adding or editing referees which is then passed to the controller.
 *
 * @see RefList
 */
public class LittleGUI extends JFrame implements ActionListener 
{
	
	//TODO check and add if needed functionality to the clear button
	//Reference variables for all the buttons
	private JButton editButton, saveAddButton, deleteButton, clearButton, exitButton;
	//Reference variables for the two comboboxes
	private final String[] qualificationTypeList = {"Type", "NJB","IJB"};
	private final String[] qualificationList = {"Level","1","2","3","4"}; 
	private JComboBox qualificationTypeCombo, qualificationsCombo;
	//Reference variables for all the text fields
	private JTextField fNameField, lNameField, idField, matchField;
	private JLabel idLabel;
	//Reference variables for the radio and check buttons.
	private JRadioButton northRadio, centralRadio, southRadio;
	private Checkbox northCheck, centralCheck, southCheck;
	//Reference variables for the radio button group. Check buttons don't have a group so an enumeration is used instead.
	private ButtonGroup homeGroup;
	private Enumeration<AbstractButton> homeRadios;
	/**Reference variables for the {@link Referee}, {@link RefList}, {@link MainGUI} classes*/
	private Referee referee;
	private RefList refList;
	private MainGUI mainGUI;
	//Reference variable to the bottom JPanel which changes in different modes of LittleGUI
	private JPanel bottom;

	/* following constants are used to set the GUI mode */
	public static final int ADD = 0;
	public static final int SEARCH = 1;

	/**
	 * The constructor for LittleGUI. It takes an int mode to decide which type of LittleGUI to be displayed. Ref is the referee passed from the MainGUI
	 * refereeList is again passed from the MainGUI and refGUI is the MainGUI itself.
	 * If the value of ref is not null the constructor makes a LittleGUI with the details of the selected referee in the existing fields.
	 * @param mode
	 * @param ref
	 * @param refereeList
	 * @param refGUI
	 */

	public LittleGUI(int mode, Referee ref, RefList refereeList, MainGUI refGUI) 
	{
		//assign the instance variables the values passed from MainGUI
		constructGui(mode);
		refList = refereeList;
		referee = ref;
		mainGUI = refGUI;

		//if ref is not null
		if (referee != null) 
		{
			//set Referee details
			fNameField.setText(referee.getFName());
			lNameField.setText(referee.getLName());
			idField.setText(referee.getRefID());
			matchField.setText("" + referee.getNumAllocs());     
			qualificationTypeCombo.setSelectedItem(referee.getQualificationType());
			qualificationsCombo.setSelectedItem(String.valueOf(referee.getQualificationLevel()));   

			//find home area
			if (referee.getHomeArea() == 0)
				northRadio.setSelected(true);
			else if (referee.getHomeArea() == 1)
				centralRadio.setSelected(true);
			else
				southRadio.setSelected(true);      

			//find preferences
			northCheck.setState(referee.getTravelInfo(Referee.NORTH));
			centralCheck.setState(referee.getTravelInfo(Referee.CENTRAL));
			southCheck.setState(referee.getTravelInfo(Referee.SOUTH));     
		}
	}

	/**
	 * Based on what mode is passed from the MainGUI constructs a new JFrame of the LittleGUI class
	 * @param mode
	 */
	private void constructGui(int mode) 
	{
		//sets the parameters of the JFrame
		setTitle("Referee Details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 400);
		setLocationRelativeTo(null);

		//layout the components of the JFrame
		layoutCenter();
		layoutBottom();

		//based on the input from the MainGUI construct the corresponding LittleGUI mode
		if (mode == ADD) 
		{
			showAdd();
		}else if (mode == SEARCH) 
		{
			showSearch();
		}
	}

	/**
	 * A method which lays out all the components in the center JPanel
	 */
	private void layoutCenter() 
	{
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
		homeRadios = homeGroup.getElements();
		home.add(new JLabel("Home:"));  
		
		//add all the radio buttons
		while (homeRadios.hasMoreElements())
		{
			home.add(homeRadios.nextElement());
		}

		JPanel preferrence = new JPanel();

		//TODO Needs to automatically spot when a referee radiobutton is clicked to highlight which is the default preference, 
		//needs also to be modified for search to automatically show results.

		northCheck = new Checkbox("North", false);
		centralCheck = new Checkbox("Central", false);
		southCheck = new Checkbox("South", false);

		//add all the preference buttons
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

	/**
	 * A method which lays down all the components from the bottom JPanel.
	 */

	private void layoutBottom() 
	{
		bottom = new JPanel();

		clearButton = new JButton("Clear");
		editButton = new JButton("Edit");
		saveAddButton = new JButton("Save");
		deleteButton = new JButton("Delete");
		exitButton = new JButton("Exit");


		clearButton.addActionListener(this);
		editButton.addActionListener(this);
		saveAddButton.addActionListener(this);
		deleteButton.addActionListener(this);
		exitButton.addActionListener(this);

		bottom.add(saveAddButton);
		bottom.add(clearButton);
		bottom.add(editButton);
		bottom.add(deleteButton);
		bottom.add(exitButton);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * A method which hides all bottom panel components
	 */
	private void hideBottomComponents() 
	{
		
		for (Component c : bottom.getComponents()) {
			c.setVisible(false);
		}
		
	}

	/**
	 * A method which edits the visibility and the editable property of certain components to show the appropriate Add mode.
	 */
	private void showAdd() 
	{
		hideBottomComponents();
		saveAddButton.setVisible(true);
		saveAddButton.setText("Add");
		exitButton.setVisible(true);
		matchField.setEditable(true);
		clearButton.setVisible(true);
		idField.setVisible(false);
		idLabel.setVisible(false);
	}

	/**
	 * A method which edits the visibility and the editable property of certain components to show the appropriate Search mode.
	 */
	private void showSearch() 
	{				
		hideBottomComponents();
		fNameField.setEditable(false);
		lNameField.setEditable(false);
		matchField.setEditable(false);
		qualificationTypeCombo.setEnabled(false);
		qualificationsCombo.setEnabled(false);
		editButton.setVisible(true);
		deleteButton.setVisible(true);
		exitButton.setVisible(true);

		while (homeRadios.hasMoreElements())
		{
			homeRadios.nextElement().setEnabled(false);
		}
		northCheck.setEnabled(false);
		centralCheck.setEnabled(false);
		southCheck.setEnabled(false);

		
	}

	/**
	 * A method which edits the visibility and the editable property of certain components to show the appropriate Edit mode.
	 */
	private void showEdit() {
		hideBottomComponents();

		saveAddButton.setVisible(true);
		saveAddButton.setText("Save");
		exitButton.setVisible(true);
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

	/**
	 * A method which handles any events submitted from the action listeners
	 */
	public void actionPerformed(ActionEvent e) 
	{

		//Edit button is pressed
		if (e.getSource() == editButton) 
		{
			showEdit();
		}

		//Save Button is pressed
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

			//Add button is pressed
			else
			{
				if (validateFields())
				{
					//If the referee with the same names has already been added to the database return an error
					if (refList.findRef(fNameField.getText(), lNameField.getText()) != null)
					{
						JOptionPane.showMessageDialog(this, "Adding referee failed. The referee already exists in the database.",
								"Error", JOptionPane.ERROR_MESSAGE);	
					}
					/**
					 * If the {@link RefList} has already have 12 referees added return an error
					 */
					else if (!refList.checkForSpace())
					{
						JOptionPane.showMessageDialog(this, "Adding referee failed. There can't be more than 12 referees in the database.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					/**
					 * if all checks have been passed add a new referee to {@link RefList}, update the MainGUI and display a message to feedback sucess
					 */
					else
					{
						refList.addRefFromGui(fNameField.getText(), lNameField.getText(), (String)qualificationTypeCombo.getSelectedItem() + Integer.parseInt((String)(qualificationsCombo.getSelectedItem())),  
								Integer.parseInt(matchField.getText()) , getHomeArea(), getTravelInfo()); 

						mainGUI.updateTable();
						clearFields();
						JOptionPane.showMessageDialog(this, "The referee has been added to the database.",
								"Success", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}	
		}

		//Delete button is pressed
		if (e.getSource() == deleteButton)
		{
			//Display a prompt to the user to confirm that they want to delete the referee
			int dialogResult = JOptionPane.showConfirmDialog (this, "Would you like to delete this referee?","Warning", JOptionPane.YES_NO_OPTION);

			//if yes call a method from RefList to delete the referee if the deletion was unsuccessful, return an error
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
				JOptionPane.showMessageDialog(this, "There was a problem deleting the referee. Please check if the referee still exists in the database.",
						"Error", JOptionPane.ERROR_MESSAGE);		
			}
		}

		//Exit button is pressed
		if (e.getSource() == exitButton)
		{
			dispose(); 
		}
		
		//Clear button is pressed
		if (e.getSource() == clearButton)
		{
			clearFields();
		}
	}


	/**
	 * A method which sets all the instance variables in referee based on the current inputs in the JComponents of LittleGUI
	 */
	private void setFields()
	{
		referee.setNumAllocs(Integer.parseInt(matchField.getText()));
		referee.setQualificationType((String)qualificationTypeCombo.getSelectedItem());
		referee.setQualificationLevel(Integer.parseInt((String)(qualificationsCombo.getSelectedItem())));
		referee.setHomeArea(getHomeArea());	
		referee.setTravelInfo(getTravelInfo());		
	}

	
	/**
	 * A method which validates the input from the JComponents of LittleGUI and returns an error if there is any discrepancy
	 * 
	 * @return boolean
	 */
	private boolean validateFields()
	{	

		//Checks if either of the name fields are empty strings 
		if (fNameField.getText().equals("") || lNameField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "The referee names cannot be empty strings.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Checks that the name of referee contains only letters 
		if (!(containsLetters(fNameField.getText()) || !(containsLetters(lNameField.getText()))))
		{		
			JOptionPane.showMessageDialog(this, "The referee names could only contain letters.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Catches any exceptions if the user enters something else than an integer
		try
		{
			Integer.parseInt(matchField.getText()); 		
		}

		
		catch (NumberFormatException nfe)
		{
			JOptionPane.showMessageDialog(this, "Please enter an integer number for the number of matches.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		//Checks if a qualification type has been selected
		if (qualificationTypeCombo.getSelectedItem().equals("Type"))
		{
			JOptionPane.showMessageDialog(this, "Please select a qualification type.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Checks if a qualification level has been selected
		if (qualificationsCombo.getSelectedItem().equals("Level"))
		{
			JOptionPane.showMessageDialog(this, "Please select a qualification level.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Checks if a home area has been selected
		if (getHomeArea().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please select a home area.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Checks if at least one preference has been selected
		if (getTravelInfo().equals("NNN"))
		{	
			JOptionPane.showMessageDialog(this, "Please select at least one preference.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Calls a method to check if the home are is reflected in the preferences
		if (!checkHomePreference())
		{
			JOptionPane.showMessageDialog(this, "The preferences should include the home area of the referee.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * A method which takes a string like a referee name, checks if it contains only letters and returns boolean value 
	 * @param name
	 * @return
	 */
	private boolean containsLetters(String name) {
		//make the string a char array
		char[] chars = name.toCharArray();
		//check each individual character
		for (char c : chars) {
			if(!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}
/**
 * A method which checks the current states of the Home Area Radio buttons in LittleGUI and returns a string suitable to be passed to {@link RefList}
 * @return
 */
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

	
	/**
	 * A method which checks the current states of the Preference check buttons in LittleGUI and returns a string suitable to be passed to {@link RefList}
	 * @return
	 */
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

	/**
	 * A method which checks if the preference corresponding check button has been selected after selecting a corresponding area. 
	 * For example if you select home radio button central you will have to select a central check button as well.
	 * @return
	 */
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
	
	/**
	 * A method which clears all the fields in LittleGUI
	 */
	private void clearFields()
	{
		fNameField.setText("");
		lNameField.setText("");
		matchField.setText("");     
		qualificationTypeCombo.setSelectedItem("Type");
		qualificationsCombo.setSelectedItem("Level");   

		while (homeRadios.hasMoreElements())
		{
			homeRadios.nextElement().setSelected(false);;
		}     

		//find preferences
		northCheck.setState(false);
		centralCheck.setState(false);
		southCheck.setState(false);     
		
	}
}
