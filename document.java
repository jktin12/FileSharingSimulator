import java.util.*;

/*
 * Holds all the variables that a document should
 * contain and keeps track of the users that liked
 * the document
 */

public class document {
	
	private String tag;
	private List<user> userLiked;
	private int popularity;
	
	public document(String tag) {
		this.tag = tag;
		userLiked = new ArrayList<user>();
		popularity = 0;
	}
	
	public String getTag(){
		return tag;
	}
	
	public int getLikes(){
		return userLiked.size();
	}
	
	//Each time the document is liked, the document stores
	//the user and the popularity is increased
	public void addLike(user u){
		userLiked.add(u);
		addPop(u.numberOfFollows()+1);
	}
	
	//Adds an amount to the popularity of the document
	public void addPop(int a){
		popularity=popularity+a;
	}
	
}
