package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.RouteSensorInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLRouteSensorInjectMatch extends TypeQLMatch implements RouteSensorInjectMatch {
	public TypeQLRouteSensorInjectMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getRoute() {
		return match.get(QueryConstants.VAR_ROUTE);
	}

	@Override
	public Object getSensor() {
		return match.get(QueryConstants.VAR_SENSOR);
	}
}
