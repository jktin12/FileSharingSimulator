package nullSquad.simulator;

import java.util.Random;
import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.users.*;

public class Simulator implements XMLSerializable
{

	private FileSharingSystem fileSharingSystem;
	private int currentSimulatorSequence;
	private int totalSimulatorSequences;
	private Random randomNumber;

	// The log text
	public static String logText = "Welcome to the Simulator!\n\n";

	public Simulator(FileSharingSystem fileSharingSystem, int totalSequences)
	{
		this.fileSharingSystem = fileSharingSystem;
		randomNumber = new Random();
		this.currentSimulatorSequence = 0;
		this.totalSimulatorSequences = totalSequences;
	}

	/**
	 * @return The FileSharingSystem used by the simulator
	 * @author MVezina
	 */
	public FileSharingSystem getFileSharingSystem()
	{
		return this.fileSharingSystem;
	}

	/**
	 * Performs one simulation cycle which calls a user to "act"
	 * 
	 * @author Raymond Wu
	 */
	public void simulationStep()
	{

		// Generate a random number so the simulation can get a random user
		User randomUser = fileSharingSystem.getUsers().get(randomNumber.nextInt(fileSharingSystem.getUsers().size()));

		String startText = " === " + randomUser.getUserName() + " has been called to act: === ";
		Simulator.appendLineLog(startText);

		randomUser.act(fileSharingSystem, 10);

		Simulator.appendLineLog("\n");

		// Add the payoff iteration for each user
		for (User u : fileSharingSystem.getUsers())
		{
			u.addIterationPayoff(currentSimulatorSequence);
		}

		currentSimulatorSequence++;

	}

	/**
	 * Adds the specified number of consumers into the file sharing system
	 *
	 * @param numberOfConsumers Number of consumers in the file sharing system
	 * @author Raymond Wu
	 */
	public void createConsumers(int numberOfConsumers)
	{
		for (int x = 0; x < numberOfConsumers; x++)
		{
			User user = new Consumer("Consumer" + x, fileSharingSystem.getTags().get(randomNumber.nextInt(fileSharingSystem.getTags().size())));
			user.registerUser(fileSharingSystem);
		}
	}

	/**
	 * Adds the specified number of producers into the file sharing system
	 * 
	 * @param numberOfProducers Number of producers in the file sharing system
	 * @author Raymond Wu
	 */
	public void createProducers(int numberOfProducers)
	{
		for (int x = 0; x < numberOfProducers; x++)
		{
			User user = new Producer("Producer" + x, fileSharingSystem.getTags().get(randomNumber.nextInt(fileSharingSystem.getTags().size())));
			user.registerUser(fileSharingSystem);
		}
	}

	public int getCurrentSimulatorSequence()
	{
		return currentSimulatorSequence;
	}

	public void setCurrentSimulatorSequence(int currentSimulatorSequence)
	{
		this.currentSimulatorSequence = currentSimulatorSequence;
	}

	public int getTotalSimulatorSequences()
	{
		return totalSimulatorSequences;
	}

	/**
	 * Appends text t to log text
	 * 
	 * @param t The text to be appended
	 */
	public static void appendLineLog(String t)
	{
		appendLog(t + "\n");
	}

	public static void appendLog(String t)
	{
		logText += t;
	}

	/**
	 * Clears log text
	 */
	public static void clearLog()
	{
		logText = "";
	}

	/* (non-Javadoc)
	 * @see nullSquad.simulator.XMLSerializable#toXML()
	 */
	@Override
	public String toXML()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nullSquad.simulator.XMLSerializable#readXML(java.lang.String)
	 */
	@Override
	public void importFromXML(String xmlObject)
	{
		// TODO Auto-generated method stub
		
	}

}
