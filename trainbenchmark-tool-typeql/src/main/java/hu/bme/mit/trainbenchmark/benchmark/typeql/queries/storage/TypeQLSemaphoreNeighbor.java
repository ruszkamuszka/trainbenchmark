package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorMatch;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;

public class TypeQLSemaphoreNeighbor extends TypeQLMainQuery<TypeQLRouteSensorMatch>{
	public TypeQLSemaphoreNeighbor(final TypeQLDriver driver) {
		super(RailwayQuery.SEMAPHORENEIGHBOR, driver);
	}

	@Override
	public Collection<TypeQLRouteSensorMatch> evaluate() throws Exception {
		return null;
	}
}
