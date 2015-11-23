package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.Consumer;
import nullSquad.filesharingsystem.users.Producer;
import nullSquad.strategies.ranking.FollowSimilarityStrategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FollowSimilarityStrategyTest {
	
	public Document doc1,doc2,doc3;
	public Producer prod;
	public Consumer con1,con2,con3,con4;
	public List<Document> docs;
	
	@Before
	public void setUp() throws Exception {
		
		docs = new ArrayList<Document>();
		
		prod = new Producer("Producer1", "TEST");
		con1 = new Consumer("Consumer1", "TEST");
		con2 = new Consumer("Consumer2", "TEST");
		con3 = new Consumer("Consumer3", "TEST");
		con4 = new Consumer("Consumer4", "TEST");
		
		doc1 = new Document("Doc1","TEST", prod);
		doc2 = new Document("Doc2","TEST", prod);
		doc3 = new Document("Doc3","TEST", prod);
		
		docs.add(doc1);
		docs.add(doc2);
		docs.add(doc3);
		
		con3.followUser(prod);
		con3.followUser(con1);
		con3.followUser(con4);
		
		con1.likeDocument(doc1);
		con2.likeDocument(doc1);
		con2.likeDocument(doc2);
		con1.likeDocument(doc3);
		con4.likeDocument(doc3);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRankDocuments() {

		List<Document> rankedDocs = (new FollowSimilarityStrategy().rankDocuments(docs, con3));
		
		assertTrue(rankedDocs.get(0).equals(doc3));
		assertTrue(rankedDocs.get(1).equals(doc1));
		assertTrue(rankedDocs.get(2).equals(doc2));
	}

}
