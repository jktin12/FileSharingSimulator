package nullSquad.tests;

import nullSquad.document.*;
import nullSquad.network.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DocumentTest.class, ProducerTest.class, UserTest.class, FileSharingSystem.class})
public class AllTests {
}
