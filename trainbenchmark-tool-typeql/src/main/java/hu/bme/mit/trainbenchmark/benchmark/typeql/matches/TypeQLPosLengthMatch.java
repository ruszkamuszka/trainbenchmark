package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.PosLengthMatch;
import hu.bme.mit.trainbenchmark.benchmark.matches.RouteSensorMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLPosLengthMatch extends TypeQLMatch implements PosLengthMatch {
	public TypeQLPosLengthMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSegment() {
		return match.get(QueryConstants.VAR_SEGMENT);
	}
}
