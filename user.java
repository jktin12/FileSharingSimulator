
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
		protected List<document> top5;
		
		public user(String taste){
			this.taste = taste;
			followedBy = new ArrayList<user>();
			following = new ArrayList<user>();
			likedDocs = new ArrayList<document>();
		}

		//Simple implementation of search using only the
		//popularity of a document
		public void search(simulation s) {
			//Get the first document in the list
			List<document> temp = new ArrayList<document>();
			temp = s.getAllDocuments();
			document currentBest;
			
			while (top5.size()<5) {	
				currentBest = temp.get(0);
				temp.remove(0);
				for (document d:temp) {
					if (currentBest.calculatePop() < d.calculatePop()) {
						currentBest = d;
					}
				}
				//Add the highest popularity document to the top5 list
				top5.add(currentBest);
				temp.remove(currentBest);
			}
		}

		//Calculates the performance for the search
		public int payoff() {
			int a=0;
			for (document d:top5){
				if ((d.getTag()==this.taste) && !(likedDocs.contains(d)))
					a=a+1;
			}
			return a;
		}
		
		public void followOrLike(){
		
			//Iterates through each document in the top 5
			for (document d:top5){
				//If the user has the same taste as the document
				//and is not currently liking it
				//add the document to the users liked list
				if ((d.getTag()==this.taste) && !(likedDocs.contains(d)))
					this.addLikedDoc(d);
				
				//Follow the users that like the document
				//if the user is not currently following them
				for (user u:d.getUserLiked()){
					if (!(following.contains(u)))
						following.add(u);
				}
			}
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
		
		public int numberOfFollowers(){
			return followedBy.size();
		}

	}

