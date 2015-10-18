/**
 * Title: SYSC 3110 Project
 * Created By: Raymond Wu			
 * Student Number: 100938326
 * Team: nullSquad
 */
package nullSquad.simulator;

import nullSquad.network.*;
import java.util.*;

public class Simulator
{
	private Network network;
	private Random randomNumber;
	private List<String> tastesAndTags;
	
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
		tastesAndTags = new ArrayList<String>();
	}
	
	/**
	 * Adds the specified number of consumers into the network
	 * with userID x
	 * 
	 * @param numberOfConsumers Number of consumers in the network
	 * @author Raymond Wu
	 */
	private void addConsumers(int numberOfConsumers) 
	{
		int randomTaste = randomNumber.nextInt(tastesAndTags.size());
		for (int x=0; x<numberOfConsumers;x++) {
			User user = new Consumer(x,tastesAndTags.get(randomTaste));
			network.registerUser(user);
		}
	}
	
	/**
	 * Adds the specified number of producers into the network
	 * with userID 10x
	 * 
	 * @param numberOfProducers Number of producers in the network
	 * @author Raymond Wu
	 */
	private void addProducers(int numberOfProducers)
	{
		int randomTaste = randomNumber.nextInt(tastesAndTags.size());
		for (int x=0; x<numberOfProducers;x++) {
			User user = new Producer(x+100,tastesAndTags.get(randomTaste));
			network.registerUser(user);
		}
	}
	
	/**
	 * Asks the user to input different tastes and tags
	 * 
	 * @param numberOfTags Number of tastes/tags to be used in network
	 * @author Raymond Wu
	 */
	private void addTastesAndTags(int numberOfTags)
	{
		Scanner in = new Scanner(System.in);
		String tag;
		
		System.out.println("Please enter" + numberOfTags + " tastes/tags");
		for (int x=0; x<numberOfTags;x++) {
			tag = in.next();
			tastesAndTags.add(tag);
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
		int randomUser = randomNumber.nextInt(network.getUsers().size());
		
		//Ask the random user to "act"
		network.getUsers().get(randomUser).act(network);
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
	        
	        System.out.println("Enter the number of tags:");
	        numberOfTags = in.nextInt();
	        System.out.println("Enter the number of consumers:");
	        numberOfConsumers = in.nextInt();
	        System.out.println("Enter the number of producers:");
	        numberOfProducers = in.nextInt();
	        
	        System.out.println("Would you like to run the simulation? (y/n)");
	        start = in.next();
	        
	        while ((start!="y") || (start!="n")) {
	        	System.out.println("Invalid input please enter either y/n");
	        	start = in.next();
	        }
	       
	        if (start=="y"){
	        	System.out.println("Simulation starting:");
	          	addTastesAndTags(numberOfTags);
	        	addProducers(numberOfProducers);
	        	addConsumers(numberOfConsumers);
	  
	        	
	        	while (start=="y") {
	        		
	        		simulationStep();	
	        		
	        		System.out.println("Current users in the simulation:");
	        		for (User user:network.getUsers()) {
	        			System.out.print(user.toString());
	        		}
	        		
	        		System.out.println("Current documents in the simulation:");
	        		for (Document document:network.getAllDocuments()) {
	        			System.out.print(document.toString());
	        		}
	        		
	        		System.out.println("Would you like to continue the simulation? (y/n)");
	        		start = in.next();
	        		
	        		while ((start!="y") || (start!="n")) {
	        			System.out.println("Invalid input please enter either y/n");
	      	        	start = in.next();
	      	        }
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