/**
 * Title: SYSC 3110 Project
 * Team: nullSquad
 * To be implemented by Justin
 */

package nullSquad.network;

import java.util.ArrayList;
import java.util.List;

public class Network
{
	private List<User> users;
	private List<Document> allDocuments;
	
	public Network()
	{
		users = new ArrayList<>();
		allDocuments = new ArrayList<>();
	}
	
	public boolean registerUser(User user)
	{
		return false;
	}
	public boolean deactivateUser(User user)
	{
		return false;
	}
	public int calculatePayoff(List<Document> documents)
	{
		return 0;
	}
	public List<Document> search(int topK)
	{
		return allDocuments;
	}
	public boolean addDocument(Document doc)
	{
		return false;
	}
	public boolean removeDocument(Document doc)
	{
		return false;
	}
	public List<Document> getAllDocuments()
	{
		return allDocuments;
	}
	public List<User> getUsers()
	{
		return users;
	}

}
