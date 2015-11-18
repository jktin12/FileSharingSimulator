package nullSquad.strategies;

import nullSquad.filesharingsystem.users.*;
import nullSquad.filesharingsystem.document.*;
import nullSquad.filesharingsystem.*;



public class DefaultProducerActStrategy implements ProducerActStrategy{
	
	
	public void act(Producer producer, FileSharingSystem network, int kResults)
	{
		// Search the network for top 10 documents
		for (Document d : network.search(producer, kResults))
		{
			// Like all documents with the same taste as this User
			if (d.getTag().equals(producer.getTaste()))
			{
				producer.likeDocument(d);

				// Follow all users who like this document
				for (User u : d.getUserLikes())
				{
					producer.followUser(u);
				}
			}
		}
	}
}
