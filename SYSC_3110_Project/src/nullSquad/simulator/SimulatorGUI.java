/**
 * 
 */
package nullSquad.simulator;

import java.awt.*;

import javax.swing.*;

import nullSquad.network.*;

/**
 * @author MVezina
 *
 */
@SuppressWarnings("serial")
public class SimulatorGUI extends JFrame
{

	private Simulator	simulator;
	JTabbedPane			tabbedMenuPane;
	DefaultListModel<Consumer> consumerListModel;
	DefaultListModel<Producer> producerListModel;
	

	/**
	 * Constructor for creating a simulator GUI
	 * 
	 * @param frameTitle The title of the GUI frame
	 */
	public SimulatorGUI(String frameTitle)
	{
		// Create Frame with specified frame title
		super(frameTitle);

		int numProducers = 2; // Integer.parseInt(JOptionPane.showInputDialog(this,
								// "Please Enter The Number Of Producers to Create (0-20): "));
		int numConsumers = 2; // Integer.parseInt(JOptionPane.showInputDialog(this,
								// "Please Enter The Number Of Consumers to Create (0-20): "));

		FileSharingSystem network = new FileSharingSystem();
		// Create a new simulator
		simulator = new Simulator(network, numProducers, numConsumers);

		// Set the frame layout
		this.setLayout(new CardLayout());

		// Set size of frame
		this.setPreferredSize(new Dimension(500, 500));
		this.setSize(500, 500);

		// Center the frame
		this.setLocationRelativeTo(null);

		// Exit the program on close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Initialize the List Models
		consumerListModel = new DefaultListModel<>();
		producerListModel = new DefaultListModel<>();
		
		for(User u : network.getUsers())
		{
			if(u instanceof Producer)
			{
				producerListModel.add(producerListModel.getSize(), (Producer) u);
			}
			else if (u instanceof Consumer)
			{
				consumerListModel.add(consumerListModel.getSize(), (Consumer) u);
			}
		}
		
		// Now we want to add any components to the GUI
		InitializeFrameComponents();

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
		
		// /--
		// / tabbedMenuPane
		// /--
		JTabbedPane tabbedMenuPane = new JTabbedPane();

		// Create the simulator panel for the simulator tab
		Panel simulatorPanel = new Panel();

		// /--
		// / stepSimulator JButton
		// /--
		JButton stepSimulator = new JButton("Step Simulator");
		stepSimulator.addActionListener(click -> stepSimulator_Click());
		stepSimulator.setSize(new Dimension(90, 20));

		// /--
		// / runSimulator JButton
		// /--
		JButton runSimulator = new JButton("Run Simulator");
		runSimulator.addActionListener(click -> runSimulator_Click());
		runSimulator.setSize(new Dimension(90, 20));

		// /--
		// / listBox of Producers
		// /--
		JList<Producer> producerJList = new JList<Producer>(producerListModel);		
		producerJList.setToolTipText("Producer List");
		producerJList.setPreferredSize(new Dimension(300, 150));

		// /--
		// / listBox of Consumers
		// /--
		JList<Consumer> consumerJList = new JList<Consumer>(consumerListModel);
		consumerJList.setToolTipText("Consumer List");
		consumerJList.setPreferredSize(new Dimension(300, 150));

		// Add the buttons correlating to the simulator to the simulator panel
		simulatorPanel.add(stepSimulator);
		simulatorPanel.add(runSimulator);
		simulatorPanel.add(producerJList);
		simulatorPanel.add(consumerJList);

		tabbedMenuPane.addTab("Simulator", simulatorPanel);
		tabbedMenuPane.addTab("Settings", new Panel());

		this.add(tabbedMenuPane);
	}

	void runSimulator_Click()
	{
		simulator.simulationRun(Math.abs(Integer.parseInt(JOptionPane.showInputDialog(this, "Please Enter the Number of Steps to Run: "))));
	}

	void stepSimulator_Click()
	{
		System.out.println("Simulation: 1 Step");
		simulator.simulationStep();
	}

	public static void main(String[] args)
	{
		new SimulatorGUI("Simulator");
	}

}
