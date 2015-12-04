package nullSquad.strategies.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nullSquad.filesharingsystem.document.Document;
import nullSquad.filesharingsystem.users.User;

/**
 * User Distance Ranking Strategy
 * 
 * @author Justin Krol
 */
public class UserDistanceRankingStrategy implements DocumentRankingStrategy
{

	/* Serializable ID */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private final int MAX_DEPTH_TO_SEARCH = 4;

	@Override
	public List<Document> rankDocuments(List<Document> allDocuments, User user)
	{
		// Set global User variable to the User passed in.
		this.user = user;

		// Copy the documents over to the new array to be ranked
		List<Document> rankedDocuments = new ArrayList<>();
		for (Document d : allDocuments)
		{
			rankedDocuments.add(d);
		}

		// Sort the top documents using the selected strategy as a comparator
		Collections.sort(rankedDocuments, this);

		// Since Collections.sort() sorts from worst -> greatest, we need to
		// reverse the list
		Collections.reverse(rankedDocuments);

		// Return the ranked documents
		return rankedDocuments;
	}

	public int compare(Document doc1, Document doc2)
	{
		// If both are null, they are ranked the same
		if (doc1 == null && doc2 == null)
			return 0;

		// If doc1 is null and doc2 isnt, doc2 is ranked higher
		if (doc1 == null && doc2 != null)
			return -1;

		// If doc1 isnt null and doc2 is, doc1 is ranked higher
		if (doc1 != null && doc2 == null)
			return 1;

		// Compare documents in terms of immediacy of friends
		// Need to call getFriendDistance() on both documents.
		// If using the recursive solution, use arguments:
		// user
		// document
		// depth (should be 1)

		int doc1_val = MAX_DEPTH_TO_SEARCH, doc2_val = MAX_DEPTH_TO_SEARCH;

		doc1_val = getFriendDistance(user, doc1, 1);
		doc2_val = getFriendDistance(user, doc2, 1);

		if (doc1_val < doc2_val)
		{
			return 1;
		}
		if (doc1_val > doc2_val)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * Recursive solution Checks up to the MAX_DEPTH_TO_SEARCH pre: given user
	 * and document, as well as current ranking (this is important as the
	 * recursions gets deeper so that it knows when it should stop looking)
	 * post: returns the number of layers of friends before one is found that
	 * likes the document If none is found, then the final return value will be
	 * MAX_DEPTH_TO_SEARCH + 1.
	 */
	private int getFriendDistance(User user, Document doc, int depth)
	{
		int minDepth = MAX_DEPTH_TO_SEARCH; // i.e. starts off greater than all
											// other possible depths
		int tempDepth = 0; // reset with every recursive call at a specific
							// depth

		// Base Case: One of this user's friends likes the document
		if (docLikedByFriend(user, doc))
		{
			return 1; // return 1 to represent a distance of one friend
		}

		// If none of this user's friends like the document.
		else
		{
			if (depth < MAX_DEPTH_TO_SEARCH)
			{ // if we are at less than the maximum allowable depth
				for (User u : getFriendsList(user))
				{ // for every user in this user's friend list
					tempDepth = getFriendDistance(u, doc, depth + 1); // get the
																		// distance
																		// to a
																		// liker
																		// of
																		// the
																		// document
																		// pass
																		// depth+1
																		// to
																		// indicate
																		// another
																		// layer
																		// of
																		// depth,
																		// so
																		// that
																		// the
																		// recursion
																		// does
																		// not
																		// continue
																		// forever.
					if (tempDepth < minDepth)
					{ // if the depth for this user is less than the previous
						// minimum,
						minDepth = tempDepth; // set it as the new minimum.
					}
				}
				return minDepth + 1; // return the minimum depth obtained
										// +1 to factor in the current level of
										// depth.
			}
			else
			{
				// if the depth is at the max searchable, add 1 for this depth
				// and one for the
				// next depth which will not be searched.
				// Final return value should end up being MAX_DEPTH_TO_SEARCH +
				// 1

				return 2;
			}
		}
	}

	/**
	 * Merges Followers/Following to form friends list
	 * 
	 * @param user The User's List of Friends to get
	 * @return The User's List of Friends
	 * @author MVezina
	 */
	private List<User> getFriendsList(User user)
	{
		// Merge followers and following to form friends list
		List<User> friends = new ArrayList<>(user.getFollowers());
		friends.addAll(user.getFollowing());

		return friends;
	}

	private boolean docLikedByFriend(User userTemp, Document doc)
	{
		for (User u : getFriendsList(userTemp))
		{
			if (u.getLikedDocuments().contains(doc))
			{
				return true;
			}
		}

		return false;
	}

}
