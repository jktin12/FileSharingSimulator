/**
 * 
 */
package nullSquad.simulator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author MVezina
 */
public class StringOutputStream extends OutputStream
{
	private String outputString;
	public static final String nullIdentifier = "0NULL0";

	/**
	 * 
	 */
	public StringOutputStream()
	{
		super();
		this.outputString = "";
	}

	/* (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(int) */
	@Override
	public void write(int b) throws IOException
	{
		if (b == 0)
			outputString += nullIdentifier;
		else
			outputString += ((char)b);
	}
	
	public String toString()
	{
		return outputString;
	}

}
