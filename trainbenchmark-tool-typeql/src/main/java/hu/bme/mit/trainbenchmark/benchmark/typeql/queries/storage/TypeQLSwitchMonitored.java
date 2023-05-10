package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchMonitoredMatch;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;

public class TypeQLSwitchMonitored extends TypeQLMainQuery<TypeQLSwitchMonitoredMatch>{
	public TypeQLSwitchMonitored(final TypeQLDriver driver) {
		super(RailwayQuery.SWITCHMONITORED, driver);
	}

	@Override
	public Collection<TypeQLSwitchMonitoredMatch> evaluate() throws Exception {
		return null;
	}
}
