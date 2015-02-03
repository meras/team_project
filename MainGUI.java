import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
//TODO import .list specifically because otherwise compiler doesn't know if we're using util.List or awt.List

/**
 * Main GUI which allows the user to enter match details, view information about the referees and search for a referee.
 */
public class MainGUI extends JFrame implements ActionListener {
	//used in getWeekInfo() below
	private final int BAD_INFO = -1;
	private final Object[] columnNames = {"ID", "Name", "Qualification", "Allocations", "Home", "North", "Central", "South"};
	private JPanel grid, top, allocRefsPanel, searchPanel, center, bottom, weekPanel, locationPanel, levelPanel,
			allocatePanel, firstPanel, lastPanel, searchButtonPanel; // panels which are used to house the components, internal panels aid in layout
	private JLabel weekLabel, locationLabel, levelLabel, firstNameLabel, lastNameLabel; // labels to indicate to the user what they are to enter
	private JTextField weekField, firstNameField, lastNameField;    // the textfields to enter the week in which a match takes place and the name of the ref to be searched for
	private JTextArea centerText;
	private JButton allocateRefButton, barChartButton, addRefButton, searchRefButton;   // the buttons which allow the user to allocate a ref, see the bar chart and add/view a ref
	private JRadioButton northButton, centralButton, southButton, juniorButton, seniorButton;   // the radio buttons to select the match location and level
	private ButtonGroup locationGroup, levelGroup;  // the groups for the radio buttons to ensure that they are mutually exclusive
	private JTable centerTable;     // the JTable which displays the information about the referees
	private JScrollPane tableScroll, textScroll;   // the scrollpane object which houses the JTable component
	private final RefList refereeList;    // a RefList object which contains all the referees that have been entered so far
	private final MatchList matchList;    // a MatchList object which contains all the matches that have been entered
	private final JTabbedPane tabbedPane = new JTabbedPane();

	/**
	 * Constructs the main GUI window and creates the MatchList and RefList objects
	 */
	public MainGUI() {
		this.setTitle("Referee Selection");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		refereeList = new RefList();
		FileProcessor.readIn("RefereesIn.txt", refereeList);
		matchList = new MatchList();
		this.layoutComponents();
	}

	/**
	 * Sets out the different GUI components within the JFrame
	 */
	private void layoutComponents() {
		// Create allocRefsPanel JPanel which will contain the match allocation components
		allocRefsPanel = new JPanel();
		allocRefsPanel.setLayout(new BoxLayout(allocRefsPanel, BoxLayout.Y_AXIS));
		allocRefsPanel.setBorder(BorderFactory.createTitledBorder("Allocate Referees"));

		// Create internal JPanels for each of the components
		weekPanel = new JPanel();
		locationPanel = new JPanel();
		levelPanel = new JPanel();
		allocatePanel = new JPanel();

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

		// Add internal panels to the allocRefsPanel JPanel
		allocRefsPanel.add(weekPanel);
		allocRefsPanel.add(locationPanel);
		allocRefsPanel.add(levelPanel);
		allocRefsPanel.add(allocatePanel);

		// Create searchPanel JPanel for the search referee components
		searchPanel = new JPanel(new GridLayout(3,1));
		searchPanel.setBorder(BorderFactory.createTitledBorder("Search for Referee"));

		// Create the internal JPanels for each of the components
		firstPanel = new JPanel();
		lastPanel = new JPanel();
		searchButtonPanel = new JPanel();

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
		searchButtonPanel.add(searchRefButton);

		// Add the internal panels to searchPanel JPanel
		searchPanel.add(firstPanel);
		searchPanel.add(lastPanel);
		searchPanel.add(searchButtonPanel);

		//Create the top JPanel which will contain both the allocRefsPanel and searchPanel JPanel so that they sit side by side
		top = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		top.add(allocRefsPanel, c);
		top.add(searchPanel, c);

		// Use the setCenterTable method to populate the table and add it to the scrollpane
		//TODO Exception in thread "main" java.lang.NumberFormatException: For input string: "North"
		setCenterTable();
		tableScroll = new JScrollPane(centerTable);
		centerTable.setFillsViewportHeight(true);

		// Create the text field which can be used to display information about the allocated referees
		centerText = new JTextArea(30, 41);
		centerText.setEditable(false);
		textScroll = new JScrollPane(centerText);

		// The center panel which contains the table or text field
		center = new JPanel();
		//center.add(tableScroll);
		//center.add(textScroll);

		/** tab experiment*/

		tabbedPane.addTab("Table", tableScroll);
		tabbedPane.addTab("TextArea", textScroll);
		/** end of tab experiment*/

		// Create the grid GUI which will contain the main sections and the table
		grid = new JPanel(new GridLayout(2,1));
		grid.add(top);
		//grid.add(center);
		grid.add(tabbedPane);
		this.add(grid, BorderLayout.CENTER);

		// Create bottom JPanel
		bottom = new JPanel();
		this.add(bottom, BorderLayout.SOUTH);

		//Create button for bar chart and add to internal JPanel
		barChartButton = new JButton("View allocations");
		barChartButton.addActionListener(this);
		bottom.add(barChartButton);

		//Create button for adding new ref
		addRefButton = new JButton("Add referee");
		addRefButton.addActionListener(this);
		bottom.add(addRefButton);
	}


