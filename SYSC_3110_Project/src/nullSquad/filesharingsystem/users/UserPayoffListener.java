package nullSquad.filesharingsystem.users;

import java.io.Serializable;

/**
 * Used for components to subscribe to when a user's payoff history has been
 * updated (Used In GraphGUI for updating the User's payoff as the simulation
 * progresses)
 * 
 * @author MVezina
 */
public interface UserPayoffListener extends Serializable
{
	public void payoffUpdated(UserPayoffEvent uPE);
}
