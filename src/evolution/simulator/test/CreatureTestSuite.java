package evolution.simulator.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	TestCreatureSetters.class,
	TestCreatureGetters.class,
	TestMutations.class,
	TestCreatureStrings.class
})

public class CreatureTestSuite {

}
