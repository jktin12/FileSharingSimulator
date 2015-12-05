package nullSquad.filesharingsystem.users;

import java.util.EventObject;

/**
 * Event for when a User's payoff has been updated
 * 
 * @author MVezina
 */
public class UserPayoffEvent extends EventObject
{

	private static final long serialVersionUID = -9096563796777825385L;
	private User user;

	public UserPayoffEvent(User sender)
	{
		super(sender);
		this.user = sender;
	}

	/**
	 * @return The User who's payoff was updated
	 */
	public User getUser()
	{
		return this.user;
	}
}
