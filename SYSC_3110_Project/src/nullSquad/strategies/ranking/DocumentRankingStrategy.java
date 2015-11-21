package nullSquad.strategies.ranking;

import nullSquad.filesharingsystem.document.*;
import nullSquad.filesharingsystem.users.*;

import java.util.*;

/**
 * Interface used for Ranking Documents
 * 
 * @author MVezina
 */
public interface DocumentRankingStrategy
{
	/**
	 * Ranks the documents from Best->Worst. (Best Document Occurs First in the
	 * list because we want it to be the first result)
	 * 
	 * @param allDocuments The documents to rank
	 * @param user The User to rank by
	 * @param tag The Tag to rank by
	 * @param topK The number of top ranked documents to return
	 * @return The topK ranked documents based on the strategy
	 * @author MVezina
	 */
	public List<Document> rankDocuments(List<Document> allDocuments, User user, String tag, int topK);

}
