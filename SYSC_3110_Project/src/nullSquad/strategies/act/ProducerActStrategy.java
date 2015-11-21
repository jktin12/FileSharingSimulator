package nullSquad.strategies.act;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.users.*;

public interface ProducerActStrategy {
	
	/**
	 * Enumeration used to hold all Producer Act Strategies
	 * 
	 * @author MVezina
	 */
	public enum Strategy
	{
		Simularity("Simularity", new SimularityActStrategy()),
		Default("Default", new DefaultProducerActStrategy());

		private ProducerActStrategy actStrategy;
		private String stratName;

		private Strategy(String name, ProducerActStrategy actStrategy)
		{
			this.stratName = name;
			this.actStrategy = actStrategy;
		}

		public String toString()
		{
			return stratName;
		}

		public ProducerActStrategy getStrategy()
		{
			return actStrategy;
		}

	}

	/**
	 * The implementation of how a producer should act
	 * @param producer The producer that is acting
	 * @param fileSharingSystem The file sharing system to act upon
	 * @param kResults The number of results to search for
	 * @author MVezina
	 */
	public void act(Producer producer, FileSharingSystem fileSharingSystem, int kResults);
	
}
