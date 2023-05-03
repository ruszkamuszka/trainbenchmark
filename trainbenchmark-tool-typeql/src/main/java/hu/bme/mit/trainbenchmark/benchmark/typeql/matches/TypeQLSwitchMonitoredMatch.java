package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.RouteSensorMatch;
import hu.bme.mit.trainbenchmark.benchmark.matches.SwitchMonitoredMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLSwitchMonitoredMatch extends TypeQLMatch implements SwitchMonitoredMatch {
	public TypeQLSwitchMonitoredMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSw() {
		return match.get(QueryConstants.VAR_SW);
	}
}
