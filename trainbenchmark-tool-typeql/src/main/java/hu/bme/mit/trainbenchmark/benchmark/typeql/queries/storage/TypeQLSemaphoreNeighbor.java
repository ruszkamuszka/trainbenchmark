package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSemaphoreNeighborMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLSemaphoreNeighbor extends TypeQLMainQuery<TypeQLSemaphoreNeighborMatch>{
	public TypeQLSemaphoreNeighbor(final TypeQLDriver driver) {
		super(RailwayQuery.SEMAPHORENEIGHBOR, driver);
	}

	boolean found = false;
	public Map<String, Object> semaphoreNeighbor() throws Exception{
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\SemaphoreNeighbor.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		String query = new String(fileBytes, StandardCharsets.UTF_8);

		Map<String, Object> matchMap = new HashMap<>();
		driver.transaction(t -> {
			System.out.println("Executing TypeQL Query: SemaphoreNeighbor");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SEMAPHORE, result.get("semaphore").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_ROUTE1, result.get("route1ID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_ROUTE2, result.get("route2ID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SENSOR1, result.get("sensor1ID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SENSOR2, result.get("sensor2ID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_TE1, result.get("te1").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_TE2, result.get("te2").asAttribute().asLong().getValue());
				}
			);
		}, "READ");
		found = matchMap.size() == 0 ? false : true;
		return matchMap;
	}

	@Override
	public Collection<TypeQLSemaphoreNeighborMatch> evaluate() throws Exception {
		final Collection<TypeQLSemaphoreNeighborMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = semaphoreNeighbor();
		if(found){
			matches.add(new TypeQLSemaphoreNeighborMatch(matchMap));
		}
		System.out.println("SemaphoreNeighbor size: " +matches.size());
		return matches;
	}
}
