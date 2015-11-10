package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nullSquad.document.Document;
import nullSquad.network.Consumer;
import nullSquad.network.FileSharingSystem;
import nullSquad.network.Producer;
import nullSquad.network.User;

public class FileSharingSystemTest {

	private FileSharingSystem network;
	private User consumer1, producer1;
	private Document docA, docB, docC, docD, docE;
//	private List<Document> documents;
	
	@Before
	public void setUp() throws Exception {
		network = new FileSharingSystem();
		consumer1 = new Consumer("John", "Programming");
		producer1 = new Producer("Jim", "Programming");
		docA = new Document("docA", "Programming", (Producer)producer1);
		docB = new Document("docB", "Books", (Producer)producer1);
		docC = new Document("docC", "Music", (Producer)producer1);
		docD = new Document("docD", "Sports", (Producer)producer1);
		docE = new Document("docE", "Programming", (Producer)producer1);
/*		documents = new ArrayList<Document>();
		documents.add(docA);
		documents.add(docB);
		documents.add(docC);
		documents.add(docD);
		documents.add(docE);
*/
	}

	@After
	public void tearDown() throws Exception {
	}

	//No need to test constructor

	@Test
	public void testRegisterUser() {
		assertTrue(network.registerUser(consumer1) != -1);
		assertTrue(network.getUsers().contains(consumer1));
	}

	@Test
	public void testDeactivateUser() {
		consumer1.registerUser(network);
		assertTrue(network.getUsers().contains(consumer1));
		assertTrue(network.deactivateUser(consumer1));
		assertTrue(!network.getUsers().contains(consumer1));
	}

	@Test
	public void testSearch() {
		network.addDocument(docA);
		network.addDocument(docB);
		network.addDocument(docC);
		network.addDocument(docD);
		network.addDocument(docE);
		
		assertEquals(5, network.search(consumer1, 6).size());
		assertEquals(4, network.search(consumer1, 4).size());
		
		List<Document> list;
		list = new ArrayList<>();
		list= network.search(consumer1, 2);
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		
		assertEquals("Programming", network.search(consumer1, 2).get(0).getTag());
		assertEquals("Programming", network.search(consumer1, 2).get(1).getTag());
	}

	@Test
	public void testAddDocument() {
		assertTrue(network.addDocument(docA));
		assertTrue(network.getAllDocuments().contains(docA));
		assertTrue(network.addDocument(docB));
		assertTrue(network.getAllDocuments().contains(docB));
	}

	@Test
	public void testRemoveDocument() {
		assertTrue(network.addDocument(docA));
		assertTrue(network.getAllDocuments().contains(docA));
		assertTrue(network.removeDocument(docA));
		assertTrue(!network.getAllDocuments().contains(docA));
	}

	@Test
	public void testGetAllDocuments() {
		network.addDocument(docA);
		network.addDocument(docB);
		network.addDocument(docC);
		network.addDocument(docD);
		assertTrue(network.getAllDocuments().contains(docA));
		assertTrue(network.getAllDocuments().contains(docB));
		assertTrue(network.getAllDocuments().contains(docC));
		assertTrue(network.getAllDocuments().contains(docD));
		assertTrue(!network.getAllDocuments().contains(docE));
		network.removeDocument(docA);
		assertTrue(!network.getAllDocuments().contains(docA));
	}

	@Test
	public void testGetUsers() {
		consumer1.registerUser(network);
		producer1.registerUser(network);
		assertTrue(network.getUsers().contains(consumer1));
		assertTrue(network.getUsers().contains(producer1));
		network.deactivateUser(producer1);
		assertTrue(!network.getUsers().contains(producer1));
	}

}
