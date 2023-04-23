package hu.bme.mit.trainbenchmark.generator.typeql.config;

import hu.bme.mit.trainbenchmark.generator.config.GeneratorConfig;
import hu.bme.mit.trainbenchmark.generator.config.GeneratorConfigBase;
import hu.bme.mit.trainbenchmark.typeql.schema.TypeQLSchema;

public class TypeQLGeneratorConfig extends GeneratorConfig {
	public TypeQLGeneratorConfig(GeneratorConfigBase configBase) {
		super(configBase);
	}

	@Override
	public String getProjectName() {
		return "typeQL";
	}
}