	private DefaultTableModel model;
	private void setCenterTable() {
		// Create the model for the JTable, ensuring it is non editable and the data is displayed correctly
		model = new DefaultTableModel(columnNames, 0)
		{

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Class<?> getColumnClass(int colIndex) {
				return getValueAt(0, colIndex).getClass();
			}

		};
		populateTable();
	}

	private void populateTable() {
		for (Referee ref : refereeList) {
			model.addRow(new Object[]{
					ref.getRefID(),
					ref.getFName() +" "+ ref.getLName(),
					ref.getQualificationType() + ref.getQualificationLevel(),
					ref.getNumAllocs(),
					ref.getHomeString(),
					ref.getTravelInfo(Referee.NORTH),
					ref.getTravelInfo(Referee.CENTRAL),
					ref.getTravelInfo(Referee.SOUTH),
			});
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
			showLittleGui(LittleGUI.ADD, null);
		}
		if (e.getSource() == allocateRefButton) {
			checkForSuitableRefs();
			clearAllocComponents();
			updateTable();
		}
		if (e.getSource() == searchRefButton) {
			processSearch();
		}
	}

	/**
	 * Shows either a blank add referee window or displays search results
	 *
	 * @param mode sets whether the information about the referee is editable or not
	 * @param ref  the referee object which will be displayed and can be edited
	 */
	private void showLittleGui(int mode, Referee ref) {
		LittleGUI littleGUI = new LittleGUI(mode, ref, refereeList, this);
		littleGUI.setVisible(true);
	}

	/**
	 * Gets match info from GUI and checks for suitable referees for that match;
	 * calls methods to allocate 2 refs to the match and display information
	 * on the suitable refs in the GUI
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
			//check if week does not already have a match scheduled
			if (!matchList.checkWeekAllocation(week)) {
				errorPane("Week " + week + " is already allocated.");
				return; //if week is already taken, exit method
			}
			boolean senMatch = seniorButton.isSelected(); //check if match is senior or junior
			//after all match info has been checked, get list of suitable refs
			List<Referee> suitableRefs = refereeList.getSuitableRefs(loc, senMatch);
			if (suitableRefs.size() < 2) //if not enough suitable refs found, display message
				displayNoSuitableRefs();
			else // else call method to allocate 2 most suitable refs to match
				allocateTwoRefs(suitableRefs, week, loc, senMatch);
			
			displayAllocatedRefs(suitableRefs);
			clearNameFields();
		}
	}

	/**
	 * gets the two most suitable refs and passes them to a new match object constructor 
	 * along with other match info
	 * @param suitRefs the full list of suitable refs
	 * @param weekNumber week number when the match is on
	 * @param place the location of the match
	 * @param senior True if match requires senior referee
	 */
	private void allocateTwoRefs(List<Referee> suitRefs, int weekNumber, int place, boolean senior) {
		Referee ref1 = suitRefs.get(0); //most suitable ref
		Referee ref2 = suitRefs.get(1); //second most suitable ref

		//TODO now passing Ref's full name to Match constructor - but I still think we should be passing the whole object. maybe.
		String ref1Name = ref1.getFName() + " " + ref1.getLName();
		String ref2Name = ref2.getFName() + " " + ref2.getLName();
		//TODO updateRows
		//create new match
		matchList.alternativeAddMatch(weekNumber, place, senior, ref1Name, ref2Name);
		//increment the number of allocations of the 2 allocated refs
		ref1.incrementAllocs();
		ref2.incrementAllocs();
	}

