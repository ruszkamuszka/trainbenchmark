package hu.bme.mit.trainbenchmark.generator.minimal;

import static hu.bme.mit.trainbenchmark.constants.ModelConstants.CONNECTSTO;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.DEFINED_BY;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.EXIT;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.ROUTE;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SEGMENT;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SEMAPHORE;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SENSOR;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.SENSOR_EDGE;

import java.io.FileNotFoundException;
import java.io.IOException;

import hu.bme.mit.trainbenchmark.generator.ModelSerializer;
import hu.bme.mit.trainbenchmark.generator.config.GeneratorConfig;

public class MinimalSemaphoreNeighborGenerator extends MinimalModelGenerator {

	public MinimalSemaphoreNeighborGenerator(final ModelSerializer serializer, final GeneratorConfig generatorConfig) {
		super(serializer, generatorConfig);
	}

	@Override
	protected void constructModel() throws FileNotFoundException, IOException {
		final Object semaphore = serializer.createVertex(SEMAPHORE);
		final Object route1 = serializer.createVertex(ROUTE);
		final Object route2 = serializer.createVertex(ROUTE);
		final Object sensor1 = serializer.createVertex(SENSOR);
		final Object sensor2 = serializer.createVertex(SENSOR);
		final Object te1 = serializer.createVertex(SEGMENT);
		final Object te2 = serializer.createVertex(SEGMENT);

		serializer.createEdge(EXIT, route1, semaphore);
		serializer.createEdge(DEFINED_BY, route1, sensor1);
		serializer.createEdge(DEFINED_BY, route2, sensor2);
		serializer.createEdge(SENSOR_EDGE, te1, sensor1);
		serializer.createEdge(SENSOR_EDGE, te2, sensor2);
		serializer.createEdge(CONNECTSTO, te1, te2);
	}

}
