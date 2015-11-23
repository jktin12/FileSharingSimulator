package nullSquad.strategies.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.User;

/**
 * User Popularity Ranking Strategy
 * 
 * @author MVezina
 */
public class UserPopularityRankingStrategy implements DocumentRankingStrategy
{

	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user)
	{
		List<Document> rankedDocuments = new ArrayList<>();

		if (allDocuments == null || allDocuments.isEmpty())
		{
			return rankedDocuments;
		}

		for (Document d : allDocuments)
		{
			rankedDocuments.add(d);
		}

		// Sort the top documents using the selected strategy as a comparator
		Collections.sort(rankedDocuments, this);

		// Since Collections.sort() sorts from worst -> greatest, we need to
		// reverse the list
		Collections.reverse(rankedDocuments);

		return rankedDocuments;

	}

	@Override
	public int compare(Document document1, Document document2)
	{
		// If both are equal, they are ranked the same
		if (document1 == document2)
			return 0;

		// If doc1 is null and doc2 isnt, doc2 is ranked higher
		if (document1 == null && document2 != null)
			return -1;

		// If doc1 isnt null and doc2 is, doc1 is ranked higher
		if (document1 != null && document2 == null)
			return 1;

	
		// Return the difference of the two user popularity averages
		return (int) (getAverageUserPopularity(document1) - getAverageUserPopularity(document2));
	}

	private float getAverageUserPopularity(Document d)
	{
		float avgUserPopularity = 0;
		
		// Determine the overall popularity of all likers of document 1
		for (User u : d.getUserLikes())
		{
			avgUserPopularity += u.getFollowers().size();
		}

		// Prevent division by 0
		if (d.getUserLikes().size() != 0)
			avgUserPopularity /= d.getUserLikes().size();
		
		return avgUserPopularity;
	}

}
