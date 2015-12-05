package nullSquad.simulator.gui;

import java.awt.Color;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class SetupDialog implements Serializable
{
	/* Serializable ID */
	private static final long serialVersionUID = 1L;

	private JFormattedTextField numProducersTextField;
	private JFormattedTextField numConsumersTextField;
	private JTextField tagsTextField;
	private JFormattedTextField numSimulationIterationsTextField;

	private boolean loadPreviousState;

	// The maximum number of characters a string can have
	private final static int TAG_MAX_LENGTH = 20;

	/**
	 * Initialize the SetupDialog
	 * 
	 * @param parent The parent of the SetupDialog
	 * @author MVezina
	 */
	public SetupDialog(JFrame parent)
	{
		loadPreviousState = false;

		// Set up a basic integer number formatter (JTextField input can only
		// take in digits)
		NumberFormatter numFormatter = new NumberFormatter(NumberFormat.getInstance());
		numFormatter.setValueClass(Integer.class);
		numFormatter.setMinimum(1);
		numFormatter.setMaximum(Integer.MAX_VALUE);
		numFormatter.setAllowsInvalid(false);

		// Set up all text fields
		this.numConsumersTextField = new JFormattedTextField(numFormatter);
		this.numProducersTextField = new JFormattedTextField(numFormatter);

		this.numSimulationIterationsTextField = new JFormattedTextField(numFormatter);

		// Initialize the tagsTextField
		this.tagsTextField = new JTextField();
		this.tagsTextField.setText("Music, Gaming, Programming, Java, Apple, Microsoft, Google");
		showDialog(parent);
	}

	/**
	 * Returns The total number of simulation iterations to run
	 * 
	 * @return Total Number of Simulation iterations to run
	 * @author MVezina
	 */
	public int getTotalSimulationIterations()
	{
		if (numSimulationIterationsTextField.getText().trim().isEmpty())
			return 0;

		return Integer.parseInt(numSimulationIterationsTextField.getText().replaceAll(",", "").trim());
	}

	/**
	 * @return Number of Producers to be created
	 * @author MVezina
	 */
	public int getNumProducers()
	{
		if (numProducersTextField.getText().trim().isEmpty())
			return 0;
		return Integer.parseInt(numProducersTextField.getText().replaceAll(",", "").trim());
	}

	/**
	 * @return Number of Consumers to be created
	 * @author MVezina
	 */
	public int getNumConsumers()
	{
		if (numConsumersTextField.getText().trim().isEmpty())
			return 0;

		return Integer.parseInt(numConsumersTextField.getText().replaceAll(",", "").trim());
	}

	/**
	 * @return Available tags
	 * @author MVezina
	 */
	public List<String> getTags()
	{
		List<String> list = new ArrayList<>();
		for (String s : tagsTextField.getText().split(","))
		{
			// Truncate the string based on the maximum length of a tag
			list.add(s.trim().substring(0, Math.min(TAG_MAX_LENGTH, s.trim().length())));
		}

		return list;
	}

	/**
	 * Checks to see if the entry field is valid
	 * 
	 * @param entry The Entry field to check
	 * @return Whether or not the field is valid
	 */
	private boolean isEntryValid(JTextField entry)
	{
		// Used to notify which field is incorrect/invalid by setting the field
		// background color to red
		if (entry.getText().isEmpty())
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
	 * @return Boolean stating whether or not the user wants to load a previous
	 *         state
	 * @author MVezina
	 */
	public boolean loadPreviousState()
	{
		return this.loadPreviousState;
	}

	/**
	 * Show the SetupDialog
	 * 
	 * @author MVezina
	 */
	private void showDialog(JFrame parentFrame)
	{
		// Components to be shown on the input dialog
		JComponent[] components = new JComponent[] { new JLabel("Number of Producers: "), numProducersTextField, new JLabel("Number of Consumers: "), numConsumersTextField, new JLabel("Tags (Separated by Commas): "), tagsTextField, new JLabel("Maximum Steps to Run: "), numSimulationIterationsTextField, };

		if (JOptionPane.showConfirmDialog(parentFrame, "Would You Like to Load A Previously Saved Simulation State?", "Load Simulation?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			loadPreviousState = true;
			return;
		}

		// Continue showing the dialog until valid entries are prevalent
		while (true)
		{

			// Show the dialog
			if (JOptionPane.showConfirmDialog(parentFrame, components, "Simulator Setup", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION)
			{
				// Whether or not ALL entries are valid
				boolean success = true;

				// Check to see if the JTextField entries are valid
				success &= isEntryValid(numProducersTextField);
				success &= isEntryValid(numConsumersTextField);
				success &= isEntryValid(numSimulationIterationsTextField);
				success &= isEntryValid(tagsTextField);

				// If there is an invalid field, we want to continue getting
				// input until the input is valid
				if (success)
					break;

				continue;
			}

			// Exit if any other button is clicked (other than OK)
			System.exit(0);
		}
	}

}
