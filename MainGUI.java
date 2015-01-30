import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
//TODO import .list specifically because otherwise compiler doesn't know if we're using util.List or awt.List

/**
 * Main GUI which allows the user to enter match details, view information about the referees and search for a referee.
 */
public class MainGUI extends JFrame implements ActionListener {
	//used in getWeekInfo() below
	private final int BAD_INFO = -1;
	/**
	 * Creates the table to display the referees and their information in the center of the main GUI
	 */
	DefaultTableModel model;
	private Object[] refArray;
	private final Object[] columnNames = {"ID", "Name", "Qualification", "Allocations", "Home", "North", "Central", "South"};
	private JPanel left, center, right, matchTitlePanel, weekPanel, locationPanel, levelPanel,
	allocatePanel, barChartPanel, addPanel, searchTitlePanel, firstPanel, lastPanel, searchPanel; // panels which are used to house the components, internal panels aid in layout
	private JLabel matchTitle, weekLabel, locationLabel, levelLabel, firstNameLabel, lastNameLabel; // labels to indicate to the user what they are to enter
	private JTextField weekField, firstNameField, lastNameField; // the textfields to enter the week in which a match takes place and the name of the ref to be searched for
	private JButton allocateRefButton, barChartButton, addRefButton, searchRefButton; // the buttons which allow the user to allocate a ref, see the bar chart and add/view a ref
	private JRadioButton northButton, centralButton, southButton, juniorButton, seniorButton; // the radio buttons to select the match location and level
	private ButtonGroup locationGroup, levelGroup; // the groups for the radio buttons to ensure that they are mutually exclusive
	private JTable centerTable; // the JTable which displays the information about the referees
	private JScrollPane centerScroll; // the scrollpane object which houses the JTable component
	private RefList refereeList; // a RefList object which contains all the referees that have been entered so far
	private MatchList matchList; // a MatchList object which contains all the matches that have been entered

