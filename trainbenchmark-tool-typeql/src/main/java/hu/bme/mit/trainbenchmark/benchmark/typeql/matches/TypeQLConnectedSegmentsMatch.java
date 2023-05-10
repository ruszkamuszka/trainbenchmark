package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.ConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLConnectedSegmentsMatch extends TypeQLMatch implements ConnectedSegmentsMatch {
	public TypeQLConnectedSegmentsMatch(Map<String, Object> match) {
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
	public Object getSegment2() {
		return match.get(QueryConstants.VAR_SEGMENT2);
	}

	@Override
	public Object getSegment3() {
		return match.get(QueryConstants.VAR_SEGMENT3);
	}

	@Override
	public Object getSegment4() {
		return match.get(QueryConstants.VAR_SEGMENT4);
	}

	@Override
	public Object getSegment5() {
		return match.get(QueryConstants.VAR_SEGMENT5);
	}

	@Override
	public Object getSegment6() {
		return match.get(QueryConstants.VAR_SEGMENT6);
	}
}
