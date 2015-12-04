/**
 * 
 */
package nullSquad.simulator;

/**
 * @author MVezina
 *
 */
public interface XMLSerializable
{
	public String toXML();
	public void importFromXML(String xmlObject);	
}
