package nullSquad.strategies.ranking;

import nullSquad.filesharingsystem.document.*;
import java.util.*;

public interface SearchRankingStrategy {
	public List<Document> rankDocuments(List<Document> allDocuments, int topK);
}
