package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.Consumer;
import nullSquad.filesharingsystem.users.Producer;
import nullSquad.strategies.ranking.LikesSimilarityRankingStrategy;


/**
 * Test class for the LikesSimilarityRankingStrategy class
 * 
 * @author Raymond Wu
 */

public class LikesSimilarityRankingStrategyTest {

	private Producer producer1;
	private Producer producer2;

	private Consumer user1;
	private Consumer user2;
	private Consumer user3;
	private Consumer userTest;

	private List<Document> allDocuments;
	
	private Document doc1,doc2,doc3,doc4;
	
	@Before
	public void setUp() throws Exception {
		String tag1 = "TEST";
		String tag2 = "BAD";
		
		producer1 = new Producer("Producer1",tag1);
		producer2 = new Producer("Producer2",tag2);
		
		doc1 = new Document("Document1", tag1, producer1);
		doc2 = new Document("Document2", tag1, producer1);
		doc3 = new Document("Document3", tag1, producer1);
		doc4 = new Document("Document4",tag2, producer2);

		user1 = new Consumer("user1", tag1);
		user2 = new Consumer("user2", tag1);
		user3 = new Consumer("user3", tag2);
		userTest = new Consumer("userTest", tag1);
		
		user2.likeDocument(doc1);
		userTest.likeDocument(doc1);
		
		user1.likeDocument(doc2);
		user2.likeDocument(doc2);
		userTest.likeDocument(doc2);
		
		user1.likeDocument(doc3);
		
		user3.likeDocument(doc4);
		
		allDocuments = new ArrayList<>();
		allDocuments.add(doc1);
		allDocuments.add(doc2);
		allDocuments.add(doc3);
		allDocuments.add(doc4);
	}

	/**
	 * @author Raymond Wu
	 */
	@Test
	public void testRankDocuments() {
		List<Document> ranked = (new LikesSimilarityRankingStrategy()).rankDocuments(allDocuments, userTest);
		
		assertEquals(ranked.size(), allDocuments.size());
		assertEquals(allDocuments.size(), 4);
		assertEquals(ranked.get(0),doc2);
		assertEquals(ranked.get(1),doc1);
		assertEquals(ranked.get(2),doc3);
		assertEquals(ranked.get(3),doc4);

	}

}
