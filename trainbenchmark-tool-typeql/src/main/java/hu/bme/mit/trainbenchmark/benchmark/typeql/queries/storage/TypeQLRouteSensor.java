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

	boolean found = false;
	public Map<String, Object> routeSensor() throws Exception {
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\RouteSensor.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		String query = new String(fileBytes, StandardCharsets.UTF_8);

		Map<String, Object> matchMap = new HashMap<>();
		driver.transaction(t -> {
			System.out.println("Executing TypeQL Query: RouteSensor");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_ROUTE, result.get("routeID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SENSOR, result.get("sensorID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SWP, result.get("switchPositionID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SW, result.get("switchID").asAttribute().asLong().getValue());
				}
			);
		}, "READ");
		found = matchMap.size() == 0 ? false : true;
		return matchMap;
	}

	@Override
	public Collection<TypeQLRouteSensorMatch> evaluate() throws Exception {
		final Collection<TypeQLRouteSensorMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = routeSensor();
		if(found){
			matches.add(new TypeQLRouteSensorMatch(matchMap));
		}
		System.out.println("RouteSensor size: " +matches.size() +"  "+ matchMap.get(QueryConstants.VAR_ROUTE)+"  "+ matchMap.get(QueryConstants.VAR_SENSOR));
		return matches;
	}
}
