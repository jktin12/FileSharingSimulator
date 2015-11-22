package nullSquad.strategies.act;

import nullSquad.filesharingsystem.users.*;
import nullSquad.simulator.gui.SimulatorGUI;
import nullSquad.filesharingsystem.document.*;

import java.util.*;

import nullSquad.filesharingsystem.*;



public class DefaultProducerActStrategy implements ProducerActStrategy{
	
	
	public void act(Producer producer, FileSharingSystem fileSharingSystem, int kResults)
	{
		if(fileSharingSystem == null || producer == null)
			return;
		
		List<Document> documentResults = fileSharingSystem.search(producer, kResults);
		
		
		
		// Append Results to log
		SimulatorGUI.appendLog("Search: Returned " + documentResults.size() + " documents");
		
		// Search the file sharing system for top K documents
		for (Document d : documentResults)
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
