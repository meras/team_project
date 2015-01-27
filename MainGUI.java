import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

//TODO import .list specifically because otherwise compiler doesn't know if we're using util.List or awt.List
import java.util.List;



/**
* Main GUI which allows the user to enter match details, view information about the referees and search for a referee.
*/ 
public class MainGUI extends JFrame implements ActionListener
{
	private JPanel left, center, right, matchTitlePanel, weekPanel, locationPanel, levelPanel, 
            allocatePanel, barChartPanel, addPanel, searchTitlePanel, firstPanel, lastPanel, searchPanel; // panels which are used to house the components, internal panels aid in layout
	private JLabel matchTitle, weekLabel, locationLabel, levelLabel, firstNameLabel, lastNameLabel; // labels to indicate to the user what they are to enter
	private JTextField weekField, firstNameField, lastNameField;    // the textfields to enter the week in which a match takes place and the name of the ref to be searched for
	private JButton allocateRefButton, barChartButton, addRefButton, searchRefButton;   // the buttons which allow the user to allocate a ref, see the bar chart and add/view a ref
	private JRadioButton northButton, centralButton, southButton, juniorButton, seniorButton;   // the radio buttons to select the match location and level
	private ButtonGroup locationGroup, levelGroup;  // the groups for the radio buttons to ensure that they are mutually exclusive
	private JTable centerTable;     // the JTable which displays the information about the referees
    private JScrollPane centerScroll;   // the scrollpane object which houses the JTable component
	private RefList refereeList;    // a RefList object which contains all the referees that have been entered so far
    private MatchList matchList;    // a MatchList object which contains all the matches that have been entered
    //used in getWeekInfo() below
    private final int BAD_INFO = -1;

    /**
    * Constructs the main GUI window and creates the MatchList and RefList objects
    */
    public MainGUI()
	{
		this.setTitle("Referee Selection");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 260);
        this.setLocationRelativeTo(null);

        refereeList = new RefList();
        FileProcessor.readIn("RefereesIn.txt", refereeList);
        System.out.println(refereeList.getRefList().get(0).getFName());

        matchList = new MatchList();

