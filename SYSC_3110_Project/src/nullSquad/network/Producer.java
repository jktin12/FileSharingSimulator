/**
 * Title: SYSC 3110 Project
 * 
 * @author MVezina
 *         Student Number: 100934579
 *         Team: noSquad
 */

package nullSquad.network;


import java.util.*;

import nullSquad.document.*;
import nullSquad.strategies.ProducerPayoffStrategy;

public class Producer extends User implements ProducerPayoffStrategy,
		DocumentLikeListener
{
	// The list of documents that the producer has produced
	private List<Document>			docsProduced;

	// The Producer Payoff Strategy to be used
	private ProducerPayoffStrategy	payoffStrategy;

	public Producer(ProducerPayoffStrategy payoffStrat, String userName, String taste)
	{
		this(userName, taste);

		// Set the payoff strategy if it is not null
		if (payoffStrat != null)
			this.payoffStrategy = payoffStrat;
	}

	/**
	 * Default Constructor.
	 * Creates a producer with the specified userID and taste
	 * 
	 * @param userID the userID of the new user
	 * @param taste the taste of the user
	 */
	public Producer(String name, String taste)
	{
		super(name, taste);
		this.docsProduced = new ArrayList<Document>();

		// Sets the default payoff strategy to the interface implementation in
		// this class
		this.payoffStrategy = this;
	}

	/**
	 * The Producer Override of addFollower(..):
	 * - Requires re-calculation of Payoff after adding a follower
	 */
	@Override
	public boolean addFollower(User user)
	{
		boolean res = super.addFollower(user);
		System.out.println("A User has followed you! Your updated payoff is: " + calculatePayoff());
		return res;
	};

	/**
	 * The Producer Acts By:
	 * - Creates a new document (has same taste as Producer)
	 * - Likes newly created document
	 * - Searches network for top K documents
	 * - Likes documents with own taste
	 */
	@Override
	public void act(Network net)
	{
		// Ensure the user has been registered
		if(userID <= 0)
		{
			System.out.println("The User is not currently registered.");
			return;
		}

		// Create a new document
		Document newDoc = new Document("Document (" + Calendar.getInstance().getTime() + ")", this.taste, this);

		// Add new document to document produced
		docsProduced.add(newDoc);
		
		// The document is now added to the network
		net.addDocument(newDoc);

		// Like your own document
		this.likeDocument(newDoc);

		// Search the network for top 10 documents
		for (Document d : net.search(this, 10))
		{
			// Like all documents with the same taste as this User
			if (d.getTag().equals(taste))
			{
				this.likeDocument(d);

				// Follow all users who like this document
				for (User u : d.getUserLikes())
				{
					this.followUser(u);
				}
			}
		}

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
	 * The Default Producer payoff method uses followers/likes to calculate
	 * payoff.
	 * One point is given for every person who likes the producer's documents
	 * Two points are given for every follower the producer has
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

	/**
	 * DocumentLiked Event Handler
	 */
	@Override
	public void DocumentLiked(DocumentLikeEvent docLikeEvent)
	{
		if(docLikeEvent.getSource().getProducer().equals(this))
		{
			// Calculate and print the payoff
			System.out.println("Your Document has been liked! Current Payoff: " + calculatePayoff());
		}
		
	}

}
