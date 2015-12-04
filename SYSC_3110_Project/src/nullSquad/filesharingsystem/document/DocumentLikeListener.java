/**
 * 
 */
package nullSquad.filesharingsystem.document;

import java.io.Serializable;

/**
 * Document Like Listener Interface Interface for classes that would like to
 * know when a document is liked
 * 
 * @author MVezina
 */
public interface DocumentLikeListener extends Serializable
{
	
	public void DocumentLiked(DocumentLikeEvent docLikeEvent);
}
