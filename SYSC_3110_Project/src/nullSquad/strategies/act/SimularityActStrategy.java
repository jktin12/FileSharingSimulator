package nullSquad.strategies.act;

import java.util.List;
import java.util.Random;

import nullSquad.filesharingsystem.users.*;
import nullSquad.simulator.gui.SimulatorGUI;
import nullSquad.filesharingsystem.document.*;
import nullSquad.filesharingsystem.*;

public class SimularityActStrategy implements ProducerActStrategy {

	
	@Override
	public void act(Producer producer, FileSharingSystem network, int kResults) {
		
		// Select a new taste from available tags
		String newTaste = network.getTags().get((new Random()).nextInt(network.getTags().size()));
		
		// Keep finding new tags until the tag does not match the producers taste
		// Make sure that the number of tags is greater than 1!
		while(newTaste.equals(producer.getTaste()) && network.getTags().size() > 1)
		{
			newTaste = network.getTags().get((new Random()).nextInt(network.getTags().size()));
		}
		
		
		// Search the network for the new tag
		List<Document> documentResults = network.search(producer, newTaste, kResults);
		SimulatorGUI.appendLog("Search: Returned " + documentResults.size() + " documents");
		
		for(Document d : documentResults)
		{
			// Likes all documents matching the tag
			producer.likeDocument(d);
			
			// Follows all users who have liked the document
			for(User u : d.getUserLikes())
			{
				producer.followUser(u);
			}
		}
	}	

}
