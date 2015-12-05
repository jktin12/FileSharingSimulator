package nullSquad.simulator.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.*;

import nullSquad.filesharingsystem.users.*;
import nullSquad.strategies.act.ProducerActStrategy;
import nullSquad.strategies.ranking.DocumentRankingStrategy;

/**
 * Representation of the Users Tab Panel
 * 
 * @author MVezina
 */
public class UsersPanel extends JPanel implements ListCellRenderer<User>
{

	/* Serializable ID */
	private static final long serialVersionUID = 1L;

	// All SubPanels
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

	// Panel for all user options
	private JPanel userOptionPanel;

	// Panel for Producer Act Control
	private JPanel actStrategySelectionPanel;
	private ButtonGroup actStrategySelectionButtonGroup;
	private JRadioButton producerActStrategyARadioButton;
	private JRadioButton producerActStrategyBRadioButton;

	// Panel for Strategy Control
	private JPanel rankStrategySelectionPanel;
	private ButtonGroup rankStrategySelectionButtonGroup;
	private JRadioButton documentPopularityStrategyRadioButton;
	private JRadioButton followSimilarityStrategyRadioButton;
	private JRadioButton likeSimilarityStrategyRadioButton;
	private JRadioButton userDistanceStrategyRadioButton;
	private JRadioButton userPopularityStrategyRadioButton;

	private User currentlySelectedUser;

