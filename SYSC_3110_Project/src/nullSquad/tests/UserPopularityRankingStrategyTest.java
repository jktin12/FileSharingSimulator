/**
 * 
 */
package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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

	private List<Document> allDocuments;

	Document doc1, doc2;

	/**
	 * @throws java.lang.Exception
	 * @author MVezina
	 */
	@Before
	public void setUp() throws Exception
	{
		producer1 = new Producer("PRODUCER1", "TEST");

		String tag = "TestTag";

		doc1 = new Document("Document1", tag, producer1);
		doc2 = new Document("Document2", tag, producer1);

		user1 = new Consumer("USER1", tag);
		user2 = new Consumer("USER2", tag);
		user3 = new Consumer("USER3", tag);
		user4 = new Consumer("USER4", tag);

		user1.followUser(user2);
		user1.followUser(user3);
		user1.followUser(user4);

		user2.followUser(user3);
		user2.followUser(user4);

		user3.followUser(user2);
		user3.followUser(user4);

		user4.followUser(user1);
		user4.followUser(user2);
		user4.followUser(user3);

		user1.likeDocument(doc1);
		user1.likeDocument(doc2);
		user2.likeDocument(doc2);
		user3.likeDocument(doc2);
		user4.likeDocument(doc2);

		allDocuments = new ArrayList<>();
		allDocuments.add(doc1);
		allDocuments.add(doc2);

	}

	/**
	 * Test method for
	 * {@link nullSquad.strategies.ranking.UserPopularityRankingStrategy#rankDocuments(java.util.List, nullSquad.filesharingsystem.users.User)}
	 * .
	 */
	@Test
	public void testRankDocuments()
	{
		List<Document> ranked = (new UserPopularityRankingStrategy()).rankDocuments(allDocuments, null);

		// Ensure lists are same size
		assertEquals(ranked.size(), allDocuments.size());
		assertEquals(allDocuments.size(), 2);

		assertEquals(ranked.get(0), doc2);
		assertEquals(ranked.get(1), doc1);
	}

}
