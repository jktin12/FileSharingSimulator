package nullSquad.simulator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import nullSquad.filesharingsystem.*;
import nullSquad.filesharingsystem.users.*;

public class Simulator implements Serializable
{

	/**
	 * 
	 */
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

	public void saveState(OutputStream oS) throws IOException
	{
		if (oS == null)
			return;

		ObjectOutputStream oos = new ObjectOutputStream(oS);

		SimulatorSaveState saveState = new SimulatorSaveState(this);

		oos.writeObject(saveState);
		oos.close();
	}

	public void restoreState(InputStream iS) throws IOException, ClassNotFoundException
	{
		if (iS == null)
			return;

		ObjectInputStream oIS = new ObjectInputStream(iS);

		// Read the first (Simulator) object
		Object inputObject = oIS.readObject();
		oIS.close();

		if (!(inputObject instanceof SimulatorSaveState))
		{
			return;
		}

		setCurrentState((SimulatorSaveState) inputObject);

	}

	private void setCurrentState(SimulatorSaveState saveState)
	{

		Simulator simulator = saveState.getSimulator();
		logText = saveState.getLogText();

		this.currentSimulatorSequence = simulator.currentSimulatorSequence;
		this.totalSimulatorSequences = simulator.totalSimulatorSequences;

		this.randomNumber = simulator.randomNumber;
		fileSharingSystem.restoreState(simulator.fileSharingSystem);

		this.previousState = simulator.previousState;

	}

	public boolean canStepBack()
	{
		return previousState != null;
	}

	/**
	 * @return
	 * @author MVezina
	 */
	public void stepBack()
	{
		if (canStepBack())
		{

			try
			{
				ByteArrayInputStream bAIS = new ByteArrayInputStream(previousState);
				restoreState(bAIS);
				
				previousState = null;
				
			} catch (ClassNotFoundException | IOException e)
			{
				System.out.println("Failed to restore state from stack!");
				e.printStackTrace();
			}
		}
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

}
