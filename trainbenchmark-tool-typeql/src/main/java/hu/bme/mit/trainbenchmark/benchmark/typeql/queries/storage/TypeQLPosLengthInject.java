package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLPosLengthInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLPosLengthInject extends TypeQLMainQuery<TypeQLPosLengthInjectMatch>{
	public TypeQLPosLengthInject(TypeQLDriver driver) {
		super(RailwayQuery.POSLENGTH_INJECT, driver);
	}

	public Map<String, Object> posLengthInject() throws Exception{
		Map<String, Object> matchMap = new HashMap<>();

		driver.transaction(t -> {
			String query = "match"
			+ "$segment isa Segment, has id $segmentID, has length $length;"
			+ "get"
			+ "$segmentID;";

			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
						matchMap.put(QueryConstants.VAR_SEGMENT , result.get("segmentID").asAttribute().getValue().asLong());
				}
			);
		}, "READ");
		return matchMap;
	}

	@Override
	public Collection<TypeQLPosLengthInjectMatch> evaluate() throws Exception {
		final Collection<TypeQLPosLengthInjectMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = posLengthInject();
		matches.add(new TypeQLPosLengthInjectMatch(matchMap));
		return matches;
	}
}
