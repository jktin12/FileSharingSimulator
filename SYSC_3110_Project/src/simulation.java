import java.util.*;

/*
 * Creates a simulation of the file-sharing
 * social network.
 */

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
		numberOfTags = tags;
	}
	
	//Returns all the documents in the simulation
	public List<document> getAllDocuments() {
		return allDocuments;
	}

	//Returns all the users in the simulation
	public List<user> getAllUsers() {
		return users;
	}

	public static void main (String [] args){
		int tags;
		int producers;
		int consumers;
		char running = 'y';
		String s;
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of tags");
		tags = in.nextInt();
		System.out.println("Enter the number of consumers");
		consumers = in.nextInt();
		System.out.println("Enter the number of producers");
		producers = in.nextInt();
		
		simulation sim = new simulation(tags,producers,consumers);
		
		//Runs the simulation until the user quits
		//A user should be chosen at random and then be asked
		//to perform an "act"
		while (running!='q'){
			
			
			
		}
		
	}
	
}