        this.layoutComponents();
    }
	
    /**
    * Sets out the different GUI components within the JFrame
    */
	public void layoutComponents()
	{
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
        this.add(center,BorderLayout.CENTER);

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
        this.add(right,BorderLayout.EAST);

        // Create the internal JPanel
        addPanel = new JPanel();
		searchTitlePanel = new JPanel(new GridLayout(3,1));
		//searchTitlePanel.setMaximumSize(new Dimension(370,370));

        firstPanel = new JPanel();
        lastPanel = new JPanel();
        searchPanel = new JPanel();

        //Create button for adding new ref 
        addRefButton = new JButton("Add referee");
        addRefButton.addActionListener(this);
        //addPanel.add(addRefButton);
        //this.add(addRefButton, BorderLayout.SOUTH);
        barChartPanel.add(addRefButton);

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
//        right.add(firstPanel);
//        right.add(lastPanel);
//        right.add(searchPanel);
	}
	
    /**
    * Creates the table to display the referees and their information in the center of the main GUI
    */
	private void setCenterTable() {
		
		// Create array of the column names and table model for JTable
		String[] columns = {"ID", "Name", "Qualification", "Allocations", "Home", "North", "Central", "South"};

        //we don't need to specify row number in DefaultTableModel because addRow adds additional rows.
		DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        model.setColumnIdentifiers(columns);
		//Take in the information from the RefList ArrayList and add it to a temporary array
        
		/*quoting from AE3 general feedback: "FitnessProgram is an array class, and its main instance variable was intended to be 
		an array of FitnessClass objects. There should not be an accessor method to return this"
		I think the same probably applies here and we should not have a getRefList() method; we should be using a method to get each
		individual ref
		*/
		for (Referee ref : refereeList.getRefList()) 
		{
			String id = ref.getRefID();
			String name = ref.getFName() + " " + ref.getLName();
			String qualification = ref.getQualification();
			Integer allocations = ref.getNumAllocs();
			String home = ref.getHomeString();
            //TODO note - changed arguments passed below to constants
			Boolean north = ref.getTravelInfo(Referee.NORTH);
			Boolean central = ref.getTravelInfo(Referee.CENTRAL);
			Boolean south = ref.getTravelInfo(Referee.SOUTH);

			Object[] refArray = {id, name, qualification, allocations, home, north, central, south};

			model.addRow(refArray);
		}
		
		// Create JTable, add it to the scrollpane
		centerTable = new JTable(model);
        centerTable.setGridColor(Color.LIGHT_GRAY);
    }

    /**
    * Decides which action will be taken depending on which input the user has given
    * @param e the action event which results from the user pressing one of the buttons 
    */	
	public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() == barChartButton) {
			//BarChartViewer a = new BarChartViewer();
            BarChartViewer b = new BarChartViewer(refereeList);
		}
		if (e.getSource() == addRefButton) {
            showLittleGui(LittleGUI.ADD);
		}
		if (e.getSource() == allocateRefButton) {
            allocateRefs();
		}
        
		if (e.getSource() == searchRefButton) 
        {
            //should we have these checks in a separate method?
		    if (!firstNameField.getText().trim().equals("") && !lastNameField.getText().trim().equals(""))
		    {
                Referee ref = refereeList.findRef(firstNameField.getText().trim(), lastNameField.getText().trim());
                if (ref!=null)
                    showLittleGui(LittleGUI.SEARCH, ref);
                else
                {
            	    JOptionPane.showMessageDialog(this, "The referee " +firstNameField.getText().trim() + " " + lastNameField.getText().trim() + " " + "was not found in the database.", 
					    	"Error", JOptionPane.ERROR_MESSAGE);
            	    clearNameFields();     	
                }
            }
		    else
		    {
			    JOptionPane.showMessageDialog(this, "The First Name and Last Name fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        	    clearNameFields();     	
		    }
        }
	}

    /**
    * Constructs the window where the user can enter information about a referee
    * @param mode sets whether or not the information will be editable as set down in the LittleGUI
    */
    private void showLittleGui(int mode) {
        LittleGUI littleGUI = new LittleGUI(mode);
        littleGUI.setVisible(true);
    }

    /**
    * Constructs the windows where the user can view and edit the inforamtion about a referee
    * @param mode sets whether the information about the referee is editable or not
    * @param ref the referee object which will be displayed and can be edited
    */
    private void showLittleGui(int mode, Referee ref) {
        LittleGUI littleGUI = new LittleGUI(mode, ref);
        littleGUI.setVisible(true);
    }

    /**
    * Displays two referees which are suitable for the match which has been entered if they exist
    */
    private void allocateRefs() {
        int week = getWeekInfo();
        int loc = getLocationInfo();
        boolean senMatch = getSeniorInfo();
        if(week != BAD_INFO) {
            //TODO first check if week is already taken
            List<Referee> suitRefs = refereeList.getSuitableRefs(loc, senMatch);
            if(suitRefs.size() < 2)
                //TODO should we have a JOptionPane here? Or should this be printed on the GUI?
                System.out.println("Not enough suitable refs found");
            else {
                Referee ref1 = suitRefs.get(0);
                Referee ref2 = suitRefs.get(1);
                //getting refIDs to pass to match constructor - but maybe we should pass the whole Ref objects instead?
                String ref1Id = ref1.getRefID();
                String ref2Id = ref2.getRefID();
                Match match = new Match(week, loc, senMatch, ref1Id, ref2Id);
                matchList.addMatch(match);
                ref1.incrementAllocs();
                ref2.incrementAllocs();
                //TODO call method to update center area with list of suitable refs
                //and say which 2 refs have been allocated to the match
            }
        }
    }

    /**
    * Retrieves the week number from its respective textfield and ensures it is valid
    * @exception nfx thrown if the input into the week number field is not an integer
    */
    private int getWeekInfo() {
        try {
            int week = Integer.parseInt(weekField.getText());

            if(week < 0 || week > 52) {
                System.out.println("Please enter a valid match week");
                return BAD_INFO;
            }
            else
                return week;
        }

        catch(NumberFormatException nfx) {
            System.out.println("please enter a valid match week");
            return BAD_INFO;
        }
    }

    /**
    * Retrieves the location of the match and returns it as a constant value which is set down in the Referee class
    * @return Referee.NORTH the final integer 0 which is used as an indicator for ease of comparison
    * @return Referee.CENTRAL the final integer 1 which is used as an indicator
    * @return Referee.SOUTH the final integer 2 which is used as an indicator
    */
    private int getLocationInfo() {
            if(northButton.isSelected()) {
                System.out.println("match is in the north"); 
                return Referee.NORTH;
            }
            else if(centralButton.isSelected())
                return Referee.CENTRAL;
            else
                return Referee.SOUTH;
    }

    /**
    * 
    */
    private boolean getSeniorInfo() {
        return seniorButton.isSelected();
    }

    private void clearNameFields()
    {
    firstNameField.setText("");
    lastNameField.setText("");
    }
}
