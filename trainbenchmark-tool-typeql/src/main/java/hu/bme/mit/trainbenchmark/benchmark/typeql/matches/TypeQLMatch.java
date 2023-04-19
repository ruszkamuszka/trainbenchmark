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
			case ACTIVEROUTE:
				break;
			case CONNECTEDSEGMENTS:
				break;
			case POSLENGTH:
				break;
			case ROUTEREACHABILITY:
				break;
			case ROUTELENGTH:
				break;
			case ROUTESENSOR:
				return new TypeQLRouteSensorMatch(match);
			case SEMAPHORENEIGHBOR:
				break;
			case SWITCHMONITORED:
				break;
			case SWITCHSET:
				break;
			case CONNECTEDSEGMENTS_INJECT:
				break;
			case POSLENGTH_INJECT:
				break;
			case ROUTESENSOR_INJECT:
				break;
			case SEMAPHORENEIGHBOR_INJECT:
				break;
			case SWITCHMONITORED_INJECT:
				break;
			case SWITCHSET_INJECT:
				break;
			default:
				throw new UnsupportedOperationException("Query not supported: " + query);
		}
		return null;
	}
}
