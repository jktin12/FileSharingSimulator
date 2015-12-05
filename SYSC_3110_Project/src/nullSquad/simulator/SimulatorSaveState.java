/**
 * 
 */
package nullSquad.simulator;

import java.io.Serializable;

/**
 * Represents a saved simulator state
 * 
 * @author MVezina
 */
public class SimulatorSaveState implements Serializable
{
	private static final long serialVersionUID = 6037231624539182213L;
	private Simulator simulatorState;
	private String logTextState;

	public SimulatorSaveState(Simulator simulator)
	{
		this.simulatorState = simulator;
		this.logTextState = Simulator.logText;
	}

	/**
	 * @return Get the log text
	 * @author MVezina
	 */
	public String getLogText()
	{
		return this.logTextState;
	}

	/**
	 * @return Get the simulator
	 * @author MVezina
	 */
	public Simulator getSimulator()
	{
		return this.simulatorState;
	}

}