	/**
	 * Inputs the error message that not enough referees were found into the text area.
	 * Hides the JTable but makes the button to view the table visible
	 */
	private void displayNoSuitableRefs()
	{
		tabbedPane.setSelectedIndex(1);
		centerText.setText("Not enough suitable refs found");
	}

	/**
	 * Inputs the suitable referee list and selected referees into the text area.
	 * Hides the JTable but makes the button to view the table visible
	 * @param suitable
	 */
	private void displayAllocatedRefs(List<Referee> suitable)
	{
		tabbedPane.setSelectedIndex(1);

		String allocated = "The referees allocated to the match are \n"+suitable.get(0).getFName()+" "+suitable.get(0).getLName()+" and "+suitable.get(1).getFName()+" "+suitable.get(1).getLName()
				+"\n\nThe referees which are suitable for the match are: \n";
		StringBuilder display = new StringBuilder(allocated);

		for (Referee aSuitable : suitable) {
			String suitableRef = String.format("%-35s%s%n", aSuitable.getFName() + " " + aSuitable.getLName(), "Number of Allocations: " + aSuitable.getNumAllocs());
			display.append(suitableRef);
		}
		String displayString = display.toString();
		centerText.setText(displayString);
	}

	/**
	 * Retrieves the week number from its respective textfield and ensures it is valid
	 * @return BAD_INFO constant if the week is invalid, week number otherwise
	 */
	private int getWeekInfo() {
		try {
			int week = Integer.parseInt(weekField.getText());
			//I'm assuming that the first week is week 1 and not 0 searchPanel?
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

	/**
	 * checks if the match level has been selected
	 * @return true if it has, false otherwise
	 */
	private boolean levelIsSelected() {
		if (juniorButton.isSelected() || seniorButton.isSelected())
			return true;
		else {
			errorPane("Please select the match level.");
			return false;
		}
	}

	/**
	 * clears the name text fields in the search area of the GUI
	 */
	private void clearNameFields() {
		firstNameField.setText("");
		lastNameField.setText("");
	}

	/**
	 * clears the components for inputting match info
	 */
	private void clearAllocComponents() {
		weekField.setText("");
		locationGroup.clearSelection();
		levelGroup.clearSelection();
	}

	/**
	 * gets ref's names input into search textfields and checks if ref exists;
	 * if so, opens LittleGUI to display info on searched ref
	 */
	private void processSearch() {
		//get ref's first and last name from GUI
		String firstName = firstNameField.getText().trim();
		String lastName = lastNameField.getText().trim();

		if(!firstName.equals("") && !lastName.equals("")) { //check if textfields were empty
			Referee ref = refereeList.findRef(firstName, lastName); //search for ref
			if (ref != null) //ref exists, show Little GUI
				showLittleGui(LittleGUI.SEARCH, ref);
			else { //ref doesn't exist, show error messge
				errorPane("The referee " + firstName + " " + lastName + " " + "was not found in the database.");
				clearNameFields();
			}
		} 
		else {
			errorPane("First Name and Last Name fields cannot be empty.");
		}
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