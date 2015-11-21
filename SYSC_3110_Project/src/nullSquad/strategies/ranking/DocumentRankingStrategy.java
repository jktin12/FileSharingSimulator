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
	 * Enumeration for all possible strategies
	 * 
	 * @author MVezina
	 */
	public enum Strategy
	{
		DocumentPopularity("Document Popularity", new DocumentPopularityStrategy()),
		UserPopularity("User Popularity", new UserPopularityRankingStrategy()),
		UserDistance("User Distance", new UserDistanceRankingStrategy()),
		LikeSimilarity("Like Similarity", new LikesSimilarityRankingStrategy()),
		FollowSimiliarity("Follow Similarity", new FollowSimilarityStrategy());

		// Associate a search ranking strategy with each enum
		private DocumentRankingStrategy docRankStrat;
		private String stratName;

		private Strategy(String name, DocumentRankingStrategy docRankStrat)
		{
			this.stratName = name;
			this.docRankStrat = docRankStrat;
		}

		public String toString()
		{
			return stratName;
		}

		public DocumentRankingStrategy getStrategy()
		{
			return docRankStrat;
		}

	}

	/**
	 * Ranks the documents from Best->Worst. (Best Document Occurs First in the
	 * list because we want it to be the first result)
	 * 
	 * @param allDocuments The documents to rank
	 * @param user The User to rank by
	 * @return The topK ranked documents based on the strategy
	 * @author MVezina
	 */
	public List<Document> rankDocuments(List<Document> allDocuments, User user);

}
