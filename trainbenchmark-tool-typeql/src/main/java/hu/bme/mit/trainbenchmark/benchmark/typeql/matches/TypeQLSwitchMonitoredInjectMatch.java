package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.SwitchMonitoredInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLSwitchMonitoredInjectMatch extends TypeQLMatch implements SwitchMonitoredInjectMatch {
	public TypeQLSwitchMonitoredInjectMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSw() {
		return match.get(QueryConstants.VAR_SW);
	}
}
