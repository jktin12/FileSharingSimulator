
package nullSquad.strategies.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.User;

/**
 * Class that implements a follow similarity strategy
 * 
 * @author Raymond Wu
 */
public class LikesSimilarityRankingStrategy implements DocumentRankingStrategy,  Comparator<Document>
{
	private User user;
	
	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user)
	{
		this.user = user;
		List<Document> rankedDocuments = new ArrayList<Document>();
		
		Collections.sort(rankedDocuments,this);
		
		Collections.reverse(rankedDocuments);
		
		return rankedDocuments;
	}
	
	@Override
	public int compare(Document doc1, Document doc2)
	{
		
		if(doc1 != null && doc2 == null){
			return 1;
		}
		
		if ((doc1 == null && doc2 == null) || (doc1.equals(doc2))) {
			return 0;
		}
		
		if(doc1 == null & doc2 != null){
			return -1;
		}
	
		return docLikeScore(doc1) - docLikeScore(doc2);
	
	}
	
	private int docLikeScore(Document doc){
		int score;
		int highestScore=0;
		for (User u:doc.getUserLikes()){
			score=0;
			for (Document aDoc: u.getLikedDocuments()){
				if (user.getLikedDocuments().contains(aDoc)){
					score+=1;
				}
			}
			if (score > highestScore){
				highestScore=score;
			}
		}
		return highestScore;
	}
	

}
