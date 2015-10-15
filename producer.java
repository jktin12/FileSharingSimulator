/*
 * Subclass of user that creates and likes its own document
 */

public class producer extends user {
	
	private document myDoc;

	public producer(String taste) {
		super(taste);
		myDoc = new document(taste);
		myDoc.addLike(this);
	}
	
	public document getDoc(){
		return myDoc;
	}
	
}
