/**
 * Title: SYSC 3110 Project
 * Created By: Marc teBoekhorst
 * Student Number: 100925246
 * Team: nullSquad
 */
package nullSquad.network;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Document
{
	private String name;
	private String tag;
	private int docID;
	private List<User> likedBy;
	private Producer producer;
	private Date dateUploaded;
	
	/**
	 * @author Marc Tebo
	 * Creates a Document with a name, tag and producer who created the document
	 * 
	 * @param name	Name of the document
	 * @param tag	Search tag that will be used to find this document
	 * @param producer	Creator of the document
	 * 
	 */
	public Document(String name, String tag, Producer producer, int docID)
	{
		this.name = name;
		this.tag = tag;
		this.producer = producer;
		this.docID = docID;
		likedBy = new ArrayList<>();
		dateUploaded = Calendar.getInstance().getTime();
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
		return ("Name: " + this.name + "\nTag: " + this.tag + "\nProducer:  " + this.producer + "\nDate Uploaded: " + this.dateUploaded);
		
	}
	
	/**
	 * @author Marc Tebo
	 * Compares if two documents are the same
	 * @returns true if documents have the same unique bookID
	 */
	public boolean equals(Object o)
	{
			// If the two object references are equal, return true
			if (this == o)
				return true;
			
			
			// If the object is not an instance of Document, return false
			if (!(o instanceof Document))
				return false;

			// Returns whether the two document IDs are the same
			return (((Document) o).docID == this.docID);

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

}
