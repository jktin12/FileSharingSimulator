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
import nullSquad.strategies.ranking.DocumentPopularityStrategy;

/**
 * @author MVezina
 *
 */
public class DocumentPopularityStrategyTest
{
	Document doc1, doc2, doc3;
	List<Document> allDocuments;
	Producer prod;
	Consumer cons1, cons2, cons3;
	
	String tag;
	
	
	/**
	 * @throws java.lang.Exception
	 * @author MVezina
	 */
	@Before
	public void setUp() throws Exception
	{
		tag = "TestTag";
		prod = new Producer("Producer", tag);
		cons1 = new Consumer("Consumer1", tag);
		cons2 = new Consumer("Consumer2", tag);
		cons3 = new Consumer("Consumer3", tag);
		
		doc1 = new Document("DOC1", tag , prod);
		doc2 = new Document("DOC2", tag , prod);
		doc3 = new Document("DOC3", tag , prod);
		
		
		cons2.likeDocument(doc1);
		
		cons3.likeDocument(doc2);
		cons2.likeDocument(doc2);
		cons1.likeDocument(doc2);
		
		cons1.likeDocument(doc3);
		cons2.likeDocument(doc3);
		
	
		allDocuments = new ArrayList<>();
		allDocuments.add(doc1);
		allDocuments.add(doc2);
		allDocuments.add(doc3);
		
	}

	@Test
	public void testRankingStrategy()
	{
		List<Document> rankedDocs = (new DocumentPopularityStrategy()).rankDocuments(allDocuments, null);
		
		assertEquals(rankedDocs.size(), allDocuments.size());
		assertEquals(allDocuments.size(), 3);
		
		assertEquals(rankedDocs.get(0), doc2);
		assertEquals(rankedDocs.get(1), doc3);
		assertEquals(rankedDocs.get(2), doc1);
	}

}
