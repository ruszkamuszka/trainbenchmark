package hu.bme.mit.trainbenchmark.generator.typeql;

import hu.bme.mit.trainbenchmark.generator.ModelGenerator;
import hu.bme.mit.trainbenchmark.generator.ScalableGeneratorFactory;
import hu.bme.mit.trainbenchmark.generator.config.GeneratorConfig;
import hu.bme.mit.trainbenchmark.generator.typeql.config.TypeQLGeneratorConfig;
import hu.bme.mit.trainbenchmark.typeql.process.TypeQLProcess;

import java.io.FileNotFoundException;

public class TypeQLGeneratorMain {
	public static void main(String[] args) throws FileNotFoundException {
		final TypeQLProcess typeQLProcess = new TypeQLProcess();
		typeQLProcess.setupDB();
		final TypeQLGeneratorConfig gc = GeneratorConfig.fromFile(args[0], TypeQLGeneratorConfig.class);
		final TypeQLSerializer typeQLSerializer = new TypeQLSerializer(gc);
		final ModelGenerator generator = ScalableGeneratorFactory.createGenerator(typeQLSerializer, gc);
		try {
			generator.generateModel();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
