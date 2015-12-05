/**
 * Title: SYSC 3110 Project
 * 
 * @author MVezina Student Number: 100934579 Team: noSquad
 */

package nullSquad.filesharingsystem.users;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.document.*;
import nullSquad.simulator.Simulator;

import java.util.*;

import nullSquad.strategies.act.ProducerActStrategy;
import nullSquad.strategies.payoff.ProducerPayoffStrategy;
import nullSquad.strategies.ranking.DocumentRankingStrategy;

/**
 * Producer class that extends User and implements the default
 * ProducerPayoffStrategy This class represents a Producer that belongs to a
 * file sharing system
 * 
 * @author MVezina
 */
public class Producer extends User implements ProducerPayoffStrategy, DocumentLikeListener
{

	private static final long serialVersionUID = -1183152556744639318L;

	public static String NODE_NAME = "producer";

	// The list of documents that the producer has produced
	private List<Document> docsProduced;

	// The Producer Payoff Strategy to be used
	private ProducerPayoffStrategy payoffStrategy;
	private ProducerActStrategy.Strategy actStrategy;

	/**
	 * Constructor that sets the Producer Payoff Strategy and Producer Act
	 * Strategy
	 * 
	 * @param payoffStrat The Payoff strategy to use
	 * @param actStrat The Producer Act Strategy to use
	 * @param docRankStrategy The Document Ranking Strategy to use
	 * @param userName The User name of the producer
	 * @param taste The taste of the producer
	 */
	public Producer(ProducerPayoffStrategy payoffStrat, ProducerActStrategy.Strategy actStrat, DocumentRankingStrategy.Strategy docRankStrategy, String userName, String taste)
	{
		this(payoffStrat, actStrat, userName, taste);

		if (docRankStrategy != null)
			super.setDocumentRankingStrategy(docRankStrategy);
	}

	/**
	 * Constructor that sets the Producer Payoff Strategy and Producer Act
	 * Strategy
	 * 
	 * @param payoffStrat The Payoff strategy to use
	 * @param actStrat The Producer Act Strategy to use
	 * @param userName The User name of the producer
	 * @param taste The taste of the producer
	 */
	public Producer(ProducerPayoffStrategy payoffStrat, ProducerActStrategy.Strategy actStrat, String userName, String taste)
	{
		this(payoffStrat, userName, taste);

		if (actStrat != null)
			this.setActStrategyEnum(actStrat);
	}

	/**
	 * Constructor that sets the Producer Payoff Strategy
	 * 
	 * @param payoffStrat The Payoff strategy to use
	 * @param userName The User name of the producer
	 * @param taste The taste of the producer
	 */
	public Producer(ProducerPayoffStrategy payoffStrat, String userName, String taste)
	{
		this(userName, taste);
		if (payoffStrat != null)
			this.payoffStrategy = payoffStrat;
	}

	/**
	 * Constructor that sets the Producer Act Strategy
	 * 
	 * @param actStrat The act strategy to use
	 * @param userName The User name of the producer
	 * @param taste The taste of the producer
	 */
	public Producer(ProducerActStrategy.Strategy actStrat, String userName, String taste)
	{
		this(userName, taste);
		if (actStrat != null)
			this.setActStrategyEnum(actStrat);
	}

	/**
	 * Default Constructor. Creates a producer with the specified userID and
	 * taste
	 * 
	 * @param userName the userID of the new user
	 * @param taste the taste of the user
	 */
	public Producer(String userName, String taste)
	{
		super(userName, taste);
		this.docsProduced = new ArrayList<Document>();

		// Sets the default strategies
		this.payoffStrategy = this;
		this.setActStrategyEnum(ProducerActStrategy.Strategy.Default);
	}

	/**
	 * The Producer Override of addFollower(..): - Requires re-calculation of
	 * Payoff after adding a follower
	 */
	@Override
	public boolean addFollower(User user)
	{
		// If added a follower succeeds, Append text to the log and return the
		// status
		if (super.addFollower(user))
		{
			Simulator.appendLineLog(this.getUserName() + " has been followed by " + user.getUserName() + ". Updated Producer Payoff: " + calculatePayoff());
			return true;
		}
		return false;
	};

