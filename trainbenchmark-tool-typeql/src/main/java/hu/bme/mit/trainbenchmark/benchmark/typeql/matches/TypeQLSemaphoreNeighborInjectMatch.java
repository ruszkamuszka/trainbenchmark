package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.SemaphoreNeighborInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLSemaphoreNeighborInjectMatch extends TypeQLMatch implements SemaphoreNeighborInjectMatch {
	public TypeQLSemaphoreNeighborInjectMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getRoute() {
		return match.get(QueryConstants.VAR_ROUTE);
	}

	@Override
	public Object getSemaphore() {
		return match.get(QueryConstants.VAR_SEMAPHORE);
	}
}
