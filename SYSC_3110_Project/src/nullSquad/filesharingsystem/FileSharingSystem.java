/**
  * Title: SYSC 3110 Project
 * 
 * @author Justin Krol Student Number: 100941980 Team: nullSquad
 */

package nullSquad.filesharingsystem;

import nullSquad.filesharingsystem.users.*;
import nullSquad.simulator.gui.SimulatorGUI;
import nullSquad.filesharingsystem.document.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * Represents the FileSharingSystem
 * 
 * @author Justin Krol
 */
public class FileSharingSystem
{
	private int nextAvailableUserID = 1;
	private int nextAvailableDocID = 1;
	private DefaultListModel<User> usersListModel;
	private DefaultListModel<Document> documentsListModel;
	private List<String> tags;

	/**
	 * @author Justin Krol Creates a File Sharing System with a list of users
	 *         and a list of documents
	 * @param tags The list of tags that can be associated with a user or
	 *        document
	 */
	public FileSharingSystem(List<String> tags)
	{
		usersListModel = new DefaultListModel<>();
		documentsListModel = new DefaultListModel<>();
		this.tags = tags;
	}

	/**
	 * @author Justin Krol Add a user to the File Sharing System if it has not
	 *         already been added
	 * @param user who will be added to the File Sharing System
	 * @return userID if the user could be added, -1 if the user could not
	 *         because it was already registered or the user argument was null
	 */
	public int registerUser(User user)
	{
		if (user != null)
		{
			if (usersListModel.contains(user))
			{
				// System.out.println("User has already been added to the
				// File Sharing System");
				return -1;
			}
			usersListModel.addElement(user);
			nextAvailableUserID++;
			return nextAvailableUserID - 1;
		}
		// System.out.println("No user object to register (null)");
		return -1;
	}

	/**
	 * @author Justin Krol Remove a user from the File Sharing System if it is
	 *         registered
	 * @param user who will be removed from the File Sharing System
	 * @return true if the user could be removed, false if the user could not
	 *         because it was not registered or the user argument was null
	 */
	public boolean deactivateUser(User user)
	{
		if (user == null || !usersListModel.contains(user))
			return false;

		// Remove all documents
		if (user instanceof Producer)
		{
			Producer producer = (Producer) user;

			// Remove all produced documents
			for(int i = 0; i < producer.getDocumentsProduced().size(); i++)
			{
				this.removeDocument(producer.getDocumentsProduced().get(i));
			}
		}

		// Unlike all documents
		// DO NOT USE FOREACH LOOP - ConcurrencyException
		// (Modifies the list that is being iterated)
		for (int i = 0; i < user.getLikedDocuments().size(); i++)
		{
			user.unlikeDocument(user.getLikedDocuments().get(i));
		}

		// Unfollow all users
		for (int i = 0; i < user.getFollowing().size(); i++)
		{
			user.unfollowUser(user.getFollowing().get(i));
		}

		// Remove all followers
		for (int i = 0; i < user.getFollowers().size(); i++)
		{
			user.getFollowers().get(i).unfollowUser(user);
		}

		usersListModel.removeElement(user);

		SimulatorGUI.appendLog("File Sharing System: User " + user.getUserName() + " has been removed from the File Sharing System");
		return true;

	}

	/**
	 * Default Search Method: Searches for the specified tag
	 * 
	 * @param user The user that will search the File Sharing System
	 * @param tag The tag to search for
	 * @param topK Number of results to be returned
	 * @return The top K results from the File Sharing System
	 * @author MVezina
	 */
	public List<Document> search(User user, String tag, int topK)
	{
		// Return a blank list if topK = 0
		if (topK == 0)
			return new ArrayList<>();

		// If topK is invalid (ex: -1), change it to the absolute value
		if (topK < 0)
			topK = Math.abs(topK);

		SimulatorGUI.appendLog("User: " + user.getUserName() + " has Searched for " + topK + " documents with Tag: " + tag);

		List<Document> documentList = new ArrayList<>();

		// Copy the documents (matching tag) from the list model over to a list
		// so it can be ranked. (We want to rank matching tags first)
		for (int i = 0; i < documentsListModel.getSize(); i++)
		{
			if (documentsListModel.getElementAt(i).getTag().equals(tag))
			{
				documentList.add((documentsListModel.getElementAt(i)));
			}

		}

		// Get a list of ranked documents
		List<Document> rankedDocuments = user.getSearchStrategyEnum().getStrategy().rankDocuments(documentList, user);

		// Only maintain the top k documents
		if (rankedDocuments.size() >= topK)
		{
			rankedDocuments = rankedDocuments.subList(0, topK);
		
		}
		// If there are spots that need to be filled in to match topK
		// and there are un-ranked documents, we want to add filler documents
		else if (documentsListModel.size() - rankedDocuments.size() > 0)
		{
			

			// If the amount of ranked documents is less than size topK and
			// there are documents that were not ranked we want to fill in the
			// rest of the documents
			List<Document> searchFiller = new ArrayList<>();

			// Copy the documents (not matching tag) from the list model over to
			// a list so it can be ranked. (We want to rank non-matching tags
			// last - after matching tags)
			for (int i = 0; i < documentsListModel.getSize(); i++)
			{
				if (!documentsListModel.getElementAt(i).getTag().equals(tag))
				{
					searchFiller.add((documentsListModel.getElementAt(i)));
				}
			}

			// We want to add the ranked (non tag-matching) documents to the end
			// (after ranked tag-matching documents)

			// Determine where to cut off the list (so that we can match topK
			// documents)
			int endIndex = Math.min(searchFiller.size(), topK - rankedDocuments.size());
			rankedDocuments.addAll(user.getSearchStrategyEnum().getStrategy().rankDocuments(searchFiller, user).subList(0, endIndex));

		}
		
		
		// We want to update the payoff for all of the producers every time
		// their documents are returned
		for (Document d : rankedDocuments)
		{
			d.getProducer().calculatePayoff();
		}

		return rankedDocuments;
	}

