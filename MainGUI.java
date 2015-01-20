import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
* Main GUI 
*/ 
public class MainGUI extends JFrame implements ActionListener
{
	private JPanel left, center, right;
	private JLabel matchTitle, addRefTitle, searchRefTitle, 
		weekLabel, locationLabel, levelLabel, findRefLabel, firstNameLabel,
		lastNameLabel, addRefLabel;
	private JTextField weekField, firstNameField, lastNameField;
	private JButton findRefButton, searchRefButton, addRefButton;
	private JRadioButton northButton, centralButton, southButton, juniorButton, seniorButton;
	private ButtonGroup locationGroup, levelGroup;
    private JScrollPane centerScroll;

	public MainGUI()
	{
		this.setTitle("Referee Selection"); //Provisional title
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	public void layoutComponents()
	{
		// Create JPanels
		left = new JPanel();
		center = new JPanel();
		right = new JPanel();
		
        // Create label for title/instructions for left panel
      


		// Create label and textField for match week number
		weekLabel = new JLabel("Week Number (1-52)");
		left.add(weekLabel);
		weekField = new JTextField(2);
		left.add(weekField);
		
		//Create Label and radio buttons for match location
		locationLabel = new JLabel("Match Location");
		left.add(locationLabel);
		
		northButton = new JRadioButton("North");
		//Code for when North location is selected
		
		centralButton = new JRadioButton("Central");
		//Code for when Central location is selected
		
		southButton = new JRadioButton("South");
		//Code for when South Location is selected
		
		//Group the location JRadioButtons so that they are mutually exclusive
		locationGroup = new ButtonGroup();
		locationGroup.add(northButton);
		locationGroup.add(centralButton);
		locationGroup.add(southButton);
        left.add(locationGroup);
		//This will need to be altered so that they display horizontally

        // Create label and radio buttons for level        
        levelLabel = new JLabel("Level");
        left.add(levelLabel);
        
        juniorButton = new JRadioButton("Junior");  
        // Code for when junior level is selected

        seniorButton = new JRadioButton("Senior");
        // Code for when senior is seleted

        // Group the level JRadioButtons so they are mutually exclusive
        levelGroup = new ButtonGroup();
        levelGroup.add(juniorButton);
        levelGroup.add(seniorButton);
        left.add(levelGroup);

        //Create JTable to display list of referees
        
        //Create JScrollPane and add to center panel
        centerScroll = new JScrollPane();
        // Add JTable component to scroll pane
        center.add(centerScroll);

        
	}
	
	public void actionPerformed(ActionEvent e) {
		//
	}
	
}
