/**
 * Interface for Producer Payoff Strategies
 * 
 * @author MVezina
 */
package nullSquad.strategies;

import java.util.List;

import nullSquad.document.Document;
import nullSquad.network.Consumer;

/**
 * This interface will be used to implement all Consumer Payoff Strategies
 * @author MVezina
 */
public interface ConsumerPayoffStrategy
{
	public int consumerPayoffStrategy(Consumer consumer, List<Document> documentSearchResults);
}
