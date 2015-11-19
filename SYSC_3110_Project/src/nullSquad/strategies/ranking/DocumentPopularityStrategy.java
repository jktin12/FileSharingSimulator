package nullSquad.strategies.ranking;

import nullSquad.filesharingsystem.document.Document;

/**
 * Class to implement a search ranking strategy
 * @author MVezina
 *
 */
public class DocumentPopularityStrategy implements SearchRankingStrategy{

	@Override
	public int compare(Document doc1, Document doc2) {
		// Compare documents in terms of popularity (Likes)
		return doc1.getUserLikes().size() - doc2.getUserLikes().size();
	}

}
