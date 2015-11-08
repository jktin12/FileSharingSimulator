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

/**
 * Added Enum for Command Strings
 * 
 * @author MVezina
 */
enum SimulationCommand
{
	RUN("RUN"), STEP("STEP"), EXIT("EXIT"), STATS("STATS"), UNKNOWN("UNKWN");

	private String	cmdStr;

	/**
	 * Allows Enum Values to be associated with a command String
	 * 
	 * @param commandStr
	 */
	SimulationCommand(String commandStr)
	{
		this.cmdStr = commandStr;
	}

	/**
	 * @return Gets Command String
	 * @author MVezina
	 */
	public String getCommandStr()
	{
		return cmdStr;
	}

	/**
	 * Converts String to Simulation Command
	 * 
	 * @param input Input String to convert to Simulation Command
	 * @return Simulation Command Associated with input String
	 * @author MVezina
	 */
	public static SimulationCommand getCommandFromStr(String input)
	{
		if (input.equalsIgnoreCase(RUN.getCommandStr()))
			return RUN;
		else if (input.equalsIgnoreCase(STEP.getCommandStr()))
			return STEP;
		else if (input.equalsIgnoreCase(STATS.getCommandStr()))
			return STATS;
		else if (input.equalsIgnoreCase(EXIT.getCommandStr()))
			return EXIT;
		else
			return UNKNOWN;
	}

}

/**
 * Represents the simulator that simulates a social network
 * 
 * @author Raymond Wu
 *
 */
public class Simulator
{
	private FileSharingSystem	network;
	private Random	randomNumber;

	private enum tags
	{
		MUSIC, BOOKS, GAMING, SCHOOL, SPORTS, PROGRAMMING
	}

	/**
	 * Creates a simulator with specified network
	 * 
	 * @param network Network of all the users/documents
	 * @author Raymond Wu
	 */
	public Simulator(FileSharingSystem network, int numberOfProducers,
			int numberOfConsumers)
	{
		this.network = network;
		randomNumber = new Random();

		createProducers(numberOfProducers);
		createConsumers(numberOfConsumers);
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
		for (int x = 0; x < numberOfConsumers; x++)
		{
			User user = new Consumer("Consumer" + x, tags.values()[randomNumber.nextInt(tags.values().length)].toString());
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
		for (int x = 0; x < numberOfProducers; x++)
		{
			User user = new Producer("Producer" + x, tags.values()[randomNumber.nextInt(tags.values().length)].toString());
			user.registerUser(network);
		}
	}

	/**
	 * Prints all available commands
	 */
	public void printAllCommandStrings()
	{
		System.out.println("- " + SimulationCommand.RUN.getCommandStr() + "\n- " + SimulationCommand.STEP.getCommandStr() + "\n- " + SimulationCommand.STATS.getCommandStr() + "\n- " + SimulationCommand.EXIT.getCommandStr() + "\n");
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

		// Ask the random user to "act"
		System.out.println("Simulation Step: " + randomUser.getUserName() + " is acting!");
		randomUser.act(network);

		System.out.println("");
	}

	/**
	 * Prints the stats of the network
	 * 
	 * @author MVezina
	 */
	public void PrintStats()
	{
		System.out.println("Current users in the simulation:");
		for (User user : network.getUsers())
		{
			System.out.print(user.toString() + "\n");
		}

		System.out.println("Current documents in the simulation: " + network.getAllDocuments().size());
		for (Document document : network.getAllDocuments())
		{
			System.out.print(document.toString() + "\n\n");
		}
	}

	/**
	 * Simple-console based implementation of the
	 * simulation
	 * 
	 * @author Raymond Wu
	 */
	public void simulationRun(int stepsToRun)
	{
		while (stepsToRun-- > 0)
		{
			simulationStep();
		}
	}

}
