package hu.bme.mit.trainbenchmark.typeql.queries.storage;

import hu.bme.mit.trainbenchmark.constants.RailwayQuery;
import hu.bme.mit.trainbenchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.typeql.matches.TypeQLMatch;
import hu.bme.mit.trainbenchmark.typeql.queries.TypeQLQuery;

public abstract class TypeQLMainQuery<TQLMatch extends TypeQLMatch> extends TypeQLQuery<TQLMatch> {

	public TypeQLMainQuery(final RailwayQuery query, final TypeQLDriver driver) {
		super(query, driver);
	}

}