	/**
	 * Searches for the documents that match the taste of the user
	 * 
	 * @param user The user that will search the File Sharing System
	 * @param topK Number of results to be returned
	 * @return The top K results from the File Sharing System
	 * @author MVezina
	 */
	public List<Document> search(User user, int topK)
	{
		return search(user, user.getTaste(), topK);
	}

	/**
	 * @author Justin Krol Add a document to the File Sharing System if it has
	 *         not already been added
	 * @param doc The Document that will be added to the File Sharing System
	 * @return true if the document could be added, false if the document could
	 *         not because it was already added or the document argument was
	 *         null
	 */
	public boolean addDocument(Document doc)
	{
		if (doc != null)
		{
			if (documentsListModel.contains(doc))
			{
				System.out.println("Document has already been added to the File Sharing System");
				return false;
			}

			doc.setDocumentID(nextAvailableDocID++);
			documentsListModel.addElement(doc);
			SimulatorGUI.appendLog("File Sharing System: Document '" + doc.getDocumentName() + "' has been uploaded");

			return true;
		}

		return false;
	}

	/**
	 * @author Justin Krol Remove a document from the File Sharing System if it
	 *         has been added
	 * @param doc The document that will be removed from the File Sharing System
	 * @return true if the document could be removed, false if the document
	 *         could not because it was not on the File Sharing System or the
	 *         document argument was null
	 */
	public boolean removeDocument(Document doc)
	{
		if (doc != null)
		{
			if (!documentsListModel.contains(doc))
			{
				// System.out.println("User is not currently registered on the
				// File Sharing System");
				return false;
			}

			// Remove the document from all users 'likes'
			for(int i = 0; i< doc.getUserLikes().size(); i++)
			{
				doc.getUserLikes().get(i).unlikeDocument(doc);
			}

			documentsListModel.removeElement(doc);
			return true;
		}
		// System.out.println("No user object to remove (null)");
		return false;
	}

	/**
	 * @author Justin Krol Getter for the documents list
	 * @return list of documents
	 */
	public List<Document> getAllDocuments()
	{
		List<Document> documentList = new ArrayList<>();

		// Copy the documents over to the list
		for (int i = 0; i < documentsListModel.getSize(); i++)
		{
			documentList.add((documentsListModel.getElementAt(i)));
		}

		return documentList;
	}

	/**
	 * Gets the Document List Model
	 * 
	 * @author MVezina
	 * @return Document List Model
	 */
	public DefaultListModel<Document> getDocumentsListModel()
	{
		return this.documentsListModel;
	}

	/**
	 * Gets the User List Model
	 * 
	 * @author MVezina
	 * @return User List Model
	 */

	public DefaultListModel<User> getUsersListModel()
	{
		return this.usersListModel;
	}

	/**
	 * @author Justin Krol Getter for the list of users
	 * @return list of users
	 */
	public List<User> getUsers()
	{
		List<User> userList = new ArrayList<>();

		// Copy the documents over to the list
		for (int i = 0; i < usersListModel.getSize(); i++)
		{
			userList.add((usersListModel.getElementAt(i)));
		}

		return userList;
	}

	/**
	 * Add a new tag to the File Sharing System
	 * 
	 * @param newTag The new tag to add
	 */
	public void addTag(String newTag)
	{
		if (!tags.contains(newTag.trim()) && !newTag.trim().isEmpty())
		{
			tags.add(newTag.trim());
		}
	}

	/**
	 * Add a new tag to the File Sharing System
	 * 
	 * @param tag The new tag to add
	 */
	public boolean removeTag(String tag)
	{
		return tags.remove(tag.trim());
	}

	/**
	 * @return A List of Tags
	 */
	public List<String> getTags()
	{
		return tags;
	}

}
