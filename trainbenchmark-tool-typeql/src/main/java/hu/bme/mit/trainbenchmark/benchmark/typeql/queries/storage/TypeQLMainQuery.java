package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.queries.TypeQLQuery;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

public abstract class TypeQLMainQuery<TQLMatch extends TypeQLMatch> extends TypeQLQuery<TQLMatch> {

	public TypeQLMainQuery(final RailwayQuery query, final TypeQLDriver driver) {
		super(query, driver);
	}

}
