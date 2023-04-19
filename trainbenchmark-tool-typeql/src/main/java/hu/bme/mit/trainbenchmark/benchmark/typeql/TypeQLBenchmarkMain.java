package hu.bme.mit.trainbenchmark.benchmark.typeql;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.typeql.config.TypeQLBenchmarkConfig;

public class TypeQLBenchmarkMain {
	public static void main(final String[] args) throws Exception {
		final TypeQLBenchmarkConfig bc = BenchmarkConfig.fromFile(args[0], TypeQLBenchmarkConfig.class);
		final TypeQLBenchmarkScenario scenario = new TypeQLBenchmarkScenario(bc);
		scenario.performBenchmark();
	}
}
