package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.SwitchSetInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLSwitchSetInjectMatch extends TypeQLMatch implements SwitchSetInjectMatch {
	public TypeQLSwitchSetInjectMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSw() {
		return match.get(QueryConstants.VAR_SW);
	}
}
