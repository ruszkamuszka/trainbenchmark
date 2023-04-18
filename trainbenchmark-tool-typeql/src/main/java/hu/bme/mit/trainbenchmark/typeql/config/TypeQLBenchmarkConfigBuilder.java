package hu.bme.mit.trainbenchmark.typeql.config;

import com.google.common.base.Preconditions;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBuilder;

public class TypeQLBenchmarkConfigBuilder extends BenchmarkConfigBuilder<TypeQLBenchmarkConfig, TypeQLBenchmarkConfigBuilder> {

	private TypeQLEngine engine;

	public TypeQLBenchmarkConfigBuilder setEngine(final TypeQLEngine engine) {
		this.engine = engine;
		return this;
	}

	@Override
	public TypeQLBenchmarkConfig createConfig() {
		checkNotNulls();
		return new TypeQLBenchmarkConfig(configBase, engine);
	}

	@Override
	public void checkNotNulls() {
		super.checkNotNulls();
		Preconditions.checkNotNull(engine);
	}
}
