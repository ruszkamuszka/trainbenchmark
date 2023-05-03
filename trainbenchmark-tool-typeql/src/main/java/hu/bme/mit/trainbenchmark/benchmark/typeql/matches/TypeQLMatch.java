package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.BaseMatch;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Arrays;
import java.util.Map;


public abstract class TypeQLMatch extends BaseMatch {
	protected final Map<String, Object> match;

	public TypeQLMatch(final Map<String, Object> match) {
		this.match = match;
	}

	@Override
	public String toString() {
		return "TypeQLMatch [match=" + Arrays.toString(toArray()) + "]";
	}
	public static TypeQLMatch createMatch(final RailwayQuery query, final Map<String, Object> match) throws Exception {
		switch (query) {
			case CONNECTEDSEGMENTS:
				return new TypeQLConnectedSegmentsMatch(match);
			case POSLENGTH:
				return new TypeQLPosLengthMatch(match);
			case ROUTESENSOR:
				return new TypeQLRouteSensorMatch(match);
			case SEMAPHORENEIGHBOR:
				return new TypeQLSemaphoreNeighborMatch(match);
			case SWITCHMONITORED:
				return new TypeQLSwitchMonitoredMatch(match);
			case SWITCHSET:
				return new TypeQLSwitchSetMatch(match);
			default:
				throw new UnsupportedOperationException("Query not supported: " + query);
		}
	}
}
