package hu.bme.mit.trainbenchmark.typeql;

import hu.bme.mit.trainbenchmark.benchmark.phases.BenchmarkScenario;
import hu.bme.mit.trainbenchmark.typeql.comparators.TypeQLMatchComparator;
import hu.bme.mit.trainbenchmark.typeql.config.TypeQLBenchmarkConfig;
import hu.bme.mit.trainbenchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.typeql.driver.TypeQLDriverFactory;
import hu.bme.mit.trainbenchmark.typeql.matches.TypeQLMatch;
import hu.bme.mit.trainbenchmark.typeql.operations.TypeQLModelOperationFactory;

public class TypeQLBenchmarkScenario
	extends BenchmarkScenario<TypeQLMatch, TypeQLDriver, TypeQLBenchmarkConfig> {

	public TypeQLBenchmarkScenario(final TypeQLBenchmarkConfig bc) throws Exception {
		super(new TypeQLDriverFactory(bc.getConfigBase().getModelDir()), new TypeQLModelOperationFactory(bc.getEngine()), new TypeQLMatchComparator(), bc);
	}
}
