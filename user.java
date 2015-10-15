
import java.util.*;

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

		public void search() {
			
			
		}

		public int payoff() {
			return 0;
		}
		
		public String getTaste() {
			return taste;
		}
		
		public int numberOfFollows(){
			return followedBy.size();
		}

	}
