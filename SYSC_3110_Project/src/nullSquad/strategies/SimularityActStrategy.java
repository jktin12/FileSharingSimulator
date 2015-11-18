package nullSquad.strategies;

import java.util.List;
import java.util.Random;

import nullSquad.filesharingsystem.users.*;
import nullSquad.filesharingsystem.document.*;
import nullSquad.filesharingsystem.*;

public class SimularityActStrategy implements ProducerActStrategy {

	
	@Override
	public void act(Producer producer, FileSharingSystem network, int kResults) {
		
		
		String newTaste = network.getTags().get((new Random()).nextInt(network.getTags().size()));
		
		
		while(newTaste.equals(producer.getTaste()) && network.getTags().size() > 1)
		{
			newTaste = network.getTags().get((new Random()).nextInt(network.getTags().size()));
		}
		
		List<Document> documents = network.search(producer, newTaste, kResults);
		for(Document d : documents)
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