	/**
	 * The Producer Acts By: - Produces a new document - Runs the specified
	 * producer act strategy - Updates the payoff
	 */
	@Override
	public void act(FileSharingSystem fileSharingSystem, int kResults)
	{
		// Ensure the user has been registered
		if (userID <= 0)
		{
			System.out.println("The User is not currently registered.");
			return;
		}

		// Create a new document and upload to the file sharing system
		produceDocument(fileSharingSystem);

		// Call the producer act strategy
		this.actStrategy.getStrategy().act(this, fileSharingSystem, kResults);

	}

	/**
	 * Produces and uploads a new document to the file sharing system
	 * 
	 * @param fileSharingSystem The file sharing system to upload the document
	 *        to
	 * @author MVezina
	 */
	private void produceDocument(FileSharingSystem fileSharingSystem)
	{
		// Create a new document
		Document newDoc = new Document("Document " + this.taste + " (" + (new Random()).nextInt(500) + ")", this.taste, this);

		// Add new document to document produced
		docsProduced.add(newDoc);

		// The document is now added to the file sharing system
		if (fileSharingSystem != null)
			fileSharingSystem.addDocument(newDoc);

		// Like your own document
		this.likeDocument(newDoc);

	}

	/**
	 * Calls the set Producer Payoff Strategy
	 * 
	 * @return The payoff of the producer using the selected strategy
	 * @author MVezina
	 */
	public int calculatePayoff()
	{
		return payoffStrategy.producerPayoffStrategy(this);
	}

	/**
	 * @return The Producer Payoff Strategy
	 * @author MVezina
	 */
	public ProducerPayoffStrategy getProducerPayoffStrategy()
	{
		return this.payoffStrategy;
	}

	/**
	 * The Default Producer payoff method uses followers/likes to calculate
	 * payoff. One point is given for every person who likes the producer's
	 * documents Two points are given for every follower the producer has
	 * 
	 * @author MVezina
	 */
	@Override
	public int producerPayoffStrategy(Producer prod)
	{
		// Two points for every follower the producer has
		int totalPayoff = prod.getFollowers().size() * 2;

		// One point for every like the producer has
		for (Document d : prod.getDocumentsProduced())
		{
			totalPayoff += d.getUserLikes().size();
		}

		return totalPayoff;
	}

	/**
	 * @return The List of Documents Produced by the producer
	 * @author MVezina
	 */
	public List<Document> getDocumentsProduced()
	{
		return this.docsProduced;
	}

	/**
	 * Sets the act strategy
	 * 
	 * @param producerActStrategy
	 */
	public void setActStrategyEnum(ProducerActStrategy.Strategy producerActStrategy)
	{
		if (producerActStrategy == null)
			return;
		this.actStrategy = producerActStrategy;
	}

	/**
	 * @return Producer act strategy enum
	 */
	public ProducerActStrategy.Strategy getActStrategyEnum()
	{
		return this.actStrategy;
	}

	@Override
	public String toString()
	{
		return "User Type: Producer (Payoff: " + calculatePayoff() + ")\n" + super.toString() + "\nDocuments Produced: " + docsProduced.size() + "\n";
	}

	@Override
	public boolean equals(Object o)
	{
		// Call the user equals method
		if (!super.equals(o))
			return false;

		// Check if the object is an instance of Producer
		if (!(o instanceof Producer))
			return false;

		// Cast to producer
		Producer p = (Producer) o;

		// Perform comparisons
		return (this.docsProduced.size() == p.docsProduced.size());

	}

	@Override
	public void DocumentLiked(DocumentLikeEvent docLikeEvent)
	{
		// Check to see if the document containing the event is owned by this
		// producer
		if (docLikeEvent.getDocument().getProducer().equals(this))
		{
			// Calculate and print the payoff
			Simulator.appendLineLog(docLikeEvent.getLikingUser().getUserName() + " has liked '" + docLikeEvent.getDocument().getDocumentName() + "'. " + docLikeEvent.getDocument().getProducer().getUserName() + " Payoff: " + calculatePayoff());
		}

	}

	@Override
	public void addIterationPayoff(int currentIteration)
	{

		// Ensures the number of iterations matches the size of the payoff list
		if (currentIteration == getPayoffHistory().size())
		{
			// Add payoff to list
			payoffHistory.add(calculatePayoff());

			// Update all payoff listeners
			for (UserPayoffListener upl : this.payoffListeners)
			{
				// Call all listener methods
				upl.payoffUpdated(new UserPayoffEvent(this));
			}
		}
	}
}
