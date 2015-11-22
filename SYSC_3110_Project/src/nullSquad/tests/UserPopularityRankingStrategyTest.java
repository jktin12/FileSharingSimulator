/**
 * 
 */
package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nullSquad.filesharingsystem.FileSharingSystem;
import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.*;
import nullSquad.strategies.ranking.UserPopularityRankingStrategy;

/**
 * @author MVezina
 */
public class UserPopularityRankingStrategyTest
{
	private Producer producer1;

	private Consumer user1;
	private Consumer user2;
	private Consumer user3;
	private Consumer user4;

	/**
	 * @throws java.lang.Exception
	 * @author MVezina
	 */
	@Before
	public void setUp() throws Exception
	{
		producer1 = new Producer("PRODUCER1", "TEST");
		
		List<String> tags = new ArrayList<>();
		
		FileSharingSystem fss = new FileSharingSystem(tags);
		
		// Call producer act so that they create two documents
		producer1.act(fss, 0);
		producer1.act(fss, 0);

		if (producer1.getDocumentsProduced().size() == 2)
		{
			Document doc1 = producer1.getDocumentsProduced().get(0);
			Document doc2 = producer1.getDocumentsProduced().get(1);
			
			
			user1 = new Consumer("USER1", "TEST");
			user2 = new Consumer("USER2", "TEST");
			user3 = new Consumer("USER3", "TEST");
			user4 = new Consumer("USER4", "TEST");
			
			user2.followUser(user1);
			//user2.followUser(user3);
			
			user3.followUser(user1);
			
			user4.followUser(user1);
			//user4.followUser(user2);
			
			user1.likeDocument(doc1);
			user2.likeDocument(doc2);
			user3.likeDocument(doc2);
			user4.likeDocument(doc2);
			
			UserPopularityRankingStrategy asd = new UserPopularityRankingStrategy();
			List<Document> ranked = asd.rankDocuments(producer1.getDocumentsProduced(), user1);
			System.out.println(doc1);
			System.out.println(doc2);
			
			assertEquals(ranked.get(0), doc1);
			assertEquals(ranked.get(1), doc2);
		}
		

	}

	/**
	 * Test method for
	 * {@link nullSquad.strategies.ranking.UserPopularityRankingStrategy#rankDocuments(java.util.List, nullSquad.filesharingsystem.users.User)}
	 * .
	 */
	@Test
	public void testRankDocuments()
	{
		fail("Not yet implemented");
	}

}
