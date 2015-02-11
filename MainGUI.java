import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main GUI which allows the user to enter match details, view information about the referees and search for a referee.
 */
public class MainGUI extends JFrame implements ActionListener {
	private final int INVALID_INFO = -1;
	private JButton allocateRefButton, barChartButton, addRefButton, searchRefButton, saveExitButton;  // the buttons which allow the user to allocate a ref, see the bar chart, add/view a ref and save and exit
	private JRadioButton northButton, centralButton, southButton, juniorButton, seniorButton;   // the radio buttons to select the match location and level
	private ButtonGroup locationGroup, levelGroup;  // the groups for the radio buttons to ensure that they are mutually exclusive
	private JTextField weekField, firstNameField, lastNameField;    // the textfields to enter the week in which a match takes place and the name of the ref to be searched for
	private JTextArea centerText; // text area to display the referees which have been allocated to a match or displays an error message
	private DefaultTableModel model;    // the model to set the features of the JTable
	private JTable centerTable;     // the JTable which displays the information about the referees
	private final JTabbedPane tabbedPane = new JTabbedPane();   // the tabbed pane which holds the table and the text area to display the allocated referees
	private final RefList refereeList;    // a RefList object which contains all the referees that have been entered so far
	private final MatchList matchList;    // a MatchList object which contains all the matches that have been entered
	private final String matchAllocsFile = "MatchAllocs.txt";
	private final String refsOutFile = "RefereesOut.txt";

	/**
	 * Constructs the main GUI window and creates the MatchList and RefList objects
	 */
	public MainGUI() {
		this.setTitle("Javaball Referee Selection");
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
		JPanel allocRefsPanel = new JPanel();
		allocRefsPanel.setLayout(new BoxLayout(allocRefsPanel, BoxLayout.Y_AXIS));
		allocRefsPanel.setBorder(BorderFactory.createTitledBorder("Allocate Referees"));

		// Create internal JPanels for each of the components
		JPanel weekPanel = new JPanel();
		JPanel locationPanel = new JPanel();
		JPanel levelPanel = new JPanel();
		JPanel allocateButtonPanel = new JPanel();

		// Create label and textField for match week number
		weekPanel.add(new JLabel("Week Number (1-52):"));
		weekField = new JTextField(2);
		weekPanel.add(weekField);

		//Create label and radio buttons for match location
		locationPanel.add(new JLabel("Match Location:"));
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


		// Create label and radio buttons for level
		levelPanel.add(new JLabel("Level:"));
		juniorButton = new JRadioButton("Junior");
		seniorButton = new JRadioButton("Senior");

		// Group the level JRadioButtons so they are mutually exclusive
		levelGroup = new ButtonGroup();
		levelGroup.add(juniorButton);
		levelGroup.add(seniorButton);
		levelPanel.add(juniorButton);
		levelPanel.add(seniorButton);

		//Create label and button for finding suitable referee
		allocateRefButton = new JButton("Allocate");
		allocateRefButton.addActionListener(this);
		allocateButtonPanel.add(allocateRefButton);

		// Add internal panels to the allocRefsPanel JPanel
		allocRefsPanel.add(weekPanel);
		allocRefsPanel.add(locationPanel);
		allocRefsPanel.add(levelPanel);
		allocRefsPanel.add(allocateButtonPanel);

		// Create searchPanel JPanel which will contain the search referee components
		JPanel searchPanel = new JPanel(new GridLayout(3, 1));
		searchPanel.setBorder(BorderFactory.createTitledBorder("Search for Referee"));

		// Create the internal JPanels for each of the components
		JPanel firstNamePanel = new JPanel();
		JPanel lastNamePanel = new JPanel();
		JPanel searchButtonPanel = new JPanel();

		//Create label and button for first name
		firstNamePanel.add(new JLabel("First Name:"));
		firstNameField = new JTextField(10);
		firstNamePanel.add(firstNameField);

		// Create label and button for last name
		lastNamePanel.add(new JLabel("Last Name:"));
		lastNameField = new JTextField(10);
		lastNamePanel.add(lastNameField);

		// Create the button for searching for the referee
		searchRefButton = new JButton("Search");
		searchRefButton.addActionListener(this);
		searchButtonPanel.add(searchRefButton);

		// Add the internal panels to searchPanel JPanel
		searchPanel.add(firstNamePanel);
		searchPanel.add(lastNamePanel);
		searchPanel.add(searchButtonPanel);

		//Create the topSections JPanel which will contain both the allocRefsPanel and searchPanel JPanel so that they sit side by side
		JPanel topSections = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		topSections.add(allocRefsPanel, c);
		topSections.add(searchPanel, c);

		// Use the setCenterTable method to populate the table and add it to the scrollpane
		//TODO Exception in thread "main" java.lang.NumberFormatException: For input string: "North"
		setCenterTable();
		JScrollPane tableScroll = new JScrollPane(centerTable);
		centerTable.setFillsViewportHeight(true);

		// Create the text field which can be used to display information about the allocated referees
		centerText = new JTextArea();
		centerText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		centerText.setEditable(false);
		JScrollPane textScroll = new JScrollPane(centerText);

		// add the table and text area to the CardLayout handler
		tabbedPane.addTab("All Referees", tableScroll);
		tabbedPane.addTab("Allocated Referees", textScroll);

		// Create the centerLayout GUI which will contain the main sections and the table
		JPanel centerLayout = new JPanel(new GridLayout(2, 1));
		centerLayout.add(topSections);
		centerLayout.add(tabbedPane);
		this.add(centerLayout, BorderLayout.CENTER);

		// Create bottomButtons JPanel
		JPanel bottomButtons = new JPanel();
		this.add(bottomButtons, BorderLayout.SOUTH);

		//Create button for bar chart and add to internal JPanel
		barChartButton = new JButton("View allocations charts");
		barChartButton.addActionListener(this);
		bottomButtons.add(barChartButton);

		//Create button for adding new ref
		addRefButton = new JButton("Add referee");
		addRefButton.addActionListener(this);
		bottomButtons.add(addRefButton);
		
		//Create button for saving and exiting
		saveExitButton = new JButton("Save and Exit");
		saveExitButton.addActionListener(this);
		bottomButtons.add(saveExitButton);
	}

