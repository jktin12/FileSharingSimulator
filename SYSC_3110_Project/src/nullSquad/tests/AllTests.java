package nullSquad.tests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({DocumentTest.class, ProducerTest.class, UserTest.class, FileSharingSystemTest.class, DocumentPopularityStrategyTest.class, UserPopularityRankingStrategyTest.class, DocumentPopularityStrategyTest.class, UserDistanceRankingStrategyTest.class, FollowSimilarityStrategyTest.class})
public class AllTests {
}
