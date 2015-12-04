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

	/* Serializable ID */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user)
	{
		// Copy the documents over to the new array to be ranked
		List<Document> rankedDocuments = new ArrayList<>();

		// Return an empty list if any of the fields are null or if the
		// documents list is empty. Note: user can be null because it is not used in this strategy
		if (allDocuments == null || allDocuments.isEmpty())
			return rankedDocuments;

		// Copy the items over from the passed in list so that we can sort and
		// reverse without affecting the original passed-in list
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
		// If both are equal, they are ranked the same
		if (doc1 == doc2)
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
