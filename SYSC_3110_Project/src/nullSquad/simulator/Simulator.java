/**
 * Title: SYSC 3110 Project
 * Created By: Raymond Wu			
 * Student Number: 100938326
 * Team: nullSquad
 */
package nullSquad.simulator;

import nullSquad.document.*;
import nullSquad.network.*;
import java.util.*;

public class Simulator
{
	private Network network;
	private Random randomNumber;
	private enum tags { MUSIC, BOOKS, GAMING, SCHOOL, SPORTS, PROGRAMMING }
	private List<String> allTastesAndTags;
	
	/**
	 * Creates a simulator with specified network
	 * 
	 * @param network Network of all the users/documents
	 * @author Raymond Wu
	 */
	public Simulator (Network network) 
	{
		this.network = network;
		randomNumber = new Random();
		allTastesAndTags = new ArrayList<String>();
	}
	
	/**
	 * Adds the specified number of consumers into the network
	 *
	 * 
	 * @param numberOfConsumers Number of consumers in the network
	 * @author Raymond Wu
	 */
	private void createConsumers(int numberOfConsumers) 
	{
		int randomTaste = randomNumber.nextInt(allTastesAndTags.size());
		for (int x=0; x<numberOfConsumers;x++) 
		{	
			User user = new Consumer("Consumer"+x,allTastesAndTags.get(randomTaste));
			user.registerUser(network);
		}
	}
	
	/**
	 * Adds the specified number of producers into the network
	 * 
	 * 
	 * @param numberOfProducers Number of producers in the network
	 * @author Raymond Wu
	 */
	private void createProducers(int numberOfProducers)
	{
		int randomTaste = randomNumber.nextInt(allTastesAndTags.size());
		for (int x=0; x<numberOfProducers;x++) 
		{
			User user = new Producer("Producer"+x,allTastesAndTags.get(randomTaste));
			user.registerUser(network);
		}
	}
	
	/**
	 * Sets number of random tags in simulation based on given parameter
	 * 
	 * @param numberOfTags Number of tastes/tags to be used in network
	 * @author Raymond Wu
	 */
	private void setTastesAndTags(int numberOfTags)
	{
		while (allTastesAndTags.size() < numberOfTags) {
			//Obtain a random tag from tags
			String randomTag = tags.values()[randomNumber.nextInt(tags.values().length)].toString();
			if (!(allTastesAndTags.contains(randomTag))) {
				allTastesAndTags.add(randomTag);
			}
		}
	}
	
	/**
	 * Performs one simulation cycle which calls
	 * a user to "act"
	 * 
	 * @author Raymond Wu
	 */
	public void simulationStep() 
	{
		//Generate a random number so the simulation can get a random user
		User randomUser = network.getUsers().get(randomNumber.nextInt(network.getUsers().size()));
		
		//Ask the random user to "act"
		System.out.println("User: " + randomUser.getUserName() + " is acting!");
		randomUser.act(network);
	}
	
	/**
	 * Simple-console based implementation of the
	 * simulation
	 * 
	 * @author Raymond Wu
	 */
	public void simulationRun() 
	{
		 	int numberOfTags;
	        int numberOfProducers;
	        int numberOfConsumers;
	        String start;
	        Scanner in = new Scanner(System.in);
	        
	        System.out.println("Welcome to a simulation of a file-sharing social network");
	      /*
	        System.out.println("Enter the number of tags: (1-6)");
	        numberOfTags = in.nextInt();
	        System.out.println("Enter the number of consumers:");
	        numberOfConsumers = in.nextInt();
	        System.out.println("Enter the number of producers:");
	        numberOfProducers = in.nextInt();
	        */
	        numberOfTags = 2;
	        numberOfConsumers = 2;
	        numberOfProducers = 2;
	        
	        
	        System.out.println("Would you like to run the simulation? (yes/no)");
	        start = in.next();
	 
	        if (start.equals("yes")){
	        	System.out.println("Simulation starting:");
	          	setTastesAndTags(numberOfTags);
	        	createProducers(numberOfProducers);
	        	createConsumers(numberOfConsumers);
	        
	        	while (start.equals("yes")) {
	        		
	        		simulationStep();	
	        		
	        		System.out.println("Current users in the simulation:");
	        		for (User user : network.getUsers()) {
	        			System.out.print(user.toString() + "\n");
	        		}
	        		
	        		System.out.println("Current documents in the simulation:");
	        		for (Document document : network.getAllDocuments()) {
	        			System.out.print(document.toString() + "\n");
	        		}
	        		
	        		System.out.println("Would you like to continue the simulation? (yes/no)");
	        		start = in.next();
	        	}  
	        }
	        System.out.println("Exiting...");       
	}
		
	public static void main(String[] args){
			
			Network network = new Network();
			Simulator simulator = new Simulator(network);
			simulator.simulationRun();
	}
	
}