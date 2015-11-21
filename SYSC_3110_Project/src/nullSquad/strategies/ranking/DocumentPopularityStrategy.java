package nullSquad.strategies.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.User;

/**
 * Class to implement a search ranking strategy
 * 
 * @author MVezina
 */
public class DocumentPopularityStrategy implements DocumentRankingStrategy, Comparator<Document>
{

	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user)
	{
		// Copy the documents over to the new array to be ranked
		List<Document> rankedDocuments = new ArrayList<>();
		
		
		for (Document d : allDocuments)
		{
			rankedDocuments.add(d);
		}

		
		// Sort the top documents using the selected strategy as a comparator
		Collections.sort(rankedDocuments, this);

		// Since Collections.sort() sorts from worst -> greatest, we need to
		// reverse the list
		Collections.reverse(rankedDocuments);

		// Return the ranked documents
		return rankedDocuments;
	}

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
