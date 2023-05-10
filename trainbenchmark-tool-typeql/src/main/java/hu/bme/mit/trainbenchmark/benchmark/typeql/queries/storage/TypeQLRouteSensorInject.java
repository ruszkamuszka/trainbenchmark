package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLRouteSensorInject extends TypeQLMainQuery<TypeQLRouteSensorInjectMatch>{
	public TypeQLRouteSensorInject(TypeQLDriver driver) {
		super(RailwayQuery.ROUTESENSOR_INJECT, driver);
	}

	public Stream<ConceptMap> routeSensorInject() throws Exception{
		driver.read("...");

		TypeQLMatch.Filtered query = TypeQL.match(
			var("route").isa("Route").has("id", var("routeID")),
			var("sensor").isa("Sensor").has("id", var("sensorID")),
			var().rel("Route", var("route")).rel("Sensor", var("sensor")).isa("requires")
			).get("routeID", "sensorID");

		Stream<ConceptMap> results = driver.getTransaction().query().match(query);
		results.forEach(result -> System.out.println(result.get("sid").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLRouteSensorInjectMatch> evaluate() throws Exception {
		return routeSensorInject().map(conceptMap -> {
			Object routeID = conceptMap.get("routeID").asAttribute().getValue();
			Object sensorID = conceptMap.get("sensorID").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_ROUTE, routeID);
			matchMap.put(QueryConstants.VAR_SENSOR, sensorID);
			return new TypeQLRouteSensorInjectMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
