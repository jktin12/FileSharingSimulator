package nullSquad.strategies.ranking;

/**
 * Enumeration of all available ranking strategies
 * @author MVezina
 */
public enum DocumentRankingStrategyEnum
{
		DocumentPopularity("Document Popularity", new DocumentPopularityStrategy()),
		UserPopularity("User Popularity", new UserPopularityRankingStrategy()), 
		UserDistance("User Distance", new UserDistanceRankingStrategy()),
		LikeSimilarity("Like Similarity", new LikesSimilarityRankingStrategy()),
		FollowSimiliarity("Follow Similarity", new FollowSimilarityStrategy());
		
		// Associate a search ranking strategy with each enum
		private DocumentRankingStrategy docRankStrat;
		private String stratName;
		
		
		private DocumentRankingStrategyEnum(String name, DocumentRankingStrategy docRankStrat)
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
		
		
		/**
		 * Determines the ranking strategy enum based on the instance of Document Ranking Strategy
		 * @param documentRankingStrategy The Ranking Strategy
		 * @return The DocumentRankingStrategyEnum (or Null if the Ranking Strategy is not one of the enums)
		 */
		public static DocumentRankingStrategyEnum getDocumentRankingStrategyEnum(DocumentRankingStrategy documentRankingStrategy)
		{
			if(documentRankingStrategy instanceof DocumentPopularityStrategy)
			{
				return DocumentRankingStrategyEnum.DocumentPopularity;
			}
			
			if(documentRankingStrategy instanceof UserPopularityRankingStrategy)
			{
				return DocumentRankingStrategyEnum.UserPopularity;
			}
			
			if(documentRankingStrategy instanceof UserDistanceRankingStrategy)
			{
				return DocumentRankingStrategyEnum.UserDistance;
			}
			
			if(documentRankingStrategy instanceof LikesSimilarityRankingStrategy)
			{
				return DocumentRankingStrategyEnum.LikeSimilarity;
			}
			
			if(documentRankingStrategy instanceof FollowSimilarityStrategy)
			{
				return DocumentRankingStrategyEnum.FollowSimiliarity;
			}
			
			
			return null;
		}
}
