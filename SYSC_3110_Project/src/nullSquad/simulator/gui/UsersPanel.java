package nullSquad.simulator.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.*;

import nullSquad.filesharingsystem.users.*;


/**
 * Representation of the Users Tab Panel
 * @author MVezina
 */
public class UsersPanel extends JPanel implements ListDataListener {

	private JScrollPane consumersListScrollPane;
	private JScrollPane producersListScrollPane;
	private JPanel userListPanel;
	private JPanel consumerListPanel;
	private JPanel producerListPanel;

	/* 'Users' tab content */
	private JList<Consumer> consumersJList;
	private DefaultListModel<Consumer> consumersListModel;
	private JList<Producer> producersJList;
	private DefaultListModel<Producer> producerListModel;
	private DefaultListModel<User> allUsersListModel;

	// Fields for the user stats panel
	private JPanel userStatsListPanel;
	private JLabel userStatsLabel;

	
	/**
	 * Creates the Users panel and all associated components
	 * @author MVezina
	 */
	public UsersPanel(DefaultListModel<User> allUsersListModel) {
	
		// Set the users list model
		this.allUsersListModel = allUsersListModel;
		this.allUsersListModel.addListDataListener(this);
		
		// Set the documents List model
		
		// Create the users tab panel
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Initialize the list models
		consumersListModel = new DefaultListModel<Consumer>();
		producerListModel = new DefaultListModel<Producer>();

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
		producersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Create a default mouse adapter for the users lists
		MouseAdapter userListMouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jList_MouseClicked(e);
			}
		};

		// Set the mouse listener for click actions on the lists
		producersJList.addMouseListener(userListMouseAdapter);
		producersJList.addListSelectionListener((ListSelectionEvent lse) -> producersJList_selectionChanged(lse));

		producersListScrollPane = new JScrollPane(producersJList);
		
		//TODO Remove:
		producersListScrollPane.addMouseListener(userListMouseAdapter);
		
		
		producersListScrollPane.setPreferredSize(new Dimension(300, 150));

		// Create the Consumers JList
		consumersJList = new JList<>(consumersListModel);
		consumersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		consumersJList.addListSelectionListener((ListSelectionEvent lse) -> consumersJList_selectionChanged(lse));
		consumersJList.addMouseListener(userListMouseAdapter);
	//	consumersJList.setComponentPopupMenu(userPopupMenu);

		consumersListScrollPane = new JScrollPane(consumersJList);
		consumersListScrollPane.setPreferredSize(new Dimension(300, 150));

		// Add list labels and add scrollable list panes to the corresponding
		// list panels
		producerListPanel.add(new JLabel("Producer List:"));
		producerListPanel.add(producersListScrollPane);
		consumerListPanel.add(new JLabel("Consumer List:"));
		consumerListPanel.add(consumersListScrollPane);

		// Add lists to the user List Panel
		userListPanel.add(producerListPanel);
		userListPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		userListPanel.add(consumerListPanel);

		// Add panels to the main user tab panel
		this.add(userListPanel);
		this.add(userStatsListPanel);
	}

	/**
	 * The Selection event method for the consumersJList
	 * 
	 * @param lse
	 */
	private void consumersJList_selectionChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting() && lse.getSource() == consumersJList) {

			if (consumersJList.getSelectedIndex() >= 0) {
				producersJList.getSelectionModel().clearSelection();
				updateUserStats(consumersJList.getSelectedValue());
			}

		}

	}

	/**
	 * Mouse Click event
	 */
	public void jList_MouseClicked(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON3) {

			// If a double click action is sent on a jlist, we want to open the
			// graph view for the selected user
			if (e.getSource() == consumersJList && consumersJList.getSelectedValue() != null) {
				System.out.println("Consumer Clicked");
				//TODO: Add Popup menu
				//userPopupMenu.show(consumersJList, e.getX(), e.getY());
				 new GraphGUI(consumersJList.getSelectedValue());
			}

			if (e.getSource() == producersJList && producersJList.getSelectedValue() != null) {
				 new GraphGUI(producersJList.getSelectedValue());
			}
		}

	}

	/**
	 * The Selection Changed event method for the producersJList
	 * 
	 * @param lse
	 *            The Event Object
	 * @author MVezina
	 */
	private void producersJList_selectionChanged(ListSelectionEvent lse) {

		if (!lse.getValueIsAdjusting() && lse.getSource() == producersJList) {
			if (producersJList.getSelectedIndex() >= 0) {
				consumersJList.getSelectionModel().clearSelection();
				;
				updateUserStats(producersJList.getSelectedValue());
			}

		}

	}
	
	/**
	 * Update the currently Selected user stats
	 * @param user The Currently Selected user
	 * @author MVezina
	 */
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
		userStats += ("<b>User Type</b>: " + (user instanceof Producer ? "Producer" : "Consumer") + " (Payoff: " +  (user.getPayoffHistory().size() > 0 ? user.getPayoffHistory().get(user.getPayoffHistory().size()-1) : 0) + ")" + newLine);
		userStats += ("<b>Taste</b>: " + user.getTaste() + newLine);
		userStats += ("<b>Followers</b>: " + user.getFollowers().size() + newLine);
		userStats += ("<b>Following</b>: " + user.getFollowing().size() + newLine);
		userStats += ("<b>Number of Documents Liked</b>: " + user.getLikedDocuments().size() + newLine);				
		
		// Set the label text (ending it with a closing HTML tag)
		userStatsLabel.setText(userStats + "</html>");
		
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
	 * The following are Empty implementations from ListDataListener Interface
	 * (No ListDataAdapter was available)
	 */
	@Override
	public void intervalRemoved(ListDataEvent e) {}
	@Override
	public void contentsChanged(ListDataEvent e) {}

}
