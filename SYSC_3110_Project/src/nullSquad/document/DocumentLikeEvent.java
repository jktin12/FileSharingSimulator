/**
 * Document Like Event Object
 * 
 * @author MVezina
 */
package nullSquad.document;


/**
 * Represents the event of a user liking a document
 * 
 * @author MVezina
 */
public class DocumentLikeEvent
{
	private Document	documentSource;

	public DocumentLikeEvent(Document source)
	{
		this.documentSource = source;
	}

	public Document getSource()
	{
		return this.documentSource;
	}
}
