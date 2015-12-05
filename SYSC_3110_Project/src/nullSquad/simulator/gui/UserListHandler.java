/**
 * 
 */
package nullSquad.simulator.gui;

import javax.swing.*;
import javax.swing.event.*;

import nullSquad.filesharingsystem.users.*;

/**
 * Used for the allUsersListModel to split Users into a producerList and a
 * consumerList. This class needed to be separate from the class containing the
 * JList due to problems with Serialization encountering a stack overflow
 * 
 * @author MVezina
 */
public class UserListHandler implements ListDataListener
{
	private DefaultListModel<Producer> producerListModel;
	private DefaultListModel<Consumer> consumersListModel;

	public UserListHandler(DefaultListModel<Producer> producerList, DefaultListModel<Consumer> consumerList)
	{
		this.producerListModel = producerList;
		this.consumersListModel = consumerList;
	}

	/**
	 * Clears Both Producer and Consumer Lists and synchronizes users
	 * 
	 * @param allUsers The list of all users
	 * @author MVezina
	 */
	public void resyncLists(DefaultListModel<?> allUsers)
	{
		producerListModel.clear();
		consumersListModel.clear();

		for (int i = 0; i < allUsers.size(); i++)
		{
			Object newUser = allUsers.get(i);

			if (!(newUser instanceof User))
				continue;

			if (newUser instanceof Producer)
			{
				producerListModel.addElement((Producer) newUser);
			}
			else if (newUser instanceof Consumer)
			{
				consumersListModel.addElement((Consumer) newUser);
			}
		}

	}

	@Override
	public void intervalAdded(ListDataEvent e)
	{
		if (!(e.getSource() instanceof DefaultListModel<?>))
			return;

		resyncLists((DefaultListModel<?>) e.getSource());

	}

	@Override
	public void intervalRemoved(ListDataEvent e)
	{
		if (!(e.getSource() instanceof DefaultListModel<?>))
			return;

		resyncLists((DefaultListModel<?>) e.getSource());
	}

	/**
	 * Empty Implementation from ListDataListener
	 */
	@Override
	public void contentsChanged(ListDataEvent e)
	{
	}

}
