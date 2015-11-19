/**
 * Interface for Producer Payoff Strategies
 * 
 * @author MVezina
 */
package nullSquad.strategies.payoff;

import java.util.List;

import nullSquad.filesharingsystem.users.*;
import nullSquad.filesharingsystem.document.*;

/**
 * This interface will be used to implement all Consumer Payoff Strategies
 * @author MVezina
 */
public interface ConsumerPayoffStrategy
{
	public int consumerPayoffStrategy(Consumer consumer, List<Document> documentSearchResults);
}
