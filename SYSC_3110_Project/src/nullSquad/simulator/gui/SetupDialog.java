package nullSquad.simulator.gui;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class SetupDialog{

	private JFormattedTextField numProducersTextField;
	private JFormattedTextField numConsumersTextField;
	private JTextField tagsTextField;
	private JFormattedTextField numSimulationIterationsTextField;
	
	/**
	 * Initialize the SetupDialog
	 * @param parent The parent of the SetupDialog
	 * @author MVezina
	 */
	public SetupDialog(JFrame parent)
	{
		// Set up a basic integer number formatter (JTextField input can only take in digits)
		NumberFormatter numFormatter = new NumberFormatter(NumberFormat.getInstance());
		numFormatter.setValueClass(Integer.class);
		numFormatter.setMinimum(0);
		numFormatter.setMaximum(Integer.MAX_VALUE);
		numFormatter.setAllowsInvalid(false);
		
		
		// Set up all text fields
		this.numConsumersTextField = new JFormattedTextField(numFormatter);
		this.numProducersTextField = new JFormattedTextField(numFormatter);		
		
		numFormatter.setMinimum(1);
		this.numSimulationIterationsTextField = new JFormattedTextField(numFormatter);
		
		// Initialize the tagsTextField
		this.tagsTextField = new JTextField();
		this.tagsTextField.setText("Music, Gaming, Programming, Java, Apple, Microsoft, Google");
		showDialog(parent);
	}
	

	
	/**
	 * Returns The total number of simulation iterations to run
	 * @return Total Number of Simulation iterations to run
	 * @author MVezina
	 */
	public int getTotalSimulationIterations()
	{	
		return Integer.parseInt(numSimulationIterationsTextField.getText().replaceAll(",", "").trim());
	}
	
	/**
	 * @return Number of Producers to be created
	 * @author MVezina
	 */
	public int getNumProducers()
	{	
		return Integer.parseInt(numProducersTextField.getText().replaceAll(",", "").trim());
	}
	
	/**
	 * @return Number of Consumers to be created
	 * @author MVezina
	 */
	public int getNumConsumers()
	{	
		return Integer.parseInt(numConsumersTextField.getText().replaceAll(",", "").trim());
	}
	
	/**
	 * @return Available tags
	 * @author MVezina
	 */
	public List<String> getTags()
	{
		List<String> list = new ArrayList<>();
		for(String s : tagsTextField.getText().split(","))
		{
			list.add(s.trim());
		}
		
		
		return list;
	}
	
	private boolean isEntryValid(JTextField entry)
	{
		// Used to notify which field is incorrect/invalid by setting the field background color to red
		if(entry.getText().isEmpty())
		{
			entry.setBackground(Color.RED);
			return false;
		}
		else
		{
			// Reset the background to white if the entry is valid
			entry.setBackground(Color.WHITE);
			return true;
		}
	}
	
	/**
	 * Show the SetupDialog
	 * @author MVezina
	 */
	private void showDialog(JFrame parentFrame)
	{
		// Components to be shown on the input dialog
		JComponent[] components = new JComponent[] {
			new JLabel("Number of Producers: "),
			numProducersTextField,
			new JLabel("Number of Consumers: "),
			numConsumersTextField,
			new JLabel("Tags (Seperated by Commas): "),
			tagsTextField,
			new JLabel("Maximum Steps to Run: "),
			numSimulationIterationsTextField,
		};
		
		// Continue showing the dialog until valid entries are prevalent
		while(true)
		{		
			// Show the dialog
			if( JOptionPane.showConfirmDialog(parentFrame, components, "Simulator Setup", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION)
			{
				System.exit(0);
			}
			else
			{
				// Whether or not ALL entries are valid
				boolean success = true;
				
				// Check to see if the JTextField entries are valid
				success &= isEntryValid(numProducersTextField);
				success &= isEntryValid(numConsumersTextField);
				success &= isEntryValid(numSimulationIterationsTextField);
				success &= isEntryValid(tagsTextField);
				
				
				// If there is an invalid field, we want to continue getting input until the input is valid
				if(success)
					break;
				
				
			}
		}
	}
	
}
