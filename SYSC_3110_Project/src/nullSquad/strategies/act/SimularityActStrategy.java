package nullSquad.strategies.act;

import java.util.List;
import java.util.Random;

import nullSquad.filesharingsystem.users.*;
import nullSquad.simulator.gui.SimulatorGUI;
import nullSquad.filesharingsystem.document.*;
import nullSquad.filesharingsystem.*;

public class SimularityActStrategy implements ProducerActStrategy {

	
	@Override
	public void act(Producer producer, FileSharingSystem fileSharingSystem, int kResults) {
	
		// Ensure the fss or producer parameters are not null
		if(fileSharingSystem == null || producer == null)
			return;
		
		
		// Select a new taste from available tags
		String newTaste = fileSharingSystem.getTags().get((new Random()).nextInt(fileSharingSystem.getTags().size()));
		
		// Keep finding new tags until the tag does not match the producers taste
		// Make sure that the number of tags is greater than 1!
		while(newTaste.equals(producer.getTaste()) && fileSharingSystem.getTags().size() > 1)
		{
			newTaste = fileSharingSystem.getTags().get((new Random()).nextInt(fileSharingSystem.getTags().size()));
		}
		
		
		// Search the file sharing system for the new tag
		List<Document> documentResults = fileSharingSystem.search(producer, newTaste, kResults);
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
