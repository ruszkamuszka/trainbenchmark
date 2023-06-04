package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLRouteSensorInject extends TypeQLMainQuery<TypeQLRouteSensorInjectMatch>{
	public TypeQLRouteSensorInject(TypeQLDriver driver) {
		super(RailwayQuery.ROUTESENSOR_INJECT, driver);
	}

	public Map<String, Object> routeSensorInject() throws Exception{
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\RouteSensorInject.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		Map<String, Object> matchMap = new HashMap<>();

		driver.transaction(t -> {
			String query = new String(fileBytes, StandardCharsets.UTF_8);

			System.out.println("Executing TypeQL Query: RouteSensorInject");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_ROUTE , result.get("routeID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SENSOR , result.get("sensorID").asAttribute().asLong().getValue());
				}
			);
		}, "READ");
		return matchMap;
	}

	@Override
	public Collection<TypeQLRouteSensorInjectMatch> evaluate() throws Exception {
		final Collection<TypeQLRouteSensorInjectMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = routeSensorInject();
		matches.add(new TypeQLRouteSensorInjectMatch(matchMap));
		return matches;
	}
}
