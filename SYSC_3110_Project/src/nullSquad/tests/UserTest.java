/**
 * Title: SYSC 3110 Project Created By: Marc teBoekhorst Student Number:
 * 100925246 Team: nullSquad
 */
package nullSquad.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nullSquad.filesharingsystem.FileSharingSystem;
import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.Consumer;
import nullSquad.filesharingsystem.users.Producer;
import nullSquad.filesharingsystem.users.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest
{
	public Producer user1;
	public User user2;
	public User user3;
	FileSharingSystem network;
	List<String> tags;

	@Before
	public void setUp() throws Exception
	{
		user1 = new Producer("TestUser1", "TEST");
		user2 = new Consumer("TestUser2", "TEST");
		user3 = new Consumer("TestUser3", "TEST");

		network = new FileSharingSystem(null);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	/**
	 * @author Marc Tebo Test function for the User constructor
	 */
	@Test
	public void testUser()
	{
		User testUser1 = new Producer("TestUser1", "TEST");
		User testUser2 = new Consumer("TestUser2", "TEST");

		assertTrue(user1.equals(testUser1) && user2.equals(testUser2));

	}

	/**
	 * @author Marc Tebo Test function for the registerUser method
	 */
	@Test
	public void testRegisterUser()
	{
		user1.registerUser(network);
		user2.registerUser(network);

		assertTrue(network.getUsers().contains(user1) && network.getUsers().contains(user2));
	}

	/**
	 * @author Marc Tebo Test function for the followUser method
	 */
	@Test
	public void testFollowUser()
	{
		user2.followUser(user1);

		assertTrue(user1.getFollowers().contains(user2));
	}

	/**
	 * @author Marc Tebo Test function for the unfollowUser method
	 */
	@Test
	public void testUnfollowUser()
	{
		user2.followUser(user1);
		assertTrue(user1.getFollowers().contains(user2));

		user2.unfollowUser(user1);
		assertTrue(!user1.getFollowers().contains(user2));
	}

	/**
	 * @author Marc Tebo Test function for the getUserID method
	 */
	@Test
	public void testGetUserID()
	{
		user1.registerUser(network);
		user2.registerUser(network);
		user3.registerUser(network);

		assertEquals(user1.getUserID(), 1);
		assertEquals(user2.getUserID(), 2);
		assertEquals(user3.getUserID(), 3);
	}

	/**
	 * @author Marc Tebo Test case for the getUserName method
	 */
	@Test
	public void testGetUserName()
	{
		assertEquals(user1.getUserName(), "TestUser1");
	}

	/**
	 * @author Marc Tebo Test case for the getTaste method
	 */
	@Test
	public void testGetTaste()
	{
		assertEquals(user1.getTaste(), "TEST");
	}

	/**
	 * @author Marc Tebo Test case for the getFollowers method
	 */
	@Test
	public void testGetFollowers()
	{
		user2.followUser(user1);
		user3.followUser(user1);

		assertTrue(user1.getFollowers().contains(user2) && user1.getFollowers().contains(user3));
	}

	/**
	 * @author Marc Tebo Test case for the getFollowing method
	 */
	@Test
	public void testGetFollowing()
	{
		user2.followUser(user1);
		user2.followUser(user3);

		assertTrue(user2.getFollowing().contains(user1) && user2.getFollowing().contains(user3));
	}

	/**
	 * @author Marc Tebo Test case for the getLikedDocuments method
	 */
	@Test
	public void testGetLikedDocuments()
	{
		Document doc1 = new Document("TestDoc1", "TEST", (Producer) user1);
		Document doc2 = new Document("TestDoc2", "TEST", (Producer) user1);
		user2.likeDocument(doc1);
		user2.likeDocument(doc2);

		assertTrue(user2.getLikedDocuments().contains(doc1) && user2.getLikedDocuments().contains(doc2));
	}

	/**
	 * @author Marc Tebo Test case for the equals method
	 */
	@Test
	public void testEqualsObject()
	{
		Producer testProd = new Producer("TestUser1", "TEST");
		Consumer testCons = new Consumer("TestUser2", "TEST");

		assertTrue(user1.equals(testProd) && user2.equals(testCons));
	}

	@Test
	public void testRankDocuments()
	{
		List<Document> documents = new ArrayList<>();

		documents.add(new Document("Doc1", "Test", user1));
		documents.add(new Document("Doc2", "Test", user1));
		documents.add(new Document("Doc3", "Test", user1));

		List<Document> rankedResults = user1.rankDocuments(documents);

		// Make sure that user.rankDocuments() return the same results as
		// user.getSearchStrategyEnum().getStrategy().rankDocuments()
		assertEquals(rankedResults, user1.getSearchStrategyEnum().getStrategy().rankDocuments(documents, user1));

		// Ensure they return the same size as the original document list
		assertEquals(rankedResults.size(), documents.size());

	}

	/**
	 * @author Marc Tebo Test case for the toString method
	 */
	@Test
	public void testToString()
	{
		// Producer toString
		assertEquals(user1.toString(), "User Type: Producer (Payoff: " + ((Producer) user1).calculatePayoff() + ")\n" + "User ID: " + user1.getUserID() + "\nUser Name: " + user1.getUserName() + "\nTaste: " + user1.getTaste() + "\nFollowers: " + user1.getFollowers().size() + "\nFollowing: " + user1.getFollowing().size() + "\nNumber of Documents Liked: " + user1.getLikedDocuments().size() + "\nDocuments Produced: " + ((Producer) user1).getDocumentsProduced().size() + "\n");

		// Consumer toString
		assertEquals(user2.toString(), "User Type: Consumer\n" + "User ID: " + user2.getUserID() + "\nUser Name: " + user2.getUserName() + "\nTaste: " + user2.getTaste() + "\nFollowers: " + user2.getFollowers().size() + "\nFollowing: " + user2.getFollowing().size() + "\nNumber of Documents Liked: " + user2.getLikedDocuments().size() + "\n");
	}

	/**
	 * @author Marc Tebo Test case for the likeDocument method
	 */
	@Test
	public void testLikeDocument()
	{
		Document testDoc = new Document("TestDocument", "TEST", (Producer) user1);
		user2.likeDocument(testDoc);

		assertTrue(testDoc.getUserLikes().contains(user2) && user2.getLikedDocuments().contains(testDoc));
	}

	/**
	 * @author Marc Tebo Test case for the unlikeDocument method
	 */
	@Test
	public void testUnlikeDocument()
	{
		Document testDoc = new Document("TestDocument", "TEST", (Producer) user1);
		user2.likeDocument(testDoc);
		assertTrue(testDoc.getUserLikes().contains(user2) && user2.getLikedDocuments().contains(testDoc));

		user2.unlikeDocument(testDoc);
		assertTrue(!(testDoc.getUserLikes().contains(user2) || user2.getLikedDocuments().contains(testDoc)));
	}
}
