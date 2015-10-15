
import java.util.*;

/* Base class user
 * implements methods and variables that can be found in
 * both consumer and producer
 */

public class user {
	
		protected String taste;
		protected List<document> likedDocs;
		protected List<user> followedBy;
		protected List<user> following;
		
		public user(String taste){
			this.taste = taste;
			followedBy = new ArrayList<user>();
			following = new ArrayList<user>();
			likedDocs = new ArrayList<document>();
		}

		public void search(simulation s) {
			
			
		}

		
		public int payoff() {
			return 0;
		}
		
		//Adds a follower
		public void addFollower(user u){
			followedBy.add(u);
		}
		
		//Adds a document to likedDocs
		public void addLikedDoc(document doc){
			likedDocs.add(doc);
		}
		
		//Follows another user
		public void follow(user u){
			following.add(u);
		}
		
		public String getTaste() {
			return taste;
		}
		
		public int numberOfFollows(){
			return followedBy.size();
		}

	}

