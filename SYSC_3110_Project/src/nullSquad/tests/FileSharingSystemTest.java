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

import nullSquad.filesharingsystem.users.*;
import nullSquad.filesharingsystem.document.*;
import nullSquad.filesharingsystem.*;

public class FileSharingSystemTest {

	private FileSharingSystem network;
	private User consumer1, producer1;
	private Document docA, docB, docC, docD, docE;
    String programmingTag, bookTag, musicTag, sportsTag;
    List<String> tags ;
	
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
        
		 network = new FileSharingSystem(tags);
		
		consumer1 = new Consumer("John", programmingTag);
		producer1 = new Producer("Jim", programmingTag);
		docA = new Document("docA", programmingTag, (Producer)producer1);
		docB = new Document("docB", bookTag, (Producer)producer1);
		docC = new Document("docC", musicTag, (Producer)producer1);
		docD = new Document("docD", sportsTag, (Producer)producer1);
		docE = new Document("docE", programmingTag, (Producer)producer1);

		consumer1.likeDocument(docB);
		
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
		assertTrue(network.addDocument(docA));
		assertTrue(network.addDocument(docB));
		assertTrue(network.addDocument(docC));
		assertTrue(network.addDocument(docD));
		
		assertEquals(4, network.search(consumer1, 6).size());		
		assertEquals(4, network.search(consumer1, 4).size());
		assertEquals(1, network.search(consumer1, -1).size());
		
	}

	@Test
	public void testAddDocument() {
		assertTrue(network.addDocument(docA));
		assertTrue(network.getAllDocuments().contains(docA));
		assertEquals(network.getAllDocuments().size(),1);
		
		assertTrue(network.addDocument(docB));
		assertTrue(network.getAllDocuments().contains(docB));
		assertEquals(network.getAllDocuments().size(),2);
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
		assertTrue(network.addDocument(docA));
		assertTrue(network.addDocument(docB));
		assertTrue(network.addDocument(docC));
		assertTrue(network.addDocument(docD));
		
		
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
