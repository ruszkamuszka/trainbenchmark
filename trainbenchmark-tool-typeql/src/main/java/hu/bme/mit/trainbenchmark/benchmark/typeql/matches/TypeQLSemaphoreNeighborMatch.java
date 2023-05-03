package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.RouteSensorMatch;
import hu.bme.mit.trainbenchmark.benchmark.matches.SemaphoreNeighborMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLSemaphoreNeighborMatch extends TypeQLMatch implements SemaphoreNeighborMatch {
	public TypeQLSemaphoreNeighborMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSemaphore() {
		return match.get(QueryConstants.VAR_SEMAPHORE);
	}

	@Override
	public Object getRoute1() {
		return match.get(QueryConstants.VAR_ROUTE1);
	}

	@Override
	public Object getRoute2() {
		return match.get(QueryConstants.VAR_ROUTE2);
	}

	@Override
	public Object getSensor1() {
		return match.get(QueryConstants.VAR_SENSOR1);
	}

	@Override
	public Object getSensor2() {
		return match.get(QueryConstants.VAR_SENSOR2);
	}

	@Override
	public Object getTe1() {
		return match.get(QueryConstants.VAR_TE1);
	}

	@Override
	public Object getTe2() {
		return match.get(QueryConstants.VAR_TE2);
	}
}