	/**
	 * Creates the Users panel and all associated components
	 * 
	 * @author MVezina
	 */
	public UsersPanel(DefaultListModel<User> allUsersListModel)
	{
		currentlySelectedUser = null;

		// Set the users list model
		this.allUsersListModel = allUsersListModel;

		// Create the users tab panel
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Initialize the list models
		consumersListModel = new DefaultListModel<Consumer>();
		producerListModel = new DefaultListModel<Producer>();

		this.allUsersListModel.addListDataListener(new UserListHandler(producerListModel, consumersListModel));

		// Create the Strategy Selection Panel
		rankStrategySelectionPanel = new JPanel();
		rankStrategySelectionPanel.setBorder(BorderFactory.createTitledBorder("Ranking Strategy"));

		// Create the strategy selection button group
		rankStrategySelectionButtonGroup = new ButtonGroup();

		// Create the user stats panel
		userStatsListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		userStatsListPanel.setBorder(BorderFactory.createTitledBorder("User Stats"));
		userStatsListPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		// Add the stats label to the info panel
		userStatsLabel = new JLabel("No User is Selected!");
		userStatsListPanel.add(userStatsLabel);
		userOptionPanel = new JPanel();
		userOptionPanel.setLayout(new BoxLayout(userOptionPanel, BoxLayout.X_AXIS));

		// Create the panel for the consumer list
		consumerListPanel = new JPanel();
		consumerListPanel.setLayout(new BoxLayout(consumerListPanel, BoxLayout.Y_AXIS));

		// Create the panel for the Producer list
		producerListPanel = new JPanel();
		producerListPanel.setLayout(new BoxLayout(producerListPanel, BoxLayout.Y_AXIS));

		// Create the panel for all User lists
		userListPanel = new JPanel();
		userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.X_AXIS));
		userListPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		// Create the producers JList
		producersJList = new JList<>(producerListModel);
		producersJList.setCellRenderer(this);
		producersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Create a default mouse adapter for the users lists
		MouseAdapter userListMouseAdapter = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				jList_MouseClicked(e);
			}
		};

		// Set the mouse listener for click actions on the lists
		producersJList.addMouseListener(userListMouseAdapter);
		producersJList.addListSelectionListener((ListSelectionEvent lse) -> producersJList_selectionChanged(lse));

		producersListScrollPane = new JScrollPane(producersJList);
		producersListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// Create the Consumers JList
		consumersJList = new JList<>(consumersListModel);
		consumersJList.setCellRenderer(this);
		consumersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		consumersJList.addListSelectionListener((ListSelectionEvent lse) -> consumersJList_selectionChanged(lse));
		consumersJList.addMouseListener(userListMouseAdapter);

		consumersListScrollPane = new JScrollPane(consumersJList);
		consumersListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

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

		/* ===== Initialization of all the strategy radio buttons ===== */
		documentPopularityStrategyRadioButton = new JRadioButton(DocumentRankingStrategy.Strategy.DocumentPopularity.toString());
		documentPopularityStrategyRadioButton.addActionListener(al -> rankStrategyRadioButton_Clicked(DocumentRankingStrategy.Strategy.DocumentPopularity));

		userPopularityStrategyRadioButton = new JRadioButton(DocumentRankingStrategy.Strategy.UserPopularity.toString());
		userPopularityStrategyRadioButton.addActionListener(al -> rankStrategyRadioButton_Clicked(DocumentRankingStrategy.Strategy.UserPopularity));

		likeSimilarityStrategyRadioButton = new JRadioButton(DocumentRankingStrategy.Strategy.LikeSimilarity.toString());
		likeSimilarityStrategyRadioButton.addActionListener(al -> rankStrategyRadioButton_Clicked(DocumentRankingStrategy.Strategy.LikeSimilarity));

		followSimilarityStrategyRadioButton = new JRadioButton(DocumentRankingStrategy.Strategy.FollowSimiliarity.toString());
		followSimilarityStrategyRadioButton.addActionListener(al -> rankStrategyRadioButton_Clicked(DocumentRankingStrategy.Strategy.FollowSimiliarity));

		userDistanceStrategyRadioButton = new JRadioButton(DocumentRankingStrategy.Strategy.UserDistance.toString());
		userDistanceStrategyRadioButton.addActionListener(al -> rankStrategyRadioButton_Clicked(DocumentRankingStrategy.Strategy.UserDistance));
		/* ===== END of Initialization of all the strategy radio buttons ===== */

		// Set the layout of the selection panel and the maximum size
		rankStrategySelectionPanel.setLayout(new BoxLayout(rankStrategySelectionPanel, BoxLayout.Y_AXIS));
		rankStrategySelectionPanel.setMaximumSize(new Dimension(rankStrategySelectionPanel.getMaximumSize().width, Integer.MAX_VALUE));

		// Add all of the radio buttons into the button group
		rankStrategySelectionButtonGroup.add(documentPopularityStrategyRadioButton);
		rankStrategySelectionButtonGroup.add(userPopularityStrategyRadioButton);
		rankStrategySelectionButtonGroup.add(likeSimilarityStrategyRadioButton);
		rankStrategySelectionButtonGroup.add(followSimilarityStrategyRadioButton);
		rankStrategySelectionButtonGroup.add(userDistanceStrategyRadioButton);

		// Add all of the buttons to the selection panel
		rankStrategySelectionPanel.add(documentPopularityStrategyRadioButton);
		rankStrategySelectionPanel.add(userPopularityStrategyRadioButton);
		rankStrategySelectionPanel.add(likeSimilarityStrategyRadioButton);
		rankStrategySelectionPanel.add(followSimilarityStrategyRadioButton);
		rankStrategySelectionPanel.add(userDistanceStrategyRadioButton);
		rankStrategySelectionPanel.setVisible(false);

		// Create the producer act strategy panel
		actStrategySelectionPanel = new JPanel();
		actStrategySelectionPanel.setBorder(BorderFactory.createTitledBorder("Producer Act Strategy"));
		actStrategySelectionPanel.setMaximumSize(new Dimension(actStrategySelectionPanel.getMaximumSize().width, Integer.MAX_VALUE));
		actStrategySelectionPanel.setPreferredSize(new Dimension(185, actStrategySelectionPanel.getPreferredSize().height));
		actStrategySelectionPanel.setLayout(new BoxLayout(actStrategySelectionPanel, BoxLayout.Y_AXIS));
		actStrategySelectionPanel.setVisible(false);

		// Create appropriate strategy buttons
		producerActStrategyARadioButton = new JRadioButton(ProducerActStrategy.Strategy.Default.toString());
		producerActStrategyARadioButton.addActionListener(al -> actStrategyRadioButton_Clicked(ProducerActStrategy.Strategy.Default));

		producerActStrategyBRadioButton = new JRadioButton(ProducerActStrategy.Strategy.Similarity.toString());
		producerActStrategyBRadioButton.addActionListener(al -> actStrategyRadioButton_Clicked(ProducerActStrategy.Strategy.Similarity));

		// Add the radio buttons to the panel and a button group
		actStrategySelectionButtonGroup = new ButtonGroup();
		actStrategySelectionButtonGroup.add(producerActStrategyARadioButton);
		actStrategySelectionButtonGroup.add(producerActStrategyBRadioButton);

		actStrategySelectionPanel.add(producerActStrategyARadioButton);
		actStrategySelectionPanel.add(producerActStrategyBRadioButton);

		// Add panels to the main user tab panel
		this.add(userListPanel);
		userOptionPanel.add(userStatsListPanel);
		userOptionPanel.add(rankStrategySelectionPanel);
		userOptionPanel.add(actStrategySelectionPanel);
		this.add(userOptionPanel);
	}

	/**
	 * Set the selected producer act strategy
	 * 
	 * @param actStrategy The Act Strategy to
	 */
	private void actStrategyRadioButton_Clicked(ProducerActStrategy.Strategy actStrategy)
	{
		Producer producer = producersJList.getSelectedValue();

		if (producer == null)
			return;

		producer.setActStrategyEnum(actStrategy);
	}

	/**
	 * Sets the strategy for the currently selected user
	 * 
	 * @param rankingStrategy The strategy to set to the user
	 */
	private void rankStrategyRadioButton_Clicked(DocumentRankingStrategy.Strategy rankingStrategy)
	{
		// Gets the currently selected user
		User selectedUser = getCurrentlySelectedUser();

		// Checks to see if there is a selected user
		if (selectedUser == null)
			return;

		// Sets the search ranking strategy
		selectedUser.setDocumentRankingStrategy(rankingStrategy);
	}

	/**
	 * Gets the currently selected user or NULL if no user is selected
	 * 
	 * @return The Currently selected user from both Producer/Consumer JLists
	 *         (or null if none have a selected value)
	 */
	private User getCurrentlySelectedUser()
	{
		if (producersJList.getSelectedValue() != null)
			return producersJList.getSelectedValue();
		else
			return consumersJList.getSelectedValue();
	}

	/**
	 * The Selection event method for the consumersJList
	 * 
	 * @param lse The List Selection Event
	 */
	private void consumersJList_selectionChanged(ListSelectionEvent lse)
	{
		if (!lse.getValueIsAdjusting() && lse.getSource() == consumersJList)
		{
			if (consumersJList.getSelectedIndex() >= 0)
			{
				// Clear the selection of the opposing list (Because we only
				// want one user to be selected)
				producersJList.getSelectionModel().clearSelection();

				// Update the user stats label
				currentlySelectedUser = (consumersJList.getSelectedValue());
				updateUserStats();
			}
		}
	}

	/**
	 * Mouse Click event
	 */
	private void jList_MouseClicked(MouseEvent e)
	{

		if (e.getClickCount() == 2)
		{
			// If a double click action is sent on a jlist, we want to open the
			// graph view for the selected user
			if (e.getSource() == consumersJList && consumersJList.getSelectedValue() != null)
			{
				new GraphGUI(consumersJList.getSelectedValue());
			}

			if (e.getSource() == producersJList && producersJList.getSelectedValue() != null)
			{
				new GraphGUI(producersJList.getSelectedValue());
			}
		}

	}

	/**
	 * The Selection Changed event method for the producersJList
	 * 
	 * @param lse The Event Object
	 * @author MVezina
	 */
	private void producersJList_selectionChanged(ListSelectionEvent lse)
	{
		if (!lse.getValueIsAdjusting() && lse.getSource() == producersJList)
		{
			if (producersJList.getSelectedIndex() >= 0)
			{
				consumersJList.getSelectionModel().clearSelection();
				currentlySelectedUser = (producersJList.getSelectedValue());
				updateUserStats();
			}
		}
	}

	/**
	 * Update the currently Selected user stats
	 * 
	 * @author MVezina
	 */
	public void updateUserStats()
	{

		if (currentlySelectedUser == null)
		{
			userStatsLabel.setText("No User is Selected!");
			rankStrategySelectionPanel.setVisible(false);
			actStrategySelectionPanel.setVisible(false);
			return;
		}

		// HTML code is used in the statsLabel for text formatting
		String userStats = "<html>";
		String newLine = "<br>";

		userStats += ("<b>ID</b>: " + currentlySelectedUser.getUserID() + newLine);
		userStats += ("<b>Name</b>: " + currentlySelectedUser.getUserName() + newLine);
		userStats += ("<b>User Type</b>: " + (currentlySelectedUser instanceof Producer ? "Producer" : "Consumer") + " (Payoff: " + (currentlySelectedUser.getPayoffHistory().size() > 0 ? currentlySelectedUser.getPayoffHistory().get(currentlySelectedUser.getPayoffHistory().size() - 1) : 0) + ")" + newLine);
		userStats += ("<b>Taste</b>: " + currentlySelectedUser.getTaste() + newLine);
		userStats += ("<b>Followers</b>: " + currentlySelectedUser.getFollowers().size() + newLine);
		userStats += ("<b>Following</b>: " + currentlySelectedUser.getFollowing().size() + newLine);
		userStats += ("<b>Number of Documents Liked</b>: " + currentlySelectedUser.getLikedDocuments().size() + newLine);

		if (currentlySelectedUser instanceof Producer)
		{
			userStats += ("<b>Number of Documents Produced</b>: " + ((Producer) currentlySelectedUser).getDocumentsProduced().size() + newLine);
		}

		// Set the label text (ending it with a closing HTML tag)
		userStatsLabel.setText(userStats + "</html>");

		// Determine which strategy is currently being used by the user
		switch (currentlySelectedUser.getDocumentRankingStrategy())
		{
			case DocumentPopularity:
				documentPopularityStrategyRadioButton.setSelected(true);
				break;
			case FollowSimiliarity:
				followSimilarityStrategyRadioButton.setSelected(true);
				break;
			case LikeSimilarity:
				likeSimilarityStrategyRadioButton.setSelected(true);
				break;
			case UserDistance:
				userDistanceStrategyRadioButton.setSelected(true);
				break;
			case UserPopularity:
				userPopularityStrategyRadioButton.setSelected(true);
				break;

		}

		// Determine which radio button is chosen when a producer is selected
		if (currentlySelectedUser instanceof Producer)
		{
			// Create a temporary producer
			Producer p = (Producer) currentlySelectedUser;

			switch (p.getActStrategyEnum())
			{
				case Default:
					producerActStrategyARadioButton.setSelected(true);
					break;
				case Similarity:
					producerActStrategyBRadioButton.setSelected(true);
					break;
			}

			// Set the act strategy selection panel to visible if a producer is
			// selected
			actStrategySelectionPanel.setVisible(true);
		}
		else
		{
			// Hide the producer act strategy selection panel if a producer was
			// not selected
			actStrategySelectionPanel.setVisible(false);
		}

		// Set the rank strategy to visible
		rankStrategySelectionPanel.setVisible(true);
	}

	/**
	 * Item added to allUsersListModel
	 */

	@Override
	public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected, boolean cellHasFocus)
	{
		// Create a new label to represent a cell in the document list
		JLabel label = new JLabel(value.getUserName() + "   (ID: " + value.getUserID() + ")");
		label.setOpaque(true);

		// Set the background and foreground colors (based on whether or not an
		// item is selected)
		if (isSelected)
		{
			label.setBackground(list.getSelectionBackground());
			label.setForeground(list.getSelectionForeground());
		}
		else
		{
			label.setBackground(list.getBackground());
			label.setForeground(list.getForeground());
		}

		return label;
	}

}
