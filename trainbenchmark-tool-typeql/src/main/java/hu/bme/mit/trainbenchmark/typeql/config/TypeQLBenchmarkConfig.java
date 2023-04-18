package hu.bme.mit.trainbenchmark.typeql.config;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBase;

public class TypeQLBenchmarkConfig extends BenchmarkConfig {

	private final TypeQLEngine engine;

	protected TypeQLBenchmarkConfig(final BenchmarkConfigBase configBase, final TypeQLEngine engine) {
		super(configBase);
		this.engine = engine;
	}

	@Override
	public String getToolName() {
		return "TypeQL";
	}

	@Override
	public String getProjectName() {
		return "typeql";
	}

	public TypeQLEngine getEngine() {
		return engine;
	}
}
