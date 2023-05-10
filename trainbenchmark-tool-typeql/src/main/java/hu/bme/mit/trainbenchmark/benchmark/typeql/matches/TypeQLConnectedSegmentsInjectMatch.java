package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.ConnectedSegmentsInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLConnectedSegmentsInjectMatch extends TypeQLMatch implements ConnectedSegmentsInjectMatch {
	public TypeQLConnectedSegmentsInjectMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSensor() {
		return match.get(QueryConstants.VAR_SENSOR);
	}

	@Override
	public Object getSegment1() {
		return match.get(QueryConstants.VAR_SEGMENT1);
	}

	@Override
	public Object getSegment3() {
		return match.get(QueryConstants.VAR_SEGMENT3);
	}
}
