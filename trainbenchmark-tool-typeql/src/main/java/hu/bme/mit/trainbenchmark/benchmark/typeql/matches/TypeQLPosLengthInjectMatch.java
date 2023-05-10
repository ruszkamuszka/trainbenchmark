package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.PosLengthInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLPosLengthInjectMatch extends TypeQLMatch implements PosLengthInjectMatch {
	public TypeQLPosLengthInjectMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSegment() {
		return match.get(QueryConstants.VAR_SEGMENT);
	}
}
