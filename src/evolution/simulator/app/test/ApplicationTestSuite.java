package evolution.simulator.app.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import evolution.simulator.creature.test.CreatureTestSuite;
import evolution.simulator.evolution.simulator.test.EvolutionSimulatorTestSuite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	CreatureTestSuite.class,
	EvolutionSimulatorTestSuite.class
})

public class ApplicationTestSuite {

}
