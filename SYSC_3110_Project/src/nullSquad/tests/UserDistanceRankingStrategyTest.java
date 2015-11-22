
package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.Consumer;
import nullSquad.strategies.ranking.UserDistanceRankingStrategy;

public class UserDistanceRankingStrategyTest {

	private List<String> tags;
	private String tag1, tag2, tag3;
	private Consumer consumer1, consumer2, consumer3, consumer4;
//	private Consumer consumer5, consumer6, consumer7;
	private Document doc1, doc2;
	private List<Document> allDocuments;
	
	@Before
	public void setUp() throws Exception {
		tags = new ArrayList<>();
		tag1 = "tag1";
		tag2 = "tag2";
		tag3 = "tag3";
		
		tags.add(tag1);
		tags.add(tag2);
		tags.add(tag3);

		//initialize users
		consumer1 = new Consumer("CONSUMER1", "Gaming");
		consumer2 = new Consumer("CONSUMER2", "Gaming");
		consumer3 = new Consumer("CONSUMER3", "Gaming");
		consumer4 = new Consumer("CONSUMER4", "Gaming");
//		consumer5 = new Consumer("CONSUMER5", "Gaming");
//		consumer6 = new Consumer("CONSUMER6", "Gaming");
//		consumer7 = new Consumer("CONSUMER7", "Gaming");
	
		allDocuments = new ArrayList<>();
		allDocuments.add(doc1);
		allDocuments.add(doc2);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRankDocuments() {
		consumer4.likeDocument(doc1);
		consumer3.likeDocument(doc2);
		
		//Set up like-network
		/*
		 * ------ = friends with
		 * 
		 * consumer1 ------ consumer2 ------ consumer3 ------ consumer4
		 * 
		 */
		consumer1.followUser(consumer2);
		consumer2.followUser(consumer3);
		consumer3.followUser(consumer4);
		
		List<Document> ranked = (new UserDistanceRankingStrategy()).rankDocuments(allDocuments, null);
		
		// Ensure lists are same size
		assertEquals(ranked.size(), allDocuments.size());
		assertEquals(2, allDocuments.size());
		
		//Test to ensure that doc2 is ranked higher than doc1
		assertEquals(ranked.get(0), doc2);
		assertEquals(ranked.get(1), doc1);
		
		consumer2.likeDocument(doc1);
		//Test to ensure that doc1 is ranked higher than doc2
		assertEquals(ranked.get(0), doc1);
		assertEquals(ranked.get(1), doc2);
		
	}
}

