package hu.bme.mit.trainbenchmark.benchmark.typeql;

import hu.bme.mit.trainbenchmark.benchmark.phases.BenchmarkScenario;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriverFactory;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.operations.TypeQLModelOperationFactory;
import hu.bme.mit.trainbenchmark.benchmark.typeql.comparators.TypeQLMatchComparator;
import hu.bme.mit.trainbenchmark.benchmark.typeql.config.TypeQLBenchmarkConfig;

public class TypeQLBenchmarkScenario
	extends BenchmarkScenario<TypeQLMatch, TypeQLDriver, TypeQLBenchmarkConfig> {

	public TypeQLBenchmarkScenario(final TypeQLBenchmarkConfig bc) throws Exception {
		super(new TypeQLDriverFactory(bc.getConfigBase().getModelDir()), new TypeQLModelOperationFactory(), new TypeQLMatchComparator(), bc);
	}
}
