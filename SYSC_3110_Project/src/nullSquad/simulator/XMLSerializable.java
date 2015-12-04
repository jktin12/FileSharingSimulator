/**
 * 
 */
package nullSquad.simulator;

import org.w3c.dom.Element;

/**
 * @author MVezina
 *
 */
public interface XMLSerializable
{
	public String toXML();
	public void importFromXML(Element rootNode);	
}
