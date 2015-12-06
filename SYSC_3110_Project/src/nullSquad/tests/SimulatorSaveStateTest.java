package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nullSquad.filesharingsystem.FileSharingSystem;
import nullSquad.simulator.Simulator;
import nullSquad.simulator.SimulatorSaveState;

public class SimulatorSaveStateTest {

	private SimulatorSaveState saveState;
	private FileSharingSystem network;
	private Simulator simulator;
    private String programmingTag, bookTag, musicTag, sportsTag;
    private List<String> tags ;
	private String logTextState;
	
	@Before
	public void setUp() throws Exception {
		programmingTag = "Programming";
        bookTag = "Book";
        musicTag = "Music";
        sportsTag = "Sports";
     
        tags = new ArrayList<>();
		tags.add(programmingTag);
		tags.add(bookTag);
		tags.add(musicTag);
		tags.add(sportsTag);
		logTextState = Simulator.logText;
		network = new FileSharingSystem(tags);
		simulator = new Simulator(network,10);
		
		saveState = new SimulatorSaveState(simulator);
		
	}

	@Test
	public void testSimulatorSaveState() {
		assertEquals(saveState.getSimulator(),simulator);
		assertEquals(Simulator.logText,logTextState);
	}

	@Test
	public void testGetLogText() {
		assertEquals(saveState.getLogText(),logTextState);
	}

	@Test
	public void testGetSimulator() {
		assertEquals(saveState.getSimulator(),simulator);
	}

}
