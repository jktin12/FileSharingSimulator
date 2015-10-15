import java.util.*;

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
	
	public void addLike(user u){
		userLiked.add(u);
	}
	
}
