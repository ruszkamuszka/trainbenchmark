package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.SwitchSetMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLSwitchSetMatch extends TypeQLMatch implements SwitchSetMatch {
	public TypeQLSwitchSetMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSemaphore() {
		return match.get(QueryConstants.VAR_SEMAPHORE);
	}

	@Override
	public Object getRoute() {
		return match.get(QueryConstants.VAR_ROUTE);
	}

	@Override
	public Object getSwP() {
		return match.get(QueryConstants.VAR_SWP);
	}

	@Override
	public Object getSw() {
		return match.get(QueryConstants.VAR_SW);
	}

	public Object getPos(){ return match.get(QueryConstants.VAR_POSITION);}
}
