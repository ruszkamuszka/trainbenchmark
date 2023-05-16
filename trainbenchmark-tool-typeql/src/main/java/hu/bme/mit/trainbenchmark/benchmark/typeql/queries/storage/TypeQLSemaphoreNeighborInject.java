package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSemaphoreNeighborInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLSemaphoreNeighborInject extends TypeQLMainQuery<TypeQLSemaphoreNeighborInjectMatch>{
	public TypeQLSemaphoreNeighborInject(TypeQLDriver driver) {
		super(RailwayQuery.SEMAPHORENEIGHBOR_INJECT, driver);
	}

	public Stream<ConceptMap> semaphoreNeighborInject() throws Exception{
		driver.read("...");

		TypeQLMatch.Filtered query = TypeQL.match(
			var("route").isa("Route").has("id", var("routeID")).has("entry", var("semaphore")),
			var("semaphore").eq(true)
		).get("routeID", "semaphore");

		Stream<ConceptMap> results = driver.getTransaction().query().match(query);
		//results.forEach(result -> System.out.println(result.get("sid").asThing().getIID()));
		//driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLSemaphoreNeighborInjectMatch> evaluate() throws Exception {
		return semaphoreNeighborInject().map(conceptMap -> {
			Object routeID = conceptMap.get("routeID").asAttribute().getValue();
			Object semaphore = conceptMap.get("semaphore").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_ROUTE, routeID);
			matchMap.put(QueryConstants.VAR_SEMAPHORE, semaphore);
			return new TypeQLSemaphoreNeighborInjectMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
