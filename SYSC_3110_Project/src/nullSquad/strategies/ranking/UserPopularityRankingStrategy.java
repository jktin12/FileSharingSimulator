package nullSquad.strategies.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.User;

public class UserPopularityRankingStrategy implements DocumentRankingStrategy, Comparator<Document>
{

	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user)
	{
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

		int avgUserPopularityDocument1 = 0, avgUserPopularityDocument2 = 0;

		// Determine the overall popularity of all likers of document 1
		for (User u : document1.getUserLikes())
		{
			avgUserPopularityDocument1 += u.getFollowers().size();
		}

		// Prevent division by 0
		if (document1.getUserLikes().size() != 0)
			avgUserPopularityDocument1 /= document1.getUserLikes().size();

		// Determine the overall popularity of all likers of document 1
		for (User u : document2.getUserLikes())
		{
			avgUserPopularityDocument2 += u.getFollowers().size();
		}

		// Prevent division by 0
		if (document2.getUserLikes().size() != 0)
			avgUserPopularityDocument2 /= document2.getUserLikes().size();

		// Return the difference of the two user popularity averages
		return avgUserPopularityDocument1 - avgUserPopularityDocument2;
	}

}
