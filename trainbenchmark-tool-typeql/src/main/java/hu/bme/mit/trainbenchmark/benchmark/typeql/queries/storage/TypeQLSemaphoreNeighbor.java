package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSemaphoreNeighborMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLSemaphoreNeighbor extends TypeQLMainQuery<TypeQLSemaphoreNeighborMatch>{
	public TypeQLSemaphoreNeighbor(final TypeQLDriver driver) {
		super(RailwayQuery.SEMAPHORENEIGHBOR, driver);
	}

	public Stream<ConceptMap> semaphoreNeighbor() throws Exception{
		driver.read("...");

		TypeQLMatch.Filtered query = TypeQL.match(
			var("route1").isa("Route").has("id", var("route1ID")).has("exit", var("semaphore")),
			var("sensor1").isa("Sensor").has("id", var("sensor1ID")),
			var("switch1").isa("Switch").has("id", var("te1")),
			var("segment1").isa("Segment").has("id", var("segment1ID")),
			var().rel("Route", var("route1")).rel("Sensor", var("sensor1")).isa("requires"),
			var().rel("Sensor", var("sensor1")).rel("Switch", var("switch1")).isa("monitoredBy"),
			var().rel("Segment", var("segment1")).rel("Switch", var("switch1")).isa("connectsTo"),
			var("route2").isa("Route").has("id", var("route2ID")).has("entry", var("entry")),
			var("sensor2").isa("Sensor").has("id", var("sensor2ID")),
			var("switch2").isa("Switch").has("id", var("te2")),
			var("segment2").isa("Segment").has("id", var("segment2ID")),
			var().rel("Route", var("route2")).rel("Sensor", var("sensor2")).isa("requires"),
			var().rel("Sensor", var("sensor2")).rel("Switch", var("switch2")).isa("monitoredBy"),
			var().rel("Segment", var("segment2")).rel("Switch", var("switch2")).isa("connectsTo"),
			var("route1ID").neq(var("route2ID")),
			var("entry").neq(var("semaphore"))
			).get("semaphore", "route1ID", "route2ID", "sensor1ID", "sensor2ID", "te1", "te2");

		Stream<ConceptMap> results = driver.getTransaction().query().match(query);
		results.forEach(result -> System.out.println(result.get("sid").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLSemaphoreNeighborMatch> evaluate() throws Exception {
		return semaphoreNeighbor().map(conceptMap -> {
			Object semaphore = conceptMap.get("semaphore").asAttribute().getValue();
			Object route1ID = conceptMap.get("route1ID").asAttribute().getValue();
			Object route2ID = conceptMap.get("route2ID").asAttribute().getValue();
			Object sensor1ID = conceptMap.get("sensor1ID").asAttribute().getValue();
			Object sensor2ID = conceptMap.get("sensor2ID").asAttribute().getValue();
			Object te1 = conceptMap.get("te1").asAttribute().getValue();
			Object te2 = conceptMap.get("te2").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SEMAPHORE, semaphore);
			matchMap.put(QueryConstants.VAR_ROUTE1, route1ID);
			matchMap.put(QueryConstants.VAR_ROUTE2, route2ID);
			matchMap.put(QueryConstants.VAR_SENSOR1, sensor1ID);
			matchMap.put(QueryConstants.VAR_SENSOR2, sensor2ID);
			matchMap.put(QueryConstants.VAR_TE1, te1);
			matchMap.put(QueryConstants.VAR_TE2, te2);
			return new TypeQLSemaphoreNeighborMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
