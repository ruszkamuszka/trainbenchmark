package hu.bme.mit.trainbenchmark.benchmark.typeql.config;

import com.google.common.base.Preconditions;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBuilder;

public class TypeQLBenchmarkConfigBuilder extends BenchmarkConfigBuilder<TypeQLBenchmarkConfig, TypeQLBenchmarkConfigBuilder> {
	@Override
	public TypeQLBenchmarkConfig createConfig() {
		checkNotNulls();
		return new TypeQLBenchmarkConfig(configBase);
	}

	@Override
	public void checkNotNulls() {
		super.checkNotNulls();
	}
}
