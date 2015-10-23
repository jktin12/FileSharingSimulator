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
 * @author Raymond Wu
 *
 */
public class Simulator
{
	private Network	network;
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
	public Simulator(Network network)
	{
		this.network = network;
		randomNumber = new Random();
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
		System.out.println("- " + SimulationCommand.RUN.getCommandStr() + "\n- " + SimulationCommand.STEP.getCommandStr() +"\n- " + SimulationCommand.STATS.getCommandStr() + "\n- " + SimulationCommand.EXIT.getCommandStr() + "\n");
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
	public void simulationRun()
	{
		int numberOfProducers;
		int numberOfConsumers;
		String start;
		Scanner in = new Scanner(System.in);

		System.out.println("Welcome to a simulation of a file-sharing social network");

		System.out.println("Enter the number of consumers:");
		numberOfConsumers = in.nextInt();
		System.out.println("Enter the number of producers:");
		numberOfProducers = in.nextInt();

		System.out.println("Would you like to run the simulation? (yes/no)");
		start = in.next();

		while (!start.equalsIgnoreCase("yes") && !start.equalsIgnoreCase("no"))
		{
			System.out.println("Would you like to run the simulation? (yes/no)");
			start = in.next();
		}

		if (start.equalsIgnoreCase("yes"))
		{
			System.out.println("Simulation starting:");

			createProducers(numberOfProducers);
			createConsumers(numberOfConsumers);

			while (true)
			{
				System.out.println("Available Commands:");
				printAllCommandStrings();

				System.out.print("Please Enter a Command: ");
				start = in.next();

				System.out.println();

				switch (SimulationCommand.getCommandFromStr(start))
				{

					case RUN:
					{
						System.out.print("Please Enter the Number of Steps to Execute: ");
						int n = in.nextInt();
						if (n <= 0)
							n = 1;

						while (n-- > 0)
							simulationStep();
					}
						break;
					// Continue the simulation
					case STEP:
						simulationStep();
						break;

					// Print out all the stats
					case STATS:
						PrintStats();
						break;

					case EXIT:
						System.out.println("Exiting Simulator..");
						System.out.println("Halted Simulator");
						System.exit(0);
						break;

					// Any other input results in asking the user again
					default:
						break;

				}

			}
		}
		System.out.println("Exiting...");
	}

	public static void main(String[] args)
	{

		Network network = new Network();
		Simulator simulator = new Simulator(network);
		simulator.simulationRun();
	}

}
