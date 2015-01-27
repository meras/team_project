import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

//TODO import .list specifically because otherwise compiler doesn't know if we're using util.List or awt.List
import java.util.List;



/**
* Main GUI 
*/ 
public class MainGUI extends JFrame implements ActionListener
{
	private JPanel left, center, right, matchTitlePanel, weekPanel, locationPanel, 
		levelPanel, allocatePanel, barChartPanel, addPanel, searchTitlePanel, firstPanel, lastPanel, searchPanel;
	private JLabel matchTitle, weekLabel, locationLabel, levelLabel, barChartLabel, 
		addRefLabel, searchRefTitle, firstNameLabel, lastNameLabel;
	private JTextField weekField, firstNameField, lastNameField;
	private JButton allocateRefButton, barChartButton, addRefButton, searchRefButton;
	private JRadioButton northButton, centralButton, southButton, juniorButton, seniorButton;
	private ButtonGroup locationGroup, levelGroup;
	private JTable centerTable;
    private JScrollPane centerScroll;
	private RefList refereeList;
    private MatchList matchList;
    //used in getWeekInfo() below
    private final int BAD_INFO = -1;


    public MainGUI()
	{
		this.setTitle("Referee Selection"); //Provisional title
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 260);
        this.setLocationRelativeTo(null);

        refereeList = new RefList();
        FileProcessor.readIn("RefereesIn.txt", refereeList);
        System.out.println(refereeList.getRefList().get(0).getFName());

        matchList = new MatchList();

        this.layoutComponents();
    }
	
	public void layoutComponents()
	{
		// Create left JPanel
		left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createTitledBorder("Allocate Referees"));
        this.add(left, BorderLayout.WEST);

        //TODO why do we need to setMaximumSize for these panels?
        // Create internal JPanels
        matchTitlePanel = new JPanel();
        // matchTitlePanel.setMaximumSize(new Dimension(400,400));
        weekPanel = new JPanel();
        //weekPanel.setMaximumSize(new Dimension(400,400));
        locationPanel = new JPanel();
        //locationPanel.setMaximumSize(new Dimension(400,400));
        levelPanel = new JPanel();
        //levelPanel.setMaximumSize(new Dimension(400,400));
        allocatePanel = new JPanel();
        //allocatePanel.setMaximumSize(new Dimension(400,400));


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

        //Create label and button for bar chart and add to internal JPanel
       // barChartLabel = new JLabel("View the number of allocations per referee:");
        //barChartPanel.add(barChartLabel);
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
		addPanel.setMaximumSize(new Dimension(370,370));
		searchTitlePanel = new JPanel(new GridLayout(3,1));
		//searchTitlePanel.setMaximumSize(new Dimension(370,370));

        firstPanel = new JPanel();
		firstPanel.setMaximumSize(new Dimension(370,370));
        lastPanel = new JPanel();
		lastPanel.setMaximumSize(new Dimension(370,370));
        searchPanel = new JPanel();
		//searchPanel.setMaximumSize(new Dimension(370,370));

        //Create label and button for adding new ref 
        //addRefLabel = new JLabel("Add a new referee:");
        //addPanel.add(addRefLabel);
        addRefButton = new JButton("Add referee");
        addRefButton.addActionListener(this);
        //addPanel.add(addRefButton);
        //this.add(addRefButton, BorderLayout.SOUTH);
        barChartPanel.add(addRefButton);


        //Create title for search section
        //searchRefTitle = new JLabel("To search for a referee enter their first and last name below");
        //searchTitlePanel.add(searchRefTitle);
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
	
	private void setCenterTable() {
        //TODO referee list is created in the constructor
		//refereeList = new RefList();
		//refereeList.addRefFromGui("Jim", "Bob", "NJB2", 9, "North", "YYN");
		
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
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == addRefButton) {
            showLittleGui(LittleGUI.ADD);
		}
		if (e.getSource() == allocateRefButton) {
            allocateRefs();
		}
		if (e.getSource() == searchRefButton) 
		//should we have these checks in a separate method?
		if (!firstNameField.getText().trim().equals("") && !lastNameField.getText().trim().equals(""))
		{
			
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
        if (e.getSource() == barChartButton) {
			//BarChartViewer a = new BarChartViewer();
            BarChartViewer b = new BarChartViewer(refereeList);
		}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "The First Name and Last Name fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        	clearNameFields();     	
		}
	}

    private void showLittleGui(int mode) {
        LittleGUI littleGUI = new LittleGUI(mode);
        littleGUI.setVisible(true);
    }

    private void showLittleGui(int mode, Referee ref) {
        LittleGUI littleGUI = new LittleGUI(mode, ref);
        littleGUI.setVisible(true);
    }

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

    private boolean getSeniorInfo() {
        return seniorButton.isSelected();
    }

    private void clearNameFields()
    {
    firstNameField.setText("");
    lastNameField.setText("");
    }
}
