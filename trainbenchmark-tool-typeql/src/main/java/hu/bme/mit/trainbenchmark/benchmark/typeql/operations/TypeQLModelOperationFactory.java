package hu.bme.mit.trainbenchmark.benchmark.typeql.operations;

import hu.bme.mit.trainbenchmark.benchmark.operations.ModelOperation;
import hu.bme.mit.trainbenchmark.benchmark.operations.ModelOperationFactory;
import hu.bme.mit.trainbenchmark.benchmark.operations.ModelQuery;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLPosLengthMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage.TypeQLConnectedSegments;
import hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage.TypeQLPosLength;
import hu.bme.mit.trainbenchmark.constants.RailwayOperation;
import hu.bme.mit.trainbenchmark.benchmark.typeql.config.TypeQLEngine;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage.TypeQLRouteSensor;

public class TypeQLModelOperationFactory extends ModelOperationFactory<TypeQLMatch, TypeQLDriver> {
	@Override
	public ModelOperation<? extends TypeQLMatch, TypeQLDriver> createOperation(final RailwayOperation operationEnum, final String workspaceDir, final TypeQLDriver driver) throws Exception {
		switch (operationEnum) {
//			case ACTIVEROUTE:
//				break;
			case CONNECTEDSEGMENTS:
				final ModelQuery<TypeQLConnectedSegmentsMatch, TypeQLDriver> query1 = new TypeQLConnectedSegments(driver);
				final ModelOperation<TypeQLConnectedSegmentsMatch, TypeQLDriver> operation1 = ModelOperation.of(query1);
				return operation1;
			case POSLENGTH:
//				final ModelQuery<TypeQLPosLengthMatch, TypeQLDriver> query2 = new TypeQLPosLength(driver);
//				final ModelOperation<TypeQLPosLengthMatch, TypeQLDriver> operation2 = ModelOperation.of(query2);
//				return operation2;
//			case ROUTELENGTH:
//				break;
//			case ROUTEREACHABILITY:
//				break;
			case ROUTESENSOR:
				final ModelQuery<TypeQLRouteSensorMatch, TypeQLDriver> query = new TypeQLRouteSensor(driver);
				final ModelOperation<TypeQLRouteSensorMatch, TypeQLDriver> operation = ModelOperation.of(query);
				return operation;
			case SEMAPHORENEIGHBOR:
				break;
			case SWITCHMONITORED:
				break;
			case SWITCHSET:
				break;
//			case CONNECTEDSEGMENTS_INJECT:
//				break;
//			case POSLENGTH_INJECT:
//				break;
//			case ROUTESENSOR_INJECT:
//				break;
//			case SEMAPHORENEIGHBOR_INJECT:
//				break;
//			case SWITCHMONITORED_INJECT:
//				break;
//			case SWITCHSET_INJECT:
//				break;
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
