/**
 * Document Like Event Object
 * 
 * @author MVezina
 */
package nullSquad.filesharingsystem.document;

import nullSquad.filesharingsystem.users.*;

/**
 * Represents the event of a user liking a document
 * 
 * @author MVezina
 */
public class DocumentLikeEvent
{
	private Document documentSource;
	private User user;

	public DocumentLikeEvent(Document source, User user)
	{
		this.documentSource = source;
		this.user = user;
	}

	/**
	 * @return The Document that was liked
	 */
	public Document getDocument()
	{
		return this.documentSource;
	}

	/**
	 * @return The user who liked the document
	 */
	public User getLikingUser()
	{
		return user;
	}
}
