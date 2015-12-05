/**
 * 
 */
package nullSquad.simulator.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nullSquad.filesharingsystem.*;

import nullSquad.filesharingsystem.users.*;
import nullSquad.simulator.Simulator;
import nullSquad.filesharingsystem.document.*;

/**
 * @author MVezina
 */
public class SimulatorGUI extends JFrame
{

	/* Serializable ID */
	private static final long serialVersionUID = 1L;

	private static final String SSF_FILE_EXT = ".ssf";

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
	private JButton restartSimulationButton;
	private JPanel simulatorControlsPanel;

	private JFileChooser fileChooser;

	/* Simulator */
	private Simulator simulator;

	/**
	 * Constructor for creating a simulator GUI. Initializes all values and
	 * components
	 * 
	 * @param frameTitle The title of the GUI frame
	 */
	public SimulatorGUI(String frameTitle)
	{
		// Create Frame with specified frame title
		super(frameTitle);

		// Ensure the Log is cleared
		Simulator.clearLog();

		// Run the setup dialog
		SetupDialog sD = new SetupDialog(this);

		// Set the values from the setup dialog
		int numProducers = sD.getNumProducers();
		int numConsumers = sD.getNumConsumers();

		// Initialize the Simulator
		simulator = new Simulator(new FileSharingSystem(sD.getTags()), sD.getTotalSimulationIterations());

		/* Initialize the file chooser */
		fileChooser = new JFileChooser();

		fileChooser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

				// These actions only apply to the save dialog
				if (!fileChooser.getName().equals("SaveFileDialog"))
					return;

				if (fileChooser.getSelectedFile() == null || fileChooser.getSelectedFile().getName() == null)
					return;

				// Ensure the file is created with the appropriate file
				// extension
				String selectedFileName = fileChooser.getSelectedFile().getAbsolutePath();

				if (!selectedFileName.toLowerCase().endsWith(SSF_FILE_EXT))
				{
					selectedFileName += SSF_FILE_EXT;
					fileChooser.setSelectedFile(new File(selectedFileName));
				}

				if (fileChooser.getSelectedFile().exists())
				{
					fileChooser.getSelectedFile().delete();
				}

			}
		});

		// Set the file filter to only enable simulation save files
		fileChooser.setFileFilter(createSimulationStateFileFilter());

		// Set current directory to user's home directory
		fileChooser.setCurrentDirectory(null);

		// Disable multiple file selections
		fileChooser.setMultiSelectionEnabled(false);

		/* Set & Initialize Frame Properties / Components */
		this.setTitle("Simulator (" + simulator.getCurrentSimulatorSequence() + "/" + simulator.getTotalSimulatorSequences() + ")");

		// Set the frame layout
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Set the minimum and preferred sizes
		this.setMinimumSize(new Dimension(640, 430));
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
	private void InitializeFrameComponents()
	{

		mainTabPanel = new JPanel(new CardLayout());

		// Create the main frame tabbed menu pane
		tabbedMenuPane = new JTabbedPane();

		// Create all of the tab panels
		simulatorPanel = new SimulatorPanel();

		// Obtain the users list model from the file sharing system
		DefaultListModel<User> allUsersListModel = simulator.getFileSharingSystem().getUsersListModel();

		// Create the users panel
		this.usersPanel = new UsersPanel(allUsersListModel);

		// Obtain the documents list model from the file sharing system
		DefaultListModel<Document> allDocumentsListModel = simulator.getFileSharingSystem().getDocumentsListModel();

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

		// restartButton JButton
		restartSimulationButton = new JButton("Restart Simulation");
		restartSimulationButton.addActionListener(click -> restartSimulation_Click());

		// Create a panel solely for simulator controls
		simulatorControlsPanel = new JPanel();

		// Add all controls to the panel
		simulatorControlsPanel.add(stepSimulatorButton);
		simulatorControlsPanel.add(runSimulatorButton);
		simulatorControlsPanel.add(restartSimulationButton);
		simulatorControlsPanel.setBorder(BorderFactory.createTitledBorder("Simulator Controls"));

		this.add(mainTabPanel);
		this.add(simulatorControlsPanel);
	}

	/**
	 * Restart Simulation button click event
	 * 
	 * @author MVezina
	 */
	private void restartSimulation_Click()
	{
		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to restart the Simulation?", "Restart?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{

			// Ensure the application does not exit on closing
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			// Hide and dispose the form and all of its components
			this.setVisible(false);
			this.dispose();

			// Create and run a new simulation
			new SimulatorGUI("Simulator");
		}

		 restoreSimulatorState();

	}
	
	
	public void saveState(OutputStream oS) throws IOException
	{
		if (oS == null)
			return;

		ObjectOutputStream oos = new ObjectOutputStream(oS);

		oos.writeObject(this);
		oos.close();
	}

	public void restoreState(InputStream iS) throws IOException, ClassNotFoundException
	{
		if (iS == null)
			return;

		ObjectInputStream oIS = new ObjectInputStream(iS);

		Object inputObject = oIS.readObject();

		oIS.close();

		if (!(inputObject instanceof Simulator))
		{
			return;
		}


	}

	/**
	 * Event Method: Called when the stepSimulator Button is clicked This will
	 * run the simulator until the end
	 * 
	 * @author MVezina
	 */
	private void runSimulator_Click()
	{

		while (simulator.getTotalSimulatorSequences() - simulator.getCurrentSimulatorSequence() > 0)
		{
			stepSimulator_Click();
		}
		saveSimulatorState();

		// saveSimulatorState();
	}

	/**
	 * Event Method: Called when the stepSimulator Button is clicked
	 * 
	 * @author MVezina
	 */
	private void stepSimulator_Click()
	{
		simulator.simulationStep();

		// Set the title
		this.setTitle("Simulator (" + simulator.getCurrentSimulatorSequence() + "/" + simulator.getTotalSimulatorSequences() + ")");

		// Set the log text box
		simulatorPanel.setLogText(Simulator.logText);

		// Repaint the frame
		this.repaint();

		if (simulator.getCurrentSimulatorSequence() == simulator.getTotalSimulatorSequences())
		{
			stepSimulatorButton.setEnabled(false);
			runSimulatorButton.setEnabled(false);

			JOptionPane.showMessageDialog(null, "The simulator has finished!", "Simulation Complete!", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void updateFrameTitle()
	{
		/* Set & Initialize Frame Properties / Components */
		this.setTitle("Simulator (" + simulator.getCurrentSimulatorSequence() + "/" + simulator.getTotalSimulatorSequences() + ")");
	}

	private void restoreSimulatorState()
	{
		// Set title
		fileChooser.setDialogTitle("Restore Simulation State");
		fileChooser.setName("RestoreFileDialog");

		// Show the save dialog and ensure the 'Open' (approve) button was
		// clicked
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{

			try
			{
				simulator.restoreState(new FileInputStream(fileChooser.getSelectedFile()));
			} catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this, "Failed to Restore state From file!");
				ex.printStackTrace();
			}
			
			
			this.simulatorPanel.setLogText(Simulator.logText);
			this.updateFrameTitle();
		}

	}

	private void saveSimulatorState()
	{

		// Set title and disable multiple file selections
		fileChooser.setDialogTitle("Save Simulation State");
		fileChooser.setName("SaveFileDialog");

		// Show the save dialog and ensure the 'Save' (approve) button was
		// clicked
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				simulator.saveState(new FileOutputStream(fileChooser.getSelectedFile()));
			} catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this, "Failed to Save state to file!");
				ex.printStackTrace();
			}
		}

	}

	/**
	 * @return
	 * @author MVezina
	 */
	private FileFilter createSimulationStateFileFilter()
	{

		return new FileFilter()
		{

			@Override
			public String getDescription()
			{
				return "Simulation State File (.ssf)";
			}

			@Override
			public boolean accept(File f)
			{
				// If the file is a directory OR we dont have permission to
				// write, return false
				if (f.isDirectory() || !f.canWrite())
					return false;

				// Check to make sure the file is a file (not a directory) and
				// ends with .ssf (Simulation State File)
				if (f.isFile() && f.getName().toLowerCase().endsWith(SSF_FILE_EXT))
				{
					return true;
				}

				return false;
			}
		};
	}

	public static void main(String[] args) throws Exception
	{
		new SimulatorGUI("Simulator");

	}

}
