package hu.bme.mit.trainbenchmark.typeql;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBase;
import hu.bme.mit.trainbenchmark.typeql.config.TypeQLBenchmarkConfig;
import hu.bme.mit.trainbenchmark.typeql.config.TypeQLEngine;

public class TypeQLBenchmarkMain {
	public static void main(final String[] args) throws Exception {
		final TypeQLBenchmarkConfig bc = BenchmarkConfig.fromFile(args[0], TypeQLBenchmarkConfig.class);
		//BenchmarkConfigBase configBase = new BenchmarkConfigBase();
		//final TypeQLBenchmarkConfig bc = new TypeQLBenchmarkConfig(configBase, );
		final TypeQLBenchmarkScenario scenario = new TypeQLBenchmarkScenario(bc);
		scenario.performBenchmark();
	}
}
