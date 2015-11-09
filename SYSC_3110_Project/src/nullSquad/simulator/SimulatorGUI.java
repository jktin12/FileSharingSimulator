/**
 * 
 */
package nullSquad.simulator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;

import org.jfree.*;
import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;

import nullSquad.network.*;

import nullSquad.document.*;

/**
 * @author MVezina
 *
 */
@SuppressWarnings("serial")
public class SimulatorGUI extends JFrame implements ListDataListener, MouseListener{

	// Main Frame Tab Pane
	private JTabbedPane tabbedMenuPane;
	private JPanel mainTabPanel;
	
	
	// Main Tab Panels
	private JPanel documentsPanel;
	private JPanel usersPanel;
	private JPanel simulatorPanel;
	
	/*  'Simulator' tab content */
	private JButton stepSimulatorButton;
	private JButton runSimulatorButton;
	private JTextArea mainSimulatorTextArea;
	private JPanel simulatorControlsPanel;
	
	/*  'Users' tab content */
	private JList<Consumer> consumersJList;
	private DefaultListModel<Consumer> consumersListModel;
	private JList<Producer> producersJList;
	private DefaultListModel<Producer> producerListModel;
	private DefaultListModel<User> allUsersListModel;
	private JScrollPane consumersListScrollPane;
	private JScrollPane producersListScrollPane;
	private JPanel userListPanel;
	private JPanel consumerListPanel;
	private JPanel producerListPanel;
	
	
	// Fields for the user stats panel
	private JPanel userStatsListPanel;
	private JLabel userStatsLabel;
	
	/*  'Documents' tab content */
	private JList<Document> documentsJList;
	private DefaultListModel<Document> allDocumentsListModel;
	private JScrollPane documentListScrollPane;
	
	// Fields for the document stats panel
	private JPanel documentStatsListPanel;
	private JLabel documentStatsLabel;
	
	
	// Simulator Fields
	private Random	randomNumber;
	private FileSharingSystem network;
	private List<String> tags;
	private int currentSimulatorSequence;
	private int totalSimulatorSequences;
	
	

	/**
	 * Constructor for creating a simulator GUI.
	 * Initializes all values and components
	 * @param frameTitle The title of the GUI frame
	 */
	public SimulatorGUI(String frameTitle) {
		// Create Frame with specified frame title
		super(frameTitle);
		
		// Run the setup dialog
		SetupDialog sD = new SetupDialog(this);
		
		
		// Initialize the network
		network = new FileSharingSystem();
		
		// Set the values from the setup dialog
		int numProducers = sD.getNumProducers(); 
		int numConsumers = sD.getNumConsumers();

		// Initialize the simulator fields
		this.currentSimulatorSequence = 0;
		this.totalSimulatorSequences = sD.getTotalSimulationIterations();		
		this.randomNumber = new Random(sD.getSimulationSeed() * new Random().nextInt());
		this.tags = sD.getTags();
		
		// Initialize the List Models
		allUsersListModel = network.getUsersListModel();
		allDocumentsListModel = network.getDocumentsListModel();
		consumersListModel = new DefaultListModel<Consumer>();
		producerListModel = new DefaultListModel<Producer>();
		
		allUsersListModel.addListDataListener(this);
		
		// Generate the producers and consumers
		createConsumers(numConsumers);
		createProducers(numProducers);
		
		
		
		/* Set & Initialize Frame Properties / Components */
		
		this.setTitle("Simulator (" + currentSimulatorSequence + "/" + totalSimulatorSequences + ")");
		
		// Set the frame layout
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		// Set the minimum and preferred sizes
		this.setMinimumSize(new Dimension(680,400));
		this.setPreferredSize(this.getMinimumSize());
		
		// Center the frame on the screen
		this.setLocationRelativeTo(null);

		// Exit the program on close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Now we want to add any components to the GUI
		InitializeFrameComponents();

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
		createSimulatorPanel();
		createUsersPanel();
		createDocumentsPanel();
		
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

		
		// Add the buttons correlating to the simulator to the simulator controls panel
		simulatorControlsPanel = new JPanel();
		
		simulatorControlsPanel.add(stepSimulatorButton);
		simulatorControlsPanel.add(runSimulatorButton);
		simulatorControlsPanel.setBorder(BorderFactory.createTitledBorder("Simulator Controls"));
		
		this.add(mainTabPanel);
		this.add(simulatorControlsPanel);
		this.add(Box.createRigidArea(new Dimension(0, 5)));
	}

