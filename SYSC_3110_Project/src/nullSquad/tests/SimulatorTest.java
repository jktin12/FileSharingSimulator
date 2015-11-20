/**
 * Title: SYSC 3110 Project
 * Created By: Raymond Wu
 * Student Number: 100938326
 * Team: nullSquad
 */

package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nullSquad.filesharingsystem.FileSharingSystem;
import nullSquad.filesharingsystem.users.Consumer;
import nullSquad.filesharingsystem.users.Producer;
import nullSquad.simulator.Simulator;

public class SimulatorTest {

	private FileSharingSystem network;
	private Simulator simulator;
    private String programmingTag, bookTag, musicTag, sportsTag;
    private List<String> tags ;
	private int currentSimulatorSequenceTest;
	private int totalSimulatorSequencesTest;
	
	
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
			
			totalSimulatorSequencesTest=10;
			currentSimulatorSequenceTest=0;
			
			network = new FileSharingSystem(tags);
			simulator = new Simulator(network,totalSimulatorSequencesTest);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNetwork() {
		assertEquals(simulator.getNetwork(),network);
	}

	@Test
	public void testSimulator() {
		assertEquals(simulator.getTotalSimulatorSequences(),totalSimulatorSequencesTest);
		assertEquals(simulator.getCurrentSimulatorSequence(),currentSimulatorSequenceTest);
		assertEquals(simulator.getNetwork(),network);
	}

	@Test
	public void testCreateConsumersEmpty() {
		simulator.createConsumers(0);
		assertTrue(network.getUsers().isEmpty());
	}
	
	@Test
	public void testCreateConsumers(){
		simulator.createConsumers(2);
		assertEquals(network.getUsers().size(),2);
		assertTrue(network.getUsers().get(0)instanceof Consumer);
		assertTrue(network.getUsers().get(1)instanceof Consumer);
		network.deactivateUser(network.getUsers().get(1));
		network.deactivateUser(network.getUsers().get(0));
	}
	
	@Test
	public void testCreateProducersEmpty(){
		simulator.createProducers(0);
		assertTrue(network.getUsers().isEmpty());
	}

	@Test
	public void testCreateProducers() {
		simulator.createProducers(2);
		assertEquals(network.getUsers().size(),2);
		assertTrue(network.getUsers().get(0)instanceof Producer);
		assertTrue(network.getUsers().get(0)instanceof Producer);
		network.deactivateUser(network.getUsers().get(1));
		network.deactivateUser(network.getUsers().get(0));
	}

	@Test
	public void testGetCurrentSimulatorSequence() {
		assertEquals(simulator.getCurrentSimulatorSequence(),currentSimulatorSequenceTest);
	}

	@Test
	public void testSetCurrentSimulatorSequence() {
		simulator.setCurrentSimulatorSequence(2);
		currentSimulatorSequenceTest=2;
		assertEquals(simulator.getCurrentSimulatorSequence(),currentSimulatorSequenceTest);
	}

	@Test
	public void testGetTotalSimulatorSequences() {
		assertEquals(simulator.getTotalSimulatorSequences(),totalSimulatorSequencesTest);
	}

}
