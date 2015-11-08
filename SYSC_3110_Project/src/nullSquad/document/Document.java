/**
 * Title: SYSC 3110 Project
 * Created By: Marc teBoekhorst
 * Student Number: 100925246
 * Team: nullSquad
 */
package nullSquad.document;

import java.util.*;

import nullSquad.network.Producer;
import nullSquad.network.User;

/**
 * Document class represents a document that is produced by a producer
 * @author Marc Tebo
 *
 */
public class Document implements Comparable<Document>
{
	private String name;
	private String tag;
	private int docID;
	private List<User> likedBy;
	private Producer producer;
	private Date dateUploaded;
	private List<DocumentLikeListener> likeListeners;
	
	/**
	 * @author Marc Tebo
	 * Creates a Document with a name, tag and producer who created the document
	 * 
	 * @param name	Name of the document
	 * @param tag	Search tag that will be used to find this document
	 * @param producer	Creator of the document
	 * 
	 */
	public Document(String name, String tag, Producer producer)
	{
		this.name = name;
		this.tag = tag;
		this.producer = producer;
		docID = 0;
		likedBy = new ArrayList<>();
		likeListeners = new LinkedList<>();
		
		// By default, the producer listens to when the document gets a like
		likeListeners.add(producer);
		
		dateUploaded = Calendar.getInstance().getTime();
	}
	
	
	/**
	 * @param listener The listener to be added to the list
	 * @author MVezina
	 */
	public void addLikeListener(DocumentLikeListener listener)
	{
		if(!likeListeners.contains(listener))
			this.likeListeners.add(listener);
	}
	
	/**
	 * @param listener The listener to be removed from the list
	 * @return Returns whether or not the listener was removed
	 * @author MVezina
	 */
	public boolean removeLikeListener(DocumentLikeListener listener)
	{
		return likeListeners.remove(listener);
	}
	
	/**
	 * @author Marc Tebo
	 * @return List of Like Listeners
	 */
	
	public List<DocumentLikeListener> getLikeListeners(){
		return likeListeners;
	}
	
	/**
	 * @author Marc Tebo
	 * User who "likes" this document will be added to the list of people who like this document
	 * 
	 * @param user User who "likes" the document
	 * @return whether or not the document was "liked"
	 */
	public boolean likeDocument(User user)
	{
		if (likedBy.contains(user))
		{
			return false;
		}	
		likedBy.add(user);
		
		// Iterate through all like listeners and notify that this document has been liked
		for(DocumentLikeListener listener : likeListeners)
		{
			listener.DocumentLiked(new DocumentLikeEvent(this));
		}
		
		return true;
	}
	
	/**
	 * @author Marc Tebo
	 * User who wants to "unlike" this document will be removed from the list of people who liked it
	 * @param user	User who "unlikes" the document
	 * @return	whether or not the doucument was "unliked"
	 */
	public boolean unlikeDocument(User user)
	{
		if(!likedBy.contains(user))
		{
			return false;
		}
		likedBy.remove(user);
		return true;
	}
	
	/**
	 * @author Marc Tebo
	 * Gets all users who have "liked" this document
	 * @return	List of users who "liked" the document
	 */
	public List<User> getUserLikes()
	{
		return likedBy;
	}
	
	/**
	 * @author Marc Tebo
	 * Returns string representation of the document
	 */
	public String toString()
	{		
		return ("Name: " + this.name + "\nDocument ID: " + docID + "\nTag: " + this.tag + "\nProducer: " + this.producer.getUserName() + "\nDate Uploaded: " + this.dateUploaded);
		
	}
	
	/**
	 * @author Marc Tebo
	 * Compares if two documents are the same
	 * @return true if documents have the same unique bookID
	 */
	@Override
	public boolean equals(Object o)
	{
			// If the two object references are equal, return true
			if (this == o)
				return true;
			
			
			// If the object is not an instance of Document, return false
			if (!(o instanceof Document))
				return false;

			// Returns whether the two document IDs are the same
			Document d = (Document) o;
			return (d.getDocumentID() == this.docID
					&& d.getDocumentName().equals(this.name) 
					&& d.getTag().equals(this.tag)
					&& d.getProducer().equals(this.producer));

	}
	
	/**
	 * @author Marc Tebo
	 * @return	Document Tag
	 */
	public String getTag()
	{
		return tag;
	}
	
	/**
	 * @author Marc Tebo
	 * @return	Document ID
	 */
	public int getDocumentID()
	{
		return docID;
	}
	
	/**
	 * @author Marc Tebo
	 * @return	Document Upload Date
	 */
	public Date getDateUploaded()
	{
		return dateUploaded;
	}
	
	/**
	 * @return Document Name
	 */
	public String getDocumentName()
	{
		return name;
	}
	
	/**
	 * @author Justin Krol
	 * @return	Document Producer
	 */
	public Producer getProducer()
	{
		return producer;
	}
	/**
	 * Sets the document ID to the integer specified in the parameters
	 * @author Marc Tebo
	 * @param docID	the document ID that will replace the existing docID
	 */
	public void setDocumentID(int docID)
	{
		this.docID = docID;
	}
	
	/**
	 * @author Justin Krol
	 */
	public int compareTo(Document doc){
		if(this.getUserLikes().size() > doc.getUserLikes().size())
			return 1;
		else if(this.getUserLikes().size() == doc.getUserLikes().size())
			return 0;
		else
			return -1;
	}
}
