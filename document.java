import java.util.*;

/*
 * Holds all the variables that a document should
 * contain and keeps track of the users that liked
 * the document
 */

public class document {
	
	private String tag;
	private List<user> userLiked;
	
	public document(String tag) {
		this.tag = tag;
		userLiked = new ArrayList<user>();
	}
	
	public String getTag(){
		return tag;
	}
	
	public int getLikes(){
		return userLiked.size();
	}
	
	//Adds a user to the list of users who liked this document
	public void addLike(user u){
		userLiked.add(u);
	}
	
	//Calculates the popularity of the document
	public int calculatePop(int a){
		int popularity = userLiked.size();
		for (user u:userLiked){
			popularity = popularity + u.numberOfFollowers();
		}
		return popularity;
	}
	
}
