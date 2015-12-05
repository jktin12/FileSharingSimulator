/**
 * 
 */
package nullSquad.simulator.gui;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import nullSquad.filesharingsystem.users.Consumer;
import nullSquad.filesharingsystem.users.Producer;
import nullSquad.filesharingsystem.users.User;

/**
 * @author MVezina
 */
public class UserListHandler implements ListDataListener
{
	DefaultListModel<Producer> producerListModel;
	DefaultListModel<Consumer> consumersListModel;

	public UserListHandler(DefaultListModel<Producer> producerList, DefaultListModel<Consumer> consumerList)
	{
		this.producerListModel = producerList;
		this.consumersListModel = consumerList;
	}

	@Override
	public void intervalAdded(ListDataEvent e)
	{
		if (!(e.getSource() instanceof DefaultListModel<?>))
			return;

		resyncLists((DefaultListModel<?>) e.getSource());

		// We want to add the newly added user object into the appropriate
		// sub-list model and subscribe to payoff updates

	}

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