	/**
	 * Constructs the main GUI window and creates the MatchList and RefList objects
	 */
	public MainGUI() {
		this.setTitle("Referee Selection");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1200, 260);
		this.setLocationRelativeTo(null);
		refereeList = new RefList();
		FileProcessor.readIn("RefereesIn.txt", refereeList);
		matchList = new MatchList();
		this.layoutComponents();
	}

	/**
	 * Sets out the different GUI components within the JFrame
	 */
	public void layoutComponents() {
		// Create left JPanel
		left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setBorder(BorderFactory.createTitledBorder("Allocate Referees"));
		this.add(left, BorderLayout.WEST);

		// Create internal JPanels
		matchTitlePanel = new JPanel();
		weekPanel = new JPanel();
		locationPanel = new JPanel();
		levelPanel = new JPanel();
		allocatePanel = new JPanel();

		// Create label for title/instructions for left panel
		matchTitle = new JLabel("To allocate two suitable referees enter the match details below");
		matchTitlePanel.add(matchTitle);

		// Create label and textField for match week number
		weekLabel = new JLabel("Week Number (1-52):");
		weekPanel.add(weekLabel);
		weekField = new JTextField(2);
		weekPanel.add(weekField);

		//Create Label and radio buttons for match location
		locationLabel = new JLabel("Match Location:");
		locationPanel.add(locationLabel);
		northButton = new JRadioButton("North");
		centralButton = new JRadioButton("Central");
		southButton = new JRadioButton("South");

		//Group the location JRadioButtons so that they are mutually exclusive

		locationGroup = new ButtonGroup();
		locationGroup.add(northButton);
		locationGroup.add(centralButton);
		locationGroup.add(southButton);

		locationPanel.add(northButton);
		locationPanel.add(centralButton);
		locationPanel.add(southButton);

		//set northButton to selected by default
		northButton.setSelected(true);

		// Create label and radio buttons for level
		levelLabel = new JLabel("Level:");
		levelPanel.add(levelLabel);

		juniorButton = new JRadioButton("Junior");
		seniorButton = new JRadioButton("Senior");

		// Group the level JRadioButtons so they are mutually exclusive
		levelGroup = new ButtonGroup();
		levelGroup.add(juniorButton);
		levelGroup.add(seniorButton);
		levelPanel.add(juniorButton);
		levelPanel.add(seniorButton);

		//set juniorButton to selected by default
		juniorButton.setSelected(true);

		//Create label and button for finding suitable referee
		allocateRefButton = new JButton("Allocate");
		allocateRefButton.addActionListener(this);
		allocatePanel.add(allocateRefButton);

		// Add internal panels to the left JPanel
		left.add(matchTitlePanel);
		left.add(weekPanel);
		left.add(locationPanel);
		left.add(levelPanel);
		left.add(allocatePanel);

		// Create center JPanel
		center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		this.add(center, BorderLayout.CENTER);

		//Create internal JPanel and scrollpane
		barChartPanel = new JPanel();

		//barChartPanel.setMaximumSize(new Dimension(400,400));
		// Use the setCenterTable method to populate the table and add it to the scrollpane
		//TODO Exception in thread "main" java.lang.NumberFormatException: For input string: "North"
		setCenterTable();

		//centerScroll.add(centerTable);
		centerScroll = new JScrollPane(centerTable);
		centerTable.setFillsViewportHeight(true);

		//Create button for bar chart and add to internal JPanel
		barChartButton = new JButton("View allocations");
		barChartButton.addActionListener(this);
		barChartPanel.add(barChartButton);
		//Add internal panels to center JPanel TODO why not place it in south?
		center.add(centerScroll);
		//center.add(barChartPanel);
		this.add(barChartPanel, BorderLayout.SOUTH);
		// Create right JPanel
		right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		this.add(right, BorderLayout.EAST);

		// Create the internal JPanel
		addPanel = new JPanel();

		searchTitlePanel = new JPanel(new GridLayout(3, 1));

		//searchTitlePanel.setMaximumSize(new Dimension(370,370));
		firstPanel = new JPanel();
		lastPanel = new JPanel();
		searchPanel = new JPanel();

		//Create button for adding new ref
		addRefButton = new JButton("Add referee");
		addRefButton.addActionListener(this);
		//addPanel.add(addRefButton);
		//this.add(addRefButton, BorderLayout.SOUTH);
		barChartPanel.add(addRefButton)
		;
		//Add a border and title to the searchpanel
		searchTitlePanel.setBorder(BorderFactory.createTitledBorder("Search:"));

		//Create label and button for first name
		firstNameLabel = new JLabel("First Name:");
		firstPanel.add(firstNameLabel);
		firstNameField = new JTextField(10);
		firstPanel.add(firstNameField);

		// Create label and button for last name
		lastNameLabel = new JLabel("Last Name:");
		lastPanel.add(lastNameLabel);
		lastNameField = new JTextField(10);
		lastPanel.add(lastNameField);

		// Create the button for searching for the referee
		searchRefButton = new JButton("Search");
		searchRefButton.addActionListener(this);
		searchPanel.add(searchRefButton);
		searchTitlePanel.add(firstPanel);
		searchTitlePanel.add(lastPanel);
		searchTitlePanel.add(searchPanel);

		// Add the internal panels to right JPanel
		right.add(searchTitlePanel);
		//right.add(addPanel);
		// right.add(firstPanel);
		// right.add(lastPanel);
		// right.add(searchPanel);
	}

	private void setCenterTable() {
		// Create the array of the column names for the JTable
		//String[] columns = {"ID", "Name", "Qualification", "Allocations", "Home", "North", "Central", "South"};
		// Create the array which will contain the information on each referee
		//final Object[] refArray = new Object[8];

		// Create the model for the JTable, ensuring it is non editable and the data is displayed correctly
		//		DefaultTableModel model = new DefaultTableModel()

		model = new DefaultTableModel(columnNames, 0) 
		{

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Class<?> getColumnClass(int colIndex) {
				return refArray[colIndex].getClass();
			}
		};
		// Use the columns array to set the column names
		//model.setColumnIdentifiers(columns);
		populateTable();        

	}

	public void populateTable() 
	{		
		refArray = new Object[8];

		for (Referee ref : refereeList) {
			refArray[0] = ref.getRefID();
			refArray[1] = ref.getFName() + " " + ref.getLName();
			refArray[2] = ref.getQualificationType() + ref.getQualificationLevel();
			refArray[3] = ref.getNumAllocs();
			refArray[4] = ref.getHomeString();
			refArray[5] = ref.getTravelInfo(Referee.NORTH);
			refArray[6] = ref.getTravelInfo(Referee.CENTRAL);
			refArray[7] = ref.getTravelInfo(Referee.SOUTH);
			// Add the array for each referee into each of the rows of the table
			model.addRow(refArray);
		}

		// Create JTable and add it to the scrollpane
		centerTable = new JTable(model);
		centerTable.setGridColor(Color.LIGHT_GRAY);
	}


	/**
	 * Decides which action will be taken depending on which input the user has given
	 *
	 * @param e the action event which results from the user pressing one of the buttons
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == barChartButton) {
			BarChartViewer chart = new BarChartViewer(refereeList);
		}
		if (e.getSource() == addRefButton) {
			showLittleGui(LittleGUI.ADD);
		}
		if (e.getSource() == allocateRefButton) {
			checkForSuitableRefs();
			clearAllocComponents();
			model.setValueAt(null, 0, 0);
		}
		if (e.getSource() == searchRefButton) {
			//should we have these checks in a separate method?
					if (!firstNameField.getText().trim().equals("") && !lastNameField.getText().trim().equals("")) {
						Referee ref = refereeList.findRef(firstNameField.getText().trim(), lastNameField.getText().trim());
						if (ref != null)
							showLittleGui(LittleGUI.SEARCH, ref);
						else {
							errorPane("The referee " + firstNameField.getText().trim() + " " + lastNameField.getText().trim() + " " + "was not found in the database.");
							clearNameFields();
						}
					} else {
						errorPane("First Name and Last Name fields cannot be empty.");
						clearNameFields();
					}
		}
	}

	/**
	 * Constructs the window where the user can enter information about a referee
	 *
	 * @param mode sets whether or not the information will be editable as set down in the LittleGUI
	 */
	private void showLittleGui(int mode) {
		LittleGUI littleGUI = new LittleGUI(mode, refereeList, this);
		littleGUI.setVisible(true);
	}

	/**
	 * Constructs the windows where the user can view and edit the inforamtion about a referee
	 *
	 * @param mode sets whether the information about the referee is editable or not
	 * @param ref  the referee object which will be displayed and can be edited
	 */
	private void showLittleGui(int mode, Referee ref) {
		LittleGUI littleGUI = new LittleGUI(mode, ref, refereeList, this);
		littleGUI.setVisible(true);
	}

	/**
	 * Displays two referees which are suitable for the match which has been entered if they exist
	 */
	private void checkForSuitableRefs() {
		//first make sure there is room for another match
		if (matchList.getNoMatches() == 52) {
			errorPane("All the weeks in the year are allocated.");
			return; //if no room for more matches, exit method
		}

		//if there is room for another match, get match info input by user
		int week = getWeekInfo();
		int loc = getLocationInfo();
		//check that all info has been input and is OK
		if (levelIsSelected() && week != BAD_INFO && loc != BAD_INFO) {
			if (!matchList.checkWeekAllocation(week)) {
				errorPane("Week " + week + " is already allocated.");
				return; //if week is already taken, exit method
			}
			boolean senMatch = getSeniorInfo();
			List<Referee> suitableRefs = refereeList.getSuitableRefs(loc, senMatch);
			if (suitableRefs.size() < 2)
				errorPane("Not enough suitable refs found.");
			else
				allocateTwoRefs(suitableRefs, week, loc, senMatch);
			//TODO call method to update center area with list of suitable refs
			//and display which 2 refs have been allocated to the match
			clearNameFields();
			//TODO testing if we got the right refs....
			for (Referee r : suitableRefs) {
				System.out.println(r.getFName() + " " + r.getLName());
			}
		}
	}

	/**
	 * @param suitRefs
	 * @param weekNumber
	 * @param place
	 * @param senior
	 */
	private void allocateTwoRefs(List<Referee> suitRefs, int weekNumber, int place, boolean senior) {
		Referee ref1 = suitRefs.get(0);
		Referee ref2 = suitRefs.get(1);

		//TODO now passing Ref's full name to Match constructor - but I still think we should be passing the whole object. maybe.
		String ref1Name = ref1.getFName() + " " + ref1.getLName();
		String ref2Name = ref2.getFName() + " " + ref2.getLName();
		//TODO updateRows
		matchList.alternativeAddMatch(weekNumber, place, senior, ref1Name, ref2Name);
		ref1.incrementAllocs();
		ref1.setAllocated(true);
		ref2.incrementAllocs();
		ref1.setAllocated(true);
	}


	/**
	 * Retrieves the week number from its respective textfield and ensures it is valid
	 *
	 * @throws nfx thrown if the input into the week number field is not an integer
	 */
	private int getWeekInfo() {
		try {
			int week = Integer.parseInt(weekField.getText());
			//I'm assuming that the first week is week 1 and not 0 right?
			if (week < 1 || week > MatchList.MAX_MATCHES) {
				errorPane("Please enter a week between 1 and " + MatchList.MAX_MATCHES + ".");
				return BAD_INFO;
			} else return week;
		} catch (NumberFormatException nfx) {
			errorPane("Please enter a valid week.");
			return BAD_INFO;
		}
	}


	/**
	 * Retrieves the location of the match and returns it as a constant value which is set down in the Referee class
	 *
	 * @return Referee.SOUTH the final integer 2 which is used as an indicator
	 */
	private int getLocationInfo() {
		if (northButton.isSelected()) {
			System.out.println("match is in the north");
			return Referee.NORTH;
		} else if (centralButton.isSelected())
			return Referee.CENTRAL;
		else if (southButton.isSelected())
			return Referee.SOUTH;
		else {
			errorPane("Please select the match location.");
			return BAD_INFO;
		}
	}

	private boolean levelIsSelected() {
		if (juniorButton.isSelected() || seniorButton.isSelected())
			return true;
		else {
			errorPane("Please select the match level.");
			return false;
		}
	}

	private boolean getSeniorInfo() {
		return seniorButton.isSelected();
	}

	private void clearNameFields() {
		firstNameField.setText("");
		lastNameField.setText("");
	}

	private void clearAllocComponents() {
		weekField.setText("");
		locationGroup.clearSelection();
		levelGroup.clearSelection();
	}

	/**
	 * Creates a JOption pane with a custom error message
	 *
	 * @param errorMessage Message to display on the JOptionPane
	 */
	public void errorPane(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void updateTable()
	{
		model.setRowCount(0);
		populateTable();
	}

}