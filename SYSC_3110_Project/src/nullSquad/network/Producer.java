package nullSquad.network;

import java.util.*;

public class Producer extends User
{
	private List<Document>	docsProduced;

	public Producer(int userID, String taste)
	{
		super(userID, taste);
		this.docsProduced = new ArrayList<>();
	}

	@Override
	public void act(Network net)
	{
		// We want to iterate through all documents and like any that match our taste
		for (Document d : net.getAllDocuments())
		{
			// Like a document if it matches our taste or it is a document that we produced
			if (d.getTag() == super.taste || d.getProducer().equals(this))
			{
				d.likeDocument(this);
			}
		}
		
		
	}

}
