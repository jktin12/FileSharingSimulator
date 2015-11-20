package nullSquad.strategies.ranking;

/**
 * Enumeration of all available ranking strategies
 * @author MVezina
 */
public enum RankingStrategyEnum
{
		DocumentPopularity("Document Popularity", new DocumentPopularityStrategy()),
		UserPopularity("User Popularity", new UserPopularityRankingStrategy()), 
		UserDistance("User Distance", new UserDistanceRankingStrategy()),
		LikeSimilarity("Like Similarity", new LikesSimilarityRankingStrategy()),
		FollowSimiliarity("Follow Similarity", new FollowSimilarityStrategy());
		
		// Associate a search ranking strategy with each enum
		private SearchRankingStrategy searchRankStrat;
		private String stratName;
		
		
		private RankingStrategyEnum(String name, SearchRankingStrategy searchRankStrat)
		{
			this.stratName = name;
			this.searchRankStrat = searchRankStrat;
		}
		
		public String toString()
		{
			return stratName;
		}
		
		public SearchRankingStrategy getStrategy()
		{
			return searchRankStrat;
		}
		
		
		/**
		 * Determines the ranking strategy enum based on the instance of searchRankingStrategy
		 * @param searchRankingStrategy The Ranking Strategy
		 * @return The RankingStrategyEnum (or Null if the Ranking Strategy is not one of the enums)
		 */
		public static RankingStrategyEnum getRankingStrategyEnum(SearchRankingStrategy searchRankingStrategy)
		{
			if(searchRankingStrategy instanceof DocumentPopularityStrategy)
			{
				return RankingStrategyEnum.DocumentPopularity;
			}
			
			if(searchRankingStrategy instanceof UserPopularityRankingStrategy)
			{
				return RankingStrategyEnum.UserPopularity;
			}
			
			if(searchRankingStrategy instanceof UserDistanceRankingStrategy)
			{
				return RankingStrategyEnum.UserDistance;
			}
			
			if(searchRankingStrategy instanceof LikesSimilarityRankingStrategy)
			{
				return RankingStrategyEnum.LikeSimilarity;
			}
			
			if(searchRankingStrategy instanceof FollowSimilarityStrategy)
			{
				return RankingStrategyEnum.FollowSimiliarity;
			}
			
			
			return null;
		}
}
