/**
 * Title: SYSC 3110 Project
 * 
 * @author Justin Krol
 *         Student Number: 100941980
 *         Team: nullSquad
 */

package nullSquad.filesharingsystem;

import nullSquad.filesharingsystem.users.*;
import nullSquad.filesharingsystem.document.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * Represents the network
 * 
 * @author Justin Krol
 */
public class FileSharingSystem
{
	private int				nextAvailableUserID	= 1;
	private int				nextAvailableDocID	= 1;
	private DefaultListModel<User>		usersListModel;
	private DefaultListModel<Document>	documentsListModel;
	private List<String>	tags;
	
	/**
	 * @author Justin Krol
	 *         Creates a Network with a list of users and a list of documents
	 *         @param tags The list of tags that can be associated with a user or document
	 */
	public FileSharingSystem(List<String> tags)
	{
		usersListModel = new DefaultListModel<>();
		documentsListModel = new DefaultListModel<>();
		this.tags = tags;
	}

	/**
	 * @author Justin Krol
	 *         Add a user to the network if it has not already been added
	 * @param user who will be added to the network
	 * @return userID if the user could be added, -1 if the user could not
	 *         because it was already registered or the user argument was null
	 */
	public int registerUser(User user)
	{
		if (user != null)
		{
			if (usersListModel.contains(user))
			{
				// System.out.println("User has already been added to the network");
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
	 * @author Justin Krol
	 *         Remove a user from the network if it is registered
	 * @param user who will be removed from the network
	 * @return true if the user could be removed, false if the user could not
	 *         because it was not registered or the user argument was null
	 */
	public boolean deactivateUser(User user)
	{
		if (user != null)
		{
			if (!usersListModel.contains(user))
			{
				// System.out.println("User is not currently registered on the network");
				return false;
			}
			
			
			// Remove all documents
			
		//	user.
			
			usersListModel.removeElement(user);
			return true;
		}
		// System.out.println("No user object to remove (null)");
		return false;
	}
	
	/**
	 * Default Search: Searches for the specified tag
	 * @param user The user that will search the network
	 * @param tag The tag to search for
	 * @param topK Number of results to be returned
	 * @return The top K results from the network
	 * @author MVezina
	 */
	public List<Document> search(User user, String tag, int topK)
	{

		List<Document> documentList = new ArrayList<>();
		
		// Copy the documents over to the list
		for(int i = 0; i < documentsListModel.getSize(); i++){
			documentList.add((documentsListModel.getElementAt(i)));
		}
		
		
		if (documentList.size() < topK)
		{
			return documentList;
		}

		List<Document> topKDocuments = new ArrayList<Document>();

		for (Document doc : documentList)
		{
			// If document isnt liked by the searching user
			if (!doc.getUserLikes().contains(user))
			{
				// If tag matches users taste
				if (doc.getTag().equals(tag))
				{
					topKDocuments.add(doc);
					continue;
				}

				for (User u : user.getFollowing())	// check all users that this
													// user follows
				{
					if (doc.getUserLikes().contains(u))
					{
						topKDocuments.add(doc);
						break;
					}
				}
			}
		}

		if (topKDocuments.size() < topK)
		{
			for (Document doc : documentList)
			{
				if (!topKDocuments.contains(doc))
				{
					if (topKDocuments.size() == topK)
						break;

					topKDocuments.add(doc);
				}
			}
		}

		else if (topKDocuments.size() > topK)
		{
			Collections.sort(topKDocuments);
			Collections.reverse(topKDocuments);
			topKDocuments = topKDocuments.subList(0, topK);
		}
		
		
		// We want to update the payoff for all of the producers every time their documents are returned
		for(Document d : topKDocuments)
		{
			d.getProducer().calculatePayoff();
		}

		// After all documents have been checked, return the list of topK
		// documents
		return topKDocuments;	
	}
	
	
	/**
	 * Searches for the documents that match the taste of the user
	 * 
	 * @param user The user that will search the network
	 * @param topK Number of results to be returned
	 * @return The top K results from the network
	 * @author MVezina
	 */
	public List<Document> search(User user, int topK)
	{
		return search(user,user.getTaste(),topK);
	}

	/**
	 * @author Justin Krol
	 *         Add a document to the network if it has not already been added
	 * 
	 * @param doc The Document that will be added to the network
	 * @return true if the document could be added, false if the document could
	 *         not
	 *         because it was already added or the document argument was null
	 */
	public boolean addDocument(Document doc)
	{
		if (doc != null)
		{
			if (documentsListModel.contains(doc))
			{
				System.out.println("Document has already been added to the network");
				return false;
			}
			doc.setDocumentID(nextAvailableDocID++);
			documentsListModel.addElement(doc);
			return true;
		}
		// System.out.println("No document object to add (null)");
		return false;
	}

	/**
	 * @author Justin Krol
	 *         Remove a document from the network if it has been added
	 * 
	 * @param doc The document that will be removed from the network
	 * @return true if the document could be removed, false if the document
	 *         could not
	 *         because it was not on the network or the document argument was
	 *         null
	 */
	public boolean removeDocument(Document doc)
	{
		if (doc != null)
		{
			if (!documentsListModel.contains(doc))
			{
				// System.out.println("User is not currently registered on the network");
				return false;
			}
			documentsListModel.removeElement(doc);
			return true;
		}
		// System.out.println("No user object to remove (null)");
		return false;
	}

	/**
	 * @author Justin Krol
	 *         Getter for the documents list
	 * @return list of documents
	 */
	public List<Document> getAllDocuments()
	{
		List<Document> documentList = new ArrayList<>();
		
		// Copy the documents over to the list
		for(int i = 0; i < documentsListModel.getSize(); i++){
			documentList.add((documentsListModel.getElementAt(i)));
		}
		
		return documentList;
	}

	/**
	 * Gets the Document List Model
	 * @author MVezina
	 * @return Document List Model
	 */
	
	public DefaultListModel<Document> getDocumentsListModel()
	{
		return this.documentsListModel;
	}
	
	
	/**
	 * Gets the User List Model
	 * @author MVezina
	 * @return User List Model
	 */
	
	public DefaultListModel<User> getUsersListModel()
	{
		return this.usersListModel;
	}
	
	/**
	 * @author Justin Krol
	 *         Getter for the list of users
	 * @return list of users
	 */
	public List<User> getUsers()
	{
		List<User> userList = new ArrayList<>();
		
		// Copy the documents over to the list
		for(int i = 0; i < usersListModel.getSize(); i++){
			userList.add((usersListModel.getElementAt(i)));
		}
		
		return userList;
	}

	public List<String> getTags() {
		return tags;
	}

}
