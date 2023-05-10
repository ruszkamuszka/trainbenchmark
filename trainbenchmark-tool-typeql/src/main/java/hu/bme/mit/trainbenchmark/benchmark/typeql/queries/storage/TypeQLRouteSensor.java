package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLPosLengthMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.not;
import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLRouteSensor extends TypeQLMainQuery<TypeQLRouteSensorMatch>{

	public TypeQLRouteSensor(final TypeQLDriver driver) {
		super(RailwayQuery.ROUTESENSOR, driver);
	}

	public Stream<ConceptMap> routeSensor() throws Exception {
		driver.read("...");

		TypeQLMatch getQuery = TypeQL.match(
			var("route").isa("Route").has("id", var("routeID")),
			var("sensor").isa("Sensor").has("id", var("sensorID")),
			var("switch").isa("Switch").has("id", var("switchID")),
			var().rel("Sensor", var("sensor")).rel("Switch", var("switch")).isa("monitoredBy"),
			var("switchPosition").isa("SwitchPosition").has("id", var("swpID")),
			var("swpID").eq("switchID"),
			var().rel("Route", var("route")).rel("SwitchPosition", var("switchPosition")).isa("follows"),
			not(
				var()
					.rel("Route", var("route"))
					.rel("Sensor", var("sensor"))
					.isa("requires")
			)
			).get("routeID", "sensorID", "switchID");


		Stream<ConceptMap> results = driver.getTransaction().query().match(getQuery);
		results.forEach(result -> System.out.println(result.get("rq").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLRouteSensorMatch> evaluate() throws Exception {
		return routeSensor().map(conceptMap -> {
			Object routeID = conceptMap.get("routeID").asAttribute().getValue();
			Object sensorID = conceptMap.get("sensorID").asAttribute().getValue();
			Object switchID = conceptMap.get("switchID").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_ROUTE, routeID);
			matchMap.put(QueryConstants.VAR_SENSOR, sensorID);
			matchMap.put(QueryConstants.VAR_SW, switchID);
			return new TypeQLRouteSensorMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
