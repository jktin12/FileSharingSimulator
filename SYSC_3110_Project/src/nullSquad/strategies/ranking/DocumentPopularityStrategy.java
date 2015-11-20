package nullSquad.strategies.ranking;

import nullSquad.filesharingsystem.document.Document;

/**
 * Class to implement a search ranking strategy
 * 
 * @author MVezina
 */
public class DocumentPopularityStrategy implements SearchRankingStrategy
{

	@Override
	public int compare(Document doc1, Document doc2)
	{


		// If both are null, they are ranked the same
		if (doc1 == null && doc2 == null)
			return 0;

		// If doc1 is null and doc2 isnt, doc2 is ranked higher
		if (doc1 == null && doc2 != null)
			return -1;

		// If doc1 isnt null and doc2 is, doc1 is ranked higher
		if (doc1 != null && doc2 == null)
			return 1;

		// Compare documents in terms of popularity (Likes)
		return doc1.getUserLikes().size() - doc2.getUserLikes().size();
	}

}
