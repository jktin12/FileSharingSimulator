/**
 * 
 */
package nullSquad.simulator.gui;

import java.awt.*;
import javax.swing.*;
import nullSquad.filesharingsystem.*;

import nullSquad.filesharingsystem.users.*;
import nullSquad.simulator.Simulator;
import nullSquad.filesharingsystem.document.*;

/**
 * @author MVezina
 *
 */
@SuppressWarnings("serial")
public class SimulatorGUI extends JFrame {

	// The log text
	private static String logText = "Welcome to the Simulator!\n\n";

	// Main Frame Tab Pane
	private JTabbedPane tabbedMenuPane;
	private JPanel mainTabPanel;

	/* 'Documents' tab content */
	private DocumentsPanel documentsPanel;

	/* 'Simulator' tab content */
	private SimulatorPanel simulatorPanel;

	/* 'Users' tab content */
	private UsersPanel usersPanel;

	/* Simulator Controls */
	private JButton stepSimulatorButton;
	private JButton runSimulatorButton;
	private JPanel simulatorControlsPanel;

	/* Simulator */
	private Simulator simulator;

	/**
	 * Appends text t to log text
	 * @param t The text to be appended
	 */
	public static void appendLog(String t) {
		logText += t + "\n";
	}

	/**
	 * Constructor for creating a simulator GUI. Initializes all values and
	 * components
	 * 
	 * @param frameTitle
	 *            The title of the GUI frame
	 */
	public SimulatorGUI(String frameTitle) {
		
		// Create Frame with specified frame title
		super(frameTitle);

		// Run the setup dialog
		SetupDialog sD = new SetupDialog(this);

		// Set the values from the setup dialog
		int numProducers = sD.getNumProducers();
		int numConsumers = sD.getNumConsumers();

		// Initialize the Simulator
		simulator = new Simulator(new FileSharingSystem(sD.getTags()), sD.getTotalSimulationIterations());

		/* Set & Initialize Frame Properties / Components */

		this.setTitle("Simulator (" + simulator.getCurrentSimulatorSequence() + "/"
				+ simulator.getTotalSimulatorSequences() + ")");

		// Set the frame layout
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Set the minimum and preferred sizes
		this.setMinimumSize(new Dimension(680, 400));
		this.setPreferredSize(this.getMinimumSize());

		// Center the frame on the screen
		this.setLocationRelativeTo(null);

		// Exit the program on close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Now we want to add any components to the GUI
		InitializeFrameComponents();

		// Generate the producers and consumers
		simulator.createConsumers(numConsumers);
		simulator.createProducers(numProducers);

		// Pack the initialized components (Adjust sizes)
		this.pack();

		// Set frame to be visible
		this.setVisible(true);

	}

	/**
	 * This method will be used to set up any Frame components
	 * 
	 * @author MVezina
	 */
	private void InitializeFrameComponents() {

		mainTabPanel = new JPanel(new CardLayout());

		// Create the main frame tabbed menu pane
		tabbedMenuPane = new JTabbedPane();

		// Create all of the tab panels
		simulatorPanel = new SimulatorPanel();

		// Obtain the users list model from the network
		DefaultListModel<User> allUsersListModel = simulator.getNetwork().getUsersListModel();

		// Create the users panel
		this.usersPanel = new UsersPanel(allUsersListModel);
	
		// Obtain the documents list model from the network
		DefaultListModel<Document> allDocumentsListModel = simulator.getNetwork().getDocumentsListModel();
	
		// Create the documents panel
		this.documentsPanel = new DocumentsPanel(allDocumentsListModel);

		// Add tab panels to the tab pane
		tabbedMenuPane.addTab("Simulator", simulatorPanel);
		tabbedMenuPane.addTab("Documents", documentsPanel);
		tabbedMenuPane.addTab("Users", usersPanel);

		// Add the tab pane to the JFrame
		mainTabPanel.add(tabbedMenuPane);

		// stepSimulator JButton
		stepSimulatorButton = new JButton("Step Simulator");
		stepSimulatorButton.addActionListener(click -> stepSimulator_Click());
		stepSimulatorButton.setSize(new Dimension(90, 20));

		// runSimulator JButton
		runSimulatorButton = new JButton("Run Simulator");
		runSimulatorButton.addActionListener(click -> runSimulator_Click());
		runSimulatorButton.setSize(new Dimension(90, 20));

		// Create a panel solely for simulator controls
		simulatorControlsPanel = new JPanel();

		// Add all controls to the panel
		simulatorControlsPanel.add(stepSimulatorButton);
		simulatorControlsPanel.add(runSimulatorButton);
		simulatorControlsPanel.setBorder(BorderFactory.createTitledBorder("Simulator Controls"));

		this.add(mainTabPanel);
		this.add(simulatorControlsPanel);
	}
	



	/**
	 * Event Method: Called when the stepSimulator Button is clicked This will
	 * run the simulator until the end
	 * 
	 * @author MVezina
	 */
	void runSimulator_Click() {

		while (simulator.getTotalSimulatorSequences() - simulator.getCurrentSimulatorSequence() > 0) {
			stepSimulator_Click();
		}
	}

	/**
	 * Event Method: Called when the stepSimulator Button is clicked
	 * 
	 * @author MVezina
	 */
	void stepSimulator_Click() {
		simulator.simulationStep();

		// Set the title
		this.setTitle("Simulator (" + simulator.getCurrentSimulatorSequence() + "/"
				+ simulator.getTotalSimulatorSequences() + ")");

		// Set the log text box
		simulatorPanel.setLogText(logText);

		// Repaint the frame
		this.repaint();

		if (simulator.getCurrentSimulatorSequence() == simulator.getTotalSimulatorSequences()) {
			stepSimulatorButton.setEnabled(false);
			runSimulatorButton.setEnabled(false);
			
			JOptionPane.showMessageDialog(null, "The simulator has finished!", "Simulation Complete!", JOptionPane.INFORMATION_MESSAGE);
			
		}

	}

	public static void main(String[] args) {
		new SimulatorGUI("Simulator");
	}

}