	/**
	* Decides which action will be taken depending on which input the user has given
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
		if(e.getSource() == saveExitButton) {
			processSaveExit();
		}
	}

	/**
	* Create the model for the JTable, ensuring it is non editable and the data is displayed correctly
	*/
	private void setCenterTable() {
		final Object[] columnNames = {"ID", "Name", "Qualification", "Allocations", "Home", "North", "Central", "South"}; // the names for each of the columns in the JTable
		model = new DefaultTableModel(columnNames, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
			public Class<?> getColumnClass(int colIndex) {
				return getValueAt(0, colIndex).getClass();
			}

		};
		populateTable();
	}

	/**
	* Add the information for each of the referees into a seperate row
	*/
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
		// Create JTable and add it to the scroll pane
		centerTable = new JTable(model);
		centerTable.setGridColor(Color.LIGHT_GRAY);
	}

	/**
	* Populates the table with the updated referee information
	*/
	public void updateTable()
	{
		model.setRowCount(0);
		populateTable();
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
	 * Retrieves the referees names from the textfields and checks if the referee exists;
	 * if so, opens LittleGUI to display info on searched ref
	 */
	private void processSearch() {
		//get ref's first and last name from GUI
		String firstName = firstNameField.getText().trim();
		String lastName = lastNameField.getText().trim();

		if(!firstName.isEmpty() && !lastName.isEmpty()) {
			Referee ref = refereeList.findRef(firstName, lastName);
			if (ref != null)
				showLittleGui(LittleGUI.SEARCH, ref);
			else {
				errorPane("The referee " + firstName + " " + lastName + " " + "was not found in the database.");
				clearNameFields();
			}
		} 
		else {
			errorPane("First Name and Last Name fields cannot be empty.");
		}
	}

	/**
	 * Gets match info from GUI and checks for suitable referees for that match;
	 * calls methods to allocate 2 refs to the match and display information
	 * on the suitable refs in the GUI
	 */
	private void checkForSuitableRefs() {
		// First make sure there is room for another match
		if (matchList.getNoMatches() == 52) {
			errorPane("All the weeks in the year are allocated.");
			return; //if no room for more matches, exit method
		}

		// If there is room for another match, get match info input by user
		int week = getValidWeekNum(weekField.getText());
		int loc = getLocationInfo();
		//check that all info has been input and is OK
		if (isLevelSelected() && week != INVALID_INFO && loc != INVALID_INFO) {
			// Check if week does not already have a match scheduled
			if (!matchList.checkWeekAllocation(week)) {
				errorPane("Week " + week + " is already allocated.");
				return; // If week is already taken, exit method
			}
			boolean senMatch = seniorButton.isSelected(); // Check if match is senior or junior
			// After all match info has been checked, get list of suitable refs
			List<Referee> suitableRefs = refereeList.getSuitableRefs(loc, senMatch);
			if (suitableRefs.size() < 2) { // If not enough suitable refs found, display message
				displayNoSuitableRefs();
			} 
            // calls method to allocate 2 most suitable refs to match
            else {
				allocateTwoRefs(suitableRefs, week, loc, senMatch);
            	displayAllocatedRefs(suitableRefs);
			}
		}
	}

	/**
	 * Retrieves the two most suitable refs and passes them as a parameter when creating the match object
	 * along with other match info
	 * @param suitRefs the full list of suitable refs
	 * @param weekNumber week number when the match is on
	 * @param place the location of the match
	 * @param senior True if match requires senior referee
	 */
	private void allocateTwoRefs(List<Referee> suitRefs, int weekNumber, int place, boolean senior) {
		Referee ref1 = suitRefs.get(0); // Most suitable ref
		Referee ref2 = suitRefs.get(1); // Second most suitable ref

		String ref1Name = ref1.getFName() + " " + ref1.getLName();
		String ref2Name = ref2.getFName() + " " + ref2.getLName();
		// Create new match
		matchList.alternativeAddMatch(weekNumber, place, senior, ref1Name, ref2Name);
		// Increment the number of allocations of the 2 allocated refs
		ref1.incrementAllocs();
		ref2.incrementAllocs();
	}

	/**
	 * Retrieves the week number from its respective textfield and ensures it is valid
	 * @return INVALID_INFO constant if the week is invalid, week number otherwise
	 */
	private int getValidWeekNum(String weekNum) {
		int week = INVALID_INFO;

		try {
			week = Integer.parseInt(weekNum);
			if (week < 1 || week > MatchList.MAX_MATCHES) {
				week = INVALID_INFO;
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfx) {
			errorPane("Please enter a week number between 1 and " + MatchList.MAX_MATCHES);
		}
		return week;
	}

	/**
	 * Retrieves the location of the match and returns it as a constant value which is set down in the Referee class
	 * @return the final int which is used as an indicator of the location
	 */
	private int getLocationInfo() {
		if (northButton.isSelected()) {
			return Referee.NORTH;
		} else if (centralButton.isSelected()) {
			return Referee.CENTRAL;
		} else if (southButton.isSelected()) {
			return Referee.SOUTH;
		} else {
			errorPane("Please select the match location.");
			return INVALID_INFO;
		}
	}

	/**
	 * Checks if the match level has been selected
	 * @return true if it has, false otherwise
	 */
	private boolean isLevelSelected() {
		if (juniorButton.isSelected() || seniorButton.isSelected())
			return true;
		else {
			errorPane("Please select the match level.");
			return false;
		}
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
	private void displayAllocatedRefs(List<Referee> suitableRefs) {
		// switch to the text area in the second tab
		tabbedPane.setSelectedIndex(1);

		StringBuilder display = new StringBuilder();
		display.append("The referees allocated to the match are: \n")
				.append(suitableRefs.get(0).getFName() + " ")
				.append(suitableRefs.get(0).getLName())
				.append(" and ")
				.append(suitableRefs.get(1).getFName() + " ")
				.append(suitableRefs.get(1).getLName())
				.append("\n\nThe referees which are suitable for the match are: \n");

		for (Referee aSuitableRef : suitableRefs) {
			display.append(String.format("%-15s%-15s%s %-4s%n",
										aSuitableRef.getFName(),
										aSuitableRef.getLName(),
										"Allocations:",
										aSuitableRef.getNumAllocs()));
		}

		centerText.setText(display.toString());
		centerText.setCaretPosition(0);
	}

	/**
	 * Clears the components for inputting match info
	 */
	private void clearAllocComponents() {
		weekField.setText("");
		locationGroup.clearSelection();
		levelGroup.clearSelection();
	}

	/**
	 * Clears the name text fields in the search area of the GUI
	 */
	private void clearNameFields() {
		firstNameField.setText("");
		lastNameField.setText("");
	}
	
	private void processSaveExit() {
		String matchAllocsText = matchList.getMatchAllocsText();
		String refReport = refereeList.getRefsOutText();
		boolean matchFileMade = FileProcessor.writeFileOut(matchAllocsFile, matchAllocsText);
		boolean refFileMade = FileProcessor.writeFileOut(refsOutFile, refReport);
		//if IO operations were successful, exit program
		if(matchFileMade && refFileMade)
			System.exit(0);
	}

	/**
	 * Creates a JOption pane with a custom error message
	 *
	 * @param errorMessage Message to display on the JOptionPane
	 */
	public void errorPane(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	
}
