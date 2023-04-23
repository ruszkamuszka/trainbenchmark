package hu.bme.mit.trainbenchmark.generator.typeql.config;

import com.google.common.base.Preconditions;
import hu.bme.mit.trainbenchmark.generator.config.GeneratorConfigBuilder;
import hu.bme.mit.trainbenchmark.typeql.schema.TypeQLSchema;

public class TypeQLGeneratorConfigBuilder extends GeneratorConfigBuilder<TypeQLGeneratorConfig, TypeQLGeneratorConfigBuilder> {
	@Override
	public TypeQLGeneratorConfig createConfig() {
		checkNotNulls();
		return new TypeQLGeneratorConfig(configBase);
	}
}
