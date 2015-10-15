import java.util.*;

public class simulation {
	private List<document> allDocuments;
	private List<user> users;
	private int numberOfTags;
	private int numberOfConsumers;
	private int numberOfProducers;
	
	public simulation(int tags, int producers, int consumers){
		allDocuments = new ArrayList<document>();
		users = new ArrayList<user>();
		numberOfConsumers = consumers;
		numberOfProducers = producers;
	}
	
	public List<document> getAllDocuments() {
		return allDocuments;
	}

	public List<user> getAllUsers() {
		return users;
	}

	public static void main (String [] args){
		
		
	}
	
}
