package hu.bme.mit.trainbenchmark.benchmark.typeql.queries;

import hu.bme.mit.trainbenchmark.benchmark.operations.ModelQuery;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLMatch;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

public abstract class TypeQLQuery<TQLMatch extends TypeQLMatch> extends ModelQuery<TQLMatch, TypeQLDriver> {
	public TypeQLQuery(final RailwayQuery query, final TypeQLDriver driver){
		super(query, driver);
	}
}
