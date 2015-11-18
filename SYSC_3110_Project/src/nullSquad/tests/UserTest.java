/**
 * Title: SYSC 3110 Project
 * Created By: Marc teBoekhorst
 * Student Number: 100925246
 * Team: nullSquad
 */
package nullSquad.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nullSquad.filesharingsystem.users.*;
import nullSquad.filesharingsystem.document.*;
import nullSquad.filesharingsystem.*;

public class UserTest {
	public User user1;
	public User user2;
	public User user3;
	FileSharingSystem network;
	List<String> tags ;
	
	@Before
	public void setUp() throws Exception {
		user1 = new Producer("TestUser1","TEST");
		user2 = new Consumer("TestUser2","TEST");
		user3 = new Consumer("TestUser3","TEST");
		
		
		network = new FileSharingSystem(null);
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * @author Marc Tebo
	 * Test function for the User constructor
	 */
	@Test
	public void testUser() {
		User testUser1 = new Producer("TestUser1","TEST");
		User testUser2 = new Consumer("TestUser2", "TEST");
		
		assertTrue(user1.equals(testUser1) && user2.equals(testUser2));
	}

	/**
	 * @author Marc Tebo
	 * Test function for the registerUser method
	 */
	@Test
	public void testRegisterUser() {
		user1.registerUser(network);
		user2.registerUser(network);
		
		assertTrue(network.getUsers().contains(user1)&& network.getUsers().contains(user2));
	}

	/**
	 * @author Marc Tebo
	 * Test function for the addFollower method
	 */
	@Test
	public void testAddFollower() {
		user1.addFollower(user2);

		assertTrue(user1.getFollowers().contains(user2));
	}

	/**
	 * @author Marc Tebo
	 * Test function for the removeFollwer method
	 */
	@Test
	public void testRemoveFollower() {
		user1.addFollower(user2);
		assertTrue(user1.getFollowers().contains(user2));
		
		user1.removeFollower(user2);
		assertTrue(!user1.getFollowers().contains(user2));
	}

	/**
	 * @author Marc Tebo
	 * Test function for the getUserID method
	 */
	@Test
	public void testGetUserID() {
		user1.registerUser(network);
		user2.registerUser(network);
		user3.registerUser(network);
		
		assertEquals(user1.getUserID(),1);
		assertEquals(user2.getUserID(),2);
		assertEquals(user3.getUserID(),3);
	}

	/**
	 * @author Marc Tebo
	 * Test case for the getUserName method
	 */
	@Test
	public void testGetUserName() {
		assertEquals(user1.getUserName(),"TestUser1");
	}

	/**
	 * @author Marc Tebo
	 * Test case for the getTaste method
	 */
	@Test
	public void testGetTaste() {
		assertEquals(user1.getTaste(),"TEST");
	}

	/**
	 * @author Marc Tebo
	 * Test case for the getFollowers method
	 */
	@Test
	public void testGetFollowers() {
		user2.followUser(user1);
		user3.followUser(user1);
		
		assertTrue(user1.getFollowers().contains(user2) && user1.getFollowers().contains(user3));
	}

	/**
	 * @author Marc Tebo
	 * Test case for the getFollowing method
	 */
	@Test
	public void testGetFollowing() {
		user2.followUser(user1);
		user2.followUser(user3);
		
		assertTrue(user2.getFollowing().contains(user1)&& user2.getFollowing().contains(user3));
	}

	/**
	 * @author Marc Tebo
	 * Test case for the getLikedDocuments method
	 */
	@Test
	public void testGetLikedDocuments() {
		Document doc1 = new Document("TestDoc1","TEST",(Producer) user1);
		Document doc2 = new Document("TestDoc2", "TEST", (Producer) user1);
		user2.likeDocument(doc1);
		user2.likeDocument(doc2);
		
		assertTrue(user2.getLikedDocuments().contains(doc1) && user2.getLikedDocuments().contains(doc2));
	}

	/**
	 * @author Marc Tebo
	 * Test case for the equals method
	 */
	@Test
	public void testEqualsObject() {
		Producer testProd = new Producer("TestUser1","TEST");
		Consumer testCons = new Consumer("TestUser2","TEST");
		
		assertTrue(user1.equals(testProd) && user2.equals(testCons));
	}

	/**
	 * @author Marc Tebo
	 * Test case for the toString method
	 */
	@Test
	public void testToString() {
		//Producer toString
		assertEquals(user1.toString(),"User Type: Producer (Payoff: " + ((Producer) user1).calculatePayoff()
						+ ")\n"+ "User ID: " + user1.getUserID() + "\nUser Name: " + user1.getUserName() + 
						"\nTaste: " + user1.getTaste() + "\nFollowers: " + 	user1.getFollowers().size()
						+ "\nFollowing: " + user1.getFollowing().size() + 	"\nNumber of Documents Liked: "
						+ user1.getLikedDocuments().size() +  "\nDocuments Produced: " + 
						((Producer) user1).getDocumentsProduced().size() + "\n");
		
		//Consumer toString
		assertEquals(user2.toString(), "User Type: Consumer\n" + "User ID: " + user2.getUserID() + "\nUser Name: " + user2.getUserName() + 
				"\nTaste: " + user2.getTaste() + "\nFollowers: " + 	user2.getFollowers().size()
				+ "\nFollowing: " + user2.getFollowing().size() + 	"\nNumber of Documents Liked: "
				+ user2.getLikedDocuments().size() + "\n");
	}
}
