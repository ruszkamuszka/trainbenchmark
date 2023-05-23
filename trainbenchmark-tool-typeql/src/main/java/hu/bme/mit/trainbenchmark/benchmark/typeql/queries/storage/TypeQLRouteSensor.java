package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLRouteSensor extends TypeQLMainQuery<TypeQLRouteSensorMatch>{

	public TypeQLRouteSensor(final TypeQLDriver driver) {
		super(RailwayQuery.ROUTESENSOR, driver);
	}

	public Map<String, Object> routeSensor() throws Exception {
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\RouteSensor.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		String query = new String(fileBytes, StandardCharsets.UTF_8);

		Map<String, Object> matchMap = new HashMap<>();
		transaction(t -> {
			System.out.println("Executing TypeQL Query: " + query);
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_ROUTE, result.get("routeID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SENSOR, result.get("sensorID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SWP, result.get("switchPositionID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SW, result.get("switchID").asAttribute().asLong().getValue());
				}
			);
		}, "READ");
		System.out.println("Match size: " +matchMap.size());
		return matchMap;
	}

	@Override
	public Collection<TypeQLRouteSensorMatch> evaluate() throws Exception {
		final Collection<TypeQLRouteSensorMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = routeSensor();
		matches.add(new TypeQLRouteSensorMatch(matchMap));
		for (TypeQLRouteSensorMatch match : matches) {
			System.out.println("SensorID: "+match.getSensor()); // Print each match element
		}
		return matches;
	}
}
