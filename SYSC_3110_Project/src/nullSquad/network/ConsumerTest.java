package nullSquad.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nullSquad.document.Document;
import nullSquad.simulator.Simulator.tags;

public class ConsumerTest {

	FileSharingSystem network1, network2;
	User consumer1, consumer2;
	User producer1;
	private Document docA, docB, docC, docD, docE, docF;
	
	
	@Before
	public void setUp() throws Exception {
		consumer1 = new Consumer("Bob", tags.PROGRAMMING.toString());
		consumer2 = new Consumer("Bob", tags.PROGRAMMING.toString());
		network1 = new FileSharingSystem();
		network2 = new FileSharingSystem();
		producer1 = new Producer("Jim", tags.PROGRAMMING.toString());
		docA = new Document("docA", tags.PROGRAMMING.toString(), (Producer)producer1);
		docB = new Document("docB", tags.BOOKS.toString(), (Producer)producer1);
		docC = new Document("docC", tags.MUSIC.toString(), (Producer)producer1);
		docD = new Document("docD", tags.SPORTS.toString(), (Producer)producer1);
		docE = new Document("docE", tags.PROGRAMMING.toString(), (Producer)producer1);
		docF = new Document("docF", tags.PROGRAMMING.toString(), (Producer)producer1);
				
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAct() {
		network1.addDocument(docA);
		network1.addDocument(docE);
		network1.addDocument(docF);
		consumer1.registerUser(network1);
		producer1.registerUser(network1);
		consumer1.act(network1, 3);
		boolean assertion = (consumer1.getLikedDocuments().contains(docA) || 
							consumer1.getLikedDocuments().contains(docE) ||
							consumer1.getLikedDocuments().contains(docF));
		
		assertTrue(assertion);
		
		network2.addDocument(docA);
		network2.addDocument(docB);
		network2.addDocument(docC);
		consumer2.registerUser(network2);
		consumer2.act(network2, 1);
		assertion = (!consumer2.getLikedDocuments().contains(docB) &&
					!consumer2.getLikedDocuments().contains(docC));
		assertTrue(assertion);
	}

	@Test
	public void testEquals() {
		Consumer consumer3 = new Consumer("Justin", tags.BOOKS.toString());
		assertTrue(consumer1.equals(consumer2));
		assertTrue(consumer2.equals(consumer1));
		assertTrue(consumer2.equals(consumer2));
		assertTrue(!consumer2.equals(consumer3));
	}

	@Test
	public void testToString() {
		assertEquals( 
				("User Type: Consumer\n"+
				"User ID: "+consumer1.getUserID()+
				"\nUser Name: "+consumer1.getUserName()+
				"\nTaste: "+consumer1.getTaste().toString()+
				"\nFollowers: "+consumer1.getFollowers().size()+
				"\nFollowing: "+consumer1.getFollowing().size()+
				"\nNumber of Documents Liked: "+consumer1.getLikedDocuments().size()+
				"\n"),
				consumer1.toString()
		);
		
	}

	@Test
	public void testConstructor2Args() {
		Consumer consumer2 = new Consumer("John", tags.BOOKS.toString());
		assertEquals("John", consumer2.getUserName());
		assertEquals("BOOKS", consumer2.getTaste());
	}


	@Test
	public void testCalculatePayoff() {
		Consumer consumer3 = new Consumer("Bob", tags.PROGRAMMING.toString());
		
		network1.addDocument(docA);
		network1.addDocument(docB);
		network1.addDocument(docC);
		network1.addDocument(docD);
		network1.addDocument(docE);
		
		consumer3.registerUser(network1);
		assertEquals(1, consumer3.calculatePayoff(network1.search(consumer3, 3)));
		assertEquals(2, consumer3.calculatePayoff(network1.search(consumer3, 2)));
		assertEquals(-1, consumer3.calculatePayoff(network1.search(consumer3, 5)));
		consumer3.followUser(producer1);
		producer1.likeDocument(docA);
		producer1.likeDocument(docB);
		producer1.likeDocument(docE);
		
		assertEquals(5, consumer3.calculatePayoff(network1.search(consumer3, 5)));
	}

}