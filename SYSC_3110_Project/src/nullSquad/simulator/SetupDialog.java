package nullSquad.simulator;

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
	private JFormattedTextField simSeedTextField;
	
	
	public SetupDialog(JFrame parent)
	{
		// Set up a basic integer number formatter (JTextField input can only take in digits)
		NumberFormatter numFormatter = new NumberFormatter(NumberFormat.getInstance());
		numFormatter.setValueClass(Integer.class);
		numFormatter.setMinimum(0);
		numFormatter.setMaximum(Integer.MAX_VALUE);
		numFormatter.setCommitsOnValidEdit(true);
		numFormatter.setAllowsInvalid(false);
		
		
		// Set up all text fields
		this.numConsumersTextField = new JFormattedTextField(numFormatter);
		this.numProducersTextField = new JFormattedTextField(numFormatter);
		
		numFormatter.setMinimum(1);
		this.numSimulationIterationsTextField = new JFormattedTextField(numFormatter);
		this.simSeedTextField = new JFormattedTextField(numFormatter);
		
		
		this.tagsTextField = new JFormattedTextField();
		
		showDialog(parent);
	}
	
	public int getSimulationSeed()
	{	
		return Integer.parseInt(simSeedTextField.getText().replaceAll(",", "").trim());
	}
	
	public int getTotalSimulationIterations()
	{	
		return Integer.parseInt(numSimulationIterationsTextField.getText().replaceAll(",", "").trim());
	}
	
	
	public int getNumProducers()
	{	
		return Integer.parseInt(numProducersTextField.getText().replaceAll(",", "").trim());
	}
	public int getNumConsumers()
	{	
		return Integer.parseInt(numConsumersTextField.getText().replaceAll(",", "").trim());
	}
	public List<String> getTags()
	{
		List<String> list = new ArrayList<>();
		for(String s : tagsTextField.getText().split(","))
		{
			list.add(s.trim());
		}
		
		
		return list;
	}
	
	public void showDialog(JFrame parentFrame)
	{
		JComponent[] components = new JComponent[] {
			new JLabel("Number of Producers: "),
			numProducersTextField,
			new JLabel("Number of Consumers: "),
			numConsumersTextField,
			new JLabel("Tags (Seperated by Commas): "),
			tagsTextField,
			new JLabel("Number of Simulations to Run: "),
			numSimulationIterationsTextField,
			new JLabel("Simulator Seed: "),
			simSeedTextField,
		};
		
		
		while(true)
		{		
			if( JOptionPane.showConfirmDialog(parentFrame, components, "Simulator Setup", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION)
			{
				System.exit(0);
			}
			else
			{
				
				boolean success = true;
				
				// Used to notify which field is incorrect/invalid by setting the field to red
				if(numProducersTextField.getText().isEmpty())
				{
					numProducersTextField.setBackground(Color.RED);
					success = false;
				}
				else
				{
					numProducersTextField.setBackground(Color.WHITE);
				}
				
				if( numConsumersTextField.getText().isEmpty())
				{
					numConsumersTextField.setBackground(Color.RED);
					success = false;
				}
				else
				{
					numConsumersTextField.setBackground(Color.WHITE);
				}
				
				if( numSimulationIterationsTextField.getText().isEmpty())
				{
					numSimulationIterationsTextField.setBackground(Color.RED);
					success = false;
					
				}
				else
				{
					numSimulationIterationsTextField.setBackground(Color.WHITE);
				}
				
				if( tagsTextField.getText().trim().isEmpty())
				{
					tagsTextField.setBackground(Color.RED);
					success = false;
					
				}
				else
				{
					tagsTextField.setBackground(Color.WHITE);
				}
				
				if( simSeedTextField.getText().isEmpty())
				{
					simSeedTextField.setBackground(Color.RED);
					success = false;
					
				}
				else
				{
					simSeedTextField.setBackground(Color.WHITE);
				}
				
				// If there is an invalid field, we want to continue getting input until the input is valid
				if(!success)
				{
					continue;
				}
				
				break;
			}
		}
	}
	
}
