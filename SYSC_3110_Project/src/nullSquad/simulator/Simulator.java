package nullSquad.simulator;

import java.io.*;
import java.util.Random;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.users.*;

public class Simulator implements Serializable
{

	private static final long serialVersionUID = -8763309362472029829L;
	private FileSharingSystem fileSharingSystem;
	private int currentSimulatorSequence;
	private int totalSimulatorSequences;
	private Random randomNumber;
	byte[] previousState;

	// The log text
	public static String logText = "Welcome to the Simulator!\n\n";

	public Simulator(FileSharingSystem fileSharingSystem, int totalSequences)
	{
		this.fileSharingSystem = fileSharingSystem;
		randomNumber = new Random();
		this.currentSimulatorSequence = 0;
		this.totalSimulatorSequences = totalSequences;

		previousState = null;

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
	 * Saves the previous state of the simulator
	 * 
	 * @author MVezina
	 */
	public void savePreviousState()
	{
		try
		{
			ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
			// Write the state to the output stream
			saveState(bAOS);
			previousState = (bAOS.toByteArray());
		} catch (IOException e)
		{
			System.out.println("Failed to write save state to byte array");
			e.printStackTrace();
		}

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

	/**
	 * @return Gets the current simulator sequence
	 * @author MVezina
	 */
	public int getCurrentSimulatorSequence()
	{
		return currentSimulatorSequence;
	}

	/**
	 * Sets the current simulator sequence
	 * 
	 * @param currentSimulatorSequence The new currentSimulator sequence
	 * @author MVezina
	 */
	public void setCurrentSimulatorSequence(int currentSimulatorSequence)
	{
		this.currentSimulatorSequence = currentSimulatorSequence;
	}

	/**
	 * @return Gets the Total simulator sequence
	 * @author MVezina
	 */
	public int getTotalSimulatorSequences()
	{
		return totalSimulatorSequences;
	}

	/**
	 * Saves the state of the Simulator to the specified OutputStream
	 * 
	 * @param oS The OutputStream to write the saved state to
	 * @throws IOException
	 * @author MVezina
	 */
	public void saveState(OutputStream oS) throws IOException
	{
		if (oS == null)
			return;

		ObjectOutputStream oos = new ObjectOutputStream(oS);

		SimulatorSaveState saveState = new SimulatorSaveState(this);

		oos.writeObject(saveState);
		oos.close();
	}

	/**
	 * Restores a previously saved simulator state from an InputStream
	 * 
	 * @param iS The InputStream to read the state from
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author MVezina
	 */
	public void restoreState(InputStream iS) throws IOException, ClassNotFoundException
	{
		// Check to see if Input Stream is null
		if (iS == null)
			return;

		// Create the object input stream
		ObjectInputStream oIS = new ObjectInputStream(iS);

		// Read the Simulator State
		Object inputObject = oIS.readObject();

		// Close the stream
		oIS.close();

		// Ensure the Object is an instance of a SimulatorSaveState
		if (!(inputObject instanceof SimulatorSaveState))
			return;

		// Sets the current state of the simulator from another simulator state
		setCurrentState((SimulatorSaveState) inputObject);

	}

	/**
	 * Sets the current simulator state so that it matches the passed in
	 * simulator save state
	 * 
	 * @param saveState The simulator state to set
	 * @author MVezina
	 */
	private void setCurrentState(SimulatorSaveState saveState)
	{

		Simulator simulator = saveState.getSimulator();

		// Get the log text from the state
		logText = saveState.getLogText();

		// Set the simulator sequences
		this.currentSimulatorSequence = simulator.currentSimulatorSequence;
		this.totalSimulatorSequences = simulator.totalSimulatorSequences;

		// Set the random number instance
		this.randomNumber = simulator.randomNumber;

		// Restore the state of the file sharing system
		fileSharingSystem.restoreState(simulator.fileSharingSystem);

		// Set the previous state equal to the previous state (saved in the
		// SimulatorSaveState object)
		this.previousState = simulator.previousState;

	}

	/**
	 * @return Whether or not the Simulator can step back
	 * @author MVezina
	 */
	public boolean canStepBack()
	{
		return previousState != null;
	}

	/**
	 * Step back
	 * 
	 * @author MVezina
	 */
	public void stepBack()
	{
		// Check to see that we can step back
		if (canStepBack())
		{
			try
			{
				// Create a byte array input stream
				ByteArrayInputStream bAIS = new ByteArrayInputStream(previousState);

				// Restore the simulator state from the previous states byte
				// array
				restoreState(bAIS);

				// Set the previous state to null (
				previousState = null;

			} catch (ClassNotFoundException | IOException e)
			{
				System.out.println("Failed to restore state from stack!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Appends line t to log text
	 * 
	 * @param t The text to be appended
	 */
	public static void appendLineLog(String t)
	{
		appendLog(t + "\n");
	}

	/**
	 * Appends text t to log text
	 * 
	 * @param t The text to be appended
	 */
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

}
