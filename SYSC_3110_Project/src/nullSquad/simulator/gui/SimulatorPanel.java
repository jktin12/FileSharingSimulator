package nullSquad.simulator.gui;

import java.awt.Dimension;

import javax.swing.*;

/**
 * Representation of the Simulator Tab Panel
 * 
 * @author MVezina
 */
public class SimulatorPanel extends JPanel
{

	private JTextArea mainSimulatorTextArea;

	/**
	 * Creates the simulator panel and all associated components
	 * 
	 * @author MVezina
	 */
	public SimulatorPanel()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Create a scrollable JTextArea pane
		mainSimulatorTextArea = new JTextArea("Welcome to the Simulator!\n");
		mainSimulatorTextArea.setEditable(false);

		this.add(new JScrollPane(mainSimulatorTextArea));
	}

	/**
	 * Sets the Log Text Area Text
	 * 
	 * @param logText
	 */
	public void setLogText(String logText)
	{
		mainSimulatorTextArea.setText(logText);
	}

}
