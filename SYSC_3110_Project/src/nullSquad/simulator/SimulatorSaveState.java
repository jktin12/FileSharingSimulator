/**
 * 
 */
package nullSquad.simulator;

import java.io.Serializable;

/**
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

	public String getLogText()
	{
		return this.logTextState;
	}

	public Simulator getSimulator()
	{
		return this.simulatorState;
	}

}
