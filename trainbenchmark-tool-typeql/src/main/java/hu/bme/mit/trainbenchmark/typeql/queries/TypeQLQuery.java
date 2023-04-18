package hu.bme.mit.trainbenchmark.typeql.queries;

import hu.bme.mit.trainbenchmark.benchmark.operations.ModelQuery;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;
import hu.bme.mit.trainbenchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.typeql.matches.TypeQLMatch;

public abstract class TypeQLQuery<TQLMatch extends TypeQLMatch> extends ModelQuery<TQLMatch, TypeQLDriver> {
	public TypeQLQuery(final RailwayQuery query, final TypeQLDriver driver){
		super(query, driver);
	}
}
