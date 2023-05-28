package hu.bme.mit.trainbenchmark.benchmark.typeql.operations;

import hu.bme.mit.trainbenchmark.benchmark.operations.ModelOperation;
import hu.bme.mit.trainbenchmark.benchmark.operations.ModelOperationFactory;
import hu.bme.mit.trainbenchmark.benchmark.operations.ModelQuery;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.*;
import hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage.*;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.inject.*;
import hu.bme.mit.trainbenchmark.constants.RailwayOperation;

public class TypeQLModelOperationFactory extends ModelOperationFactory<TypeQLMatch, TypeQLDriver> {
	@Override
	public ModelOperation<? extends TypeQLMatch, TypeQLDriver> createOperation(final RailwayOperation operationEnum, final String workspaceDir, final TypeQLDriver driver) throws Exception {
		switch (operationEnum) {
			case CONNECTEDSEGMENTS:
				final ModelQuery<TypeQLConnectedSegmentsMatch, TypeQLDriver> query1 = new TypeQLConnectedSegments(driver);
				return ModelOperation.of(query1);
			case POSLENGTH:
				final ModelQuery<TypeQLPosLengthMatch, TypeQLDriver> query2 = new TypeQLPosLength(driver);
				return ModelOperation.of(query2);
			case ROUTESENSOR:
				final ModelQuery<TypeQLRouteSensorMatch, TypeQLDriver> query3 = new TypeQLRouteSensor(driver);
				return ModelOperation.of(query3);
			case SEMAPHORENEIGHBOR:
				final ModelQuery<TypeQLSemaphoreNeighborMatch, TypeQLDriver> query4 = new TypeQLSemaphoreNeighbor(driver);
				return ModelOperation.of(query4);
			case SWITCHMONITORED:
				final ModelQuery<TypeQLSwitchMonitoredMatch, TypeQLDriver> query5 = new TypeQLSwitchMonitored(driver);
				return ModelOperation.of(query5);
			case SWITCHSET:
				final ModelQuery<TypeQLSwitchSetMatch, TypeQLDriver> query6 = new TypeQLSwitchSet(driver);
				return ModelOperation.of(query6);
			case CONNECTEDSEGMENTS_INJECT:
				final ModelQuery<TypeQLConnectedSegmentsInjectMatch, TypeQLDriver> query7 = new TypeQLConnectedSegmentsInject(driver);
				final TypeQLTransformation<TypeQLConnectedSegmentsInjectMatch, TypeQLDriver> transformation7 = new TypeQLTransformationInjectConnectedSegments<>(driver);
				return ModelOperation.of(query7, transformation7);
			case POSLENGTH_INJECT:
				final ModelQuery<TypeQLPosLengthInjectMatch, TypeQLDriver> query8 = new TypeQLPosLengthInject(driver);
				final TypeQLTransformation<TypeQLPosLengthInjectMatch, TypeQLDriver> transformation8 = new TypeQLTransformationInjectPosLength<>(driver);
				return ModelOperation.of(query8, transformation8);
			case ROUTESENSOR_INJECT:
				final ModelQuery<TypeQLRouteSensorInjectMatch, TypeQLDriver> query9 = new TypeQLRouteSensorInject(driver);
				final TypeQLTransformation<TypeQLRouteSensorInjectMatch, TypeQLDriver> transformation9 = new TypeQLTransformationInjectRouteSensor<>(driver);
				return ModelOperation.of(query9, transformation9);
			case SEMAPHORENEIGHBOR_INJECT:
				final ModelQuery<TypeQLSemaphoreNeighborInjectMatch, TypeQLDriver> query10 = new TypeQLSemaphoreNeighborInject(driver);
				return ModelOperation.of(query10);
			case SWITCHMONITORED_INJECT:
				final ModelQuery<TypeQLSwitchMonitoredInjectMatch, TypeQLDriver> query11 = new TypeQLSwitchMonitoredInject(driver);
				final TypeQLTransformation<TypeQLSwitchMonitoredInjectMatch, TypeQLDriver> transformation11 = new TypeQLTransformationInjectSwitchMonitored<>(driver);
				return ModelOperation.of(query11, transformation11);
			case SWITCHSET_INJECT:
				final ModelQuery<TypeQLSwitchSetInjectMatch, TypeQLDriver> query12 = new TypeQLSwitchSetInject(driver);
				final TypeQLTransformation<TypeQLSwitchSetInjectMatch, TypeQLDriver> transformation12 = new TypeQLTransformationInjectSwitchSet<>(driver);
				return ModelOperation.of(query12, transformation12);
//			case CONNECTEDSEGMENTS_REPAIR:
//				break;
//			case POSLENGTH_REPAIR:
//				break;
//			case ROUTESENSOR_REPAIR:
//				break;
//			case SEMAPHORENEIGHBOR_REPAIR:
//				break;
//			case SWITCHMONITORED_REPAIR:
//				break;
//			case SWITCHSET_REPAIR:
//				break;
		}
		throw new UnsupportedOperationException("Operation " + operationEnum + " not supported.");
	}
}
