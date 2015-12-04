/**
 * 
 */
package nullSquad.simulator;

import org.w3c.dom.Node;

/**
 * @author MVezina
 *
 */
public interface XMLSerializable
{
	public String toXML();
	public void importFromXML(Node rootNode);	
}
