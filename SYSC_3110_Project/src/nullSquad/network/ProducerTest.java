/**
 * Title: SYSC 3110 Project
 * Created By: Raymond Wu
 * Student Number: 100938326
 * Team: nullSquad
 */
package nullSquad.network;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import nullSquad.document.Document;
import nullSquad.network.Consumer;
import nullSquad.network.FileSharingSystem;
import nullSquad.network.Producer;
import nullSquad.strategies.ProducerPayoffStrategy;

public class ProducerTest {

	private Producer producer1;
	private Producer producer2;
	private ProducerPayoffStrategy strategy;
	private FileSharingSystem network;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception {
		strategy = null;
		producer1 = new Producer("TestName","TEST");
		producer2 = new Producer(strategy,"TestName","TEST");
		network = new FileSharingSystem();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	}
	
	/**
	 * Test case for the Producer constructor that takes
	 * variables ProducerPayoffStrategy, String and String
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testProducerProducerPayoffStrategyStringString() {
		Producer testProducer = new Producer (strategy,"TestName","TEST");
		assertTrue(producer2.equals(testProducer));
	}
	
	/**
	 *  Test case for the Producer constructor that takes
	 *  variables String and String
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testProducerStringString() {
		Producer testProducer = new Producer ("TestName","TEST");
		assertTrue(producer1.equals(testProducer));
	}
	
	/**
	 * Test case for the act method
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testAct() {
		Document testDoc = new Document("TestAct","TEST",producer1);
		Producer testProducer = new Producer("TestName","TEST");
		Consumer testConsumer = new Consumer("TestName2","TEST");
		testDoc.likeDocument(testConsumer);
		network.addDocument(testDoc);
		network.registerUser(testProducer);
		network.registerUser(testConsumer);
		network.registerUser(producer1);
		testProducer.act(network, 10);
		assertEquals(testProducer.getDocumentsProduced().size(),0);
	}
	
	/**
	 * Test case for the addFollower method
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testAddFollower() {
		Producer testProducer = new Producer ("TestName","TEST");
		Consumer testConsumer = new Consumer ("TestName","TEST");
		testProducer.addFollower(testConsumer);
		assertTrue(testProducer.getFollowers().contains(testConsumer));
	}
	
	/**
	 * Test case for the calculatePayoff method
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testCalculatePayoff() {
		Producer testProducer = new Producer("TestName","TEST");
		assertEquals(testProducer.calculatePayoff(),producer1.calculatePayoff());
	}
	
	/**
	 * Test case for the payoffStrategy method
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testProducerPayoffStrategy() {
		Producer testProducer = new Producer("TestName","TEST");
		assertEquals(testProducer.producerPayoffStrategy(testProducer),producer1.producerPayoffStrategy(producer1));
	}
	
	/**
	 * Test case for the getDocumentsProduced method
	 * with an empty list
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testGetDocumentsProducedEmpty() {
		List<Document> docsProduced = new ArrayList<Document>();;
		assertEquals(producer1.getDocumentsProduced(),docsProduced);
	}
	
	/**
	 * Test case for the getDocumentsProduced method
	 * 
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testGetDocumentsProduced() {
		List<Document> docsProduced = new ArrayList<Document>();
		Producer testProducer = new Producer ("TestName","TEST");
		Document doc = new Document("TestDocument","TEST",testProducer);
		Document doc2 = new Document("TestDocument","TEST",testProducer);
		docsProduced.add(doc);
		docsProduced.add(doc2);
		testProducer.getDocumentsProduced().add(doc);
		testProducer.getDocumentsProduced().add(doc2);
		assertEquals(testProducer.getDocumentsProduced(),docsProduced);
	}
	
	/**
	 * Test case for the documentLiked method
	 * 
	 * @author Raymond Wu
	 */
	@Test
	public void testDocumentLiked() {
		System.out.print("Producer: " + producer1.getUserName() + " Document has been liked! Current Payoff: " + producer1.calculatePayoff());
		assertEquals("Producer: " + producer1.getUserName() + " Document has been liked! Current Payoff: " + producer1.calculatePayoff(), outContent.toString());
	}

}