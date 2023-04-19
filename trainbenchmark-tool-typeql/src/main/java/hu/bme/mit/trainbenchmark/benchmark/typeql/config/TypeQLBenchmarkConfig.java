package hu.bme.mit.trainbenchmark.benchmark.typeql.config;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBase;

public class TypeQLBenchmarkConfig extends BenchmarkConfig {

	protected TypeQLBenchmarkConfig(final BenchmarkConfigBase configBase) {
		super(configBase);
	}

	@Override
	public String getToolName() {
		return "TypeQL";
	}

	@Override
	public String getProjectName() {
		return "typeql";
	}
}
