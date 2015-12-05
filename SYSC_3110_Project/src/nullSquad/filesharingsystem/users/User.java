/**
 * Title: SYSC 3110 Project
 * 
 * @author MVezina Student Number: 100934579 Team: noSquad
 */

package nullSquad.filesharingsystem.users;

import java.io.Serializable;
import java.util.*;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.document.*;
import nullSquad.strategies.ranking.DocumentRankingStrategy;

/**
 * Abstract class that represents a User
 * 
 * @author MVezina
 */
public abstract class User implements Serializable
{
	private static final long serialVersionUID = -2111365206540720745L;
	private DocumentRankingStrategy.Strategy searchStrategy;
	protected int userID;
	protected String userName;
	protected List<User> followers;
	protected List<User> following;
	protected List<Document> likedDocuments;
	protected String taste;
	protected List<Integer> payoffHistory;
	protected List<UserPayoffListener> payoffListeners;

	/**
	 * Creates a user with the specified taste
	 * 
	 * @param userName The user's name
	 * @param taste The taste of the user
	 * @author MVezina
	 */
	public User(String userName, String taste)
	{
		if (userName == null)
			userName = "";

		if (taste == null)
			taste = "";

		// Initialize default values
		this.userID = 0;
		this.userName = userName;
		this.taste = taste;
		followers = new ArrayList<>();
		following = new ArrayList<>();
		likedDocuments = new ArrayList<>();
		payoffHistory = new ArrayList<>();
		payoffListeners = new ArrayList<>();

		// Sets the default ranking strategy
		this.setDocumentRankingStrategy(DocumentRankingStrategy.Strategy.DocumentPopularity);

	}

	/**
	 * Adds the current iteration payoff to the payoff History
	 * 
	 * @param currentIteration The current iteration of the simulator
	 * @author MVezina
	 */
	public abstract void addIterationPayoff(int currentIteration);

	/**
	 * Returns the history of the user's payoff
	 * 
	 * @return The List of integers representing the user's payoff
	 * @author MVezina
	 */
	public List<Integer> getPayoffHistory()
	{
		return payoffHistory;
	}

	/**
	 * Add Payoff Listener
	 * 
	 * @param payoffListener The Listener to add
	 */
	public void addPayoffListener(UserPayoffListener payoffListener)
	{
		// Do not add the listener if already contained in the list
		if (payoffListener == null || this.payoffListeners.contains(payoffListener))
			return;

		// Add the payoff listener
		this.payoffListeners.add(payoffListener);
	}

	/**
	 * The action that is run by the user when the simulator calls it.
	 * Subclasses will override this method, but must call this first
	 * 
	 * @param fileSharingSystem The main FileSharingSystem for the simulation
	 * @param kResults The number of results to search for
	 * @author MVezina
	 */
	public abstract void act(FileSharingSystem fileSharingSystem, int kResults);

	/**
	 * Registers the user with the specified FileSharingSystem
	 * 
	 * @param fileSharingSystem The FileSharingSystem to register the user on
	 * @author MVezina
	 */
	public void registerUser(FileSharingSystem fileSharingSystem)
	{
		// Registers this user with the file sharing system
		// Registering a user returns the userID
		if (userID <= 0)
			this.userID = fileSharingSystem.registerUser(this);
	}

	/**
	 * Called When the User wants to like a document
	 * 
	 * @param doc The Document that the user is going to like
	 * @return Whether or not the document was added successfully
	 * @author MVezina
	 */
	public boolean likeDocument(Document doc)
	{
		if (doc == null)
			return false;

		// Check to see if the document already exists in the list
		if (likedDocuments.contains(doc))
			return false;

		// Adds the document to the list of liked documents
		likedDocuments.add(doc);

		// Adds this user to the list of users who like the document and return
		// the result
		return doc.likeDocument(this);
	}

	/**
	 * Unlikes a document
	 * 
	 * @param doc the document to be unliked
	 * @return Returns whether or not the document was unliked successfully
	 * @author MVezina
	 */
	public boolean unlikeDocument(Document doc)
	{
		if (doc == null)
			return false;

		// Checks to make sure that the document has been liked before unliking
		// the document
		if (!likedDocuments.contains(doc))
			return false;

		// Removes the document from the list of liked documents and tells the
		// document that the user no longer likes it
		return likedDocuments.remove(doc) && doc.unlikeDocument(this);
	}

	/**
	 * Call the User's selected ranking strategy
	 * 
	 * @param allDocuments The documents to be ranked
	 * @return The Documents ranked according to the User's selected strategy
	 * @author MVezina
	 */
	public List<Document> rankDocuments(List<Document> allDocuments)
	{
		return this.getDocumentRankingStrategy().getStrategy().rankDocuments(allDocuments, this);
	}

