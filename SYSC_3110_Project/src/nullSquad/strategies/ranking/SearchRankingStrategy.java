package nullSquad.strategies.ranking;

import nullSquad.filesharingsystem.document.*;
import java.util.*;

/**
 * Tag interface for Ranking strategies
 * ( Forces strategies to use Comparator<Document> )
 * @author MVezina
 */
public interface SearchRankingStrategy extends Comparator<Document>{}
