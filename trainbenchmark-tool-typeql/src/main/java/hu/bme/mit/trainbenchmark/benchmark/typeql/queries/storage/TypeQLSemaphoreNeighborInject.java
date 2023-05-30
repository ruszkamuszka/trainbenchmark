package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSemaphoreNeighborInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLSemaphoreNeighborInject extends TypeQLMainQuery<TypeQLSemaphoreNeighborInjectMatch>{
	public TypeQLSemaphoreNeighborInject(TypeQLDriver driver) {
		super(RailwayQuery.SEMAPHORENEIGHBOR_INJECT, driver);
	}

	public Map<String, Object> semaphoreNeighborInject() throws Exception{
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\SemaphoreNeighborInject.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		Map<String, Object> matchMap = new HashMap<>();

		driver.transaction(t -> {
			String query = new String(fileBytes, StandardCharsets.UTF_8);

			System.out.println("Executing TypeQL Query: SemaphoreNeighborInject");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SEMAPHORE , result.get("semaphoreID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_ROUTE , result.get("routeID").asAttribute().asLong().getValue());
				}
			);
		}, "READ");
		System.out.println("SemaphoreNeighborInject size: " +matchMap.size());
		return matchMap;
	}

	@Override
	public Collection<TypeQLSemaphoreNeighborInjectMatch> evaluate() throws Exception {
		final Collection<TypeQLSemaphoreNeighborInjectMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = semaphoreNeighborInject();
		matches.add(new TypeQLSemaphoreNeighborInjectMatch(matchMap));
		return matches;
	}
}
