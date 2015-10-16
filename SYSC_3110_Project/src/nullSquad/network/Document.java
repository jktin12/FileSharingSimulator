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
	 * Creates a Document with a name, tag and producer who created the document
	 * 
	 * @param name	Name of the document
	 * @param tag	Search tag that will be used to find this document
	 * @param producer	Creator of the document
	 */
	public Document(String name, String tag, Producer producer)
	{
		this.name = name;
		this.tag = tag;
		this.producer = producer;
		
		likedBy = new ArrayList<>();
		dateUploaded = Calendar.getInstance().getTime();
	}
	
	/**
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
	 * Gets all users who have "liked" this document
	 * @return	List of users who "liked" the document
	 */
	public List<User> allLikers()
	{
		return likedBy;
	}
	
	/**
	 * Returns string representation of the document
	 */
	public String toString()
	{		
		return ("Name: " + this.name + "\nTag: " + this.tag + "\nProducer:  " + this.producer + "\nDate Uploaded: " + this.dateUploaded);
		
	}
	
	/**
	 * Compares if two documents are the same
	 * @returns true if documents have the same unique bookID
	 */
	public boolean equals(Object o)
	{
		if(this.equals(o))
			return true;
		if(!(o instanceof Document))
			return false;
		else
		{	
			Document d = (Document)o;
			if(d.docID == this.docID)
				return true;
		}
		return false;
	}
	
	/**
	 * @return	Document Tag
	 */
	public String getTag()
	{
		return tag;
	}
	
	/**
	 * 
	 * @return	Document ID
	 */
	public int getDocID()
	{
		return docID;
	}
	
	/**
	 * 
	 * @return	Document Upload Date
	 */
	public Date getDate()
	{
		return dateUploaded;
	}

}