	/**
	 * Creates the simulator panel and all associated components
	 * @author MVezina
	 */
	private void createSimulatorPanel()
	{
		simulatorPanel = new JPanel();		
		simulatorPanel.setLayout(new BoxLayout(simulatorPanel, BoxLayout.Y_AXIS));

		// Create a scrollable JTextArea pane
		mainSimulatorTextArea = new JTextArea("Welcome to the Simulator!\n");
		mainSimulatorTextArea.setEditable(false);
		simulatorPanel.add(new JScrollPane(mainSimulatorTextArea));
		
	}
	
	/**
	 * Creates the Users panel and all associated components
	 * @author MVezina
	 */
	private void createUsersPanel()
	{
		// Create the users tab panel
		usersPanel = new JPanel();
		usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
		
		// Create the user stats panel
		userStatsListPanel = new JPanel();
		userStatsListPanel.setBorder(BorderFactory.createTitledBorder("User Stats"));
		userStatsListPanel.setPreferredSize(new Dimension(300, 150));
		
		// Add the stats label to the info panel
		userStatsLabel = new JLabel("No User is Selected!");
		userStatsListPanel.add(userStatsLabel);
				
		
		// Create the panel for the consumer list
		consumerListPanel = new JPanel();
		consumerListPanel.setLayout(new BoxLayout(consumerListPanel, BoxLayout.Y_AXIS));
		
		// Create the panel for the Producer list
		producerListPanel = new JPanel();
		producerListPanel.setLayout(new BoxLayout(producerListPanel, BoxLayout.Y_AXIS));
		
		// Create the panel for all User lists
		userListPanel = new JPanel();
		userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.X_AXIS));
		
		// Create the producers JList
		producersJList = new JList<>(producerListModel);
		producersJList.addMouseListener(this);
		producersJList.addListSelectionListener((ListSelectionEvent lse) -> producersJList_selectionChanged(lse));
	
		producersListScrollPane = new JScrollPane(producersJList);
		producersListScrollPane.addMouseListener(this);
		producersListScrollPane.setPreferredSize(new Dimension(300, 150));
		
		// Create the Consumers JList
		consumersJList = new JList<>(consumersListModel);
		consumersJList.addListSelectionListener((ListSelectionEvent lse) -> consumersJList_selectionChanged(lse));
		consumersJList.addMouseListener(this);
		consumersListScrollPane = new JScrollPane(consumersJList);
		consumersListScrollPane.setPreferredSize(new Dimension(300, 150));
		
		// Add list labels and add scrollable list panes to the corresponding list panels
		producerListPanel.add(new JLabel("Producer List:"));
		producerListPanel.add(producersListScrollPane);
		consumerListPanel.add(new JLabel("Consumer List:"));
		consumerListPanel.add(consumersListScrollPane);
		
		// Add lists to the user List Panel
		userListPanel.add(producerListPanel);	
		userListPanel.add(Box.createRigidArea(new Dimension(5,0)));
		userListPanel.add(consumerListPanel);
		
		// Add panels to the main user tab panel
		usersPanel.add(userListPanel);
		usersPanel.add(userStatsListPanel);
	
	}
	
	private void consumersJList_selectionChanged(ListSelectionEvent lse) 
	{
		if(!lse.getValueIsAdjusting() && lse.getSource() == consumersJList)
		{
			
			if(consumersJList.getSelectedIndex() >= 0)
			{
				producersJList.getSelectionModel().clearSelection();
				updateUserStats(consumersJList.getSelectedValue());
			}
			
			
			
		}
		
	}

	private void producersJList_selectionChanged(ListSelectionEvent lse) 
	{
		
		if(!lse.getValueIsAdjusting() && lse.getSource() == producersJList)
		{
			if(producersJList.getSelectedIndex() >= 0)
			{
				consumersJList.getSelectionModel().clearSelection();;
				updateUserStats(producersJList.getSelectedValue());
			}
			
		}
		
	}

	/**
	 * Creates the Documents panel and all associated components
	 * @author MVezina
	 */
	private void createDocumentsPanel() {
		documentsPanel = new JPanel();
		documentsPanel.setLayout(new BoxLayout(documentsPanel, BoxLayout.Y_AXIS));
		
		
		// Create the document list scroll pane
		documentsJList = new JList<>(allDocumentsListModel);
		documentsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		documentsJList.addListSelectionListener((ListSelectionEvent e) -> documentJList_SelectionChanged(e));
		documentListScrollPane = new JScrollPane(documentsJList);
		
		
		documentListScrollPane.setPreferredSize(new Dimension(300, 150));
		documentsPanel.add(documentListScrollPane);

		// Document Statistics Panel
		documentStatsListPanel = new JPanel();
		documentStatsListPanel.setBorder(BorderFactory.createTitledBorder("Document Stats"));
		documentStatsListPanel.setPreferredSize(new Dimension(300, 150));
		
		// Add the stats label to the info panel
		documentStatsLabel = new JLabel("No Document Selected!");
		documentStatsListPanel.add(documentStatsLabel);
		
		// Add the info panel to the document tab panel
		documentsPanel.add(documentStatsListPanel);
		
	}
	
	
	/**
	 * Event Method: Called when the index of the documentJList is changed
	 * @author MVezina
	 */
	void documentJList_SelectionChanged(ListSelectionEvent lse)
	{
		// Check to see when the value is finished adjusting and if the source is a list
		if(!lse.getValueIsAdjusting() && lse.getSource() == documentsJList)
		{
			// Get the selected index
			if(documentsJList.getSelectedIndex() < 0)
			{
				// No document is selected
				updateDocumentStats(null);
				return;
			}
			
			// Update the document stats
			updateDocumentStats(documentsJList.getSelectedValue());
		}
	}
	
	
	private void updateUserStats(User user)
	{
		if(user == null)
		{
			userStatsLabel.setText("No User is Selected!");
			return;
		}
		
		// HTML code is used in the statsLabel for text formatting
		String userStats = "<html>";
		String newLine = "<br>";
				
		userStats += ("<b>ID</b>: " + user.getUserID() + newLine);		
		userStats += ("<b>Name</b>: " + user.getUserName() + newLine);
		userStats += ("<b>User Type</b>: " + (user instanceof Producer ? "Producer" : "Consumer") + newLine);
		userStats += ("<b>Taste</b>: " + user.getTaste() + newLine);
		userStats += ("<b>Followers</b>: " + user.getFollowers().size() + newLine);
		userStats += ("<b>Following</b>: " + user.getFollowing().size() + newLine);
		userStats += ("<b>Number of Documents Liked</b>: " + user.getLikedDocuments().size() + newLine);				
		
		// Set the label text (ending it with a closing HTML tag)
		userStatsLabel.setText(userStats + "</html>");
		
	}
	
	/**
	 * Updates the document stats shown in the documents tab
	 * @param selectedDoc The document currently selected. (null = No document is selected)
	 */
	private void updateDocumentStats(Document selectedDoc)
	{
		if(selectedDoc == null)
		{
			documentStatsLabel.setText("No Document Selected!");
			return;
		}
		
		// HTML code is used in the statsLabel for text formatting
		String docStats = "<html>";
		String newLine = "<br>";
		
		docStats += ("<b>ID</b>: " + selectedDoc.getDocumentID() + newLine);		
		docStats += ("<b>Name</b>: " + selectedDoc.getDocumentName() + newLine);
		docStats += ("<b>Tag</b>: " + selectedDoc.getTag() + newLine);
		docStats += ("<b>Producer</b>: " + selectedDoc.getProducer().getUserName() + newLine);
		docStats += ("<b>Date Uploaded</b>: " + selectedDoc.getDateUploaded() + newLine);
		docStats += ("<b>Likes</b>: " + selectedDoc.getUserLikes().size() + newLine);
		
		// Set the label text (ending it with a closing HTML tag)
		documentStatsLabel.setText(docStats + "</html>");
	}

	/**
	 * Event Method: Called when the stepSimulator Button is clicked
	 * This will run the simulator until the end
	 * @author MVezina
	 */
	void runSimulator_Click() {
		
	
		while (totalSimulatorSequences - currentSimulatorSequence > 0)
		{
			simulationStep();
		}
	}
	
	/**
	 * Adds the specified number of consumers into the network
	 *
	 * 
	 * @param numberOfConsumers Number of consumers in the network
	 * @author Raymond Wu
	 */
	private void createConsumers(int numberOfConsumers)
	{
		for (int x = 0; x < numberOfConsumers; x++)
		{
			User user = new Consumer("Consumer" + x, tags.get(randomNumber.nextInt(tags.size())));
			user.registerUser(network);
		}
	}
	
	
	/**
	 * Adds the specified number of producers into the network
	 * 
	 * 
	 * @param numberOfProducers Number of producers in the network
	 * @author Raymond Wu
	 */
	private void createProducers(int numberOfProducers)
	{
		for (int x = 0; x < numberOfProducers; x++)
		{
			User user = new Producer("Producer" + x, tags.get(randomNumber.nextInt(tags.size())));
			user.registerUser(network);
		}
	}

	/**
	 * Performs one simulation cycle which calls
	 * a user to "act"
	 * 
	 * @author Raymond Wu
	 */
	public void simulationStep()
	{
		// Generate a random number so the simulation can get a random user
		User randomUser = network.getUsers().get(randomNumber.nextInt(network.getUsers().size()));
		mainSimulatorTextArea.setText(mainSimulatorTextArea.getText() + "User: " + randomUser.getUserName() + " has been called to act!\n");
		
		randomUser.act(network, 10);
		
		// Add the payoff iteration for each user
		for(User u : network.getUsers())
		{
			u.addIterationPayoff(currentSimulatorSequence);
		}
		
		
		
		currentSimulatorSequence++;		
		
		
		if(currentSimulatorSequence == totalSimulatorSequences)
		{
			JOptionPane.showMessageDialog(null, "The simulator has finished!","Simulation Complete!", JOptionPane.INFORMATION_MESSAGE);
			stepSimulatorButton.setEnabled(false);
			runSimulatorButton.setEnabled(false);
		}
		
		this.setTitle("Simulator (" + currentSimulatorSequence + "/" + totalSimulatorSequences + ")");
		
	}
	
	/**
	 * Event Method: Called when the stepSimulator Button is clicked
	 * @author MVezina
	 */
	void stepSimulator_Click() {
		simulationStep();
		
	}
	

	public static void main(String[] args) {
		new SimulatorGUI("Simulator");
		//new GraphGUI(new Producer("asd", "asd"));
	}

	/**
	 * Item added to allUsersListModel
	 */
	@Override
	public void intervalAdded(ListDataEvent e) {
		if(e.getSource() == allUsersListModel)
		{
			User newUser = allUsersListModel.getElementAt(e.getIndex0());
			if(newUser instanceof Producer)
			{
				producerListModel.addElement((Producer)newUser);
			}
			else if(newUser instanceof Consumer)
			{
				consumersListModel.addElement((Consumer)newUser);
			}				
		}
		
	}

	/**
	 * Item removed from allUsersListModel
	 */
	@Override
	public void intervalRemoved(ListDataEvent e) {
	}

	/**
	 * Contents changed (allUsersListModel)
	 */
	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Mouse Click event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2)
		{
			if(e.getSource() == consumersJList && consumersJList.getSelectedValue() != null)
			{
				new GraphGUI(consumersJList.getSelectedValue());
			}
			
			if(e.getSource() == producersJList && producersJList.getSelectedValue() != null)
			{
				new GraphGUI(producersJList.getSelectedValue());
			}
		}
		
	}

	/**
	 * Mouse Pressed event
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Mouse Released event
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Mouse Enter event
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Mouse Leave event
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
