package nullSquad.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import nullSquad.filesharingsystem.users.*;
import nullSquad.filesharingsystem.document.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DocumentTest {
	public Producer prod;
	public Document doc;
	public Consumer cons;
	
	@Before
	public void setUp() throws Exception {
		prod = new Producer("Producer","TEST");
		doc = new Document("TestDocument", "TEST", prod);
		cons = new Consumer("TestConsumer","TEST");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * @author Marc Tebo
	 * Test case for the Document constructor
	 */
	@Test
	public void testDocument() {
		Document testDoc = new Document("TestDocument","TEST",prod);
		assertTrue(doc.equals(testDoc));
	}
	
	/**
	 * @author Marc Tebo
	 * Test case for the addLikeListener method
	 */
	@Test
	public void testAddLikeListener() {
		doc.addLikeListener(prod);
		assertTrue(doc.getLikeListeners().contains(prod));
	}
	
	/**
	 * @author Marc Tebo
	 * Test case for the removeLikeListener method
	 */
	@Test
	public void testRemoveLikeListener() {
		assertTrue(doc.getLikeListeners().contains(prod));
		doc.removeLikeListener(prod);
		assertTrue(!doc.getLikeListeners().contains(prod));
	}
	
	/**
	 * @author Marc Tebo
	 * Test case for the getLikeListeners method
	 */
	@Test
	public void testGetLikeListeners(){
		Producer testProd = new Producer("TestProducer","TEST");
		doc.addLikeListener(testProd);
		assertTrue(doc.getLikeListeners().contains(prod) && doc.getLikeListeners().contains(testProd));
	}
	
	/**
	 * @author Marc Tebo
	 * Test case for the likeDocument method
	 */
	@Test
	public void testLikeDocument() {
		doc.likeDocument(cons);
		assertTrue(doc.getUserLikes().contains(cons));
	}

	/**
	 * @author Marc Tebo
	 * Test case for the unlikeDocument method
	 */
	@Test
	public void testUnlikeDocument() {
		doc.likeDocument(cons);
		assertTrue(doc.getUserLikes().contains(cons));
		doc.unlikeDocument(cons);
		assertTrue(!doc.getUserLikes().contains(cons));
	}

	/**
	 * @author Marc Tebo
	 * Test case for the getUserLikes method
	 */
	@Test
	public void testGetUserLikes() {
		Consumer testCons = new Consumer("TestConsumer2","TEST");
		doc.likeDocument(cons);
		doc.likeDocument(testCons);
		assertTrue(doc.getUserLikes().contains(cons)&& doc.getUserLikes().contains(testCons));
	}

	/**
	 * @author Marc Tebo
	 * Test case for the toString method
	 */
	@Test
	public void testToString() {
		boolean result = true;
		result &= this.doc.toString().contains(this.doc.getDocumentName());
		result &= this.doc.toString().contains(this.doc.getTag());
		result &= this.doc.toString().contains(this.doc.getUserLikes().size() + "");
		
		assertTrue(result);
	}

	/**
	 * @author Marc Tebo
	 * Test case for the equals method
	 */
	@Test
	public void testEqualsObject() {
		Document testDoc1 = new Document("TestDocument", "TEST", prod);
		Document testDoc2 = new Document("TestDocument", "TEST", prod);
		assertTrue(testDoc1.equals(testDoc2));
	}
	
	/**
	 * @author Marc Tebo
	 * Test case for the getTag method
	 */
	@Test
	public void testGetTag() {
		assertTrue(doc.getTag().equals("TEST"));
	}
	
	/**
	 * @author Marc Tebo
	 * Test case for the getDocumentID method
	 */
	@Test
	public void testGetDocumentID() {
		doc.setDocumentID(1234);
		assertEquals(doc.getDocumentID(),1234);
	}

	/**
	 * @author Marc Tebo
	 * Test case for the getDateUploaded method
	 */
	@Test
	public void testGetDateUploaded() {
		Document testDoc = new Document("TestDocument","TEST",prod);
		assertTrue(testDoc.getDateUploaded().getTime() > 0);
	}

	/**
	 * @author Marc Tebo
	 * Test case for the testGetDocumentName method
	 */
	@Test
	public void testGetDocumentName() {
		assertEquals(doc.getDocumentName(),"TestDocument");
	}

	/**
	 * @author Marc Tebo
	 * Test case for the getProducer method
	 */
	@Test
	public void testGetProducer() {
		assertEquals(doc.getProducer(),prod);
	}

	/**
	 * @author Marc Tebo
	 * Test case for setDocumentID method
	 */
	@Test
	public void testSetDocumentID() {
		assertEquals(doc.getDocumentID(),0);
		doc.setDocumentID(12345);
		assertEquals(doc.getDocumentID(),12345);
	}

	/**
	 * @author Marc Tebo
	 * Test case for the compareTo method
	 */
	@Test
	public void testCompareTo() {
		Document testDoc = new Document("TestDocument2","TEST",prod);
		doc.likeDocument(prod);
		testDoc.likeDocument(prod);
		testDoc.likeDocument(cons);
		
		assertEquals(testDoc.compareTo(doc), 1);
		
		testDoc.unlikeDocument(cons);
		assertEquals(testDoc.compareTo(doc),0);
		
		testDoc.unlikeDocument(prod);
		assertEquals(testDoc.compareTo(doc), -1);
	}
}
