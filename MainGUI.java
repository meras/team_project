import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
* Main GUI 
*/ 
public class MainGUI extends JFrame implements ActionListener
{
	private JPanel left, center, right, titlePanel, weekPanel, locationPanel, levelPanel, firstPanel, lastPanel;
	private JLabel matchTitle, searchRefTitle, 
		weekLabel, locationLabel, levelLabel, allocateRefLabel, firstNameLabel,
		lastNameLabel, addRefLabel;
	private JTextField weekField, firstNameField, lastNameField;
	private JButton allocateRefButton, searchRefButton, addRefButton, barChartButton;
	private JRadioButton northButton, centralButton, southButton, juniorButton, seniorButton;
	private ButtonGroup locationGroup, levelGroup;
    private JScrollPane centerScroll;

	public MainGUI()
	{
		this.setTitle("Referee Selection"); //Provisional title
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
        this.setSize(1200,250);
        this.setLocationRelativeTo(null);
	}
	
	public void layoutComponents()
	{
		// Create left JPanel
		left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setMaximumSize(new Dimension(450,450));
        this.add(left,BorderLayout.WEST);
		
        // Create internal JPanels 
        titlePanel = new JPanel();
        titlePanel.setMaximumSize(new Dimension(400,400));
        weekPanel = new JPanel();
        weekPanel.setMaximumSize(new Dimension(400,400));
        locationPanel = new JPanel();
        locationPanel.setMaximumSize(new Dimension(400,400));
        levelPanel = new JPanel();
        levelPanel.setMaximumSize(new Dimension(400,400));;

        // Create label for title/instructions for left panel
        matchTitle = new JLabel("To find a suitable referee enter the match details below");
        titlePanel.add(matchTitle);

		// Create label and textField for match week number
		weekLabel = new JLabel("Week Number (1-52)");
		weekPanel.add(weekLabel);
		weekField = new JTextField(2);
		weekPanel.add(weekField);
		
		//Create Label and radio buttons for match location
		locationLabel = new JLabel("Match Location");
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
		//TODO altered so that they display horizontally

        // Create label and radio buttons for level        
        levelLabel = new JLabel("Level");
        levelPanel.add(levelLabel);
        juniorButton = new JRadioButton("Junior");  
        seniorButton = new JRadioButton("Senior");

        // Group the level JRadioButtons so they are mutually exclusive
        levelGroup = new ButtonGroup();
        levelGroup.add(juniorButton);
        levelGroup.add(seniorButton);
        levelPanel.add(juniorButton);
        levelPanel.add(seniorButton);
        //TODO altered so that they display horizontally

        // Add internal panels to the left JPanel
        left.add(titlePanel);
        left.add(weekPanel);
        left.add(locationPanel);
        left.add(levelPanel);

        //Create label and button for finding suitable referee
        allocateRefLabel = new JLabel("Find a suitable referee");
        left.add(allocateRefLabel);
        allocateRefButton = new JButton("Find");
        allocateRefButton.addActionListener(this);
        left.add(allocateRefButton);

        // Create center JPanel
        center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        this.add(center,BorderLayout.CENTER);

        //Create JTable to display list of referees
        
        //Create JScrollPane and add to center panel
        centerScroll = new JScrollPane();
        // Add JTable component to scroll pane
        center.add(centerScroll);




        // Create right JPanel
		right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
        right.setMaximumSize(new Dimension(400,400));
        this.add(right,BorderLayout.EAST);

        // Create the internal JPanel
        firstPanel = new JPanel();
        lastPanel = new JPanel();

        //Create label and button for adding new ref 
        addRefLabel = new JLabel("Add a new referee");
        right.add(addRefLabel);
        addRefButton = new JButton("Add");
        addRefButton.addActionListener(this);
        right.add(addRefButton);
        
        //Create title for search section
        searchRefTitle = new JLabel("To search for a referee enter their first and last name below");
        right.add(searchRefTitle);

        //Create label and button for first name
        firstNameLabel = new JLabel("First Name");
        firstPanel.add(firstNameLabel);
        firstNameField = new JTextField(10);
        firstPanel.add(firstNameField);

        // Create label and button for last name
        lastNameLabel = new JLabel("Last Name");
        lastPanel.add(lastNameLabel);
        lastNameField = new JTextField(10);
        lastPanel.add(lastNameField);

        // Add the internal panels to right JPanel
        right.add(firstPanel);
        right.add(lastPanel);

        // Create the button for searching for the referee
        searchRefButton = new JButton("Search");
        searchRefButton.addActionListener(this);
        right.add(searchRefButton);

	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addRefButton) {

		}
		if (e.getSource() == allocateRefButton) {

		}
		if (e.getSource() == searchRefButton) {

		}
		if (e.getSource() == barChartButton) {

		}
	}
	
}
