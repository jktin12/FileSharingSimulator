package nullSquad.filesharingsystem.users;

/**
 * Used for components to subscribe to when a user's payoff history has been
 * updated (Used In GraphGUI for updating the User's payoff as the simulation
 * progresses)
 * 
 * @author MVezina
 */
public interface UserPayoffListener
{
	public void payoffUpdated(UserPayoffEvent uPE);
}
