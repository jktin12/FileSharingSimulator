package nullSquad.strategies.act;

/**
 * Enum used to hold all Producer Act Strategies
 * 
 * @author MVezina
 */
public enum ProducerActStrategyEnum
{
	Simularity("Simularity", new SimularityActStrategy()),
	Default("Default", new DefaultProducerActStrategy());

	private ProducerActStrategy actStrategy;
	private String stratName;

	private ProducerActStrategyEnum(String name, ProducerActStrategy actStrategy)
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
