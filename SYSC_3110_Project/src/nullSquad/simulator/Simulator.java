package nullSquad.simulator;

import java.util.Random;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.users.*;
import nullSquad.simulator.gui.SimulatorGUI;

public class Simulator {

	private FileSharingSystem network;
	
	public FileSharingSystem getNetwork() {
		return network;
	}

	private int currentSimulatorSequence;
	
	private int totalSimulatorSequences;
	private Random randomNumber;
	
	
	public Simulator(FileSharingSystem network, int totalSequences)
	{
		this.network = network;
		randomNumber = new Random();
		this.currentSimulatorSequence = 0;
		this.totalSimulatorSequences = totalSequences;
	}
	
	/**
	 * Performs one simulation cycle which calls
	 * a user to "act"
	 * 
	 * @author Raymond Wu
	 */
	public void simulationStep()
	{
		
		// Generate a random number so the simulation can get a random user
		User randomUser = network.getUsers().get(randomNumber.nextInt(network.getUsers().size()));
		SimulatorGUI.appendLog("User: " + randomUser.getUserName() + " has been called to act!\n");
		
		randomUser.act(network, 10);
		
		// Add the payoff iteration for each user
		for(User u : network.getUsers())
		{
			u.addIterationPayoff(currentSimulatorSequence);
		}
		
		
		
		currentSimulatorSequence++;		
		
		
	}
	
	/**
	 * Adds the specified number of consumers into the network
	 *
	 * 
	 * @param numberOfConsumers Number of consumers in the network
	 * @author Raymond Wu
	 */
	public void createConsumers(int numberOfConsumers)
	{
		for (int x = 0; x < numberOfConsumers; x++)
		{
			User user = new Consumer("Consumer" + x, network.getTags().get(randomNumber.nextInt(network.getTags().size())));
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
	public void createProducers(int numberOfProducers)
	{
		for (int x = 0; x < numberOfProducers; x++)
		{
			User user = new Producer("Producer" + x, network.getTags().get(randomNumber.nextInt(network.getTags().size())));
			user.registerUser(network);
		}
	}
	
	
	public int getCurrentSimulatorSequence() {
		return currentSimulatorSequence;
	}

	public void setCurrentSimulatorSequence(int currentSimulatorSequence) {
		this.currentSimulatorSequence = currentSimulatorSequence;
	}

	public int getTotalSimulatorSequences() {
		return totalSimulatorSequences;
	}

	
}
