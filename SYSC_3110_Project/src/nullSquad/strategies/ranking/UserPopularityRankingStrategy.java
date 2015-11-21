package nullSquad.strategies.ranking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.User;

public class UserPopularityRankingStrategy implements DocumentRankingStrategy, Comparator<Document>
{

	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user, String tag, int topK)
	{
		List<Document> rankedDocuments = new ArrayList<>();

		for (Document d : allDocuments)
		{
			if (d.getTag().equals(tag))
			{
				rankedDocuments.add(d);
			}
		}

		// Go through all user likes and rank based on

		return rankedDocuments;

	}

	@Override
	public int compare(Document document1, Document document2)
	{
		int avgUserPopularityDocument1 = 0, avgUserPopularityDocument2 = 0;

		// Determine the overall popularity of all likers of document 1
		for (User u : document1.getUserLikes())
		{
			avgUserPopularityDocument1 += u.getFollowers().size();
		}

		avgUserPopularityDocument1 /= document1.getUserLikes().size();

		// Determine the overall popularity of all likers of document 1
		for (User u : document2.getUserLikes())
		{
			avgUserPopularityDocument2 += u.getFollowers().size();
		}

		avgUserPopularityDocument2 /= document2.getUserLikes().size();

		
		// Return the difference of the two user popularity averages
		return avgUserPopularityDocument1 - avgUserPopularityDocument2;
	}

}
