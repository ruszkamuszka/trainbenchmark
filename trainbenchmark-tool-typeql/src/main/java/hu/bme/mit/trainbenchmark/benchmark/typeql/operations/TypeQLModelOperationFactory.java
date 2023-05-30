package hu.bme.mit.trainbenchmark.benchmark.typeql.operations;

import hu.bme.mit.trainbenchmark.benchmark.operations.ModelOperation;
import hu.bme.mit.trainbenchmark.benchmark.operations.ModelOperationFactory;
import hu.bme.mit.trainbenchmark.benchmark.operations.ModelQuery;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.*;
import hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage.*;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.inject.*;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.repair.*;
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
				final TypeQLTransformation<TypeQLSemaphoreNeighborInjectMatch, TypeQLDriver> transformation10 = new TypeQLTransformationInjectSemaphoreNeighbor<>(driver);
				return ModelOperation.of(query10, transformation10);
			case SWITCHMONITORED_INJECT:
				final ModelQuery<TypeQLSwitchMonitoredInjectMatch, TypeQLDriver> query11 = new TypeQLSwitchMonitoredInject(driver);
				final TypeQLTransformation<TypeQLSwitchMonitoredInjectMatch, TypeQLDriver> transformation11 = new TypeQLTransformationInjectSwitchMonitored<>(driver);
				return ModelOperation.of(query11, transformation11);
			case SWITCHSET_INJECT:
				final ModelQuery<TypeQLSwitchSetInjectMatch, TypeQLDriver> query12 = new TypeQLSwitchSetInject(driver);
				final TypeQLTransformation<TypeQLSwitchSetInjectMatch, TypeQLDriver> transformation12 = new TypeQLTransformationInjectSwitchSet<>(driver);
				return ModelOperation.of(query12, transformation12);
			case CONNECTEDSEGMENTS_REPAIR:
				final ModelQuery<TypeQLConnectedSegmentsMatch, TypeQLDriver> query13 = new TypeQLConnectedSegments(driver);
				final TypeQLTransformation<TypeQLConnectedSegmentsMatch, TypeQLDriver> transformation13 = new TypeQLTransformationRepairConnectedSegments<>(driver);
				return ModelOperation.of(query13, transformation13);
			case POSLENGTH_REPAIR:
				final ModelQuery<TypeQLPosLengthMatch, TypeQLDriver> query14 = new TypeQLPosLength(driver);
				final TypeQLTransformation<TypeQLPosLengthMatch, TypeQLDriver> transformation14 = new TypeQLTransformationRepairPosLength<>(driver);
				return ModelOperation.of(query14, transformation14);
			case ROUTESENSOR_REPAIR:
				final ModelQuery<TypeQLRouteSensorMatch, TypeQLDriver> query15 = new TypeQLRouteSensor(driver);
				final TypeQLTransformation<TypeQLRouteSensorMatch, TypeQLDriver> transformation15 = new TypeQLTransformationRepairRouteSensor<>(driver);
				return ModelOperation.of(query15, transformation15);
			case SEMAPHORENEIGHBOR_REPAIR:
				final ModelQuery<TypeQLSemaphoreNeighborMatch, TypeQLDriver> query16 = new TypeQLSemaphoreNeighbor(driver);
				final TypeQLTransformation<TypeQLSemaphoreNeighborMatch, TypeQLDriver> transformation16 = new TypeQLTransformationRepairSemaphoreNeighbor<>(driver);
				return ModelOperation.of(query16, transformation16);
			case SWITCHMONITORED_REPAIR:
				final ModelQuery<TypeQLSwitchMonitoredMatch, TypeQLDriver> query17 = new TypeQLSwitchMonitored(driver);
				final TypeQLTransformation<TypeQLSwitchMonitoredMatch, TypeQLDriver> transformation17 = new TypeQLTransformationRepairSwitchMonitored<>(driver);
				return ModelOperation.of(query17, transformation17);
			case SWITCHSET_REPAIR:
				final ModelQuery<TypeQLSwitchSetMatch, TypeQLDriver> query18 = new TypeQLSwitchSet(driver);
				final TypeQLTransformation<TypeQLSwitchSetMatch, TypeQLDriver> transformation18 = new TypeQLTransformationRepairSwitchSet<>(driver);
				return ModelOperation.of(query18, transformation18);
		}
		throw new UnsupportedOperationException("Operation " + operationEnum + " not supported.");
	}
}
