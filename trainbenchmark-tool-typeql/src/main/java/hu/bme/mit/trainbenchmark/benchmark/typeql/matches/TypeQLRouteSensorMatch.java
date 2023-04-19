package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.RouteSensorMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLRouteSensorMatch extends TypeQLMatch implements RouteSensorMatch {
	public TypeQLRouteSensorMatch(Map<String, Object> match) {
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

	@Override
	public Object getSwP() {
		return match.get(QueryConstants.VAR_SWP);
	}

	@Override
	public Object getSw() {
		return match.get(QueryConstants.VAR_SW);
	}
}
