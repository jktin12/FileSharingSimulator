package nullSquad.strategies.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.User;

/**
 * @author Marc Tebo Rank the documents by the "like" similarity (i.e. if we
 *         tend to "like" the same documents, I will trust you when you "like"
 *         documents I haven't seen yet)
 */

public class FollowSimilarityStrategy implements DocumentRankingStrategy
{
	/* Serializable ID */
	private static final long serialVersionUID = 1L;
	
	
	private User user;

	/**
	 * @author Marc Tebo return list of documents ranked off of "like"
	 *         similarity
	 */
	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user)
	{
		this.user = user;
		List<Document> rankedDocuments = new ArrayList<Document>();

		// We want to copy the documents into a new array list for modification
		for (Document d : allDocuments)
		{
			rankedDocuments.add(d);
		}

		Collections.sort(rankedDocuments, this);

		Collections.reverse(rankedDocuments);

		return rankedDocuments;
	}

	/**
	 * @author Marc Tebo Comparing documents based on "like" similarity return 1
	 *         if doc1 is better, 0 if it is a tie, -1 if doc2 is better
	 */
	@Override
	public int compare(Document doc1, Document doc2)
	{
		if (doc1 != null && doc2 == null)
		{
			return 1;
		}

		if (doc1 == null && doc2 == null)
		{
			return 0;
		}

		if (doc1 == null & doc2 != null)
		{
			return -1;
		}

		int likesSimilarityDoc1 = 0;
		int likesSimilarityDoc2 = 0;

		for (User u : user.getFollowing())
		{

			// For each user following the current user, if the user likes
			// the document, give the document a point

			if (doc1.getUserLikes().contains(u))
			{
				likesSimilarityDoc1++;
			}
			if (doc2.getUserLikes().contains(u))
			{
				likesSimilarityDoc2++;
			}
		}

		return likesSimilarityDoc1 - likesSimilarityDoc2;
	}
}
