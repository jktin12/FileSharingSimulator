package nullSquad.strategies.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.User;
/**
* @author Marc Tebo
* Rank the documents by the “follow” similarity 
* (i.e., if I follow you, I will trust you when I “like” 
* documents I haven’t seen yet)
*/

public class FollowSimilarityStrategy implements DocumentRankingStrategy, Comparator<Document>
{	
	private User user;
	
	/**
	 * @author Marc Tebo
	 * return list of documents ranked off of "follow" similarity
	 */
	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user)
	{
		this.user = user;
		List<Document> rankedDocuments = new ArrayList<Document>();
		
		Collections.sort(rankedDocuments,this);
		
		Collections.reverse(rankedDocuments);
		
		return rankedDocuments;
	}
	
	/**
	 * @author Marc Tebo
	 * Comparing documents based on "follow" similarity
	 * return 1 if doc1 is better, 0 if it is a tie, -1 if doc2 is better
	 */
	@Override
	public int compare(Document doc1, Document doc2)
	{
		if(doc1 != null && doc2 == null){
			return 1;
		}
		
		if(doc1 == null && doc2 == null){
			return 0;
		}
		
		if(doc1 == null & doc2 != null){
			return -1;
		}
		
		int likesSimilarityDoc1 = 0;
		int likesSimilarityDoc2 = 0;
		
		for(User u: user.getFollowing()){
			
			// For each user following the current user, if the user likes
			// the document, give the document a point
			
			if(doc1.getUserLikes().contains(u)){
				likesSimilarityDoc1++;
			}
			if(doc2.getUserLikes().contains(u)){
				likesSimilarityDoc2++;
			}
		}
		
		return likesSimilarityDoc1 - likesSimilarityDoc2;
	}
}
