package nullSquad.strategies.ranking;

import nullSquad.filesharingsystem.document.Document;

/**
 * Class to implement a search ranking strategy
 * @author MVezina
 *
 */
public class DocumentPopularityStrategy implements SearchRankingStrategy{

	@Override
	public int compare(Document doc1, Document doc2) {
		// Compare documents in terms of popularity (Likes)
		return doc1.getUserLikes().size() - doc2.getUserLikes().size();
	}

}

/*
 * TODO: Remove. Old Search algorithm
 * 
 * List<Document> topKDocuments = new ArrayList<Document>();
 * 
 * // If the number of documents is less than topK, just return all the
 * documents if (allDocuments.size() < topK) { return allDocuments; }
 * 
 * 
 * for (Document doc : allDocuments) { // If document isnt liked by the
 * searching user if (!doc.getUserLikes().contains(user)) { if
 * (doc.getTag().equals(searchTag)) { topKDocuments.add(doc); continue; }
 * 
 * // for (User u : user.getFollowing()) { if (doc.getUserLikes().contains(u)) {
 * topKDocuments.add(doc); break; } } } }
 * 
 * if (topKDocuments.size() < topK) { for (Document doc : allDocuments) { if
 * (!topKDocuments.contains(doc)) { if (topKDocuments.size() == topK) break;
 * 
 * topKDocuments.add(doc); } } }
 * 
 * else if (topKDocuments.size() > topK) { Collections.sort(topKDocuments);
 * Collections.reverse(topKDocuments); topKDocuments = topKDocuments.subList(0,
 * topK); }
 * 
 * 
 * // We want to update the payoff for all of the producers every time their
 * documents are returned for(Document d : topKDocuments) {
 * d.getProducer().calculatePayoff(); }
 * 
 * // After all documents have been checked, return the list of topK //
 * documents return topKDocuments;
 */
