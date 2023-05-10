package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchSetMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.*;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLSwitchSet extends TypeQLMainQuery<TypeQLSwitchSetMatch>{
	public TypeQLSwitchSet(final TypeQLDriver driver) {
		super(RailwayQuery.SWITCHSET, driver);
	}

	public Stream<ConceptMap> switchSet() throws Exception {
		driver.read("...");

		TypeQLMatch getQuery = TypeQL.match(
			var("rq").rel("r").rel("s").isa("requires"),
			var("r").isa("Route").has("id", var("r-id")),
			var("s").isa("Semaphore").has("id", var("s-id"))
		).get("rq", "r", "s");


		Stream<ConceptMap> results = driver.getTransaction().query().match(getQuery);
		results.forEach(result -> System.out.println(result.get("rq").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLSwitchSetMatch> evaluate() throws Exception {
		Stream<ConceptMap> conceptMapStream = switchSet();
		List<TypeQLSwitchSetMatch> switchSetMatches = new ArrayList<>();
		conceptMapStream.forEach(conceptMap -> {
			Object semaphore = conceptMap.get("rq").asEntity().getType();
			Object route = conceptMap.get("r").asEntity().getType();
			Object swP = conceptMap.get("s").asEntity().getType();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SEMAPHORE, semaphore);
			matchMap.put(QueryConstants.VAR_ROUTE, route);
			matchMap.put(QueryConstants.VAR_SWP, swP);
			TypeQLSwitchSetMatch switchSetMatch = new TypeQLSwitchSetMatch(matchMap);
			switchSetMatches.add(switchSetMatch);
		});
		return switchSetMatches;
	}
}