	/**
	 * Adds a user to the list of followers
	 * 
	 * @param user The user to add the list of followers
	 * @return Whether or not the follower was added
	 * @author MVezina
	 */
	protected boolean addFollower(User user)
	{
		if (user == null || user.equals(this))
			return false;

		// Checks to see if the user is already following this user
		if (followers.contains(user))
			return false;

		// Add the user to the list of people who are following this user
		return followers.add(user);
	}

	/**
	 * Removes the Follower from the list of followers
	 * 
	 * @param user The User to remove as a follower
	 * @return Whether or not the follower was removed successfully
	 * @author MVezina
	 */
	protected boolean removeFollower(User user)
	{
		if (user == null)
			return false;

		// Returns whether or not the user was removed
		return followers.remove(user);
	}

	/**
	 * Follows the specified user
	 * 
	 * @param user the user to be followed
	 * @return Returns whether or not the user was followed successfully
	 * @author MVezina
	 */
	public boolean followUser(User user)
	{

		if (user == null)
			return false;

		// Checks to see if the specified user can be followed
		// Checks to see if user is equal to this user
		if (!user.addFollower(this) || equals(user))
			return false;

		// Add the user to the list of users being followed by this user
		return this.following.add(user);
	}

	public DocumentRankingStrategy.Strategy getDocumentRankingStrategy()
	{
		return searchStrategy;
	}

	/**
	 * Sets the Search Strategy
	 * 
	 * @param searchStrategy The Search Ranking Strategy to use
	 */
	public void setDocumentRankingStrategy(DocumentRankingStrategy.Strategy searchStrategy)
	{
		if (searchStrategy != null)
			this.searchStrategy = searchStrategy;
	}

	/**
	 * Gets the User ID of the user
	 * 
	 * @return the user ID
	 * @author MVezina
	 */
	public int getUserID()
	{
		return this.userID;
	}

	/**
	 * Gets the user name
	 * 
	 * @return the user name
	 * @author MVezina
	 */
	public String getUserName()
	{
		return this.userName;
	}

	/**
	 * @return The taste of the user
	 * @author MVezina
	 */
	public String getTaste()
	{
		return this.taste;
	}

	/**
	 * Gets all of the users that are currently following this user
	 * 
	 * @return The list of followers
	 * @author MVezina
	 */
	public List<User> getFollowers()
	{
		return this.followers;
	}

	/**
	 * Gets all of the users that this user is following
	 * 
	 * @return The list of users being followed by this user
	 * @author MVezina
	 */
	public List<User> getFollowing()
	{
		return this.following;
	}

	/**
	 * Gets all of the documents liked by this user
	 * 
	 * @return The List of documents liked by this user
	 * @author MVezina
	 */
	public List<Document> getLikedDocuments()
	{
		return this.likedDocuments;
	}

	/**
	 * Unfollows the specified user
	 * 
	 * @param user The user to unfollow
	 * @return Whether or not the user was unfollowed successfully
	 * @author MVezina
	 */
	public boolean unfollowUser(User user)
	{
		// Remove user from following list
		return user.removeFollower(this) && this.following.remove(user);
	}

	/**
	 * Overrides the equals method (from Object) Checks to see if two user
	 * objects are the same
	 * 
	 * @param o The object to compare to
	 * @return Returns whether or not two objects are the same
	 * @author MVezina
	 */
	@Override
	public boolean equals(Object o)
	{
		// If the address of the two objects is the same, they are equivalent
		if (this == o)
			return true;

		// If the object is not an instance of user, return false
		if (!(o instanceof User))
			return false;

		// Cast object to user
		User u = (User) o;

		// If either IDs are set, compare by ID.
		// If one is set, and the other is unset, return false (!=)
		// If they are different, return false (!=)
		// If they are both not set (userID == 0), then compare by other fields
		if (userID > 0 || u.getUserID() > 0)
			return (this.userID == ((User) o).userID);

		// else compare by other fields (number of following, number of
		// followers, taste of user, number of liked documents
		return (this.userName.equals(u.userName) && this.likedDocuments.size() == u.getLikedDocuments().size() && this.followers.size() == u.getFollowers().size() && this.following.size() == u.getFollowing().size() && this.taste.equals(u.getTaste()));

	}

	/**
	 * Gets the string representation of the user
	 * 
	 * @return Returns a string representation of this user
	 * @author MVezina
	 */
	@Override
	public String toString()
	{
		return "User ID: " + this.userID + "\nUser Name: " + this.userName + "\nTaste: " + this.taste + "\nFollowers: " + followers.size() + "\nFollowing: " + this.following.size() + "\nNumber of Documents Liked: " + this.likedDocuments.size();
	}

}
