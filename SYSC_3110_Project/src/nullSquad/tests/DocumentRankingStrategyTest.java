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
import nullSquad.filesharingsystem.users.Producer;
import nullSquad.strategies.ranking.DocumentRankingStrategy;

/**
 * @author MVezina
 */
public class DocumentRankingStrategyTest
{
	private Producer user1;
	private String tag;
	private List<Document> documents;

	/**
	 * @throws java.lang.Exception
	 * @author MVezina
	 */
	@Before
	public void setUp() throws Exception
	{
		user1 = new Producer("Producer1", tag);
		tag = "Test";

		documents = new ArrayList<>();

		documents.add(new Document("Doc1", tag, user1));
		documents.add(new Document("Doc2", tag, user1));
		documents.add(new Document("Doc3", tag, user1));
	}

	@Test
	public void testAllRankingStrategies()
	{
		for (int i = 0; i < DocumentRankingStrategy.Strategy.values().length; i++)
		{
			System.out.println(DocumentRankingStrategy.Strategy.values()[i].toString());
			assertEquals(documents.size(), DocumentRankingStrategy.Strategy.values()[i].getStrategy().rankDocuments(documents, user1).size());
		}

	}

}
