/**
 * Title: SYSC 3110 Project
 * 
 * @author MVezina Student Number: 100934579 Team: noSquad
 */

package nullSquad.filesharingsystem.users;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.document.*;
import nullSquad.simulator.gui.SimulatorGUI;
import nullSquad.strategies.payoff.ConsumerPayoffStrategy;
import nullSquad.strategies.ranking.DocumentRankingStrategy;

import java.util.*;

/**
 * Producer class that extends User and implements the default
 * ProducerPayoffStrategy This class represents a Producer that belongs to a
 * file sharing system
 * 
 * @author MVezina
 */
public class Consumer extends User implements ConsumerPayoffStrategy
{

	// The Consumer Payoff Strategy to be used
	private ConsumerPayoffStrategy payoffStrategy;
	private int currentPayoff;

	/**
	 * Calls the default constructor and sets the Consumer Payoff Strategy
	 * 
	 * @param payoffStrat The payoff Strategy to be used
	 * @param userName The name of the user
	 * @param taste The taste of the user
	 */
	public Consumer(ConsumerPayoffStrategy payoffStrat, String userName, String taste)
	{
		// Call the default Constructor
		this(userName, taste);

		// Set the payoff Strategy if it is not null
		if (payoffStrat != null)
			this.payoffStrategy = payoffStrat;
	}
	
	/**
	 * Calls the default constructor and sets the Consumer Payoff Strategy and Ranking Strategy
	 * 
	 * @param payoffStrat The payoff Strategy to be used
	 * @param rankingStrat The Document Ranking Strategy to Set
	 * @param userName The name of the user
	 * @param taste The taste of the user
	 */
	public Consumer(ConsumerPayoffStrategy payoffStrat, DocumentRankingStrategy.Strategy rankingStrat, String userName, String taste)
	{
		// Call the default Constructor
		this(payoffStrat, userName, taste);
		super.setDocumentRankingStrategy(rankingStrat);

		// Set the payoff Strategy if it is not null
		if (payoffStrat != null)
			this.payoffStrategy = payoffStrat;
	}

	/**
	 * Default Constructor. Sets the default Consumer Payoff Strategy (the
	 * payoffStrategy(..) method defined in this class)
	 * 
	 * @param userName The User's Name
	 * @param taste The taste of the Consumer
	 */
	public Consumer(String userName, String taste)
	{
		super(userName, taste);
		this.currentPayoff = 0;
		this.payoffStrategy = this;
	}

	@Override
	public void act(FileSharingSystem fileSharingSystem, int kResults)
	{
		// Ensure the user has been registered
		if (userID < 1)
		{
			System.out.println("The User is not currently registered.");
			return;
		}

		// Searches for the top k results (k = random number between 1-10)
		List<Document> documentResults = fileSharingSystem.search(this, (new Random()).nextInt(10) + 1);

		// Calculates the payoff of the search results
		int payoff = calculatePayoff(documentResults);

		SimulatorGUI.appendLog("Search: Returned " + documentResults.size() + " documents (Result Payoff: " + payoff + ")");

		// Like all documents that match the users taste
		for (Document d : documentResults)
		{
			if (d.getTag().equals(taste))
			{
				// Likes the document
				this.likeDocument(d);
			}
		}

		// Set the current payoff for the user
		this.currentPayoff += payoff;

	}

	/**
	 * Calculates the payoff of the returned documents using the set strategy.
	 * This method will be used to call the strategy and perform any pre/post
	 * operations
	 * 
	 * @param documents the documents returned by the file sharing System search
	 * @return the value of the calculated payoff
	 * @author MVezina
	 */
	public int calculatePayoff(List<Document> documents)
	{
		return payoffStrategy.consumerPayoffStrategy(this, documents);
	}

	// Default Method Implementation of Consumer Payoff Strategy
	@Override
	public int consumerPayoffStrategy(Consumer consumer, List<Document> documentSearchResults)
	{
		int totalPayoff = 0;
		if (documentSearchResults == null)
		{
			return totalPayoff;
		}

		for (Document d : documentSearchResults)
		{
			// One point is associated with if the user's taste matches the
			// document tag
			if (d.getTag().equals(consumer.getTaste()))
			{
				totalPayoff++;
			}

		}

		return totalPayoff;
	}

	/**
	 * Override toString Returns User Type and super class String representation
	 */
	@Override
	public String toString()
	{
		// Print User Type and the basic fields of a user
		return "User Type: Consumer\n" + super.toString() + "\n";
	}

	/**
	 * Override equals Determines whether or not the object and this consumer
	 * are equal
	 */
	@Override
	public boolean equals(Object o)
	{
		// If o is an instance of Consumer and the superclass method of equals
		// returns true, the two Consumers are equal
		return (super.equals(o) && o instanceof Consumer);

	}

	@Override
	public void addIterationPayoff(int currentIteration)
	{
		// Only add a payoff iteration if the history is not updated
		if (currentIteration == getPayoffHistory().size())
		{
			this.payoffHistory.add(currentPayoff);

			for (UserPayoffListener upl : this.payoffListeners)
			{
				upl.payoffUpdated(new UserPayoffEvent(this));
			}

		}
	}

}
